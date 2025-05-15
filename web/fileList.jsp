<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>文件管理系统</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #ffffff; /* 白色背景 */
        }
        .header {
            background-color: #2196F3; /* 蓝色导航栏 */
            color: white;
            padding: 15px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .header h1 {
            margin: 0;
            font-size: 24px;
        }
        .user-info {
            display: flex;
            align-items: center;
        }
        .user-info span {
            margin-right: 15px;
        }
        .auth-buttons a {
            color: white;
            text-decoration: none;
            margin-left: 15px;
            padding: 8px 15px;
            border-radius: 4px;
            transition: background-color 0.3s;
        }
        .login-btn {
            background-color: #42A5F5; /* 浅蓝色登录按钮 */
        }
        
        .register-btn {
            background-color: #42A5F5; /* 浅蓝色注册按钮 */
        }
        .logout-btn {
            background-color: #42A5F5; /* 浅蓝色退出按钮 */
        }
        .upload-btn {
            background-color: #ffffff; /* 白色背景 */
            color: #2196F3; /* 蓝色文字 */
            border: 1px solid #ffffff; /* 白色边框 */
            text-decoration: none; /* 确保无下划线 */
        }
        .container {
            max-width: 1200px;
            margin: 20px auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h2 {
            color: #2196F3; /* 蓝色标题 */
            border-bottom: 1px solid #eee;
            padding-bottom: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            table-layout: fixed; /* 添加固定表格布局 */
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
            word-wrap: break-word; /* 允许长单词或URL换行 */
        }
        th {
            background-color: #E3F2FD; /* 浅蓝色表头 */
            font-weight: bold;
        }
        /* 设置各列宽度 */
        #fileTable th:nth-child(1),
        #fileTable td:nth-child(1) {
            width: 20%; /* 文件名列 */
        }
        #fileTable th:nth-child(2),
        #fileTable td:nth-child(2) {
            width: 40%; /* 类型列 - 固定宽度 */
        }
        #fileTable th:nth-child(3),
        #fileTable td:nth-child(3) {
            width: 10%; /* 大小列 */
        }
        #fileTable th:nth-child(4),
        #fileTable td:nth-child(4) {
            width: 10%; /* 下载次数列 */
        }
        #fileTable th:nth-child(5),
        #fileTable td:nth-child(5) {
            width: 10%; /* 状态列 */
        }
        #fileTable th:nth-child(6),
        #fileTable td:nth-child(6) {
            width: 15%; /* 操作列 */
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .pagination {
            margin-top: 20px;
            display: flex;
            justify-content: center;
        }
        .pagination a {
            color: #2196F3; /* 蓝色分页链接 */
            padding: 8px 16px;
            text-decoration: none;
            border: 1px solid #ddd;
            margin: 0 4px;
        }
        .pagination a.active {
            background-color: #2196F3;
            color: white;
            border: 1px solid #2196F3;
        }
        .action-btn {
            padding: 5px 10px;
            margin: 0 5px;
            text-decoration: none;
            color: white;
            border-radius: 3px;
            font-size: 14px;
        }
        .action_reports-btn {
            background-color: #ffffff; /* 白色背景 */
            color: #2196F3; /* 蓝色文字 */
            border: 1px solid #ffffff; /* 白色边框 */
            text-decoration: none; /* 确保无下划线 */
        }
        .download-btn {
            background-color: #42A5F5; 
        }
        .block-btn {
            background-color: #42A5F5; 
        }
        .report-btn {
            background-color: #42A5F5; 
        }
        .no-access {
            color: #999;
            font-style: italic;
        }
        .modal {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
            z-index: 1000;
            justify-content: center;
            align-items: center;
        }
        .modal-content {
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            width: 400px;
            max-width: 90%;
            box-shadow: 0 0 20px rgba(0,0,0,0.3);
        }
        .modal h3 {
            margin-top: 0;
            color: #2196F3; 
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .modal-buttons {
            display: flex;
            justify-content: space-between;
            margin-top: 20px;
        }
        .modal-buttons button {
            padding: 8px 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-weight: bold;
        }
        .submit-btn {
            background-color: #42A5F5; 
            color: white;
        }
        .cancel-btn {
            background-color: #42A5F5; 
            color: white;
        }
        .switch-form {
            text-align: center;
            margin-top: 15px;
        }
        .switch-form a {
            color: #2196F3;
            text-decoration: none;
            cursor: pointer;
        }
        .error-message {
            color: #f44336;
            font-size: 14px;
            margin-top: 5px;
            display: none;
        }
        .file-type-icon {
            margin-right: 5px;
        }
        .file-size {
            text-align: right;
        }
        .file-status {
            text-align: center;
        }
        .public-status {
            display: inline-block;
            padding: 3px 8px;
            border-radius: 3px;
            font-size: 12px;
        }
        .public {
            background-color: #E3F2FD; 
            color: #2196F3;
        }
        .private {
            background-color: #E3F2FD;
            color: #2196F3;
        }
        .blocked {
            background-color: #E3F2FD; 
            color: #2196F3;
        }
        .user-role {
            display: inline-block;
            padding: 3px 8px;
            border-radius: 3px;
            font-size: 12px;
            background-color: #E3F2FD; 
            color: #2196F3;
            margin-right: 10px;
        }
        .admin-role {
            background-color: #E3F2FD; 
            color: #2196F3;
        }
        #reportReason {
            width: 400px; /* 设置固定宽度，可根据需求调整 */
            height: 150px; /* 设置固定高度，可根据需求调整 */
            resize: none; /* 禁止用户手动调整 textarea 的大小 */
            background-color: #e6ecf1;
        }
    </style>
