package staff;

import auth.Authentication;
import gui.MainFrame;
import student.StudentSession;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StaffDashboardPanel extends JPanel {

    // =====================================
    // PANEL CONSTANTS
    // =====================================

    private static final String VIEW_PANEL =
            "VIEW";

    private static final String MARKS_PANEL =
            "MARKS";

    private static final String RESULTS_PANEL =
            "RESULTS";

    // =====================================
    // COLOR THEME
    // =====================================

    private final Color backgroundColor =
            Color.decode("#F8FAFC");

    private final Color sidebarColor =
            Color.decode("#2461f0");

    private final Color contentColor =
            Color.WHITE;

    @SuppressWarnings("unused")
private final Color primaryColor =
            Color.decode("#3B82F6");

    @SuppressWarnings("unused")
private final Color primaryHover =
            Color.decode("#2563EB");

    private final Color dangerColor =
            Color.decode("#EF4444");

    private final Color dangerHover =
            Color.decode("#DC2626");

    @SuppressWarnings("unused")
private final Color textColor =
            Color.decode("#0F172A");

    // =====================================
    // CARDLAYOUT
    // =====================================

    private CardLayout cardLayout;

    private JPanel contentPanel;

    // =====================================
    // PANELS
    // =====================================

    private ViewStudentsPanel viewStudentsPanel;

    private UpdateMarksPanel updateMarksPanel;

    private ResultsPanel resultsPanel;

    private JButton btnViewStudents;

        private JButton btnUpdateMarks;

private JButton btnResults;

private JButton activeButton;

    // =====================================
    // CONSTRUCTOR
    // =====================================

    public StaffDashboardPanel() {

        setLayout(new BorderLayout());

        setBackground(backgroundColor);

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
        // SIDEBAR HEADER
        // =====================================

        JPanel topSidebar =
                new JPanel();

        topSidebar.setOpaque(false);

        topSidebar.setLayout(
                new BoxLayout(
                        topSidebar,
                        BoxLayout.Y_AXIS
                )
        );

        JLabel lblTitle =
                new JLabel("STAFF");

        
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
                        "Dashboard Panel"
                );

        lblSubtitle.setForeground(
                new Color(200,200,200)
        );

        lblSubtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        topSidebar.add(lblTitle);

        topSidebar.add(
                Box.createVerticalStrut(5)
        );

        topSidebar.add(lblSubtitle);

        // =====================================
        // MENU BUTTON PANEL
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
                        40,
                        0,
                        0,
                        0
                )
        );

        btnViewStudents =
        createSidebarButton(
                "View Students"
        );

btnUpdateMarks =
        createSidebarButton(
                "Update Marks"
        );

btnResults =
        createSidebarButton(
                "Analytics & Results"
        );

        JButton btnLogout =
                createLogoutButton(
                        "Logout"
                );

        menuPanel.add(btnViewStudents);

menuPanel.add(
        Box.createVerticalStrut(12)
);

menuPanel.add(btnUpdateMarks);

menuPanel.add(
        Box.createVerticalStrut(12)
);

menuPanel.add(btnResults);

menuPanel.add(
        Box.createVerticalGlue()
);

menuPanel.add(btnLogout);

        // =====================================
        // ADD TO SIDEBAR
        // =====================================

        sidebarPanel.add(
                topSidebar,
                BorderLayout.NORTH
        );

        sidebarPanel.add(
                menuPanel,
                BorderLayout.CENTER
        );

        add(
                sidebarPanel,
                BorderLayout.WEST
        );

        // =====================================
        // CONTENT PANEL
        // =====================================

        cardLayout =
                new CardLayout();

        contentPanel =
                new JPanel(cardLayout);

        contentPanel.setBackground(
                backgroundColor
        );

        contentPanel.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        // =====================================
        // CREATE PANELS
        // =====================================

        viewStudentsPanel =
                new ViewStudentsPanel();

        updateMarksPanel =
                new UpdateMarksPanel();

        resultsPanel =
                new ResultsPanel();

        // =====================================
        // WRAPPER PANELS
        // =====================================

        contentPanel.add(
                wrapPanel(viewStudentsPanel),
                VIEW_PANEL
        );

        contentPanel.add(
                wrapPanel(updateMarksPanel),
                MARKS_PANEL
        );

        contentPanel.add(
                wrapPanel(resultsPanel),
                RESULTS_PANEL
        );

        add(
                contentPanel,
                BorderLayout.CENTER
        );

        // =====================================
        // DEFAULT PANEL
        // =====================================

        viewStudentsPanel.refreshData();

        cardLayout.show(
        contentPanel,
        VIEW_PANEL
);

