package org.example.managers;

import org.example.models.Order;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderManager {
    private static OrderManager instance;
    private Map<String, Order> orders = new HashMap<>();
    private static final String FILE_PATH = "orders.dat";

    // Private constructor to implement the Singleton pattern
    private OrderManager() {
        loadOrders(); // Load orders when the manager is initialized
    }

    // Static method to provide a global point of access to the singleton instance
    public static OrderManager getInstance() {
        if (instance == null) {
            instance = new OrderManager();
        }
        return instance;
    }

    // Method to create a new order and save it to the file
    public void createOrder(Order order) {
        orders.put(order.getOrderId(), order);
        saveOrders(); // Save the orders whenever a new one is added
        System.out.println("Order created: " + order.getOrderId());
    }

    // Method to list all orders
    public void listOrders() {
        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.println("Current Orders:");
            for (Order order : orders.values()) {
                System.out.println("Order ID: " + order.getOrderId() +
                        ", Total Price: $" + order.getTotalPrice() +
                        ", Status: " + order.getStatus());
            }
        }
    }

    // Method to get an order by its ID
    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }

    // Method to update the status of an order
    public void updateOrderStatus(String orderId, String status) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(status);
            saveOrders(); // Save the orders after updating
            System.out.println("Order status updated: " + order.getOrderId());
        } else {
            System.out.println("Order ID not found.");
        }
    }

    // Method to get all orders as a list
    public List<Order> getAllOrders() {
        return orders.values().stream().collect(Collectors.toList());
    }

    // Private method to save all orders to a file
    private void saveOrders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Private method to load orders from a file
    private void loadOrders() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            orders = (Map<String, Order>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No existing order data found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
