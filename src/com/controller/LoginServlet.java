package com.controller;
import com.entity.Person;
import com.service.IPersonService;
import com.service.PersonService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        IPersonService service = new PersonService();
        try {
            Person person = service.selet(username, password);
            String json;
            if (person != null) {
                HttpSession session = request.getSession();
                session.setAttribute("person", person);
                session.setAttribute("username", person.getUsername());
                session.setAttribute("password", person.getPassword());
                session.setAttribute("id", person.getId());
                session.setAttribute("role", person.getRole());
                json = "{\"success\": true}";
            } else {
                json = "{\"success\": false, \"message\": \"输入账号不存在或密码错误\"}";
            }
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
            out.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}