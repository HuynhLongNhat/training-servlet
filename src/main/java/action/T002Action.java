package action;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Constant;
import dto.T002Dto;
import service.T002Service;

/**
 * Servlet controller for handling customer list display and search (T002 screen).
 *
 * <p>Features:</p>
 * <ul>
 *     <li>Validates user session (redirects to T001 if not logged in).</li>
 *     <li>Handles customer listing with optional search filters and pagination.</li>
 *     <li>Validates birthday range input for search.</li>
 *     <li>Preserves search criteria and shows relevant error messages if validation fails.</li>
 * </ul>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-07-23
 */
@WebServlet("/T002")
public class T002Action extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /** Service instance for data access. */
    private final T002Service t002Service = T002Service.getInstance();

    /** Default number of records per page. */
    private static final int PAGE_SIZE = 15;

    /**
     * Checks if a string is null or empty after trimming.
     *
     * @param value the string to check
     * @return {@code true} if null or empty, otherwise {@code false}
     */
    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates birthday range input.
     *
     * @param birthdayFrom start date string in {@code yyyy/MM/dd} format
     * @param birthdayTo   end date string in {@code yyyy/MM/dd} format
     * @return error message if invalid, {@code null} if valid
     */
    private String validateBirthdayInput(String birthdayFrom, String birthdayTo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try {
            LocalDate from = isEmpty(birthdayFrom) ? null : LocalDate.parse(birthdayFrom.trim(), formatter);
            LocalDate to = isEmpty(birthdayTo) ? null : LocalDate.parse(birthdayTo.trim(), formatter);
            if (from != null && to != null && to.isBefore(from)) {
                return "There is an error in the range input of birthday.";
            }
        } catch (DateTimeParseException e) {
            return e.getParsedString().equals(birthdayFrom) ? "Invalid Birthday (From)." : "Invalid Birthday (To).";
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Processes GET and POST requests for T002 screen.
     *
     * @param request  the {@link HttpServletRequest}
     * @param response the {@link HttpServletResponse}
     * @throws ServletException if servlet error occurs
     * @throws IOException      if I/O error occurs
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Validate session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/T001");
            return;
        }

        // Search parameters
        String userName = request.getParameter("lblUserName");
        String sex = request.getParameter("cboSex");
        String birthdayFrom = request.getParameter("txtBirthdayForm");
        String birthdayTo = request.getParameter("txtBirthdayTo");
        boolean isSearch = !isEmpty(userName) || !isEmpty(sex) || !isEmpty(birthdayFrom) || !isEmpty(birthdayTo);

        // Pagination
        int currentPage;
        try {
            currentPage = Math.max(Integer.parseInt(request.getParameter("page")), 1);
        } catch (NumberFormatException e) {
            currentPage = 1;
        }
        int offset = (currentPage - 1) * PAGE_SIZE;

        try {
            // Validate birthday range if searching
            String errorMessage = isSearch ? validateBirthdayInput(birthdayFrom, birthdayTo) : null;
            if (errorMessage != null) {
                request.setAttribute("errorMessage", errorMessage);
                // Reset filters if validation fails
                userName = sex = birthdayFrom = birthdayTo = null;
            }

            // Fetch data (filtered or all)
            Map<String, Object> data = t002Service.searchCustomers(userName, sex, birthdayFrom, birthdayTo, offset, PAGE_SIZE);
            List<T002Dto> customers = (List<T002Dto>) data.get("customers");
            int totalRecords = (int) data.get("totalCount");

            int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);
            if (currentPage > totalPages && totalPages > 0) {
                currentPage = totalPages;
            }

            // Set attributes for JSP
            request.setAttribute("customers", customers);
            request.setAttribute("totalRecords", totalRecords);
            request.setAttribute("currentPage", currentPage);
            request.setAttribute("totalPages", totalPages);
            boolean disableFirst = currentPage == 1;
            boolean disableLast = currentPage == totalPages || totalPages == 0;
            request.setAttribute("disableFirst", disableFirst);
            request.setAttribute("disablePrevious", disableFirst);
            request.setAttribute("disableNext", disableLast);
            request.setAttribute("disableLast", disableLast);

            // Preserve search inputs
            request.setAttribute("searchUserName", request.getParameter("lblUserName"));
            request.setAttribute("searchSex", request.getParameter("cboSex"));
            request.setAttribute("searchBirthdayFrom", request.getParameter("txtBirthdayForm"));
            request.setAttribute("searchBirthdayTo", request.getParameter("txtBirthdayTo"));

            request.getRequestDispatcher(Constant.T002).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher(Constant.T002).forward(request, response);
        }
    }
}
