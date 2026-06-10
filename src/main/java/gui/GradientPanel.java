package gui;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {

    private final Color startColor;

    private final Color endColor;

    public GradientPanel(
            Color startColor,
            Color endColor
    ) {

        this.startColor = startColor;

        this.endColor = endColor;

        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 =
                (Graphics2D) g;

        // SMOOTH RENDERING

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // CREATE GRADIENT

        GradientPaint gradient =
                new GradientPaint(
                        0,
                        0,
                        startColor,

                        getWidth(),
                        getHeight(),
                        endColor
                );

        g2.setPaint(gradient);

        g2.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
        );

        super.paintComponent(g);
    }
}