package models;

// ============================================================
//  AUTHOR  : Zaki
//  FILE    : User.java
//  ABOUT   : Abstract base class for all users in the system.
//            Defines common attributes (id, name, email) and
//            enforces the polymorphic getRole() method that
//            every subclass (Student, Teacher) must implement.
// ============================================================
public abstract class User {
    protected String id;
    protected String name;
    protected String email;

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Polymorphic method
    public abstract String getRole();
}
