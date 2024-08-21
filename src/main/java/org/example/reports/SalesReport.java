package org.example.reports;

import org.example.models.MenuItem;
import org.example.models.Order;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesReport {
    private double totalRevenue;
    private Map<String, Integer> popularItems;
    private Map<Integer, Double> tableSales;

    public SalesReport(double totalRevenue, Map<String, Integer> popularItems, Map<Integer, Double> tableSales) {
        this.totalRevenue = totalRevenue;
        this.popularItems = popularItems;
        this.tableSales = tableSales;
    }

    // Generate a textual report
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("-----------------------------\n");
        report.append("Daily Sales Report\n");
        report.append("-----------------------------\n");
        report.append("Total Revenue: $" + totalRevenue + "\n\n");

        report.append("Most Popular Items:\n");
        popularItems.forEach((item, quantity) ->
                report.append(item + ": " + quantity + " orders\n"));

        report.append("\nTable Sales:\n");
        tableSales.forEach((tableId, sales) ->
                report.append("Table " + tableId + ": $" + sales + "\n"));

        return report.toString();
    }

    // Export the report to a text file
    public void exportReport(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(generateReport());
            System.out.println("Report exported to " + fileName);
        } catch (IOException e) {
            System.out.println("Error exporting report: " + e.getMessage());
        }
    }

    // Generate report from orders
    public static SalesReport generateReportFromOrders(List<Order> orders) {
        double totalRevenue = 0;
        Map<String, Integer> popularItems = new HashMap<>();
        Map<Integer, Double> tableSales = new HashMap<>();

        // Process orders to gather statistics
        for (Order order : orders) {
            totalRevenue += order.getTotalPrice();
            for (Map.Entry<MenuItem, Integer> entry : order.getItemsOrdered().entrySet()) {
                MenuItem item = entry.getKey();
                popularItems.put(item.getName(), popularItems.getOrDefault(item.getName(), 0) + entry.getValue());
            }
            // Assuming you have a way to track tableId in Order, otherwise you need to add this to your Order class
            // tableSales.put(order.getTableId(), tableSales.getOrDefault(order.getTableId(), 0.0) + order.getTotalPrice());
        }

        return new SalesReport(totalRevenue, popularItems, tableSales);
    }
}
