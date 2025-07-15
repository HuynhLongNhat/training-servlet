package training.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/T001")
public class T001 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển tiếp yêu cầu đến file T001.jsp
    	RequestDispatcher dispatcher = request.getRequestDispatcher("WebContent/WEB-INF/jsp/T001.jsp");
    	dispatcher.forward(request, response);
         }
}
