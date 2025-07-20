package action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Constant;
import dto.T001Dto;
import service.T001Service;

/**
 * T001Action
 *
 * Chức năng: Xử lý yêu cầu đăng nhập của người dùng.
 * Chuyển hướng đến màn hình T002 khi đăng nhập thành công.
 *
 * @author YourName
 * @since 2025-07-20
 */
@WebServlet("/T001")
public class T001Action extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** Service xử lý nghiệp vụ đăng nhập */
	private final T001Service t001Service = new T001Service();

	/**
	 * Xử lý yêu cầu HTTP GET. Hiển thị màn hình đăng nhập.
	 *
	 * @param request  đối tượng HttpServletRequest
	 * @param response đối tượng HttpServletResponse
	 * @throws ServletException nếu xảy ra lỗi servlet
	 * @throws IOException      nếu xảy ra lỗi nhập/xuất
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Chuyển tiếp đến trang đăng nhập
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(Constant.T001);
		requestDispatcher.forward(request, response);
	}

	/**
	 * Xử lý yêu cầu HTTP POST. Kiểm tra dữ liệu người dùng nhập và xác thực thông
	 * tin đăng nhập.
	 *
	 * @param request  đối tượng HttpServletRequest
	 * @param response đối tượng HttpServletResponse
	 * @throws ServletException nếu xảy ra lỗi servlet
	 * @throws IOException      nếu xảy ra lỗi nhập/xuất
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Lấy dữ liệu từ form
		String userId = request.getParameter("txtuserID");
		String password = request.getParameter("txtpassword");

		// (1) Kiểm tra User ID có rỗng không
		if (userId == null || userId.trim().isEmpty()) {
			request.setAttribute("errorMessage", "Vui lòng nhập User ID.");
			request.getRequestDispatcher(Constant.T001).forward(request, response);
			return;
		}

		// (2) Kiểm tra Password có rỗng không
		if (password == null || password.trim().isEmpty()) {
			request.setAttribute("errorMessage", "Vui lòng nhập Password.");
			request.getRequestDispatcher(Constant.T001).forward(request, response);
			return;
		}

		// (3) Xác thực thông tin đăng nhập thông qua Service
		T001Dto t001Dto = new T001Dto();
		t001Dto.setUserId(userId);
		t001Dto.setPassword(password);
		boolean isValidUser = t001Service.login(t001Dto);

		if (!isValidUser) {
			// (3.1) Đăng nhập thất bại: quay lại màn hình login với thông báo lỗi
			request.setAttribute("errorMessage", "User ID hoặc Password không đúng.");
			request.getRequestDispatcher(Constant.T001).forward(request, response);
		} else {
			// (3.2) Đăng nhập thành công: điều hướng sang màn hình T002
			response.sendRedirect("/Servlet-blank/T002");
		}
	}
}