</head>
<body>
<!-- 顶部导航栏 -->
<div class="header">
    <h1>文件管理系统</h1>
    <div class="user-info">
        <c:choose>
            <c:when test="${not empty sessionScope.person}">
                <span>欢迎, ${sessionScope.person.username}</span>
                <div class="auth-buttons">
                    <c:if test="${not empty sessionScope.person && sessionScope.person.role == '管理员'}">
                        <a href="viewReports" class="action-btn">查看所有举报</a>
                    </c:if>
                    <a href="logout.jsp" class="logout-btn">退出登录</a>
                </div>
            </c:when>
            <c:otherwise>
                <span>游客</span>
                <div class="auth-buttons">
                    <a href="javascript:void(0)" onclick="showLoginModal()" class="login-btn">登录</a>
                    <a href="javascript:void(0)" onclick="showRegisterModal()" class="register-btn">注册</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<div class="container">
    <h2>文件列表

        <c:if test="${not empty sessionScope.person}">
            <c:if test="${sessionScope.person.role == '管理员'}">
                <a href="viewReports" class="action_reports-btn style="float: right">查看所有举报</a>
            </c:if>
            <a href="javascript:void(0)" onclick="showUploadModal()" class="upload-btn" style="float: right; margin-top: -5px;">上传文件</a>
        </c:if>

    </h2>
    <div id="fileTableContainer">
        <table id="fileTable">
            <thead>
            <tr>
                <th>文件名</th>
                <th>类型</th>
                <th>大小</th>
                <th>下载次数</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="fileTableBody"></tbody>
        </table>
        <div class="pagination" id="pagination"></div>
    </div>
</div>



<!-- 登录模态框 -->
<div id="loginModal" class="modal">
    <div class="modal-content">
        <h3>登录</h3>
        <div class="form-group">
            <label for="loginUsername">用户名</label>
            <input type="text" id="loginUsername" placeholder="请输入用户名">
            <div id="loginUsernameError" class="error-message"></div>
        </div>
        <div class="form-group">
            <label for="loginPassword">密码</label>
            <input type="password" id="loginPassword" placeholder="请输入密码">
            <div id="loginPasswordError" class="error-message"></div>
        </div>
        <div class="modal-buttons">
            <button class="cancel-btn" onclick="hideModal('loginModal')">取消</button>
            <button class="submit-btn" onclick="login()">登录</button>
        </div>
        <div class="switch-form">
            还没有账号？<a onclick="switchToRegister()">立即注册</a>
        </div>
    </div>
</div>

