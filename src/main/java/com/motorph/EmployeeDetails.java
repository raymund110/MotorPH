package com.motorph;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private JLabel lblPhulhealth;
    private JLabel lblTotalDeduction;
    private JLabel lblPagibig;
    private JLabel lblWithholdingTax;
    private JLabel lblSSS;
    private JLabel lblNetSalary;
    private JButton btnCompute;
    private JComboBox cbxMonthSalary;
    private JButton btnBack;

    public EmployeeDetails (String selectedEmployee) {
        this.setContentPane(this.panelEmpDetails);
        this.setTitle("MotorPH");
        this.setSize(850,700);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Logo Config
        ImageIcon logo = new ImageIcon("src/main/resources/MotorPH-Logo.png");
        this.setIconImage(logo.getImage());

        this.setVisible(true);

        employeeInfo(selectedEmployee);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(EmployeeDetails.this,
                        "Are sure you want to go back?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (option == 0) {
                    new MainFrame();
                    dispose();
                }

            }
        });
    }

    public void employeeInfo (String selectedEmployeeNumber) {
        EmployeeDataFromFile employeeDataFromFile = new EmployeeDataFromFile();
        ArrayList<Employee> employees = employeeDataFromFile.getEmployeeData();

        // Placeholder remains null if the selected employee is not found
        Employee employeeData = null;

        // iterates through the (employees) array list
        for (Employee emp : employees) {
            if (emp != null && String.valueOf(emp.getEmployeeNumber()).equals(selectedEmployeeNumber)) {
                // if not null and employee number match: then an employee object is stored in searchedEmployee
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

}
