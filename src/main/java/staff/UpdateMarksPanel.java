package staff;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UpdateMarksPanel extends JPanel {

    JComboBox<String> cmbDepartment;

    JComboBox<String> cmbSubject;

    JButton btnLoad;

    JButton btnSave;

    JTable table;

    DefaultTableModel model;

    JLabel lblMessage;

    int selectedDepartmentId = -1;

    int selectedSubjectId = -1;

    // =====================================
    // COLOR THEME
    // =====================================

    Color backgroundColor =
            Color.decode("#F1F5F9");

    Color cardColor =
            Color.WHITE;

    Color primaryColor =
            Color.decode("#2563EB");

    Color primaryHover =
            Color.decode("#1D4ED8");

    Color successColor =
            Color.decode("#16A34A");

    Color dangerColor =
            Color.decode("#DC2626");

    Color borderColor =
            Color.decode("#CBD5E1");

    Color textDark =
            Color.decode("#0F172A");

    public UpdateMarksPanel() {

        setLayout(
                new BorderLayout(
                        20,
                        20
                )
        );

        setBackground(
                backgroundColor
        );

        setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // =====================================
        // HEADER PANEL
        // =====================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(
                primaryColor
        );

        headerPanel.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        
           

     

        // =====================================
        // MAIN CARD
        // =====================================

        JPanel mainCard =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        mainCard.setBackground(
                cardColor
        );

        mainCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                borderColor,
                                1,
                                true
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        // =====================================
        // FILTER PANEL
        // =====================================

        JPanel filterPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                15,
                                10
                        )
                );

        filterPanel.setOpaque(false);

        JLabel lblDepartment =
                createLabel("Department");

        JLabel lblSubject =
                createLabel("Subject");

        cmbDepartment =
                new JComboBox<>();

        styleComboBox(
                cmbDepartment
        );

        cmbSubject =
                new JComboBox<>();

        styleComboBox(
                cmbSubject
        );

        btnLoad =
                createPrimaryButton(
                        "LOAD STUDENTS"
                );

        filterPanel.add(lblDepartment);

        filterPanel.add(cmbDepartment);

        filterPanel.add(
                Box.createHorizontalStrut(10)
        );

        filterPanel.add(lblSubject);

        filterPanel.add(cmbSubject);

        filterPanel.add(
                Box.createHorizontalStrut(10)
        );

        filterPanel.add(btnLoad);

        mainCard.add(
                filterPanel,
                BorderLayout.NORTH
        );

        // =====================================
        // TABLE
        // =====================================

        model =
                new DefaultTableModel(
                        new String[] {

                                "Student ID",

                                "Roll No",

                                "Student Name",

                                "Marks"
                        },
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return column == 3;
                    }
                };

        table =
                new JTable(model);

        table.setRowHeight(35);

        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        table.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        table.getTableHeader().setBackground(
                primaryColor
        );

        table.getTableHeader().setForeground(
                Color.WHITE
        );

        table.setSelectionBackground(
                Color.decode("#518ac4")
        );

        table.setGridColor(
                borderColor
        );

        JScrollPane scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                new LineBorder(
                        borderColor,
                        1,
                        true
                )
        );

        mainCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================
        // BOTTOM PANEL
        // =====================================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setOpaque(false);

        btnSave =
                createSuccessButton(
                        "SAVE MARKS"
                );

        lblMessage =
                new JLabel("");

        lblMessage.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        bottomPanel.add(
                btnSave,
                BorderLayout.WEST
        );

        bottomPanel.add(
                lblMessage,
                BorderLayout.CENTER
        );

        mainCard.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        add(
                mainCard,
                BorderLayout.CENTER
        );

        // =====================================
        // INITIAL LOAD
        // =====================================

        loadDepartments();

        loadSubjects();

        // =====================================
        // EVENTS
        // =====================================

        cmbDepartment.addActionListener(e -> {

            loadSubjects();

            model.setRowCount(0);
        });

        btnLoad.addActionListener(e -> {

            loadStudents();
        });

        btnSave.addActionListener(e -> {

            saveMarks();
        });
    }

    // =====================================
    // LABEL STYLE
    // =====================================

    private JLabel createLabel(
            String text
    ) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        label.setForeground(
                textDark
        );

        return label;
    }

    // =====================================
    // COMBOBOX STYLE
    // =====================================

    private void styleComboBox(
            JComboBox<String> comboBox
    ) {

        comboBox.setPreferredSize(
                new Dimension(
                        220,
                        40
                )
        );

        comboBox.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        comboBox.setBackground(
                Color.WHITE
        );

        comboBox.setBorder(
                new LineBorder(
                        borderColor,
                        1,
                        true
                )
        );
    }

    // =====================================
    // PRIMARY BUTTON
    // =====================================

    private JButton createPrimaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(
                        180,
                        42
                )
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setBackground(
                primaryColor
        );

        button.setForeground(
                Color.WHITE
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                primaryHover
                        );
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        button.setBackground(
                                primaryColor
                        );
                    }
                }
        );

        return button;
    }

    // =====================================
    // SUCCESS BUTTON
    // =====================================

    private JButton createSuccessButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(
                        170,
                        42
                )
        );

        button.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setBackground(
                successColor
        );

        button.setForeground(
                Color.WHITE
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        return button;
    }

    // =====================================
    // LOAD DEPARTMENTS
    // =====================================

    public void loadDepartments() {

        cmbDepartment.removeAllItems();

        try {

            Connection conn =
                    DBConnection.connect();

            String sql =
                    "SELECT * FROM departments";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()) {

                cmbDepartment.addItem(

                        rs.getInt("id")
                                + "-"
                                + rs.getString(
                                "department_name"
                        )
                );
            }

            rs.close();

            pst.close();

        } catch(Exception e) {

            e.printStackTrace();
        }



        
    }



    // =====================================
    // LOAD SUBJECTS
    // =====================================

    public void loadSubjects() {

        cmbSubject.removeAllItems();

        try {

            if(cmbDepartment.getSelectedItem()
                    == null) {

                return;
            }

            String selectedDept =
                    cmbDepartment
                            .getSelectedItem()
                            .toString();

            selectedDepartmentId =
                    Integer.parseInt(
                            selectedDept
                                    .split("-")[0]
                    );

            Connection conn =
                    DBConnection.connect();

            String sql =
                    "SELECT * FROM subjects "
                            + "WHERE department_id=?";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            pst.setInt(
                    1,
                    selectedDepartmentId
            );

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()) {

                cmbSubject.addItem(

                        rs.getInt("id")
                                + "-"
                                + rs.getString(
                                "subject_name"
                        )
                                + " (Sem "
                                + rs.getInt(
                                "semester"
                        )
                                + ")"
                );
            }

            rs.close();

            pst.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // =====================================
    // LOAD STUDENTS
    // =====================================

    public void loadStudents() {

        model.setRowCount(0);

        try {

            if(cmbSubject.getSelectedItem()
                    == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "No Subject Found"
                );

                return;
            }

            String selectedSubject =
                    cmbSubject
                            .getSelectedItem()
                            .toString();

            selectedSubjectId =
                    Integer.parseInt(
                            selectedSubject
                                    .split("-")[0]
                    );

            Connection conn =
                    DBConnection.connect();

            String sql =
                    "SELECT * FROM students "
                            + "WHERE department_id=?";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            pst.setInt(
                    1,
                    selectedDepartmentId
            );

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()) {

                int studentId =
                        rs.getInt(
                                "student_id"
                        );

                model.addRow(
                        new Object[] {

                                studentId,

                                rs.getString(
                                        "roll_number"
                                ),

                                rs.getString(
                                        "name"
                                ),

                                getExistingMark(
                                        studentId,
                                        selectedSubjectId
                                )
                        }
                );
            }

            rs.close();

            pst.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    // =====================================
    // GET EXISTING MARK
    // =====================================

    public String getExistingMark(
            int studentId,
            int subjectId
    ) {

        try {

            Connection conn =
                    DBConnection.connect();

            String sql =
                    "SELECT marks FROM marks "
                            + "WHERE student_id=? "
                            + "AND subject_id=?";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            pst.setInt(1, studentId);

            pst.setInt(2, subjectId);

            ResultSet rs =
                    pst.executeQuery();

            if(rs.next()) {

                String mark =
                        rs.getString("marks");

                rs.close();

                pst.close();

                return mark;
            }

            rs.close();

            pst.close();

        } catch(Exception e) {

            e.printStackTrace();
        }

        return "";
    }

    // =====================================
    // SAVE MARKS
    // =====================================

    public void saveMarks() {

        try {

            if(table.isEditing()) {

                table.getCellEditor()
                        .stopCellEditing();
            }

            String selectedSubject =
                    cmbSubject
                            .getSelectedItem()
                            .toString();

            selectedSubjectId =
                    Integer.parseInt(
                            selectedSubject
                                    .split("-")[0]
                    );

            Connection conn =
                    DBConnection.connect();

            for(int i = 0;
                i < model.getRowCount();
                i++) {

                int studentId =
                        Integer.parseInt(
                                model.getValueAt(
                                        i,
                                        0
                                ).toString()
                        );

                Object value =
                        model.getValueAt(
                                i,
                                3
                        );

                if(value == null) {

                    continue;
                }

                String markText =
                        value.toString().trim();

                if(markText.isEmpty()) {

                    continue;
                }

                int mark =
                        Integer.parseInt(
                                markText
                        );

                if(mark < 0 || mark > 100) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Marks must be between 0 and 100"
                    );

                    return;
                }

                String checkSql =
                        "SELECT * FROM marks "
                                + "WHERE student_id=? "
                                + "AND subject_id=?";

                PreparedStatement checkPst =
                        conn.prepareStatement(checkSql);

                checkPst.setInt(1, studentId);

                checkPst.setInt(2, selectedSubjectId);

                ResultSet rs =
                        checkPst.executeQuery();

                if(rs.next()) {

                    String updateSql =
                            "UPDATE marks "
                                    + "SET marks=? "
                                    + "WHERE student_id=? "
                                    + "AND subject_id=?";

                    PreparedStatement updatePst =
                            conn.prepareStatement(updateSql);

                    updatePst.setInt(1, mark);

                    updatePst.setInt(2, studentId);

                    updatePst.setInt(3, selectedSubjectId);

                    updatePst.executeUpdate();

                    updatePst.close();

                } else {

                    String insertSql =
                            "INSERT INTO marks "
                                    + "(student_id, subject_id, marks) "
                                    + "VALUES (?, ?, ?)";

                    PreparedStatement insertPst =
                            conn.prepareStatement(insertSql);

                    insertPst.setInt(1, studentId);

                    insertPst.setInt(2, selectedSubjectId);

                    insertPst.setInt(3, mark);

                    insertPst.executeUpdate();

                    insertPst.close();
                }

                rs.close();

                checkPst.close();
            }

            lblMessage.setForeground(
                    successColor
            );

            lblMessage.setText(
                    "Marks Updated Successfully"
            );

            loadStudents();

        } catch(Exception e) {

            e.printStackTrace();

            lblMessage.setForeground(
                    dangerColor
            );

            lblMessage.setText(
                    "Failed to Save Marks"
            );

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    // =====================================
    // REFRESH DATA
    // =====================================

    public void refreshData() {

        loadDepartments();

        loadSubjects();

        model.setRowCount(0);

        lblMessage.setText("");
    }
}