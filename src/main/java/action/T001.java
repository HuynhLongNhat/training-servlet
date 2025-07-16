package action;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import dao.T001Dao;
import dto.T001Dto;
import utils.DBUtils;

@WebServlet("/T001")
public class T001 extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispathcher = request.getRequestDispatcher("WebContent/WEB-INF/jsp/T001.jsp");
		dispathcher.forward(request, response);
		
//		try (Connection conn = DBUtils.getConnection()) {
//		    System.out.println("✅ Kết nối thành công đến DB.");
//		} catch (Exception e) {
//		    e.printStackTrace();
//		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("txtUserId");
		String password = request.getParameter("txtPassword");
		T001Dto t001Dto = new T001Dto(userId, password);
		T001Dao t001Dao = new T001Dao();
		String errorMessage = t001Dao.checkLogin(t001Dto);
         
		System.out.println("errormessage :" + errorMessage);
		if (errorMessage == null) {
			// chuyên hướng đến home
			response.sendRedirect("home.jsp");
		}

		else {
			// Đăng nhập thất bại -> Quay về trang login và hiển thị lỗi
			request.setAttribute("errorMessage", errorMessage);
			RequestDispatcher dispatcher = request.getRequestDispatcher("WebContent/WEB-INF/jsp/T001.jsp");
			dispatcher.forward(request, response);
		}
	}
}
