package models;

/**
 * Represents a teacher in the system who can manage sessions.
 */
public class Teacher extends User {
    private String department;

    public Teacher(String id, String name, String email, String department) {
        super(id, name, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String getRole() {
        return "Teacher";
    }
}
