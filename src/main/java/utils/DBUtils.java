package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
	private static String URL;
	private static String USERNAME;
	private static String PASSWORD;

	static {
		try (InputStream input = DBUtils.class.getClassLoader().getResourceAsStream("dbConfig.properties")) {
			if (input == null) {
				throw new RuntimeException("Không tìm thấy file cấu hình dbConfig.properties");
			}

			Properties prop = new Properties();
			prop.load(input);

			String serverName = prop.getProperty("db.serverName");
			String port = prop.getProperty("db.port", "1433");
			String dbName = prop.getProperty("db.databaseName");
			USERNAME = prop.getProperty("db.username");
			PASSWORD = prop.getProperty("db.password");

			URL = String.format("jdbc:sqlserver://%s:%s;databaseName=%s", serverName, port, dbName);

			// Load JDBC driver
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException("Lỗi khi load cấu hình DB: " + e.getMessage(), e);
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
				System.out.println("✅ Kết nối đã đóng.");
			} catch (SQLException e) {
				System.out.println("❌ Lỗi khi đóng kết nối: " + e.getMessage());
			}
		}
	}

	public static void main(String[] args) {
		try (Connection conn = getConnection()) {
			if (conn != null && !conn.isClosed()) {
				System.out.println("✅ Kết nối thành công đến SQL Server!");
			}
		} catch (SQLException e) {
			System.out.println("❌ Lỗi kết nối: " + e.getMessage());
		}
	}
}
