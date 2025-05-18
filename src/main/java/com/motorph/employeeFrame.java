package com.motorph;

import javax.swing.*;

public class employeeFrame extends JFrame {

    private JPanel empDashboard;

    public employeeFrame () {
        this.setContentPane(this.empDashboard);
        this.setTitle("MotorPH Employee Dashboard");
        this.setSize(500,500);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
