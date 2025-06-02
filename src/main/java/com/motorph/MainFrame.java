package com.motorph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JPanel panelMotorPH;
    private JTable tableEmployeeList;
    private JButton btnViewEmployee;
    private JButton btnNewEmployee;
    private JButton btnLogout;

    private String selectedEmployee; // Placeholder for current selected employee

    public MainFrame() {
        // Frame config
        this.setContentPane(this.panelMotorPH);
        this.setTitle("MotorPH");
        this.setSize(1050,630);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        // confirmation on closing the window
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                int option = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Are you sure you want to close MotorPH", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    dispose();
                    System.exit(0);
                }
            }
        });

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
                // get EmployeeNumber(column 0) from the selected row
                selectedEmployee = model.getValueAt(row, 0).toString();
                btnViewEmployee.setEnabled(true); // Make view employee clickable
            }
        });

        // View Employee
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

        // Logout
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
        // New Employee
        btnNewEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(MainFrame.this,
                        "Are you sure you want to add new employee?", "Confirmation", JOptionPane.YES_NO_OPTION);

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
}
