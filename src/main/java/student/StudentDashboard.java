package student;

import auth.Authentication;
import gui.MainFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentDashboard extends JPanel {

    // =====================================
    // STUDENT ID
    // =====================================

    int studentId;

    // =====================================
    // CARD LAYOUT
    // =====================================

    CardLayout cardLayout;

    JPanel contentPanel;

    JLabel lblHeaderTitle;

    JLabel lblHeaderDesc;

    // =====================================
    // COLORS
    // =====================================

    Color sidebarColor =
            Color.decode("#1d4ed8");

    Color sidebarHover =
            Color.decode("#1E293B");

    Color backgroundColor =
            Color.decode("#F1F5F9");

    Color cardColor =
            Color.WHITE;

    Color primaryColor =
            Color.decode("#2563EB");

    Color primaryHover =
            Color.decode("#1D4ED8");

    Color dangerColor =
            Color.decode("#DC2626");

    Color dangerHover =
            Color.decode("#B91C1C");

    Color textDark =
            Color.decode("#0F172A");

    Color textLight =
            Color.decode("#64748B");

    Color borderColor =
            Color.decode("#E2E8F0");

    // =====================================
    // CONSTRUCTOR
    // =====================================

    public StudentDashboard(int loggedStudentId) {

        this.studentId = loggedStudentId;

        setLayout(new BorderLayout());

        setBackground(backgroundColor);

        // =====================================
        // SIDEBAR
        // =====================================

        JPanel sidebar =
                new JPanel(
                        new BorderLayout()
                );

        sidebar.setPreferredSize(
                new Dimension(260, 0)
        );

        sidebar.setBackground(sidebarColor);

        // =====================================
        // TOP SIDEBAR
        // =====================================

        JPanel topPanel =
                new JPanel();

        topPanel.setOpaque(false);

        topPanel.setLayout(
                new BoxLayout(
                        topPanel,
                        BoxLayout.Y_AXIS
                )
        );

        topPanel.setBorder(
                new EmptyBorder(
                        40,
                        30,
                        30,
                        30
                )
        );

        JLabel lblLogo =
                new JLabel("STUDENT");

        lblLogo.setForeground(Color.WHITE);

        lblLogo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        34
                )
        );

        JLabel lblPortal =
                new JLabel("Student Portal");

        lblPortal.setForeground(
                new Color(180, 180, 180)
        );

        lblPortal.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );

        topPanel.add(lblLogo);

        topPanel.add(
                Box.createVerticalStrut(6)
        );

        topPanel.add(lblPortal);

        // =====================================
        // MENU PANEL
        // =====================================

        JPanel menuPanel =
                new JPanel();

        menuPanel.setOpaque(false);

        menuPanel.setLayout(
                new BoxLayout(
                        menuPanel,
                        BoxLayout.Y_AXIS
                )
        );

        menuPanel.setBorder(
                new EmptyBorder(
                        20,
                        18,
                        20,
                        18
                )
        );

        JButton profileBtn =
                createSidebarButton(
                        "My Profile",
                        primaryColor,
                        primaryHover
                );

        JButton marksBtn =
                createSidebarButton(
                        "My Marks",
                        primaryColor,
                        primaryHover
                );

        JButton performanceBtn =
                createSidebarButton(
                        "Performance",
                        primaryColor,
                        primaryHover
                );

        JButton logoutBtn =
                createSidebarButton(
                        "Logout",
                        dangerColor,
                        dangerHover
                );

        menuPanel.add(profileBtn);

        menuPanel.add(
                Box.createVerticalStrut(15)
        );

        menuPanel.add(marksBtn);

        menuPanel.add(
                Box.createVerticalStrut(15)
        );

        menuPanel.add(performanceBtn);

        // =====================================
        // BOTTOM PANEL
        // =====================================

        JPanel bottomPanel =
                new JPanel(
                        new BorderLayout()
                );

        bottomPanel.setOpaque(false);

        bottomPanel.setBorder(
                new EmptyBorder(
                        20,
                        18,
                        30,
                        18
                )
        );

        bottomPanel.add(
                logoutBtn,
                BorderLayout.CENTER
        );

        // =====================================
        // SIDEBAR ADD
        // =====================================

        sidebar.add(
                topPanel,
                BorderLayout.NORTH
        );

        sidebar.add(
                menuPanel,
                BorderLayout.CENTER
        );

        sidebar.add(
                bottomPanel,
                BorderLayout.SOUTH
        );

        // =====================================
        // MAIN CONTENT PANEL
        // =====================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                0,
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
        // HEADER PANEL
        // =====================================

        JPanel headerPanel =
                new JPanel(
                        new BorderLayout()
                );

        headerPanel.setBackground(cardColor);

        headerPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        new LineBorder(
                                borderColor,
                                1,
                                true
                        ),
                        new EmptyBorder(
                                25,
                                30,
                                25,
                                30
                        )
                )
        );

        lblHeaderTitle =
                new JLabel(
                        "Student Dashboard"
                );

        lblHeaderTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        lblHeaderTitle.setForeground(textDark);

        lblHeaderDesc =
                new JLabel(
                        "Manage your academic information"
                );

        lblHeaderDesc.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        lblHeaderDesc.setForeground(textLight);

        JPanel headerText =
                new JPanel();

        headerText.setOpaque(false);

        headerText.setLayout(
                new BoxLayout(
                        headerText,
                        BoxLayout.Y_AXIS
                )
        );

        headerText.add(lblHeaderTitle);

        headerText.add(
                Box.createVerticalStrut(6)
        );

        headerText.add(lblHeaderDesc);

        headerPanel.add(
                headerText,
                BorderLayout.WEST
        );

        // =====================================
        // CONTENT PANEL
        // =====================================

        cardLayout =
                new CardLayout();

        contentPanel =
                new JPanel(cardLayout);

        contentPanel.setOpaque(false);

        // =====================================
        // PANELS
        // =====================================

        MyProfilePanel profilePanel =
                new MyProfilePanel(
                        Authentication.loggedStudentId
                );

        MyMarksPanel marksPanel =
                new MyMarksPanel();

        PerformancePanel performancePanel =
                new PerformancePanel(
                        Authentication.loggedStudentId
                );

        contentPanel.add(
                profilePanel,
                "PROFILE"
        );

        contentPanel.add(
                marksPanel,
                "MARKS"
        );

        contentPanel.add(
                performancePanel,
                "PERFORMANCE"
        );

        // =====================================
        // MAIN PANEL ADD
        // =====================================

        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );

        mainPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        // =====================================
        // FINAL ADD
        // =====================================

        add(
                sidebar,
                BorderLayout.WEST
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );

        // =====================================
        // DEFAULT PAGE
        // =====================================

        profilePanel.refreshData();

        cardLayout.show(
                contentPanel,
                "PROFILE"
        );

        // =====================================
        // PROFILE BUTTON
        // =====================================

        profileBtn.addActionListener(e -> {

            profilePanel.refreshData();

            lblHeaderTitle.setText(
                    "My Profile"
            );

            lblHeaderDesc.setText(
                    "View your personal and academic details"
            );

            cardLayout.show(
                    contentPanel,
                    "PROFILE"
            );
        });

        // =====================================
        // MARKS BUTTON
        // =====================================

        marksBtn.addActionListener(e -> {

            marksPanel.refreshData();

            lblHeaderTitle.setText(
                    "My Marks"
            );

            lblHeaderDesc.setText(
                    "Check your semester subject marks"
            );

            cardLayout.show(
                    contentPanel,
                    "MARKS"
            );
        });

        // =====================================
        // PERFORMANCE BUTTON
        // =====================================

        performanceBtn.addActionListener(e -> {

            performancePanel.refreshData();

            lblHeaderTitle.setText(
                    "Performance Analytics"
            );

            lblHeaderDesc.setText(
                    "Visual overview of your academic performance"
            );

            cardLayout.show(
                    contentPanel,
                    "PERFORMANCE"
            );
        });

        // =====================================
        // LOGOUT BUTTON
        // =====================================

        logoutBtn.addActionListener(e -> {

            Authentication.loggedStudentId = -1;

            JOptionPane optionPane =
                    new JOptionPane(
                            "Logged Out Successfully",
                            JOptionPane.INFORMATION_MESSAGE
                    );

            JDialog dialog =
                    optionPane.createDialog(
                            this,
                            "Logout"
                    );

            dialog.setSize(
                    420,
                    180
            );

            dialog.setLocationRelativeTo(this);

            dialog.setVisible(true);

            MainFrame.showPanel("LOGIN");
        });
    }

    // =====================================
    // SIDEBAR BUTTON
    // =====================================

    private JButton createSidebarButton(
            String text,
            Color bg,
            Color hover
    ) {

        JButton btn =
                new JButton(text);

        btn.setFocusPainted(false);

        btn.setBorderPainted(false);

        btn.setCursor(
                new Cursor(
                        Cursor.HAND_CURSOR
                )
        );

        btn.setBackground(bg);

        btn.setForeground(Color.WHITE);

        btn.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        btn.setPreferredSize(
                new Dimension(
                        200,
                        52
                )
        );

        btn.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        52
                )
        );

        btn.setBorder(
                new EmptyBorder(
                        12,
                        20,
                        12,
                        20
                )
        );

        btn.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        // =====================================
        // HOVER EFFECT
        // =====================================

        btn.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseEntered(
                            MouseEvent e
                    ) {

                        btn.setBackground(hover);
                    }

                    @Override
                    public void mouseExited(
                            MouseEvent e
                    ) {

                        btn.setBackground(bg);
                    }
                }
        );

        return btn;
    }
}