package com.controller;

import com.dao.FileDao;
import com.dao.IFileDao;
import com.entity.Person;
import com.service.IReportsService;
import com.service.ReportsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/fileBlock")
public class FileBlockControl extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否是管理员
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        Person person = (Person) request.getSession().getAttribute("person");

        int fileId = Integer.parseInt(request.getParameter("fileId"));
        int reportId = Integer.parseInt(request.getParameter("reportId"));

        IFileDao fileDao = new FileDao();
        IReportsService reportDao = new ReportsService();

        try {
            // 封存文件
            fileDao.blockFile(fileId);

            // 更新举报状态
            reportDao.updateReportStatus(reportId, "已处理-文件已封存");

            // 返回举报管理页面
            response.sendRedirect("viewReports");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "数据库错误");
        }
    }
}