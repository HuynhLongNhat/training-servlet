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
    List<T002Dto> customers = (List<T002Dto>) request.getAttribute("customers");
    int currentPage = request.getAttribute("currentPage") != null ? (Integer) request.getAttribute("currentPage") : 1;
    int totalPages = request.getAttribute("totalPages") != null ? (Integer) request.getAttribute("totalPages") : 1;

    boolean disableFirst = request.getAttribute("disableFirst") != null ? (Boolean) request.getAttribute("disableFirst") : true;
    boolean disablePrevious = request.getAttribute("disablePrevious") != null ? (Boolean) request.getAttribute("disablePrevious") : true;
    boolean disableNext = request.getAttribute("disableNext") != null ? (Boolean) request.getAttribute("disableNext") : true;
    boolean disableLast = request.getAttribute("disableLast") != null ? (Boolean) request.getAttribute("disableLast") : true;

    boolean disableDelete = (customers == null || customers.isEmpty());

    String errorMessage = (String) request.getAttribute("errorMessage");
    
    System.out.println("errorMessage :" + errorMessage);
%>

<div class="container">
    <div class="nav-group">
        <span>Login</span> <span>&gt;</span> <span>Search Customer</span>
    </div>
    <div class="welcome">
        <span>Welcome <label>${user.userName}</label></span>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>

    <div class="blue-bar"></div>

    <!-- Search form -->
    <form action="T002" class="search-form" method="post">
        <label>Customer Name <input type="text" id="lblUserName" name="lblUserName"></label>
        <label>Sex
            <select id="cboSex" name="cboSex">
                <option value=""></option>
                <option value="0">Male</option>
                <option value="1">Female</option>
            </select>
        </label>
        <label>Birthday
            <input type="text" id="txtBirthdayForm" name="txtBirthdayForm"> -
            <input type="text" id="txtBirthdayTo" name="txtBirthdayTo">
        </label>
        <button type="submit">Search</button>
    </form>

    <!-- Pagination -->
    <div class="btn-pagination">
        <div class="previous">
            <form action="T002" method="get" style="display:inline;">
                <input type="hidden" name="page" value="1">
                <button type="submit" <%=disableFirst ? "disabled" : ""%>>&lt;&lt;</button>
            </form>
            <form action="T002" method="get" style="display:inline;">
                <input type="hidden" name="page" value="<%=currentPage - 1%>">
                <button type="submit" <%=disablePrevious ? "disabled" : ""%>>&lt;</button>
            </form>
            <span>Previous</span>
        </div>
        <div class="next">
            <span>Next</span>
            <form action="T002" method="get" style="display:inline;">
                <input type="hidden" name="page" value="<%=currentPage + 1%>">
                <button type="submit" <%=disableNext ? "disabled" : ""%>>&gt;</button>
            </form>
            <form action="T002" method="get" style="display:inline;">
                <input type="hidden" name="page" value="<%=totalPages%>">
                <button type="submit" <%=disableLast ? "disabled" : ""%>>&gt;&gt;</button>
            </form>
        </div>
        <div>
            <span>Page <%=currentPage%> of <%=totalPages%></span>
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
                <td><input type="checkbox" class="chkDetail" name="customerIds" value="<%=customer.getCustomerID()%>"></td>
                <td class="id-data">
                    <a href="T003?customerId=<%=customer.getCustomerID()%>"><%=customer.getCustomerID()%></a>
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
                <td colspan="6" style="text-align:center;">Không có khách hàng nào.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>

        <div class="actions">
            <button type="button" class="btn-add" onclick="redirectToEditPage()">Add New</button>
            <button type="submit" class="btn-delete" <%=disableDelete ? "disabled" : ""%>>Delete</button>
        </div>
    </form>
</div>

<%@ include file="Footer.jsp"%>

<script src="${pageContext.request.contextPath}/WebContent/js/script.js?<%=System.currentTimeMillis()%>"></script>

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
