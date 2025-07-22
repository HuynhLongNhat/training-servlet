package action;

import java.io.IOException;
import java.sql.SQLException;

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
 * <p>This servlet handles requests to edit a customer's information. It retrieves
 * the customer's data by ID from {@link T003Service} and forwards it to the JSP
 * view for editing.</p>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-07-21
 */
@WebServlet("/T003")
public class T003Action extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** Service instance for retrieving customer data */
    private final T003Service t003Service = new T003Service();

    /**
     * Handles GET requests to display the customer edit screen.
     *
     * <p>Retrieves a customer's information based on the {@code customerId}
     * parameter, sets it as a request attribute, and forwards to the JSP page
     * specified in {@link Constant#T003}.</p>
     *
     * @param request  the {@link HttpServletRequest} object
     * @param response the {@link HttpServletResponse} object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during forwarding
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve customerId from request
        String customerId = request.getParameter("customerId");

        try {
            // Retrieve customer details by ID
            T002Dto customer = t003Service.getCustomerById(customerId);

            // Set customer data in request scope
            request.setAttribute("customer", customer);

            // Forward to edit JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher(Constant.T003);
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve customer information for editing.", e);
        }
    }

    /**
     * Handles POST requests for updating customer information.
     *
     * <p>This method will be implemented to process submitted changes and update
     * the customer record in the database.</p>
     *
     * @param request  the {@link HttpServletRequest} object
     * @param response the {@link HttpServletResponse} object
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs during forwarding
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO: Implement update logic here
        doGet(request, response);
    }
}
