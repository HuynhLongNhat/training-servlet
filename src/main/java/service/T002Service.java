package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dao.T002Dao;

/**
 * Service class for handling business logic related to customers.
 *
 * <p>This class acts as a bridge between the controller layer and
 * the {@link T002Dao} data access layer. It provides methods for retrieving
 * and managing customer records, including search with filters, pagination,
 * and marking records as deleted.</p>
 *
 * <p>Implements the Singleton pattern to ensure a single instance is used
 * throughout the application.</p>
 * 
 * @author YourName
 * @version 1.0
 * @since 2025-07-21
 */
public class T002Service {

    /** Singleton instance of {@code T002Service}. */
    private static final T002Service INSTANCE = new T002Service();

    /** DAO instance for accessing customer data. */
    private final T002Dao t002Dao = T002Dao.getInstance();

    /** Private constructor to enforce Singleton pattern. */
    private T002Service() {}

    /**
     * Returns the singleton instance of {@code T002Service}.
     *
     * @return singleton instance
     */
    public static T002Service getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves customers based on optional filters and pagination.
     *
     * @param userName      filter by customer name (nullable)
     * @param sex           filter by gender: "0" for Male, "1" for Female (nullable)
     * @param birthdayFrom  filter by start birthday date (nullable)
     * @param birthdayTo    filter by end birthday date (nullable)
     * @param offset        zero-based index of the first row to fetch
     * @param limit         maximum number of rows to return
     * @return a map containing:
     *         - "customers": {@code List<T002Dto>} of customers
     *         - "totalCount": {@code Integer} total number of matching records
     * @throws SQLException if a database access error occurs
     */
    public Map<String, Object> searchCustomers(String userName, String sex,
                                               String birthdayFrom, String birthdayTo,
                                               int offset, int limit) throws SQLException {
        return t002Dao.searchCustomers(userName, sex, birthdayFrom, birthdayTo, offset, limit);
    }

    /**
     * Marks customers as deleted based on the provided list of IDs.
     *
     * @param customerIds list of customer IDs to be marked as deleted
     * @throws SQLException if a database access error occurs
     */
    public void deleteCustomers(List<String> customerIds) throws SQLException {
        t002Dao.deleteCustomer(customerIds);
    }
}
