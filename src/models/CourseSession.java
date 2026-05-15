package models;

import java.util.List;

/**
 * Represents a Course session.
 * Allows selection for 2 sections.
 */
public class CourseSession extends Session {
    private List<String> allowedSections;

    public CourseSession(String sessionId, String teacherId, double latitude, double longitude, List<String> allowedSections) {
        super(sessionId, teacherId, latitude, longitude);
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
