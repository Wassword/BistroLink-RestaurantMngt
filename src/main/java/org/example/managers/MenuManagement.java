package org.example.managers;

import org.example.models.MenuItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MenuManagement {
    private List<MenuItem> menuItems;

    public MenuManagement() {
        this.menuItems = new ArrayList<>();
        initializeMenu();  // Initialize with Italian menu items
    }

    public void initializeMenu() {
        menuItems.add(new MenuItem("Margherita Pizza", "Classic pizza with tomatoes, mozzarella, and basil", 15, 10.99, List.of("Tomatoes", "Mozzarella", "Basil")));
        menuItems.add(new MenuItem("Lasagna", "Layered pasta with beef, cheese, and tomato sauce", 30, 14.99, List.of("Pasta", "Beef", "Tomato Sauce", "Cheese")));
        menuItems.add(new MenuItem("Spaghetti Carbonara", "Spaghetti with eggs, cheese, pancetta, and pepper", 20, 12.99, List.of("Spaghetti", "Eggs", "Cheese", "Pancetta", "Pepper")));
        menuItems.add(new MenuItem("Fettuccine Alfredo", "Pasta with a creamy Alfredo sauce", 20, 11.99, List.of("Fettuccine", "Cream", "Butter", "Parmesan")));
        menuItems.add(new MenuItem("Tiramisu", "Classic Italian dessert with layers of coffee-soaked ladyfingers and mascarpone cheese", 10, 6.99, List.of("Ladyfingers", "Coffee", "Mascarpone", "Cocoa")));
        menuItems.add(new MenuItem("Bruschetta", "Grilled bread topped with tomatoes, garlic, and basil", 10, 7.99, List.of("Bread", "Tomatoes", "Garlic", "Basil", "Olive Oil")));
        menuItems.add(new MenuItem("Caprese Salad", "Salad of fresh tomatoes, mozzarella, and basil", 10, 8.99, List.of("Tomatoes", "Mozzarella", "Basil", "Olive Oil", "Balsamic Vinegar")));
    }

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    // Added to match the method name in CustomerOrderSystem.java
    public List<MenuItem> getAllMenuItems() {
        return getMenuItems();
    }

    public MenuItem getMenuItem(String name) {
        for (MenuItem item : menuItems) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public void listMenuItems() {
        for (MenuItem item : menuItems) {
            System.out.println(item);
        }
    }

    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void removeMenuItem(String name) {
        menuItems.removeIf(item -> item.getName().equalsIgnoreCase(name));
    }

    public void editMenuItem(String name, MenuItem newItem) {
        for (int i = 0; i < menuItems.size(); i++) {
            if (menuItems.get(i).getName().equalsIgnoreCase(name)) {
                menuItems.set(i, newItem);
                break;
            }
        }
    }

    // Save menu to file
    public void saveMenu() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("menu.dat"))) {
            oos.writeObject(menuItems);
        }
    }

    // Load menu from file
    @SuppressWarnings("unchecked")
    public void loadMenu() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("menu.dat"))) {
            menuItems = (List<MenuItem>) ois.readObject();
        }
    }
}
