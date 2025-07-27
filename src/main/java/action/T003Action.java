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

import common.Constant;
import dto.T002Dto;
import service.T003Service;

/**
 * Servlet controller responsible for displaying the customer edit screen.
 *
 * <p>
 * This servlet handles requests to edit a customer's information. It retrieves
 * the customer's data by ID from {@link T003Service} and forwards it to the JSP
 * view for editing.
 * </p>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-07-21
 */
@WebServlet("/T003")
public class T003Action extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/** Service instance for retrieving customer data */
	private final T003Service t003Service = T003Service.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String customerId = request.getParameter("customerId");

		if (customerId != null && !customerId.trim().isEmpty()) {
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

		RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.T003);
		dispatcher.forward(request, response);
	}

	/**
	 * Checks if a string is null or empty after trimming.
	 *
	 * @param value the string to check
	 * @return {@code true} if null or empty, otherwise {@code false}
	 */
	private boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
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
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");

	    String customerId = request.getParameter("txtCustomerId");
	    String customerName = request.getParameter("txtCustomerName");
	    String sex = request.getParameter("cboSex");
	    String birthday = request.getParameter("txtBirthday");
	    String email = request.getParameter("txtEmail");
	    String address = request.getParameter("txtAddress");
	    String mode = request.getParameter("mode"); // "ADD" hoặc "EDIT"

	    // Validate
	    String birthdayError = validateBirthday(birthday);
	    if (birthdayError != null) {
	        request.setAttribute("errorMessage", birthdayError);
	        request.setAttribute("mode", mode);
	        request.getRequestDispatcher(Constant.T003).forward(request, response);
	        return;
	    }

	    String emailError = validateEmail(email);
	    if (emailError != null) {
	        request.setAttribute("errorMessage", emailError);
	        request.setAttribute("mode", mode);
	        request.getRequestDispatcher(Constant.T003).forward(request, response);
	        return;
	    }

	    // Map to DTO
	    T002Dto customer = new T002Dto();
	    customer.setCustomerID(customerId);
	    customer.setCustomerName(customerName);
	    customer.setSex(sex);
	    customer.setBirthday(birthday);
	    customer.setEmail(email);
	    customer.setAddress(address);

	    // Lấy mã user đang đăng nhập
	    String psnCd = (String) request.getSession().getAttribute("psnCd");

	    try {
	        boolean success;
	        if ("ADD".equals(mode)) {
	            success = t003Service.insertCustomer(customer, psnCd);
	        } else {
	            success = t003Service.updateCustomer(customer, psnCd);
	        }

	        if (success) {
	            response.sendRedirect(request.getContextPath() + "/T002"); 
	        } else {
	            request.setAttribute("errorMessage", "Đã xảy ra lỗi khi lưu dữ liệu.");
	            request.setAttribute("mode", mode);
	            request.getRequestDispatcher(Constant.T003).forward(request, response);
	        }
	    } catch (SQLException e) {
	        throw new ServletException("Database error occurred while saving customer.", e);
	    }
	}

}
