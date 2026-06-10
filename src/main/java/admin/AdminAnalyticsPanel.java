package admin;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import analytics.NativeAnalytics;

public class AdminAnalyticsPanel extends JPanel {


    JButton btnGenerate;

    JLabel chartLabel;

    JLabel lblStatus;


        //================================
        // C-BACKEND RESULTS   
        //================================

        JLabel lblAverage;
JLabel lblPassPercent;
JLabel lblHighest;
JLabel lblLowest;

JPanel statsPanel;





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

    public AdminAnalyticsPanel() {

        setLayout(new BorderLayout());

        setBackground(backgroundColor);

        // =====================================
        // MAIN PANEL
        // =====================================

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                20,
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
        // TOP ACTION PANEL
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

      
        // =====================================
        // GENERATE BUTTON
        // =====================================

        btnGenerate =
                createPrimaryButton(
                        "GENERATE TOP STUDENTS"
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

      

        actionPanel.add(btnGenerate);

        actionPanel.add(
                Box.createHorizontalStrut(10)
        );

        actionPanel.add(lblStatus);

lblAverage =
        new JLabel("-");

lblPassPercent =
        new JLabel("-");

lblHighest =
        new JLabel("-");

lblLowest =
        new JLabel("-");

statsPanel =
        new JPanel(
                new GridLayout(
                        1,
                        4,
                        15,
                        0
                )
        );

statsPanel.setOpaque(false);

statsPanel.add(
        createStatCard(
                "Average",
                lblAverage
        )
);

statsPanel.add(
        createStatCard(
                "Pass %",
                lblPassPercent
        )
);

statsPanel.add(
        createStatCard(
                "Highest",
                lblHighest
        )
);

statsPanel.add(
        createStatCard(
                "Lowest",
                lblLowest
        )
);



        JPanel topPanel =
        new JPanel(
                new BorderLayout(
                        0,
                        15
                )
        );

topPanel.setOpaque(false);

topPanel.add(
        actionPanel,
        BorderLayout.NORTH
);

topPanel.add(
        statsPanel,
        BorderLayout.CENTER
);

mainPanel.add(
        topPanel,
        BorderLayout.NORTH
);

        // =====================================
        // CHART CARD
        // =====================================

        JPanel chartCard =
                new JPanel(
                        new BorderLayout()
                );

        chartCard.setBackground(cardColor);

        chartCard.setBorder(
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

        // =====================================
        // CHART LABEL
        // =====================================

        chartLabel =
                new JLabel();

        chartLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        chartLabel.setVerticalAlignment(
                SwingConstants.CENTER
        );

        chartLabel.setText(
                "Generate analytics to view chart"
        );

        chartLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );

        chartLabel.setForeground(
                Color.GRAY
        );

        JScrollPane scrollPane =
                new JScrollPane(chartLabel);

        scrollPane.setBorder(null);

        chartCard.add(
                scrollPane,
                BorderLayout.CENTER
        );

        mainPanel.add(
                chartCard,
                BorderLayout.CENTER
        );

        add(
                mainPanel,
                BorderLayout.CENTER
        );

        // =====================================
        // BUTTON EVENTS
        // =====================================

        btnGenerate.addActionListener(
                e -> generateChart()
        );
    }
        // =====================================
     
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
                        220,
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

        button.setBackground(primaryColor);

        button.setForeground(Color.WHITE);

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

private JPanel createStatCard(
        String title,
        JLabel valueLabel
) {

    JPanel card = new JPanel();

    card.setLayout(
            new BoxLayout(
                    card,
                    BoxLayout.Y_AXIS
            )
    );

    card.setBackground(Color.WHITE);

    card.setBorder(
            BorderFactory.createCompoundBorder(
                    new LineBorder(
                            borderColor,
                            1,
                            true
                    ),
                    new EmptyBorder(
                            15,
                            20,
                            15,
                            20
                    )
            )
    );

    JLabel lblTitle =
            new JLabel(title);

    lblTitle.setFont(
            new Font(
                    "Segoe UI",
                    Font.PLAIN,
                    13
            )
    );

    valueLabel.setFont(
            new Font(
                    "Segoe UI",
                    Font.BOLD,
                    24
            )
    );

    card.add(lblTitle);
    card.add(Box.createVerticalStrut(8));
    card.add(valueLabel);

    return card;
}
    

    // =====================================
    // REFRESH DATA
    // =====================================

   public void refreshData() {

    chartLabel.setIcon(null);

    chartLabel.setText(
            "Generate analytics to view chart"
    );

    lblStatus.setText("");

    lblAverage.setText("-");
    lblPassPercent.setText("-");
    lblHighest.setText("-");
    lblLowest.setText("-");

    revalidate();
    repaint();
}

    // =====================================
    // GENERATE CHART
    // =====================================

    private void generateChart() {

        try {

            lblStatus.setForeground(
                    primaryColor
            );

            lblStatus.setText(
                    "Generating Chart..."
            );
            String result =
        NativeAnalytics.generateAnalytics();
        System.out.println("RESULT:");
System.out.println(result);

String[] lines =
        result.split("\n");

for(String line : lines) {

    
    if(line.startsWith("AVERAGE=")) {

    lblAverage.setText(
            line.replace(
                    "AVERAGE=",
                    ""
            )
    );
}

else if(line.startsWith("PASS_PERCENT=")) {

    lblPassPercent.setText(
            line.replace(
                    "PASS_PERCENT=",
                    ""
            ) + "%"
    );
}

else if(line.startsWith("HIGHEST=")) {

    lblHighest.setText(
            line.replace(
                    "HIGHEST=",
                    ""
            )
    );
}

else if(line.startsWith("LOWEST=")) {

    lblLowest.setText(
            line.replace(
                    "LOWEST=",
                    ""
            )
    );
}





}

            ProcessBuilder pb =
                    new ProcessBuilder(
                            "python",
                            "analytics/admin_chart.py"
                    );

            pb.start().waitFor();

  

            // =====================================
            // LOAD IMAGE
            // =====================================

            ImageIcon icon =
                    new ImageIcon(
                            "analytics/admin_chart.png"
                    );

            // =====================================
            // IMAGE CHECK
            // =====================================

            if(icon.getIconWidth() <= 0) {

                lblStatus.setForeground(
                        dangerColor
                );

                lblStatus.setText(
                        "Chart image not generated"
                );

                return;
            }

            // =====================================
            // RESIZE IMAGE
            // =====================================

            Image img =
                    icon.getImage()
                            .getScaledInstance(
                                    760,
                                    430,
                                    Image.SCALE_SMOOTH
                            );

            chartLabel.setText("");

            chartLabel.setIcon(
                    new ImageIcon(img)
            );

            lblStatus.setForeground(
                    successColor
            );

            lblStatus.setText(
                    "Chart Generated Successfully"
            );

        } catch (
                IOException |
                InterruptedException e
        ) {

            e.printStackTrace();

            lblStatus.setForeground(
                    dangerColor
            );

            lblStatus.setText(
                    "Failed to Generate Chart"
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Failed to Generate Chart"
            );
        }
    }
}