package com.controller;
import com.dao.FileDao;
import com.dao.IFileDao;
import com.dao.IReportsDao;

import com.dao.ReportsDao;
import com.entity.File;
import com.entity.Person;
import com.entity.Reports;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/viewReports")
public class ReportsListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否是管理员
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        Person person = (Person) request.getSession().getAttribute("person");

        IReportsDao reportDao = new ReportsDao();
        IFileDao fileDao = new FileDao();

        try {
            // 获取所有举报记录
            List<Reports> reports = reportDao.getAllReports();

            // 为每个举报记录关联文件信息
            for (Reports report : reports) {
                File file = fileDao.getFileById(Integer.parseInt(report.getFile_id()));
                report.setFile(file);
            }

            request.setAttribute("reports", reports);
            request.getRequestDispatcher("viewReports.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "数据库错误");
        }
    }
}