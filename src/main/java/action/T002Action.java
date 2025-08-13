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

@WebServlet("/T002")
public class T002Action extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final T002Service t002Service = T002Service.getInstance();
	private static final int PAGE_SIZE = 15;
	private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy/MM/dd");

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!checkLogin(request, response))
			return;
		T002SCO sco = getSCO(request);
		findCustomer(request, response, sco);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!checkLogin(request, response))
			return;
		String actionType = request.getParameter("actionType");
		if ("delete".equalsIgnoreCase(actionType)) {
			String[] ids = request.getParameterValues("customerIds");
			deleteCustomer(request, response,ids);
		} else if ("search".equalsIgnoreCase(actionType)) {
			T002SCO sco = getSCO(request);
			String customerName = request.getParameter("customerName");
			String sex = request.getParameter("sex");
			String birthdayFrom = request.getParameter("birthdayFrom");
			String birthdayTo = request.getParameter("birthdayTo");
			sco.setCustomerName(customerName);
			sco.setSex(sex);
			String err = validateBirthday(birthdayFrom, birthdayTo);
			if (err != null) {
				request.setAttribute("errorMessage", err);
			}
			else {
				sco.setBirthdayFrom(birthdayFrom);
				sco.setBirthdayTo(birthdayTo);
			}
			HttpSession session = request.getSession();
			session.setAttribute("T002SCO", sco);
			findCustomer(request, response, sco);
		}
	}

	/** Check login status and redirect if session expired */
	private boolean checkLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (!Helper.isLogin(request)) {
			response.sendRedirect(request.getContextPath() + "/T001");
			return false;
		}
		return true;
	}

	/** Validate birthday input */
	private String validateBirthday(String fromStr, String toStr) {
	    LocalDate fromDate = null, toDate = null;

	    if (!Helper.isEmpty(fromStr)) {
	        try {
	            fromDate = LocalDate.parse(fromStr.trim(), DATE_FMT);
	        } catch (DateTimeParseException e) {
	            return "Invalid Birthday (From).";
	        }
	    }

	    if (!Helper.isEmpty(toStr)) {
	        try {
	            toDate = LocalDate.parse(toStr.trim(), DATE_FMT);
	        } catch (DateTimeParseException e) {
	            return "Invalid Birthday (To).";
	        }
	    }

	    if (fromDate != null && toDate != null && toDate.isBefore(fromDate)) {
	        return "Birthday To must be after or equal Birthday From.";
	    }
	    return null;
	}


	/** Display and search customer list */
	@SuppressWarnings("unchecked")
	private void findCustomer(HttpServletRequest request, HttpServletResponse response, T002SCO sco)
			throws ServletException, IOException {

		int currentPage = parsePage(request.getParameter("page"));
		int offset = (currentPage - 1) * PAGE_SIZE;

		try {
			Map<String, Object> data = t002Service.searchCustomers(sco, offset, PAGE_SIZE);
			List<T002Dto> customers = (List<T002Dto>) data.get("customers");
			int totalRecords = (int) data.get("totalCount");
			int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);

			if (currentPage > totalPages && totalPages > 0) {
				currentPage = totalPages;
			}

			request.setAttribute("customers", customers);
			request.setAttribute("totalRecords", totalRecords);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("disableFirst", currentPage == 1);
			request.setAttribute("disablePrevious", currentPage == 1);
			request.setAttribute("disableNext", currentPage == totalPages || totalPages == 0);
			request.setAttribute("disableLast", currentPage == totalPages || totalPages == 0);

			request.setAttribute("searchUserName", sco.getCustomerName());
			request.setAttribute("searchSex", sco.getSex());
			request.setAttribute("searchBirthdayFrom", sco.getBirthdayFrom());
			request.setAttribute("searchBirthdayTo", sco.getBirthdayTo());

			request.getRequestDispatcher(Constant.T002).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			request.getRequestDispatcher(Constant.T002).forward(request, response);
		}
	}

	/** Delete selected customers */
	private void deleteCustomer(HttpServletRequest request, HttpServletResponse response, String[] ids)
			throws ServletException, IOException {
		T002SCO sco = getSCO(request);
		if (ids == null || ids.length == 0) {
			request.setAttribute("errorMessage", "行を選択してください。");
			findCustomer(request, response, sco);
			return;
		}
		try {
			t002Service.deleteCustomers(Arrays.asList(ids));
			response.sendRedirect(request.getContextPath() + "/T002"); 
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	/** Utility: parse page number */
	private int parsePage(String pageStr) {
		try {
			return Math.max(Integer.parseInt(pageStr), 1);
		} catch (NumberFormatException e) {
			return 1;
		}
	}
	
	private T002SCO getSCO(HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    T002SCO sco = (T002SCO) session.getAttribute("T002SCO");
	    if (sco == null) {
	        sco = new T002SCO();
	        session.setAttribute("T002SCO", sco);
	    }
	    return sco;
	}
}
