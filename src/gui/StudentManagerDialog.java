package gui;

import core.AttendanceManager;
import models.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Collection;

public class StudentManagerDialog extends JDialog {

    private DefaultTableModel tableModel;
    private JTable studentTable;

    public StudentManagerDialog(JFrame parent) {
        super(parent, "Manage Students", true);
        setSize(500, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        initUI();
        loadStudents();
    }

    private void initUI() {
        // Table
        String[] columns = {"ID", "Name", "Email", "Group", "Section"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);

        // Buttons Panel
        JPanel btnPanel = new JPanel(new FlowLayout());
        
        JButton addBtn = new JButton("Add Student");
        addBtn.addActionListener(e -> addStudent());
        btnPanel.add(addBtn);

        JButton deleteBtn = new JButton("Delete Selected");
        deleteBtn.addActionListener(e -> deleteStudent());
        btnPanel.add(deleteBtn);

        JButton closeBtn = new JButton("Close");
        closeBtn.addActionListener(e -> dispose());
        btnPanel.add(closeBtn);

        add(btnPanel, BorderLayout.SOUTH);
    }

    private void loadStudents() {
        tableModel.setRowCount(0);
        Collection<Student> students = AttendanceManager.getInstance().getAllStudents();
        for (Student s : students) {
            tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getEmail(), s.getGroup(), s.getSection()});
        }
    }

    private void addStudent() {
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        
        // Setup Groups combo
        JComboBox<String> groupField = new JComboBox<>(new String[]{"A1", "A2", "A3", "A4", "B1", "B2", "B3", "B4"});
        // Setup Section combo
        JComboBox<String> secField = new JComboBox<>(new String[]{"SecA", "SecB"});

        form.add(new JLabel("ID:")); form.add(idField);
        form.add(new JLabel("Name:")); form.add(nameField);
        form.add(new JLabel("Email:")); form.add(emailField);
        form.add(new JLabel("Group:")); form.add(groupField);
        form.add(new JLabel("Section:")); form.add(secField);

        int result = JOptionPane.showConfirmDialog(this, form, "Add New Student", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String id = idField.getText().trim();
            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID cannot be empty.");
                return;
            }
            Student newStudent = new Student(
                id, 
                nameField.getText().trim(), 
                emailField.getText().trim(), 
                (String) groupField.getSelectedItem(), 
                (String) secField.getSelectedItem()
            );
            AttendanceManager.getInstance().addStudent(newStudent);
            loadStudents();
        }
    }

    private void deleteStudent() {
        int selectedRow = studentTable.getSelectedRow();
        if (selectedRow != -1) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            AttendanceManager.getInstance().removeStudent(id);
            loadStudents();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a student to delete.");
        }
    }
}
