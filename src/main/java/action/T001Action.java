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
import common.Helper;
import dto.T001Dto;
import service.T001Service;

/**
 * T001Action handling user login requests This class processes login form
 * submissions and manages authentication logic.
 */
@WebServlet("/T001")
public class T001Action extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/** Service instance for data access. */
	private final T001Service t001Service = T001Service.getInstance();

	/**
	 * Handles GET requests for the login screen.
	 *
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (Helper.isLogin(request)) {
			// Logged in, redirect to T002
			response.sendRedirect(request.getContextPath() + "/T002");
		} else {
			// Invalid session, display login screen
			forwardToLogin(request, response, null);
		}
	}

	/**
	 * Handles POST requests for user login.
	 *
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 * 
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Get login form inputs
		String userId = request.getParameter("txtuserID");
		String password = request.getParameter("txtpassword");
		// Create DTO to carry login information
		T001Dto loginDto = new T001Dto();
		loginDto.setUserId(userId);
		loginDto.setPassword(password);
		// Validate form input
		String errorMessage = validateForm(loginDto);
		if (errorMessage != null) {
			// Validation failed: redisplay login page with error message
			forwardToLogin(request, response, errorMessage);
			return;
		}
		try {
			// Authenticate user credentials through service
			T001Dto user = t001Service.getUserLogin(loginDto);
			if (user != null) {
				// Successful login: create session and redirect to T002
				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				response.sendRedirect(request.getContextPath() + "/T002");
			} else {
				// Authentication failed: redisplay login page with error message
				forwardToLogin(request, response, "User does not exist.");
			}
		} catch (SQLException e) {
			// Handle database errors during login process
			throw new ServletException("Database error occurred during login.", e);
		}
	}

	/**
	 * Validate login form fields.
	 *
	 * @param t001Dto DTO containing user input
	 * @return error message if validation fails; null if valid
	 */
	private String validateForm(T001Dto t001Dto) {
		// Check if User ID is empty
		if (Helper.isEmpty(t001Dto.getUserId()))
			return "Please enter User ID.";
		// Check if Password is empty
		if (Helper.isEmpty(t001Dto.getPassword()))
			return "Please enter Password.";
		// All fields are valid
		return null;
	}

	/**
	 * Forward to login page with optional error message.
	 *
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @param errorMessage        error message to display on login page
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forwardToLogin(HttpServletRequest request, HttpServletResponse response, String errorMessage)
			throws ServletException, IOException {
		// Set error message if present
		if (errorMessage != null)
			request.setAttribute("errorMessage", errorMessage);
		// Forward to login page
		request.getRequestDispatcher(Constant.T001).forward(request, response);
	}

}
