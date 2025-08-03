package action;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.RequestDispatcher;
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
 * T003Action handling request adding and editing customer information.
 * Supports both GET (load form) and POST (submit form) operations.
 * 
 * @author YourName
 * @version 1.0
 * @since 2025-07-21
 */
@WebServlet("/T003")
public class T003Action extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** Service instance for handling customer-related operations */
    private final T003Service t003Service = T003Service.getInstance();

    /**
     * Handles GET requests to display the customer form.
     * Loads existing customer data for editing if {@code customerId} is provided;
     * otherwise, prepares the form for adding a new customer.
     *
     * @param request  HTTP request object
     * @param response HTTP response object
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException      if I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	// Validate session
		if (!Helper.isLogin(request)) {
			response.sendRedirect(request.getContextPath() + "/T001");
			return;
		}
        // Parse customerId from request parameter
        Integer customerId = parseCustomerId(request.getParameter("customerId"));

        if (customerId != null) {
            // Load customer data for editing
            T002Dto customer = t003Service.getCustomerById(customerId);
            if (customer != null) {
                request.setAttribute("customer", customer);
                request.setAttribute("mode", "EDIT");
            } else {
                // If customer not found, fallback to ADD mode
                request.setAttribute("mode", "ADD");
            }
        } else {
            // No customerId provided, prepare for adding new customer
            request.setAttribute("mode", "ADD");
        }

        // Forward to customer form JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.T003);
        dispatcher.forward(request, response);
    }

    /**
     * Handles POST requests to save customer data.
     * Validates form inputs, maps them to DTO, and delegates saving to the service layer.
     *
     * @param request  HTTP request object
     * @param response HTTP response object
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException      if I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set request encoding to UTF-8
        request.setCharacterEncoding("UTF-8");

        // Retrieve form parameters
        Integer customerId = parseCustomerId(request.getParameter("txtCustomerId"));
        String customerName = request.getParameter("txtCustomerName");
        String sex = request.getParameter("cboSex");
        String birthday = request.getParameter("txtBirthday");
        String email = request.getParameter("txtEmail");
        String address = request.getParameter("txtAddress");

        // Determine mode based on presence of customerId
        String mode = (customerId == null) ? "ADD" : "EDIT";

        // Validate birthday input
        String birthdayError = validateBirthday(birthday);
        if (birthdayError != null) {
            // Forward back to form with error message
            forwardWithError(request, response, birthdayError, mode);
            return;
        }

        // Validate email input
        String emailError = validateEmail(email);
        if (emailError != null) {
            // Forward back to form with error message
            forwardWithError(request, response, emailError, mode);
            return;
        }

        // Map form data to DTO
        T002Dto customer = new T002Dto();
        customer.setCustomerID(customerId);
        customer.setCustomerName(customerName);
        customer.setSex(sex);
        customer.setBirthday(birthday);
        customer.setEmail(email);
        customer.setAddress(address);

        // Get logged-in user info for audit tracking
        HttpSession session = request.getSession(false);
        T001Dto loggedInUser = (T001Dto) session.getAttribute("user");
        Integer psnCd = (loggedInUser != null) ? loggedInUser.getPsnCd() : null;

        try {
            // Save customer data via service
            boolean success = "ADD".equals(mode)
                    ? t003Service.insertCustomer(customer, psnCd)
                    : t003Service.updateCustomer(customer, psnCd);

            if (success) {
                // Redirect to customer list screen after successful save
                response.sendRedirect(request.getContextPath() + "/T002");
            } else {
                // Forward back with general error message
                forwardWithError(request, response, "Đã xảy ra lỗi khi lưu dữ liệu.", mode);
            }
        } catch (SQLException e) {
            // Handle database-related errors
            throw new ServletException("Database error occurred while saving customer.", e);
        }
    }

    /**
     * Parses the customerId parameter safely.
     *
     * @param customerIdParam raw customerId string from request
     * @return parsed Integer value or {@code null} if invalid
     */
    private Integer parseCustomerId(String customerIdParam) {
        if (customerIdParam != null && !customerIdParam.trim().isEmpty()) {
            try {
                return Integer.parseInt(customerIdParam.trim());
            } catch (NumberFormatException e) {
                // Log and ignore invalid format
            }
        }
        return null;
    }

    /**
     * Forwards the request back to the customer form with an error message.
     *
     * @param request      HTTP request object
     * @param response     HTTP response object
     * @param errorMessage error message to display
     * @param mode         current form mode ("ADD" or "EDIT")
     * @throws ServletException if servlet-specific error occurs
     * @throws IOException      if I/O error occurs
     */
    private void forwardWithError(HttpServletRequest request, HttpServletResponse response, String errorMessage,
            String mode) throws ServletException, IOException {
        request.setAttribute("errorMessage", errorMessage);
        request.setAttribute("mode", mode);
        request.getRequestDispatcher(Constant.T003).forward(request, response);
    }

    /**
     * Validates a single birthday input.
     *
     * @param birthday date string in {@code yyyy/MM/dd} format
     * @return error message if invalid, {@code null} if valid
     */
    private String validateBirthday(String birthday) {
        if (birthday == null || birthday.trim().isEmpty()) {
            return "誕生日が不正です。";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            LocalDate.parse(birthday.trim(), formatter);
        } catch (DateTimeParseException e) {
            return "誕生日が不正です。";
        }
        return null;
    }

    /**
     * Validates email format.
     *
     * @param email email string to validate
     * @return error message if invalid, {@code null} if valid
     */
    private String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return "メールアドレスが不正です。";
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            return "メールアドレスが不正です。";
        }
        return null;
    }
}
