package org.example.models;

import java.util.HashMap;
import java.util.Map;

/* The Order class will handle customer orders.
 * This class includes properties such as order ID, items ordered, total price, and status.
 */

public class Order {
    private String orderId;
    private Map<MenuItem, Integer> itemsOrdered; // MenuItem as key, quantity as value
    private double totalPrice;
    private String status; // "Waiting", "Preparing", "Ready"

    // Constructor
    public Order(String orderId) {
        this.orderId = orderId;
        this.itemsOrdered = new HashMap<>();
        this.totalPrice = 0.0;
        this.status = "Waiting";
    }

    // Methods to add and remove items
    public void addItem(MenuItem item, int quantity) {
        this.itemsOrdered.put(item, this.itemsOrdered.getOrDefault(item, 0) + quantity);
        this.totalPrice += quantity * item.getPrice();
    }

    public void removeItem(MenuItem item, int quantity) {
        if (this.itemsOrdered.containsKey(item)) {
            int currentQuantity = this.itemsOrdered.get(item);
            if (currentQuantity <= quantity) {
                this.itemsOrdered.remove(item);
                this.totalPrice -= currentQuantity * item.getPrice();
            } else {
                this.itemsOrdered.put(item, currentQuantity - quantity);
                this.totalPrice -= quantity * item.getPrice();
            }
        }
    }

    // Getters and Setters
    public String getOrderId() {
        return orderId;
    }

    public Map<MenuItem, Integer> getItemsOrdered() {
        return itemsOrdered;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    // New method to set the total price
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Convenience method to check if order has items
    public boolean hasItems() {
        return !itemsOrdered.isEmpty();
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
