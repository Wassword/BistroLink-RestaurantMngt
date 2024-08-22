package org.example.models;

public class Staff {
    private int staffId;
    private String staffName;
    private String staffRole;
    private int hoursWorked;

    public Staff(int staffId, String staffName, String staffRole, int hoursWorked) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffRole = String.valueOf(staffRole);
        this.hoursWorked = hoursWorked;

    }

    //Getters and setters
    public int getStaffId() {
        return staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public String getStaffRole() {
        return staffRole;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public void setStaffRole(String staffRole) {
        this.staffRole = staffRole;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

}
