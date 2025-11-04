import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/skillswap";
    private static final String USER = "root";
    private static final String PASSWORD = "root123"; // <-- change this

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Connected to MySQL successfully!");
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed!");
            System.out.println("Error: " + e.getMessage());
        }
        return conn;
    }

    public static void main(String[] args) {
        connect(); // Test the connection
    }
}

