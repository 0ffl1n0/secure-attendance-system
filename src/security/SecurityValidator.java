package security;

/**
 * Interface defining security validation rules for attendance.
 */
public interface SecurityValidator {
    boolean validateLocation(double studentLat, double studentLng, double classroomLat, double classroomLng) throws SecurityViolationException;
    boolean validateDevice(String sessionId, String studentId, String ipAddress, String browserFingerprint) throws SecurityViolationException;
    boolean validateTimezone(String studentTimezone) throws SecurityViolationException;
    boolean validateToken(String token, String expectedToken) throws SecurityViolationException;
}
