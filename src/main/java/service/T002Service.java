package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dao.T002Dao;

/**
 * Service class that handles business logic related to customers.
 *
 * <p>This class acts as a bridge between the controller layer and
 * the {@link T002Dao} data access layer.</p>
 * 
 * <p>Provides methods to fetch customer lists with pagination and search filters,
 * along with the total count of matching customers.</p>
 * 
 * <p>Implements the Singleton pattern to ensure a single instance
 * is used throughout the application.</p>
 * 
 * @author YourName
 * @version 2.0
 * @since 2025-07-21
 */
/**
 * 
 */
/**
 * 
 */
/**
 * 
 */
public class T002Service {

    /** Singleton instance of {@code T002Service}. */
    private static final T002Service INSTANCE = new T002Service();

    /** Reference to the data access object (DAO) for customers. */
    private final T002Dao t002Dao = T002Dao.getInstance();

    /** Private constructor to prevent external instantiation. */
    private T002Service() {}

    /**
     * Provides access to the single instance of {@code T002Service}.
     *
     * @return the singleton instance
     */
    public static T002Service getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves customers with optional search filters and pagination.
     *
     * @param userName      customer name filter (nullable)
     * @param sex           gender filter: "0" for Male, "1" for Female (nullable)
     * @param birthdayFrom  birthday from date filter (nullable)
     * @param birthdayTo    birthday to date filter (nullable)
     * @param offset        row index to start (zero-based)
     * @param limit         maximum number of records to retrieve
     * @return a map containing:
     *         - "customers": List&lt;T002Dto&gt; of customers
     *         - "totalCount": Integer total matching records
     * @throws SQLException if a database access error occurs
     */
    public Map<String, Object> searchCustomers(String userName, String sex,
                                               String birthdayFrom, String birthdayTo,
                                               int offset, int limit) throws SQLException {
        return t002Dao.searchCustomers(userName, sex, birthdayFrom, birthdayTo, offset, limit);
    }
    
    
    /**
     * Deletes customers based on the provided list of IDs.
     *
     * @param customerIds the list of customer IDs to be marked as deleted
     * @throws SQLException if a database access error occurs
     */
    public void deleteCustomers(List<String> customerIds) throws SQLException {
        t002Dao.deleteCustomer(customerIds);
    }

    
    
}
