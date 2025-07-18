package service;

import java.sql.SQLException;

import dao.T001Dao;

public class T001Service {
    private T001Dao t001Dao = new T001Dao();
	public int login(String userId, String password) {
		try {
			return t001Dao.login(userId , password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
   
}
