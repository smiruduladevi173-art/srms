package staff;

import gui.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StaffResultsPanel extends JPanel {

    JLabel departmentChart;

    JLabel subjectChart;

    JLabel lblStatus;

    JButton btnLoad;

    JButton btnBack;

    int departmentId;

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

    Color textDark =
            Color.decode("#0F172A");

    Color successColor =
            Color.decode("#16A34A");

    Color dangerColor =
            Color.decode("#DC2626");

    public StaffResultsPanel(
            int departmentId
    ) {

        this.departmentId =
                departmentId;

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

        JLabel lblTitle =
                new JLabel(
                        "STAFF ANALYTICS"
                );

        lblTitle.setForeground(
                Color.WHITE
        );

        lblTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        JLabel lblSubtitle =
                new JLabel(
                        "Department & Subject Performance Charts"
                );

        lblSubtitle.setForeground(
                new Color(230,230,230)
        );

        lblSubtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        JPanel titlePanel =
                new JPanel();

        titlePanel.setOpaque(false);

        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );

        titlePanel.add(lblTitle);

        titlePanel.add(
                Box.createVerticalStrut(5)
        );

        titlePanel.add(lblSubtitle);

        headerPanel.add(
                titlePanel,
                BorderLayout.WEST
        );

        add(
                headerPanel,
                BorderLayout.NORTH
        );

        // =====================================
        // CENTER PANEL
        // =====================================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout(
                                15,
                                15
                        )
                );

        centerPanel.setOpaque(false);

        // =====================================
        // ACTION PANEL
        // =====================================

        JPanel actionPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                10,
                                0
                        )
                );

        actionPanel.setOpaque(false);

        btnLoad =
                createPrimaryButton(
                        "GENERATE ANALYTICS"
                );

        btnBack =
                createSecondaryButton(
                        "BACK"
                );

        lblStatus =
                new JLabel("");

        lblStatus.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        actionPanel.add(btnLoad);

        actionPanel.add(btnBack);

        actionPanel.add(
                Box.createHorizontalStrut(10)
        );

        actionPanel.add(lblStatus);

        centerPanel.add(
                actionPanel,
                BorderLayout.NORTH
        );

        // =====================================
        // CHART PANEL
        // =====================================

        JPanel chartPanel =
                new JPanel();

        chartPanel.setOpaque(false);

        chartPanel.setLayout(
                new GridLayout(
                        2,
                        1,
                        20,
                        20
                )
        );

        // =====================================
        // DEPARTMENT CHART CARD
        // =====================================

        JPanel departmentCard =
                createChartCard(
                        "Department Performance"
                );

        departmentChart =
                new JLabel(
                        "Generate analytics to view chart",
                        JLabel.CENTER
                );

        departmentChart.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        departmentChart.setForeground(
                Color.GRAY
        );

        departmentCard.add(
                departmentChart,
                BorderLayout.CENTER
        );

        // =====================================
        // SUBJECT CHART CARD
        // =====================================

        JPanel subjectCard =
                createChartCard(
                        "Subject Wise Analysis"
                );

        subjectChart =
                new JLabel(
                        "Generate analytics to view chart",
                        JLabel.CENTER
                );

        subjectChart.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        subjectChart.setForeground(
                Color.GRAY
        );

        subjectCard.add(
                subjectChart,
                BorderLayout.CENTER
        );

        chartPanel.add(departmentCard);

        chartPanel.add(subjectCard);

        centerPanel.add(
                new JScrollPane(chartPanel),
                BorderLayout.CENTER
        );

        add(
                centerPanel,
                BorderLayout.CENTER
        );

        // =====================================
        // EVENTS
        // =====================================

        btnLoad.addActionListener(
                e -> loadCharts()
        );

        btnBack.addActionListener(
                e -> MainFrame.showPanel(
                        "STAFF"
                )
        );
    }

    // =====================================
    // CREATE CHART CARD
    // =====================================

    private JPanel createChartCard(
            String title
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout()
                );

        panel.setBackground(cardColor);

        panel.setBorder(
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

        JLabel lblTitle =
                new JLabel(title);

        lblTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        lblTitle.setForeground(textDark);

        lblTitle.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        15,
                        0
                )
        );

        panel.add(
                lblTitle,
                BorderLayout.NORTH
        );

        return panel;
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
                        210,
                        45
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
    // SECONDARY BUTTON
    // =====================================

    private JButton createSecondaryButton(
            String text
    ) {

        JButton button =
                new JButton(text);

        button.setPreferredSize(
                new Dimension(
                        120,
                        45
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

        button.setBackground(
                Color.WHITE
        );

        button.setForeground(
                textDark
        );

        button.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                new LineBorder(
                        borderColor,
                        1,
                        true
                )
        );

        return button;
    }

    // =====================================
    // LOAD CHARTS
    // =====================================

    private void loadCharts() {

        try {

            lblStatus.setForeground(
                    primaryColor
            );

            lblStatus.setText(
                    "Generating Charts..."
            );

            // =====================================
            // DEPARTMENT CHART
            // =====================================

            new ProcessBuilder(
                    "python",
                    "analytics/staff_department_chart.py",
                    String.valueOf(
                            departmentId
                    )
            ).start().waitFor();

            // =====================================
            // SUBJECT CHART
            // =====================================

            new ProcessBuilder(
                    "python",
                    "analytics/staff_subject_chart.py"
            ).start().waitFor();

            // =====================================
            // LOAD DEPARTMENT IMAGE
            // =====================================

            ImageIcon icon1 =
                    new ImageIcon(
                            "analytics/staff_department_chart.png"
                    );

            Image img1 =
                    icon1.getImage()
                            .getScaledInstance(
                                    850,
                                    320,
                                    Image.SCALE_SMOOTH
                            );

            departmentChart.setText("");

            departmentChart.setIcon(
                    new ImageIcon(img1)
            );

            // =====================================
            // LOAD SUBJECT IMAGE
            // =====================================

            ImageIcon icon2 =
                    new ImageIcon(
                            "analytics/staff_subject_chart.png"
                    );

            Image img2 =
                    icon2.getImage()
                            .getScaledInstance(
                                    850,
                                    320,
                                    Image.SCALE_SMOOTH
                            );

            subjectChart.setText("");

            subjectChart.setIcon(
                    new ImageIcon(img2)
            );

            lblStatus.setForeground(
                    successColor
            );

            lblStatus.setText(
                    "Charts Generated Successfully"
            );

        } catch (Exception e) {

            e.printStackTrace();

            lblStatus.setForeground(
                    dangerColor
            );

            lblStatus.setText(
                    "Failed to Generate Charts"
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to Generate Charts"
            );
        }
    }
}