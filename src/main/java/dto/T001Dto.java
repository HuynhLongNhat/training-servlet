package dto;

/**
 * Lớp T001Dto là Data Transfer Object (DTO) dùng để truyền dữ liệu đăng nhập
 * giữa các tầng Controller, Service và DAO.
 */
public class T001Dto {

    /** Mã định danh của người dùng */
    private String userId;

    /** Mật khẩu của người dùng */
    private String password;

    /**
     * Constructor mặc định.
     */
    public T001Dto() { }

    /**
     * Constructor khởi tạo đầy đủ.
     *
     * @param userId   User ID
     * @param password Password
     */
    public T001Dto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    /**
     * Lấy User ID.
     *
     * @return User ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Thiết lập User ID.
     *
     * @param userId User ID cần thiết lập
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Lấy mật khẩu.
     *
     * @return Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Thiết lập mật khẩu.
     *
     * @param password Password cần thiết lập
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
