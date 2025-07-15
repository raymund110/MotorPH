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
    private JTextArea txtAddress;
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
    private JButton btnBack;

    public NewEmployee () {
        this.setContentPane(this.pnlNewEmployee);
        this.setTitle("MotorPH");
        this.setSize(700,820);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        // confirmation on closing the frame
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(NewEmployee.this,
                        "Are you sure you want to close New Employee window", "Confirmation", JOptionPane.YES_NO_OPTION);
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
        // Placeholders for users to know the important formats
        basicPlaceholder(txtBirthday, "MM/dd/yyyy");
        basicPlaceholder(txtSupervisor, "Last Name, First Name");
        basicPlaceholder(txtPhoneNumber, "000-000-000");
        basicPlaceholder(txtSSSNumber, "00-0000000-0");
        basicPlaceholder(txtTINNumber, "000-000-000-000");

        moneyPlaceholder(txtBasicSalary, "₱ 00,000,00");
        moneyPlaceholder(txtRiceSubsidy, "₱ 0,000,00");
        moneyPlaceholder(txtPhoneAllowance, "₱ 0,000,00");
        moneyPlaceholder(txtClothingAllowance, "₱ 0,000,00");
        moneyPlaceholder(txtGrossSemi, "₱ 00,000,00");
        moneyPlaceholder(txtHourlyRate, "₱ 000,00");

        // While focused is on JTextArea, TAB key will transfer the focus to the next component instead of giving an indent.
        txtAddress.setFocusTraversalKeysEnabled(true);
        InputMap inputMap = txtAddress.getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(KeyStroke.getKeyStroke("TAB"), "focusNext");
        txtAddress.getActionMap().put("focusNext", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtAddress.transferFocus();
            }
        });

        // Save button
        btnSaveEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateInputs()) { // If inputs are valid
                    EmployeeDataFile employeeData = new EmployeeDataFile();
                    // process the saving of employee data to the CSV file using the EmployeeDataFile
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

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(NewEmployee.this,
                        "Are you sure you want to go back to Main MotorPH Window", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    new MainFrame();
                    dispose();
                }
            }
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
                    "Please enter valid date format.\nMM/dd/yyyy",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Check phone number format
        if (!txtPhoneNumber.getText().matches("\\d{3}-\\d{3}-\\d{3}")) {
            JOptionPane.showMessageDialog(NewEmployee.this,
                    "Invalid Phone Number format.\nInput must be in the format 000-000-000",
                    "Invalid Format",
                    JOptionPane.ERROR_MESSAGE);
            txtPhoneNumber.requestFocus();
            return false;
        }
        // Check SSS number format
        if (!txtSSSNumber.getText().matches("\\d{2}-\\d{7}-\\d{1}")) {
            JOptionPane.showMessageDialog(NewEmployee.this,
                    "Invalid SSS Number format.\nInput must be in the format 00-0000000-0",
                    "Invalid Format",
                    JOptionPane.ERROR_MESSAGE);
            txtSSSNumber.requestFocus();
            return false;
        }
        // Check Philhealth number format
        if (!txtPhilhealthNumber.getText().matches("\\d{12}")) {
            JOptionPane.showMessageDialog(NewEmployee.this,
                    "Invalid Philhealth Number.\nPhilhealth Number must have 12 digits",
                    "Invalid Format",
                    JOptionPane.ERROR_MESSAGE);
            txtPhilhealthNumber.requestFocus();
            return false;
        }
        // Check TIN number format
        if (!txtTINNumber.getText().matches("\\d{3}-\\d{3}-\\d{3}-\\d{3}")) {
            JOptionPane.showMessageDialog(NewEmployee.this,
                    "Invalid TIN Number.\nInput must be in the format 000-000-000-000",
                    "Invalid Format",
                    JOptionPane.ERROR_MESSAGE);
            txtTINNumber.requestFocus();
            return false;
        }
        // Check Pagibig number format
        if (!txtPagibigNumber.getText().matches("\\d{12}")) {
            JOptionPane.showMessageDialog(NewEmployee.this,
                    "Invalid Pagibig Number.\nPagibig Number must have 12 digits",
                    "Invalid Format",
                    JOptionPane.ERROR_MESSAGE);
            txtPagibigNumber.requestFocus();
            return false;
        }
        try { //Checking the validity of numerical inputs
            Integer.parseInt(txtEmployeeNumber.getText().trim());

            moneyInputs(txtBasicSalary.getText());
            moneyInputs(txtRiceSubsidy.getText());
            moneyInputs(txtPhoneAllowance.getText());
            moneyInputs(txtClothingAllowance.getText());
            moneyInputs(txtGrossSemi.getText());
            moneyInputs(txtHourlyRate.getText());
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

    // parse money input
    private double moneyInputs(String money) {
        // Remove peso sign spaces, and commas
        String cleaningInputs = money.replace("₱", "").replace(" ", "").replace(",", "");
        return Double.parseDouble(cleaningInputs);
    }
    // Save employee data inputs
    public String[] saveEmployee () {
        // Basic info
        String empNumber = (txtEmployeeNumber.getText());
        String lastName = (txtLastName.getText());
        String firstName = (txtFirstName.getText());
        String birthday = (txtBirthday.getText());
        // Contact info
        String address = txtAddress.getText();
        String phoneNumber = txtPhoneNumber.getText();
        // GOV ID
        String sssID = (txtSSSNumber.getText());
        String philhealthID = (txtPhilhealthNumber.getText());
        String tinID = (txtTINNumber.getText());
        String pagibigID = (txtPagibigNumber.getText());
        // Job
        String status = String.valueOf(cbxStatus.getSelectedItem());
        String position = txtPosition.getText();
        String supervisor = txtSupervisor.getText();
        // Compensation
        // Decimael formatter to match the csv text
        DecimalFormat salaryFormat = new DecimalFormat("#,###"); // No decimal points
        DecimalFormat hourlyFormat = new DecimalFormat("#,##0.00"); // With decimal points

        String basicSalary = salaryFormat.format(moneyInputs(txtBasicSalary.getText()));
        String riceSubsidy = salaryFormat.format(moneyInputs(txtRiceSubsidy.getText()));
        String phoneAllowance = salaryFormat.format(moneyInputs(txtPhoneAllowance.getText()));
        String clothingAllowance = salaryFormat.format(moneyInputs(txtClothingAllowance.getText()));
        String grossSemi = salaryFormat.format(moneyInputs(txtGrossSemi.getText()));
        String hourlyRate = hourlyFormat.format(moneyInputs(txtHourlyRate.getText()));

        return new String[]{empNumber, lastName, firstName, birthday
                , address, phoneNumber, sssID, philhealthID, tinID, pagibigID, status, position, supervisor,
                basicSalary, riceSubsidy, phoneAllowance, clothingAllowance, grossSemi, hourlyRate};
    }
    // Placeholder method for basic information format
    private void basicPlaceholder (JTextField textField, String placeholder) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                super.focusLost(e);
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }
    // Placeholder method for money format
    private void moneyPlaceholder(JTextField textField, String placeholder) {
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("₱ ");
                    textField.setForeground(Color.BLACK);
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                // If peso value was not set
                if (textField.getText().equals("₱ ")) {
                    textField.setText("");
                }
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

}
