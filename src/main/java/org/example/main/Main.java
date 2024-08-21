package org.example.main;

import org.example.managers.OrderManager;
import org.example.managers.ReservationManager;
import org.example.managers.SpecialOfferManager;
import org.example.managers.TableManager;
import org.example.models.Order;
import org.example.models.Reservation;
import org.example.models.SpecialOffer;
import org.example.models.Table;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ReservationManager reservationManager = new ReservationManager();
        SpecialOfferManager specialOfferManager = new SpecialOfferManager();
        OrderManager orderManager = new OrderManager();
        TableManager tableManager = new TableManager();

        while (true) {
            System.out.println("\nRestaurant Management System");
            System.out.println("1. Create a Reservation");
            System.out.println("2. Manage Special Offers");
            System.out.println("3. Process Orders");
            System.out.println("4. Manage Tables");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (option) {
                case 1:
                    System.out.print("Enter Customer Name: ");
                    String customerName = scanner.nextLine();
                    System.out.print("Enter Reservation Time (yyyy-MM-ddTHH:mm): ");
                    String reservationTimeInput = scanner.nextLine();
                    LocalDateTime reservationTime = LocalDateTime.parse(reservationTimeInput);
                    System.out.print("Enter Table ID: ");
                    int tableId = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline
                    Reservation reservation = new Reservation(customerName, reservationTime, tableId);
                    reservationManager.addReservation(reservation);
                    break;

                case 2:
                    System.out.println("1. Add a Special Offer");
                    System.out.println("2. List All Special Offers");
                    System.out.println("3. Apply Special Offer to Order");
                    System.out.println("4. Remove a Special Offer");
                    System.out.print("Select an option: ");
                    int specialOfferOption = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline

                    switch (specialOfferOption) {
                        case 1:
                            System.out.print("Enter Offer ID: ");
                            String offerId = scanner.nextLine();
                            System.out.print("Enter Offer Description: ");
                            String description = scanner.nextLine();
                            System.out.print("Enter Discount Amount: ");
                            double discountAmount = scanner.nextDouble();
                            scanner.nextLine();  // Consume the newline
                            SpecialOffer specialOffer = new SpecialOffer(offerId, description, discountAmount);
                            specialOfferManager.addOffer(specialOffer);
                            break;

                        case 2:
                            specialOfferManager.listOffers();
                            break;

                        case 3:
                            System.out.print("Enter Offer ID to apply: ");
                            offerId = scanner.nextLine();
                            System.out.print("Enter Order ID: ");
                            String orderIdForOffer = scanner.nextLine();
                            Order orderForOffer = orderManager.getOrderById(orderIdForOffer);
                            if (orderForOffer != null) {
                                specialOfferManager.applyOfferToOrder(offerId, orderForOffer);
                            } else {
                                System.out.println("Order ID not found.");
                            }
                            break;

                        case 4:
                            System.out.print("Enter Offer ID to remove: ");
                            offerId = scanner.nextLine();
                            specialOfferManager.removeOffer(offerId);
                            break;

                        default:
                            System.out.println("Invalid option.");
                    }
                    break;

                case 3:
                    System.out.println("1. Create a new Order");
                    System.out.println("2. Update Order Status");
                    System.out.println("3. List all Orders");
                    System.out.print("Select an option: ");
                    int orderOption = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline

                    switch (orderOption) {
                        case 1:
                            System.out.print("Enter Order ID: ");
                            String orderId = scanner.nextLine();
                            Order order = new Order(orderId);
                            while (true) {
                                System.out.print("Enter Item Name (or 'done' to finish): ");
                                String itemName = scanner.nextLine();
                                if (itemName.equalsIgnoreCase("done")) {
                                    break;
                                }
                                System.out.print("Enter Quantity: ");
                                int quantity = scanner.nextInt();
                                System.out.print("Enter Price per Item: ");
                                double price = scanner.nextDouble();
                                scanner.nextLine();  // Consume the newline
                                order.addItem(itemName, quantity, price);
                            }
                            orderManager.createOrder(order);
                            break;

                        case 2:
                            System.out.print("Enter Order ID to update: ");
                            orderId = scanner.nextLine();
                            System.out.print("Enter new status (Waiting/Preparing/Ready): ");
                            String status = scanner.nextLine();
                            orderManager.updateOrderStatus(orderId, status);
                            break;

                        case 3:
                            orderManager.listOrders();
                            break;

                        default:
                            System.out.println("Invalid option.");
                    }
                    break;

                case 4:
                    System.out.println("1. Add a new Table");
                    System.out.println("2. List all Tables");
                    System.out.println("3. Assign Customer to a Table");
                    System.out.println("4. Update Table Status");
                    System.out.print("Select an option: ");
                    int tableOption = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline

                    switch (tableOption) {
                        case 1:
                            System.out.print("Enter Table ID: ");
                            int tableIdForAdd = scanner.nextInt();
                            System.out.print("Enter Table Size: ");
                            int size = scanner.nextInt();
                            scanner.nextLine();  // Consume the newline
                            Table table = new Table(tableIdForAdd, size);
                            tableManager.addTable(table);
                            break;

                        case 2:
                            tableManager.listTables();
                            break;

                        case 3:
                            System.out.print("Enter Table ID to assign: ");
                            int tableIdForAssign = scanner.nextInt();
                            scanner.nextLine();  // Consume the newline
                            tableManager.assignCustomerToTable(tableIdForAssign);
                            break;

                        case 4:
                            System.out.print("Enter Table ID to update: ");
                            int tableIdForUpdate = scanner.nextInt();
                            scanner.nextLine();  // Consume the newline
                            System.out.print("Enter new status (Available/Reserved/Occupied): ");
                            String statusForUpdate = scanner.nextLine();
                            tableManager.updateTableStatus(tableIdForUpdate, statusForUpdate);
                            break;

                        default:
                            System.out.println("Invalid option.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }
}
