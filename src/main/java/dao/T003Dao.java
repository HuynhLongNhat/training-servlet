package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dto.T002Dto;
import utils.DBUtils;

public class T003Dao {
	/** Singleton eager instance */
	private static final T003Dao instance = new T003Dao();

	/** Private constructor to prevent external instantiation */
	private T003Dao() {
	}

	/** Returns the singleton instance */
	public static T003Dao getInstance() {
		return instance;
	}

	public T002Dto getCustomerById(String customerId) {
	    String query = "SELECT CUSTOMER_ID, CUSTOMER_NAME, SEX, BIRTHDAY, EMAIL, ADDRESS " +
	                   "FROM MSTCUSTOMER " +
	                   "WHERE CUSTOMER_ID = ? AND DELETE_YMD IS NULL";

	    try (Connection conn = DBUtils.getInstance().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(query)) {

	        stmt.setString(1, customerId);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                T002Dto customer = new T002Dto();
	                customer.setCustomerID(rs.getString("CUSTOMER_ID"));
	                customer.setCustomerName(rs.getString("CUSTOMER_NAME"));
	                customer.setSex(rs.getString("SEX"));
	                customer.setBirthday(rs.getString("BIRTHDAY"));
	                customer.setEmail(rs.getString("EMAIL"));
	                customer.setAddress(rs.getString("ADDRESS"));
	                return customer;
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
    
	public int insertCustomer(T002Dto customer, String psnCd) {
	    String sql = "INSERT INTO MSTCUSTOMER (CUSTOMER_ID, CUSTOMER_NAME, SEX, BIRTHDAY, EMAIL, ADDRESS, " +
	                 "DELETE_YMD, INSERT_YMD, INSERT_PSN_CD, UPDATE_YMD, UPDATE_PSN_CD) " +
	                 "VALUES (SEQ_CUSTOMER_ID.NEXTVAL, ?, ?, ?, ?, ?, NULL, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?)";
	    try (Connection conn = DBUtils.getInstance().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, customer.getCustomerName());
	        stmt.setString(2, customer.getSex());
	        stmt.setString(3, customer.getBirthday());
	        stmt.setString(4, customer.getEmail());
	        stmt.setString(5, customer.getAddress());
	        stmt.setString(6, psnCd);
	        stmt.setString(7, psnCd);

	        return stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0;
	}
	
	public int updateCustomer(T002Dto customer, String psnCd) {
	    String sql = "UPDATE MSTCUSTOMER " +
	                 "SET CUSTOMER_NAME = ?, SEX = ?, BIRTHDAY = ?, EMAIL = ?, ADDRESS = ?, " +
	                 "DELETE_YMD = NULL, UPDATE_YMD = CURRENT_TIMESTAMP, UPDATE_PSN_CD = ? " +
	                 "WHERE CUSTOMER_ID = ?";
	    try (Connection conn = DBUtils.getInstance().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setString(1, customer.getCustomerName());
	        stmt.setString(2, customer.getSex());
	        stmt.setString(3, customer.getBirthday());
	        stmt.setString(4, customer.getEmail());
	        stmt.setString(5, customer.getAddress());
	        stmt.setString(6, psnCd);
	        stmt.setString(7, customer.getCustomerID());

	        return stmt.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return 0;
	}



}
