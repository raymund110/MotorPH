package com.motorph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class employeeFrame extends JFrame {

    private JPanel empDashboard;
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
    private JLabel lblBasicSalary;
    private JLabel lblRiceSubsidy;
    private JLabel lblPhoneAllowance;
    private JLabel lblClothingAllowance;
    private JTextField txtSearchEmployee;
    private JLabel lblMonthlyGross;
    private JLabel lblWeeklyGross;
    private JLabel lblWeeklyNet;
    private JLabel lblHourlyRate;

    public employeeFrame() {
        // Window Configuration
        this.setContentPane(this.empDashboard);
        this.setTitle("MotorPH Employee Dashboard");
        this.setSize(750,700);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Logo Config
        ImageIcon logo = new ImageIcon("src/main/resources/MotorPH-Logo.png");
        this.setIconImage(logo.getImage());

        this.setVisible(true); // Set employeeFrame visible

        // Action listener for the search employee text field when press enters
        txtSearchEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = txtSearchEmployee.getText().trim();
                txtSearchEmployee.setText(""); // to clear the text field after

                try {
                    // Parse String to int to determine employee number range
                    int employeeNumber = Integer.parseInt(search);

                    // Range of employee numbers based on the csv file
                    if (employeeNumber >= 10001 && employeeNumber <= 10034) {
                        // if entered employee number is inside range
                        employeeInfo(search);
                    }

                    else {
                        // if entered employee number is out of range
                        JOptionPane.showMessageDialog(employeeFrame.this,
                                "Employee Number Does Not Exist!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        txtSearchEmployee.setText(""); // to clear the text field after error inputs
                    }

                } catch (NumberFormatException ex) {
                    // Invalid input error handler // if entered input is not a number
                    JOptionPane.showMessageDialog(employeeFrame.this,
                            "Please enter a valid employee Number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    txtSearchEmployee.setText(""); // to clear the text field after error inputs
                }

            }
        });
    }

    // Method to load employee info to the labels and text fields
    private void employeeInfo (String loggedInEmployeeNumber) {
        // Employee Info
        EmployeeDataFromFile dataFile = new EmployeeDataFromFile();
        ArrayList<Employee> employees = dataFile.getEmployeeData();

        // Find the logged employee from the frameLogin class using the parameters of this constructor
        Employee loggedInEmployee = null;

        for (Employee emp : employees) {
            if (emp != null && String.valueOf(emp.getEmployeeNumber()).equals(loggedInEmployeeNumber)) {
                loggedInEmployee = emp;
                break;
            }
        }

        // Set the text fields if an employee is found
        if (loggedInEmployee != null) {
            lblEmployeeNumber.setText(String.valueOf(loggedInEmployee.getEmployeeNumber()));
            lblName.setText(loggedInEmployee.getPerson().getFirstName() + " " +
                    loggedInEmployee.getPerson().getLastName());
            lblBirthday.setText(String.valueOf(loggedInEmployee.getBirthday()));
            lblStatus.setText(loggedInEmployee.getStatus());

            //Job
            lblPosition.setText(loggedInEmployee.getJob().getPosition());
            lblSupervisor.setText(loggedInEmployee.getJob().getSupervisor());

            // Contacts
            txtAddress.setText(loggedInEmployee.getContactInfo().getAddress());
            lblPhone.setText(loggedInEmployee.getContactInfo().getPhoneNumber());

            // Government ID
            lblSSS.setText(loggedInEmployee.getGovID().getsssID());
            lblPhilhealth.setText(loggedInEmployee.getGovID().getphilhealthID());
            lblTin.setText(loggedInEmployee.getGovID().gettinID());
            lblPagibig.setText(loggedInEmployee.getGovID().getpagibigID());

            // Compensation
            lblBasicSalary.setText("₱ " + String.valueOf(loggedInEmployee.getCompensation().getBasicSalary()));
            lblRiceSubsidy.setText("₱ " + String.valueOf(loggedInEmployee.getCompensation().getRiceSubsidy()));
            lblPhoneAllowance.setText("₱ " + String.valueOf(loggedInEmployee.getCompensation().getPhoneAllowance()));
            lblClothingAllowance.setText("₱ " + String.valueOf(loggedInEmployee.getCompensation().getClothingAllowance()));
            lblHourlyRate.setText("₱ " + String.valueOf(loggedInEmployee.getCompensation().getHourlyRate()));

            employeeSalary(loggedInEmployeeNumber);
        }
    }

    // Method to set the salary fields
    private void employeeSalary (String loggedInEmployeeNumber) {
        // Employee Info
        EmployeeDataFromFile dataFile = new EmployeeDataFromFile();
        ArrayList<Employee> employees = dataFile.getEmployeeData();

        SalaryDeduction deduction = new SalaryDeduction();

        // Find the logged employee from the frameLogin class using the parameters of this constructor
        Employee loggedInEmployee = null;

        for (Employee emp : employees) {
            if (emp != null && String.valueOf(emp.getEmployeeNumber()).equals(loggedInEmployeeNumber)) {
                loggedInEmployee = emp;
                break;
            }
        }

        if (loggedInEmployee != null) {
            double monthlyGross = loggedInEmployee.getCompensation().getBasicSalary()
                    + loggedInEmployee.getCompensation().getRiceSubsidy()
                    + loggedInEmployee.getCompensation().getPhoneAllowance()
                    + loggedInEmployee.getCompensation().getClothingAllowance();

            // Setting salary values
            loggedInEmployee.getSalary().setMonthlyGross(monthlyGross);
            loggedInEmployee.getSalary().setWeeklyGross(monthlyGross / 4);
            loggedInEmployee.getSalary().setWeeklyNet(loggedInEmployee.getSalary().getWeeklyGross()
            - deduction.getTotalSalaryDeductions(loggedInEmployee.getCompensation().getBasicSalary()) / 4);

            // Setting lbl texts
            lblMonthlyGross.setText("₱ " + String.valueOf(loggedInEmployee.getSalary().getMonthlyGross()));
            lblWeeklyGross.setText("₱ " + String.valueOf(loggedInEmployee.getSalary().getWeeklyGross()));
            lblWeeklyNet.setText("₱ " + String.valueOf(loggedInEmployee.getSalary().getWeeklyNet()));

        }


    }

    // Comment this out because this is an additional feature NOT from the console app
    // Saving it just in case
    /*
    // Method to load the attendance records in the tableAttendanceRecords
    private void attendanceTable (String loggedInEmployeeNumber) {
        // Column Names
        String[] columnNames = {"Name", "Date", "Time In", "Time Out"};

        // Setting Column Names
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Attendance att : attendancesList) {
            if (att != null && String.valueOf(att.getEmployee().getEmployeeNumber()).equals(loggedInEmployeeNumber)) {
                Object[] row = {
                        att.getEmployee().getPerson().getFirstName() + " " + att.getEmployee().getPerson().getLastName(),
                        att.getAttendanceDate(),
                        att.getTimeIn(),
                        att.getTimeOut()
                };
                model.addRow(row);
            }
        }

        // Sets table model
        tableAttendanceRecords.setModel(model);

        // Customize table appearance
        tableAttendanceRecords.setAutoCreateRowSorter(true);
        tableAttendanceRecords.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        tableAttendanceRecords.setRowHeight(25);

        // Set column widths
        tableAttendanceRecords.getColumnModel().getColumn(0).setPreferredWidth(150); // Name column
        tableAttendanceRecords.getColumnModel().getColumn(1).setPreferredWidth(100); // Date column
        tableAttendanceRecords.getColumnModel().getColumn(2).setPreferredWidth(100); // Time In column
        tableAttendanceRecords.getColumnModel().getColumn(3).setPreferredWidth(100); // Time Out column
    }
     */

}