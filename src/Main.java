import gui.DashboardUI;
import network.StudentWebServer;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // Apply Modern Dark Mode Theme
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.put("control", new Color(30, 30, 30));
            UIManager.put("info", new Color(30, 30, 30));
            UIManager.put("nimbusBase", new Color(18, 30, 49));
            UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
            UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
            UIManager.put("nimbusFocus", new Color(115, 164, 209));
            UIManager.put("nimbusGreen", new Color(176, 179, 50));
            UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
            UIManager.put("nimbusLightBackground", new Color(18, 30, 49));
            UIManager.put("nimbusOrange", new Color(191, 98, 4));
            UIManager.put("nimbusRed", new Color(169, 46, 34));
            UIManager.put("nimbusSelectedText", new Color(255, 255, 255));
            UIManager.put("nimbusSelectionBackground", new Color(104, 93, 156));
            UIManager.put("text", new Color(230, 230, 230));
            UIManager.put("Panel.background", new Color(30, 30, 30));
            UIManager.put("Label.foreground", new Color(230, 230, 230));
            UIManager.put("Button.background", new Color(60, 63, 65));
            UIManager.put("Button.foreground", new Color(230, 230, 230));
            UIManager.put("TextField.background", new Color(43, 43, 43));
            UIManager.put("TextField.foreground", new Color(230, 230, 230));
            UIManager.put("TextArea.background", new Color(43, 43, 43));
            UIManager.put("TextArea.foreground", new Color(230, 230, 230));
            UIManager.put("ComboBox.background", new Color(43, 43, 43));
            UIManager.put("ComboBox.foreground", new Color(230, 230, 230));
            UIManager.put("List.background", new Color(43, 43, 43));
            UIManager.put("List.foreground", new Color(230, 230, 230));
            UIManager.put("TitledBorder.titleColor", new Color(200, 200, 200));
            UIManager.put("ScrollPane.background", new Color(43, 43, 43));
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
