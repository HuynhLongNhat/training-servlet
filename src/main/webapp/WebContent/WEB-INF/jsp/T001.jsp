<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String errorMessage = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html>
<head>
    <title>Login Page</title>
     <link rel="stylesheet" type="text/css" 
     href="${pageContext.request.contextPath}/WebContent/css/T001.css">
</head>
<body>
<div class="login-container">
    <h2>Đăng nhập</h2>

    <% if (errorMessage != null) { %>
        <div class="error-message"><%= errorMessage %></div>
    <% } %>

    <form action="LoginServlet" method="post">
        <label for="userId">Tên đăng nhập:</label>
        <input type="text" id="userId" name="userId" required>

        <label for="password">Mật khẩu:</label>
        <input type="password" id="password" name="password" required>

        <div class="button-group">
            <button type="submit">Đăng nhập</button>
            <button type="reset">Reset</button>
        </div>
    </form>
</div>
</body>
</html>
