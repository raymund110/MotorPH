import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class GrossCalculation extends Calculation {

    static Scanner scanner = new Scanner(System.in);

    static double totalHours = 0;
    static double hourlyRate = 0;
    static double grossIncome;

    @Override
    public double calculateGross(double calc) {
        totalHours = calc;
        return grossIncome = totalHours * hourlyRate;
    }

    /**
     * Method to calculate the gross salary of an employee based on attendance data.
     * The method reads attendance data from a CSV file, filters records based on
     * employee number and date range, and calculates the total hours worked and
     * gross income.
     */
    public void grossCal() {
        // Path to the CSV file containing attendance data
        String file = "src\\AttendanceData.csv";
        String line;
        boolean employeeFound = false;

        // Prompt the user to enter the employee number and date range
        System.out.print("Enter employee number: ");
        String targetEmployee = scanner.nextLine();

        System.out.print("Enter start date (MM/dd/yyyy): ");
        String startDateInput = scanner.nextLine();
        System.out.print("Enter end date (MM/dd/yyyy): ");
        String endDateInput = scanner.nextLine();

        // Parse the input dates using the specified format
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate startDate = LocalDate.parse(startDateInput, dateFormatter);
        LocalDate endDate = LocalDate.parse(endDateInput, dateFormatter);

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                String employeeNumber = data[0];
                LocalDate date = LocalDate.parse(data[2], dateFormatter);

                // checks if employee number and dates match
                if (employeeNumber.equals(targetEmployee) &&
                        (date.isEqual(startDate) || date.isAfter(startDate)) &&
                        (date.isEqual(endDate) || date.isBefore(endDate))) {

                    employeeFound = true;

                    LocalTime timeIn = LocalTime.parse(data[3]);
                    LocalTime timeOut = LocalTime.parse(data[4]);
                    hourlyRate = Double.parseDouble(data[5]);

                    // Calculate hours worked
                    double hoursWorked = Duration.between(timeIn, timeOut).toMinutes() / 60.0;
                    totalHours += hoursWorked;
                }
            }

            // If the employee is found, calculate and display the gross income
            if (employeeFound) {
                // Calculate gross income using method from Calculation class
                calculateGross(totalHours);

                // Header for employee gross salary section
                System.out.println("***********************************************");
                System.out.println("**           Employee Gross Salary           **");
                System.out.println("***********************************************");

                System.out.printf("%nTotal hours worked: %.2f hours%n", totalHours);
                System.out.printf("Gross income: PHP %.2f%n", grossIncome);
            }

            else { // Display message if no records found
                System.out.println("\nNo records found for that employee within the given date range.");
            }

        }
        // Catch block to handle exceptions
        catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}