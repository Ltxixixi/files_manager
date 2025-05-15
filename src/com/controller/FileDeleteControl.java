package com.controller;

import com.dao.FileDao;
import com.dao.IFileDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/deleteFile")
public class FileDeleteControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String fileIdParam = request.getParameter("fileId");
        if (fileIdParam == null || fileIdParam.isEmpty()) {
            response.getWriter().write("{\"success\": false, \"message\": \"文件 ID 不能为空\"}");
            return;
        }

        try {
            int fileId = Integer.parseInt(fileIdParam);
            IFileDao fileDao = new FileDao();
            boolean isDeleted = fileDao.deleteFile(fileId);
            if (isDeleted) {
                response.getWriter().write("{\"success\": true, \"message\": \"文件删除成功\"}");
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"文件删除失败\"}");
            }
        }  catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter()
                    .write("{\"success\": false, \"message\": \"系统错误: " + e.getMessage() + "\"}");
        }
    }
}
