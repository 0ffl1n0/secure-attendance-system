package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class defining an attendance session.
 * Demonstrates Polymorphism and Abstraction.
 */
public abstract class Session {
    protected String sessionId;
    protected String teacherId;
    protected long startTime;
    protected double latitude;
    protected double longitude;
    protected String module;
    protected List<String> attendedStudentIds;

    public Session(String sessionId, String teacherId, double latitude, double longitude, String module) {
        this.sessionId = sessionId;
        this.teacherId = teacherId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.module = module;
        this.startTime = System.currentTimeMillis();
        this.attendedStudentIds = new ArrayList<>();
    }

    public String getModule() {
        return module;
    }

    public String getSessionId() {
        return sessionId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public List<String> getAttendedStudentIds() {
        return attendedStudentIds;
    }

    public void addAttendance(String studentId) {
        if (!attendedStudentIds.contains(studentId)) {
            attendedStudentIds.add(studentId);
        }
    }

    /**
     * Polymorphic method to validate if a student belongs to this session type.
     */
    public abstract boolean isValidStudentForSession(Student student);

    public abstract String getSessionType();
}
