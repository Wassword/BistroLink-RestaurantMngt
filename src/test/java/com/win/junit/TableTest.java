package com.win.junit;

import org.example.models.Table;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {

    @Test
    void testTableCreation() {
        // Test creation of a new table
        Table table = new Table(1, 4);
        assertEquals(1, table.getTableId());
        assertEquals(4, table.getSize());
        assertEquals("Available", table.getStatus());
    }

    @Test
    void testSetStatus() {
        // Test setting the status of the table
        Table table = new Table(2, 2);
        assertEquals("Available", table.getStatus());

        table.setStatus("Occupied");
        assertEquals("Occupied", table.getStatus());

        table.setStatus("Reserved");
        assertEquals("Reserved", table.getStatus());
    }

    @Test
    void testToString() {
        // Test the toString method
        Table table = new Table(3, 6);
        String expectedString = "Table{tableId=3, size=6, status='Available'}";
        assertEquals(expectedString, table.toString());
    }
}
