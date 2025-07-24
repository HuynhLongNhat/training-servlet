package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dto.T002Dto;
import utils.DBUtils;

/**
 * Data Access Object (DAO) for handling customer information.
 *
 * <p>
 * Provides a single method {@code searchCustomers} to retrieve customers
 * with optional search filters, pagination, and total count.
 * </p>
 */
public class T002Dao {

    /** Singleton eager instance */
    private static final T002Dao instance = new T002Dao();

    /** Private constructor to prevent external instantiation */
    private T002Dao() {}

    /** Returns the singleton instance */
    public static T002Dao getInstance() {
        return instance;
    }

    /**
     * Searches customers with optional filters and pagination.
     *
     * @param userName      Customer name (nullable)
     * @param sex           Gender: "0" for Male, "1" for Female (nullable)
     * @param birthdayFrom  Birthday from date (nullable)
     * @param birthdayTo    Birthday to date (nullable)
     * @param offset        Row index to start
     * @param limit         Maximum number of rows to return
     * @return Map containing:
     *         - "customers": List&lt;T002Dto&gt; of customers
     *         - "totalCount": Integer total matching records
     * @throws SQLException if a database access error occurs
     */
    public Map<String, Object> searchCustomers(String userName, String sex,
                                               String birthdayFrom, String birthdayTo,
                                               int offset, int limit) throws SQLException {

        // Build WHERE clause
        List<Object> params = new ArrayList<>();
        StringBuilder whereClause = new StringBuilder(" WHERE DELETE_YMD IS NULL");

        if (userName != null && !userName.trim().isEmpty()) {
            whereClause.append(" AND CUSTOMER_NAME LIKE ?");
            params.add("%" + userName.trim() + "%");
        }
        if (sex != null && !sex.trim().isEmpty()) {
            whereClause.append(" AND SEX = ?");
            params.add(sex.trim());
        }
        if (birthdayFrom != null && !birthdayFrom.trim().isEmpty()) {
            whereClause.append(" AND BIRTHDAY >= ?");
            params.add(birthdayFrom.trim());
        }
        if (birthdayTo != null && !birthdayTo.trim().isEmpty()) {
            whereClause.append(" AND BIRTHDAY <= ?");
            params.add(birthdayTo.trim());
        }

        // 1. Query total count
        int totalCount = 0;
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement psCount = conn.prepareStatement("SELECT COUNT(*) FROM MSTCUSTOMER" + whereClause)) {

            setParameters(psCount, params);
            try (ResultSet rs = psCount.executeQuery()) {
                if (rs.next()) {
                    totalCount = rs.getInt(1);
                }
            }
        }

        // 2. Query customers with pagination
        List<Object> listParams = new ArrayList<>(params);
        String sql = "SELECT CUSTOMER_ID, CUSTOMER_NAME, " +
                "CASE WHEN SEX = '0' THEN 'Male' WHEN SEX = '1' THEN 'Female' END AS SEX, " +
                "BIRTHDAY, ADDRESS FROM MSTCUSTOMER" + whereClause +
                " ORDER BY CUSTOMER_ID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        listParams.add(offset);
        listParams.add(limit);

        List<T002Dto> customers = new ArrayList<>();
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setParameters(ps, listParams);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    customers.add(mapRow(rs));
                }
            }
        }

        // Return both list and totalCount
        Map<String, Object> result = new HashMap<>();
        result.put("customers", customers);
        result.put("totalCount", totalCount);
        return result;
    }

    /** Sets PreparedStatement parameters */
    private void setParameters(PreparedStatement ps, List<Object> params) throws SQLException {
        for (int i = 0; i < params.size(); i++) {
            ps.setObject(i + 1, params.get(i));
        }
    }

    /** Maps a ResultSet row to a T002Dto */
    private T002Dto mapRow(ResultSet rs) throws SQLException {
        T002Dto dto = new T002Dto();
        dto.setCustomerID(rs.getString("CUSTOMER_ID"));
        dto.setCustomerName(rs.getString("CUSTOMER_NAME"));
        dto.setSex(rs.getString("SEX"));
        dto.setBirthday(rs.getString("BIRTHDAY"));
        dto.setAddress(rs.getString("ADDRESS"));
        return dto;
    }
}
