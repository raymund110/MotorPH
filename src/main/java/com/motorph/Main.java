package com.motorph;

import java.util.Scanner;

public class Main {

    public static EmployeeDataFromFile employeeDataFromFile = new EmployeeDataFromFile();
    public static SalaryDeduction salaryDeduction = new SalaryDeduction();
    public static AttendanceData attendanceData = new AttendanceData();

    static Scanner scan = new Scanner(System.in);
    static boolean loop = true;

    public static void main(String[] args) {
//        new frameLogin();
        new employeeFrame("10034");
        listAttendance(10034);
    }

    public static void listAttendance(int searchEmployee) {
        Attendance[] attendancesList = attendanceData.getAttendanceData();
        boolean found = false;

        for (Attendance att : attendancesList) {
            if(att != null && att.getEmployee().getEmployeeNumber() == searchEmployee){
                System.out.print("Employee Number: " + att.getEmployee().getEmployeeNumber()
                + "\nEmployee Name: " + att.getEmployee().getPerson().getFirstName() + " "
                + att.getEmployee().getPerson().getLastName()
                + "\nDate: " + att.getAttendanceDate()
                + "\nTime In: " + att.getTimeIn()
                + "\nTime Out: " + att.getTimeout());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Employee Not Found: " + searchEmployee);
        }

    }

// Comment out calculateSalary
//    public static void calculateSalary(int searchEmployeeNumber) {
//        try {
//            System.out.print("Enter Start Date (MM/dd/yyyy): ");
//            String startDateStr = scan.nextLine();
//
//            System.out.print("Enter End Date (MM/dd/yyyy): ");
//            String endDateStr = scan.nextLine();
//
//            // Parse dates
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//            LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
//            LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);
//
//            Employee[] employeeList = employeeDataFromFile.getEmployeeData();
//            boolean found = false;
//
//            System.out.println("\n--------------------------------------");
//            System.out.println("-------- Salary Computation ----------");
//            System.out.println("--------------------------------------");
//            System.out.println("Period: " + startDateStr + " to " + endDateStr);
//
//            for (Employee employee : employeeList) {
//                if (employee != null && employee.getEmployeeNumber() == searchEmployeeNumber) {
//                    // Get total hours worked
//                    List<Attendance> records = attendanceDataFromFile.getAttendanceRecords(searchEmployeeNumber,
//                            startDate, endDate);
//                    double totalHours = attendanceDataFromFile.calculateTotalHours(searchEmployeeNumber, startDate,
//                            endDate);
//                    double hourlyRate = employee.getCompensation().getHourlyRate();
//
//                    // Calculate salary based on hours worked
//                    double basicSalaryForPeriod = totalHours * hourlyRate;
//
//                    // Calculate monthly allowances (divide by 4 for weekly)
//                    double weeklyAllowances = (employee.getCompensation().getRiceSubsidy() +
//                            employee.getCompensation().getPhoneAllowance() +
//                            employee.getCompensation().getClothingAllowance()) / 4;
//
//                    // Calculate gross and net
//                    double grossSalary = basicSalaryForPeriod + weeklyAllowances;
//                    double deductions = salaryDeduction
//                            .totalSalaryDeductions(employee.getCompensation().getBasicSalary()) / 4;
//                    double netSalary = grossSalary - deductions;
//
//                    // Print employee details
//                    System.out.println("\nEmployee Details:");
//                    System.out.println("Employee Number: " + employee.getEmployeeNumber());
//                    System.out.println("Name: " + employee.getPerson().getLastName() + ", " +
//                            employee.getPerson().getFirstName());
//                    System.out.println("Position: " + employee.getJob().getPosition());
//
//                    // Print work summary
//                    System.out.println("\nWork Summary:");
//                    System.out.println("Total Days Worked: " + records.size());
//                    System.out.printf("Total Hours Worked: %.2f\n", totalHours);
//                    System.out.printf("Hourly Rate: PHP %.2f\n", hourlyRate);
//
//                    // Print salary details
//                    System.out.println("\nSalary Details:");
//                    System.out.printf("Basic Salary for Period: PHP %.2f\n", basicSalaryForPeriod);
//                    System.out.printf("Weekly Allowances: PHP %.2f\n", weeklyAllowances);
//                    System.out.printf("Gross Salary: PHP %.2f\n", grossSalary);
//                    System.out.printf("Deductions: PHP %.2f\n", deductions);
//                    System.out.printf("Net Salary: PHP %.2f\n", netSalary);
//
//                    found = true;
//                    break;
//                }
//            }
//
//            if (!found) {
//                System.out.println("Employee not found with employee number: " + searchEmployeeNumber);
//            }
//            System.out.println("--------------------------------------\n");
//
//        } catch (Exception e) {
//            System.out.println("Error: " + e.getMessage());
//            System.out.println("Please make sure to enter dates in the correct format (MM/dd/yyyy)");
//        }
//    }

}