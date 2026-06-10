package student;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MyMarksPanel extends JPanel {

    JTable table;

    DefaultTableModel model;

    JLabel lblMessage;

    Color backgroundColor =
            new Color(245,247,250);

    Color cardColor =
            Color.WHITE;

    Color primaryColor =
            new Color(25,118,210);

    // =========================
    // CONSTRUCTOR
    // =========================

    public MyMarksPanel() {

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
        // MAIN CARD PANEL
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
        // HEADER PANEL
        // =========================

        JPanel headerPanel =
                new JPanel(
                        new GridLayout(2,1)
                );

        headerPanel.setOpaque(false);

        JLabel lblTitle =
                new JLabel(
                        "My Academic Marks"
                );

        lblTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        lblTitle.setForeground(
                new Color(33,37,41)
        );

        JLabel lblSubtitle =
                new JLabel(
                        "View your semester-wise marks"
                );

        lblSubtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        lblSubtitle.setForeground(
                new Color(108,117,125)
        );

        headerPanel.add(lblTitle);

        headerPanel.add(lblSubtitle);

        card.add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =========================
        // TABLE MODEL
        // =========================

        model =
                new DefaultTableModel();

        model.addColumn("Subject");

        model.addColumn("Semester");

        model.addColumn("Marks");

        // =========================
        // TABLE
        // =========================

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

        table.setGridColor(
                new Color(235,235,235)
        );

        table.setShowVerticalLines(false);

        table.setIntercellSpacing(
                new Dimension(0,1)
        );

        table.setSelectionBackground(
                Color.decode("#00aaff")
        );
        //DCD6FA
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

        JScrollPane scrollPane =
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
        // STATUS LABEL
        // =========================

        lblMessage =
                new JLabel(
                        "",
                        JLabel.CENTER
                );

        lblMessage.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        lblMessage.setBorder(
                new EmptyBorder(
                        10,
                        0,
                        0,
                        0
                )
        );

        card.add(
                lblMessage,
                BorderLayout.SOUTH
        );

        // =========================
        // LOAD DATA
        // =========================

        loadMarks();
    }

    // =========================
    // REFRESH DATA
    // =========================

    public void refreshData() {

        loadMarks();

        revalidate();

        repaint();
    }

    // =========================
    // LOAD MARKS
    // =========================

    public void loadMarks() {

        // CLEAR OLD DATA

        model.setRowCount(0);

        try {

            Connection conn =
                    DBConnection.connect();

            String sql = """
                    SELECT
                    sub.subject_name,
                    sub.semester,
                    m.marks
                    FROM marks m
                    JOIN subjects sub
                    ON m.subject_id = sub.id
                    WHERE m.student_id = ?
                    ORDER BY sub.semester
                    """;

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            // LOGGED-IN STUDENT ID

            pst.setInt(
                    1,
                    StudentSession.loggedStudentId
            );

            System.out.println(
                    "StudentSession ID = "
                    + StudentSession.loggedStudentId
            );

            ResultSet rs =
                    pst.executeQuery();

            boolean found = false;

            while(rs.next()) {

                found = true;

                model.addRow(
                        new Object[] {

                                rs.getString(
                                        "subject_name"
                                ),

                                rs.getInt(
                                        "semester"
                                ),

                                rs.getInt(
                                        "marks"
                                )
                        }
                );
            }

            // =========================
            // STATUS MESSAGE
            // =========================

            if(!found) {

                lblMessage.setForeground(
                        Color.RED
                );

                lblMessage.setText(
                        "No Marks Available"
                );

            } else {

                lblMessage.setForeground(
                        new Color(40,167,69)
                );

                lblMessage.setText(
                        "Marks Loaded Successfully"
                );
            }

        } catch(Exception e) {

            e.printStackTrace();

            lblMessage.setForeground(
                    Color.RED
            );

            lblMessage.setText(
                    "Error Loading Marks"
            );

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}