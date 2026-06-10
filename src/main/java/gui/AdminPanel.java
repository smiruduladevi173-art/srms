
package gui;

import admin.AddCoursePanel;
import admin.AddStudentPanel;
import admin.AddSubjectPanel;
import admin.AdminAnalyticsPanel;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminPanel extends JPanel {

    JButton btnCourse;
    JButton btnSubject;
    JButton btnStudent;
    JButton btnAnalytics;
    JButton btnLogout;

    JPanel contentPanel;

    CardLayout contentLayout;

    StatsCard studentCard;
    StatsCard subjectCard;
    StatsCard departmentCard;

// =====================================
// COLOR THEME
// =====================================


private final Color sidebarColor =
        Color.decode("#2461F0");


private final Color primaryColor =
        Color.decode("#3B82F6");


            @SuppressWarnings("unused")
        private JButton activeButton;

    public AdminPanel() {

        setLayout(new BorderLayout());

        setBackground(
                Color.decode("#F8FAFC")
        );

        // =====================================
        // SIDEBAR PANEL
        // =====================================
        // =====================================
// SIDEBAR PANEL
// =====================================

JPanel sidebarPanel =
        new JPanel();




        sidebarPanel.setBackground(
        sidebarColor
);

        sidebarPanel.setPreferredSize(
        new Dimension(240, 0)
);

        sidebarPanel.setLayout(
                new BorderLayout()
        );

sidebarPanel.setBorder(
        new EmptyBorder(
                30,
                20,
                30,
                20
        )
);

        // =====================================
        // TITLE
        // =====================================

        JPanel topPanel =
                new JPanel();

        topPanel.setOpaque(false);

        topPanel.setBorder(
                new EmptyBorder(
                        40,
                        20,
                        40,
                        20
                )
        );

        topPanel.setLayout(
                new BoxLayout(
                        topPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblTitle =
                new JLabel("ADMIN" );

        lblTitle.setForeground(Color.WHITE);

        lblTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        JLabel lblSubtitle =
                new JLabel("Dashboard Panel");

        lblSubtitle.setForeground(
        new Color(
                200,
                200,
                200
        )
);
        lblSubtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        topPanel.add(lblTitle);

        topPanel.add(
                Box.createVerticalStrut(8)
        );

        topPanel.add(lblSubtitle);

        sidebarPanel.add(
                topPanel,
                BorderLayout.NORTH
        );

        add(
        sidebarPanel,
        BorderLayout.WEST
);


// =====================================
// MENU PANEL
// =====================================

JPanel menuPanel = new JPanel();
menuPanel.setOpaque(false);
menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

btnCourse = createMenuButton("Course Management");
btnSubject = createMenuButton("Subject Management");
btnStudent = createMenuButton("Student Management");
btnAnalytics = createMenuButton("Analytics");

menuPanel.add(btnCourse);
menuPanel.add(Box.createVerticalStrut(15));

menuPanel.add(btnSubject);
menuPanel.add(Box.createVerticalStrut(15));

menuPanel.add(btnStudent);
menuPanel.add(Box.createVerticalStrut(15));

menuPanel.add(btnAnalytics);

sidebarPanel.add(menuPanel, BorderLayout.CENTER);


// =====================================
// LOGOUT PANEL
// =====================================

JPanel bottomPanel = new JPanel();
bottomPanel.setOpaque(false);
bottomPanel.setLayout(new BorderLayout());

btnLogout = createLogoutButton("Logout");

bottomPanel.add(btnLogout, BorderLayout.SOUTH);

sidebarPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        

        // =====================================
        // CONTENT PANEL
        // =====================================

        contentLayout =
                new CardLayout();

        contentPanel =
                new JPanel(contentLayout);

        contentPanel.setOpaque(false);

        contentPanel.setBorder(
                new EmptyBorder(
                        10,
                        20,
                        20,
                        20
                )
        );

        AddCoursePanel addCoursePanel =
                new AddCoursePanel();

        AddSubjectPanel addSubjectPanel =
                new AddSubjectPanel();

        AddStudentPanel addStudentPanel =
                new AddStudentPanel();

        AdminAnalyticsPanel analyticsPanel =
                new AdminAnalyticsPanel();

        contentPanel.add(
                addCoursePanel,
                "COURSE"
        );

        contentPanel.add(
                addSubjectPanel,
                "SUBJECT"
        );

        contentPanel.add(
                addStudentPanel,
                "STUDENT"
        );

        contentPanel.add(
                analyticsPanel,
                "ANALYTICS"
        );

        // =====================================
        // STATS PANEL
        // =====================================

        JPanel statsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                5
                        )
                );

        statsPanel.setOpaque(false);

        statsPanel.setBorder(
                new EmptyBorder(
                        5,
                        10,
                        5,
                        10
                )
        );

        studentCard =
                new StatsCard(
                        "Students",
                        String.valueOf(
                                getStudentCount()
                        ),
                        "👨‍🎓",
                        Color.decode("#0d137f"),
                        Color.decode("#2563EB")
                );

        subjectCard =
                new StatsCard(
                        "Subjects",
                        String.valueOf(
                                getSubjectCount()
                        ),
                        "📚",
                        Color.decode("#3b106c"),
                        Color.decode("#9d64ff")
                );

        departmentCard =
                new StatsCard(
                        "Departments",
                        String.valueOf(
                                getDepartmentCount()
                        ),
                        "🏫",
                        Color.decode("#ac0303"),
                        Color.decode("#f17645")
                );

        statsPanel.add(studentCard);

        statsPanel.add(subjectCard);

        statsPanel.add(departmentCard);

        // =====================================
        // RIGHT PANEL
        // =====================================

        JPanel rightPanel =
                new JPanel(
                        new BorderLayout()
                );

        rightPanel.setOpaque(false);

        rightPanel.add(
                statsPanel,
                BorderLayout.NORTH
        );

        rightPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );

        add(
                rightPanel,
                BorderLayout.CENTER
        );

        // =====================================
        // DEFAULT PANEL
        // =====================================

              contentLayout.show(
        contentPanel,
        "COURSE"
);

        setActiveButton (btnCourse);

        // =====================================
        // EVENTS
        // =====================================

        btnCourse.addActionListener(e -> {

    setActiveButton(btnCourse);

    addCoursePanel.refreshData();

    refreshStats();

    contentLayout.show(
            contentPanel,
            "COURSE"
    );
});



btnSubject.addActionListener(e -> {

    setActiveButton(btnSubject);

    addSubjectPanel.refreshData();

    refreshStats();

    contentLayout.show(
            contentPanel,
            "SUBJECT"
    );
});




       btnStudent.addActionListener(e -> {

    setActiveButton(btnStudent);

    addStudentPanel.refreshDepartments();

    refreshStats();

    contentLayout.show(
            contentPanel,
            "STUDENT"
    );
});





        btnAnalytics.addActionListener(e -> {

    setActiveButton(btnAnalytics);

    analyticsPanel.refreshData();

    refreshStats();

    contentLayout.show(
            contentPanel,
            "ANALYTICS"
    );
});

        btnLogout.addActionListener(e -> {

            MainFrame.showPanel(
                    "LOGIN"
            );
        });
    }

    // =====================================
    // REFRESH STATS
    // =====================================

  private void setActiveButton(
        JButton selectedButton
) {

    JButton[] buttons = {
            btnCourse,
            btnSubject,
            btnStudent,
            btnAnalytics
    };

    for (JButton button : buttons) {

       button.setBackground(
        primaryColor
);

       button.setForeground(
        Color.WHITE
);

        button.setBorder(
                new EmptyBorder(
                        15,
                        20,
                        15,
                        20
                )
        );
    }

    selectedButton.setBackground(
        Color.WHITE
);

    selectedButton.setForeground(
            primaryColor
    );

    selectedButton.setBorder(
            BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(
                            0,
                            5,
                            0,
                            0,
                            primaryColor
                    ),
                    new EmptyBorder(
                            15,
                            15,
                            15,
                            20
                    )
            )
    );

    activeButton = selectedButton;
}

    private void refreshStats() {

        studentCard.lblValue.setText(
                String.valueOf(
                        getStudentCount()
                )
        );

        subjectCard.lblValue.setText(
                String.valueOf(
                        getSubjectCount()
                )
        );

        departmentCard.lblValue.setText(
                String.valueOf(
                        getDepartmentCount()
                )
        );
    }

    // =====================================
    // DATABASE COUNTS
    // =====================================

    private int getStudentCount() {

        return getCount(
                "SELECT COUNT(*) FROM students"
        );
    }

    private int getSubjectCount() {

        return getCount(
                "SELECT COUNT(*) FROM subjects"
        );
    }

    private int getDepartmentCount() {

        return getCount(
                "SELECT COUNT(*) FROM departments"
        );
    }

    private int getCount(
            String query
    ) {

        int count = 0;

        try {

            Connection con =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(query);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                count =
                        rs.getInt(1);
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return count;
    }

    // =====================================
    // MENU BUTTON
    // =====================================

  private JButton createMenuButton(
        String text
) {

    JButton button =
            new JButton(text);

    button.setFocusPainted(false);

    button.setBorderPainted(false);

    button.setContentAreaFilled(true);

    button.setOpaque(true);

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

    button.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    15
            )
    );

    button.setHorizontalAlignment(
            SwingConstants.LEFT
    );

    button.setAlignmentX(
            Component.CENTER_ALIGNMENT
    );

   button.setPreferredSize(
        new Dimension(
                200,
                58
        )
);

