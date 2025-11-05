import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SkillSwapApp extends JFrame {
    private JTextField nameField, emailField, skillField, userIdField;
    private JTextArea outputArea;
    private Connection conn;

    public SkillSwapApp() {
        setTitle("SkillSwap Platform");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Connect to database ---
        conn = connectDatabase();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Database connection failed!");
            System.exit(1);
        }

        // --- Top Panel (Inputs) ---
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("User & Skill Details"));

        inputPanel.add(new JLabel("User Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Skill Name:"));
        skillField = new JTextField();
        inputPanel.add(skillField);

        inputPanel.add(new JLabel("User ID (for skill):"));
        userIdField = new JTextField();
        inputPanel.add(userIdField);

        add(inputPanel, BorderLayout.NORTH);

        // --- Center (Output Area) ---
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        // --- Bottom Panel (Buttons) ---
        JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        JButton addUserBtn = new JButton("Add User");
        JButton addSkillBtn = new JButton("Add Skill");
        JButton viewUsersBtn = new JButton("View Users");
        JButton viewSkillsBtn = new JButton("View Skills");

        buttonPanel.add(addUserBtn);
        buttonPanel.add(addSkillBtn);
        buttonPanel.add(viewUsersBtn);
        buttonPanel.add(viewSkillsBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        // --- Button Actions ---
        addUserBtn.addActionListener(e -> addUser());
        addSkillBtn.addActionListener(e -> addSkill());
        viewUsersBtn.addActionListener(e -> viewUsers());
        viewSkillsBtn.addActionListener(e -> viewSkills());
    }

    // ---------------- Database Connection ----------------
    private Connection connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/skillswap"; // change DB name if needed
            String user = "root";  // your MySQL username
            String password = "root123";  // your MySQL password
            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ---------------- Add User ----------------
    private void addUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and email!");
            return;
        }
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO users (name, email) VALUES (?, ?)")) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
            outputArea.append("✅ Added user: " + name + "\n");
            nameField.setText("");
            emailField.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding user: " + e.getMessage());
        }
    }

    // ---------------- Add Skill ----------------
    private void addSkill() {
        String skill = skillField.getText();
        String userIdText = userIdField.getText();
        if (skill.isEmpty() || userIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter both skill name and user ID!");
            return;
        }
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO skills (skill_name, user_id) VALUES (?, ?)")) {
            stmt.setString(1, skill);
            stmt.setInt(2, Integer.parseInt(userIdText));
            stmt.executeUpdate();
            outputArea.append("✅ Added skill: " + skill + "\n");
            skillField.setText("");
            userIdField.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding skill: " + e.getMessage());
        }
    }

    // ---------------- View Users ----------------
    private void viewUsers() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {

            outputArea.append("\n--- Users ---\n");
            while (rs.next()) {
                outputArea.append("ID: " + rs.getInt("user_id") +
                                  ", Name: " + rs.getString("name") +
                                  ", Email: " + rs.getString("email") + "\n");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching users: " + e.getMessage());
        }
    }

    // ---------------- View Skills ----------------
    private void viewSkills() {
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT s.skill_id, s.skill_name, u.name AS owner FROM skills s JOIN users u ON s.user_id = u.user_id")) {

            outputArea.append("\n--- Skills ---\n");
            while (rs.next()) {
                outputArea.append("Skill ID: " + rs.getInt("skill_id") +
                                  ", Skill: " + rs.getString("skill_name") +
                                  ", Owner: " + rs.getString("owner") + "\n");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching skills: " + e.getMessage());
        }
    }

    // ---------------- Main ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SkillSwapApp().setVisible(true));
    }
}
