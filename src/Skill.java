public class Skill {
    private int id;
    private String name;
    private String category;
    private String description;

    public Skill(int id, String name, String category, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    // Constructor for new skills (no id yet)
    public Skill(String name, String category, String description) {
        this(0, name, category, description);
    }

    // Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCategory() {
        return category;
    }
    public String getDescription() {
        return description;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Skill ID: " + id +
               "\nName: " + name +
               "\nCategory: " + category +
               "\nDescription: " + description;
    }
}