button.setMaximumSize(
        new Dimension(
                220,
                52
        )
);

    return button;
}
private JButton createLogoutButton(
        String text
) {

    JButton button =
            new JButton(
                    "" + text
            );

    button.setFocusPainted(false);

    button.setBorderPainted(false);

    button.setContentAreaFilled(true);

    button.setOpaque(true);

    button.setBackground(
            new Color(
                    239,
                    68,
                    68
            ) // #EF4444
    );

    button.setForeground(
            Color.WHITE
    );

    button.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    15
            )
    );

    button.setHorizontalAlignment(
            SwingConstants.LEFT
    );

    button.setCursor(
            new Cursor(
                    Cursor.HAND_CURSOR
            )
    );

    button.setMaximumSize(
            new Dimension(
                    Integer.MAX_VALUE,
                    52
            )
    );

    button.setBorder(
            new EmptyBorder(
                    15,
                    20,
                    15,
                    20
            )
    );

    button.addMouseListener(
            new MouseAdapter() {

                @Override
                public void mouseEntered(
                        MouseEvent e
                ) {

                    button.setBackground(
                            new Color(
                                    220,
                                    38,
                                    38
                            ) // #DC2626
                    );
                }

                @Override
                public void mouseExited(
                        MouseEvent e
                ) {

                    button.setBackground(
                            new Color(
                                    239,
                                    68,
                                    68
                            ) // #EF4444
                    );
                }
            }
    );

    return button;
}
}