package com.motorph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginFrame extends JFrame{
    private JTextField txtUser;
    private JPasswordField psPassword;
    private JButton btnLogin;
    private JPanel panelLogin;

    public loginFrame () {
        this.setContentPane(this.panelLogin);
        this.setTitle("MotorPH");
        this.setSize(350,350);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Logo Config
        ImageIcon logo = new ImageIcon("src/main/resources/MotorPH-Logo.png");
        this.setIconImage(logo.getImage());

        this.setVisible(true);

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkingCridentials(); // run the checker
            }
        });

        txtUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                psPassword.requestFocus(); // change cursor focus to the password field
            }
        });

        // if user pressed enter key
        psPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkingCridentials(); // run the checker
            }
        });
    }

    // check cridentials if it matches the inputs
    public void checkingCridentials () {
        String userID = txtUser.getText();
        String passwordID = new String(psPassword.getPassword());
        // Check if credintials is correct
        if (userID.equals("Admin") && passwordID.equals("1234")) {
            new motorphFrame(); // if correct open main window
            dispose(); // dispose login frame
        }
        else {
            JOptionPane.showMessageDialog(loginFrame.this,
                    "Invalid Cridential Try Again!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            // Clear the input fields
            txtUser.setText("");
            psPassword.setText("");
            txtUser.requestFocus(); // Cursor focus on user input
        }
    }

}
