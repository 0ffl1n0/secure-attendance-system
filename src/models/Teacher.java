package models;

// ============================================================
//  AUTHOR  : Zaki
//  FILE    : Teacher.java
//  ABOUT   : Concrete subclass of User representing a teacher.
//            Adds a department attribute and overrides getRole()
//            returning "Teacher". Used to identify session owners.
// ============================================================
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
