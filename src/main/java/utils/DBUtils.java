package utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Lớp tiện ích DBUtils quản lý kết nối cơ sở dữ liệu theo mẫu Singleton.
 * 
 * <p>
 * Đọc thông tin cấu hình từ file {@code dbConfig.properties} trong classpath và tạo
 * một kết nối duy nhất (Singleton) tới cơ sở dữ liệu. Kết nối này sẽ được tái sử dụng
 * trong toàn bộ vòng đời của ứng dụng.
 * </p>
 *
 */
public class DBUtils {
    /** URL kết nối JDBC */
    private static String URL;
    /** Tên người dùng DB */
    private static String USERNAME;
    /** Mật khẩu DB */
    private static String PASSWORD;
    /** Tên server DB */
    private static String SERVERNAME;
    /** Cổng DB */
    private static String PORT;
    /** Tên cơ sở dữ liệu */
    private static String DATABASENAME;

    /** Singleton Connection duy nhất */
    private static Connection connection;

    // Khối static initializer: đọc file cấu hình và load driver JDBC
    static {
        try (InputStream input = DBUtils.class.getClassLoader().getResourceAsStream("dbConfig.properties")) {
            if (input == null) {
                throw new RuntimeException("Không tìm thấy file cấu hình dbConfig.properties");
            }

            Properties prop = new Properties();
            prop.load(input);

            SERVERNAME = prop.getProperty("db.serverName");
            PORT = prop.getProperty("db.port", "1433");
            DATABASENAME = prop.getProperty("db.databaseName");
            USERNAME = prop.getProperty("db.username");
            PASSWORD = prop.getProperty("db.password");

            // Tạo URL JDBC
            URL = String.format("jdbc:sqlserver://%s:%s;databaseName=%s", SERVERNAME, PORT, DATABASENAME);

            // Load driver JDBC
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Lỗi khi load cấu hình DB: " + e.getMessage(), e);
        }
    }

    /**
     * Lấy kết nối tới cơ sở dữ liệu theo mẫu Singleton.
     * <p>
     * Nếu kết nối chưa tồn tại hoặc đã bị đóng, tạo một kết nối mới.
     * </p>
     *
     * @return {@link Connection} đối tượng kết nối duy nhất
     * @throws SQLException nếu có lỗi khi tạo kết nối
     */
    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        return connection;
    }
}
