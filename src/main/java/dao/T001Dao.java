package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.T001Dto;
import utils.DBUtils;

public class T001Dao  {
 
	public String checkLogin(T001Dto t001Dto) {
	    // Kiểm tra null hoặc chuỗi rỗng đúng cách
	    if (t001Dto.getUserId() == null || t001Dto.getUserId().trim().isEmpty() ||
	        t001Dto.getPassword() == null || t001Dto.getPassword().trim().isEmpty()) {
	        return "Vui lòng nhập username hoặc password";
	    }

	    try (Connection conn = DBUtils.getConnection()) {
	        String sql = "SELECT * FROM MSTUSER WHERE USERID = ? AND PASSWORD = ?";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, t001Dto.getUserId());
	        stmt.setString(2, t001Dto.getPassword());

	        ResultSet rs = stmt.executeQuery();

	        if (!rs.next()) {
	            return "Username hoặc password không đúng";
	        }

	      	rs.close();
	        stmt.close();

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "Lỗi kết nối CSDL";
	    }

	    return null;
	}

}
