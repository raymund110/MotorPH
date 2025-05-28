package com.motorph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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

    private String currentEmployee; // Placeholder for current searched employee

    public motorphFrame () {
        this.setContentPane(this.panelMotorPH);
        this.setTitle("MotorPH");
        this.setSize(950,630);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Logo Config
        ImageIcon logo = new ImageIcon("src/main/resources/MotorPH-Logo.png");
        this.setIconImage(logo.getImage());

        employeeTable();

        this.setVisible(true); // Set employeeFrame visible

        // Searching Employee function
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
                        currentEmployee = search; // Store the currentEmployee value for reuse
                        salary(search);
                        txtStartDate.requestFocus(); // Cursor focus on txtStartDate
                    }

                    else {
                        // if entered employee number is out of range
                        JOptionPane.showMessageDialog(motorphFrame.this,
                                "Employee Number Does Not Exist!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        txtEmployeeNumber.setText(""); // to clear the text field after error inputs
                    }

                }
                // Catch invalid inputs if the input is not a number etc.
                catch (NumberFormatException ex) {
                    // show dialog of what is the error
                    JOptionPane.showMessageDialog(motorphFrame.this,
                            "Please enter a valid employee Number\n",
                                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    txtEmployeeNumber.setText(""); // to clear the text field after error inputs
                }
            }
        });

        // Check if the entered date in the text field is in a correct format
        txtStartDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    // if the date format is correct, then proceed to end date input
                    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate.parse(txtStartDate.getText().trim(), dateFormat);
                    txtEndDate.requestFocus();

                }
                // catch error if a date format is incorrect
                catch (DateTimeException ex) {
                    // show dialog of what is the error
                    JOptionPane.showMessageDialog(motorphFrame.this,
                            "Enter valid date format (MM/dd/yyyy)",
                            "Invalid", JOptionPane.ERROR_MESSAGE);
                    txtStartDate.setText(""); // clear text field to try again
                }

            }
        });

        // Calculate the salary based on hours when pressed the enter key
        txtEndDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // show dialog if employee number text field is empty
                if (currentEmployee == null ) {
                    JOptionPane.showMessageDialog(motorphFrame.this,
                            "Please enter an employee number first",
                            "Invalid", JOptionPane.ERROR_MESSAGE);
                    txtEmployeeNumber.requestFocus(); // change the cursor focus at the employee number text field
                }

                try {
                    // Date format
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate start = LocalDate.parse(txtStartDate.getText().trim(), formatter);
                    LocalDate end = LocalDate.parse(txtEndDate.getText().trim(), formatter);

                    if (start.isAfter(end)) { // Determine if the chronological order is correct
                        JOptionPane.showMessageDialog(motorphFrame.this,
                                "Start date must be before end date",
                                "Invalid Dates",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    // runs the salary calculations
                    hourBasedSalary(currentEmployee, start, end);

                }
                // Catch input error
                catch (DateTimeException ex) {
                    // show dialog of what is the error
                    JOptionPane.showMessageDialog(motorphFrame.this,
                            "Enter valid date format (MM/dd/yyyy)",
                            "Invalid", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    // Loads the employee table
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

        // iterates through the (employees) array list and add rows
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

        // Set a table model and some table configurations
        tableEmployeeList.setModel(model);
        tableEmployeeList.setAutoCreateRowSorter(true);
        tableEmployeeList.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        tableEmployeeList.setRowHeight(25);
        // Column widths
        tableEmployeeList.getColumnModel().getColumn(0).setPreferredWidth(50);
        tableEmployeeList.getColumnModel().getColumn(1).setPreferredWidth(110);
        tableEmployeeList.getColumnModel().getColumn(2).setPreferredWidth(50);

    }

    // To view the salary details
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
            // Setting text values the match the searched employee number
            lblName.setText(searchedEmployee.getPerson().getFirstName() + " " +
                    searchedEmployee.getPerson().getLastName());
            lblPosition.setText(searchedEmployee.getJob().getPosition());
            lblHourlyRate.setText("₱ " + String.format("%,.2f", searchedEmployee.getCompensation().getHourlyRate()));
            lblBasicSalary.setText("₱ " + String.format("%,.2f", searchedEmployee.getCompensation().getBasicSalary()));
            // calculate gross salary
            double grossSalary = searchedEmployee.getCompensation().getBasicSalary() +
                    searchedEmployee.getCompensation().getRiceSubsidy() +
                    searchedEmployee.getCompensation().getPhoneAllowance() +
                    searchedEmployee.getCompensation().getClothingAllowance();
            // Set gross salary value
            searchedEmployee.getCompensation().setGrossSalary(grossSalary);

            lblGrossSalary.setText("₱ " + String.format("%,.2f", searchedEmployee.getCompensation().getGrossSalary()));

            double basicSalary = searchedEmployee.getCompensation().getBasicSalary();

            // Deductions
            lblSSS.setText("₱ " + String.format("%,.2f", searchedEmployee.getSalaryDeduction().getSSSdeduction(basicSalary)));
            lblWithholdingTax.setText("₱ " + String.format("%,.2f", searchedEmployee.getSalaryDeduction().getWithholdingTax(basicSalary)));
            lblPagibig.setText("₱ " + String.format("%,.2f", searchedEmployee.getSalaryDeduction().getPagibigDeduction(basicSalary)));
            lblPhilhealth.setText("₱ " + String.format("%,.2f", searchedEmployee.getSalaryDeduction().getPhilHealthDeduction(basicSalary)));
            lblTotalDeductions.setText("₱ " + String.format("%,.2f", searchedEmployee.getSalaryDeduction().getTotalDeductions(basicSalary)));
        }

    }
    // Calculate the net salary based on the start date and end date inputs by the user
    public void hourBasedSalary (String searchedEmployeeNumber, LocalDate startDate, LocalDate endDate) {
        // Initialized the attendance and employee objects
        EmployeeDataFromFile employeeDataFromFile = new EmployeeDataFromFile();
        ArrayList<Employee> employees = employeeDataFromFile.getEmployeeData();
        AttendanceDataFromFile attendanceDataFromFile = new AttendanceDataFromFile();
        ArrayList<Attendance> attendances = attendanceDataFromFile.getAttendanceData();

        double totalHoursWorked = 0.0;
        Employee employee = null;

        // iterates through the (employees) array list
        for (Employee emp : employees) {
            if (emp != null && String.valueOf(emp.getEmployeeNumber()).equals(searchedEmployeeNumber)) {
                // if not null and employee number match: then an employee object is stored in searchedEmployee
                employee = emp;
                break;
            }
        }

        if (employee != null) {
            // iterates through the (attendance) array list
            for (Attendance att : attendances) {
                if (att != null && String.valueOf(att.getEmployee().getEmployeeNumber()).equals(searchedEmployeeNumber)) {
                    LocalDate date = att.getAttendanceDate();

                    if ((date.equals(startDate) || date.isAfter(startDate)) &&
                            (date.equals(endDate) || date.isBefore(endDate))) {
                        LocalTime timeIn = att.getTimeIn();
                        LocalTime timeOut = att.getTimeOut();
                        double hoursWork = Duration.between(timeIn, timeOut).toMinutes() / 60.0;
                        totalHoursWorked += hoursWork; // add hourswork to total hours worked
                    }

                }
            }
            // Calculate the gross based on hours worked
            double gross = totalHoursWorked * employee.getCompensation().getHourlyRate();
            double deductions = employee.getSalaryDeduction()
                    .getTotalDeductions(employee.getCompensation().getBasicSalary());
            // Calculate netSalary
            double netSalary = gross - deductions;

            lblWorkHours.setText(String.format("%,.2f hours", totalHoursWorked));
            lblNetSalary.setText("₱ " + String.format("%,.2f", netSalary));
        }
    }

}
