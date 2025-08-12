package action;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.Constant;
import common.Helper;
import dto.T002Dto;
import dto.T002SCO;
import service.T002Service;

/**
 * T002Action handling request customer list display and search.
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
	 * Validates birthday range input.
	 *
	 * @param birthdayFrom start date string in yyyy/MM/dd format
	 * @param birthdayTo   end date string in yyyy/MM/dd format
	 * @return error message if invalid, null if valid
	 */
	private String validateBirthdayInput(String birthdayFrom, String birthdayTo) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		try {
			LocalDate from = Helper.isEmpty(birthdayFrom) ? null : LocalDate.parse(birthdayFrom.trim(), formatter);
			LocalDate to = Helper.isEmpty(birthdayTo) ? null : LocalDate.parse(birthdayTo.trim(), formatter);

			// Check if range is invalid
			if (from != null && to != null && to.isBefore(from)) {
				return "There is an error in the range input of birthday.";
			}
		} catch (DateTimeParseException e) {
			// Identify which date is invalid
			return e.getParsedString().equals(birthdayFrom) ? "Invalid Birthday (From)." : "Invalid Birthday (To).";
		}
		return null;
	}

	/**
	 * Handles GET requests for T002 screen.
	 *
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		findCustomer(request, response);
	}

	/**
	 * Handles POST requests for T002 screen. Supports both search and delete
	 * actions.
	 *
	 * @param HttpServletRequest  request
	 * @param HttpServletResponse response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");

		// Check if delete action is triggered
		if ("delete".equals(action)) {
			deleteCustomer(request, response);
		} else {
			findCustomer(request, response);
		}
	}

	/**
	 * Processes customer listing and search logic.
	 *
	 * @param request  HTTP request
	 * @param response HTTP response
	 * @throws ServletException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void findCustomer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Validate session
		if (!Helper.isLogin(request)) {
			response.sendRedirect(request.getContextPath() + "/T001");
			return;
		}
		;
		String actionType = request.getParameter("actionType");
		HttpSession session = request.getSession();

		// Get search condition object from session
		T002SCO sco = (T002SCO) session.getAttribute("T002SCO");
		if (sco == null) {
			sco = new T002SCO();
		}

		if ("search".equals(actionType)) {
			// Get from request
			String customerName = request.getParameter("lblUserName");
			String sex = request.getParameter("cboSex");
			String birthdayFrom = request.getParameter("txtBirthdayForm");
			String birthdayTo = request.getParameter("txtBirthdayTo");

			sco.setCustomerName(customerName);
			sco.setSex(sex);
			sco.setBirthdayFrom(birthdayFrom);
			sco.setBirthdayTo(birthdayTo);

			// Validate input
			String errorMessage = validateBirthdayInput(sco.getBirthdayFrom(), sco.getBirthdayTo());
			if (errorMessage != null) {
				request.setAttribute("errorMessage", errorMessage);
			}
			// Save SCO into session
			session.setAttribute("T002SCO", sco);
		}
		// Handle pagination
		int currentPage;
		try {
			currentPage = Math.max(Integer.parseInt(request.getParameter("page")), 1);
		} catch (NumberFormatException e) {
			currentPage = 1;
		}
		// Calculate offset
		int offset = (currentPage - 1) * PAGE_SIZE;

		try {
			// Fetch customer data from service
			Map<String, Object> data = t002Service.searchCustomers(sco, offset, PAGE_SIZE);
			List<T002Dto> customers = (List<T002Dto>) data.get("customers");
			int totalRecords = (int) data.get("totalCount");

			// Calculate total pages
			int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);
			if (currentPage > totalPages && totalPages > 0) {
				currentPage = totalPages;
			}

			// Set attributes for JSP
			request.setAttribute("customers", customers);
			request.setAttribute("totalRecords", totalRecords);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("totalPages", totalPages);

			// Handle pagination button states
			boolean disableFirst = currentPage == 1;
			boolean disableLast = currentPage == totalPages || totalPages == 0;
			request.setAttribute("disableFirst", disableFirst);
			request.setAttribute("disablePrevious", disableFirst);
			request.setAttribute("disableNext", disableLast);
			request.setAttribute("disableLast", disableLast);

			// Preserve search inputs
			request.setAttribute("searchUserName", sco.getCustomerName());
			request.setAttribute("searchSex", sco.getSex());
			request.setAttribute("searchBirthdayFrom", sco.getBirthdayFrom());
			request.setAttribute("searchBirthdayTo", sco.getBirthdayTo());
			// Forward to JSP
			request.getRequestDispatcher(Constant.T002).forward(request, response);
		} catch (Exception e) {
			// Handle unexpected errors
			e.printStackTrace();
			request.getRequestDispatcher(Constant.T002).forward(request, response);
		}
	}
	/**
	 * Handles deletion of selected customers.
	 *
	 * @param request  HTTP request
	 * @param response HTTP response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get selected customer IDs
		String[] ids = request.getParameterValues("customerIds");

		// If no rows selected, show error and reload list
		if (ids == null || ids.length == 0) {
			request.setAttribute("errorMessage", "行を選択してください。");
			findCustomer(request, response);
			return;
		}

		try {
			// Delete selected customers
			t002Service.deleteCustomers(Arrays.asList(ids));
		} catch (SQLException e) {
			throw new ServletException(e);
		}

		// Reload list after deletion
		findCustomer(request, response);
	}
}
