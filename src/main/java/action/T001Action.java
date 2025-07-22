package action;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
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
 * Servlet responsible for handling user login requests.
 *
 * <p>
 * This servlet displays the login page via
 * {@link #doGet(HttpServletRequest, HttpServletResponse)} and processes login
 * submissions via {@link #doPost(HttpServletRequest, HttpServletResponse)}.
 * Successful login redirects the user to screen {@code T002}, while failed
 * login redisplays the login page with an error message.
 * </p>
 *
 * @author YourName
 * @version 1.1
 * @since 2025-07-21
 */
@WebServlet("/T001")
public class T001Action extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/** Singleton service instance for handling login business logic */
	private final T001Service t001Service = T001Service.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.T001);
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Retrieve form data
		String userId = request.getParameter("txtuserID");
		String password = request.getParameter("txtpassword");

		// (1) Validate User ID
		if (userId == null || userId.trim().isEmpty()) {
			request.setAttribute("errorMessage", "Please enter User ID.");
			request.getRequestDispatcher(Constant.T001).forward(request, response);
			return;
		}

		// (2) Validate Password
		if (password == null || password.trim().isEmpty()) {
			request.setAttribute("errorMessage", "Please enter Password.");
			request.getRequestDispatcher(Constant.T001).forward(request, response);
			return;
		}

		// (3) Authenticate credentials using Service
		T001Dto t001Dto = new T001Dto(userId, password);
		int countUser;
		try {
			countUser = t001Service.login(t001Dto);
		} catch (SQLException e) {
			throw new ServletException("Database error occurred during login.", e);
		}

		// (4) Handle authentication result
		if (countUser == 1) {
			String userName;
			try {
				userName = t001Service.getUserName(userId);
			} catch (SQLException e) {
				throw new ServletException("Database error occurred while retrieving user name.", e);
			}

			HttpSession session = request.getSession();
			session.setAttribute("userName", userName);

			// Successful login: redirect to screen T002
			response.sendRedirect(request.getContextPath() + "/T002");
		} else {
			// Failed login: redisplay login with error message
			request.setAttribute("errorMessage", "Invalid User ID or Password.");
			request.getRequestDispatcher(Constant.T001).forward(request, response);
		}
	}
}
