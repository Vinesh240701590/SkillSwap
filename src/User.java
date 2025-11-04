public class User {
    private int id;
    private String name;
    private String email;
    private String skillOffered;
    private String skillWanted;

    public User(int id, String name, String email, String skillOffered, String skillWanted) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.skillOffered = skillOffered;
        this.skillWanted = skillWanted;
    }

    // Constructor without ID (for new users before saving to DB)
    public User(String name, String email, String skillOffered, String skillWanted) {
        this(0, name, email, skillOffered, skillWanted);
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getSkillOffered() {
        return skillOffered;
    }
    public String getSkillWanted() {
        return skillWanted;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }
    public void setSkillOffered(String skillOffered) {
        this.skillOffered = skillOffered;
    }
    public void setSkillWanted(String skillWanted) {
        this.skillWanted = skillWanted;
    }

    @Override
    public String toString() {
        return "User ID: " + id + 
               "\nName: " + name + 
               "\nEmail: " + email + 
               "\nOffers: " + skillOffered + 
               "\nWants: " + skillWanted;
    }
}
