package ConnDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        // Lấy biến môi trường từ Railway (KHÔNG fallback)
        String host     = System.getenv("MYSQLHOST");
        String port     = System.getenv("MYSQLPORT");
        String db       = System.getenv("MYSQLDATABASE");
        String user     = System.getenv("MYSQLUSER");
        String password = System.getenv("MYSQLPASSWORD");

        // Check lỗi nếu thiếu biến môi trường
        if (host == null || port == null || db == null || user == null || password == null) {
            throw new RuntimeException("❌ Thiếu biến môi trường MySQL trên Railway");
        }

        URL = "jdbc:mysql://" + host + ":" + port + "/" + db +
                "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

        USER = user;
        PASSWORD = password;

        // Log debug (xem trong Railway Logs)
        System.out.println("=== DB CONNECT INFO ===");
        System.out.println("HOST: " + host);
        System.out.println("PORT: " + port);
        System.out.println("DB: " + db);
        System.out.println("USER: " + user);
        System.out.println("=======================");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("❌ Không load được MySQL Driver", e);
        }
    }

    public static Connection layKetNoi() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void dongKetNoi(Connection conn) {
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }
}