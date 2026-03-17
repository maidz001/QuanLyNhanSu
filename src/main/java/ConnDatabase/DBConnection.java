package ConnDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        // Ưu tiên biến môi trường (Railway), fallback về local
        String host     = System.getenv("MYSQLHOST")     != null ? System.getenv("MYSQLHOST")     : "localhost";
        String port     = System.getenv("MYSQLPORT")     != null ? System.getenv("MYSQLPORT")     : "3306";
        String db       = System.getenv("MYSQLDATABASE") != null ? System.getenv("MYSQLDATABASE") : "quan_ly_nhan_su";
        String user     = System.getenv("MYSQLUSER")     != null ? System.getenv("MYSQLUSER")     : "root";
        String password = System.getenv("MYSQLPASSWORD") != null ? System.getenv("MYSQLPASSWORD") : "12345678";

        URL      = "jdbc:mysql://" + host + ":" + port + "/" + db + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";
        USER     = user;
        PASSWORD = password;
        System.out.println("HOST: " + host);
        System.out.println("PORT: " + port);
        System.out.println("DB: " + db);
        System.out.println("USER: " + user);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
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