package action;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Constant;
import dto.T002Dto;
import service.T002Service;

/**
 * Servlet controller responsible for displaying the paginated customer list.
 *
 * <p>This servlet handles requests to view customers with pagination support.
 * It retrieves the total record count and current page of customers from
 * {@link T002Service}, then forwards the data to the JSP view for rendering.</p>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-07-21
 */
@WebServlet("/T002")
public class T002Action extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** Service instance for retrieving customer data (singleton). */
    private final T002Service t002Service = T002Service.getInstance();

    /**
     * Handles GET requests to display the paginated customer list.
     *
     * @param request  the {@link HttpServletRequest} object
     * @param response the {@link HttpServletResponse} object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int currentPage = 1;
        int pageSize = 15;

        try {
            // Parse current page from request parameter
            String pageParam = request.getParameter("page");
            if (pageParam != null) {
                try {
                    currentPage = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    currentPage = 1; // Default to first page if parsing fails
                }
            }

            // Retrieve total record count
            int totalRecords = t002Service.getTotalCustomerCount();
            int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

            // Ensure currentPage is within valid range
            if (currentPage < 1) {
                currentPage = 1;
            }
            if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
            }

            // Calculate offset for SQL query
            int offset = (currentPage - 1) * pageSize;

            // Retrieve customer list for current page
         
            List<T002Dto> customers = t002Service.getAllCustomers(offset, pageSize);

            // Set attributes for JSP
            request.setAttribute("customers", customers);
            request.setAttribute("totalRecords", totalRecords);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);

            // Determine button states
            boolean disableFirst, disablePrevious, disableNext, disableLast;
            if (totalRecords <= pageSize) {
                disableFirst = disablePrevious = disableNext = disableLast = true;
            } else {
                disableFirst = currentPage == 1;
                disablePrevious = currentPage == 1;
                disableNext = currentPage == totalPages;
                disableLast = currentPage == totalPages;
            }

            request.setAttribute("disableFirst", disableFirst);
            request.setAttribute("disablePrevious", disablePrevious);
            request.setAttribute("disableNext", disableNext);
            request.setAttribute("disableLast", disableLast);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load customer list.");
        }

        // Forward to JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.T002);
        dispatcher.forward(request, response);
    }

    /**
     * Handles POST requests by delegating to
     * {@link #doGet(HttpServletRequest, HttpServletResponse)}.
     *
     * @param request  the {@link HttpServletRequest} object
     * @param response the {@link HttpServletResponse} object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during forwarding
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
