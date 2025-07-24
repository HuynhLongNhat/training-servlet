<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/WebContent/css/T001.css">
</head>
<body>

	<%@ include file="Header.jsp"%>
	<nav class="nav-login">Login</nav>
	<div class="login-container">
		<h2 class="title">LOGIN</h2>
		<p class="error-message" id="lblErrorMessage">
			<%=request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : ""%>
		</p>

		<form action="T001" id="loginForm" method="post">
			<div class="input-group">
				<label for="txtuserID">User Id :</label> <input type="text"
					id="txtuserID" name="txtuserID">
			</div>

			<div class="input-group">
				<label for="txtpassword">Password :</label> <input type="password"
					id="txtpassword" name="txtpassword">
			</div>

			<div class="button-group">
				<button type="submit" name="btnLogin">Login</button>
				<button type="button" name="btnClear" onclick="resetFormFields()">Clear</button>
			</div>
		</form>
	</div>
	<%@ include file="Footer.jsp"%>

	<script src="${pageContext.request.contextPath}/WebContent/js/script.js"></script>
</body>
</html>
