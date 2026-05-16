package core;

import java.security.SecureRandom;
import java.util.Base64;

public class QRTokenGenerator {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private String currentToken;
    private long tokenCreationTime;
    private static final long TOKEN_VALIDITY_MS = 30000; // 30 seconds

    public String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        currentToken = base64Encoder.encodeToString(randomBytes);
        tokenCreationTime = System.currentTimeMillis();
        return currentToken;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public boolean isTokenValid(String token) {
        if (currentToken == null || !currentToken.equals(token)) {
            return false;
        }
        long currentTime = System.currentTimeMillis();
        return (currentTime - tokenCreationTime) <= TOKEN_VALIDITY_MS;
    }
}
