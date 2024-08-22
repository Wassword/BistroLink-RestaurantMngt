package org.example.models;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class MenuItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private int preparationTime;
    private double price;
    private List<String> ingredients;

    // Constructor
    public MenuItem(String name, String description, int preparationTime, double price, List<String> ingredients) {
        this.name = name;
        this.description = description;
        this.preparationTime = preparationTime;
        this.price = price;
        // Defensive copy of the ingredients list
        this.ingredients = new ArrayList<>(ingredients);
    }

    // Getters and Setters for menu item properties
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getIngredients() {
        // Return a defensive copy to prevent external modification
        return new ArrayList<>(ingredients);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setIngredients(List<String> ingredients) {
        // Defensive copy of the ingredients list
        this.ingredients = new ArrayList<>(ingredients);
    }

    @Override
    public String toString() {
        return String.format("%s: %s | Price: $%.2f | Preparation Time: %d min | Ingredients: %s",
                name, description, price, preparationTime, String.join(", ", ingredients));
    }
}
