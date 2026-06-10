package gui;

import auth.Authentication;
import student.StudentDashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPanel extends JPanel {

    JTextField txtUsername;
    JPasswordField txtPassword;
    JButton btnLogin;
    JButton btnShowPassword;
    JLabel lblMessage;

    CardLayout cardLayout;
    JPanel mainPanel;

    boolean passwordVisible = false;
    Timer messageTimer;

    // =====================================
    // COLORS
    // =====================================
    Color backgroundColor = Color.decode("#e1e1e1");
    Color leftPanelColor = Color.decode("#1E3A8A");
    Color cardColor = Color.WHITE;
    Color primaryColor = Color.decode("#2563EB");
    Color primaryHover = Color.decode("#1d4ed8");
    Color textDark = Color.decode("#0F172A");
    Color textLight = Color.decode("#64748B");
    Color borderColor = Color.decode("#CBD5E1");
    Color successColor = Color.decode("#16A34A");
    Color errorColor = Color.decode("#DC2626");

    public LoginPanel(CardLayout cardLayout, JPanel mainPanel) {
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;

        setLayout(new GridLayout(1, 2));
        setBackground(backgroundColor);

        // =====================================
        // LEFT PANEL (Improved Visual Depth)
        // =====================================
        GradientPanel leftPanel = new GradientPanel(
                Color.decode("#3a5ebe"),
                Color.decode("#0c225f").darker()
        );
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(80, 70, 80, 70));

        JLabel lblTitle = new JLabel("<html>STUDENT RECORD<br>MANAGEMENT SYSTEM</html>");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 42));
        lblTitle.setForeground(Color.WHITE);

        JLabel lblSubtitle = new JLabel("<html>Modern Academic Platform<br>with Secure Authentication</html>");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblSubtitle.setForeground(new Color(220, 220, 220));

        JPanel featureCard = createFeatureCard();

        JLabel lblFooter = new JLabel("SRMS Version 2.0");
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblFooter.setForeground(new Color(180, 180, 180));

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(lblTitle);
        leftPanel.add(Box.createVerticalStrut(25));
        leftPanel.add(lblSubtitle);
        leftPanel.add(Box.createVerticalStrut(45));
        leftPanel.add(featureCard);
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(lblFooter);

        // =====================================
        // RIGHT PANEL - More Premium Card
        // =====================================
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(backgroundColor);

        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setPreferredSize(new Dimension(560, 620));
        card.setBackground(cardColor);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 220), 1, true),
                new EmptyBorder(45, 48, 45, 48)
        ));

        // Top Section
        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel lblWelcome = new JLabel("WELCOME BACK!");
        lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 34));
        lblWelcome.setForeground(textDark);

        JLabel lblLoginText = new JLabel("Login to continue");
        lblLoginText.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLoginText.setForeground(textLight);
        lblLoginText.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        topPanel.add(lblWelcome);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(lblLoginText);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(13, 0, 13, 0);

        // Username
        gbc.gridy = 0;
        formPanel.add(createFieldLabel("USERNAME"), gbc);

        txtUsername = createStyledTextField();
        gbc.gridy++;
        formPanel.add(txtUsername, gbc);

        // Password
        gbc.gridy++;
        formPanel.add(createFieldLabel("PASSWORD"), gbc);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setOpaque(false);

        txtPassword = createStyledPasswordField();
        btnShowPassword = createShowPasswordButton();

        passwordPanel.add(txtPassword, BorderLayout.CENTER);
        passwordPanel.add(btnShowPassword, BorderLayout.EAST);

        gbc.gridy++;
        formPanel.add(passwordPanel, gbc);

        // Login Button
        btnLogin = createLoginButton();
        gbc.gridy++;
        gbc.insets = new Insets(32, 0, 12, 0);
        formPanel.add(btnLogin, gbc);

        // Message
        lblMessage = createMessageLabel();
        gbc.gridy++;
        gbc.insets = new Insets(18, 0, 0, 0);
        formPanel.add(lblMessage, gbc);

        card.add(topPanel, BorderLayout.NORTH);
        card.add(formPanel, BorderLayout.CENTER);

        rightPanel.add(card);

        add(leftPanel);
        add(rightPanel);

        // Events
        btnLogin.addActionListener(e -> login());
        txtPassword.addActionListener(e -> login());
        btnShowPassword.addActionListener(e -> togglePassword());
    }

    // =====================================
    // FEATURE CARD (Slightly refined)
    // =====================================
    private JPanel createFeatureCard() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(createFeatureLabel("Secure Authentication"));
        panel.add(Box.createVerticalStrut(18));
        panel.add(createFeatureLabel("Performance Analytics"));
        panel.add(Box.createVerticalStrut(18));
        panel.add(createFeatureLabel("Automatic Role Detection"));

        return panel;
    }

    private JLabel createFeatureLabel(String text) {
        JLabel label = new JLabel("•  " + text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return label;
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(textDark);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setPreferredSize(new Dimension(0, 55));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(borderColor, 1, true),
                new EmptyBorder(0, 18, 0, 18)
        ));
        addFocusEffect(field);
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setEchoChar('•');
        field.setPreferredSize(new Dimension(0, 55));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(borderColor, 1, true),
                new EmptyBorder(0, 18, 0, 18)
        ));
        addFocusEffect(field);
        return field;
    }

    private JButton createShowPasswordButton() {
        JButton btn = new JButton("SHOW");
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setForeground(primaryColor);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton createLoginButton() {
        JButton btn = new JButton("LOGIN");
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setBackground(primaryColor);
        btn.setForeground(Color.WHITE);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setPreferredSize(new Dimension(0, 58));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(primaryHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(primaryColor);
            }
        });
        return btn;
    }

    private JLabel createMessageLabel() {
        JLabel label = new JLabel("", JLabel.CENTER);
        label.setOpaque(true);
        label.setVisible(false);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setBorder(new EmptyBorder(14, 20, 14, 20));
        label.setPreferredSize(new Dimension(420, 55));
        return label;
    }

    private void addFocusEffect(JTextField field) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(primaryColor, 2, true),
                        new EmptyBorder(0, 18, 0, 18)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(borderColor, 1, true),
                        new EmptyBorder(0, 18, 0, 18)
                ));
            }
        });
    }

    // All methods below (showMessage, togglePassword, login) remain exactly the same
    private void showMessage(String message, boolean success) {
        if (messageTimer != null) messageTimer.stop();

        lblMessage.setText(message);
        lblMessage.setBackground(success ? successColor : errorColor);
        lblMessage.setVisible(true);

        messageTimer = new Timer(4000, e -> {
            lblMessage.setVisible(false);
            lblMessage.setText("");
        });
        messageTimer.setRepeats(false);
        messageTimer.start();
    }

    private void togglePassword() {
        if (!passwordVisible) {
            txtPassword.setEchoChar((char) 0);
            btnShowPassword.setText("HIDE");
            passwordVisible = true;
        } else {
            txtPassword.setEchoChar('•');
            btnShowPassword.setText("SHOW");
            passwordVisible = false;
        }
    }

    private void login() {
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Please fill all fields", false);
            return;
        }

        String dbRole = Authentication.login(username, password);

        if (dbRole == null) {
            showMessage("Invalid username or password", false);
            return;
        }

        showMessage("Login Successful", true);

        switch (dbRole.toUpperCase()) {
            case "ADMIN":
                cardLayout.show(mainPanel, "ADMIN");
                break;
            case "STAFF":
                cardLayout.show(mainPanel, "STAFF");
                break;
            case "STUDENT":
                try {
                    mainPanel.remove(mainPanel.getComponent(3));
                } catch (Exception ex) {}
                StudentDashboard studentDashboard = new StudentDashboard(Authentication.loggedStudentId);
                mainPanel.add(studentDashboard, "STUDENT");
                cardLayout.show(mainPanel, "STUDENT");
                break;
        }

        txtUsername.setText("");
        txtPassword.setText("");
        txtPassword.setEchoChar('•');
        btnShowPassword.setText("SHOW");
        passwordVisible = false;
    }
}