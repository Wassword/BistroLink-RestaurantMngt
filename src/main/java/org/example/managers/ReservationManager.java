package org.example.managers;

import org.example.models.Reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationManager {
    private List<Reservation> reservations;

    public ReservationManager() {
        this.reservations = new ArrayList<>();
    }

    // Method to create and add a new reservation
    public void createAndAddReservation(Scanner scanner) {
        System.out.print("Enter Customer Name: ");
        String customerName = scanner.nextLine();

        LocalDate date = null;
        LocalTime time = null;

        // Prompt for date
        while (date == null) {
            try {
                System.out.print("Enter Reservation Date (yyyy-MM-dd): "); // Prompt for date input
                String dateInput = scanner.nextLine();
                date = LocalDate.parse(dateInput, DateTimeFormatter.ISO_LOCAL_DATE);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format yyyy-MM-dd.");
            }
        }

        // Prompt for time
        while (time == null) {
            try {
                System.out.print("Enter Reservation Time (HH:mm): "); // Prompt for time input
                String timeInput = scanner.nextLine();
                time = LocalTime.parse(timeInput, DateTimeFormatter.ISO_LOCAL_TIME);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please enter the time in the format HH:mm.");
            }
        }

        // Combine date and time into LocalDateTime
        LocalDateTime reservationDateTime = LocalDateTime.of(date, time);

        System.out.print("Enter Table ID: ");
        int tableId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Create the Reservation object with the correct argument order
        Reservation reservation = new Reservation(customerName, reservationDateTime, tableId);

        // Add the reservation using the existing method
        addReservation(reservation);
    }

    // Method to add a new reservation
    public void addReservation(Reservation reservation) {
        if (isTableAvailable(reservation.getTableId(), reservation.getReservationTime())) {
            reservations.add(reservation);
            System.out.println("Reservation added: " + reservation);
        } else {
            System.out.println("Table " + reservation.getTableId() + " is not available at " +
                    reservation.getReservationTime());
        }
    }

    // Method to check if a table is available
    private boolean isTableAvailable(int tableId, LocalDateTime time) {
        for (Reservation r : reservations) {
            if (r.getTableId() == tableId && r.getReservationTime().equals(time)) {
                return false;
            }
        }
        return true;
    }

    // Method to list all reservations
    public void listReservations() {
        System.out.println("Current Reservations:");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    // Method to remove a reservation
    public void removeReservation(String customerName, int tableId, LocalDateTime time) {
        reservations.removeIf(r -> r.getCustomerName().equals(customerName) &&
                r.getTableId() == tableId &&
                r.getReservationTime().equals(time));
        System.out.println("Reservation removed for " + customerName + " at table " + tableId);
    }
}
