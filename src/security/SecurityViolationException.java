package security;

// ============================================================
//  AUTHOR  : Zaki
//  FILE    : SecurityViolationException.java
//  ABOUT   : Custom checked exception thrown whenever a security
//            rule is violated (wrong location, VPN detected,
//            device sharing, or invalid QR token).
// ============================================================
public class SecurityViolationException extends Exception {
    public SecurityViolationException(String message) {
        super(message);
    }
}
