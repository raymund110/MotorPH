package com.motorph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class NewEmployee extends JFrame{
    private JPanel pnlNewEmployee;
    private JTextField txtEmployeeNumber;
    private JTextField txtFirstName;
    private JTextField txtLastName;
    private JTextField txtBirthday;
    private JTextField txtPhoneNumber;
    private JTextField txtAddress;
    private JTextField txtPosition;
    private JTextField txtSupervisor;
    private JTextField txtPhilhealthNumber;
    private JTextField txtSSSNumber;
    private JTextField txtTINNumber;
    private JTextField txtPagibigNumber;
    private JTextField txtRiceSubsidy;
    private JTextField txtBasicSalary;
    private JTextField txtPhoneAllowance;
    private JTextField txtClothingAllowance;
    private JTextField txtGrossSemi;
    private JTextField txtHourlyRate;
    private JButton btnSaveEmployee;
    private JComboBox cbxStatus;

    public NewEmployee () {
        this.setContentPane(this.pnlNewEmployee);
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
                int option = JOptionPane.showConfirmDialog(NewEmployee.this,
                        "Exit on creating new employee details?", "Confirmation", JOptionPane.YES_NO_OPTION);
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
        // Placeholders for input formats
        placeholders();

        // Save button
        btnSaveEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) { // If inputs are valid
                    EmployeeDataFromFile employeeData = new EmployeeDataFromFile();
                    // process the saving of employee data to the CSV file using the EmployeeDataFromFile
                    boolean saved = employeeData.addNewEmployee(saveEmployee(), NewEmployee.this);
                    if (saved) { // message to the user if addNewEmployee method returns true
                        JOptionPane.showMessageDialog(NewEmployee.this,
                                "Employee saved successfully!",
                                "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                        new MainFrame();
                    }
                }
            }
        });
        pnlNewEmployee.addComponentListener(new ComponentAdapter() {
        });
    }
    // validating all input formats
    private boolean validateInputs () {
        // Checking formats text fields if empty
        if (txtEmployeeNumber.getText().trim().isEmpty() || txtLastName.getText().trim().isEmpty() || txtFirstName.getText().trim().isEmpty() ||
                txtBirthday.getText().trim().isEmpty() || txtPhoneNumber.getText().trim().isEmpty() || txtAddress.getText().trim().isEmpty() ||
                txtPosition.getText().trim().isEmpty() || txtSupervisor.getText().trim().isEmpty() || txtSSSNumber.getText().trim().isEmpty() ||
                txtPhilhealthNumber.getText().trim().isEmpty() || txtTINNumber.getText().trim().isEmpty() || txtPagibigNumber.getText().trim().isEmpty() ||
                txtBasicSalary.getText().trim().isEmpty() || txtRiceSubsidy.getText().trim().isEmpty() || txtPhoneAllowance.getText().trim().isEmpty() ||
                txtClothingAllowance.getText().trim().isEmpty() || txtGrossSemi.getText().trim().isEmpty() || txtHourlyRate.getText().trim().isEmpty()
            ){
            JOptionPane.showMessageDialog(NewEmployee.this,
                    "Please fill in all required fields",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try { // checking the birthday format input
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate.parse(txtBirthday.getText().trim(), formatter);
        } catch (DateTimeException e) {
            JOptionPane.showMessageDialog(NewEmployee.this,
                    "Please enter valid date format",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try { //Checking the validity of numerical inputs
            Integer.parseInt(txtEmployeeNumber.getText().trim());
            Double.parseDouble(txtBasicSalary.getText().trim());
            Double.parseDouble(txtRiceSubsidy.getText().trim());
            Double.parseDouble(txtPhoneAllowance.getText().trim());
            Double.parseDouble(txtClothingAllowance.getText().trim());
            Double.parseDouble(txtGrossSemi.getText().trim());
            Double.parseDouble(txtHourlyRate.getText().trim());
            // Add more numeric validations as needed
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(NewEmployee.this,
                    "Please enter valid numeric values",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true; // inputs are valid
    }
    // Save employee data inputs
    public String saveEmployee () {
        // Basic info
        String empNumber = String.valueOf(txtEmployeeNumber.getText());
        String lastName = String.valueOf(txtLastName.getText());
        String firstName = String.valueOf(txtFirstName.getText());
        String birthday = String.valueOf(txtBirthday.getText());
        // Contact info
        String address = "\"" + txtAddress.getText() + "\"";
        String phoneNumber = txtPhoneNumber.getText();
        // GOV ID
        String sssID = String.valueOf(txtSSSNumber.getText());
        String philhealthID = String.valueOf(txtPhilhealthNumber.getText());
        String tinID = String.valueOf(txtTINNumber.getText());
        String pagibigID = String.valueOf(txtPagibigNumber.getText());
        // Job
        String status = String.valueOf(cbxStatus.getSelectedItem());
        String position = txtPosition.getText();
        String supervisor = "\"" + txtSupervisor.getText() + "\"";

        // Compensation
        // Decimael formatter to match the csv text
        DecimalFormat salaryFormat = new DecimalFormat("#,###");
        DecimalFormat hourlyFormat = new DecimalFormat("#,##0.00");

        String basicSalary = "\"" + salaryFormat.format(Double.parseDouble(txtBasicSalary.getText())) + "\"";
        String riceSubsidy = "\"" + salaryFormat.format(Double.parseDouble(txtRiceSubsidy.getText())) + "\"";
        String phoneAllowance = "\"" + salaryFormat.format(Double.parseDouble(txtPhoneAllowance.getText())) + "\"";
        String clothingAllowance = "\"" + salaryFormat.format(Double.parseDouble(txtClothingAllowance.getText())) + "\"";
        String grossSemi = "\"" + salaryFormat.format(Double.parseDouble(txtGrossSemi.getText())) + "\"";

        String hourlyRate = hourlyFormat.format(Double.parseDouble(txtHourlyRate.getText()));

        return empNumber + "," + lastName + "," + firstName  + "," + birthday
                + "," + address + "," + phoneNumber + "," +
                sssID + "," + philhealthID + "," + tinID + "," + pagibigID
                + "," + status + "," + position + "," + supervisor
                + "," + basicSalary + "," + riceSubsidy + "," + phoneAllowance
                + "," + clothingAllowance + "," + grossSemi + "," + hourlyRate;
    }

    // Placeholders for users to know the important formats
    private void placeholders () {
        // Birthday placeholder
        txtBirthday.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (txtBirthday.getText().equals("MM/dd/yyyy")) {
                    txtBirthday.setText("");
                    txtBirthday.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (txtBirthday.getText().isEmpty()) {
                    txtBirthday.setText("MM/dd/yyyy");
                    txtBirthday.setForeground(Color.GRAY);
                }
            }
        });
        // Supervisor placeholder
        txtSupervisor.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (txtSupervisor.getText().equals("Last Name, FIrst Name")) {
                    txtSupervisor.setText("");
                    txtSupervisor.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (txtSupervisor.getText().isEmpty()) {
                    txtSupervisor.setText("Last Name, FIrst Name");
                    txtSupervisor.setForeground(Color.GRAY);
                }
            }
        });
        // Phone number placeholder
        txtPhoneNumber.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (txtPhoneNumber.getText().equals("000-000-000")) {
                    txtPhoneNumber.setText("");
                    txtPhoneNumber.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (txtPhoneNumber.getText().isEmpty()) {
                    txtPhoneNumber.setText("000-000-000");
                    txtPhoneNumber.setForeground(Color.GRAY);
                }
            }
        });
        // SSS number placeholder
        txtSSSNumber.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (txtSSSNumber.getText().equals("00-0000000-0")) {
                    txtSSSNumber.setText("");
                    txtSSSNumber.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (txtSSSNumber.getText().isEmpty()) {
                    txtSSSNumber.setText("00-0000000-0");
                    txtSSSNumber.setForeground(Color.GRAY);
                }
            }
        });
        // TIN number placeholder
        txtTINNumber.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                if (txtTINNumber.getText().equals("000-000-000-000")) {
                    txtTINNumber.setText("");
                    txtTINNumber.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (txtTINNumber.getText().isEmpty()) {
                    txtTINNumber.setText("000-000-000-000");
                    txtTINNumber.setForeground(Color.GRAY);
                }
            }
        });
    }

}
