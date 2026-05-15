package core;

import models.Session;
import models.Student;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton managing application state.
 */
public class AttendanceManager {
    private static AttendanceManager instance;
    
    // In-memory data
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
        // Mocking Data Persistence (in a real app, this would load from a File or DB)
        studentDatabase.put("001", new Student("001", "Alice", "alice@test.com", "A1", "SecA"));
        studentDatabase.put("002", new Student("002", "Bob", "bob@test.com", "A2", "SecA"));
        studentDatabase.put("003", new Student("003", "Charlie", "charlie@test.com", "A3", "SecA"));
        studentDatabase.put("004", new Student("004", "Diana", "diana@test.com", "A4", "SecA"));
        studentDatabase.put("005", new Student("005", "Ethan", "ethan@test.com", "B1", "SecB"));
        studentDatabase.put("006", new Student("006", "Fiona", "fiona@test.com", "B2", "SecB"));
        studentDatabase.put("007", new Student("007", "George", "george@test.com", "B3", "SecB"));
        studentDatabase.put("008", new Student("008", "Hannah", "hannah@test.com", "B4", "SecB"));
        studentDatabase.put("009", new Student("009", "Ian", "ian@test.com", "A1", "SecA"));
        studentDatabase.put("010", new Student("010", "Julia", "julia@test.com", "B1", "SecB"));
        
        loadAbsences();
    }
    
    private void loadAbsences() {
        java.io.File file = new java.io.File("absences.txt");
        if (file.exists()) {
            try (java.util.Scanner scanner = new java.util.Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String[] parts = scanner.nextLine().split(",");
                    if (parts.length == 2) {
                        Student s = studentDatabase.get(parts[0]);
                        if (s != null) {
                            s.setAbsences(Integer.parseInt(parts[1]));
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
                if (s.getAbsences() > 0) {
                    writer.println(s.getId() + "," + s.getAbsences());
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
            for (Student s : studentDatabase.values()) {
                if (session.isValidStudentForSession(s)) {
                    if (!session.getAttendedStudentIds().contains(s.getId())) {
                        s.setAbsences(s.getAbsences() + 1);
                        if (s.getAbsences() == 3) {
                            writer.println("ID: " + s.getId() + " | Name: " + s.getName() + " | Group: " + s.getGroup() + " | Section: " + s.getSection() + " | Reached 3 Absences");
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
