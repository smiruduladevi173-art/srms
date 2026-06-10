package admin;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddSubjectPanel extends JPanel {

    // =====================================
    // COMPONENTS
    // =====================================

    JComboBox<String> cmbDepartment;
    JComboBox<Integer> cmbSemester;
    JTextField txtSubject;

    JButton btnAdd;
    JButton btnDelete;

    JLabel lblMessage;

    JTable table;
    DefaultTableModel model;
    JScrollPane scrollPane;

    Timer messageTimer;

    // =====================================
    // COLORS
    // =====================================

    Color backgroundColor = Color.decode("#F1F5F9");
    Color cardColor = Color.WHITE;
    Color primaryColor = Color.decode("#2563EB");
    Color primaryHover = Color.decode("#1D4ED8");
    Color dangerColor = Color.decode("#DC2626");
    Color dangerHover = Color.decode("#B91C1C");
    Color successColor = Color.decode("#16A34A");
    Color borderColor = Color.decode("#CBD5E1");
    Color textDark = Color.decode("#0F172A");

    // =====================================
    // CONSTRUCTOR
    // =====================================

    public AddSubjectPanel() {

        setLayout(new BorderLayout());
        setBackground(backgroundColor);

        // =====================================
        // CENTER PANEL
        // =====================================

        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // =====================================
        // FORM CARD
        // =====================================

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(cardColor);
        formCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(borderColor, 1, true),
                        new EmptyBorder(25, 25, 25, 25)
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // =====================================
        // COMPONENTS
        // =====================================

        cmbDepartment = createComboBox();
        cmbSemester = createSemesterCombo();
        txtSubject = createTextField();

        btnAdd = createPrimaryButton("ADD SUBJECT");
        btnDelete = createDangerButton("DELETE SUBJECT");

        lblMessage = new JLabel("");
        lblMessage.setFont(new Font("Segoe UI", Font.BOLD, 13));

        // =====================================
        // FORM ROWS
        // =====================================

        addFormRow(formCard, gbc, 0, "Department", cmbDepartment);
        addFormRow(formCard, gbc, 1, "Semester", cmbSemester);
        addFormRow(formCard, gbc, 2, "Subject Name", txtSubject);

        // =====================================
        // BUTTON PANEL (Only ADD button now)
        // =====================================

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setOpaque(false);

        buttonPanel.add(btnAdd);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(25, 10, 10, 10);
        formCard.add(buttonPanel, gbc);

        centerPanel.add(formCard, BorderLayout.NORTH);

        // =====================================
        // TABLE CARD
        // =====================================

        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(cardColor);
        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(borderColor, 1, true),
                        new EmptyBorder(20, 20, 20, 20)
                )
        );

        JLabel lblTableTitle = new JLabel("Subject List");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTableTitle.setForeground(textDark);
        lblTableTitle.setBorder(new EmptyBorder(0, 0, 15, 0));
        tableCard.add(lblTableTitle, BorderLayout.NORTH);

        // =====================================
        // TABLE
        // =====================================

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("ID");
        model.addColumn("Semester");
        model.addColumn("Subject");

        table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(borderColor);
        table.setSelectionBackground(Color.decode("#518ac4"));
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 38));

        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(borderColor, 1, true));
        tableCard.add(scrollPane, BorderLayout.CENTER);

        // =====================================
        // BOTTOM PANEL - DELETE BUTTON + MESSAGE
        // =====================================

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 0));
        bottomPanel.setOpaque(false);

        JPanel deletePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        deletePanel.setOpaque(false);
        deletePanel.add(btnDelete);

        bottomPanel.add(deletePanel, BorderLayout.WEST);
        bottomPanel.add(lblMessage, BorderLayout.CENTER);

        tableCard.add(bottomPanel, BorderLayout.SOUTH);

        centerPanel.add(tableCard, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // =====================================
        // LOAD DATA
        // =====================================

        refreshDepartments();

        // HIDE ID COLUMN
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        // =====================================
        // EVENTS
        // =====================================

        btnAdd.addActionListener(e -> addSubject());
        btnDelete.addActionListener(e -> deleteSubject());
        cmbDepartment.addActionListener(e -> loadSubjects());
    }

    // =====================================
    // FORM ROW
    // =====================================

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(textDark);
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(component, gbc);
    }

    // =====================================
    // TEXT FIELD
    // =====================================

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(260, 42));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(borderColor, 1, true),
                        new EmptyBorder(0, 12, 0, 12)
                )
        );
        return field;
    }

    // =====================================
    // COMBO BOX
    // =====================================

    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(260, 42));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        return comboBox;
    }

    // =====================================
    // SEMESTER COMBO
    // =====================================

    private JComboBox<Integer> createSemesterCombo() {
        JComboBox<Integer> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(260, 42));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);

        for (int i = 1; i <= 8; i++) {
            comboBox.addItem(i);
        }
        return comboBox;
    }

    // =====================================
    // PRIMARY BUTTON
    // =====================================

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 44));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        return button;
    }

    // =====================================
    // DANGER BUTTON
    // =====================================

    private JButton createDangerButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 44));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBackground(dangerColor);
        button.setForeground(Color.WHITE);
        return button;
    }

    // =====================================
    // REFRESH DATA
    // =====================================

    public void refreshData() {
        refreshDepartments();
        loadSubjects();
    }

    // =====================================
    // REFRESH DEPARTMENTS
    // =====================================

    public void refreshDepartments() {
        cmbDepartment.removeAllItems();
        try {
            Connection conn = DBConnection.connect();
            String sql = "SELECT department_name FROM departments ORDER BY department_name";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                cmbDepartment.addItem(rs.getString("department_name"));
            }
        } catch (Exception e) {
            showMessage("Failed to Load Departments", dangerColor);
        }
    }

    // =====================================
    // GET DEPARTMENT ID
    // =====================================

    private int getDepartmentId(String departmentName) throws SQLException {
        Connection conn = DBConnection.connect();
        String sql = "SELECT id FROM departments WHERE department_name=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, departmentName);
        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }
        return -1;
    }

    // =====================================
    // LOAD SUBJECTS
    // =====================================

    private void loadSubjects() {
        try {
            model.setRowCount(0);
            if (cmbDepartment.getSelectedItem() == null) return;

            String department = cmbDepartment.getSelectedItem().toString();
            int departmentId = getDepartmentId(department);

            Connection conn = DBConnection.connect();
            String sql = "SELECT id, semester, subject_name FROM subjects WHERE department_id=? ORDER BY semester";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, departmentId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getInt("semester"),
                        rs.getString("subject_name")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =====================================
    // ADD SUBJECT
    // =====================================

    private void addSubject() {
        try {
            if (cmbDepartment.getSelectedItem() == null) {
                showMessage("Add Department First", dangerColor);
                return;
            }

            String department = cmbDepartment.getSelectedItem().toString();
            int semester = (int) cmbSemester.getSelectedItem();
            String subject = txtSubject.getText().trim();

            if (subject.isEmpty()) {
                showMessage("Enter Subject Name", dangerColor);
                return;
            }

            int departmentId = getDepartmentId(department);

            Connection conn = DBConnection.connect();

            // CHECK DUPLICATE
            String checkSql = "SELECT * FROM subjects WHERE department_id = ? AND semester = ? AND subject_name = ?";
            PreparedStatement checkPst = conn.prepareStatement(checkSql);
            checkPst.setInt(1, departmentId);
            checkPst.setInt(2, semester);
            checkPst.setString(3, subject);

            if (checkPst.executeQuery().next()) {
                showMessage("Subject Already Exists", dangerColor);
                return;
            }

            // INSERT
            String sql = "INSERT INTO subjects (department_id,semester,subject_name) VALUES(?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, departmentId);
            pst.setInt(2, semester);
            pst.setString(3, subject);
            pst.executeUpdate();

            txtSubject.setText("");
            loadSubjects();
            showMessage("Subject Added Successfully", successColor);

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Failed to Add Subject", dangerColor);
        }
    }

    // =====================================
    // DELETE SUBJECT
    // =====================================

    private void deleteSubject() {
        int row = table.getSelectedRow();
        if (row == -1) {
            showMessage("Select a subject first", dangerColor);
            return;
        }

        int confirm = themedConfirmDialog("Delete selected subject?");
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            int subjectId = Integer.parseInt(table.getValueAt(row, 0).toString());

            Connection conn = DBConnection.connect();

            // DELETE MARKS FIRST
            String deleteMarks = "DELETE FROM marks WHERE subject_id=?";
            PreparedStatement pst1 = conn.prepareStatement(deleteMarks);
            pst1.setInt(1, subjectId);
            pst1.executeUpdate();

            // DELETE SUBJECT
            String deleteSubject = "DELETE FROM subjects WHERE id=?";
            PreparedStatement pst2 = conn.prepareStatement(deleteSubject);
            pst2.setInt(1, subjectId);
            pst2.executeUpdate();

            loadSubjects();
            showMessage("Subject Deleted Successfully", successColor);

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Delete Failed", dangerColor);
        }
    }

    // =====================================
    // MESSAGE SYSTEM
    // =====================================

    private void showMessage(String message, Color color) {
        lblMessage.setForeground(color);
        lblMessage.setText(message);

        if (messageTimer != null) {
            messageTimer.stop();
        }

        messageTimer = new Timer(4000, e -> lblMessage.setText(""));
        messageTimer.setRepeats(false);
        messageTimer.start();
    }

    // =====================================
    // THEMED CONFIRM DIALOG
    // =====================================

    private int themedConfirmDialog(String message) {
        UIManager.put("OptionPane.background", cardColor);
        UIManager.put("Panel.background", cardColor);
        UIManager.put("OptionPane.messageForeground", textDark);
        UIManager.put("Button.background", primaryColor);
        UIManager.put("Button.foreground", Color.WHITE);

        return JOptionPane.showConfirmDialog(
                this,
                message,
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );
    }
}