package com.motorph;

import javax.swing.*;

public class employeeFrame extends JFrame {

    private JPanel empDashboard;
    private JPanel panelEmployeeJob;
    private JTextField txtName;
    private JTextField txtStatus;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
    private JButton button1;
    private JButton button2;

    public employeeFrame () {
        this.setContentPane(this.empDashboard);
        this.setTitle("MotorPH Employee Dashboard");
        this.setSize(950,650);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
