package org.example.models;

/* The Table class will manage restaurant tables.
 * This class will include properties such as table ID, size, and status.
 */

public class Table {
    private int tableId; // 'tableId': Unique identifier for each table.
    private int size; // Number of seats
    private String status; // "Available", "Reserved", "Occupied"

    // Constructor
    public Table(int tableId, int size) {
        this.tableId = tableId;
        this.size = size;
        this.status = "Available"; // Initialize status as "Available"
    }

    // Getters and Setters
    public int getTableId() {
        return tableId;
    }

    public int getSize() {
        return size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableId=" + tableId +
                ", size=" + size +
                ", status='" + status + '\'' +
                '}';
    }
}

