package com.motorph;

import java.util.Scanner;

public class Main {

    public static EmployeeDataFromFile employeeDataFromFile;

    static Scanner scan = new Scanner(System.in);
    static Employee employeeData = new Employee();
    static boolean loop = true;

    public static void main(String[] args) {
        employeeDataFromFile = new EmployeeDataFromFile();

        while (loop) {
            mainMenu();
            String choice = scan.nextLine();
            options(choice);
        }
    }

    public static void mainMenu() {
        System.out.println("-----MotorPH-----");
        System.out.println("1. View Employee Data");
        System.out.println("2. Exit Program");
        System.out.print("Enter Choice: ");
    }

    public static void options(String option) {
        switch (option) {
            case "1":
                listOfEmployees();
                break;

            case "2":
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

        // used for loop statement to iterate thru the list of employees
        for (int a = 0; a < employeeList.length; a++) {
            Employee employee = employeeList[a];
            if (employee != null) { // Add this check
                System.out.print("Employee Number: " + employee.getEmployeeNumber()
                        + "  Employee Name: " + employee.getLastName() + " "
                        + employee.getFirstName()
                        + "  Birthday: " + employee.getBirthday() + "\n");
            }
        }

    }

}