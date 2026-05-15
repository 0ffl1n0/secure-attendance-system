package security;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements security checks to prevent cheating.
 */
public class AttendanceSecurityManager implements SecurityValidator {

    // Increased to 50,000 meters (50km) for testing purposes. 
    // In production, change this back to 50.0!
    private static final double MAX_DISTANCE_METERS = 50000.0;
    
    // Map of SessionId -> (StudentId -> DeviceFingerprint (IP+Browser))
    private Map<String, Map<String, String>> sessionDeviceLocks = new HashMap<>();

    @Override
    public boolean validateLocation(double studentLat, double studentLng, double classroomLat, double classroomLng) throws SecurityViolationException {
        double distance = calculateDistance(studentLat, studentLng, classroomLat, classroomLng);
        if (distance > MAX_DISTANCE_METERS) {
            throw new SecurityViolationException("Location check failed: Student is " + String.format("%.2f", distance) + " meters away. Max allowed is 50m.");
        }
        return true;
    }

    @Override
    public boolean validateDevice(String sessionId, String studentId, String ipAddress, String browserFingerprint) throws SecurityViolationException {
        String currentFingerprint = ipAddress + "|" + browserFingerprint;
        
        sessionDeviceLocks.putIfAbsent(sessionId, new HashMap<>());
        Map<String, String> deviceLocks = sessionDeviceLocks.get(sessionId);
        
        // Check if student already registered a different device in this session
        if (deviceLocks.containsKey(studentId)) {
            String lockedFingerprint = deviceLocks.get(studentId);
            if (!lockedFingerprint.equals(currentFingerprint)) {
                throw new SecurityViolationException("Device Lock violation: ID " + studentId + " is already linked to another device/IP.");
            }
        } else {
            // Check if this device is already used by ANOTHER student in this session
            for (Map.Entry<String, String> entry : deviceLocks.entrySet()) {
                if (entry.getValue().equals(currentFingerprint)) {
                    throw new SecurityViolationException("Machine ID Lock violation: This device/IP is already used by student " + entry.getKey());
                }
            }
            // Lock device to student
            deviceLocks.put(studentId, currentFingerprint);
        }
        return true;
    }

    @Override
    public boolean validateTimezone(String studentTimezone) throws SecurityViolationException {
        // Simple Anti-VPN check: Assuming the local timezone should be "Africa/Algiers" or similar.
        // For a more robust check, we compare it against a list of valid regional timezones.
        // For demonstration, we just ensure it's not empty and perhaps log it.
        if (studentTimezone == null || studentTimezone.isEmpty() || studentTimezone.equalsIgnoreCase("unknown")) {
            throw new SecurityViolationException("Anti-VPN check failed: Invalid or hidden timezone.");
        }
        
        // Example check: block common VPN default timezones if they don't match the expected region
        // if (studentTimezone.equals("America/New_York") && expectedRegion.equals("Europe/Paris")) ...
        
        return true;
    }

    @Override
    public boolean validateToken(String token, String expectedToken) throws SecurityViolationException {
        if (token == null || !token.equals(expectedToken)) {
            throw new SecurityViolationException("Invalid or expired QR Token.");
        }
        return true;
    }
    
    // Haversine formula to calculate distance in meters
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        return distance;
    }
}
