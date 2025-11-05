import java.sql.*;

public class User {
    // Add a new user to the database
    public static void addUser(Connection conn, String name, String email) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("✅ User added successfully!");
        } catch (SQLException e) {
            System.out.println("⚠️ Error adding user: " + e.getMessage());
        }
    }

    // Retrieve all users
    public static void getAllUsers(Connection conn) {
        String sql = "SELECT * FROM users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Users ---");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("user_id") +
                                   ", Name: " + rs.getString("name") +
                                   ", Email: " + rs.getString("email"));
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Error retrieving users: " + e.getMessage());
        }
    }
}