<!-- 注册模态框 -->
<div id="registerModal" class="modal">
    <div class="modal-content">
        <h3>注册</h3>
        <div class="form-group">
            <label for="registerUsername">用户名</label>
            <input type="text" id="registerUsername" placeholder="请输入用户名">
            <div id="registerUsernameError" class="error-message"></div>
        </div>
        <div class="form-group">
            <label for="registerPassword">密码</label>
            <input type="password" id="registerPassword" placeholder="请输入密码">
            <div id="registerPasswordError" class="error-message"></div>
        </div>
        <div class="form-group">
            <label for="registerRole">角色</label>
            <select id="registerRole">
                <option value="用户">用户</option>
                <option value="管理员">管理员</option>
            </select>
        </div>
        <div class="modal-buttons">
            <button class="cancel-btn" onclick="hideModal('registerModal')">取消</button>
            <button class="submit-btn" onclick="register()">注册</button>
        </div>
        <div class="switch-form">
            已有账号？<a onclick="switchToLogin()">立即登录</a>
        </div>
    </div>
</div>

<!-- 举报模态框 -->
<div id="reportModal" class="modal">
    <div class="modal-content">
        <h3>举报文件</h3>
        <input type="hidden" id="reportFileId">
        <div class="form-group">
            <label for="reportReason">举报内容</label>
            <textarea id="reportReason" rows="4"></textarea>
            <div id="reportReasonError" class="error-message"></div>
        </div>
        <div class="modal-buttons">
            <button class="cancel-btn" onclick="hideModal('reportModal')">取消</button>
            <button class="submit-btn" onclick="submitReport()">提交</button>
        </div>
    </div>
</div>

<!-- 上传文件模态框 -->
<div id="uploadModal" class="modal">
    <div class="modal-content">
        <h3>上传文件</h3>
        <div class="form-group">
            <label for="uploadFile">选择文件和权限</label>
            <input type="file" id="uploadFile">
            <div id="uploadFileError" class="error-message"></div>
        </div>
        <div class="form-group">
            <div>
                <input type="radio" id="publicFile" name="filePermission" value="Y" checked>
                <label for="publicFile">公开</label>
                <input type="radio" id="privateFile" name="filePermission" value="N">
                <label for="privateFile">私有</label>
            </div>
        </div>
        <div class="modal-buttons">
            <button class="cancel-btn" onclick="hideModal('uploadModal')">取消</button>
            <button class="submit-btn" onclick="uploadFile()">上传</button>
        </div>
    </div>
</div>


