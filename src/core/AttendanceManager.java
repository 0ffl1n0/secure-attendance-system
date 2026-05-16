package core;

import models.Session;
import models.Student;

import java.util.HashMap;
import java.util.Map;

public class AttendanceManager {
    private static AttendanceManager instance;

    private Map<String, Student> studentDatabase;
    private Session activeSession;

    public final QRTokenGenerator qrTokenGenerator;

    private AttendanceManager() {
        studentDatabase = new HashMap<>();
        qrTokenGenerator = new QRTokenGenerator();
        loadMockStudents();
    }

    public static AttendanceManager getInstance() {
        if (instance == null) {
            instance = new AttendanceManager();
        }
        return instance;
    }

    private void loadMockStudents() {
        studentDatabase.put("001", new Student("001", "AIT AMER MEZIANE HOCINE", "ait.amer@student.edu", "A2", "SecA"));
        studentDatabase.put("002",
                new Student("002", "SAADI MOHAMMED MONCIF", "saadi.mohammed@student.edu", "B1", "SecB"));
        studentDatabase.put("003",
                new Student("003", "BOUCENNA MOHAMMED ANES", "boucenna.mohammed@student.edu", "A1", "SecA"));
        studentDatabase.put("004",
                new Student("004", "ZENGLA MOHAMMED ABDELILLAH", "zengla.mohammed@student.edu", "B1", "SecB"));
        studentDatabase.put("005", new Student("005", "selah abdelhak", "selah.abdelhak@student.edu", "B1", "SecB"));
        studentDatabase.put("006",
                new Student("006", "lahacani mohammed reda", "lahacani.mohammed@student.edu", "B1", "SecB"));
        studentDatabase.put("007", new Student("007", "HEDDADI RABEH", "heddadi.rabeh@student.edu", "A1", "SecA"));
        studentDatabase.put("008", new Student("008", "TOUMI YACINE", "toumi.yacine@student.edu", "A1", "SecA"));
        studentDatabase.put("009",
                new Student("009", "REGHDA MOHAMMED ZAKARIA", "reghda.mohammed@student.edu", "A1", "SecA"));
        studentDatabase.put("010",
                new Student("010", "kassoul mohammed ali", "kassoul.mohammed@student.edu", "A2", "SecA"));

        loadAbsences();
    }

    private void loadAbsences() {
        java.io.File file = new java.io.File("absences.txt");
        if (file.exists()) {
            try (java.util.Scanner scanner = new java.util.Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String[] parts = scanner.nextLine().split(",");
                    if (parts.length == 3) {
                        Student s = studentDatabase.get(parts[0]);
                        if (s != null) {
                            s.setAbsences(parts[1], Integer.parseInt(parts[2]));
                        }
                    } else if (parts.length == 2) {
                        Student s = studentDatabase.get(parts[0]);
                        if (s != null) {
                            s.setAbsences("Object Oriented Programming", Integer.parseInt(parts[1]));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveAbsences() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter("absences.txt"))) {
            for (Student s : studentDatabase.values()) {
                for (java.util.Map.Entry<String, Integer> entry : s.getAbsencesMap().entrySet()) {
                    if (entry.getValue() > 0) {
                        writer.println(s.getId() + "," + entry.getKey() + "," + entry.getValue());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Student getStudent(String studentId) {
        return studentDatabase.get(studentId);
    }

    public void addStudent(Student student) {
        studentDatabase.put(student.getId(), student);
    }

    public void removeStudent(String studentId) {
        studentDatabase.remove(studentId);
    }

    public java.util.Collection<Student> getAllStudents() {
        return studentDatabase.values();
    }

    public void startSession(Session session) {
        this.activeSession = session;
    }

    public Session getActiveSession() {
        return activeSession;
    }

    public void endSession() {
        if (activeSession != null) {
            processAbsences(activeSession);
            this.activeSession = null;
        }
    }

    private void processAbsences(Session session) {
        java.io.File excludedFile = new java.io.File("excluded_students.txt");
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(excludedFile, true))) {
            String currentModule = session.getModule();
            for (Student s : studentDatabase.values()) {
                if (session.isValidStudentForSession(s)) {
                    if (!session.getAttendedStudentIds().contains(s.getId())) {
                        s.addAbsence(currentModule);
                        if (s.getAbsences(currentModule) == 3) {
                            writer.println("ID: " + s.getId() + " | Name: " + s.getName() + " | Group: " + s.getGroup()
                                    + " | Section: " + s.getSection() + " | Module: " + currentModule
                                    + " | Reached 3 Absences");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveAbsences();
    }
}
