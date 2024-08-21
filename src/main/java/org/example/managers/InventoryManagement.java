package org.example.managers;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class InventoryManagement {
    private Map<String, Integer> ingredients;
    private int lowStockThreshold;

    public InventoryManagement(int lowStockThreshold) {
        this.ingredients = new HashMap<>();
        this.lowStockThreshold = lowStockThreshold; // Customizable low stock threshold
    }

    // Add new ingredient with initial quantity
    public void addIngredient(String name, int quantity) {
        ingredients.put(name, quantity);
    }

    // Use ingredients and reduce the quantity
    public void useIngredient(String name, int quantity) {
        if (ingredients.containsKey(name)) {
            int currentQuantity = ingredients.get(name);
            if (currentQuantity >= quantity) {
                ingredients.put(name, currentQuantity - quantity);
                if (currentQuantity - quantity <= lowStockThreshold) {
                    System.out.println("Warning: Low stock of " + name);
                }
            } else {
                System.out.println("Insufficient stock of " + name);
            }
        } else {
            System.out.println("Ingredient " + name + " does not exist.");
        }
    }

    // Check stock for a specific ingredient
    public int checkStock(String name) {
        return ingredients.getOrDefault(name, 0);
    }

    // Load inventory data from file
    public void loadInventoryFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String name = parts[0].trim();
                    int quantity = Integer.parseInt(parts[1].trim());
                    ingredients.put(name, quantity);
                }
            }
            System.out.println("Inventory loaded from " + fileName);
        } catch (IOException e) {
            System.out.println("Error loading inventory from file: " + e.getMessage());
        }
    }

    // Save inventory data to file
    public void saveInventoryToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
                writer.write(entry.getKey() + ": " + entry.getValue());
                writer.newLine();
            }
            System.out.println("Inventory saved to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving inventory to file: " + e.getMessage());
        }
    }
}
