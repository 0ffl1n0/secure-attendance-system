package models;

/**
 * Represents a student in the attendance system.
 * Inherits from User and adds student-specific attributes.
 */
public class Student extends User {
    private String group;
    private String section;
    private int absences;

    public Student(String id, String name, String email, String group, String section) {
        super(id, name, email);
        this.group = group;
        this.section = section;
        this.absences = 0;
    }

    public int getAbsences() {
        return absences;
    }

    public void setAbsences(int absences) {
        this.absences = absences;
    }

    public String getGroup() {
        return group;
    }

    public String getSection() {
        return section;
    }

    @Override
    public String getRole() {
        return "Student";
    }
}
