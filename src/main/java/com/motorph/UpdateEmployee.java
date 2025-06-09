package com.motorph;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class UpdateEmployee extends JFrame{
    private JPanel pnlUpdateEmployee;
    private JTextField txtEmployeeNumber;
    private JTextField txtFirstName;
    private JTextField txtBirthday;
    private JTextField txtLastName;
    private JTextField txtPhoneNumber;
    private JTextArea txtAddress;
    private JTextField txtPosition;
    private JTextField txtSupervisor;
    private JComboBox cbxStatus;
    private JTextField txtBasicSalary;
    private JTextField txtRiceSubsidy;
    private JTextField txtPhoneAllowance;
    private JTextField txtClothingAllowance;
    private JTextField txtGrossSemi;
    private JTextField txtHourlyRate;
    private JTextField txtSSSNumber;
    private JTextField txtPhilhealthNumber;
    private JTextField txtTINNumber;
    private JTextField txtPagibigNumber;
    private JButton btnUpdateEmployee;
    private JButton btnBack;

    public UpdateEmployee (String selectedEmployee) {
        this.setContentPane(this.pnlUpdateEmployee);
        this.setTitle("MotorPH");
        this.setSize(800,650);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        // confirmation on closing the frame
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                new MainFrame();
                dispose();
            }
        });

        // Logo Config
        ImageIcon logo = new ImageIcon("src/main/resources/MotorPH-Logo.png");
        this.setIconImage(logo.getImage());

        this.setVisible(true);
        employeeInfo(selectedEmployee);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainFrame();
                dispose();
            }
        });

        btnUpdateEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeeChanges();
            }
        });

    }

    private void employeeInfo (String selectedEmployeeNumber) {
        EmployeeDataFile employeeDataFromFile = new EmployeeDataFile();
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
            txtEmployeeNumber.setText(String.valueOf(employeeData.getEmployeeNumber()));
            txtFirstName.setText(employeeData.getPerson().getFirstName());
            txtLastName.setText(employeeData.getPerson().getLastName());
            txtBirthday.setText(String.valueOf(employeeData.getBirthday()));

            int status = 0;
            if (employeeData.getStatus().equals("Regular")) {
                cbxStatus.setSelectedIndex(status);
            } else { //Probationary
                status = 1;
                cbxStatus.setSelectedIndex(status);
            }

            txtPosition.setText(employeeData.getJob().getPosition());
            txtSupervisor.setText(employeeData.getJob().getSupervisor());

            // Contacts
            txtPhoneNumber.setText(employeeData.getContactInfo().getPhoneNumber());
            txtAddress.setText(employeeData.getContactInfo().getAddress());

            // Government ID
            txtSSSNumber.setText(employeeData.getGovID().getsssID());
            txtPhilhealthNumber.setText(employeeData.getGovID().getphilhealthID());
            txtTINNumber.setText(employeeData.getGovID().gettinID());
            txtPagibigNumber.setText(employeeData.getGovID().getpagibigID());

            // Compensation
            txtBasicSalary.setText(String.format("₱ " + "%,.2f", employeeData.getCompensation().getBasicSalary()));
            txtRiceSubsidy.setText(String.format("₱ " + "%,.2f", employeeData.getCompensation().getRiceSubsidy()));
            txtPhoneAllowance.setText(String.format("₱ " + "%,.2f", employeeData.getCompensation().getPhoneAllowance()));
            txtClothingAllowance.setText(String.format("₱ " + "%,.2f", employeeData.getCompensation().getClothingAllowance()));
            txtGrossSemi.setText(String.format("₱ " + "%,.2f", employeeData.getCompensation().getGrossSemiMonthlyRate()));
            txtHourlyRate.setText(String.format("₱ " + "%,.2f", employeeData.getCompensation().getHourlyRate()));

        }
    }

    private void employeeChanges () {
        try {
            // Create a new Employee object with updated information
            Employee updatedEmployee = new Employee();
            Person person = new Person();
            ContactInfo contactInfo = new ContactInfo();
            GovernmentID govID = new GovernmentID();
            Job job = new Job();
            Compensation compensation = new Compensation();

            // Set all the updated values
            updatedEmployee.setEmployeeNumber(Integer.parseInt(txtEmployeeNumber.getText()));

            person.setFirstName(txtFirstName.getText());
            person.setLastName(txtLastName.getText());
            updatedEmployee.setPerson(person);

            updatedEmployee.setBirthday(LocalDate.parse(txtBirthday.getText()));

            contactInfo.setPhoneNumber(txtPhoneNumber.getText());
            contactInfo.setAddress(txtAddress.getText());
            updatedEmployee.setContactInfo(contactInfo);

            updatedEmployee.setStatus(String.valueOf(cbxStatus.getSelectedItem()));

            job.setPosition(txtPosition.getText());
            job.setSupervisor(txtSupervisor.getText());
            updatedEmployee.setJob(job);

            govID.setsssID(txtSSSNumber.getText());
            govID.setphilhealthID(txtPhilhealthNumber.getText());
            govID.settinID(txtTINNumber.getText());
            govID.setpagibigID(txtPagibigNumber.getText());
            updatedEmployee.setGovID(govID);

            // Remove currency symbol and formatting for parsing
            String basicSalary = txtBasicSalary.getText().replace("₱", "").replace(",", "").trim();
            String riceSubsidy = txtRiceSubsidy.getText().replace("₱", "").replace(",", "").trim();
            String phoneAllowance = txtPhoneAllowance.getText().replace("₱", "").replace(",", "").trim();
            String clothingAllowance = txtClothingAllowance.getText().replace("₱", "").replace(",", "").trim();
            String grossSemi = txtGrossSemi.getText().replace("₱", "").replace(",", "").trim();
            String hourlyRate = txtHourlyRate.getText().replace("₱", "").replace(",", "").trim();

            compensation.setBasicSalary(Double.parseDouble(basicSalary));
            compensation.setRiceSubsidy(Double.parseDouble(riceSubsidy));
            compensation.setPhoneAllowance(Double.parseDouble(phoneAllowance));
            compensation.setClothingAllowance(Double.parseDouble(clothingAllowance));
            compensation.setGrossSemiMonthlyRate(Double.parseDouble(grossSemi));
            compensation.setHourlyRate(Double.parseDouble(hourlyRate));
            updatedEmployee.setCompensation(compensation);

            // Call the update function
            EmployeeDataFile employeeDataFile = new EmployeeDataFile();
            if (employeeDataFile.updateEmployee(txtEmployeeNumber.getText(), UpdateEmployee.this, updatedEmployee)) {
                new MainFrame();
                dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(UpdateEmployee.this,
                    "Error updating employee: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}
