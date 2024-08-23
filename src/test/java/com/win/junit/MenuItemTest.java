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
        menuItem = new MenuItem("Pizza", "Delicious cheese pizza", 15, 12.99, List.of("Dough", "Cheese", "Tomato Sauce"));
    }

    @Test
    void testGetName() {
        assertEquals("Pizza", menuItem.getName());
    }

    @Test
    void testSetName() {
        menuItem.setName("Burger");
        assertEquals("Burger", menuItem.getName());
    }

    @Test
    void testGetDescription() {
        assertEquals("Delicious cheese pizza", menuItem.getDescription());
    }

    @Test
    void testSetDescription() {
        menuItem.setDescription("Tasty beef burger");
        assertEquals("Tasty beef burger", menuItem.getDescription());
    }

    @Test
    void testGetPreparationTime() {
        assertEquals(15, menuItem.getPreparationTime());
    }

    @Test
    void testSetPreparationTime() {
        menuItem.setPreparationTime(20);
        assertEquals(20, menuItem.getPreparationTime());
    }

    @Test
    void testGetPrice() {
        assertEquals(12.99, menuItem.getPrice());
    }

    @Test
    void testSetPrice() {
        menuItem.setPrice(8.99);
        assertEquals(8.99, menuItem.getPrice());
    }

    @Test
    void testGetIngredients() {
        assertEquals(List.of("Dough", "Cheese", "Tomato Sauce"), menuItem.getIngredients());
    }

    @Test
    void testSetIngredients() {
        List<String> newIngredients = List.of("Bun", "Beef", "Lettuce", "Tomato");
        menuItem.setIngredients(newIngredients);
        assertEquals(newIngredients, menuItem.getIngredients());
    }

    @Test
    void testToString() {
        String expected = "Pizza: Delicious cheese pizza | Price: $12.99 | Preparation Time: 15 min | Ingredients: Dough, Cheese, Tomato Sauce";
        assertEquals(expected, menuItem.toString());
    }

    @Test
    void testDefensiveCopyOfIngredients() {
        // Get the ingredients and modify the list
        List<String> ingredients = menuItem.getIngredients();
        ingredients.add("Extra Cheese");

        // The original ingredients list should not be affected
        assertEquals(List.of("Dough", "Cheese", "Tomato Sauce"), menuItem.getIngredients());
    }

    @Test
    void testSetIngredientsDefensiveCopy() {
        List<String> newIngredients = List.of("Bun", "Beef", "Lettuce", "Tomato");
        menuItem.setIngredients(newIngredients);

        // Modify the original list passed to setIngredients
        newIngredients.add("Pickles");

        // The ingredients in menuItem should not include "Pickles"
        assertEquals(List.of("Bun", "Beef", "Lettuce", "Tomato"), menuItem.getIngredients());
    }
}
