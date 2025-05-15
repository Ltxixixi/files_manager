package com.controller;

import com.entity.File;
import com.entity.Person;
import com.service.IFileService;
import com.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@WebServlet("/fileDownload")
public class FileDownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        String fileIdStr = request.getParameter("fileId");
        if (fileIdStr == null || fileIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "文件ID不能为空");
            return;
        }

        int fileId = Integer.parseInt(fileIdStr);
        IFileService fileService = new FileService();

        try {
            File file = fileService.getFileById(fileId);
            if (file == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "文件不存在");
                return;
            }

            // 检查文件是否被封存
            if ("Y".equals(file.getIs_blocked())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "该文件已被封存，无法下载");
                return;
            }

            HttpSession session = request.getSession();
            Person person = (Person) session.getAttribute("person");

            // 检查权限：游客只能查看公共文件但不能下载
            if (person == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "请登录后下载文件");
                return;
            }

            // 检查权限：非公共文件且不是上传者
            if ("N".equals(file.getIs_public()) && !String.valueOf(person.getId()).equals(file.getUploader_id())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "无权限下载此文件");
                return;
            }

            // 更新下载次数
            fileService.incrementDownloadCount(fileId);

            // 设置响应头
            String header = request.getHeader("User-Agent");
            String encodedFileName;
            if (header.contains("MSIE") || header.contains("Trident")) {
                encodedFileName = URLEncoder.encode(file.getFilename(), "UTF-8").replaceAll("\\+", "%20");
            } else {
                encodedFileName = new String(file.getFilename().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + encodedFileName + "\"");
            response.setContentLength(file.getFile_size());

            // 读取文件并写入响应
            try (FileInputStream fis = new FileInputStream(file.getFile_path());
                 OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "下载文件时出错");
        }
    }
}