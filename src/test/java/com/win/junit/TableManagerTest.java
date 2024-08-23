package com.win.junit;

import org.example.managers.TableManager;
import org.example.models.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TableManagerTest {

    private TableManager tableManager;

    @Before
    public void setUp() {
        tableManager = new TableManager(); // Initialize tableManager before each test
    }

    @Test
    public void testAddTable() {
        Table table1 = new Table(1, 4);
        tableManager.addTable(table1);

        List<Table> tables = tableManager.getTables();
        assertNotNull(tables);
        assertEquals(1, tables.size());
        assertEquals(table1, tables.get(0));
    }

    @Test
    public void testListTables() {
        Table table1 = new Table(1, 4);
        tableManager.addTable(table1);

        List<Table> tables = tableManager.getTables();
        assertNotNull(tables);
        assertEquals(1, tables.size());
        assertEquals(table1, tables.get(0));
    }

    @Test
    public void testFindTableById() {
        Table table1 = new Table(1, 4);
        tableManager.addTable(table1);

        Table foundTable = tableManager.findTableById(1);
        assertNotNull(foundTable);
        assertEquals(table1, foundTable);
    }

    @Test
    public void testAssignCustomerToTable() {
        Table table1 = new Table(1, 4);
        tableManager.addTable(table1);

        tableManager.assignCustomerToTable(1);
        assertEquals("Occupied", table1.getStatus());
    }

    @Test
    public void testUpdateTableStatus() {
        Table table1 = new Table(1, 4);
        tableManager.addTable(table1);

        tableManager.updateTableStatus(1, "Reserved");
        assertEquals("Reserved", table1.getStatus());
    }
}
