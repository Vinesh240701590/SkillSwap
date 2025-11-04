import java.util.*;
import java.sql.*;

/*
  Robust Main.java for SkillSwap (BlueJ-friendly console)
  - Reads menu input as lines and parses integers (avoids Scanner nextInt pitfalls)
  - Uses try-with-resources for DB resources
  - Expects Database.connect() to return a java.sql.Connection
*/

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===================================");
        System.out.println("     Welcome to SkillSwap App      ");
        System.out.println("===================================");

        while (true) {
            showMenu();
            int choice = readLineThenParseInt("Choose an option: ");
            switch (choice) {
                case 1 -> addUser();
                case 2 -> addSkill();
                case 3 -> viewUsers();
                case 4 -> viewSkills();
                case 5 -> {
                    System.out.println("Exiting... Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice! Enter a number 1-5.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add User");
        System.out.println("2. Add Skill");
        System.out.println("3. View All Users");
        System.out.println("4. View All Skills");
        System.out.println("5. Exit");
    }

    // Read a full line and parse to int; returns -1 on bad parse
    private static int readLineThenParseInt(String prompt) {
        System.out.print(prompt);
        String line = sc.nextLine();
        if (line == null || line.trim().isEmpty()) return -1;
        try {
            return Integer.parseInt(line.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addUser() {
        try {
            System.out.print("Enter name: ");
            String name = sc.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Aborting add.");
                return;
            }

            System.out.print("Enter email: ");
            String email = sc.nextLine().trim();
            if (email.isEmpty()) {
                System.out.println("Email cannot be empty. Aborting add.");
                return;
            }

            String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
            try (Connection conn = Database.connect();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                int rows = ps.executeUpdate();
                if (rows > 0) System.out.println("✅ User added successfully!");
                else System.out.println("❌ Insert reported 0 rows.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void addSkill() {
        try {
            System.out.print("Enter skill name: ");
            String skillName = sc.nextLine().trim();
            if (skillName.isEmpty()) {
                System.out.println("Skill name cannot be empty. Aborting add.");
                return;
            }

            System.out.print("Enter description: ");
            String desc = sc.nextLine().trim();

            String sql = "INSERT INTO skills (skill_name, description) VALUES (?, ?)";
            try (Connection conn = Database.connect();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, skillName);
                ps.setString(2, desc);
                int rows = ps.executeUpdate();
                if (rows > 0) System.out.println("✅ Skill added successfully!");
                else System.out.println("❌ Insert reported 0 rows.");
            }
        } catch (SQLException e) {
            System.out.println("Error adding skill: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void viewUsers() {
        String sql = "SELECT id, name, email FROM users ORDER BY id";
        try (Connection conn = Database.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- All Users ---");
            boolean any = false;
            while (rs.next()) {
                any = true;
                System.out.println("ID: " + rs.getInt("id") +
                                   " | Name: " + rs.getString("name") +
                                   " | Email: " + rs.getString("email"));
            }
            if (!any) System.out.println("[No users found]");
        } catch (SQLException e) {
            System.out.println("Error fetching users: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void viewSkills() {
        String sql = "SELECT id, skill_name, description FROM skills ORDER BY id";
        try (Connection conn = Database.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n--- All Skills ---");
            boolean any = false;
            while (rs.next()) {
                any = true;
                System.out.println("ID: " + rs.getInt("id") +
                                   " | Skill: " + rs.getString("skill_name") +
                                   " | Desc: " + rs.getString("description"));
            }
            if (!any) System.out.println("[No skills found]");
        } catch (SQLException e) {
            System.out.println("Error fetching skills: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}
