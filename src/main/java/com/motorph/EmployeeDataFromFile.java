package com.motorph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EmployeeDataFromFile {
    private Employee[] employees;

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
        try {
            employee.setEmployeeNumber(Integer.parseInt(values[0].trim()));
            employee.setLastName(values[1].trim());
            employee.setFirstName(values[2].trim());

            DateTimeFormatter birtdayFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            employee.setBirthday(LocalDate.parse(values[3].trim(), birtdayFormat));

            // Contacts
            contactInfo.setAddress(values[4].trim());
            contactInfo.setPhoneNumber(values[5].trim());

            // Parse salary and allowances
            String basicSalary = values[13].replace(",", "").replace("\"", "").trim();
            String riceSubsidy = values[14].replace(",", "").replace("\"", "").trim();
            String phoneAllowance = values[15].replace(",", "").replace("\"", "").trim();
            String clothingAllowance = values[16].replace(",", "").replace("\"", "").trim();
            String hourlyRate = values[18].replace(",", "").replace("\"", "").trim();

            compensation.setBasicSalary(Double.parseDouble(basicSalary));
            compensation.setRiceSubsidy(Double.parseDouble(riceSubsidy));
            compensation.setPhoneAllowance(Double.parseDouble(phoneAllowance));
            compensation.setClothingAllowance(Double.parseDouble(clothingAllowance));
            compensation.setHourlyRate(Double.parseDouble(hourlyRate));
        } catch (Exception e) {
            System.out.println("Error creating employee: " + e.getMessage());
        }
        return employee;
    }

}
