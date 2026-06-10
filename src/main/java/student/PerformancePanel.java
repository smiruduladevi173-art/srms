package student;

import database.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PerformancePanel extends JPanel {

    JLabel lblAverageValue;
    JLabel lblTotalSubjectsValue;
    JLabel lblPassPercentageValue;
    JLabel lblPerformanceValue;
    JLabel lblStatus;

    int studentId;

    // =========================
    // COLORS
    // =========================
    Color bgColor = Color.decode("#F8FAFC");
    Color cardColor = Color.WHITE;
    Color primaryColor = Color.decode("#2563EB");
    Color successColor = Color.decode("#10B981");
    Color warningColor = Color.decode("#F59E0B");
    Color dangerColor = Color.decode("#EF4444");
    Color textDark = Color.decode("#0F172A");
    Color borderColor = new Color(220, 220, 220);

    // =========================
    // FONTS
    // =========================
    Font titleFont = new Font("Segoe UI", Font.BOLD, 28);
    Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
    Font valueFont = new Font("Segoe UI", Font.BOLD, 24);
    Font statusFont = new Font("Segoe UI", Font.BOLD, 18);

    public PerformancePanel(int studentId) {
        this.studentId = studentId;

        setLayout(new BorderLayout());
        setBackground(bgColor);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(bgColor);

        // =========================
        // MAIN CARD
        // =========================
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(cardColor);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(40, 40, 40, 40)
        ));
        card.setPreferredSize(new Dimension(680, 580));

        // =========================
        // HEADER
        // =========================
        JLabel lblIcon = new JLabel("📊");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 72));
        lblIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("Performance Report");
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(textDark);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lblIcon);
        card.add(Box.createVerticalStrut(12));
        card.add(lblTitle);
        card.add(Box.createVerticalStrut(40));

        // =========================
        // STATS CONTAINER
        // =========================
        JPanel statsContainer = new JPanel();
        statsContainer.setLayout(new BoxLayout(statsContainer, BoxLayout.Y_AXIS));
        statsContainer.setBackground(cardColor);

        // Add each stat in modern box
        statsContainer.add(createStatPanel("📈", "Average Marks", lblAverageValue = createValueLabel()));
        statsContainer.add(Box.createVerticalStrut(14));
        statsContainer.add(createStatPanel("📚", "Total Subjects", lblTotalSubjectsValue = createValueLabel()));
        statsContainer.add(Box.createVerticalStrut(14));
        statsContainer.add(createStatPanel("✅", "Pass Percentage", lblPassPercentageValue = createValueLabel()));
        statsContainer.add(Box.createVerticalStrut(14));
        statsContainer.add(createStatPanel("🏆", "Performance", lblPerformanceValue = createValueLabel()));

        card.add(statsContainer);
        card.add(Box.createVerticalStrut(45));

        // =========================
        // STATUS
        // =========================
        lblStatus = new JLabel("", JLabel.CENTER);
        lblStatus.setFont(statusFont);
        lblStatus.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(cardColor);
        statusPanel.setBorder(new EmptyBorder(12, 30, 12, 30));
        statusPanel.add(lblStatus);

        card.add(statusPanel);

        wrapper.add(card);
        add(wrapper, BorderLayout.CENTER);

        loadPerformance();
    }

    // =====================================
    // CREATE STAT PANEL (Modern Box Style)
    // =====================================
    private JPanel createStatPanel(String emoji, String labelText, JLabel valueLabel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(cardColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(borderColor, 1, true),
                new EmptyBorder(18, 22, 18, 22)
        ));

        // Left: Emoji + Label
        JPanel left = new JPanel(new BorderLayout(14, 0));
        left.setBackground(cardColor);

        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));

        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        label.setForeground(textDark);

        left.add(emojiLabel, BorderLayout.WEST);
        left.add(label, BorderLayout.CENTER);

        panel.add(left, BorderLayout.WEST);
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

    // =========================
    // REFRESH DATA
    // =========================
    public void refreshData() {
        loadPerformance();
        revalidate();
        repaint();
    }

    // =========================
    // LOAD PERFORMANCE (Logic unchanged)
    // =========================
    public void loadPerformance() {
        try {
            Connection conn = DBConnection.connect();

            String sql = """
                    SELECT
                    AVG(m.marks) AS average_marks,
                    COUNT(m.mark_id) AS total_subjects,
                    SUM(CASE WHEN m.marks >= 50 THEN 1 ELSE 0 END) AS passed_subjects
                    FROM marks m
                    JOIN students s ON m.student_id = s.student_id
                    WHERE s.user_id = ?
                    """;

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, StudentSession.loggedInUserId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                double average = rs.getDouble("average_marks");
                int totalSubjects = rs.getInt("total_subjects");
                int passedSubjects = rs.getInt("passed_subjects");

                double passPercentage = totalSubjects > 0 ?
                        ((double) passedSubjects / totalSubjects) * 100 : 0;

                lblAverageValue.setText(String.format("%.2f", average));
                lblTotalSubjectsValue.setText(String.valueOf(totalSubjects));
                lblPassPercentageValue.setText(String.format("%.2f%%", passPercentage));

                // Performance Logic (unchanged)
                String performanceText;
                Color performanceColor;

                if (average >= 90) {
                    performanceText = "Excellent";
                    performanceColor = successColor;
                } else if (average >= 75) {
                    performanceText = "Very Good";
                    performanceColor = successColor;
                } else if (average >= 60) {
                    performanceText = "Good";
                    performanceColor = primaryColor;
                } else if (average >= 50) {
                    performanceText = "Average";
                    performanceColor = warningColor;
                } else {
                    performanceText = "Needs Improvement";
                    performanceColor = dangerColor;
                }

                lblPerformanceValue.setText(performanceText);
                lblPerformanceValue.setForeground(performanceColor);

                lblStatus.setText("Performance Analysis Loaded Successfully");
                lblStatus.setForeground(successColor);

            } else {
                showNoData();
            }

        } catch (Exception e) {
            e.printStackTrace();
            lblStatus.setText("Error Loading Performance Data");
            lblStatus.setForeground(dangerColor);
        }
    }

    private void showNoData() {
        lblAverageValue.setText("0");
        lblTotalSubjectsValue.setText("0");
        lblPassPercentageValue.setText("0%");
        lblPerformanceValue.setText("No Data");
        lblPerformanceValue.setForeground(dangerColor);

        lblStatus.setText("No Performance Data Available");
        lblStatus.setForeground(warningColor);
    }
}