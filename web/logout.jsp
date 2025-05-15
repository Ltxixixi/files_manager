<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    session.invalidate(); // 清除session
    response.sendRedirect("fileList.jsp"); // 重定向回首页
%>
</body>
</html>
