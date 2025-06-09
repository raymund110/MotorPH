package com.motorph;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import javax.swing.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeDataFile {
    private final ArrayList<Employee> employees = new ArrayList<>();
    private static final String DataFile = "src/main/resources/MotorPH Employee Data.csv";
    // Parser config
    private final CSVParser parser = new CSVParserBuilder()
            .withSeparator(',')
            .withIgnoreQuotations(false)
            .build();

    public EmployeeDataFile() {
        dataFromFile(DataFile);
    }

    // Getter method that returns employees ArrayList that contains all employee objects
    public ArrayList<Employee> getEmployeeData() {
        return employees;
    }

    // Method that reads the CSV file of employee data
    public void dataFromFile(String employeeData) {
        try (CSVReader reader = new CSVReaderBuilder(new FileReader(employeeData))
                .withCSVParser(parser).build()){

            reader.readNext(); // Skip header
            String[] line;

            // Read the csv file line by line
            while ((line = reader.readNext()) != null) {
                employees.add(createEmployee(line)); // Add employee objects to employees ArrayList
            }
        } catch (Exception e) { // Handle reading file errors
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Add new employee data to the file. Returning boolean value for showing diaglog
    public boolean addNewEmployee (String employeeData, JFrame NewEmployee) {
        try {
            String newEmpNumber = employeeData.split(",")[0].trim();
            // Read the existing file to check the duplicate employee number
            try (CSVReader reader = new CSVReaderBuilder(new FileReader(DataFile))
                    .withCSVParser(parser).build()) {

                String[] line;
                reader.readNext();

                while ((line = reader.readNext()) != null) {
                    // check if the employee number is already existing
                    if (line[0].trim().equals(newEmpNumber) ) {
                        // Message dialog if the employee already exists
                        JOptionPane.showMessageDialog(NewEmployee,
                                "Employee Number already exists!",
                                "Already Exists", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                }
            }
            // Append new employee data
            try (CSVWriter writer = new CSVWriter(new FileWriter(DataFile, true),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.DEFAULT_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END)) {

                // Convert the employeeData string to array
                String[] employeeFields = employeeData.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                writer.writeNext(employeeFields);
                return true;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error saving employee data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Update existing employee data in the file
    public boolean updateEmployee(String employeeNumber, JFrame UpdateEmployee, Employee updatedEmployee) {
        try {
            ArrayList<String[]> updatedData = new ArrayList<>();
            boolean employeeFound = false;

            // Read the existing data from the file
            try (CSVReader reader = new CSVReaderBuilder(new FileReader(DataFile))
                    .withCSVParser(parser).build()) {

                // Store header
                updatedData.add(reader.readNext());
                String[] line;

                // Read and store all lines, updating the matching employee
                while ((line = reader.readNext()) != null) {
                    if (line[0].trim().equals(employeeNumber)) {
                        employeeFound = true;
                        updatedData.add(new String[]{
                                String.valueOf(updatedEmployee.getEmployeeNumber()),
                                updatedEmployee.getPerson().getLastName(),
                                updatedEmployee.getPerson().getFirstName(),
                                updatedEmployee.getBirthday().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                                updatedEmployee.getContactInfo().getAddress(),
                                updatedEmployee.getContactInfo().getPhoneNumber(),
                                updatedEmployee.getGovID().getsssID(),
                                updatedEmployee.getGovID().getphilhealthID(),
                                updatedEmployee.getGovID().gettinID(),
                                updatedEmployee.getGovID().getpagibigID(),
                                updatedEmployee.getStatus(),
                                updatedEmployee.getJob().getPosition(),
                                updatedEmployee.getJob().getSupervisor(),
                                String.format("%.2f", updatedEmployee.getCompensation().getBasicSalary()),
                                String.format("%.2f", updatedEmployee.getCompensation().getRiceSubsidy()),
                                String.format("%.2f", updatedEmployee.getCompensation().getPhoneAllowance()),
                                String.format("%.2f", updatedEmployee.getCompensation().getClothingAllowance()),
                                String.format("%.2f", updatedEmployee.getCompensation().getGrossSemiMonthlyRate()),
                                String.format("%.2f", updatedEmployee.getCompensation().getHourlyRate())

                        });
                    } else {
                        updatedData.add(line);
                    }
                }
            }

            if (!employeeFound) {
                JOptionPane.showMessageDialog(UpdateEmployee,
                        "Employee not found!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }


            // Write all data back to the file
            try (CSVWriter writer = new CSVWriter(new FileWriter(DataFile),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.DEFAULT_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END)) {

                writer.writeAll(updatedData);
                JOptionPane.showMessageDialog(UpdateEmployee,
                        "Employee information updated successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error updating employee data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Delete the selected employee from the table
    public boolean deleteEmployee(String employeeNumber, JFrame frame) {
        try {
            ArrayList<String[]> remainingData = new ArrayList<>();
            boolean employeeFound = false;

            // Read all existing data except the one to be deleted
            try (CSVReader reader = new CSVReaderBuilder(new FileReader(DataFile))
                    .withCSVParser(parser).build()) {

                // Store header
                remainingData.add(reader.readNext());
                String[] line;

                while ((line = reader.readNext()) != null) {
                    if (line[0].trim().equals(employeeNumber)) {
                        employeeFound = true;
                    } else {
                        remainingData.add(line);
                    }
                }
            }

            if (!employeeFound) {
                JOptionPane.showMessageDialog(frame,
                        "Employee not found!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Write remaining data back to the file
            try (CSVWriter writer = new CSVWriter(new FileWriter(DataFile),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.DEFAULT_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END)) {

                writer.writeAll(remainingData);
                JOptionPane.showMessageDialog(frame,
                        "Employee deleted successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "Error deleting employee data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Converts single lines of CSV data into an Employee Objects
    private Employee createEmployee(String[] values) {
        // Instantiate employee-related class
        Employee employee = new Employee();
        Compensation compensation = new Compensation();
        ContactInfo contactInfo = new ContactInfo();
        Person person = new Person();
        GovernmentID govID = new GovernmentID();
        Job job = new Job();

        // Parse and set all employee attributes
        try {
            employee.setEmployeeNumber(Integer.parseInt(values[0].trim()));
            // Basic employee info
            employee.setPerson(person);
            person.setLastName(values[1].trim());
            person.setFirstName(values[2].trim());

            // Format data
            DateTimeFormatter birthdayFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            employee.setBirthday(LocalDate.parse(values[3].trim(), birthdayFormat));

            // Contacts
            employee.setContactInfo(contactInfo);
            contactInfo.setAddress(values[4].trim());
            contactInfo.setPhoneNumber(values[5].trim());

            // GovernmentID
            employee.setGovID(govID);
            govID.setsssID(values[6].trim());
            govID.setphilhealthID(values[7].trim());
            govID.settinID(values[8].trim());
            govID.setpagibigID(values[9].trim());

            employee.setStatus(values[10].trim());

            // Job
            employee.setJob(job);
            job.setPosition(values[11].trim());
            job.setSupervisor(values[12].trim());

            // Compensation
            employee.setCompensation(compensation);
            compensation.setBasicSalary(Double.parseDouble(values[13].replaceAll(",", "").trim()));
            compensation.setRiceSubsidy(Double.parseDouble(values[14].replaceAll(",", "").trim()));
            compensation.setPhoneAllowance(Double.parseDouble(values[15].replaceAll(",", "").trim()));
            compensation.setClothingAllowance(Double.parseDouble(values[16].replaceAll(",", "").trim()));
            compensation.setGrossSemiMonthlyRate(Double.parseDouble(values[17].replaceAll(",", "").trim()));
            compensation.setHourlyRate(Double.parseDouble(values[18].replaceAll(",", "").trim()));

        }

        catch (Exception e) { // Handle parsing errors
            System.out.println("Error creating employee: " + e.getMessage());
        }
        return employee;
    }
}
