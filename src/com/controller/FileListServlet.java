package com.controller;
import com.alibaba.fastjson.JSON;
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
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/fileList")
public class FileListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置响应内容类型和字符编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");

        // 初始化分页参数
        int page = 1;
        int pageSize = 10;

        // 从请求参数中获取分页信息
        try {
            page = Integer.parseInt(request.getParameter("page"));
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        } catch (NumberFormatException e) {
            // 参数解析失败，使用默认值
        }

        // 从会话中获取用户信息
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("person");
        int userId = (person != null) ? person.getId() : 0;
        boolean isPublicOnly = (person == null);

        // 创建文件服务实例
        IFileService fileService = new FileService();

        try {
            // 获取文件列表和文件总数
            List<File> files = fileService.getFiles(page, pageSize, userId, isPublicOnly);
            int totalCount = fileService.getFileCount(userId, isPublicOnly);

            // 处理每个文件，添加 canDownload 字段
            for (File file : files) {
                Map<String, Object> fileMap = new HashMap<>();
                fileMap.put("id", file.getId());
                fileMap.put("filename", file.getFilename());
                fileMap.put("file_size", file.getFile_size());
                fileMap.put("file_type", file.getFile_type());
                fileMap.put("is_public", file.getIs_public());
                fileMap.put("download_count", file.getDownload_count());
                fileMap.put("is_blocked", file.getIs_blocked());
                fileMap.put("canDownload", person != null && ("Y".equals(file.getIs_public()) ||
                        String.valueOf(person.getId()).equals(file.getUploader_id())));
            }

            // 构建最终的响应数据
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("files", files);
            responseData.put("totalCount", totalCount);

            // 使用 FastJSON 将响应数据转换为 JSON 字符串
            String json = JSON.toJSONString(responseData);

            // 输出 JSON 响应
            response.getWriter().print(json);
        } catch (SQLException | ClassNotFoundException e) {
            // 记录异常信息
            e.printStackTrace();
            // 返回错误响应
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "获取文件列表时出错");
        }
    }
}