package service;

import java.sql.SQLException;

import dao.T001Dao;
import dto.T001Dto;

/**
 * T001Service class xử lý nghiệp vụ login
 */
public class T001Service {

	// T001Dao thao tác với bảng MSTUSER
	private static final T001Dao t001Dao = new T001Dao();

	/**
	 * Xác thực thông tin đăng nhập.
	 *
	 * @param t001Dto đối tượng chứa userId và password
	 * @return true nếu thông tin hợp lệ, false nếu không hợp lệ
	 */
	public boolean login(T001Dto t001Dto) {
		try {

			// Gọi DAO để kiểm tra dữ liệu đăng nhập trong cơ sở dữ liệu
			return t001Dao.login(t001Dto);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
