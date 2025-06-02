package com.motorph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        // confirmation on closing the window
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                int option = JOptionPane.showConfirmDialog(loginFrame.this,
                        "Are you sure you want to close MotorPH", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    dispose();
                    System.exit(0);
                }
            }
        });

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
            new MainFrame(); // if correct open main window
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
