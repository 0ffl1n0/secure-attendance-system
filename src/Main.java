import gui.DashboardUI;
import network.StudentWebServer;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Apply Modern Light Theme
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            
            // Apply professional fonts
            Font uiFont = new Font("Segoe UI", Font.PLAIN, 14);
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
            UIManager.put("Label.font", uiFont);
            UIManager.put("TextField.font", uiFont);
            UIManager.put("TextArea.font", uiFont);
            UIManager.put("ComboBox.font", uiFont);
            UIManager.put("List.font", uiFont);
            UIManager.put("TitledBorder.font", new Font("Segoe UI", Font.BOLD, 14));
            
            UIManager.put("control", new Color(248, 250, 252));
            UIManager.put("info", new Color(248, 250, 252));
            UIManager.put("nimbusBase", new Color(30, 58, 138));
            UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
            UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
            UIManager.put("nimbusFocus", new Color(37, 99, 235));
            UIManager.put("nimbusGreen", new Color(34, 197, 94));
            UIManager.put("nimbusInfoBlue", new Color(37, 99, 235));
            UIManager.put("nimbusLightBackground", new Color(248, 250, 252));
            UIManager.put("nimbusOrange", new Color(191, 98, 4));
            UIManager.put("nimbusRed", new Color(239, 68, 68));
            UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
            UIManager.put("nimbusSelectionBackground", new Color(37, 99, 235));
            UIManager.put("text", new Color(30, 58, 138));
            UIManager.put("Panel.background", new Color(248, 250, 252));
            UIManager.put("Label.foreground", new Color(30, 58, 138));
            UIManager.put("Button.background", new Color(37, 99, 235));
            UIManager.put("Button.foreground", new Color(255, 255, 255));
            UIManager.put("TextField.background", new Color(255, 255, 255));
            UIManager.put("TextField.foreground", new Color(30, 58, 138));
            UIManager.put("TextArea.background", new Color(255, 255, 255));
            UIManager.put("TextArea.foreground", new Color(30, 58, 138));
            UIManager.put("ComboBox.background", new Color(255, 255, 255));
            UIManager.put("ComboBox.foreground", new Color(30, 58, 138));
            UIManager.put("List.background", new Color(255, 255, 255));
            UIManager.put("List.foreground", new Color(30, 58, 138));
            UIManager.put("TitledBorder.titleColor", new Color(30, 58, 138));
            UIManager.put("ScrollPane.background", new Color(248, 250, 252));
        } catch (Exception e) {
            e.printStackTrace();
        }

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
