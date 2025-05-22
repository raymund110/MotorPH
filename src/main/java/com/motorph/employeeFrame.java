package com.motorph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class employeeFrame extends JFrame {

    private JPanel empDashboard;
    private JPanel empInfo;
    private JPanel govID;
    private JTextField txtSearchAttendance;
    private JLabel lblEmployeeNumber;
    private JLabel lblName;
    private JLabel lblStatus;
    private JLabel lblPosition;
    private JLabel lblSupervisor;
    private JLabel lblBirthday;
    private JLabel lblPhone;
    private JTextArea txtAddress;
    private JLabel lblSSS;
    private JLabel lblPhilhealth;
    private JLabel lblTin;
    private JLabel lblPagibig;
    private JButton btnLogout;
    private JTable tableRecords;
    private JButton btnSearchAttendance;
    private JLabel lblBasicSalary;
    private JLabel lblRiceSubsidy;
    private JLabel lblPhoneAllowance;
    private JLabel lblClothingAllowance;

    public employeeFrame(String loggedInEmployeeNumber) {
        EmployeeDataFromFile dataFile = new EmployeeDataFromFile();
        Employee[] employees = dataFile.getEmployeeData();

        // Find the logged employee from the frameLogin class using the parameters of this constructor
        Employee loggedInEmployee = null;
        for (Employee emp : employees) {
            if (emp != null && String.valueOf(emp.getEmployeeNumber()).equals(loggedInEmployeeNumber)) {
                loggedInEmployee = emp;
                break;
            }
        }

        this.setContentPane(this.empDashboard);
        this.setTitle("MotorPH Employee Dashboard");
        this.setSize(1150,650);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Logo Config
        ImageIcon logo = new ImageIcon("src/main/resources/MotorPH-Logo.png");
        this.setIconImage(logo.getImage());

        // Set the text fields if an employee is found
        if (loggedInEmployee != null) {
            lblEmployeeNumber.setText(String.valueOf(loggedInEmployee.getEmployeeNumber()));
            lblName.setText(loggedInEmployee.getPerson().getFirstName() + " " +
                    loggedInEmployee.getPerson().getLastName());
            lblStatus.setText(loggedInEmployee.getStatus());
            lblPosition.setText(loggedInEmployee.getJob().getPosition());
            lblSupervisor.setText(loggedInEmployee.getJob().getSupervisor());
            lblBirthday.setText(String.valueOf(loggedInEmployee.getBirthday()));

            // Contacts
            txtAddress.setText(loggedInEmployee.getContactInfo().getAddress());
            lblPhone.setText(loggedInEmployee.getContactInfo().getPhoneNumber());

            // Government ID
            lblSSS.setText(loggedInEmployee.getGovID().getsssID());
            lblPhilhealth.setText(loggedInEmployee.getGovID().getphilhealthID());
            lblTin.setText(loggedInEmployee.getGovID().gettinID());
            lblPagibig.setText(loggedInEmployee.getGovID().getpagibigID());

            //Payroll
            lblBasicSalary.setText(String.valueOf(loggedInEmployee.getCompensation().getBasicSalary()));
            lblRiceSubsidy.setText(String.valueOf(loggedInEmployee.getCompensation().getRiceSubsidy()));
            lblPhoneAllowance.setText(String.valueOf(loggedInEmployee.getCompensation().getPhoneAllowance()));
            lblClothingAllowance.setText(String.valueOf(loggedInEmployee.getCompensation().getClothingAllowance()));

        }

        this.setVisible(true);

        // Action listener for the LogOut button
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int result = JOptionPane.showConfirmDialog(employeeFrame.this, "Are you sure you want to logout?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    new frameLogin();
                    dispose();
                }

            }
        });

    }
}