<script>
    // 全局变量
    var currentPage = 1;
    var pageSize = 10;
    var totalCount = 0;

    // 加载文件列表
    function loadFiles(page) {
        console.log("开始加载第", page, "页文件列表");
        currentPage = page;
        $.get("fileList", { page: page, pageSize: pageSize, timestamp: new Date().getTime() }, function (data) {
            console.log("获取文件列表数据:", data);
            var files = data.files;
            totalCount = data.totalCount;

            var $tbody = $("#fileTableBody");
            $tbody.empty();

            if (files.length === 0) {
                $tbody.append('<tr><td colspan="6" style="text-align:center;">暂无文件</td></tr>');
                $("#pagination").empty();
                return;
            }

            $.each(files, function (index, file) {
                var sizeKB = (file.file_size / 1024).toFixed(2);
                var sizeMB = (file.file_size / (1024 * 1024)).toFixed(2);
                var sizeText = file.file_size < 1024 * 1024?
                    sizeKB + 'KB' : sizeMB + 'MB';

                var isPublic = file.is_public === "Y";
                var isBlocked = file.is_blocked === "Y";

                var $tr = $("<tr>");

                // 文件名
                $tr.append($("<td>").text(file.filename));

                // 文件类型
                var $typeCell = $("<td>");
                var iconClass = getFileIconClass(file.file_type);
                $typeCell.append($("<i>").addClass("file-type-icon " + iconClass));
                $typeCell.append(file.file_type);
                $tr.append($typeCell);

                // 文件大小
                $tr.append($("<td>").addClass("file-size").text(sizeText));

                // 下载次数
                $tr.append($("<td>").text(file.download_count));

                // 状态
                var $statusCell = $("<td>").addClass("file-status");
                var $publicStatus = $("<span>").addClass("public-status " +
                    (isPublic? "public" : "private"))
                    .text(isPublic? "公开" : "私有");
                $statusCell.append($publicStatus);

                if (isBlocked) {
                    $statusCell.append(" / ");
                    $statusCell.append($("<span>").addClass("public-status blocked")
                        .text("已封存"));
                }
                $tr.append($statusCell);

                // 操作
                var $actions = $("<td>");

                // 下载按钮
                var canDownload = !isBlocked && ${not empty sessionScope.person};
                if (canDownload) {
                    $actions.append($("<a>")
                        .addClass("action-btn download-btn")
                        .attr("href", "#")
                        .text("下载")
                        .click(function(e) {
                            e.preventDefault();
                            downloadFile(file.id, file.filename);
                            return false;
                        }));
                } else {
                    $actions.append($("<span>").addClass("no-access").text("无权限"));
                }

                // 举报按钮（登录用户且文件未被封存）
                <c:if test="${not empty sessionScope.person}">
                if (!isBlocked) {
                    $actions.append(" ");
                    $actions.append($("<a>")
                        .addClass("action-btn report-btn")
                        .attr("href", "#")
                        .text("举报")
                        .click(function () {
                            showReportDialog(file.id, file.filename);
                            return false;
                        }));
                }
                </c:if>

                $tr.append($actions);
                $tbody.append($tr);
            });

            renderPagination(page);
            console.log("第", page, "页文件列表加载完成");
        }, "json");
    }

    // 渲染分页
    function renderPagination(currentPage) {
        var totalPages = Math.ceil(totalCount / pageSize);
        var $pagination = $("#pagination");
        $pagination.empty();

        if (totalPages <= 1) return;

        // 上一页
        if (currentPage > 1) {
            $pagination.append($("<a>")
                .attr("href", "#")
                .html("&laquo; 上一页")
                .click(function () {
                    loadFiles(currentPage - 1);
                    return false;
                }));
        }

        // 显示页码（最多显示5个页码）
        var startPage = Math.max(1, currentPage - 2);
        var endPage = Math.min(totalPages, startPage + 4);

        if (endPage - startPage < 4) {
            startPage = Math.max(1, endPage - 4);
        }

        for (var i = startPage; i <= endPage; i++) {
            if (i === currentPage) {
                $pagination.append($("<a>")
                    .addClass("active")
                    .attr("href", "#")
                    .text(i));
            } else {
                $pagination.append($("<a>")
                    .attr("href", "#")
                    .text(i)
                    .click(function () {
                        loadFiles(parseInt($(this).text()));
                        return false;
                    }));
            }
        }

        // 下一页
        if (currentPage < totalPages) {
            $pagination.append($("<a>")
                .attr("href", "#")
                .html("下一页 &raquo;")
                .click(function () {
                    loadFiles(currentPage + 1);
                    return false;
                }));
        }
    }

    // 根据文件类型获取图标类
    function getFileIconClass(fileType) {
        if (fileType.startsWith("image/")) {
            return "fa fa-file-image-o";
        } else if (fileType === "application/pdf") {
            return "fa fa-file-pdf-o";
        } else if (fileType.startsWith("text/") || fileType === "application/msword" ||
            fileType === "application/vnd.openxmlformats-officedocument.wordprocessingml.document") {
            return "fa fa-file-text-o";
        } else if (fileType === "application/vnd.ms-excel" ||
            fileType === "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") {
            return "fa fa-file-excel-o";
        } else if (fileType === "application/vnd.ms-powerpoint" ||
            fileType === "application/vnd.openxmlformats-officedocument.presentationml.presentation") {
            return "fa fa-file-powerpoint-o";
        } else if (fileType === "application/zip" || fileType === "application/x-rar-compressed") {
            return "fa fa-file-archive-o";
        } else {
            return "fa fa-file-o";
        }
    }

    $(document).ready(function () {
        // 初始加载第一页
        loadFiles(currentPage);
    });

    // 下载文件函数
    function downloadFile(fileId, filename) {
        // 显示下载中状态
        var $downloadBtns = $(".download-btn");
        $downloadBtns.prop("disabled", true).text("下载中...");

        // 创建隐藏的iframe来实现文件下载
        var iframe = document.createElement("iframe");
        iframe.style.display = "none";
        iframe.src = "fileDownload?fileId=" + fileId;
        document.body.appendChild(iframe);

        // 检查下载是否完成（这种方法不完美，但可以工作）
        setTimeout(function() {
            document.body.removeChild(iframe);
            $downloadBtns.prop("disabled", false).text("下载");

            // 下载完成后刷新页面
            loadFiles(currentPage);

            // 或者显示下载完成提示
            alert("文件 '" + filename + "' 下载完成");
        }, 1000); // 3秒后假设下载完成
    }




    // 上传文件
    function uploadFile() {
        var fileInput = document.getElementById("uploadFile");
        var file = fileInput.files[0];
        var isPublic = $("input[name='filePermission']:checked").val();

        if (!file) {
            $("#uploadFileError").text("请选择文件").show();
            return;
        }

        var formData = new FormData();
        formData.append("file", file);
        formData.append("isPublic", isPublic);

        $.ajax({
            url: "fileUpload",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            dataType: "json",
            success: function (response) {
                console.log("上传成功响应数据:", response);
                if (response.status === "success") {
                    alert("文件上传成功");
                    hideModal("uploadModal");
                    // 重置文件输入
                    $("#uploadFile").val("");
                    // 重新加载第一页文件列表
                    loadFiles(1);
                } else {
                    $("#uploadFileError").text(response.message).show();
                }
            },
            error: function (xhr, status, error) {
                $("#uploadFileError").text("文件上传失败: " + error).show();
                console.error("上传错误:", xhr.responseText);
            }
        });
    }

    // 显示模态框
    function showModal(modalId) {
        $("#" + modalId).css("display", "flex");
        // 清空错误信息
        $(".error-message").hide().text("");
    }

    // 隐藏模态框
    function hideModal(modalId) {
        $("#" + modalId).hide();
    }

    // 显示登录模态框
    function showLoginModal() {
        showModal("loginModal");
    }

    // 显示注册模态框
    function showRegisterModal() {
        showModal("registerModal");
    }

    // 显示举报模态框
    function showReportDialog(fileId, filename) {
        $("#reportFileId").val(fileId);
        $("#reportModal h3").text("举报文件: " + filename);
        showModal("reportModal");
    }

    // 显示上传文件模态框
    function showUploadModal() {
        showModal("uploadModal");
    }

    // 切换到注册表单
    function switchToRegister() {
        hideModal("loginModal");
        showRegisterModal();
    }

    // 切换到登录表单
    function switchToLogin() {
        hideModal("registerModal");
        showLoginModal();
    }

    // 登录功能
    function login() {
        var username = $("#loginUsername").val().trim();
        var password = $("#loginPassword").val().trim();

        // 验证输入
        if (!username) {
            $("#loginUsernameError").text("请输入用户名").show();
            return;
        }
        if (!password) {
            $("#loginPasswordError").text("请输入密码").show();
            return;
        }

        $.ajax({
            url: "login",
            type: "POST",
            data: { username: username, password: password },
            dataType: "json",
            success: function (response) {
                if (response.success) {
                    hideModal("loginModal");
                    location.reload(); // 刷新页面更新登录状态
                } else {
                    $("#loginPasswordError").text(response.message).show();
                }
            },
            error: function () {
                $("#loginPasswordError").text("登录失败，请稍后重试").show();
            }
        });
    }

    // 注册功能
    function register() {
        var username = $("#registerUsername").val().trim();
        var password = $("#registerPassword").val().trim();
        var role = $("#registerRole").val();

        // 验证输入
        if (!username) {
            $("#registerUsernameError").text("请输入用户名").show();
            return;
        }
        if (!password) {
            $("#registerPasswordError").text("请输入密码").show();
            return;
        }
        if (password.length < 6) {
            $("#registerPasswordError").text("密码长度不能少于6位").show();
            return;
        }

        $.ajax({
            url: "register",
            type: "POST",
            data: { username: username, password: password,role: role },
            dataType: "json",
            success: function (response) {
                if (response.success) {
                    alert(response.message);
                    hideModal("registerModal");
                    showLoginModal();
                } else {
                    $("#registerUsernameError").text(response.message).show();
                }
            },
            error: function () {
                $("#registerUsernameError").text("注册失败，请稍后重试").show();
            }
        });
    }

    // 提交举报
    function submitReport() {
        var fileId = $("#reportFileId").val();
        var reason = $("#reportReason").val().trim();

        if (!reason) {
            $("#reportReasonError").text("请输入举报原因").show();
            return;
        }

        $.post("reportFile",
            { fileId: fileId, reason: reason },
            function (response) {
                if (response.status === "success") {
                    alert("举报已提交，管理员会尽快处理");
                    hideModal("reportModal");
                } else {
                    $("#reportReasonError").text(response.message).show();
                }
            }, "json");
    }

</script>

<!-- Font Awesome 图标库 -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</body>
</html>