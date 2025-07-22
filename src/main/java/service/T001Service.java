package service;

import java.sql.SQLException;
import dao.T001Dao;
import dto.T001Dto;

/**
 * Service class responsible for handling login-related business logic.
 *
 * <p>This class acts as an intermediary between the presentation layer
 * and the data access layer ({@link T001Dao}). It validates login requests
 * and delegates database operations to the DAO.</p>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-07-21
 */
public class T001Service {

    /** Singleton instance */
    private static final T001Service instance = new T001Service();

    /** DAO instance for interacting with the {@code MSTUSER} table */
    private final T001Dao t001Dao = T001Dao.getInstance();

    /** Private constructor to prevent instantiation */
    private T001Service() {}

    /**
     * Returns the singleton instance of {@code T001Service}.
     *
     * @return the singleton instance
     */
    public static T001Service getInstance() {
        return instance;
    }

    /**
     * Authenticates the user based on login credentials.
     *
     * <p>This method delegates the credential check to {@link T001Dao}.
     * It returns the count of matching user records.</p>
     *
     * @param t001Dto a {@link T001Dto} object containing {@code userId} and {@code password}
     * @return the number of matching user records; {@code 0} if authentication fails
     * @throws SQLException if a database access error occurs
     */
    public int login(T001Dto t001Dto) throws SQLException {
        return t001Dao.login(t001Dto);
    }

    /**
     * Retrieves the user's name by user ID.
     *
     * @param userId the ID of the user
     * @return the user's name, or {@code null} if not found
     * @throws SQLException if a database access error occurs
     */
    public String getUserName(String userId) throws SQLException {
        return t001Dao.getUserName(userId);
    }
}
