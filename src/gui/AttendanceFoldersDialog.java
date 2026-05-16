package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class AttendanceFoldersDialog extends JDialog {
    public AttendanceFoldersDialog(JFrame parent) {
        super(parent, "Attendance Folders & Excluded Students", true);
        setSize(500, 300);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel gridPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        gridPanel.setBorder(BorderFactory.createTitledBorder("Open Group Folders"));

        String[] folders = { "A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4", "SecA", "SecB" };
        for (String folder : folders) {
            JButton btn = new JButton("Open " + folder);
            btn.setBackground(UIManager.getColor("Button.background"));
            btn.addActionListener(e -> openFolder("attendance_data/" + folder));
            gridPanel.add(btn);
        }

        panel.add(gridPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton excludedBtn = new JButton("View Excluded Students (> 2 Absences)");
        excludedBtn.setBackground(new Color(169, 46, 34)); // Reddish color for emphasis
        excludedBtn.setForeground(Color.WHITE);
        excludedBtn.addActionListener(e -> openFile("excluded_students.txt"));
        bottomPanel.add(excludedBtn);

        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
    }

    private void openFolder(String path) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Desktop.getDesktop().open(dir);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not open folder: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openFile(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            Desktop.getDesktop().open(file);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not open file: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
