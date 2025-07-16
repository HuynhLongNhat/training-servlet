package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=CustomerSystem;integratedSecurity=true";
	public static Connection getConnection() throws SQLException {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");


		} catch (ClassNotFoundException e) {
			System.out.println("loi ket noi roi");
			e.printStackTrace();
		}
		return DriverManager.getConnection(URL);
	}
  public static void closeConnection(Connection connection) {
	  if(connection != null) {
		   try {
			  connection.close();
			  System.out.println("Connected has been closed ");
		   }catch(SQLException e) {
			   System.out.println("Error when close connect" + e.getMessage());
		   }
	  }
  }
}
