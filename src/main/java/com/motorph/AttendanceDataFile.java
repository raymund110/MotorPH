package com.motorph;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AttendanceDataFile {
    private final ArrayList<Attendance> attendance = new ArrayList<>();

    public AttendanceDataFile() {
        String attendanceData = "src/main/resources/Attendance Record.csv";
        dataFromFile(attendanceData);
    }

    // Getter method that returns attendance ArrayList that contains all attendance objects
    public ArrayList<Attendance> getAttendanceData() { return attendance; }

    // Method that reads the CSV file of attendance data
    public void dataFromFile(String attendanceData) {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',').build();

        try (CSVReader reader = new CSVReaderBuilder(new FileReader(attendanceData))
                .withCSVParser(parser).build()){
            reader.readNext(); // Skip header
            String[] line;

            // Read the csv file line by line
            while ((line = reader.readNext()) != null) {
                    attendance.add(createAttendance(line)); // Add attendance objects to attendance ArrayList
            }

        } catch (Exception e) { // Handle reading file errors
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Converts single lines of CSV data into an attendance Objects
    private Attendance createAttendance (String[] values) {
        // Instantiate employee-related class
        Attendance attendance = new Attendance();
        Person person = new Person();

        // Parse and set all attendance attributes
        try {
            attendance.getEmployee().setEmployeeNumber(Integer.parseInt(values[0].trim()));

            attendance.getEmployee().setPerson(person);
            person.setLastName(values[1].trim());
            person.setFirstName(values[2].trim());

            // Format date to match the csv file format
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            // set date parsed and formatted
            attendance.setAttendanceDate(LocalDate.parse(values[3].trim(), dateFormatter));

            // Format time to match the csv file format 24 hours can handle single-digit and double-digit
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
            // set TimeIn and Out parsed and formatted
            attendance.setTimeIn(LocalTime.parse(values[4].trim(), timeFormatter));
            attendance.setTimeOut(LocalTime.parse(values[5].trim(), timeFormatter));

        } catch (Exception e) { // Handles parsing errors
            System.out.println("Error parsing date: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return attendance;
    }

}
