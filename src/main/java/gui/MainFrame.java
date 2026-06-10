package gui;

import javax.swing.*;
import java.awt.*;

import com.formdev.flatlaf.FlatLightLaf;

import admin.AddCoursePanel;
import admin.AddStudentPanel;
import admin.AddSubjectPanel;
import admin.AdminAnalyticsPanel;

import staff.StaffDashboardPanel;

public class MainFrame extends JFrame {

    // =====================================
    // GLOBAL CARDLAYOUT
    // =====================================

    public static CardLayout cardLayout;

    public static JPanel mainPanel;

    // =====================================
    // ADMIN PANELS
    // =====================================

    public static AdminPanel adminPanel;

    public static AddCoursePanel addCoursePanel;

    public static AddSubjectPanel addSubjectPanel;

    public static AddStudentPanel addStudentPanel;

    public static AdminAnalyticsPanel adminAnalyticsPanel;

    // =====================================
    // STAFF PANEL
    // =====================================

    public static StaffDashboardPanel staffDashboardPanel;

    // =====================================
    // CONSTRUCTOR
    // =====================================

    public MainFrame() {

        setTitle("SRMS");

        setSize(1400, 800);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        // =====================================
        // MAIN CARD PANEL
        // =====================================

        cardLayout =
                new CardLayout();

        mainPanel =
                new JPanel(cardLayout);

        // =====================================
        // LOGIN PANEL
        // =====================================

        mainPanel.add(
                new LoginPanel(
                        cardLayout,
                        mainPanel
                ),
                "LOGIN"
        );

        // =====================================
        // ADMIN PANEL
        // =====================================

        adminPanel =
                new AdminPanel();

        mainPanel.add(
                adminPanel,
                "ADMIN"
        );

        // =====================================
        // STAFF PANEL
        // =====================================

        staffDashboardPanel =
                new StaffDashboardPanel();

        mainPanel.add(
                staffDashboardPanel,
                "STAFF"
        );

        // =====================================
        // ADD MAIN PANEL
        // =====================================

        add(mainPanel);

        // =====================================
        // SHOW LOGIN
        // =====================================

        cardLayout.show(
                mainPanel,
                "LOGIN"
        );

        setVisible(true);
    }

    // =====================================
    // SHOW PANEL
    // =====================================

    public static void showPanel(
            String panelName
    ) {

        cardLayout.show(
                mainPanel,
                panelName
        );
    }

    // =====================================
    // MAIN METHOD
    // =====================================

    public static void main(
            String[] args
    ) {
        
        System.out.println("SRMS STARTING...");
        try {

            FlatLightLaf.setup();

        } catch (Exception e) {

            e.printStackTrace();
        }

       


        SwingUtilities.invokeLater(
                () -> new MainFrame()
        );
    }
}

// Command for building the project .jar file:
// C:\apache-maven-3.9.16\bin\mvn.cmd clean package
// The .jar file will be located in the target folder after building.
// Command for running the .jar file:
// java -jar target/srms-1.0-SNAPSHOT.jar
// Make sure to replace "srms-1.0-SNAPSHOT.jar" with the actual name of the generated .jar file.
//such as "SRMS.jar" if you have renamed it.
//Note: You should have minimum Java 17 installed to run the .jar file, as the project is built using Java 17.
// For development, you can run the MainFrame class directly from your IDE, which will allow you to see the console output and debug more easily.
