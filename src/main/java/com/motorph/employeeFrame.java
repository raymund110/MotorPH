package com.motorph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class employeeFrame extends JFrame {

    private JPanel empDashboard;
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
    private JButton btnSearchAttendance;
    private JLabel lblBasicSalary;
    private JLabel lblRiceSubsidy;
    private JLabel lblPhoneAllowance;
    private JLabel lblClothingAllowance;
    private JTable tableAttendanceRecords;
    private JButton btnRefresh;

    // Attendance
    AttendanceData attendanceData = new AttendanceData();
    ArrayList<Attendance> attendancesList = attendanceData.getAttendanceData();

    public employeeFrame(String loggedInEmployeeNumber) {

        this.setContentPane(this.empDashboard);
        this.setTitle("MotorPH Employee Dashboard");
        this.setSize(1230,600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Logo Config
        ImageIcon logo = new ImageIcon("src/main/resources/MotorPH-Logo.png");
        this.setIconImage(logo.getImage());

        employeeInfo(loggedInEmployeeNumber);

        this.setVisible(true);

        // Search Attendance Record Button (NOT WORKING)
        btnSearchAttendance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchDate = txtSearchAttendance.getText().trim();

                String[] columnNames = {"Name", "Date", "Time In", "Time Out"};
                DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };

                for (Attendance att : attendancesList) {
                    if (att != null && String.valueOf(att.getEmployee().getEmployeeNumber()).equals(loggedInEmployeeNumber)) {
                        if (searchDate.isEmpty() || att.getAttendanceDate().toString().equals(searchDate)) {
                            Object[] row = new Object[]{
                                    att.getEmployee().getPerson().getFirstName() + " " + att.getEmployee().getPerson().getLastName(),
                                    att.getAttendanceDate(),
                                    att.getTimeIn(),
                                    att.getTimeOut()
                            };
                            model.addRow(row);
                        }
                    }
                }
            }
        });


        // Button to refresh the tableAttendanceRecords
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attendanceTable(loggedInEmployeeNumber);
                txtSearchAttendance.setText("");
            }
        });


        // Action listener for the LogOut button
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int result = JOptionPane.showConfirmDialog(employeeFrame.this, "Are you sure you want to logout?",
                        "Confirmation", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    new frameLogin();
                    dispose();
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

            //Payroll
            lblBasicSalary.setText(String.valueOf(loggedInEmployee.getCompensation().getBasicSalary()));
            lblRiceSubsidy.setText(String.valueOf(loggedInEmployee.getCompensation().getRiceSubsidy()));
            lblPhoneAllowance.setText(String.valueOf(loggedInEmployee.getCompensation().getPhoneAllowance()));
            lblClothingAllowance.setText(String.valueOf(loggedInEmployee.getCompensation().getClothingAllowance()));

            attendanceTable(loggedInEmployeeNumber);
        }
    }

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

}