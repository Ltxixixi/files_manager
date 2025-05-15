package com.controller;
import com.entity.Person;
import com.service.IPersonService;
import com.service.PersonService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebServlet( urlPatterns = "/register")
public class RegisterControl extends HttpServlet {
    private static final int MIN_PASSWORD_LENGTH = 6;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        Map<String, String> errors = new HashMap<>();
        Map<String, String> formData = new HashMap<>();

        // 3. 非空校验
        if (username == null || username.trim().isEmpty()) {
            errors.put("username", "用户名不能为空");
        } else {
            formData.put("username", username);
        }

        if (password == null || password.trim().isEmpty()) {
            errors.put("password", "密码不能为空");
        } else if (password.length() < MIN_PASSWORD_LENGTH) {
            errors.put("password", "密码长度不能少于" + MIN_PASSWORD_LENGTH + "位");
        }

        String json;
        if (!errors.isEmpty()) {
            // 打印error内容
            json = "{\"success\": false, \"message\": \"注册信息有误： " + errors + "\"}";
        } else {
            Person person = new Person();
            person.setUsername(username);
            person.setPassword(password);
            person.setRole(role);
            IPersonService service = new PersonService();
            try {
                int num = service.insert(person);
                if (num > 0) {
                    json = "{\"success\": true, \"message\": \"注册成功！即将跳转到登录页面...\"}";
                } else {
                    json = "{\"success\": false, \"message\": \"注册失败，请重试\"}";
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                json = "{\"success\": false, \"message\": \"系统错误: " + e.getMessage() + "\"}";
            }
        }
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
        out.close();
    }
}