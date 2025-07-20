package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.T001Dto;
import utils.DBUtils;

/**
 * T001DAO class xử lý truy vấn đăng nhập
 */
public class T001Dao {

	/**
	 * Kiểm tra thông tin đăng nhập trong bảng MSTUSER.
	 *
	 * @param t001Dto đối tượng chứa userId và password
	 * @return true nếu user tồn tại, false nếu không
	 * @throws SQLException nếu có lỗi khi truy vấn CSDL
	 */
	public boolean login(T001Dto t001Dto) throws SQLException {
		String sql = "SELECT COUNT(*) AS CNT FROM MSTUSER "
				+ "WHERE DELETE_YMD IS NULL AND USERID = ? AND PASSWORD = ?";
		
		Connection conn = DBUtils.getConnection();
		try (PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, t001Dto.getUserId());
			ps.setString(2, t001Dto.getPassword());

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("CNT") == 1;
				}
			}
		}
		return false;
	}
}
