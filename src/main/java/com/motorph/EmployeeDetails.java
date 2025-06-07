package com.motorph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class EmployeeDetails extends JFrame{
    private JPanel panelEmpDetails;
    private JLabel lblEmployeeNumber;
    private JLabel lblName;
    private JLabel lblBirthday;
    private JLabel lblJobStatus;
    private JLabel lblPosition;
    private JLabel lblSupervisor;
    private JLabel lblPhoneNumber;
    private JTextArea txtAddress;
    private JLabel lblSSSNumber;
    private JLabel lblPhilhealthNumber;
    private JLabel lblTinNumber;
    private JLabel lblPagibigNumber;
    private JLabel lblBasicSalary;
    private JLabel lblRiceSubsidy;
    private JLabel lblPhoneAllowance;
    private JLabel lblClothingAllowance;
    private JLabel lblGrossSemi;
    private JLabel lblHourlyRate;
    private JLabel lblHoursWork;
    private JLabel lblPhilhealth;
    private JLabel lblTotalDeduction;
    private JLabel lblPagibig;
    private JLabel lblWithholdingTax;
    private JLabel lblSSS;
    private JLabel lblNetSalary;
    private JButton btnCompute;
    private JComboBox cbxMonths;
    private JLabel lblSalaryMonth;

    private String selectedMonth = "January"; // Default selection of month

    public EmployeeDetails (String selectedEmployee) {
        this.setContentPane(this.panelEmpDetails);
        this.setTitle("MotorPH");
        this.setSize(850,750);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        // confirmation on closing the frame
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                int option = JOptionPane.showConfirmDialog(EmployeeDetails.this,
                        "Exit on viewing employee details?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    dispose();
                    new MainFrame(); // goes back to the main frame
                }
            }
        });

        // Logo Config
        ImageIcon logo = new ImageIcon("src/main/resources/MotorPH-Logo.png");
        this.setIconImage(logo.getImage());

        this.setVisible(true);

        employeeInfo(selectedEmployee);

        // To save the selected month using combo box
        cbxMonths.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedMonth != null) {
                    // Save selected value to selectedMonth
                    selectedMonth = String.valueOf(cbxMonths.getSelectedItem());
                    System.out.println(selectedMonth); // Check if working
                    lblSalaryMonth.setText("Salary for month of: " + selectedMonth);
                }
            }
        });

        btnCompute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Salary computation here
                computeSalary(selectedEmployee, selectedMonth);
            }
        });

    }

    private void employeeInfo (String selectedEmployeeNumber) {
        EmployeeDataFromFile employeeDataFromFile = new EmployeeDataFromFile();
        ArrayList<Employee> employees = employeeDataFromFile.getEmployeeData();

        // Placeholder remains null if the selected employee is not found
        Employee employeeData = null;

        // iterates through the (employees) array list
        for (Employee emp : employees) {
            if (emp != null && String.valueOf(emp.getEmployeeNumber()).equals(selectedEmployeeNumber)) {
                // if not null and employee number match: then an employee object is stored in employeeData
                employeeData = emp;
                break;
            }
        }

        if (employeeData != null) {
            // Basic Information
            lblEmployeeNumber.setText(String.valueOf(employeeData.getEmployeeNumber()));
            lblName.setText(employeeData.getPerson().getLastName() + " " +
                    employeeData.getPerson().getFirstName());
            lblBirthday.setText(String.valueOf(employeeData.getBirthday()));
            lblJobStatus.setText(employeeData.getStatus());
            lblPosition.setText(employeeData.getJob().getPosition());
            lblSupervisor.setText(employeeData.getJob().getSupervisor());

            // Contacts
            lblPhoneNumber.setText(employeeData.getContactInfo().getPhoneNumber());
            txtAddress.setText(employeeData.getContactInfo().getAddress());

            // Government ID
            lblSSSNumber.setText(employeeData.getGovID().getsssID());
            lblPhilhealthNumber.setText(employeeData.getGovID().getphilhealthID());
            lblTinNumber.setText(employeeData.getGovID().gettinID());
            lblPagibigNumber.setText(employeeData.getGovID().getpagibigID());

            // Compensation
            lblBasicSalary.setText(String.format("₱ " + "%,.2f", employeeData.getCompensation().getBasicSalary()));
            lblRiceSubsidy.setText(String.format("₱ " + "%,.2f", employeeData.getCompensation().getRiceSubsidy()));
            lblPhoneAllowance.setText(String.format("₱ " + "%,.2f", employeeData.getCompensation().getPhoneAllowance()));
            lblClothingAllowance.setText(String.format("₱ " + "%,.2f", employeeData.getCompensation().getClothingAllowance()));
            lblGrossSemi.setText(String.format("₱ " + "%,.2f", employeeData.getCompensation().getGrossSemiMonthlyRate()));
            lblHourlyRate.setText(String.format("₱ " + "%,.2f", employeeData.getCompensation().getHourlyRate()));

        }
    }
    // Compute salary based on the selected month
    private void computeSalary (String selectedEmployee, String selectedMonth) {
        // Initialized the attendance and employee objects
        EmployeeDataFromFile employeeDataFromFile = new EmployeeDataFromFile();
        ArrayList<Employee> employees = employeeDataFromFile.getEmployeeData();
        AttendanceDataFromFile attendanceDataFromFile = new AttendanceDataFromFile();
        ArrayList<Attendance> attendances = attendanceDataFromFile.getAttendanceData();

        double totalHoursWorked = 0.0;
        Employee employee = null;

        // iterates through the (employees) array list
        for (Employee emp : employees) {
            if (emp != null && String.valueOf(emp.getEmployeeNumber()).equals(selectedEmployee)) {
                // if not null and employee number match: then an employee object is stored in employee
                employee = emp;
                break;
            }
        }

        if (employee != null) {
            // iterates through the (attendance) array list
            for (Attendance att : attendances) {
                if (att != null && String.valueOf(att.getEmployee().getEmployeeNumber()).equals(selectedEmployee)) {
                    totalHoursWorked += getDates(selectedMonth, att);
                }
            }

            // Calculate the gross based on hours worked
            double gross = totalHoursWorked * employee.getCompensation().getHourlyRate();
            double deductions = employee.getSalaryDeduction()
                    .getTotalDeductions(employee.getCompensation().getBasicSalary());
            // Calculate netSalary
            double netSalary = gross - deductions;

            double basicSalary = employee.getCompensation().getBasicSalary();

            lblHoursWork.setText(String.format("%,.2f hours", totalHoursWorked));
            lblSSS.setText(String.format("%,.2f", employee.getSalaryDeduction().getSSSdeduction(basicSalary)));
            lblWithholdingTax.setText(String.format("%,.2f", employee.getSalaryDeduction().getWithholdingTax(basicSalary)));
            lblPhilhealth.setText(String.format("%,.2f", employee.getSalaryDeduction().getPhilHealthDeduction(basicSalary)));
            lblPagibig.setText(String.format("%,.2f", employee.getSalaryDeduction().getPagibigDeduction(basicSalary)));
            lblTotalDeduction.setText(String.format("%,.2f", employee.getSalaryDeduction().getTotalDeductions(basicSalary)));

            lblNetSalary.setText("₱ " + String.format("%,.2f", netSalary));
        }

        if (totalHoursWorked == 0.0) {
            // Default display
            lblHoursWork.setText("0.00 Hours");
            lblSSS.setText("₱ 0000.00");
            lblWithholdingTax.setText("₱ 0000.00");
            lblPhilhealth.setText("₱ 0000.00");
            lblPagibig.setText("₱ 0000.00");
            lblTotalDeduction.setText("₱ 0000.00");
            lblNetSalary.setText("₱ 0000.00");
            JOptionPane.showMessageDialog(EmployeeDetails.this,
                    "No attendance data from the selected month\nSelect a different month",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private double getDates (String selectedMonth, Attendance att) {
        double totalHoursWorked = 0.0;
        LocalDate date = att.getAttendanceDate();

        LocalDate[] month = getMonth(selectedMonth);
        if (month == null) {return 0.0;}

        LocalDate startDate = month[0];
        LocalDate endDate = month[1];

        if (!date.isBefore(startDate) && !date.isAfter(endDate)) {
            LocalTime timeIn = att.getTimeIn();
            LocalTime timeOut = att.getTimeOut();
            double hoursWork = Duration.between(timeIn, timeOut).toMinutes() / 60.0;
            totalHoursWorked += hoursWork; // add hourswork to total hours worked
        }
        return totalHoursWorked;
    }

    private LocalDate[] getMonth (String selectedMonth) {
        // convert month name to number
        int month;
        switch (selectedMonth.trim().toLowerCase()) {
            case "january" -> month = 1;
            case "february" -> month = 2;
            case "march" -> month = 3;
            case "april" -> month = 4;
            case "may" -> month = 5;
            case "june" -> month = 6;
            case "july" -> month = 7;
            case "august" -> month = 8;
            case "september" -> month = 9;
            case "october" -> month = 10;
            case "november" -> month = 11;
            case "december" -> month = 12;
            default -> {
                System.out.println("Selectetd Month: " + selectedMonth);
                JOptionPane.showMessageDialog(EmployeeDetails.this,
                    "Invalid Month", "Invalid", JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
        }

        try {
            LocalDate startDate = LocalDate.of(2024, month, 1);

            LocalDate endDate = startDate.withDayOfMonth(
                    startDate.getMonth().length(startDate.isLeapYear()));
            return new LocalDate[]{startDate, endDate};

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
