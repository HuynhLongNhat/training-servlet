<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
String errorMessage = (String) request.getAttribute("errorMessage");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Page</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/WebContent/css/T001.css">
</head>
<body>
	<div class="container">
		<div class="header">
			<%@ include file="Header.jsp"%>
		</div>
		<div class="login-form">
			<h4>Login Message</h4>

			<%
			if (errorMessage != null) {
			%>
			<div class="error-message"><%=errorMessage%></div>
			<%
			}
			%>

			<form action="T001" method="post">
				<div class="form-group">
					<label for="txtUserId">User Id:</label> <input type="text"
						id="txtUserId" name="txtUserId" placeholder="abc12345" >
				</div>

				<div class="form-group">
					<label for="txtPassword">Password:</label> <input type="password"
						id="txtPassword" name="txtPassword" placeholder="******" >
				</div>

				<div class="form-actions">
					<button type="submit" name="btnLogin" class="btn-login">Login</button>
					<button type="reset" name="btnClear" class="btn-clear">Clear</button>
				</div>
			</form>
		</div>
		<div class="footer"><%@ include file="Footer.jsp"%></div>
	</div>



</body>
</html>
