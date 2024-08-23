package com.win.junit;

import org.example.managers.MenuManagement;
import org.example.models.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuManagementTest {

    private MenuManagement menuManagement;

    @BeforeEach
    void setUp() {
        menuManagement = new MenuManagement();
    }

    @Test
    void testInitializeMenu() {
        List<MenuItem> menuItems = menuManagement.getMenuItems();
        assertNotNull(menuItems);
        assertFalse(menuItems.isEmpty());
        assertEquals(7, menuItems.size(), "The menu should have 7 items after initialization.");
    }

    @Test
    void testGetMenuItem() {
        MenuItem menuItem = menuManagement.getMenuItem("Margherita Pizza");
        assertNotNull(menuItem);
        assertEquals("Margherita Pizza", menuItem.getName());
        assertEquals(10.99, menuItem.getPrice(), 0.01);
    }

    @Test
    void testAddMenuItem() {
        MenuItem newItem = new MenuItem("Pasta Primavera", "Pasta with fresh vegetables", 25, 13.99, List.of("Pasta", "Vegetables", "Olive Oil"));
        menuManagement.addMenuItem(newItem);
        assertEquals(8, menuManagement.getMenuItems().size());
        assertNotNull(menuManagement.getMenuItem("Pasta Primavera"));
    }

    @Test
    void testRemoveMenuItem() {
        menuManagement.removeMenuItem("Margherita Pizza");
        assertNull(menuManagement.getMenuItem("Margherita Pizza"));
        assertEquals(6, menuManagement.getMenuItems().size());
    }

    @Test
    void testEditMenuItem() {
        MenuItem updatedItem = new MenuItem("Lasagna", "Layered pasta with spinach and ricotta", 35, 15.99, List.of("Pasta", "Spinach", "Ricotta", "Tomato Sauce"));
        menuManagement.editMenuItem("Lasagna", updatedItem);

        MenuItem menuItem = menuManagement.getMenuItem("Lasagna");
        assertNotNull(menuItem);
        assertEquals("Layered pasta with spinach and ricotta", menuItem.getDescription());
        assertEquals(35, menuItem.getPreparationTime());
        assertEquals(15.99, menuItem.getPrice(), 0.01);
    }

    @Test
    void testSaveAndLoadMenu() {
        MenuItem newItem = new MenuItem("Pasta Primavera", "Pasta with fresh vegetables", 25, 13.99, List.of("Pasta", "Vegetables", "Olive Oil"));
        menuManagement.addMenuItem(newItem);
        try {
            menuManagement.saveMenu();

            // Create a new MenuManagement instance and load the saved menu
            MenuManagement newMenuManagement = new MenuManagement();
            newMenuManagement.loadMenu();
            assertEquals(8, newMenuManagement.getMenuItems().size());
            assertNotNull(newMenuManagement.getMenuItem("Pasta Primavera"));
        } catch (IOException | ClassNotFoundException e) {
            fail("Exception should not occur during save/load: " + e.getMessage());
        }
    }

    @Test
    void testListMenuItems() {
        // Redirect output to a byte stream to capture the printed menu
        var outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        menuManagement.listMenuItems();

        // Convert captured output to a string
        String output = outContent.toString();

        // Reset the system output to the default console output
        System.setOut(System.out);

        // Check that the output contains specific menu items
        assertTrue(output.contains("Margherita Pizza"));
        assertTrue(output.contains("Lasagna"));
        assertTrue(output.contains("Spaghetti Carbonara"));
    }
}
