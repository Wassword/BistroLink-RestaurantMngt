package org.example.managers;

import org.example.models.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderManager {
    private Map<String, Order> orders;

    public OrderManager() {
        this.orders = new HashMap<>();
    }

    // Method to create a new order
    public void createOrder(Order order) {
        orders.put(order.getOrderId(), order);
        System.out.println("Order created: " + order);
    }

    // Method to update order status
    public void updateOrderStatus(String orderId, String status) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.setStatus(status);
            System.out.println("Order " + orderId + " status updated to " + status);
        } else {
            System.out.println("Order ID " + orderId + " not found.");
        }
    }

    // Method to list all orders
    public void listOrders() {
        System.out.println("Current Orders:");
        for (Order order : orders.values()) {
            System.out.println(order);
        }
    }

    // Method to get an order by ID
    public Order getOrderById(String orderId) {
        return orders.get(orderId);
    }

    // Method to return all orders as a list
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }
}
