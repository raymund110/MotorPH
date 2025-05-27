import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ViewEmployee {

    // Reads employee data from a CSV file and displays it in a formatted table.
    public void view() {
        // Path to the CSV file
        String csvFile = "src\\EmployeeData.csv";
        String line;
        String csvSplitBy = ",";

        // Read file
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            br.readLine();

            // Print the header for the output
            System.out.printf("%-20s%-40s%-15s%n", "Employee Number", "Employee Name", "Birthday");
            System.out.println("------------------------------------------------------------------------");

            // Read each line of the CSV file
            while ((line = br.readLine()) != null) {
                String[] employee = line.split(csvSplitBy);

                // Extract employee details
                String employeeNum = employee[0];
                String lastName = employee[1];
                String firstName = employee[2];
                String birthday = employee[3];

                // Output format
                String formatOutput = String.format("%-20s%-40s%-15s", employeeNum, firstName + "  " + lastName,
                        birthday);

                // Print formatted employee information
                System.out.println(formatOutput);
            }
        }
        // Handle file reading errors
        catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }
}
