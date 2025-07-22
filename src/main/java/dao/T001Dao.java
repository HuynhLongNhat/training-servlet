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
    public int login(T001Dto t001Dto) throws SQLException {
        String sql = "SELECT COUNT(*) AS CNT FROM MSTUSER "
                   + "WHERE DELETE_YMD IS NULL AND USERID = ? AND PASSWORD = ?";

        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t001Dto.getUserId());
            ps.setString(2, t001Dto.getPassword());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("CNT");
                }
            }
        }
        return 0;
    }

    /**
     * Retrieves the user's name from the {@code MSTUSER} table by user ID.
     *
     * @param userID the ID of the user
     * @return the user's name, or {@code null} if not found
     * @throws SQLException if a database access error occurs
     */
    public String getUserName(String userID) throws SQLException {
        String sql = "SELECT USERNAME FROM MSTUSER WHERE USERID = ? AND DELETE_YMD IS NULL";

        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("USERNAME");
                }
            }
        }
        return null;
    }
}
