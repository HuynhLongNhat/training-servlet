<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/WebContent/css/T002.css">
</head>
<style>
</style>
<body>
<body>
	<%@ include file="Header.jsp"%>
	<div class="container">

		<div class="nav-group">
			<span>Login</span> <span>></span> <span>Search Customer</span>
		</div>
		<div class="welcome">
			<span>Welcome ABC</span> <a href="#">Log Out</a>
		</div>

		<div class="blue-bar"></div>

		<div class="search-form">
			<label>Customer Name <input type="text" name="customerName"></label>
			<label>Sex <select name="sex">
					<option value="">All</option>
					<option value="Male">Male</option>
					<option value="Female">Female</option>
			</select>
			</label> <label>Birthday <input type="date" name="birthdayFrom">
				- <input type="date" name="birthdayTo"></label>
			<button type="submit">Search</button>
		</div>
		<div class="btn-pagination">
			<div class="previous">
				<button>&lt;&lt;</button>
				<button>&lt;</button>
				<span>Previous</span>
			</div>

			<div class="next">
				<span>Next</span>
				<button>&gt;&gt;</button>
				<button>&gt;</button>
			</div>

		</div>
		<table class="customer-table">
			<thead>
				<tr>
					<th class="select">☐</th>
					<th class="customer-id">Customer ID</th>
					<th class="customer-name">Customer Name</th>
					<th class="sex">Sex</th>
					<th class="birthday">Birthday</th>
					<th class="address">Address</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>
				<tr>
					<td><input type="checkbox"></td>
					<td class="id-data">11010001</td>
					<td>Nguyen Van A</td>
					<td>Male</td>
					<td>1982/01/01</td>
					<td>123 No Trang Long</td>
				</tr>

			</tbody>
		</table>
		<div class="actions">
			<button class="btn-add">Add New</button>
			<button class="btn-delete">Delete</button>
		</div>
	</div>
	<%@ include file="Footer.jsp"%>
</body>

</html>