package com.controller;
import com.entity.File;
import com.entity.Person;
import com.service.IFileService;
import com.service.FileService;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet("/fileUpload")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024, maxRequestSize = 20 * 1024 * 1024)
public class FileUploadControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("person");

        if (person == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "请先登录");
            return;
        }

        String isPublic = request.getParameter("isPublic");
        Part filePart = request.getPart("file");

        // 获取原始文件名
        String fileName = extractFileName(filePart);
        String uuidName;
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            String fileExt = fileName.substring(fileName.lastIndexOf("."));
            uuidName = UUID.randomUUID().toString() + fileExt;
        } else {
            // 处理没有扩展名的文件
            uuidName = UUID.randomUUID().toString();
        }

        // 获取文件保存路径
        String uploadPath = getServletContext().getRealPath("/uploads");
        java.io.File uploadDir = new java.io.File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        // 保存文件
        String filePath = uploadPath + java.io.File.separator + uuidName;
        filePart.write(filePath);

        // 保存文件信息到数据库
        File file = new File();
        file.setFilename(fileName);
        file.setUuid_name(uuidName);
        file.setFile_path(filePath);
        file.setFile_size((int) filePart.getSize());
        file.setFile_type(filePart.getContentType());
        file.setIs_public("Y".equals(isPublic) ? "Y" : "N");
        file.setUploader_id(String.valueOf(person.getId()));
        file.setDownload_count(0);
        file.setIs_blocked("N");

        IFileService fileService = new FileService();
        try {
            fileService.insert(file);
            response.getWriter().write("{\"status\":\"success\",\"message\":\"文件上传成功\"}");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.getWriter().write("{\"status\":\"error\",\"message\":\"文件上传失败\"}");
        }
    }
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
}