package models;

import java.util.Arrays;
import java.util.List;

/**
 * Represents a TD session.
 * Allows selection for 4 distinct groups.
 */
public class TDSession extends Session {
    private List<String> allowedGroups;

    public TDSession(String sessionId, String teacherId, double latitude, double longitude, String module, List<String> allowedGroups) {
        super(sessionId, teacherId, latitude, longitude, module);
        this.allowedGroups = allowedGroups;
    }

    @Override
    public boolean isValidStudentForSession(Student student) {
        return allowedGroups.contains(student.getGroup());
    }

    @Override
    public String getSessionType() {
        return "TD";
    }

    public List<String> getAllowedGroups() {
        return allowedGroups;
    }
}
