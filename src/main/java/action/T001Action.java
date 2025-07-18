package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.T001Service;

@WebServlet("/T001")
public class T001Action extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private T001Service t001Service = new T001Service();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispathcher = request.getRequestDispatcher("WebContent/WEB-INF/jsp/T001.jsp");
		dispathcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("txtuserID");
		String password = request.getParameter("txtpassword");
        
		System.out.println("userID" + userId);
		System.out.println("password" + password);
		if (userId == null || userId.trim().isEmpty()) {
			request.setAttribute("errorMessage", "Vui lòng nhập User ID.");
			request.getRequestDispatcher("WebContent/WEB-INF/jsp/T001.jsp").forward(request, response);
			return;
		}

		if (password == null || password.trim().isEmpty()) {
			request.setAttribute("errorMessage", "Vui lòng nhập Password.");
			request.getRequestDispatcher("WebContent/WEB-INF/jsp/T001.jsp").forward(request, response);
			return;
		}
		
		int isValidUser = t001Service.login(userId, password);
		if (isValidUser != 1) {
			request.setAttribute("errorMessage", "User ID hoặc Password không đúng.");
			request.getRequestDispatcher("WebContent/WEB-INF/jsp/T001.jsp").forward(request, response);

		} else {
			response.sendRedirect("home.jsp"); 
		}
	}
}

