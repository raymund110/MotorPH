package com.motorph;

public class Main {

    public static void main(String[] args) {
        new employeeFrame();
    }

      //Comment out calculateSalary
    /*
    public static void calculateSalary(int searchEmployeeNumber) {
        try {
            System.out.print("Enter Start Date (MM/dd/yyyy): ");
            String startDateStr = scan.nextLine();

            System.out.print("Enter End Date (MM/dd/yyyy): ");
            String endDateStr = scan.nextLine();

            // Parse dates
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate startDate = LocalDate.parse(startDateStr, dateFormatter);
            LocalDate endDate = LocalDate.parse(endDateStr, dateFormatter);

            Employee[] employeeList = employeeDataFromFile.getEmployeeData();
            boolean found = false;

            System.out.println("\n--------------------------------------");
            System.out.println("-------- Salary Computation ----------");
            System.out.println("--------------------------------------");
            System.out.println("Period: " + startDateStr + " to " + endDateStr);

            for (Employee employee : employeeList) {
                if (employee != null && employee.getEmployeeNumber() == searchEmployeeNumber) {
                    // Get total hours worked
                    List<Attendance> records = attendanceDataFromFile.getAttendanceRecords(searchEmployeeNumber,
                            startDate, endDate);
                    double totalHours = attendanceDataFromFile.calculateTotalHours(searchEmployeeNumber, startDate,
                            endDate);
                    double hourlyRate = employee.getCompensation().getHourlyRate();

                    // Calculate salary based on hours worked
                    double basicSalaryForPeriod = totalHours * hourlyRate;

                    // Calculate monthly allowances (divide by 4 for weekly)
                    double weeklyAllowances = (employee.getCompensation().getRiceSubsidy() +
                            employee.getCompensation().getPhoneAllowance() +
                            employee.getCompensation().getClothingAllowance()) / 4;

                    // Calculate gross and net
                    double grossSalary = basicSalaryForPeriod + weeklyAllowances;
                    double deductions = salaryDeduction
                            .totalSalaryDeductions(employee.getCompensation().getBasicSalary()) / 4;
                    double netSalary = grossSalary - deductions;

                    // Print employee details
                    System.out.println("\nEmployee Details:");
                    System.out.println("Employee Number: " + employee.getEmployeeNumber());
                    System.out.println("Name: " + employee.getPerson().getLastName() + ", " +
                            employee.getPerson().getFirstName());
                    System.out.println("Position: " + employee.getJob().getPosition());

                    // Print work summary
                    System.out.println("\nWork Summary:");
                    System.out.println("Total Days Worked: " + records.size());
                    System.out.printf("Total Hours Worked: %.2f\n", totalHours);
                    System.out.printf("Hourly Rate: PHP %.2f\n", hourlyRate);

                    // Print salary details
                    System.out.println("\nSalary Details:");
                    System.out.printf("Basic Salary for Period: PHP %.2f\n", basicSalaryForPeriod);
                    System.out.printf("Weekly Allowances: PHP %.2f\n", weeklyAllowances);
                    System.out.printf("Gross Salary: PHP %.2f\n", grossSalary);
                    System.out.printf("Deductions: PHP %.2f\n", deductions);
                    System.out.printf("Net Salary: PHP %.2f\n", netSalary);

                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Employee not found with employee number: " + searchEmployeeNumber);
            }
            System.out.println("--------------------------------------\n");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Please make sure to enter dates in the correct format (MM/dd/yyyy)");
        }
    } */

}
