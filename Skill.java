import java.sql.*;

public class Skill {
    // Add a new skill linked to a user
    public static void addSkill(Connection conn, String skillName, int userId) {
        String sql = "INSERT INTO skills (skill_name, user_id) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, skillName);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            System.out.println("✅ Skill added successfully!");
        } catch (SQLException e) {
            System.out.println("⚠️ Error adding skill: " + e.getMessage());
        }
    }

    // Retrieve all skills
    public static void getAllSkills(Connection conn) {
        String sql = "SELECT s.id, s.skill_name, u.name AS owner FROM skills s JOIN users u ON s.user_id = u.id";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n--- Skills ---");
            while (rs.next()) {
                System.out.println("Skill ID: " + rs.getInt("id") +
                                   ", Skill: " + rs.getString("skill_name") +
                                   ", Owner: " + rs.getString("owner"));
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Error retrieving skills: " + e.getMessage());
        }
    }
}
