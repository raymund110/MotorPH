package com.motorph;

import java.util.Scanner;

public class Main {

    public static EmployeeDataFromFile employeeDataFromFile = new EmployeeDataFromFile();
    public static SalaryDeduction salaryDeduction = new SalaryDeduction();

    static Scanner scan = new Scanner(System.in);
    static Employee employeeData = new Employee();
    static boolean loop = true;

    public static void main(String[] args) {

        while (loop) {
            mainMenu();
            String choice = scan.nextLine();
            options(choice);
        }
    }

    public static void mainMenu() {
        System.out.println("-----MotorPH-----");
        System.out.println("1. List of Employee");
        System.out.println("2. Calculate Salary");
        System.out.println("3. Exit Program");
        System.out.print("Enter Choice: ");
    }

    public static void options(String option) {
        switch (option) {
            case "1":
                listOfEmployees();
                break;

            case "2":
                calculateSalary();
                break;

            case "3":
                System.out.println("Closing Program...");
                loop = false;
                break;

            default:
                System.out.println("Invalid Input Try again!");
                break;
        }
    }

    public static void listOfEmployees() {
        Employee[] employeeList = employeeDataFromFile.getEmployeeData();

        System.out.println("--------------------------------------");
        System.out.println("---------- List of Employees ---------");
        System.out.println("--------------------------------------");

        // used for loop statement to iterate thru the list of employees
        for (int a = 0; a < employeeList.length; a++) {
            Employee employee = employeeList[a];
            if (employee != null) {
                System.out.print("Employee Number: " + employee.getEmployeeNumber()
                        + "  Employee Name: " + employee.getLastName() + " "
                        + employee.getFirstName()
                        + "  Birthday: " + employee.getBirthday()
                        + "\n");
            }
        }
    }

    public static void calculateSalary() {
        Employee[] employeeList = employeeDataFromFile.getEmployeeData();

        System.out.println("--------------------------------------");
        System.out.println("--------- Total Weekly Salary --------");
        System.out.println("--------------------------------------");

        for (int i = 0; i < employeeList.length; i++) {
            Employee employee = employeeList[i];
            if (employee != null) {
                // Calculate monthly gross (basic + allowances)
                double monthlyGross = employee.getBasicSalary() +
                        employee.getRiceSubsidy() +
                        employee.getPhoneAllowance() +
                        employee.getClothingAllowance();

                // Convert to weekly (divide by 4)
                double weeklyGross = monthlyGross / 4;

                // Calculate weekly net (after deductions)
                double weeklyNet = weeklyGross - salaryDeduction.totalSalaryDeductions(employee.getBasicSalary()) / 4;

                System.out.print("Last Name: " + employee.getLastName()
                        + ", First Name: " + employee.getFirstName()
                        + "\nMonthly Basic Salary: PHP " + employee.getBasicSalary()
                        + "\nAllowances:"
                        + "\n  - Rice Subsidy: PHP " + employee.getRiceSubsidy()
                        + "\n  - Phone Allowance: PHP " + employee.getPhoneAllowance()
                        + "\n  - Clothing Allowance: PHP " + employee.getClothingAllowance()
                        + "\nMonthly Gross Salary: PHP " + monthlyGross
                        + "\nWeekly Gross Salary: PHP " + weeklyGross
                        + "\nWeekly Net Salary: PHP " + weeklyNet
                        + "\n----------------------------------------\n");
            }
        }
    }

}