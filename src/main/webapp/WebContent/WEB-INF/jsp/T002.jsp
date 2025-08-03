<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="dto.T002Dto"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Search Customer</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/WebContent/css/T002.css">
</head>
<body>
	<%@ include file="Header.jsp"%>

	<%
    // Lấy dữ liệu từ request
    List<T002Dto> customers = (List<T002Dto>) request.getAttribute("customers");
    int currentPage = request.getAttribute("currentPage") != null ? (Integer) request.getAttribute("currentPage") : 1;
    int totalPages = request.getAttribute("totalPages") != null ? (Integer) request.getAttribute("totalPages") : 1;

    boolean disableFirst = request.getAttribute("disableFirst") != null ? (Boolean) request.getAttribute("disableFirst") : true;
    boolean disablePrevious = request.getAttribute("disablePrevious") != null ? (Boolean) request.getAttribute("disablePrevious") : true;
    boolean disableNext = request.getAttribute("disableNext") != null ? (Boolean) request.getAttribute("disableNext") : true;
    boolean disableLast = request.getAttribute("disableLast") != null ? (Boolean) request.getAttribute("disableLast") : true;

    boolean disableDelete = (customers == null || customers.isEmpty());

    String errorMessage = (String) request.getAttribute("errorMessage");

    String searchUserName = (String) request.getAttribute("searchUserName");
    String searchSex = (String) request.getAttribute("searchSex");
    String searchBirthdayFrom = (String) request.getAttribute("searchBirthdayFrom");
    String searchBirthdayTo = (String) request.getAttribute("searchBirthdayTo");
%>

	<%!
    // Hàm sinh các input hidden cho phân trang
    public String renderHiddenParams(String searchUserName, String searchSex, String searchBirthdayFrom, String searchBirthdayTo) {
        return "<input type='hidden' name='actionType' value='search'>" +
               "<input type='hidden' name='lblUserName' value='" + (searchUserName != null ? searchUserName : "") + "'>" +
               "<input type='hidden' name='cboSex' value='" + (searchSex != null ? searchSex : "") + "'>" +
               "<input type='hidden' name='txtBirthdayForm' value='" + (searchBirthdayFrom != null ? searchBirthdayFrom : "") + "'>" +
               "<input type='hidden' name='txtBirthdayTo' value='" + (searchBirthdayTo != null ? searchBirthdayTo : "") + "'>";
    }
%>

	<div class="container">
		<div class="nav-group">
			<span>Login</span> <span>&gt;</span> <span>Search Customer</span>
		</div>
		<div class="welcome">
			<span>Welcome <label>${user.userName}</label></span> <a
				href="${pageContext.request.contextPath}/logout">Logout</a>
		</div>

		<div class="blue-bar"></div>

		<!-- Search form -->
		<form action="T002" class="search-form" method="post">
			<input type="hidden" name="actionType" value="search"> <label>Customer
				Name <input type="text" id="lblUserName" name="lblUserName"
				value="<%=searchUserName != null ? searchUserName : ""%>">
			</label> <label>Sex <select id="cboSex" name="cboSex">
					<option value=""></option>
					<option value="0" <%= "0".equals(searchSex) ? "selected" : "" %>>Male</option>
					<option value="1" <%= "1".equals(searchSex) ? "selected" : "" %>>Female</option>
			</select>
			</label> <label>Birthday <input type="text" id="txtBirthdayForm"
				name="txtBirthdayForm"
				value="<%=searchBirthdayFrom != null ? searchBirthdayFrom : ""%>">
				- <input type="text" id="txtBirthdayTo" name="txtBirthdayTo"
				value="<%=searchBirthdayTo != null ? searchBirthdayTo : ""%>">
			</label>
			<button type="submit">Search</button>
		</form>

		<!-- Pagination -->
		<%
        int[] pages = {1, currentPage - 1, currentPage + 1, totalPages};
        String[] labels = {"&lt;&lt;", "&lt;", "&gt;", "&gt;&gt;"};
        boolean[] disabled = {disableFirst, disablePrevious, disableNext, disableLast};
    %>
		<div class="btn-pagination">
			<div class="previous">
				<span>Previous</span>
				<% for (int i = 0; i < 2; i++) { %>
				<form action="T002" method="post" style="display: inline;">
					<%= renderHiddenParams(searchUserName, searchSex, searchBirthdayFrom, searchBirthdayTo) %>
					<input type="hidden" name="page" value="<%=pages[i]%>">
					<button type="submit" <%=disabled[i] ? "disabled" : ""%>><%=labels[i]%></button>
				</form>
				<% } %>
			</div>
			<div class="next">
				<span>Next</span>
				<% for (int i = 2; i < 4; i++) { %>
				<form action="T002" method="post" style="display: inline;">
					<%= renderHiddenParams(searchUserName, searchSex, searchBirthdayFrom, searchBirthdayTo) %>
					<input type="hidden" name="page" value="<%=pages[i]%>">
					<button type="submit" <%=disabled[i] ? "disabled" : ""%>><%=labels[i]%></button>
				</form>
				<% } %>
			</div>
		</div>

		<!-- Customer list and Delete form -->
		<form action="T002" method="post">
			<input type="hidden" name="action" value="delete">
			<table class="customer-table">
				<thead>
					<tr>
						<th class="select"><input type="checkbox" id="chkAll"></th>
						<th class="customer-id">Customer ID</th>
						<th class="customer-name">Customer Name</th>
						<th class="sex">Sex</th>
						<th class="birthday">Birthday</th>
						<th class="address">Address</th>
					</tr>
				</thead>
				<tbody>
					<%
                if (customers != null && !customers.isEmpty()) {
                    for (T002Dto customer : customers) {
            %>
					<tr>
						<td><input type="checkbox" class="chkDetail"
							name="customerIds" value="<%=customer.getCustomerID()%>"></td>
						<td class="id-data"><a
							href="T003?customerId=<%=customer.getCustomerID()%>"><%=customer.getCustomerID()%></a>
						</td>
						<td><%=customer.getCustomerName()%></td>
						<td><%=customer.getSex()%></td>
						<td><%=customer.getBirthday()%></td>
						<td><%=customer.getAddress()%></td>
					</tr>
					<%
                    }
                } else {
            %>
					<tr>
						<td colspan="6" style="text-align: center;">Không có khách
							hàng nào.</td>
					</tr>
					<%
                }
            %>
				</tbody>
			</table>

			<div class="actions">
				<button type="button" class="btn-add" onclick="redirectToEditPage()">Add
					New</button>
				<button type="submit" class="btn-delete"
					<%=disableDelete ? "disabled" : ""%>>Delete</button>
			</div>
		</form>
	</div>

	<%@ include file="Footer.jsp"%>

	<script
		src="${pageContext.request.contextPath}/WebContent/js/script.js?<%=System.currentTimeMillis()%>"></script>

	<%
    if (errorMessage != null) {
%>
	<script type="text/javascript">
    showErrorMessage("<%=errorMessage%>");
</script>
	<%
    }
%>

	<script>
    document.addEventListener("DOMContentLoaded", function () {
        const checkAll = document.getElementById('chkAll');
        const checkDetails = document.querySelectorAll('.chkDetail');

        if (checkAll) {
            checkAll.addEventListener('change', function () {
                checkDetails.forEach(chk => chk.checked = checkAll.checked);
            });
        }
    });

    function redirectToEditPage() {
        window.location.href = 'T003';
    }
</script>
</body>
</html>
