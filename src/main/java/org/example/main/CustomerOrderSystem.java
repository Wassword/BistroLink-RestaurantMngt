package org.example.main;

import org.example.managers.MenuManagement;
import org.example.managers.OrderManager;
import org.example.models.MenuItem;
import org.example.models.Order;
import org.example.models.Sound;

import java.util.Scanner;

public class CustomerOrderSystem {
    public static void main(String[] args) {
        // Start the background music
        Sound soundManager = new Sound();
        new Thread(() -> soundManager.playSound("OneRepublic_-_Nobody_from_Kaiju_No_8.wav")).start();

        Scanner scanner = new Scanner(System.in);
        MenuManagement menuManagement = new MenuManagement();
        OrderManager orderManager = new OrderManager();

        try {
            menuManagement.loadMenu();
        } catch (Exception e) {
            System.out.println("Error loading menu: " + e.getMessage());
            System.out.println("Initializing a fresh menu...");
            // Optionally initialize some default menu items
            menuManagement.initializeMenu();  // This could be a method to populate with default items
        }

        System.out.println("Welcome to the Restaurant Ordering System");
        System.out.println("Menu:");

        // Print each menu item
        for (MenuItem item : menuManagement.getAllMenuItems()) {
            System.out.println(item);
        }

        System.out.print("Enter Order ID: ");
        String orderId = scanner.nextLine();
        Order order = new Order(orderId);

        while (true) {
            System.out.print("Enter Item Name (or 'done' to finish): ");
            String itemName = scanner.nextLine();
            if (itemName.equalsIgnoreCase("done")) {
                break;
            }
            MenuItem menuItem = menuManagement.getMenuItem(itemName);
            if (menuItem == null) {
                System.out.println("Item not found. Please try again.");
                continue;
            }
            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();  // Consume the newline
            order.addItem(menuItem, quantity);
        }

        orderManager.createOrder(order);
        System.out.println("Order created successfully!");
    }
}
