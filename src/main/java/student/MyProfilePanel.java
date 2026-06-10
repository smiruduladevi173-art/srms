package student;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MyProfilePanel extends JPanel {

    JLabel lblRollValue;
    JLabel lblNameValue;
    JLabel lblDobValue;
    JLabel lblGenderValue;
    JLabel lblDepartmentValue;

    int studentId;

    Color bgColor = Color.decode("#F8FAFC");
    Color cardColor = Color.WHITE;
    Color primaryColor = Color.decode("#2563EB");
    Color textDark = Color.decode("#0F172A");
    Color borderColor = new Color(220, 220, 220);

    Font titleFont = new Font("Segoe UI", Font.BOLD, 28);
    Font subtitleFont = new Font("Segoe UI", Font.PLAIN, 16);
    Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
    Font valueFont = new Font("Segoe UI", Font.PLAIN, 17);

    public MyProfilePanel(int studentId) {
        this.studentId = studentId;

        setLayout(new BorderLayout());
        setBackground(bgColor);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bgColor);

        // =========================
        // MAIN PROFILE CARD
        // =========================
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardColor);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(40, 40, 40, 40)
        ));
        card.setPreferredSize(new Dimension(560, 620));

        // =========================
        // PROFILE ICON
        // =========================
        JLabel lblIcon = new JLabel("🎓");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =========================
        // TITLE & SUBTITLE
        // =========================
        JLabel lblTitle = new JLabel("Student Profile");
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(textDark);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Personal Information");
        lblSubtitle.setFont(subtitleFont);
        lblSubtitle.setForeground(new Color(100, 116, 139));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =========================
        // ADD HEADER
        // =========================
        card.add(lblIcon);
        card.add(Box.createVerticalStrut(12));
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(4));
        card.add(lblSubtitle);
        card.add(Box.createVerticalStrut(40));

        // =========================
        // DETAILS
        // =========================
        card.add(createInfoPanel("👤", "Roll Number", lblRollValue = createValueLabel()));
        card.add(Box.createVerticalStrut(12));
        card.add(createInfoPanel("📝", "Name", lblNameValue = createValueLabel()));
        card.add(Box.createVerticalStrut(12));
        card.add(createInfoPanel("🎂", "Date of Birth", lblDobValue = createValueLabel()));
        card.add(Box.createVerticalStrut(12));
        card.add(createInfoPanel("⚧", "Gender", lblGenderValue = createValueLabel()));
        card.add(Box.createVerticalStrut(12));
        card.add(createInfoPanel("🏢", "Department", lblDepartmentValue = createValueLabel()));

        wrapper.add(card);
        add(wrapper, BorderLayout.CENTER);

        loadProfile();
    }

    // =====================================
    // CREATE INDIVIDUAL INFO PANEL (New Design)
    // =====================================
    private JPanel createInfoPanel(String emoji, String labelText, JLabel valueLabel) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(cardColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(borderColor, 1, true),
                new EmptyBorder(14, 18, 14, 18)
        ));

        // Left side - Emoji + Label
        JPanel leftPanel = new JPanel(new BorderLayout(12, 0));
        leftPanel.setBackground(cardColor);

        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(textDark);

        leftPanel.add(emojiLabel, BorderLayout.WEST);
        leftPanel.add(label, BorderLayout.CENTER);

        // Add to main panel
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(valueLabel, BorderLayout.CENTER);

        return panel;
    }

    // =====================================
    // CREATE VALUE LABEL
    // =====================================
    private JLabel createValueLabel() {
        JLabel label = new JLabel("-");
        label.setFont(valueFont);
        label.setForeground(primaryColor);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    // =====================================
    // LOAD PROFILE (Unchanged)
    // =====================================
    public void loadProfile() {
        try {
            Connection conn = DBConnection.connect();
            String sql = "SELECT s.roll_number, s.name, s.dob, s.gender, d.department_name " +
                        "FROM students s " +
                        "JOIN departments d ON s.department_id = d.id " +
                        "WHERE s.student_id = ?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, studentId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                lblRollValue.setText(rs.getString("roll_number"));
                lblNameValue.setText(rs.getString("name"));
                lblDobValue.setText(rs.getString("dob"));
                lblGenderValue.setText(rs.getString("gender"));
                lblDepartmentValue.setText(rs.getString("department_name"));
            } else {
                JOptionPane.showMessageDialog(this, "Student Profile Not Found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error Loading Profile");
        }
    }

    public void refreshData() {
        loadProfile();
    }
}