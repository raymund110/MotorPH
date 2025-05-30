package com.motorph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewEmployee extends JFrame{
    private JPanel pnlNewEmployee;
    private JTextField txtEmployeeNumber;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtBirthday;
    private JTextField txtPhoneNumber;
    private JTextField txtAddress;
    private JTextField txtStatus;
    private JTextField txtPosition;
    private JTextField txtSupervisor;
    private JTextField txtPhilhealthNumber;
    private JTextField txtSSSNumber;
    private JTextField txtTINNumber;
    private JTextField txtPagibigNumber;
    private JTextField txtRiceSubsidy;
    private JTextField txtBasicSalary;
    private JTextField txtPhoneAllowance;
    private JTextField txtClothingAllowance;
    private JTextField txtGrossSemi;
    private JTextField txtHourlyRate;
    private JButton btnSaveEmployee;
    private JButton btnBack;

    public NewEmployee () {
        this.setContentPane(this.pnlNewEmployee);
        this.setTitle("MotorPH");
        this.setSize(750,650);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Logo Config
        ImageIcon logo = new ImageIcon("src/main/resources/MotorPH-Logo.png");
        this.setIconImage(logo.getImage());

        this.setVisible(true);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(NewEmployee.this,
                        "Are sure you want to go back?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (option == 0) {
                    new MainFrame();
                    dispose();
                }
            }
        });
    }

}
