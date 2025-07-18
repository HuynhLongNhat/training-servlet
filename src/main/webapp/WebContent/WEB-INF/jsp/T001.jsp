<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>
<style>
body {
	background: linear-gradient(to bottom right, #e0f7fa, #b2ebf2);
	font-family: Arial, sans-serif;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
}

.login-container {
	width: 400px;
}

.title {
	text-align: center;
	color: #1976d2;
	font-size: 30px;
	margin-bottom: 5px;
}

.error-message {
	color: red;
	text-align: center;
	font-size: 16px;
}

.input-group {
	display: flex;
	align-items: center;
	margin-bottom: 15px;
}

.input-group label {
	width: 100px;
}

.input-group input {
	flex: 1;
	max-width: 200px;
	padding-bottom: 2px;
	padding-top: 4px;
	border: 1px solid red;
	border-left: 2px solid #000;
	border-top: 2px solid #000;
	outline: none;
	border: 1px solid #000;
}

#txtpassword {
	font-size: 13px;
}

.button-group {
	display: flex;
	justify-content: space-around;
	margin-top: 30px;
}

.button-group button {
	cursor: pointer;
	width: 80px;
	padding: 4px 20px;
	border: 1px solid #000;
	border-bottom: 1px solid #000;
	border-right: 2px solid #000;
	border-bottom: 2px solid #000
}
</style>

</head>
<body>
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
	<script type="text/javascript">
		function resetFormFields() {
			const form = document.getElementById('loginForm');
			if (form) {
				form.reset();
			}
			const errorMessage = document.getElementById('lblErrorMessage');
			if (errorMessage) {
				errorMessage.textContent = '';
			}

		}
	</script>


</body>
</html>
