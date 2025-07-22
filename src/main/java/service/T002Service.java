package service;

import java.sql.SQLException;
import java.util.List;

import dao.T002Dao;
import dto.T002Dto;

/**
 * Service class that handles business logic related to customers.
 *
 * <p>This class acts as a bridge between the controller layer and
 * the {@link T002Dao} data access layer.</p>
 * 
 * <p>Responsibilities include:</p>
 * <ul>
 *     <li>Fetching paginated customer lists.</li>
 *     <li>Retrieving the total number of active customers.</li>
 * </ul>
 * 
 * <p>Implements the Singleton pattern to ensure a single instance
 * is used throughout the application.</p>
 * 
 * @author YourName
 * @version 1.1
 * @since 2025-07-21
 */
public class T002Service {

    /** Singleton instance of {@code T002Service}. */
    private static final T002Service INSTANCE = new T002Service();

    /** Reference to the data access object (DAO) for customers. */
    private final T002Dao t002Dao = T002Dao.getInstance();

    /** 
     * Private constructor to prevent external instantiation. 
     */
    private T002Service() {
        // Prevent instantiation
    }

    /**
     * Provides access to the single instance of {@code T002Service}.
     *
     * @return the singleton instance
     */
    public static T002Service getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves a paginated list of active customers.
     *
     * @param offset   the number of records to skip (zero-based)
     * @param pageSize the maximum number of records to retrieve
     * @return a list of {@link T002Dto} representing the customers
     * @throws SQLException if a database access error occurs
     */
    public List<T002Dto> getAllCustomers(int offset, int pageSize) throws SQLException {
        return t002Dao.getAllCustomers(offset, pageSize);
    }

    /**
     * Retrieves the total number of active customers.
     *
     * @return the total count of active customers
     * @throws SQLException if a database access error occurs
     */
    public int getTotalCustomerCount() throws SQLException {
        return t002Dao.getTotalCustomerCount();
    }
}
