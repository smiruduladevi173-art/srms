package staff;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ResultsPanel extends JPanel {

    JButton btnGenerate;

    JComboBox<String> cmbDepartment;

    JLabel chartLabel;

    JLabel lblStatus;

    JScrollPane scrollPane;

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

    Color borderColor =
            Color.decode("#CBD5E1");

    Color successColor =
            Color.decode("#16A34A");

    Color dangerColor =
            Color.decode("#DC2626");

    public ResultsPanel() {

        setLayout(new BorderLayout());

        setBackground(backgroundColor);

        // =====================================
        // MAIN PANEL
        // =====================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        mainPanel.setOpaque(false);

        mainPanel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // =====================================
        // TOP ACTION PANEL
        // =====================================

        JPanel actionPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                0,
                                0
                        )
                );

        actionPanel.setOpaque(false);

        // =====================================
        // DEPARTMENT COMBOBOX
        // =====================================

        cmbDepartment =
                new JComboBox<>();

        cmbDepartment.setPreferredSize(
                new Dimension(
                        220,
                        48
                )
        );

        cmbDepartment.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        loadDepartments();

        // =====================================
        // BUTTON
        // =====================================

        btnGenerate =
                createPrimaryButton(
                        "GENERATE ANALYTICS"
                );

        // =====================================
        // STATUS LABEL
        // =====================================

        lblStatus =
                new JLabel("");

        lblStatus.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        // =====================================
        // ADD COMPONENTS
        // =====================================

        actionPanel.add(cmbDepartment);

        actionPanel.add(
                Box.createHorizontalStrut(15)
        );

        actionPanel.add(btnGenerate);

        actionPanel.add(
                Box.createHorizontalStrut(15)
        );

        actionPanel.add(lblStatus);

        mainPanel.add(
                actionPanel,
                BorderLayout.NORTH
        );

        // =====================================
        // CHART CARD
        // =====================================

        JPanel chartCard =
                new JPanel(
                        new BorderLayout()
                );

        chartCard.setBackground(cardColor);

        chartCard.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                borderColor,
                                1,
                                true
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        // =====================================
        // CHART LABEL
        // =====================================

        chartLabel =
                new JLabel();

        chartLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        chartLabel.setVerticalAlignment(
                SwingConstants.CENTER
        );

        chartLabel.setOpaque(true);

        chartLabel.setBackground(
                Color.decode("#e4e5e9")
        );

        chartLabel.setText("Click 'Generate Analytics' to display charts");

        // =====================================
        // SCROLL PANE
        // =====================================

        scrollPane =
                new JScrollPane(chartLabel);

        scrollPane.setBorder(null);

        scrollPane.getViewport().setBackground(
                Color.decode("#0F172A")
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        chartCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                chartCard,
                BorderLayout.CENTER
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );

        // =====================================
        // BUTTON EVENT
        // =====================================

        btnGenerate.addActionListener(
                e -> generateChart()
        );
    }

    // =====================================
    // LOAD DEPARTMENTS
    // =====================================

    private void loadDepartments() {

        cmbDepartment.removeAllItems();

        try {

            Connection con =
                    DBConnection.connect();

            String sql =
                    """
                    SELECT department_name
                    FROM departments
                    ORDER BY department_name
                    """;

            PreparedStatement pst =
                    con.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()) {

                cmbDepartment.addItem(
                        rs.getString(
                                "department_name"
                        )
                );
            }

        } catch(Exception e) {

            e.printStackTrace();
        }
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
                        220,
                        48
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

        button.setBackground(primaryColor);

        button.setForeground(Color.WHITE);

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
    // REFRESH DATA
    // =====================================

    public void refreshData() {

        chartLabel.setIcon(null);

        chartLabel.setText("");

        lblStatus.setText("");

        loadDepartments();

        revalidate();

        repaint();
    }

  // =====================================
// GENERATE CHART
// =====================================

private void generateChart() {

    try {

        String department =
                cmbDepartment
                        .getSelectedItem()
                        .toString();

        lblStatus.setForeground(primaryColor);

        lblStatus.setText(
                "Generating analytics..."
        );

        System.out.println(
                cmbDepartment.getSelectedItem()
        );

        // =====================================
        // RUN PYTHON SCRIPT
        // =====================================

        ProcessBuilder pb =
                new ProcessBuilder(
                        "python",
                        "analytics/charts.py",
                        department
                );

        pb.redirectErrorStream(true);

        Process process = pb.start();

        int exitCode = process.waitFor();

        // =====================================
        // CHECK IF PYTHON FAILED
        // =====================================

        if(exitCode != 0) {

            lblStatus.setForeground(
                    dangerColor
            );

            lblStatus.setText(
                    "Python chart generation failed"
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Python script failed"
            );

            return;
        }

        // =====================================
        // CLEAR OLD IMAGE
        // =====================================

        chartLabel.setIcon(null);

        // =====================================
        // LOAD NEW IMAGE
        // =====================================

        Image image =
                Toolkit.getDefaultToolkit()
                        .createImage(
                                "analytics/chart.png"
                        );

        ImageIcon icon =
                new ImageIcon(image);

        // =====================================
        // IMAGE VALIDATION
        // =====================================

        if(icon.getIconWidth() <= 0) {

            lblStatus.setForeground(
                    dangerColor
            );

            lblStatus.setText(
                    "Chart image not found"
            );

            JOptionPane.showMessageDialog(
                    this,
                    "chart.png was not generated"
            );

            return;
        }

        // =====================================
        // DISPLAY IMAGE
        // =====================================

        chartLabel.setText("");

        chartLabel.setIcon(icon);

       

        chartLabel.revalidate();

        chartLabel.repaint();

        scrollPane.revalidate();

        scrollPane.repaint();

        // =====================================
        // SUCCESS
        // =====================================

        lblStatus.setForeground(
                successColor
        );

        lblStatus.setText(
                "Analytics generated successfully"
        );

    } catch (
            IOException |
            InterruptedException e
    ) {

        e.printStackTrace();

        lblStatus.setForeground(
                dangerColor
        );

        lblStatus.setText(
                "Failed to generate analytics"
        );

        JOptionPane.showMessageDialog(
                this,
                e.getMessage()
        );
    }
}
}