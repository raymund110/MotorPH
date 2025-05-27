package com.motorph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class motorphFrame extends JFrame {
    private JPanel panelMotorPH;
    private JTextField txtEmployeeNumber;
    private JLabel lblName;
    private JLabel lblPosition;
    private JLabel lblHourlyRate;
    private JLabel lblBasicSalary;
    private JLabel lblGrossSalary;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JTable tableEmployeeList;
    private JLabel lblSSS;
    private JLabel lblWithholdingTax;
    private JLabel lblPagibig;
    private JLabel lblPhilhealth;
    private JLabel lblWorkHours;
    private JLabel lblNetSalary;
    private JLabel lblTotalDeductions;

    public motorphFrame () {
        this.setContentPane(this.panelMotorPH);
        this.setTitle("MotorPH");
        this.setSize(950,600);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Logo Config
        ImageIcon logo = new ImageIcon("src/main/resources/MotorPH-Logo.png");
        this.setIconImage(logo.getImage());

        employeeTable();

        this.setVisible(true); // Set employeeFrame visible

        txtEmployeeNumber.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = txtEmployeeNumber.getText();

                try {
                    // Parse String to int to determine employee number range
                    int employeeNumber = Integer.parseInt(search);

                    // Range of employee numbers based on the csv file
                    if (employeeNumber >= 10001 && employeeNumber <= 10034) {
                        // if entered employee number is inside range
                        salary(search);
                    }

                    else {
                        // if entered employee number is out of range
                        JOptionPane.showMessageDialog(motorphFrame.this,
                                "Employee Number Does Not Exist!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        txtEmployeeNumber.setText(""); // to clear the text field after error inputs
                    }

                } catch (NumberFormatException ex) {
                    // Invalid input error handler // if entered input is not a number
                    JOptionPane.showMessageDialog(motorphFrame.this,
                            "Please enter a valid employee Number\n" + ex.getMessage() + JOptionPane.ERROR_MESSAGE);
                    txtEmployeeNumber.setText(""); // to clear the text field after error inputs
                }
            }
        });

        txtEndDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtStartDate.getText();
                txtEndDate.getText();

                try {
                    if (txtStartDate != null && txtEndDate != null) {
                        JOptionPane.showMessageDialog(motorphFrame.this, "Okay!");
                    }
                }

                catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }

    public void employeeTable () {
        // Instantiate Employee
        EmployeeDataFromFile dataFile = new EmployeeDataFromFile(); //
        // Retrieves the ArrayList of Employee objects from EmployeeDataFromFile
        ArrayList<Employee> employee = dataFile.getEmployeeData(); // Store Employee Objects

        // Column Names
        String[] columnName = {"Employee Number", "Name", "Birthday"};

        DefaultTableModel model = new DefaultTableModel(columnName, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        for (Employee emp : employee) {
            if (emp != null) {
                Object[] row = {
                        emp.getEmployeeNumber(),
                        emp.getPerson().getFirstName() + " " + emp.getPerson().getLastName(),
                        emp.getBirthday()
                };
                model.addRow(row);
            }
        }

        // Set a table model
        tableEmployeeList.setModel(model);

        tableEmployeeList.setAutoCreateRowSorter(true);
        tableEmployeeList.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        tableEmployeeList.setRowHeight(25);

        // Column widths
        tableEmployeeList.getColumnModel().getColumn(0).setPreferredWidth(10);
        tableEmployeeList.getColumnModel().getColumn(1).setPreferredWidth(110);
        tableEmployeeList.getColumnModel().getColumn(2).setPreferredWidth(20);

    }

    public void salary (String searchedEmployeeNumber) {
        // Instantiate Employee
        EmployeeDataFromFile dataFile = new EmployeeDataFromFile();
        ArrayList<Employee> employees = dataFile.getEmployeeData(); // Store Employee Objects

        // Placeholder remains null if the entered employee number is not found
        Employee searchedEmployee = null;

        // iterates through the (employees) array list
        for (Employee emp : employees) {
            if (emp != null && String.valueOf(emp.getEmployeeNumber()).equals(searchedEmployeeNumber)) {
                // if not null and employee number match: then an employee object is stored in searchedEmployee
                searchedEmployee = emp;
                break;
            }
        }

        if (searchedEmployee != null) {
            // Employee name, position, hourly rate, gross salary
            lblName.setText(searchedEmployee.getPerson().getFirstName() + " " +
                    searchedEmployee.getPerson().getLastName());
            lblPosition.setText(searchedEmployee.getJob().getPosition());
            lblHourlyRate.setText("₱ " + String.valueOf(searchedEmployee.getCompensation().getHourlyRate()));
            lblBasicSalary.setText("₱ " + String.valueOf(searchedEmployee.getCompensation().getBasicSalary()));
            // Calculate gross salary
            double grossSalary = searchedEmployee.getCompensation().getBasicSalary() +
                    searchedEmployee.getCompensation().getRiceSubsidy() +
                    searchedEmployee.getCompensation().getPhoneAllowance() +
                    searchedEmployee.getCompensation().getClothingAllowance();
            // Set gross salary value
            searchedEmployee.getCompensation().setGrossSalary(grossSalary);

            lblGrossSalary.setText("₱ " + String.valueOf(searchedEmployee.getCompensation().getGrossSalary()));

            double basicSalary = searchedEmployee.getCompensation().getBasicSalary();

            // Deductions
            lblSSS.setText("₱ " + String.valueOf(searchedEmployee.getSalaryDeduction().getSSSdeduction(basicSalary)));
            lblWithholdingTax.setText("₱ " + String.valueOf(searchedEmployee.getSalaryDeduction().getWithholdingTax(basicSalary)));
            lblPagibig.setText("₱ " + String.valueOf(searchedEmployee.getSalaryDeduction().getPagibigDeduction(basicSalary)));
            lblPhilhealth.setText("₱ " + String.valueOf(searchedEmployee.getSalaryDeduction().getPhilHealthDeduction(basicSalary)));
            lblTotalDeductions.setText("₱ " + String.valueOf(searchedEmployee.getSalaryDeduction().getTotalDeductions(basicSalary)));
        }

    }

    public void hourBasedSalary (String searchedEmployeeNumber, LocalDate startDate, LocalDate endDate) {
        AttendanceDataFromFile dataFromFile = new AttendanceDataFromFile();
        ArrayList<Attendance> attendances = dataFromFile.getAttendanceData();

        Attendance searchEmployee = null;

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        for (Attendance att : attendances) {
            if (att != null && String.valueOf(att.getEmployee().getEmployeeNumber()).equals(searchedEmployeeNumber)) {
                searchEmployee = att;
                break;
            }
        }

        if (searchEmployee != null) {
            // Set timein and timeout

        }

    }

}
