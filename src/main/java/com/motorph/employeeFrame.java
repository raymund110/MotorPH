package com.motorph;

import javax.swing.*;

public class employeeFrame extends JFrame {

    private JPanel empDashboard;
    private JTextField txtEmpNumber;
    private JTextField txtStatus;
    private JTextField txtPosition;
    private JTextField txtSupervisor;
    private JTextField txtEmpName;
    private JTextArea textArea1;

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
        this.setSize(750,500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the text fields if employee is found
        if (loggedInEmployee != null) {
            txtEmpNumber.setText(String.valueOf(loggedInEmployee.getEmployeeNumber()));
            txtEmpName.setText(loggedInEmployee.getPerson().getFirstName() + " " +
                    loggedInEmployee.getPerson().getLastName());
            txtStatus.setText(loggedInEmployee.getStatus());
            txtPosition.setText(loggedInEmployee.getJob().getPosition());
            txtSupervisor.setText(loggedInEmployee.getJob().getSupervisor());
        }

        this.setVisible(true);
    }


}
