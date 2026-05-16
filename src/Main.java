// ============================================================
//  AUTHOR  : Yacine
//  FILE    : Main.java
//  ABOUT   : Entry point of the entire application. Starts the
//            embedded HTTP web server on port 8080, then launches
//            the teacher dashboard GUI on the Swing event thread.
// ============================================================
import gui.DashboardUI;
import network.StudentWebServer;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Apply Modern Light Theme initially
        gui.ThemeManager.applyLightTheme();

        // Start Web Server
        try {
            StudentWebServer server = new StudentWebServer(8080);
            server.start();
        } catch (Exception e) {
            System.err.println("Failed to start web server: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        // Start GUI
        SwingUtilities.invokeLater(() -> {
            DashboardUI dashboard = new DashboardUI();
            dashboard.setVisible(true);
        });
    }
}
