package common;

/**
 * Lớp Constant chứa các hằng số dùng chung trong ứng dụng,
 * đặc biệt là các đường dẫn tới các file JSP.
 */
public class Constant {

    /**
     * Đường dẫn đến thư mục chứa các file JSP.
     * Các file JSP được đặt trong thư mục /WEB-INF/jsp/ để bảo mật.
     */
    public static final String VIEW_FOLDER = "WebContent/WEB-INF/jsp/";
    /**
     * Đường dẫn đến trang JSP T001.
     */
    public static final String T001 = VIEW_FOLDER + "T001.jsp";

    /**
     * Đường dẫn đến trang JSP T002.
     */
    public static final String T002 = VIEW_FOLDER + "T002.jsp";

    /**
     * Đường dẫn đến trang JSP T003.
     */
    public static final String T003 = VIEW_FOLDER + "T003.jsp";
}
