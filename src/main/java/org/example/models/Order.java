package org.example.models;

/* The Order class will handle customer orders.
 * This class will include properties such as order ID, items ordered, total price, and status.
 */

import java.util.HashMap;
import java.util.Map;

public class Order { // 'orderId': Unique identifier for each order.
    private String orderId;
    private Map<String, Integer> itemsOrdered; // Item name as key, quantity as value
    private double totalPrice; // 'totalPrice': The total price of the order.
    private String status; // "Waiting", "Preparing", "Ready"

    // Constructor
    public Order(String orderId) {
        this.orderId = orderId;
        this.itemsOrdered = new HashMap<>();
        this.totalPrice = 0.0;
        this.status = "Waiting";
    }

    // Methods to add and remove items
    public void addItem(String itemName, int quantity, double price) {
        this.itemsOrdered.put(itemName, this.itemsOrdered.getOrDefault(itemName, 0) + quantity);
        this.totalPrice += quantity * price;
    }

    public void removeItem(String itemName, int quantity, double price) {
        if (this.itemsOrdered.containsKey(itemName)) {
            int currentQuantity = this.itemsOrdered.get(itemName);
            if (currentQuantity <= quantity) {
                this.itemsOrdered.remove(itemName);
                this.totalPrice -= currentQuantity * price;
            } else {
                this.itemsOrdered.put(itemName, currentQuantity - quantity);
                this.totalPrice -= quantity * price;
            }
        }
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public Map<String, Integer> getItemsOrdered() {
        return itemsOrdered;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", itemsOrdered=" + itemsOrdered +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                '}';
    }
}

