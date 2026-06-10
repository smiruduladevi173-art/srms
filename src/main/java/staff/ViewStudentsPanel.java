package staff;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewStudentsPanel extends JPanel {

    JTable table;

    DefaultTableModel model;

    JScrollPane scrollPane;

    JButton btnSave;

    JLabel lblStatus;

    JTextField txtSearch;

    TableRowSorter<DefaultTableModel> sorter;

    JPanel mainPanel;

    boolean isChanged = false;

    Color backgroundColor =
            new Color(245,247,250);

    Color cardColor =
            Color.WHITE;

    Color primaryColor =
            new Color(25,118,210);

    Color successColor =
            new Color(40,167,69);

    public ViewStudentsPanel() {

        setLayout(new BorderLayout());

        setBackground(backgroundColor);

        setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // =========================
        // MAIN CARD
        // =========================

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        card.setBackground(cardColor);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220,225,230),
                                1
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        add(card, BorderLayout.CENTER);

        // =========================
        // TOP PANEL
        // =========================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );

        topPanel.setOpaque(false);

        JLabel title =
                new JLabel(
                        "View Students"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        title.setForeground(
                new Color(33,37,41)
        );

        JLabel subtitle =
                new JLabel(
                        "Search and manage student records"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                new Color(108,117,125)
        );

        JPanel titlePanel =
                new JPanel(
                        new GridLayout(2,1)
                );

        titlePanel.setOpaque(false);

        titlePanel.add(title);

        titlePanel.add(subtitle);

        // =========================
        // SEARCH PANEL
        // =========================

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT
                        )
                );

        searchPanel.setOpaque(false);

        JLabel lblSearch =
                new JLabel("Search");

        lblSearch.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        txtSearch =
                new JTextField(20);

        txtSearch.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        txtSearch.setPreferredSize(
                new Dimension(
                        220,
                        38
                )
        );

        txtSearch.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(210,215,220),
                                1
                        ),
                        new EmptyBorder(
                                5,
                                12,
                                5,
                                12
                        )
                )
        );

        searchPanel.add(lblSearch);

        searchPanel.add(txtSearch);

        topPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        topPanel.add(
                searchPanel,
                BorderLayout.EAST
        );

        card.add(
                topPanel,
                BorderLayout.NORTH
        );

        // =========================
        // TABLE
        // =========================

        String[] columns = {

                "ID",

                "Roll No",

                "Name",

                "DOB",

                "Gender",

                "Department"
        };

        model =
                new DefaultTableModel(
                        columns,
                        0
                ) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column
                    ) {

                        return column != 0
                                && column != 5;
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

        table.setSelectionBackground(
                Color.decode("#518ac4")
        );

        table.setGridColor(
                new Color(235,235,235)
        );

        table.setShowVerticalLines(false);

        table.setIntercellSpacing(
                new Dimension(0,1)
        );

        JTableHeader header =
                table.getTableHeader();

        header.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        header.setBackground(primaryColor);

        header.setForeground(Color.WHITE);

        header.setPreferredSize(
                new Dimension(
                        header.getWidth(),
                        40
                )
        );

        sorter =
                new TableRowSorter<>(model);

        table.setRowSorter(sorter);

        scrollPane =
                new JScrollPane(table);

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        new Color(230,230,230),
                        1
                )
        );

        card.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =========================
        // BOTTOM PANEL
        // =========================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setOpaque(false);

        btnSave =
                new JButton(
                        "Save Changes"
                );

        btnSave.setFocusPainted(false);

        btnSave.setBackground(successColor);

        btnSave.setForeground(Color.WHITE);

        btnSave.setBorderPainted(false);

        btnSave.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        btnSave.setPreferredSize(
                new Dimension(
                        170,
                        42
                )
        );

        btnSave.setVisible(false);

        lblStatus =
                new JLabel("");

        lblStatus.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        bottomPanel.add(
                lblStatus,
                BorderLayout.WEST
        );

        bottomPanel.add(
                btnSave,
                BorderLayout.EAST
        );

        card.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =========================
        // SEARCH FILTER
        // =========================

        txtSearch.getDocument()
                .addDocumentListener(
                        new DocumentListener() {

                            public void insertUpdate(
                                    DocumentEvent e
                            ) {

                                filter();
                            }

                            public void removeUpdate(
                                    DocumentEvent e
                            ) {

                                filter();
                            }

                            public void changedUpdate(
                                    DocumentEvent e
                            ) {

                                filter();
                            }

                            private void filter() {

                                String text =
                                        txtSearch
                                                .getText();

                                if(text.trim().isEmpty()) {

                                    sorter.setRowFilter(
                                            null
                                    );

                                } else {

                                    sorter.setRowFilter(
                                            RowFilter.regexFilter(
                                                    "(?i)"
                                                            + text
                                            )
                                    );
                                }
                            }
                        }
                );

        // =========================
        // CHANGE DETECTION
        // =========================

        model.addTableModelListener(e -> {

            isChanged = true;

            btnSave.setVisible(true);
        });

        // =========================
        // SAVE BUTTON
        // =========================

        btnSave.addActionListener(e -> {

            int confirm =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Save changes?",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION
                    );

            if(confirm ==
                    JOptionPane.YES_OPTION) {

                saveChanges();

                showMessage(
                        "Updated Successfully",
                        successColor
                );

            } else {

                loadStudents();

                showMessage(
                        "Changes Discarded",
                        Color.RED
                );
            }

            btnSave.setVisible(false);

            isChanged = false;
        });
    }

    // =========================
    // REFRESH DATA
    // =========================

    public void refreshData() {

        loadStudents();

        revalidate();

        repaint();
    }

    // =========================
    // LOAD STUDENTS
    // =========================

    private void loadStudents() {

        try {

            model.setRowCount(0);

            Connection conn =
                    DBConnection.connect();

            String sql =
                    "SELECT s.student_id, " +
                    "s.roll_number, " +
                    "s.name, " +
                    "s.dob, " +
                    "s.gender, " +
                    "d.department_name " +
                    "FROM students s " +
                    "JOIN departments d " +
                    "ON s.department_id = d.id";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()) {

                model.addRow(
                        new Object[] {

                                rs.getInt(
                                        "student_id"
                                ),

                                rs.getString(
                                        "roll_number"
                                ),

                                rs.getString(
                                        "name"
                                ),

                                rs.getString(
                                        "dob"
                                ),

                                rs.getString(
                                        "gender"
                                ),

                                rs.getString(
                                        "department_name"
                                )
                        }
                );
            }

        } catch(Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to Load Students"
            );
        }
    }

    // =========================
    // SAVE CHANGES
    // =========================

    private void saveChanges() {

        try {

            Connection conn =
                    DBConnection.connect();

            String sql =
                    "UPDATE students " +
                    "SET " +
                    "roll_number=?, " +
                    "name=?, " +
                    "dob=?, " +
                    "gender=? " +
                    "WHERE student_id=?";

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            for(int i = 0;
                i < model.getRowCount();
                i++) {

                int id =
                        Integer.parseInt(
                                model.getValueAt(
                                        i,
                                        0
                                ).toString()
                        );

                String roll =
                        model.getValueAt(
                                i,
                                1
                        ).toString();

                String name =
                        model.getValueAt(
                                i,
                                2
                        ).toString();

                String dob =
                        model.getValueAt(
                                i,
                                3
                        ).toString();

                String gender =
                        model.getValueAt(
                                i,
                                4
                        ).toString();

                pst.setString(1, roll);

                pst.setString(2, name);

                pst.setString(3, dob);

                pst.setString(4, gender);

                pst.setInt(5, id);

                pst.executeUpdate();
            }

        } catch(Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Save Error : "
                            + e.getMessage()
            );
        }
    }

    // =========================
    // STATUS MESSAGE
    // =========================

    private void showMessage(
            String msg,
            Color color
    ) {

        lblStatus.setForeground(color);

        lblStatus.setText(msg);

        Timer timer =
                new Timer(
                        3000,
                        e -> lblStatus.setText("")
                );

        timer.setRepeats(false);

        timer.start();
    }
}