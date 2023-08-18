package com.example.emp_system;

public class EmployeeData {
    private String employeeID, name, email, position, department, NIC, mobileNumber;
    private int payRate;
    private boolean status;

    public EmployeeData(String employeeID, String name, String email, String position, String department, int payRate, String NIC, String mobileNumber, boolean status) {
        this.employeeID = employeeID;
        this.name = name;
        this.email = email;
        this.position = position;
        this.department = department;
        this.payRate = payRate;
        this.NIC = NIC;
        this.mobileNumber = mobileNumber;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public EmployeeData(boolean status){
        this.status = status;
    }


    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getPayRate() {
        return payRate;
    }

    public void setPayRate(int payRate) {
        this.payRate = payRate;
    }

    public String getNIC() {
        return NIC;
    }

    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
