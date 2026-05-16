package security;

// ============================================================
//  AUTHOR  : Zaki
//  FILE    : SecurityValidator.java
//  ABOUT   : Interface that defines the 4 security rules every
//            attendance check must pass: location, device lock,
//            timezone (anti-VPN), and QR token validation.
//            Implemented by AttendanceSecurityManager.
// ============================================================
public interface SecurityValidator {
    boolean validateLocation(double studentLat, double studentLng, double classroomLat, double classroomLng) throws SecurityViolationException;
    boolean validateDevice(String sessionId, String studentId, String ipAddress, String browserFingerprint) throws SecurityViolationException;
    boolean validateTimezone(String studentTimezone) throws SecurityViolationException;
    boolean validateToken(String token, String expectedToken) throws SecurityViolationException;
}
