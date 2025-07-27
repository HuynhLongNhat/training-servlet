package service;

import java.sql.SQLException;

import dao.T003Dao;
import dto.T002Dto;

public class T003Service {
    /** Singleton instance of {@code T003Service}. */
    private static final T003Service INSTANCE = new T003Service();

    /** Reference to the data access object (DAO) for customers. */
    private final T003Dao t003Dao = T003Dao.getInstance();

    /** Private constructor to prevent external instantiation. */
    private T003Service() {}

    /**
     * Provides access to the single instance of {@code T003Service}.
     *
     * @return the singleton instance
     */
    public static T003Service getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId the customer ID
     * @return a {@link T002Dto} object if found, otherwise {@code null}
     */
    public T002Dto getCustomerById(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            return null;
        }
        return t003Dao.getCustomerById(customerId);
    }
    
    public boolean insertCustomer(T002Dto customer, String psnCd) throws SQLException {
        return t003Dao.insertCustomer(customer, psnCd) > 0;
    }

    public boolean updateCustomer(T002Dto customer, String psnCd) throws SQLException {
        return t003Dao.updateCustomer(customer, psnCd) > 0;
    }
}
