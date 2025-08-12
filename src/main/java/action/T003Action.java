package action;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Constant;
import common.Helper;
import dto.T001Dto;
import dto.T002Dto;
import service.T003Service;

/**
 * Servlet implementation class T003Action.
 * 
 * <p>This servlet handles the Add/Edit operations for customer data (T002Dto).</p>
 * It supports:
 * <ul>
 *   <li>Loading customer information for editing (GET request)</li>
 *   <li>Validating and saving customer data (POST request)</li>
 * </ul>
 * 
 * Mapped to URL: <code>/T003</code>
 * 
 * @author 
 */
@WebServlet("/T003")
public class T003Action extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** Service instance for customer operations. */
    private final T003Service t003Service = T003Service.getInstance();

    /**
     * Handles GET requests.
     * 
     * <p>If the user is not logged in, they will be redirected to the login page.</p>
     * <p>If a customerId is provided, the corresponding customer is retrieved and
     * displayed in edit mode; otherwise, the form is shown in add mode.</p>
     *
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if an error occurs while forwarding to the JSP
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (!Helper.isLogin(request)) {
            response.sendRedirect(request.getContextPath() + "/T001");
            return;
        }

        Integer customerId = parseCustomerId(request.getParameter("customerId"));
        if (customerId != null) {
            T002Dto customer = t003Service.getCustomerById(customerId);
            if (customer != null) {
                request.setAttribute("customer", customer);
                request.setAttribute("mode", "EDIT");
            } else {
                request.setAttribute("mode", "ADD");
            }
        } else {
            request.setAttribute("mode", "ADD");
        }
        request.getRequestDispatcher(Constant.T003).forward(request, response);
    }

    /**
     * Handles POST requests.
     * 
     * <p>Reads the form data, validates it, and either inserts or updates
     * the customer record depending on the mode (ADD/EDIT).</p>
     * 
     * @param request  the HTTP request
     * @param response the HTTP response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        Integer customerId = parseCustomerId(request.getParameter("txtCustomerId"));
        String customerName = request.getParameter("txtCustomerName");
        String sex = request.getParameter("cboSex");
        String birthday = request.getParameter("txtBirthday");
        String email = request.getParameter("txtEmail");
        String address = request.getParameter("txtAddress");

        String mode = request.getParameter("mode");
        System.out.println("mode" + mode);
        // Prepare DTO to retain user input in case of validation errors
        T002Dto customer = new T002Dto();
        customer.setCustomerID(customerId);
        customer.setCustomerName(customerName);
        customer.setSex(sex);
        customer.setBirthday(birthday);
        customer.setEmail(email);
        customer.setAddress(address);

        // Validate birthday
        String birthdayError = validateBirthday(birthday);
        if (birthdayError != null) {
            forwardWithError(request, response, birthdayError, mode, customer);
            return;
        }

        // Validate email
        String emailError = validateEmail(email);
        if (emailError != null) {
            forwardWithError(request, response, emailError, mode, customer);
            return;
        }

        // Get logged-in user's ID
        HttpSession session = request.getSession(false);
        T001Dto loggedInUser = (T001Dto) session.getAttribute("user");
        Integer psnCd = (loggedInUser != null) ? loggedInUser.getPsnCd() : null;
        try {
            if ("ADD".equals(mode)) {
                t003Service.insertCustomer(customer, psnCd);
            } else {
                t003Service.updateCustomer(customer, psnCd);
            }

            // Nếu chạy tới đây là không có lỗi → redirect
			response.sendRedirect(request.getContextPath() + "/T002");

        } catch (SQLException e) {
            throw new ServletException("Database error occurred while saving customer.", e);
        } catch (Exception e) {
            // Bắt các lỗi khác (nếu cần)
            forwardWithError(request, response, "An error occurred while saving data.", mode, customer);
        }

    }

    /**
     * Parses a string into an Integer customer ID.
     * 
     * @param customerIdParam the string representation of the customer ID
     * @return the parsed Integer, or null if the string is empty or invalid
     */
    private Integer parseCustomerId(String customerIdParam) {
        if (customerIdParam != null && !customerIdParam.trim().isEmpty()) {
            try {
                return Integer.parseInt(customerIdParam.trim());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Forwards the request back to the JSP with an error message and pre-filled form data.
     * 
     * @param request       the HTTP request
     * @param response      the HTTP response
     * @param errorMessage  the error message to display
     * @param mode          the current form mode ("ADD" or "EDIT")
     * @param customer      the customer DTO containing user input
     * @throws ServletException if an error occurs while forwarding
     * @throws IOException      if an I/O error occurs
     */
    private void forwardWithError(HttpServletRequest request, HttpServletResponse response,
                                   String errorMessage, String mode, T002Dto customer)
            throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("mode", mode);
        request.setAttribute("customer", customer);
        request.getRequestDispatcher(Constant.T003).forward(request, response);
    }

    /**
     * Validates the birthday string format (yyyy/MM/dd).
     * 
     * @param birthday the birthday string
     * @return an error message if invalid, or null if valid
     */
    private String validateBirthday(String birthday) {
        if (birthday == null || birthday.trim().isEmpty()) {
            return "Invalid birthday.";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            LocalDate.parse(birthday.trim(), formatter);
        } catch (DateTimeParseException e) {
            return "Invalid birthday.";
        }
        return null;
    }

    /**
     * Validates the email format.
     * 
     * @param email the email string
     * @return an error message if invalid, or null if valid
     */
    private String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "Invalid email address.";
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            return "Invalid email address.";
        }
        return null;
    }
}
