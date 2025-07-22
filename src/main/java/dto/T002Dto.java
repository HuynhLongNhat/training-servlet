package dto;

/**
 * Data Transfer Object (DTO) for transferring customer information between the
 * Controller, Service, and DAO layers.
 *
 * <p>
 * This class holds customer details such as ID, name, sex, birthday, and
 * address.
 * </p>
 * 
 * @author YourName
 * @version 1.0
 * @since 2025-07-21
 */
public class T002Dto {

	/** Unique identifier of the customer */
	private String customerID;

	/** Name of the customer */
	private String customerName;

	/** Sex of the customer */
	private String sex;

	/** Birthday of the customer */
	private String birthday;

	/** Address of the customer */
	private String address;

	/**
	 * Default constructor.
	 */
	public T002Dto() {
	}

	/**
	 * Gets the customer's ID.
	 *
	 * @return the customer's ID
	 */
	public String getCustomerID() {
		return customerID;
	}

	/**
	 * Sets the customer's ID.
	 *
	 * @param customerID the customer's ID to set
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	/**
	 * Gets the customer's name.
	 *
	 * @return the customer's name
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * Sets the customer's name.
	 *
	 * @param customerName the customer's name to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * Gets the customer's sex.
	 *
	 * @return the customer's sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * Sets the customer's sex.
	 *
	 * @param sex the customer's sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * Gets the customer's birthday.
	 *
	 * @return the customer's birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * Sets the customer's birthday.
	 *
	 * @param birthday the customer's birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * Gets the customer's address.
	 *
	 * @return the customer's address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Sets the customer's address.
	 *
	 * @param address the customer's address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
}
