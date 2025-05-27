package com.motorph;

import javax.swing.*;

public class motorphFrame extends JFrame {
    private JPanel panelMotorPH;
    private JTable table1;
    private JTextField txtEmployeeNumber;

    public motorphFrame () {
        this.setContentPane(this.panelMotorPH);
        this.setTitle("MotorPH");
        this.setSize(750,700);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Logo Config
        ImageIcon logo = new ImageIcon("src/main/resources/MotorPH-Logo.png");
        this.setIconImage(logo.getImage());

        this.setVisible(true); // Set employeeFrame visible
    }

}
