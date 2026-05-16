package models;

// ============================================================
//  AUTHOR  : Zaki
//  FILE    : Student.java
//  ABOUT   : Concrete subclass of User representing a student.
//            Adds group, section, and per-module absence tracking.
//            Inherits and overrides getRole() returning "Student".
// ============================================================
public class Student extends User {
    private String group;
    private String section;
    private java.util.Map<String, Integer> absencesByModule;

    public Student(String id, String name, String email, String group, String section) {
        super(id, name, email);
        this.group = group;
        this.section = section;
        this.absencesByModule = new java.util.HashMap<>();
    }

    public int getAbsences(String module) {
        return absencesByModule.getOrDefault(module, 0);
    }

    public void setAbsences(String module, int absences) {
        this.absencesByModule.put(module, absences);
    }

    public void addAbsence(String module) {
        this.absencesByModule.put(module, getAbsences(module) + 1);
    }

    public java.util.Map<String, Integer> getAbsencesMap() {
        return absencesByModule;
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
