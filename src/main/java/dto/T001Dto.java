package dto;

/**
 * Data Transfer Object (DTO) for transferring login information
 * between the Controller, Service, and DAO layers.
 *
 * <p>This class holds the user credentials used for authentication.</p>
 *
 * @author YourName
 * @version 1.0
 * @since 2025-07-21
 */
public class T001Dto {

    /** Unique identifier of the user */
    private String userId;

    /** Password of the user */
    private String password;
    
    /** userName of the user */
    private String userName;

    /**
     * Default constructor.
     */
    public T001Dto() {
    }

    /**
     * Parameterized constructor for creating a {@code T001Dto} instance
     * with specified user ID and password.
     *
     * @param userId   the user ID
     * @param password the password
     */
    public T001Dto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    /**
     * Returns the user ID.
     *
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     *
     * @param userId the user ID to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Sets the password.
     *
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
}
