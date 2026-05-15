package security;

/**
 * Custom exception thrown when a security violation is detected
 * (e.g., VPN usage, wrong geolocation, device sharing).
 */
public class SecurityViolationException extends Exception {
    public SecurityViolationException(String message) {
        super(message);
    }
}
