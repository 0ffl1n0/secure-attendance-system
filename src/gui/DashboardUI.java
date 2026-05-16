// ============================================================
//  AUTHOR  : Yacine
//  FILE    : DashboardUI.java
//  ABOUT   : Main teacher desktop dashboard (Swing JFrame).
//            Allows starting/stopping sessions, selecting module,
//            session type (TD/Course) and group. Displays a
//            live-refreshing QR code every 30 seconds and shows
//            a real-time attendance log on the side.
// ============================================================
package gui;

import core.AttendanceManager;
import models.CourseSession;
import models.Session;
import models.TDSession;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class DashboardUI extends JFrame {

    private JLabel qrLabel;
    private JLabel statusLabel;
    private JTextArea attendanceLog;
    private JTextField ngrokUrlField;
    private JComboBox<String> sessionTypeCombo;
    private JComboBox<String> moduleCombo;
    private JComboBox<String> groupsCombo;
    private Timer qrTimer;
    private Timer uiUpdateTimer;

    private static final double CLASS_LAT = 36.752887;
    private static final double CLASS_LNG = 3.042048;

    public DashboardUI() {
        setTitle("Attendance Dashboard (Teacher)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(15, 15));
        ((JPanel)getContentPane()).setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Global outer padding

        initUI();
    }

    private void  initUI() {
        JPanel controlPanel = new JPanel(new GridLayout(12, 1, 10, 10));
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Session Controls"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        controlPanel.add(new JLabel("Ngrok Base URL (Required for 
                kUrlField = new JTextField("https://clamp-zeppelin-pa
                rolPanel.add(ngrokUrlField);
        controlPanel.add(new JLabel("Module:"));
        moduleCombo = new JComboBox<>(new String[]{
            "Object Oriented Programming",
            "Mathematical Logic",
            "Mathematical analysis4",
            "Optics and electromagnetic waves",
            "Introduction to Systems Information", 
                "Probability and Statistic2"
                
                rolPanel.add(moduleCombo)
                
                rolPanel.add(new JLabel("Session Type:
                ionTypeCombo = new JComboBox<>(new String[]{"TD", "Course"});
        controlPanel.add(sessionTypeCombo);

        controlPanel.add(new JLabel("Select Groups/Sections:"));
        DefaultComboBoxModel<String> groupsModel = new DefaultComboBoxModel<>();
        groupsCombo = new JComboBox<>(groupsModel);   
        controlPanel.add(groupsCombo);

        sessionTypeCombo.addActionListener(e -> {
            groupsModel.removeAllElements();
            if ("TD".equals(sessionTypeCombo.getSelectedItem())) {
                groupsModel.addElement("A1"); groupsModel.addElement("A2");
                groupsModel.addElement("A3"); groupsModel.addElement("A4");
                groupsModel.addElement("B1"); groupsModel.addElement("B2");
                groupsModel.addElement("B3"); groupsModel.addElement("B4");
            } else {
                groupsModel.addElement("SecA"
                ;
                groupsModel.addElement("SecB"
                ;
            }
                
        });
                
        sessionTypeCombo.setSelectedIndex(0); 

        JButton manageStudentsBtn = new JButton("Manage Students");
        manageStudentsBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        manageStudentsBtn.addActionListener(e -> new StudentManagerDialog(this).setVisible(true));
        controlPanel.add(manageStudentsBtn);

        JButton attendanceFoldersBtn = new JButton("Attendance / Folders");
        attendanceFoldersBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        attendanceFoldersBtn.addActionListener(e -> new AttendanceFoldersDialog(this).setVisible(true));
        controlPanel.add(attendanceFoldersBtn);

        JButton startBtn = new JButton("Start Session");
        startBtn.setBackground(new Color(34, 197, 94)); 
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startBtn.addActionListener(e -> startSession());
        controlPanel.add(startBtn);
        
        JButton stopBtn = new JButton("Stop Session");
        stopBtn.setBackground(new Color(239, 68, 68)); 
        stopBtn.setForeground(Color.WHITE);
        stopBtn.setFocusPainted(false);
        stopBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        stopBtn.addActionListener(e -> stopSession());
        controlPanel.add(stopBtn);
        
        add(controlPanel, BorderLayout.WEST);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        JButton themeBtn = new JButton("🌙");
        themeBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        themeBtn.setToolTipText("Toggle Dark/Light Mode");
        themeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        themeBtn.setFocusPainted(false);
        // Make it look like a clean icon button
        themeBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        themeBtn.setContentAreaFilled(false);
        themeBtn.addActionListener(e -> {
            gui.ThemeManager.toggleTheme();
            themeBtn.setText(gui.ThemeManager.isDarkMode ? "☀️" : "🌙");
        });
        headerPanel.add(themeBtn);
        add(headerPanel, BorderLayout.NORTH);

        JPanel qrPanel = new JPanel(new BorderLayout());
        qrPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Dynamic QR Code"),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        qrLabel = new JLabel("QR Code will appear here", SwingConstants.CENTER);
        qrPanel.add(qrLabel, BorderLayout.CENTER);
                
                usLabel = new JLabel("Status: Stopped", SwingCostatusLabel.setForeground(new Color(239, 68, 68));
        qrPanel.add(statusLabel, BorderLayout.SOUTH);


        
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Live Attendance"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        attendanceLog = new JTextArea();
        attendanceLog.setEditable(false);
                anel.add(new JScrollPane(attendanceLog), BorderLayou
                anel.setPreferredSize(new Dimension(250, 0));
        add(logPanel, BorderLayout.EAST);
        
        uiUpdateTimer = new Timer(2000, e -> updateAttendanceLog());
        uiUpdateTimer.start();
    }


        String type = (String) sessionTypeCombo.getSelectedItem();
        String module = (String) moduleCombo.getSelectedItem();
        String selectedGroupOrSection = (String) groupsCombo.getSelectedItem();

        if (selectedGroupOrSection == null) {
            JOptionPane.showMessageDialog(this, "Please select a group/section.");
            return;
        }

        List<String> selected = java.util.Collections.singletonList(selectedGroupOrSection);

        String sessionId = "S" + System.currentTimeMillis();
        Session session;

        if ("TD".equals(type)) {
            session = new TDSession(sessionId, "T001", CLASS_LAT, CLASS_LNG, module, selected);
        } else {
            session = new CourseSession(sessionId, "T001", CLASS_LAT, CLASS_LNG, module, selected);
        }

        AttendanceManager.getInstance().startSession(session);
        statusLabel.setText("Status: Active (" + type + ")");
        statusLabel.setForeground(new Color(34, 197, 94));

        updateQRCode();

        if (qrTimer != null) {
            qrTimer.stop();
        }
        qrTimer = new Timer(30000, e -> updateQRCode());
        qrTimer.start();
    }

    private void stopSession() {
        Session session = AttendanceManager.getInstance().getActiveSession();
        if (session != null) {
            saveAttendanceToCSV(session);
        }

        AttendanceManager.getInstance().endSession();
        statusLabel.setText("Status: Stopped");
        statusLabel.setForeground(new Color(239, 68, 68));
        if (qrTimer != null) qrTimer.stop();
        qrLabel.setIcon(null);
        qrLabel.setText("Session Stopped");
    }

    private void saveAttenda
            ceToCSV(Session session) {
        boolean savedAny = false;
        for (String id : session.getAttendedStudentIds()) {
            models.Student s = AttendanceManager.getInstance().getStudent(id);
            if (s != null) {
                String folderName = "attendance_data/" + ("TD".equals(session.getSessionType()) ? s.getGroup() : s.getSection());
                java.io.File folder = new java.io.File(folderName);
                if (!folder.exists()) folder.mkdirs();
                
                String filename = folderName + "/attendance_" + session.getSessionId() + ".csv";
                boolean isNewFile = !new java.io.File(
                        ilename).exists();
                
                try (java.io.PrintWri
                    er writer = new 

                    writer.println(s.getId() + "," + s.getName() + "," + s.getGroup() + "," + s.getSection());
                    savedAny = true;

                    e.printStackTrace();
                }
                        
            }
        }
        
        if (savedAny) {
            JOptionPane.showMessageDialog(this, 
                "✅ Session Ended successfully.\n\n" +
         

                "Data Saved", JOptionPane.INFORMATION_MESSAGE);
        } else {
                    ionPane.showMessageDialog(this, "Sess
                            
                            
                            
                    
    private void updateQRCode() {
        if (AttendanceManager.getInstance().getActiveSession() == null) return;
                    

        String baseUrl = ngrokUrlField.getText().trim();
        String token = AttendanceManager.getInstance().qrTokenGenerator.generateNewToken();
        
        String urlParams = baseUrl + "/attendance?token=" + token;
            

        try {
            // Use an external API to generate QR code to avoid dragging heavy ZXing depend

            java.net.HttpURLConnection connection = (java.net.HttpURLConnection) new java.net.URL(apiUrl).openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            // 
            Image image = ImageIO.read(connection.getInputStream());
                    
            qrLabel.setText("");
                    
            qrLabel.setIcon(new ImageIcon(image));
        } catch (Exception ex) {
            qrLabel.setIcon(null);
            qrLabel.setText("Error generating QR");
            ex.printStackTrace();
        }
    }

    private void updateAttendanceLog() {
        Session session = AttendanceManager.getInstance().getActiveSession();
        if (session != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Total: ").append(session.getAttendedStudentIds().size()).append("\n\n");
            for (String id : session.getAttendedStudentIds()) {
                sb.append("ID: ").append(id).append("\n");
            }
            attendanceLog.setText(sb.toString());
        } else {
            attendanceLog.setText("");
        }
    }
}
