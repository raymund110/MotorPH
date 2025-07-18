package com.motorph;

import com.opencsv.CSVReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileReader;

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
                checkingCredentials(); // run the checker
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
                checkingCredentials(); // run the checker
            }
        });
    }

    // check credentials if it matches the inputs
    private void checkingCredentials () {
        String loginData = "src/main/resources/LoginCredentials.csv"; // potential error here
        String userID = txtUser.getText();
        String passwordID = new String(psPassword.getPassword());

        try (CSVReader reader = new CSVReader(new FileReader(loginData))) {
            String[] line;
            boolean userExists = false;
            boolean correctCredentials = false;

            while ((line = reader.readNext()) != null) {
                if (line.length >= 4) {
                    String userName = line[2].trim();
                    String userPass = line[3].trim();

                    // Check if credintials is correct
                    if (userID.equals(userName)) {
                        userExists = true;
                        if (passwordID.equals(userPass)) {
                            correctCredentials = true;
                        }
                        break;
                    }
                }
            }

            // Empty fields
            if (userID.isEmpty() && passwordID.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame.this,
                        "Login fields empty.\nEnter your credentials.", "Empty fields", JOptionPane.ERROR_MESSAGE);
                txtUser.requestFocus();
            }
            // Empty password field
            else if (passwordID.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame.this,
                        "Please enter your password credentials.", "Empty passwords", JOptionPane.ERROR_MESSAGE);
                psPassword.requestFocus();
            }
            // Correct Credentials
            else if (correctCredentials) {
                new MainFrame();
                dispose();
            }
            // user correct but wrong password
            else if (userExists) {
                JOptionPane.showMessageDialog(loginFrame.this,
                        "Incorrect password. Please try again.", "Invalid Password", JOptionPane.ERROR_MESSAGE);
                psPassword.setText("");
                psPassword.requestFocus();
            }
            // user not found in login credentials files
            else {
                JOptionPane.showMessageDialog(loginFrame.this,
                        "User not found. Please check your credentials.", "Invalid User", JOptionPane.ERROR_MESSAGE);
                // Clear the input fields
                txtUser.setText("");
                psPassword.setText("");
                txtUser.requestFocus(); // Cursor focus on user input
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
