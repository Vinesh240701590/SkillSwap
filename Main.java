import java.util.Scanner;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection conn = null;

        try {
            conn = Database.connect();
            if (conn != null) {
                System.out.println("✅ Connected to MySQL successfully!");
            } else {
                System.out.println("❌ Database connection failed!");
                return;
            }

            while (true) {
                System.out.println("\n=== SkillSwap Menu ===");
                System.out.println("1. Add User");
                System.out.println("2. Add Skill");
                System.out.println("3. View All Users");
                System.out.println("4. View All Skills");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();
                sc.nextLine(); // clear input buffer

                switch (choice) {
                    case 1:
                        System.out.print("Enter user name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter email: ");
                        String email = sc.nextLine();

                        User.addUser(conn, name, email);
                        break;

                    case 2:
                        System.out.print("Enter skill name: ");
                        String skillName = sc.nextLine();
                        System.out.print("Enter user ID (owner): ");
                        int userId = sc.nextInt();

                        Skill.addSkill(conn, skillName, userId);
                        break;

                    case 3:
                        User.getAllUsers(conn);
                        break;

                    case 4:
                        Skill.getAllSkills(conn);
                        break;

                    case 5:
                        System.out.println("👋 Exiting program. Goodbye!");
                        sc.close();
                        return;

                    default:
                        System.out.println("❗ Invalid choice, try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println("⚠️ SQL Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("⚠️ Unexpected Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

