package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.DBUtils;

public class T001Dao {

	public int login(String userId, String password) throws SQLException {

		try (Connection conn = DBUtils.getConnection()) {
			String sql = "SELECT COUNT(*) AS CNT FROM MSTUSER WHERE DELETE_YMD IS NULL AND USERID = ? AND PASSWORD = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, password);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("CNT");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
