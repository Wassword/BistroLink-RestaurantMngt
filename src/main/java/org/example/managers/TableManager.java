package org.example.managers;

import org.example.models.Table;

import java.util.ArrayList;
import java.util.List;

public class TableManager {
    private List<Table> tables;

    public TableManager() {
        this.tables = new ArrayList<>();
    }

    // Method to add a new table
    public void addTable(Table table) {
        tables.add(table);
        System.out.println("Table added: " + table);
    }

    // Method to list all tables
    public void listTables() {
        System.out.println("Current Tables:");
        for (Table table : tables) {
            System.out.println(table);
        }
    }

    // Method to find a table by ID
    public Table findTableById(int tableId) {
        for (Table table : tables) {
            if (table.getTableId() == tableId) {
                return table;
            }
        }
        return null;
    }

    // Method to assign a customer to a table
    public void assignCustomerToTable(int tableId) {
        Table table = findTableById(tableId);
        if (table != null && table.getStatus().equalsIgnoreCase("Available")) {
            table.setStatus("Occupied");
            System.out.println("Table " + tableId + " is now occupied.");
        } else if (table != null) {
            System.out.println("Table " + tableId + " is not available.");
        } else {
            System.out.println("Table " + tableId + " not found.");
        }
    }

    // Method to update table status
    public void updateTableStatus(int tableId, String status) {
        Table table = findTableById(tableId);
        if (table != null) {
            table.setStatus(status);
            System.out.println("Table " + tableId + " status updated to " + status);
        } else {
            System.out.println("Table " + tableId + " not found.");
        }
    }

    // Method to get the list of tables (added to support testing)
    public List<Table> getTables() {
        return tables;
    }
}
