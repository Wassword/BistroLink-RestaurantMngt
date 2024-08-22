package org.example.models;

import java.time.LocalDateTime;

public class Reservation {
    private String customerName; // customerName: Stores the name of the customer who made the reservation.
    private LocalDateTime reservationTime; // reservationTime: A LocalDateTime object that stores the date and time of the reservation.
    private int tableId; // tableId: Identifies which table is reserved.

    // Constructor
    // NOTE: This constructor is correctly ordered and does not need changes.
    public Reservation(String customerName, LocalDateTime reservationTime, int tableId) {
        this.customerName = customerName;
        this.reservationTime = reservationTime;
        this.tableId = tableId;
    }

    // Getters and Setters
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "customerName='" + customerName + '\'' +
                ", reservationTime=" + reservationTime +
                ", tableId=" + tableId +
                '}';
    }
}
