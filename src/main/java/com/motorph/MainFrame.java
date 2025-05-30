package com.motorph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JPanel panelMotorPH;
    private JTable tableEmployeeList;
    private JButton btnViewEmployee;
    private JButton btnNewEmployee;
    private JButton btnLogout;

    private String selectedEmployee; // Placeholder for current selected employee

    public MainFrame() {
        this.setContentPane(this.panelMotorPH);
        this.setTitle("MotorPH");
        this.setSize(1050,630);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Logo Config
        ImageIcon logo = new ImageIcon("src/main/resources/MotorPH-Logo.png");
        this.setIconImage(logo.getImage());

        employeeTable();

        this.setVisible(true); // Set employeeFrame visible

        // get employee number on selected row
        tableEmployeeList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int row = tableEmployeeList.getSelectedRow(); // get selected row index
                DefaultTableModel model = (DefaultTableModel)tableEmployeeList.getModel();
                selectedEmployee = model.getValueAt(row, 0).toString();
//                btnViewEmployee.setEnabled(true);
            }
        });

        // View EmployeeDetails frame
        btnViewEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (selectedEmployee != null) {
                    new EmployeeDetails(selectedEmployee);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Select an employee on the table first.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        // Logout from MainFrame
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Are you sure you want to logout?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (option == 0) {
                    new loginFrame();
                    dispose();
                }
            }
        });

        btnNewEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Are you sure you want to create new employee?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (option == 0) {
                    new NewEmployee();
                    dispose();
                }
            }
        });
    }

    // Loads the employee table
    private void employeeTable () {
        // Instantiate Employee
        EmployeeDataFromFile dataFile = new EmployeeDataFromFile(); //
        // Retrieves the ArrayList of Employee objects from EmployeeDataFromFile
        ArrayList<Employee> employee = dataFile.getEmployeeData(); // Store Employee Objects

        // Column Names
        String[] columnName = {"Employee Number", "Last Name", "First Name",
                "SSS Number", "PhilHealth Number", "TIN", "Pag-IBIG Number"};

        DefaultTableModel model = new DefaultTableModel(columnName, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        // iterates through the (employees) array list and add rows
        for (Employee emp : employee) {
            if (emp != null) {
                Object[] row = {
                        emp.getEmployeeNumber(), emp.getPerson().getLastName(),
                        emp.getPerson().getFirstName(),
                        emp.getGovID().getsssID(), emp.getGovID().getphilhealthID(),
                        emp.getGovID().gettinID(), emp.getGovID().getpagibigID()
                };
                model.addRow(row);
            }
        }

        // Set a table model and some table configurations
        tableEmployeeList.setModel(model);
        tableEmployeeList.setAutoCreateRowSorter(true);
        tableEmployeeList.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        tableEmployeeList.setRowHeight(25);

    }

    // Calculate the net salary based on the start date and end date inputs by the user
//    private void hourBasedSalary (String searchedEmployeeNumber, LocalDate startDate, LocalDate endDate) {
//        // Initialized the attendance and employee objects
//        EmployeeDataFromFile employeeDataFromFile = new EmployeeDataFromFile();
//        ArrayList<Employee> employees = employeeDataFromFile.getEmployeeData();
//        AttendanceDataFromFile attendanceDataFromFile = new AttendanceDataFromFile();
//        ArrayList<Attendance> attendances = attendanceDataFromFile.getAttendanceData();
//
//        double totalHoursWorked = 0.0;
//        Employee employee = null;
//
//        // iterates through the (employees) array list
//        for (Employee emp : employees) {
//            if (emp != null && String.valueOf(emp.getEmployeeNumber()).equals(searchedEmployeeNumber)) {
//                // if not null and employee number match: then an employee object is stored in searchedEmployee
//                employee = emp;
//                break;
//            }
//        }
//
//        if (employee != null) {
//            // iterates through the (attendance) array list
//            for (Attendance att : attendances) {
//                if (att != null && String.valueOf(att.getEmployee().getEmployeeNumber()).equals(searchedEmployeeNumber)) {
//                    LocalDate date = att.getAttendanceDate();
//
//                    if ((date.equals(startDate) || date.isAfter(startDate)) &&
//                            (date.equals(endDate) || date.isBefore(endDate))) {
//                        LocalTime timeIn = att.getTimeIn();
//                        LocalTime timeOut = att.getTimeOut();
//                        double hoursWork = Duration.between(timeIn, timeOut).toMinutes() / 60.0;
//                        totalHoursWorked += hoursWork; // add hourswork to total hours worked
//                    }
//
//                }
//            }
//            // Calculate the gross based on hours worked
//            double gross = totalHoursWorked * employee.getCompensation().getHourlyRate();
//            double deductions = employee.getSalaryDeduction()
//                    .getTotalDeductions(employee.getCompensation().getBasicSalary());
//            // Calculate netSalary
//            double netSalary = gross - deductions;
//
//            lblWorkHours.setText(String.format("%,.2f hours", totalHoursWorked));
//            lblNetSalary.setText("₱ " + String.format("%,.2f", netSalary));
//        }
//    }

}
