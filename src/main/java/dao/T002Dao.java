package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dto.T002Dto;
import utils.DBUtils;

/**
 * Data Access Object (DAO) for handling customer information.
 *
 * <p>This class provides methods to interact with the {@code MSTCUSTOMER} table in
 * the database, specifically for retrieving active customer records.</p>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-07-21
 */
public class T002Dao {

    /** Singleton eager instance */
    private static final T002Dao instance = new T002Dao();

    /** Private constructor to prevent external instantiation */
    private T002Dao() {}

    /**
     * Returns the single eager instance of {@code T002Dao}.
     *
     * @return the singleton instance
     */
    public static T002Dao getInstance() {
        return instance;
    }

    /**
     * Retrieves all active customers from the {@code MSTCUSTOMER} table with pagination.
     *
     * @param offset the starting row index
     * @param limit  the maximum number of rows to return
     * @return a list of {@link T002Dto} objects containing customer information
     * @throws SQLException if a database access error occurs
     */
    public List<T002Dto> getAllCustomers(int offset, int limit) throws SQLException {
    
        List<T002Dto> customers = new ArrayList<>();
        String sql = "SELECT CUSTOMER_ID, CUSTOMER_NAME, "
                   + "CASE WHEN SEX = '0' THEN 'Male' "
                   + "     WHEN SEX = '1' THEN 'Female' END AS SEX, "
                   + "BIRTHDAY, ADDRESS "
                   + "FROM MSTCUSTOMER "
                   + "WHERE DELETE_YMD IS NULL "
                   + "ORDER BY CUSTOMER_ID "
                   + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, offset);
            ps.setInt(2, limit);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    T002Dto dto = new T002Dto();
                    dto.setCustomerID(rs.getString("CUSTOMER_ID"));
                    dto.setCustomerName(rs.getString("CUSTOMER_NAME"));
                    dto.setSex(rs.getString("SEX"));
                    dto.setBirthday(rs.getString("BIRTHDAY"));
                    dto.setAddress(rs.getString("ADDRESS"));
                    customers.add(dto);
                }
            }
        }
        return customers;
    }

    /**
     * Retrieves the total number of active customers.
     *
     * @return the total count of customers
     * @throws SQLException if a database access error occurs
     */
    public int getTotalCustomerCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM MSTCUSTOMER WHERE DELETE_YMD IS NULL";
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}
