package org.example.models;

import java.io.Serializable;
import java.util.List;

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
        this.ingredients = ingredients;
    }

    // Getter and setters for menu item properties
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
        return ingredients;
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
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return String.format("%s: %s | Price: $%.2f | Preparation Time: %d min | Ingredients: %s",
                name, description, price, preparationTime, String.join(", ", ingredients));
    }
}
