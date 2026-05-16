package core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLogger {
    private static final String LOG_FILE = "audit_log.txt";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void log(String studentId, String ipAddress, String action, String reason) {
        String timestamp = dtf.format(LocalDateTime.now());
        String logEntry = String.format("[%s] ID: %s | IP: %s | Action: %s | Reason: %s\n",
                timestamp,
                studentId != null ? studentId : "UNKNOWN",
                ipAddress != null ? ipAddress : "UNKNOWN",
                action,
                reason);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(logEntry);
            System.out.print(logEntry);
        } catch (IOException e) {
            System.err.println("Failed to write to audit log: " + e.getMessage());
        }
    }
}
