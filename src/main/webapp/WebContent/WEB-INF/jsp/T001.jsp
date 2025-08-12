<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="dto.T001Dto"%>
<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/WebContent/css/T001.css">
</head>
<body>
    <%
    String errorMessage = (String) request.getAttribute("errorMessage");
    T001Dto t001Dto = (T001Dto) request.getAttribute("t001Dto");
    %>
	<%@ include file="Header.jsp"%>
	<nav class="nav-login">Login</nav>
	<div class="login-container">
		<h2 class="title">LOGIN</h2>
		<p class="error-message" id="lblErrorMessage">
			<%= errorMessage != null ? errorMessage : ""%>
		</p>

		<form action="T001" id="loginForm" method="post">
			<div class="input-group">
				<label for="txtuserID">User Id :</label> 
				<input type="text"
					   id="txtuserID" 
					   value="<%= t001Dto != null ? t001Dto.getUserId() : "" %>"
					   name="userID">
			</div>

			<div class="input-group">
				<label for="txtpassword">Password :</label> 
				<input type="password"
					   id="txtpassword" 
					   value="<%= t001Dto != null ? t001Dto.getPassword() : "" %>"
					   name="password">
			</div>

			<div class="button-group">
				<button type="submit" name="btnLogin">Login</button>
				<button type="button" name="btnClear" onclick="clearFormLogin()">Clear</button>
			</div>
		</form>
	</div>
	<%@ include file="Footer.jsp"%>
	<script src="${pageContext.request.contextPath}/WebContent/js/script.js?<%=System.currentTimeMillis()%>"></script>

</body>
</html>
