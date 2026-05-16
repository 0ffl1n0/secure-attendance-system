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
