package com.motorph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class frameLogin extends JFrame{
    private JPanel panelLogin;
    private JLabel lblHeader;
    private JLabel lblEmpNum;
    private JTextField txtEmpNum;
    private JPasswordField password;
    private JLabel lblPassword;
    private JButton btnLogin;

    public frameLogin () {
        this.setContentPane(this.panelLogin);
        this.setSize(450, 350);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("MotorPH Employee Login");

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String empNum = txtEmpNum.getText();
                String empPass = new String(password.getPassword());
                if (empNum.equals("10028") && empPass.equals("emp1234")) {
                    new employeeFrame();
                    dispose();
                }
                else {
                    JOptionPane.showMessageDialog(btnLogin, "Invalid Credentials, Try Again!");
                    txtEmpNum.setText("");
                    password.setText("");
                }
            }
        });
    }

}