setActiveButton(
        btnViewStudents
);

        // =====================================
        // BUTTON EVENTS
        // =====================================

      btnViewStudents.addActionListener(e -> {

    setActiveButton(
            btnViewStudents
    );

    viewStudentsPanel.refreshData();

    cardLayout.show(
            contentPanel,
            VIEW_PANEL
    );
});

       btnUpdateMarks.addActionListener(e -> {

    setActiveButton(
            btnUpdateMarks
    );

    updateMarksPanel.refreshData();

    cardLayout.show(
            contentPanel,
            MARKS_PANEL
    );
});

      btnResults.addActionListener(e -> {

    setActiveButton(
            btnResults
    );

    resultsPanel.refreshData();

    cardLayout.show(
            contentPanel,
            RESULTS_PANEL
    );
});

        // =====================================
        // LOGOUT
        // =====================================

        btnLogout.addActionListener(e -> {

            StudentSession.loggedStudentId = -1;

            StudentSession.loggedInUserId = -1;

            Authentication.loggedStudentId = -1;

            MainFrame.cardLayout.show(
                    MainFrame.mainPanel,
                    "LOGIN"
            );
        });
    }

    // =====================================
    // WRAP PANEL INSIDE CARD
    // =====================================

    private JPanel wrapPanel(
            JPanel panel
    ) {

        JPanel wrapper =
                new JPanel(
                        new BorderLayout()
                );

        wrapper.setBackground(
                backgroundColor
        );

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );

        card.setBackground(
                contentColor
        );

        card.setBorder(
                new EmptyBorder(
                        20,
                        20,
                        20,
                        20
                )
        );

        card.add(
                panel,
                BorderLayout.CENTER
        );

        wrapper.add(
                card,
                BorderLayout.CENTER
        );

        return wrapper;
    }

    // =====================================
    // SIDEBAR BUTTON
    // =====================================
private void setActiveButton(
        JButton selectedButton
) {

    JButton[] buttons = {
            btnViewStudents,
            btnUpdateMarks,
            btnResults
    };

    for(JButton button : buttons) {

        button.setBackground(
                new Color(
                        255,
                        255,
                        255,
                        30
                )
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
            sidebarColor
    );

    selectedButton.setBorder(
            BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(
                            0,
                            5,
                            0,
                            0,
                            sidebarColor
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





    private JButton createSidebarButton(
        String text
) {

    JButton button =
            new JButton(text);

    button.setFocusPainted(false);

    button.setBorderPainted(false);

    button.setOpaque(true);

    button.setBackground(
            new Color(
                    255,
                    255,
                    255,
                    30
            )
    );

    button.setForeground(
            Color.WHITE
    );

    button.setHorizontalAlignment(
            SwingConstants.LEFT
    );

    button.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    15
            )
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

                    if(button != activeButton) {

                        button.setBackground(
                                new Color(
                                        255,
                                        255,
                                        255,
                                        60
                                )
                        );
                    }
                }

                @Override
                public void mouseExited(
                        MouseEvent e
                ) {

                    if(button != activeButton) {

                        button.setBackground(
                                new Color(
                                        255,
                                        255,
                                        255,
                                        30
                                )
                        );
                    }
                }
            }
    );

    return button;
}

    // =====================================
    // LOGOUT BUTTON
    // =====================================

   private JButton createLogoutButton(
        String text
) {

    JButton button =
            new JButton("Logout");

    button.setFocusPainted(false);

    button.setBorderPainted(false);

    button.setOpaque(true);

    button.setBackground(
            dangerColor
    );

    button.setForeground(
            Color.WHITE
    );

    button.setHorizontalAlignment(
            SwingConstants.LEFT
    );

    button.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    15
            )
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
                            dangerHover
                    );
                }

                @Override
                public void mouseExited(
                        MouseEvent e
                ) {

                    button.setBackground(
                            dangerColor
                    );
                }
            }
    );

    return button;
}
}