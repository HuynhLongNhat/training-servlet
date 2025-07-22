package action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet responsible for handling user logout functionality.
 *
 * <p>This servlet invalidates the current user session (if it exists) 
 * and redirects the user back to the login page.</p>
 *
 * <p>Mapped to the URL pattern <code>/logout</code>.</p>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-07-22
 */
@WebServlet("/logout")
public class LogoutAction extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Handles HTTP GET requests for logging out the user.
     *
     * <p>Invalidates the current session and redirects to the login page.</p>
     *
     * @param request  the {@link HttpServletRequest} object
     * @param response the {@link HttpServletResponse} object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during redirection
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Invalidate the current session if it exists
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // Redirect back to the login page
        response.sendRedirect(request.getContextPath() + "/T001");
    }

    /**
     * Handles HTTP POST requests by delegating to {@link #doGet(HttpServletRequest, HttpServletResponse)}.
     *
     * @param request  the {@link HttpServletRequest} object
     * @param response the {@link HttpServletResponse} object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during redirection
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
