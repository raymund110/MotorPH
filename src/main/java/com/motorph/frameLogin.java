package com.motorph;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class frameLogin extends JFrame {
    private JPanel panelLogin;
    private JLabel lblHeader;
    private JLabel lblEmpNum;
    private JTextField txtEmpNum;
    private JPasswordField password;
    private JLabel lblPassword;
    private JButton btnLogin;

    public frameLogin() {
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
                File file = new File ("src/main/resources/Sheet1.csv");
                logininfo login = new logininfo();

                try (BufferedReader reader = new BufferedReader(new FileReader(file))){
                    String line;
                    boolean correct = false;

                    reader.readLine();

                    while ((line = reader.readLine()) != null) {
                        String[] value = line.split(",");
                        if (value.length >= 2) {
                            login.setEmployeeNum(value[0].trim());
                            login.setEmployeePass(value[2].trim());

                            if (empNum.equals(login.getEmployeeNum()) && empPass.equals(login.getEmployeePass())) {
                                correct = true;
                                break;
                            }
                        }
                    }

                    if (correct) {
                        new employeeFrame();
                        dispose(); // Close the login window
                    } else {
                        JOptionPane.showMessageDialog(btnLogin, "Wrong Employee Number and Password Try Again!");
                        // Clear the fields
                        txtEmpNum.setText("");
                        password.setText("");
                    }
                } catch (Exception ex) {
                    System.out.println("Error reading Sheet1.csv");
                    JOptionPane.showMessageDialog(frameLogin.this, "Error reading file");
                }
                

            }
        });
    }

//    public boolean validateLogin(String empNum, String empPass) {
//        logininfo login = new logininfo();
//        String csvFile = "src/main/resources/Sheet1.csv";
//        String line;
//
//        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
//            // Skip the header line if it exists
//            br.readLine();
//
//            while ((line = br.readLine()) != null) {
//                String[] credentials = line.split(",");
//                if (credentials.length >= 2) {
//                    login.setEmployeeNum(credentials[0].trim());
//                    login.setEmployeePass(credentials[2].trim());
//
//                    if (empNum.equals(login.getEmployeeNum()) && empPass.equals(login.getEmployeePass())) {
//                        return true;
//                    }
//                }
//            }
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(btnLogin, "Error Reading Credentials");
//            return false;
//        }
//        return false;
//    }
}