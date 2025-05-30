package com.motorph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class EmployeeDataFromFile {
    private final ArrayList<Employee> employees = new ArrayList<>();

    public EmployeeDataFromFile() {
        String employeeData = "src/main/resources/MotorPH Employee Data.csv";
        dataFromFile(employeeData);
    }

    // Getter method that returns employees ArrayList that contains all employee objects
    public ArrayList<Employee> getEmployeeData() {
        return employees;
    }

    // Method that reads the CSV file of employee data
    public void dataFromFile(String employeeData) {
        try (BufferedReader reader = new BufferedReader(new FileReader(employeeData))) {
            reader.readLine(); // Skip header
            String line;

            // Read the csv file line by line
            while ((line = reader.readLine()) != null) {
                employees.add(createEmployee(line)); // Add employee objects to employees ArrayList
            }
        } catch (Exception e) { // Handle reading file errors
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Converts single lines of CSV data into an Employee Objects
    private Employee createEmployee(String lines) {
        // Instantiate employee-related class
        Employee employee = new Employee();
        Compensation compensation = new Compensation();
        ContactInfo contactInfo = new ContactInfo();
        Person person = new Person();
        GovernmentID govID = new GovernmentID();
        Job job = new Job();

        // Parse and set all employee attributes
        try {
            // Split by comma but respect quotes
            String[] values = lines.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

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

            // Parse salary and allowances
            // Remove additional "" quotation marks to all double data types
            String basicSalary = values[13].replace(",", "").replace("\"", "").trim();
            String riceSubsidy = values[14].replace(",", "").replace("\"", "").trim();
            String phoneAllowance = values[15].replace(",", "").replace("\"", "").trim();
            String clothingAllowance = values[16].replace(",", "").replace("\"", "").trim();
            String grossSemi = values[17].replace(",", "").replace("\"", "").trim();
            String hourlyRate = values[18].replace(",", "").replace("\"", "").trim();

            // Compensation
            employee.setCompensation(compensation);
            compensation.setBasicSalary(Double.parseDouble(basicSalary));
            compensation.setRiceSubsidy(Double.parseDouble(riceSubsidy));
            compensation.setPhoneAllowance(Double.parseDouble(phoneAllowance));
            compensation.setClothingAllowance(Double.parseDouble(clothingAllowance));
            compensation.setGrossSemiMonthlyRate(Double.parseDouble(grossSemi));
            compensation.setHourlyRate(Double.parseDouble(hourlyRate));
        }

        catch (Exception e) { // Handle parsing errors
            System.out.println("Error creating employee: " + e.getMessage());
        }
        return employee;
    }
}
