package admin;

import database.DBConnection;
import utils.PasswordUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddStudentPanel extends JPanel {

    JTextField txtRoll;
    JTextField txtName;
    JTextField txtDob;
    JTextField txtUsername;

    JPasswordField txtPassword;

    JComboBox<String> cmbGender;
    JComboBox<String> cmbDepartment;

    JButton btnAdd;
    JButton btnDelete;

    JLabel lblMessage;

    JTable table;
    DefaultTableModel model;

    JTextField txtSearch;
    TableRowSorter<DefaultTableModel> sorter;

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

    public AddStudentPanel() {

        setLayout(new BorderLayout(20, 20));
        setBackground(backgroundColor);
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // =====================================
        // MAIN CONTENT
        // =====================================

        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setOpaque(false);

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

        txtRoll = createTextField();
        txtName = createTextField();
        txtDob = createTextField();
        txtUsername = createTextField();
        txtPassword = createPasswordField();

        cmbGender = createComboBox();
        cmbDepartment = createComboBox();

        cmbGender.addItem("Male");
        cmbGender.addItem("Female");

        btnAdd = createPrimaryButton("ADD STUDENT");

        lblMessage = new JLabel("");
        lblMessage.setFont(new Font("Segoe UI", Font.BOLD, 13));

        addFormRow(formCard, gbc, 0, "Roll Number", txtRoll);
        addFormRow(formCard, gbc, 1, "Student Name", txtName);
        addFormRow(formCard, gbc, 2, "DOB (YYYY-MM-DD)", txtDob);
        addFormRow(formCard, gbc, 3, "Gender", cmbGender);
        addFormRow(formCard, gbc, 4, "Department", cmbDepartment);
        addFormRow(formCard, gbc, 5, "Username", txtUsername);
        addFormRow(formCard, gbc, 6, "Password", txtPassword);

        // =====================================
        // BUTTON PANEL
        // =====================================

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(420, 50));

        addButtonHover(btnAdd, primaryColor, primaryHover);
        buttonPanel.add(btnAdd);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        formCard.add(buttonPanel, gbc);

        gbc.gridy++;
        JLabel lblHint = new JLabel("Manage students using the table");
        lblHint.setForeground(Color.GRAY);
        lblHint.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formCard.add(lblHint, gbc);

        gbc.gridy++;
        formCard.add(lblMessage, gbc);

        // =====================================
        // TABLE CARD
        // =====================================

        JPanel tableCard = new JPanel(new BorderLayout(15, 15));
        tableCard.setBackground(cardColor);
        tableCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(borderColor, 1, true),
                        new EmptyBorder(20, 20, 20, 20)
                )
        );

        JLabel lblTableTitle = new JLabel("Student List");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setOpaque(false);

        topPanel.add(lblTableTitle, BorderLayout.NORTH);

        // ===================== SEARCH LABEL + FIELD =====================
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        searchPanel.setOpaque(false);

        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSearch.setForeground(textDark);

        txtSearch = createTextField();
        txtSearch.setPreferredSize(new Dimension(250, 40));
        txtSearch.setToolTipText("Search Student...");

        searchPanel.add(lblSearch);
        searchPanel.add(txtSearch);

        topPanel.add(searchPanel, BorderLayout.SOUTH);
        // ================================================================

        tableCard.add(topPanel, BorderLayout.NORTH);

        // =====================================
        // TABLE
        // =====================================

        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("User ID");
        model.addColumn("Roll No");
        model.addColumn("Name");
        model.addColumn("Department");

        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setSelectionBackground(Color.decode("#518ac4"));
        table.setGridColor(borderColor);
        table.setShowVerticalLines(false);

        JTableHeader header = table.getTableHeader();
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { searchStudent(); }
            @Override
            public void removeUpdate(DocumentEvent e) { searchStudent(); }
            @Override
            public void changedUpdate(DocumentEvent e) { searchStudent(); }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        table.setFillsViewportHeight(true);

        tableCard.add(scrollPane, BorderLayout.CENTER);

        // =====================================
        // DELETE BUTTON
        // =====================================

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);

        btnDelete = createDangerButton("DELETE STUDENT");
        addButtonHover(btnDelete, dangerColor, dangerHover);
        bottomPanel.add(btnDelete);

        tableCard.add(bottomPanel, BorderLayout.SOUTH);

        // =====================================
        // ADD TO MAIN
        // =====================================

        contentPanel.add(formCard);
        contentPanel.add(tableCard);
        add(contentPanel, BorderLayout.CENTER);

        refreshDepartments();
        loadStudents();

        // =====================================
        // EVENTS
        // =====================================

        btnAdd.addActionListener(e -> addStudent());
        btnDelete.addActionListener(e -> deleteStudent());
    }

    // =====================================
    // BUTTON HOVER
    // =====================================

    private void addButtonHover(JButton button, Color normal, Color hover) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(normal);
            }
        });
    }

    // =====================================
    // LOAD STUDENTS
    // =====================================

    private void loadStudents() {
        try {
            model.setRowCount(0);
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT students.user_id, roll_number, name, department_name " +
                         "FROM students INNER JOIN departments " +
                         "ON students.department_id = departments.id ORDER BY name";

            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("roll_number"),
                        rs.getString("name"),
                        rs.getString("department_name")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshData() {
        refreshDepartments();
        loadStudents();
    }

    private void searchStudent() {
        String text = txtSearch.getText().trim();
        if (text.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    // =====================================
    // REFRESH DEPARTMENTS
    // =====================================

    public void refreshDepartments() {
        cmbDepartment.removeAllItems();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT department_name FROM departments ORDER BY department_name";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                cmbDepartment.addItem(rs.getString("department_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =====================================
    // ADD STUDENT (Improved Validation)
    // =====================================

    private void addStudent() {
        try {
            String roll = txtRoll.getText().trim();
            String name = txtName.getText().trim();
            String dob = txtDob.getText().trim();
            String gender = cmbGender.getSelectedItem().toString();
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());

            if (cmbDepartment.getSelectedItem() == null) {
                showMessage("Please add at least one department first", dangerColor);
                return;
            }

            String department = cmbDepartment.getSelectedItem().toString();

            // === Validation ===
            if (roll.isEmpty()) {
                showMessage("Roll Number is required", dangerColor);
                txtRoll.requestFocus();
                return;
            }
            if (name.isEmpty()) {
                showMessage("Student Name is required", dangerColor);
                txtName.requestFocus();
                return;
            }
            if (dob.isEmpty()) {
                showMessage("Date of Birth is required", dangerColor);
                txtDob.requestFocus();
                return;
            }
            if (username.isEmpty()) {
                showMessage("Username is required", dangerColor);
                txtUsername.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                showMessage("Password is required", dangerColor);
                txtPassword.requestFocus();
                return;
            }

            // DOB Format Validation
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate.parse(dob, formatter);
            } catch (DateTimeParseException e) {
                showMessage("Invalid Date Format! Use YYYY-MM-DD (e.g., 2005-03-15)", dangerColor);
                txtDob.requestFocus();
                return;
            }

            Connection conn = DBConnection.getConnection();

            // Check duplicate username
            PreparedStatement checkUser = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
            checkUser.setString(1, username);
            if (checkUser.executeQuery().next()) {
                showMessage("Username already exists! Please choose another.", dangerColor);
                txtUsername.requestFocus();
                return;
            }

            // Get department ID
            String deptSql = "SELECT id FROM departments WHERE department_name=?";
            PreparedStatement deptPst = conn.prepareStatement(deptSql);
            deptPst.setString(1, department);
            ResultSet deptRs = deptPst.executeQuery();

            int departmentId = -1;
            if (deptRs.next()) {
                departmentId = deptRs.getInt("id");
            } else {
                showMessage("Selected department not found", dangerColor);
                return;
            }

            String hashedPassword = PasswordUtil.hashPassword(password);

            // Insert into users
            String userSql = "INSERT INTO users(username, password, role, department_id) VALUES(?,?,?,?)";
            PreparedStatement userPst = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            userPst.setString(1, username);
            userPst.setString(2, hashedPassword);
            userPst.setString(3, "STUDENT");
            userPst.setInt(4, departmentId);
            userPst.executeUpdate();

            ResultSet keys = userPst.getGeneratedKeys();
            int userId = -1;
            if (keys.next()) userId = keys.getInt(1);

            // Insert into students
            String studentSql = "INSERT INTO students(roll_number, name, dob, gender, department_id, user_id) VALUES(?,?,?,?,?,?)";
            PreparedStatement studentPst = conn.prepareStatement(studentSql);
            studentPst.setString(1, roll);
            studentPst.setString(2, name);
            studentPst.setString(3, dob);
            studentPst.setString(4, gender);
            studentPst.setInt(5, departmentId);
            studentPst.setInt(6, userId);
            studentPst.executeUpdate();

            clearFields();
            loadStudents();
            showMessage("Student Added Successfully!", successColor);

        } catch (SQLException e) {
            e.printStackTrace();
            showMessage("Database Error: " + e.getMessage(), dangerColor);
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Unexpected Error: " + e.getMessage(), dangerColor);
        }
    }

    // =====================================
    // DELETE STUDENT
    // =====================================

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row == -1) {
            showMessage("Please select a student to delete", dangerColor);
            return;
        }

        int modelRow = table.convertRowIndexToModel(row);
        int userId = Integer.parseInt(model.getValueAt(modelRow, 0).toString());

        int confirm = themedConfirmDialog("Are you sure you want to delete this student?");
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            Connection conn = DBConnection.getConnection();

            PreparedStatement pst1 = conn.prepareStatement("DELETE FROM marks WHERE student_id = ?");
            pst1.setInt(1, userId);
            pst1.executeUpdate();

            PreparedStatement pst2 = conn.prepareStatement("DELETE FROM students WHERE user_id = ?");
            pst2.setInt(1, userId);
            pst2.executeUpdate();

            PreparedStatement pst3 = conn.prepareStatement("DELETE FROM users WHERE id = ?");
            pst3.setInt(1, userId);
            pst3.executeUpdate();

            loadStudents();
            showMessage("Student deleted successfully", successColor);

        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Failed to delete student. They may have linked records.", dangerColor);
        }
    }

    // =====================================
    // MESSAGE
    // =====================================

    private void showMessage(String message, Color color) {
        lblMessage.setForeground(color);
        lblMessage.setText(message);

        if (messageTimer != null) messageTimer.stop();

        messageTimer = new Timer(4000, e -> lblMessage.setText(""));
        messageTimer.setRepeats(false);
        messageTimer.start();
    }

    // =====================================
    // CONFIRM DIALOG
    // =====================================

    private int themedConfirmDialog(String message) {
        UIManager.put("OptionPane.background", cardColor);
        UIManager.put("Panel.background", cardColor);
        return JOptionPane.showConfirmDialog(this, message, "Confirm Delete", JOptionPane.YES_NO_OPTION);
    }

    // =====================================
    // CLEAR FIELDS
    // =====================================

    private void clearFields() {
        txtRoll.setText("");
        txtName.setText("");
        txtDob.setText("");
        txtUsername.setText("");
        txtPassword.setText("");
        cmbGender.setSelectedIndex(0);
    }

    // =====================================
    // FORM ROW
    // =====================================

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(component, gbc);
    }

    // Helper methods (createTextField, createPasswordField, etc.) remain unchanged
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(260, 44));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(borderColor, 1, true),
                new EmptyBorder(0, 14, 0, 14)
        ));
        return field;
    }

    private JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(260, 44));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(borderColor, 1, true),
                new EmptyBorder(0, 14, 0, 14)
        ));
        return field;
    }

    private JComboBox<String> createComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setPreferredSize(new Dimension(260, 44));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return comboBox;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(primaryColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(180, 48));
        return button;
    }

    private JButton createDangerButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(dangerColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(170, 46));
        return button;
    }
}