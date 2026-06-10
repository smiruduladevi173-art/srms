package gui;

import auth.Authentication;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginUI extends JPanel {

    // =====================================
    // COMPONENTS
    // =====================================

    JTextField txtUsername;

    JPasswordField txtPassword;

    JComboBox<String> cmbRole;

    JButton btnLogin;

    JLabel lblMessage;

    // =====================================
    // CARDLAYOUT
    // =====================================

    CardLayout cardLayout;

    JPanel mainPanel;

    // =====================================
    // CONSTRUCTOR
    // =====================================

    public LoginUI(
            CardLayout cardLayout,
            JPanel mainPanel
    ) {

        this.cardLayout = cardLayout;

        this.mainPanel = mainPanel;

        // =====================================
        // PANEL DESIGN
        // =====================================

        setLayout(new GridBagLayout());

        setBackground(
                new Color(240,245,250)
        );

        GridBagConstraints gbc =
                new GridBagConstraints();

        gbc.insets =
                new Insets(10,10,10,10);

        // =====================================
        // MAIN CARD PANEL
        // =====================================

        JPanel cardPanel =
                new JPanel();

        cardPanel.setLayout(
                new BorderLayout(10,10)
        );

        cardPanel.setBackground(Color.WHITE);

        cardPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(220,220,220),
                                1
                        ),
                        new EmptyBorder(
                                25,
                                25,
                                25,
                                25
                        )
                )
        );

        // =====================================
        // TITLE SECTION
        // =====================================

        JPanel titlePanel =
                new JPanel(
                        new GridLayout(2,1)
                );

        titlePanel.setOpaque(false);

        JLabel lblTitle =
                new JLabel(
                        "STUDENT RECORD MANAGEMENT SYSTEM",
                        JLabel.CENTER
                );

        lblTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        lblTitle.setForeground(
                new Color(25,25,25)
        );

        JLabel lblSubtitle =
                new JLabel(
                        "Secure Login Portal",
                        JLabel.CENTER
                );

        lblSubtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        lblSubtitle.setForeground(
                Color.GRAY
        );

        titlePanel.add(lblTitle);

        titlePanel.add(lblSubtitle);

        cardPanel.add(
                titlePanel,
                BorderLayout.NORTH
        );

        // =====================================
        // FORM PANEL
        // =====================================

        JPanel formPanel =
                new JPanel();

        formPanel.setOpaque(false);

        formPanel.setLayout(
                new GridBagLayout()
        );

        GridBagConstraints fgc =
                new GridBagConstraints();

        fgc.insets =
                new Insets(8,8,8,8);

        fgc.fill =
                GridBagConstraints.HORIZONTAL;

        // =====================================
        // USERNAME
        // =====================================

        fgc.gridx = 0;
        fgc.gridy = 0;

        JLabel lblUsername =
                new JLabel("Username");

        lblUsername.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        formPanel.add(
                lblUsername,
                fgc
        );

        fgc.gridx = 1;

        txtUsername =
                new JTextField(18);

        txtUsername.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        txtUsername.setPreferredSize(
                new Dimension(220,35)
        );

        formPanel.add(txtUsername, fgc);

        // =====================================
        // PASSWORD
        // =====================================

        fgc.gridx = 0;
        fgc.gridy++;

        JLabel lblPassword =
                new JLabel("Password");

        lblPassword.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        formPanel.add(
                lblPassword,
                fgc
        );

        fgc.gridx = 1;

        txtPassword =
                new JPasswordField(18);

        txtPassword.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        txtPassword.setPreferredSize(
                new Dimension(220,35)
        );

        formPanel.add(
                txtPassword,
                fgc
        );

        // =====================================
        // ROLE
        // =====================================

        fgc.gridx = 0;
        fgc.gridy++;

        JLabel lblRole =
                new JLabel("Role");

        lblRole.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        formPanel.add(
                lblRole,
                fgc
        );

        fgc.gridx = 1;

        cmbRole =
                new JComboBox<>(
                        new String[] {
                                "ADMIN",
                                "STAFF",
                                "STUDENT"
                        }
                );

        cmbRole.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        cmbRole.setPreferredSize(
                new Dimension(220,35)
        );

        formPanel.add(
                cmbRole,
                fgc
        );

        // =====================================
        // LOGIN BUTTON
        // =====================================

        fgc.gridx = 0;
        fgc.gridy++;

        fgc.gridwidth = 2;

        btnLogin =
                new JButton("LOGIN");

        btnLogin.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        btnLogin.setFocusPainted(false);

        btnLogin.setBackground(
                new Color(0,123,255)
        );

        btnLogin.setForeground(Color.WHITE);

        btnLogin.setPreferredSize(
                new Dimension(220,40)
        );

        formPanel.add(
                btnLogin,
                fgc
        );

        // =====================================
        // MESSAGE LABEL
        // =====================================

        fgc.gridy++;

        lblMessage =
                new JLabel(
                        "",
                        JLabel.CENTER
                );

        lblMessage.setForeground(Color.RED);

        lblMessage.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        formPanel.add(
                lblMessage,
                fgc
        );

        cardPanel.add(
                formPanel,
                BorderLayout.CENTER
        );

        // =====================================
        // ADD CARD PANEL
        // =====================================

        add(cardPanel, gbc);

        // =====================================
        // LOGIN BUTTON EVENT
        // =====================================

        btnLogin.addActionListener(
                e -> login()
        );
    }

    // =====================================
    // LOGIN METHOD
    // =====================================

    private void login() {

        String username =
                txtUsername.getText();

        String password =
                String.valueOf(
                        txtPassword.getPassword()
                );

        String selectedRole =
                cmbRole.getSelectedItem()
                        .toString();

        // =====================================
        // VALIDATION
        // =====================================

        if (username.isEmpty()
                || password.isEmpty()) {

            lblMessage.setText(
                    "Fill all fields"
            );

            return;
        }

        // =====================================
        // CHECK DATABASE
        // =====================================

        String dbRole =
                Authentication.login(
                        username,
                        password
                );

        if (dbRole == null) {

            lblMessage.setText(
                    "Invalid Credentials"
            );

            return;
        }

        // =====================================
        // ROLE CHECK
        // =====================================

        if (!dbRole.equals(selectedRole)) {

            lblMessage.setText(
                    "Wrong Role Selected"
            );

            return;
        }

        // =====================================
        // OPEN DASHBOARD
        // =====================================

        switch (dbRole) {

            case "ADMIN":

                cardLayout.show(
                        mainPanel,
                        "ADMIN"
                );

                break;

            case "STAFF":

                cardLayout.show(
                        mainPanel,
                        "STAFF"
                );

                break;

            case "STUDENT":

                cardLayout.show(
                        mainPanel,
                        "STUDENT"
                );

                break;
        }
    }
}