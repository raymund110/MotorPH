package com.motorph;

import java.time.LocalDate;

public class Employee {

    private int employeeNumber;
    private String status;
    private Person person;
    private LocalDate birthday;
    private Compensation compensation;
    private ContactInfo contactInfo;
    private GovernmentID govID;
    private Job job;
    private SalaryDeduction salaryDeduction;

    public Employee() {
        this.person = new Person();
        this.contactInfo = new ContactInfo();
        this.govID = new GovernmentID();
        this.job = new Job();
        this.compensation = new Compensation();
        this.salaryDeduction = new SalaryDeduction();
    }

    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Compensation getCompensation() {
        return compensation;
    }

    public void setCompensation(Compensation compensation) {
        this.compensation = compensation;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public GovernmentID getGovID() {
        return govID;
    }

    public void setGovID(GovernmentID govID) {
        this.govID = govID;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public SalaryDeduction getSalaryDeduction () {
        return salaryDeduction;
    }

    public void setSalaryDeduction (SalaryDeduction salaryDeduction) {
        this.salaryDeduction = salaryDeduction;
    }

}
