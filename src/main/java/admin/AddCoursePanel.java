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

public class AddCoursePanel extends JPanel {

    JTextField txtCourse;

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
    Color dangerColor = Color.decode("#DC2626");
    Color successColor = Color.decode("#16A34A");
    Color borderColor = Color.decode("#CBD5E1");
    Color textDark = Color.decode("#0F172A");

    public AddCoursePanel() {

        setLayout(new BorderLayout(20, 20));
        setBackground(backgroundColor);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // =====================================
        // CENTER PANEL
        // =====================================

        JPanel centerPanel = new JPanel(new BorderLayout(20, 20));
        centerPanel.setOpaque(false);

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

        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel lblDepartment = new JLabel("Department Name");
        lblDepartment.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        formCard.add(lblDepartment, gbc);

        gbc.gridx++;

        txtCourse = new JTextField(22);
        txtCourse.setPreferredSize(new Dimension(250, 45));
        txtCourse.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtCourse.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(borderColor, 1, true),
                        new EmptyBorder(0, 12, 0, 12)
                )
        );
        formCard.add(txtCourse, gbc);

        // =====================================
        // BUTTON PANEL (Only ADD button)
        // =====================================

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        buttonPanel.setOpaque(false);

        btnAdd = createButton("ADD", primaryColor);

        JLabel lblHint = new JLabel("Manage departments using the table below");
        lblHint.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblHint.setForeground(Color.GRAY);

        buttonPanel.add(btnAdd);
        buttonPanel.add(lblHint);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        formCard.add(buttonPanel, gbc);

        centerPanel.add(formCard, BorderLayout.NORTH);

        // =====================================
        // TABLE CARD
        // =====================================

        JPanel tableCard = new JPanel(new BorderLayout(10, 10));
        tableCard.setBackground(cardColor);
        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(borderColor, 1, true),
                        new EmptyBorder(20, 20, 20, 20)
                )
        );

        JLabel lblTableTitle = new JLabel("Department List");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tableCard.add(lblTableTitle, BorderLayout.NORTH);

        // =====================================
        // TABLE
        // =====================================

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Department Name");

        table = new JTable(model);
        table.setRowHeight(32);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JTableHeader header = table.getTableHeader();
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        scrollPane = new JScrollPane(table);
        tableCard.add(scrollPane, BorderLayout.CENTER);

        // =====================================
        // MESSAGE LABEL (INITIALIZED HERE)
        // =====================================
        lblMessage = new JLabel("", JLabel.CENTER);
        lblMessage.setFont(new Font("Segoe UI", Font.BOLD, 14));

        // =====================================
        // BOTTOM PANEL - DELETE BUTTON + MESSAGE
        // =====================================

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 0));
        bottomPanel.setOpaque(false);

        btnDelete = createButton("DELETE", dangerColor);

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

        loadDepartments();

        // HIDE ID COLUMN
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        // =====================================
        // EVENTS
        // =====================================

        btnAdd.addActionListener(e -> addDepartment());
        btnDelete.addActionListener(e -> deleteSelected());
    }

    // =====================================
    // LOAD DEPARTMENTS
    // =====================================

    private void loadDepartments() {
        try {
            model.setRowCount(0);
            Connection conn = DBConnection.connect();
            String sql = "SELECT id, department_name FROM departments ORDER BY department_name";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("department_name")
                });
            }
        } catch (Exception e) {
            showMessage("Error Loading Departments", dangerColor);
        }
    }

    // =====================================
    // ADD DEPARTMENT
    // =====================================

    private void addDepartment() {
        String course = txtCourse.getText().trim();

        if (course.isEmpty()) {
            showMessage("Department cannot be empty", dangerColor);
            return;
        }

        try {
            Connection conn = DBConnection.connect();
            String sql = "INSERT INTO departments(department_name) VALUES(?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, course);
            pst.executeUpdate();

            txtCourse.setText("");
            loadDepartments();
            showMessage("Department Added Successfully", successColor);

        } catch (Exception e) {
            showMessage("Department Already Exists", dangerColor);
        }
    }

    // =====================================
    // DELETE SELECTED
    // =====================================

    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            showMessage("Select a department first", dangerColor);
            return;
        }

        int option = themedConfirmDialog("Delete selected department?");
        if (option != 0) return;

        int departmentId = Integer.parseInt(table.getValueAt(row, 0).toString());
        deleteDepartment(departmentId);
    }

    // =====================================
    // DELETE DEPARTMENT
    // =====================================

    private void deleteDepartment(int departmentId) {
        try {
            Connection conn = DBConnection.connect();

            // CHECK SUBJECTS
            String subjectSql = "SELECT COUNT(*) FROM subjects WHERE department_id = ?";
            PreparedStatement pst1 = conn.prepareStatement(subjectSql);
            pst1.setInt(1, departmentId);
            ResultSet rs1 = pst1.executeQuery();

            if (rs1.next() && rs1.getInt(1) > 0) {
                showMessage("Cannot delete. Subjects exist.", dangerColor);
                return;
            }

            // CHECK STUDENTS
            String studentSql = "SELECT COUNT(*) FROM students WHERE department_id = ?";
            PreparedStatement pst2 = conn.prepareStatement(studentSql);
            pst2.setInt(1, departmentId);
            ResultSet rs2 = pst2.executeQuery();

            if (rs2.next() && rs2.getInt(1) > 0) {
                showMessage("Cannot delete. Students exist.", dangerColor);
                return;
            }

            // DELETE
            String deleteSql = "DELETE FROM departments WHERE id = ?";
            PreparedStatement pst3 = conn.prepareStatement(deleteSql);
            pst3.setInt(1, departmentId);
            pst3.executeUpdate();

            loadDepartments();
            showMessage("Department Deleted Successfully", successColor);

        } catch (Exception e) {
            showMessage("Delete Failed", dangerColor);
        }
    }

    // =====================================
    // MESSAGE SYSTEM
    // =====================================

    private void showMessage(String message, Color color) {
        lblMessage.setText(message);
        lblMessage.setForeground(color);

        if (messageTimer != null) {
            messageTimer.stop();
        }

        messageTimer = new Timer(4000, e -> lblMessage.setText(""));
        messageTimer.setRepeats(false);
        messageTimer.start();
    }

    // =====================================
    // THEMED DIALOG
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

    // =====================================
    // REFRESH
    // =====================================

    public void refreshData() {
        loadDepartments();
    }

    // =====================================
    // BUTTON STYLE
    // =====================================

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 42));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        return button;
    }
}