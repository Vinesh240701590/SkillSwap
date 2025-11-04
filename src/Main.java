public class Main {
    public static void main(String[] args) {
        try {
            Database.getConnection();
            System.out.println("✅ Connected to MySQL successfully!");
        } catch (Exception e) {
            System.out.println("❌ Connection failed!");
            e.printStackTrace();
        }
    }
}
