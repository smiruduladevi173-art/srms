package gui;

import javax.swing.*;

public class StudentPanel extends JPanel {

    public StudentPanel() {

        JLabel label = new JLabel("STUDENT DASHBOARD");

        JButton logoutBtn = new JButton("Logout");

        logoutBtn.addActionListener(e ->
                MainFrame.cardLayout.show(MainFrame.mainPanel, "LOGIN"));

        add(label);
        add(logoutBtn);
    }
}