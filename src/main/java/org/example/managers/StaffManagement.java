package org.example.managers;

import org.example.models.Staff;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StaffManagement {
    private final List<Staff> staffList = new ArrayList<>();

    public void addStaff(Staff staff) {
        staffList.add(staff);
        System.out.println("Staff added: ");
    }

    public void removeStaff(int staffId) {
        staffList.removeIf(staff -> staff.getStaffId() == staffId);
        System.out.println("Staff removed: ");
    }

    public void editStaff(int staffId, String newName, String newRole, int newHoursWorked){
        for (Staff staff : staffList) {
            if (staff.getStaffId() == staffId){
                staff.setStaffName(newName);
                staff.setStaffRole(newRole);
                staff.setHoursWorked(newHoursWorked);
                System.out.println("Staff edited: ");
                return;
            }
        }
        System.out.println("Staff not found");
    }
    public void displayStaff() {
        for (Staff staff : staffList) {
            System.out.println("ID: " + staff.getStaffId() + ", Name: " + staff.getStaffName() +
                    ", Role: " + staff.getStaffRole() + ", Hours Worked: " + staff.getHoursWorked());
        }        }
}