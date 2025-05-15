<!-- Resports.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>举报管理</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #ffffff;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            color: #2196F3;
            border-bottom: 1px solid #e3f2fd;
            padding-bottom: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #e3f2fd;
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #e3f2fd;
            color: #2196F3;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f8fbff;
        }
        .status-pending {
            color: #ff9800;
            font-weight: bold;
        }
        .status-resolved {
            color: #4CAF50;
            font-weight: bold;
        }
        .action-btn {
            padding: 5px 10px;
            margin: 0 5px;
            text-decoration: none;
            color: white;
            border-radius: 3px;
            font-size: 14px;
            transition: background-color 0.3s; background-color: #2196F3;
        }
        .action-btn:hover {
            background-color: #1976D2;
        }
        .block-btn {
            background-color: #f44336;
        }
        .block-btn:hover {
            background-color: #d32f2f;
        }
        .resolve-btn {
            background-color: #2196F3;
        }
        .resolve-btn:hover {
            background-color: #1976D2;
        }
        .back-btn {
            display: inline-block;
            margin-bottom: 20px;
            padding: 8px 15px;
            background-color: #2196F3;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .back-btn:hover {
            background-color: #1976D2;
        }
    </style>
</head>
<body>
<div class="container">
    <a href="fileList.jsp" class="back-btn" style="float: right">返回文件列表</a>
    <h1>举报管理</h1>

    <table>
        <thead>
        <tr>
            <th>举报ID</th>
            <th>文件名</th>
            <th>举报人ID</th>
            <th>举报原因</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reports}" var="report">
            <tr>
                <td>${ireport.d}</td>
                <td>${report.file.filename}</td>
                <td>${report.reporter_id}</td>
                <td>${report.reason}</td>
                <td class="status-${report.status eq '待处理' ? 'pending' : 'resolved'}">${report.status}</td>
                <td>
                    <a href="fileDownload?fileId=${report.file_id}" class="action-btn ">下载文件</a>
                    <a href="blockFile?fileId=${report.file_id}&reportId=${report.id}" class="action-btn">封存文件</a>
                    <c:if test="${report.status eq '待处理'}">
                        <a href="/reportResolve?reportId=${report.id}" class="action-btn">标记为已处理</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>