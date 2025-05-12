package com.motorph;

import java.io.BufferedReader;
import java.io.FileReader;

public class EmployeeDataFromFile {
    private Employee[] employees;

    public EmployeeDataFromFile() {
        String employeeData = "motorph/src/main/resources/MotorPH Employee Data.csv";
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
                String[] values = line.split(",");
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
        try {
            employee.setEmployeeNumber(values[0].trim());
            employee.setLastName(values[1].trim());
            employee.setFirstName(values[2].trim());
            employee.setBirthday(values[3].trim());
            employee.setBasicSalary(Double.parseDouble(values[13]));
            employee.setHourlyRate(Double.parseDouble(values[18]));
        } catch (Exception e) {
            System.out.println("Error creating employee: " + e.getMessage());
        }
        return employee;
    }

}
