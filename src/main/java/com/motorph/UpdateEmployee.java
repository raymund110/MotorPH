package com.motorph;

import javax.swing.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class UpdateEmployee extends JFrame{
    private JPanel pnlUpdateEmployee;
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
    private JLabel lblEmployeeNumber;

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
                int option = JOptionPane.showConfirmDialog(UpdateEmployee.this,
                        "Are you sure you want to close Update Employee Window", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    new MainFrame();
                    dispose();
                }
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
                int option = JOptionPane.showConfirmDialog(UpdateEmployee.this,
                        "Are you sure you want to go back to Main MotorPH", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    new MainFrame();
                    dispose();
                }
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
            lblEmployeeNumber.setText(String.valueOf(employeeData.getEmployeeNumber()));
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

    // parse money input
    private double moneyInputs(String money) {
        // Remove peso sign spaces, and commas
        String cleaningInputs = money.replace("₱", "").replace(" ", "").replace(",", "");
        return Double.parseDouble(cleaningInputs);
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

            // Set all the updated value
            updatedEmployee.setEmployeeNumber(Integer.parseInt(lblEmployeeNumber.getText()));
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

            compensation.setBasicSalary(moneyInputs(txtBasicSalary.getText()));
            compensation.setRiceSubsidy(moneyInputs(txtRiceSubsidy.getText()));
            compensation.setPhoneAllowance(moneyInputs(txtPhoneAllowance.getText()));
            compensation.setClothingAllowance(moneyInputs(txtClothingAllowance.getText()));
            compensation.setGrossSemiMonthlyRate(moneyInputs(txtGrossSemi.getText()));
            compensation.setHourlyRate(moneyInputs(txtHourlyRate.getText()));
            updatedEmployee.setCompensation(compensation);

            // Call the update function
            EmployeeDataFile employeeDataFile = new EmployeeDataFile();
            if (employeeDataFile.updateEmployee(lblEmployeeNumber.getText(), UpdateEmployee.this, updatedEmployee)) {
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
