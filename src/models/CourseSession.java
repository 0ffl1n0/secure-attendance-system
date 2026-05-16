package models;

import java.util.List;


public class CourseSession extends Session {
    private List<String> allowedSections;

    public CourseSession(String sessionId, String teacherId, double latitude, double longitude, String module, List<String> allowedSections) {
        super(sessionId, teacherId, latitude, longitude, module);
        this.allowedSections = allowedSections;
    }

    @Override
    public boolean isValidStudentForSession(Student student) {
        return allowedSections.contains(student.getSection());
    }

    @Override
    public String getSessionType() {
        return "Course";
    }

    public List<String> getAllowedSections() {
        return allowedSections;
    }
}
