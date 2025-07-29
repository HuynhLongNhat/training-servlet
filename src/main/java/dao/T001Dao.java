package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dto.T001Dto;
import utils.DBUtils;

/**
 * Data Access Object (DAO) for handling login operations.
 *
 * <p>This class provides methods to interact with the {@code MSTUSER} table
 * in the database, specifically for verifying user login credentials.</p>
 */
public class T001Dao {

    /** Singleton eager instance */
    private static final T001Dao instance = new T001Dao();

    /** Private constructor to prevent external instantiation */
    private T001Dao() {}

    /**
     * Returns the single eager instance of {@code T001Dao}.
     *
     * @return the singleton instance
     */
    public static T001Dao getInstance() {
        return instance;
    }

    /**
     * Verifies login credentials by checking the {@code MSTUSER} table.
     *
     * @param t001Dto a {@link T001Dto} object containing {@code userId} and {@code password}
     * @return the number of matching records; returns {@code 0} if no match is found
     * @throws SQLException if a database access error occurs
     */
    public T001Dto getUserLogin(T001Dto inputDto) throws SQLException {
        String sql = "SELECT USERID, USERNAME , PSN_CD "
                   + "FROM MSTUSER "
                   + "WHERE DELETE_YMD IS NULL AND USERID = ? AND PASSWORD = ?";

        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, inputDto.getUserId());
            ps.setString(2, inputDto.getPassword());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    T001Dto userDto = new T001Dto();
                    userDto.setUserId(rs.getString("USERID"));
                    userDto.setUserName(rs.getString("USERNAME"));
                    userDto.setPSN_CD(rs.getInt("PSN_CD"));
                    return userDto; // Trả về DTO nếu tìm thấy user
                }
            }
        }
        return null; // Không tìm thấy user
    }

   
}
