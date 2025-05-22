package com.motorph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeDataFromFile {
    private final Employee[] employees;

    public EmployeeDataFromFile() {
        String employeeData = "src/main/resources/MotorPH Employee Data.csv";
        employees = new Employee[50];
        dataFromFile(employeeData);
    }

    public Employee[] getEmployeeData() {
        return employees;
    }

    public void dataFromFile(String employeeData) {
        try (BufferedReader reader = new BufferedReader(new FileReader(employeeData))) {
            reader.readLine(); // Skip header
            String line;
            int counter = 0;

            while ((line = reader.readLine()) != null && counter < employees.length) {
                // Split by comma but respect quotes
                String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
                if (values.length >= 19) {
                    employees[counter] = createEmployee(values);
                    counter++;
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private Employee createEmployee(String[] values) {
        Employee employee = new Employee();
        Compensation compensation = new Compensation();
        ContactInfo contactInfo = new ContactInfo();
        Person person = new Person();
        GovernmentID govID = new GovernmentID();
        Job job = new Job();

        try {
            employee.setEmployeeNumber(Integer.parseInt(values[0].trim()));
            // Basic employee info
            employee.setPerson(person);
            person.setLastName(values[1].trim());
            person.setFirstName(values[2].trim());

            DateTimeFormatter birtdayFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            employee.setBirthday(LocalDate.parse(values[3].trim(), birtdayFormat));

            // Contacts
            employee.setContactInfo(contactInfo); // initialize
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
            String basicSalary = values[13].replace(",", "").replace("\"", "").trim();
            String riceSubsidy = values[14].replace(",", "").replace("\"", "").trim();
            String phoneAllowance = values[15].replace(",", "").replace("\"", "").trim();
            String clothingAllowance = values[16].replace(",", "").replace("\"", "").trim();
            String hourlyRate = values[18].replace(",", "").replace("\"", "").trim();

            // Compensation
            employee.setCompensation(compensation); // initialize
            compensation.setBasicSalary(Double.parseDouble(basicSalary));
            compensation.setRiceSubsidy(Double.parseDouble(riceSubsidy));
            compensation.setPhoneAllowance(Double.parseDouble(phoneAllowance));
            compensation.setClothingAllowance(Double.parseDouble(clothingAllowance));

            compensation.setHourlyRate(Double.parseDouble(hourlyRate));
        }

        catch (Exception e) {
            System.out.println("Error creating employee: " + e.getMessage());
        }
        return employee;
    }

}
