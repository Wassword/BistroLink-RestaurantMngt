package org.example.managers;

/* Manages all reservations, including creating, listing, and managing conflicts
 * (e.g., ensuring no double-booking of tables at the same time).
 */

import org.example.models.Reservation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {
    private List<Reservation> reservations;

    public ReservationManager() {
        this.reservations = new ArrayList<>();
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

