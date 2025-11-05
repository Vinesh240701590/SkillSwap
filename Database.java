import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/skillswap";
    private static final String USER = "root"; // change if different
    private static final String PASSWORD = "root123"; // your actual password

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
