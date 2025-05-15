package com.controller;

import com.entity.Person;
import com.entity.Reports;
import com.service.IReportsService;
import com.service.ReportsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/reportFile")
public class ReportInsertControl extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("person");

        if (person == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "请先登录");
            return;
        }

        String fileId = request.getParameter("fileId");
        String reason = request.getParameter("reason");

        if (fileId == null || fileId.isEmpty() || reason == null || reason.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "参数不完整");
            return;
        }

        Reports report = new Reports();
        report.setFile_id(fileId);
        report.setReporter_id(String.valueOf(person.getId()));
        report.setReason(reason);
        report.setStatus("pending");

        IReportsService reportsService = new ReportsService();
        try {
            reportsService.insert(report);
            out.print("{\"status\":\"success\",\"message\":\"举报已提交\"}");
        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"status\":\"error\",\"message\":\"举报提交失败\"}");
        }
    }
}