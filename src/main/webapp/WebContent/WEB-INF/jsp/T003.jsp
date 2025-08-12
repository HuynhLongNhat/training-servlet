<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="dto.T002Dto" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Information</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/WebContent/css/T003.css?<%=System.currentTimeMillis()%>">
</head>
<body>
<%@ include file="Header.jsp" %>
<%
    T002Dto customer = (T002Dto) request.getAttribute("customer");
    String mode = (String) request.getAttribute("mode");
%>
<div class="container">
    <div class="nav-group">
        <span>Login</span> <span>&gt;</span> <span>Search Customer</span> <span>&gt;</span>
        <span><%= "EDIT".equalsIgnoreCase(mode) ? "Edit Customer info" : "Add Customer" %></span>
    </div>
    <div class="welcome">
        <span>Welcome <label id="lblUserName">${user.userName}</label></span>
        <a id="llblLogout" href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>

    <div class="blue-bar">
        <label id="llblScreenName"><%= "EDIT".equalsIgnoreCase(mode) ? "EDIT" : "ADD" %></label>
    </div>

    <div class="form-container">
        <form action="T003" method="post" id="customerForm">
        	 <input type="hidden" name="mode" value="<%= mode != null ? mode : "ADD" %>" />
            <label class="error-message" id="lblErrorMessage">
                <%= request.getAttribute("errorMessage") != null ? request.getAttribute("errorMessage") : "" %>
            </label>

            <div class="form-group">
                <label>Customer Id</label>
                <label id="lblCustomerID"><%= customer != null && customer.getCustomerID() != null ? customer.getCustomerID() : "" %></label>
                <input type="hidden" id="txtCustomerId" name="txtCustomerId"
                       value="<%= customer != null && customer.getCustomerID() != null ? customer.getCustomerID() : "" %>" />
            </div>

            <div class="form-group">
                <label>Customer Name</label>
                <input type="text" id="txtCustomerName" name="txtCustomerName"
                       value="<%= customer != null ? customer.getCustomerName() : "" %>">
            </div>

            <div class="form-group">
                <label>Sex</label>
                <select id="cboSex" name="cboSex">
                    <option value=""></option>
                    <option value="0" <%= (customer != null && "0".equals(customer.getSex())) ? "selected" : "" %>>Male</option>
                    <option value="1" <%= (customer != null && "1".equals(customer.getSex())) ? "selected" : "" %>>Female</option>
                </select>
            </div>

            <div class="form-group">
                <label>Birthday</label>
                <input type="text" id="txtBirthday" name="txtBirthday"
                       value="<%= customer != null ? customer.getBirthday() : "" %>">
            </div>

            <div class="form-group">
                <label>Email</label>
                <input type="text" id="txtEmail" name="txtEmail"
                       value="<%= customer != null ? customer.getEmail() : "" %>">
            </div>

            <div class="form-group">
                <label>Address</label>
                <textarea id="txtAddress" name="txtAddress" rows="3"><%= customer != null ? customer.getAddress() : "" %></textarea>
            </div>

            <div class="button-group">
                <button type="submit" id="btnSave">Save</button>
                <button type="button" name="btnClear" onclick="clearFormEdit()">Clear</button>
            </div>
        </form>
    </div>
</div>
<%@ include file="Footer.jsp" %>
<script src="${pageContext.request.contextPath}/WebContent/js/script.js?<%=System.currentTimeMillis()%>"></script>
<script>
    function resetCustomerForm() {
        const form = document.getElementById('customerForm');
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
