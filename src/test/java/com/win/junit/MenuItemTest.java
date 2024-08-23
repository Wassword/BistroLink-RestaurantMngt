package com.win.junit;

import org.example.models.MenuItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MenuItemTest {

    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        menuItem = new MenuItem(
                "Pizza",
                "Delicious cheese pizza",
                15,
                12.99,
                List.of("Dough", "Cheese", "Tomato Sauce")
        );
    }

    @Test
    void testGetName() {
        assertEquals("Pizza", menuItem.getName());
    }

    @Test
    void testGetDescription() {
        assertEquals("Delicious cheese pizza", menuItem.getDescription());
    }

    @Test
    void testGetPreparationTime() {
        assertEquals(15, menuItem.getPreparationTime());
    }

    @Test
    void testGetPrice() {
        assertEquals(12.99, menuItem.getPrice());
    }

    @Test
    void testGetIngredients() {
        List<String> ingredients = menuItem.getIngredients();
        assertEquals(3, ingredients.size());
        assertTrue(ingredients.contains("Dough"));
        assertTrue(ingredients.contains("Cheese"));
        assertTrue(ingredients.contains("Tomato Sauce"));

        // Test that adding an ingredient to the returned list doesn't affect the original list
        ingredients.add("Olives");
        assertFalse(menuItem.getIngredients().contains("Olives"));
    }
}