package com.controller;
import com.service.IFileService;
import com.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet( urlPatterns = "/handleReport")
public class ReportHandleControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        int fileId = Integer.parseInt(request.getParameter("fileId"));
        IFileService fileService = new FileService();
        String json;
        try {
            boolean result = fileService.blockFile(fileId);
            if (result) {
                json = "{\"success\": true, \"message\": \"文件已封存\"}";
            } else {
                json = "{\"success\": false, \"message\": \"文件封存失败\"}";
            }
        } catch (SQLException | ClassNotFoundException e) {
            json = "{\"success\": false, \"message\": \"系统错误: " + e.getMessage() + "\"}";
        }
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
}