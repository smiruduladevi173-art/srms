package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StatsCard extends JPanel {

    JLabel lblTitle;

    JLabel lblValue;

    JLabel lblIcon;

    public StatsCard(
            String title,
            String value,
            String icon,
            Color color1,
            Color color2
    ) {

        setLayout(
                new BorderLayout()
        );

        setOpaque(false);

        // MUCH SMALLER HEIGHT

        setPreferredSize(
                new Dimension(220, 70)
        );

        setMaximumSize(
                new Dimension(220, 70)
        );

        setBorder(
                new EmptyBorder(
                        10,
                        15,
                        10,
                        15
                )
        );

        // =========================
        // ICON
        // =========================

        lblIcon =
                new JLabel(icon);

        lblIcon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        18
                )
        );

        // =========================
        // TITLE
        // =========================

        lblTitle =
                new JLabel(title);

        lblTitle.setForeground(
                new Color(240,240,240)
        );

        lblTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        // =========================
        // VALUE
        // =========================

        lblValue =
                new JLabel(value);

        lblValue.setForeground(Color.WHITE);

        lblValue.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );

        // =========================
        // TOP PANEL
        // =========================

        JPanel topPanel =
                new JPanel(
                        new BorderLayout()
                );

        topPanel.setOpaque(false);

        topPanel.add(
                lblTitle,
                BorderLayout.WEST
        );

        topPanel.add(
                lblIcon,
                BorderLayout.EAST
        );

        add(
                topPanel,
                BorderLayout.NORTH
        );

        add(
                lblValue,
                BorderLayout.CENTER
        );

        // =========================
        // GRADIENT COLORS
        // =========================

        putClientProperty(
                "color1",
                color1
        );

        putClientProperty(
                "color2",
                color2
        );
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 =
                (Graphics2D) g;

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        Color color1 =
                (Color) getClientProperty(
                        "color1"
                );

        Color color2 =
                (Color) getClientProperty(
                        "color2"
                );

        GradientPaint gp =
                new GradientPaint(
                        0,
                        0,
                        color1,

                        getWidth(),
                        getHeight(),
                        color2
                );

        g2.setPaint(gp);

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                20,
                20
        );

        super.paintComponent(g);
    }
}

