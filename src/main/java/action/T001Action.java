package action;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Constant;
import dto.T001Dto;
import service.T001Service;

/**
 * Servlet controller responsible for handling user login requests.
 *
 * <p>
 * This servlet displays the login screen via {@link #doGet} and processes login
 * submissions via {@link #doPost}. It validates user input, authenticates
 * credentials, and manages user sessions upon successful login.
 * </p>
 *
 * <p>
 * On successful authentication, users are redirected to screen {@code T002}. On
 * failure, the login page is redisplayed with an appropriate error message.
 * </p>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-07-21
 */
@WebServlet("/T001")
public class T001Action extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/** Singleton service instance for handling login business logic */
	private final T001Service t001Service = T001Service.getInstance();

	/**
	 * Handles GET requests.
	 * <ul>
	 * <li>If the user session exists, redirects to T002.</li>
	 * <li>If no session, forwards to the login page.</li>
	 * </ul>
	 *
	 * @param request  the {@link HttpServletRequest}s
	 * @param response the {@link HttpServletResponse} object
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false); // không tạo session mới
		if (session != null && session.getAttribute("user") != null) {
			// User đã đăng nhập, chuyển hướng tới T002
			response.sendRedirect(request.getContextPath() + "/T002");
		} else {
			// Chưa đăng nhập, hiển thị màn hình login
			request.getRequestDispatcher(Constant.T001).forward(request, response);
		}
	}

	/**
	 * Utility method to check if a string is null or empty after trimming.
	 *
	 * @param value the string to check
	 * @return {@code true} if the string is null or empty, {@code false} otherwise
	 */
	private boolean isEmpty(String value) {
		return value == null || value.trim().isEmpty();
	}

	/**
	 * Validates the login form fields.
	 *
	 * @param t001Dto the {@link T001Dto} containing user ID and password
	 * @return an error message if validation fails, or {@code null} if valid
	 */
	private String validateForm(T001Dto t001Dto) {
		if (isEmpty(t001Dto.getUserId())) {
			return "Please enter User ID.";
		}
		if (isEmpty(t001Dto.getPassword())) {
			return "Please enter Password.";
		}
		return null;
	}

	/**
	 * Handles POST requests for user login.
	 * <ul>
	 * <li>Validates input fields</li>
	 * <li>Authenticates user credentials via {@link T001Service}</li>
	 * <li>Creates a session and redirects to {@code T002} on success</li>
	 * <li>Forwards back to login screen with an error message on failure</li>
	 * </ul>
	 *
	 * @param request  the {@link HttpServletRequest} containing login form data
	 * @param response the {@link HttpServletResponse} for sending the result
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userId = request.getParameter("txtuserID");
		String password = request.getParameter("txtpassword");

		T001Dto loginDto = new T001Dto(userId, password);

		// Validate input fields
		String errorMessage = validateForm(loginDto);
		if (errorMessage != null) {
			request.setAttribute("errorMessage", errorMessage);
			request.getRequestDispatcher(Constant.T001).forward(request, response);
			return;
		}

		try {
			// Authenticate user credentials
			T001Dto user = t001Service.getUserLogin(loginDto);

			if (user != null) {
				// Successful login: create session and redirect
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect(request.getContextPath() + "/T002");
			} else {
				// Authentication failed: redisplay login with error message
				request.setAttribute("errorMessage", "User does not exist.");
				request.getRequestDispatcher(Constant.T001).forward(request, response);
			}
		} catch (SQLException e) {
			throw new ServletException("Database error occurred during login.", e);
		}
	}
}
