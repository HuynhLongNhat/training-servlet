package service;

import java.sql.SQLException;
import dao.T001Dao;
import dto.T001Dto;

/**
 * Service class for managing login-related operations.
 *
 * <p>This class handles the business logic of user authentication and 
 * acts as an intermediary between the presentation layer (controllers/actions) 
 * and the data access layer ({@link T001Dao}).</p>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-07-21
 */
public class T001Service {

    /** Singleton instance of {@code T001Service} */
    private static final T001Service instance = new T001Service();

    /** DAO instance for accessing {@code MSTUSER} table */
    private final T001Dao t001Dao = T001Dao.getInstance();

    /** Private constructor to enforce singleton pattern */
    private T001Service() {}

    /**
     * Returns the singleton instance of {@code T001Service}.
     *
     * @return singleton instance
     */
    public static T001Service getInstance() {
        return instance;
    }

    /**
     * Authenticates a user by validating the given login credentials.
     *
     * <p>This method delegates the authentication process to {@link T001Dao}.
     * If credentials are valid, it returns the user details as a {@link T001Dto} object.
     * Otherwise, it returns {@code null}.</p>
     *
     * @param t001Dto DTO containing {@code userId} and {@code password}
     * @return {@link T001Dto} with authenticated user details, or {@code null} if authentication fails
     * @throws SQLException if a database access error occurs
     */
    public T001Dto getUserLogin(T001Dto t001Dto) throws SQLException {
        return t001Dao.getUserLogin(t001Dto);
    }
}
