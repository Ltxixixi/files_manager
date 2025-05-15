<!-- upload.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
<h2>文件上传</h2>
<form id="uploadForm" enctype="multipart/form-data">
    <div>
        <label>选择文件：</label>
        <input type="file" name="file" id="fileInput" required>
    </div>
    <div>
        <label>公开文件：</label>
        <input type="radio" name="isPublic" value="Y" checked> 是
        <input type="radio" name="isPublic" value="N"> 否
    </div>
    <button type="button" id="uploadBtn">上传</button>
</form>
<div id="uploadResult"></div>

<script>
    $(document).ready(function() {
        $("#uploadBtn").click(function() {
            var formData = new FormData($("#uploadForm")[0]);

            $.ajax({
                url: "${pageContext.request.contextPath}/fileUpload",
                type: "POST",
                data: formData,
                processData: false,
                contentType: false,
                success: function(response) {
                    var result = JSON.parse(response);
                    if (result.status === "success") {
                        $("#uploadResult").html("<p style='color:green'>" + result.message + "</p>");
                        $("#fileInput").val("");
                    } else {
                        $("#uploadResult").html("<p style='color:red'>" + result.message + "</p>");
                    }
                },
                error: function() {
                    $("#uploadResult").html("<p style='color:red'>上传失败，请重试</p>");
                }
            });
        });
    });
</script>
</body>
</html>