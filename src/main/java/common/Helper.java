package common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Helper - Utility class providing common helper methods used across the application.
 */
public class Helper {
	
	/**
     * Check if a string is null or empty after trimming.
     *
     * @param value the string to check
     * @return true if the string is null or empty, false otherwise
     */
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Check for invalid user session.
     *
     * @param HttpServletRequest request
     * @return true if session is valid (logged in), false if not logged in
     */
    public static boolean isValidSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // không tạo session mới
        return (session != null && session.getAttribute("user") != null);
    }
}
