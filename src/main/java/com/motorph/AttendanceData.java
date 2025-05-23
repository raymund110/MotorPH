package com.motorph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// This class was made to refactor the AttendanceDataFromFile
public class AttendanceData {
    private final ArrayList<Attendance> attendance = new ArrayList<>();

    public AttendanceData() {
        String attendanceData = "src/main/resources/Attendance Record.csv";
        dataFromFile(attendanceData);
    }

    public ArrayList<Attendance> getAttendanceData() { return attendance; }

    public void dataFromFile(String attendanceData) {
        try (BufferedReader reader = new BufferedReader(new FileReader(attendanceData))){
            reader.readLine(); //
            String line;

            while ((line = reader.readLine()) != null) {
                    attendance.add(createAttendance(line));
            }

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private Attendance createAttendance (String lines) {
        Attendance attendance = new Attendance();
        Person person = new Person();

        try {
            String[] values = lines.split(",");
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

        } catch (Exception e) {
            System.out.println("Error parsing date: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return attendance;
    }

    // Not Done
    public double calculateDailyWorkHours () {
        double dailyHours = 0.0;
        return dailyHours;
    }

    // Not Done
    public double calculateWorkHours () {
        double workHours = 0.0;
        return workHours;
    }

}
