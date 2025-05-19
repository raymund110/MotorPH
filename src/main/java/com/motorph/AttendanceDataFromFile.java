package com.motorph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AttendanceDataFromFile {
    private final List<Attendance> attendanceList;
    private static final String ATTENDANCE_FILE = "src/main/resources/Attendance Record.csv";

    public AttendanceDataFromFile() {
        attendanceList = new ArrayList<>();
        loadAttendanceData();
    }

    private void loadAttendanceData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ATTENDANCE_FILE))) {
            reader.readLine(); // Skip header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 6) {
                    try {
                        Attendance attendance = new Attendance();

                        // Set employee number
                        attendance.getEmployee().setEmployeeNumber(Integer.parseInt(values[0].trim()));

                        // Set date
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        attendance.setAttendanceDate(LocalDate.parse(values[3].trim(), dateFormatter));

                        // Set time in
                        String[] timeInParts = values[4].trim().split(":");
                        if (timeInParts.length == 2) {
                            attendance.setTimeIn(LocalTime.of(
                                    Integer.parseInt(timeInParts[0]),
                                    Integer.parseInt(timeInParts[1])));
                        }

                        // Set time out
                        String[] timeOutParts = values[5].trim().split(":");
                        if (timeOutParts.length == 2) {
                            attendance.setTimeOut(LocalTime.of(
                                    Integer.parseInt(timeOutParts[0]),
                                    Integer.parseInt(timeOutParts[1])));
                        }

                        // Add to list if time in and time out are valid
                        if (attendance.getTimeIn() != null && attendance.getTimeout() != null) {
                            attendanceList.add(attendance);
                        }
                    } catch (Exception e) {
                        System.out.println("Error parsing line: " + line);
                        System.out.println("Error details: " + e.getMessage());
                    }
                }
            }
            System.out.println("Successfully loaded " + attendanceList.size() + " attendance records.");
        } catch (Exception e) {
            System.out.println("Error reading attendance file: " + e.getMessage());
        }
    }

    // Get attendance records for a specific employee and date range
    public List<Attendance> getAttendanceRecords(int employeeNumber, LocalDate startDate, LocalDate endDate) {
        List<Attendance> filteredRecords = new ArrayList<>();

        for (Attendance record : attendanceList) {
            if (record.getEmployee().getEmployeeNumber() == employeeNumber &&
                    !record.getAttendanceDate().isBefore(startDate) &&
                    !record.getAttendanceDate().isAfter(endDate)) {
                filteredRecords.add(record);
            }
        }

        return filteredRecords;
    }

    // Calculate hours worked between time in and time out
    public double calculateDailyHours(Attendance record) {
        if (record.getTimeIn() != null && record.getTimeout() != null) {
            // Calculate minutes worked
            int minutesWorked = (record.getTimeout().getHour() * 60 + record.getTimeout().getMinute()) -
                    (record.getTimeIn().getHour() * 60 + record.getTimeIn().getMinute());

            // Convert to hours
            double hoursWorked = minutesWorked / 60.0;

            // Cap at 8 hours
            return Math.min(hoursWorked, 8.0);
        }
        return 0.0;
    }

    // Calculate total hours worked for date range
    public double calculateTotalHours(int employeeNumber, LocalDate startDate, LocalDate endDate) {
        double totalHours = 0.0;
        List<Attendance> records = getAttendanceRecords(employeeNumber, startDate, endDate);

        for (Attendance record : records) {
            totalHours += calculateDailyHours(record);
        }

        return totalHours;
    }
}
