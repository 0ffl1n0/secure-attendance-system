package gui;

import javax.swing.*;
import java.awt.*;

public class ThemeManager {
    public static boolean isDarkMode = false;

    public static void applyLightTheme() {
        isDarkMode = false;
        try {
            javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(new javax.swing.plaf.metal.DefaultMetalTheme());
            UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
            
            // Apply professional fonts
            Font uiFont = new Font("Segoe UI", Font.PLAIN, 14);
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
            UIManager.put("Label.font", uiFont);
            UIManager.put("TextField.font", uiFont);
            UIManager.put("TextArea.font", uiFont);
            UIManager.put("ComboBox.font", uiFont);
            UIManager.put("List.font", uiFont);
            UIManager.put("TitledBorder.font", new Font("Segoe UI", Font.BOLD, 14));
            
            UIManager.put("control", new javax.swing.plaf.ColorUIResource(248, 250, 252));
            UIManager.put("info", new javax.swing.plaf.ColorUIResource(248, 250, 252));
            UIManager.put("nimbusBase", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            UIManager.put("nimbusAlertYellow", new javax.swing.plaf.ColorUIResource(248, 187, 0));
            UIManager.put("nimbusDisabledText", new javax.swing.plaf.ColorUIResource(128, 128, 128));
            UIManager.put("nimbusFocus", new javax.swing.plaf.ColorUIResource(37, 99, 235));
            UIManager.put("nimbusGreen", new javax.swing.plaf.ColorUIResource(34, 197, 94));
            UIManager.put("nimbusInfoBlue", new javax.swing.plaf.ColorUIResource(37, 99, 235));
            UIManager.put("nimbusLightBackground", new javax.swing.plaf.ColorUIResource(248, 250, 252));
            UIManager.put("nimbusOrange", new javax.swing.plaf.ColorUIResource(191, 98, 4));
            UIManager.put("nimbusRed", new javax.swing.plaf.ColorUIResource(239, 68, 68));
            UIManager.put("nimbusSelectedText", new javax.swing.plaf.ColorUIResource(255, 255, 255));
            UIManager.put("nimbusSelectionBackground", new javax.swing.plaf.ColorUIResource(37, 99, 235));
            UIManager.put("text", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            
            UIManager.put("Panel.background", new javax.swing.plaf.ColorUIResource(248, 250, 252));
            UIManager.put("Label.foreground", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            UIManager.put("Button.background", new javax.swing.plaf.ColorUIResource(226, 232, 240));
            UIManager.put("Button.foreground", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            UIManager.put("controlText", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            UIManager.put("textText", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            UIManager.put("userText", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            UIManager.put("TextField.background", new javax.swing.plaf.ColorUIResource(255, 255, 255));
            UIManager.put("TextField.foreground", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            UIManager.put("TextArea.background", new javax.swing.plaf.ColorUIResource(255, 255, 255));
            UIManager.put("TextArea.foreground", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            UIManager.put("ComboBox.background", new javax.swing.plaf.ColorUIResource(255, 255, 255));
            UIManager.put("ComboBox.foreground", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            UIManager.put("List.background", new javax.swing.plaf.ColorUIResource(255, 255, 255));
            UIManager.put("List.foreground", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            UIManager.put("TitledBorder.titleColor", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            UIManager.put("ScrollPane.background", new javax.swing.plaf.ColorUIResource(248, 250, 252));
            UIManager.put("OptionPane.background", new javax.swing.plaf.ColorUIResource(248, 250, 252));
            UIManager.put("OptionPane.messageForeground", new javax.swing.plaf.ColorUIResource(30, 58, 138));
            
            updateAllWindows();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void applyDarkTheme() {
        isDarkMode = true;
        try {
            javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(new javax.swing.plaf.metal.DefaultMetalTheme());
            UIManager.setLookAndFeel(new javax.swing.plaf.metal.MetalLookAndFeel());
            
            // Apply professional fonts
            Font uiFont = new Font("Segoe UI", Font.PLAIN, 14);
            UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
            UIManager.put("Label.font", uiFont);
            UIManager.put("TextField.font", uiFont);
            UIManager.put("TextArea.font", uiFont);
            UIManager.put("ComboBox.font", uiFont);
            UIManager.put("List.font", uiFont);
            UIManager.put("TitledBorder.font", new Font("Segoe UI", Font.BOLD, 14));
            
            Color bg = new javax.swing.plaf.ColorUIResource(30, 30, 30);
            Color fg = new javax.swing.plaf.ColorUIResource(220, 220, 220);
            Color accent = new javax.swing.plaf.ColorUIResource(71, 85, 105);
            Color inputBg = new javax.swing.plaf.ColorUIResource(45, 45, 45);
            
            UIManager.put("control", bg);
            UIManager.put("info", bg);
            UIManager.put("nimbusBase", fg);
            UIManager.put("nimbusLightBackground", bg);
            UIManager.put("text", fg);
            
            UIManager.put("Panel.background", bg);
            UIManager.put("Label.foreground", fg);
            UIManager.put("Button.background", accent);
            UIManager.put("Button.foreground", new javax.swing.plaf.ColorUIResource(255, 255, 255));
            UIManager.put("controlText", fg);
            UIManager.put("textText", fg);
            UIManager.put("userText", fg);
            UIManager.put("TextField.background", inputBg);
            UIManager.put("TextField.foreground", fg);
            UIManager.put("TextArea.background", inputBg);
            UIManager.put("TextArea.foreground", fg);
            UIManager.put("ComboBox.background", inputBg);
            UIManager.put("ComboBox.foreground", fg);
            UIManager.put("List.background", inputBg);
            UIManager.put("List.foreground", fg);
            UIManager.put("TitledBorder.titleColor", fg);
            UIManager.put("ScrollPane.background", bg);
            UIManager.put("OptionPane.background", bg);
            UIManager.put("OptionPane.messageForeground", fg);
            
            updateAllWindows();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void toggleTheme() {
        if (isDarkMode) {
            applyLightTheme();
        } else {
            applyDarkTheme();
        }
    }

    private static void updateAllWindows() {
        for (Window window : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
            window.repaint();
        }
    }
}
