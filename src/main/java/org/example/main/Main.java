package org.example.main;

import org.example.managers.*;
import org.example.models.*;
import org.example.reports.SalesReport;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserLogin userLogin = new UserLogin();
        ReservationManager reservationManager = new ReservationManager();
        SpecialOfferManager specialOfferManager = new SpecialOfferManager();
        OrderManager orderManager = new OrderManager();
        TableManager tableManager = new TableManager();
        InventoryManagement inventoryManagement = new InventoryManagement(5);  // Set a default low stock threshold
        MenuManagement menuManagement = new MenuManagement();

        System.out.println("Welcome to the Restaurant Management System");

        while (true) {
            System.out.println("1. Register New Employee");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int initialOption = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (initialOption) {
                case 1:
                    registerEmployee(scanner, userLogin);
                    break;
                case 2:
                    if (performLogin(scanner, userLogin)) {
                        return;  // End program after successful login and role-based operations
                    }
                    break;
                case 3:
                    System.out.println("Exiting the system.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void registerEmployee(Scanner scanner, UserLogin userLogin) {
        try {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter role (Manager/Staff): ");
            String role = scanner.nextLine();

            if (!role.equalsIgnoreCase("Manager") && !role.equalsIgnoreCase("Staff")) {
                System.out.println("Invalid role. Please enter 'Manager' or 'Staff'.");
                return;
            }

            userLogin.addUser(username, password, role);
            System.out.println("Employee registered successfully!");
        } catch (Exception e) {
            System.out.println("An error occurred during registration: " + e.getMessage());
        }
    }

    private static boolean performLogin(Scanner scanner, UserLogin userLogin) {
        try {
            System.out.print("Please enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();

            if (userLogin.login(username, password)) {
                System.out.println("Login successful!");
                String role = userLogin.getRole(username);
                System.out.println("Your role: " + role);

                if ("Manager".equalsIgnoreCase(role)) {
                    showManagerMenu(scanner, userLogin);
                } else if ("Staff".equalsIgnoreCase(role)) {
                    showStaffMenu(scanner, userLogin);
                }
                return true;
            } else {
                System.out.println("Invalid username or password.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
        }
        return false;
    }

    private static void showManagerMenu(Scanner scanner, UserLogin userLogin) {
        ReservationManager reservationManager = new ReservationManager();
        SpecialOfferManager specialOfferManager = new SpecialOfferManager();
        OrderManager orderManager = new OrderManager();
        TableManager tableManager = new TableManager();
        InventoryManagement inventoryManagement = new InventoryManagement(5);
        MenuManagement menuManagement = new MenuManagement();

        while (true) {
            System.out.println("\nManager Menu");
            System.out.println("1. Create a Reservation");
            System.out.println("2. Manage Special Offers");
            System.out.println("3. Process Orders");
            System.out.println("4. Manage Tables");
            System.out.println("5. Manage Inventory");
            System.out.println("6. Manage Menu");
            System.out.println("7. Generate Sales Report");
            System.out.println("8. Logout");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (option) {
                case 1:
                    createReservation(scanner, reservationManager);
                    break;
                case 2:
                    manageSpecialOffers(scanner, specialOfferManager, orderManager);
                    break;
                case 3:
                    processOrders(scanner, orderManager);
                    break;
                case 4:
                    manageTables(scanner, tableManager);
                    break;
                case 5:
                    manageInventory(scanner, inventoryManagement);
                    break;
                case 6:
                    manageMenu(scanner, menuManagement);
                    break;
                case 7:
                    generateSalesReport(scanner, orderManager);
                    break;
                case 8:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void showStaffMenu(Scanner scanner, UserLogin userLogin) {
        OrderManager orderManager = new OrderManager();
        TableManager tableManager = new TableManager();

        while (true) {
            System.out.println("\nStaff Menu");
            System.out.println("1. Process Orders");
            System.out.println("2. Manage Tables");
            System.out.println("3. Logout");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (option) {
                case 1:
                    processOrders(scanner, orderManager);
                    break;
                case 2:
                    manageTables(scanner, tableManager);
                    break;
                case 3:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    // Additional methods used in the menu (e.g., createReservation, manageSpecialOffers, etc.)
    // Implement these methods similarly to how you've done in the main loop

    private static void createReservation(Scanner scanner, ReservationManager reservationManager) {
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
    }

    private static void manageSpecialOffers(Scanner scanner, SpecialOfferManager specialOfferManager, OrderManager orderManager) {
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
    }

    private static void processOrders(Scanner scanner, OrderManager orderManager) {
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
                    System.out.print("Enter Item Description: ");
                    String itemDescription = scanner.nextLine();
                    System.out.print("Enter Preparation Time (in minutes): ");
                    int preparationTime = scanner.nextInt();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Enter Ingredients (comma-separated): ");
                    String ingredientsInput = scanner.nextLine();
                    List<String> ingredients = List.of(ingredientsInput.split(",\\s*"));

                    MenuItem menuItem = new MenuItem(itemName, itemDescription, preparationTime, price, ingredients);
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    order.addItem(menuItem, quantity);
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
    }

    private static void manageTables(Scanner scanner, TableManager tableManager) {
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
    }

    private static void manageInventory(Scanner scanner, InventoryManagement inventoryManagement) {
        System.out.println("1. Add Ingredient");
        System.out.println("2. Use Ingredient");
        System.out.println("3. Check Ingredient Stock");
        System.out.println("4. Load Inventory from File");
        System.out.println("5. Save Inventory to File");
        System.out.print("Select an option: ");
        int inventoryOption = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        switch (inventoryOption) {
            case 1:
                System.out.print("Enter Ingredient Name: ");
                String ingredientName = scanner.nextLine();
                System.out.print("Enter Quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();  // Consume the newline
                inventoryManagement.addIngredient(ingredientName, quantity);
                break;

            case 2:
                System.out.print("Enter Ingredient Name: ");
                String ingredientToUse = scanner.nextLine();
                System.out.print("Enter Quantity: ");
                int quantityToUse = scanner.nextInt();
                scanner.nextLine();  // Consume the newline
                inventoryManagement.useIngredient(ingredientToUse, quantityToUse);
                break;

            case 3:
                System.out.print("Enter Ingredient Name: ");
                String ingredientToCheck = scanner.nextLine();
                int stock = inventoryManagement.checkStock(ingredientToCheck);
                System.out.println("Current stock of " + ingredientToCheck + ": " + stock);
                break;

            case 4:
                System.out.print("Enter file name to load inventory from: ");
                String loadFileName = scanner.nextLine();
                inventoryManagement.loadInventoryFromFile(loadFileName);
                break;

            case 5:
                System.out.print("Enter file name to save inventory to: ");
                String saveFileName = scanner.nextLine();
                inventoryManagement.saveInventoryToFile(saveFileName);
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    private static void manageMenu(Scanner scanner, MenuManagement menuManagement) {
        System.out.println("1. Add Menu Item");
        System.out.println("2. Remove Menu Item");
        System.out.println("3. Edit Menu Item");
        System.out.println("4. Save Menu");
        System.out.println("5. Load Menu");
        System.out.print("Select an option: ");
        int menuOption = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        switch (menuOption) {
            case 1:
                System.out.print("Enter Item Name: ");
                String itemName = scanner.nextLine();
                System.out.print("Enter Item Description: ");
                String itemDescription = scanner.nextLine();
                System.out.print("Enter Preparation Time (in minutes): ");
                int preparationTime = scanner.nextInt();
                System.out.print("Enter Price: ");
                double price = scanner.nextDouble();
                scanner.nextLine();  // Consume the newline

                System.out.print("Enter Ingredients (comma-separated): ");
                String ingredientsInput = scanner.nextLine();
                List<String> ingredients = List.of(ingredientsInput.split(",\\s*"));

                MenuItem menuItem = new MenuItem(itemName, itemDescription, preparationTime, price, ingredients);
                menuManagement.addMenuItem(menuItem);
                break;

            case 2:
                System.out.print("Enter Item Name to remove: ");
                String itemNameToRemove = scanner.nextLine();
                menuManagement.removeMenuItem(itemNameToRemove);
                break;

            case 3:
                System.out.print("Enter Item Name to edit: ");
                String itemNameToEdit = scanner.nextLine();
                System.out.print("Enter New Item Description: ");
                String newItemDescription = scanner.nextLine();
                System.out.print("Enter New Preparation Time (in minutes): ");
                int newPreparationTime = scanner.nextInt();
                System.out.print("Enter New Price: ");
                double newPrice = scanner.nextDouble();
                scanner.nextLine();  // Consume the newline

                System.out.print("Enter New Ingredients (comma-separated): ");
                String newIngredientsInput = scanner.nextLine();
                List<String> newIngredients = List.of(newIngredientsInput.split(",\\s*"));

                MenuItem newItem = new MenuItem(itemNameToEdit, newItemDescription, newPreparationTime, newPrice, newIngredients);
                menuManagement.editMenuItem(itemNameToEdit, newItem);
                break;

            case 4:
                try {
                    menuManagement.saveMenu();
                    System.out.println("Menu saved successfully.");
                } catch (IOException e) {
                    System.out.println("Error saving menu: " + e.getMessage());
                }
                break;

            case 5:
                try {
                    menuManagement.loadMenu();
                    System.out.println("Menu loaded successfully.");
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error loading menu: " + e.getMessage());
                }
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    private static void generateSalesReport(Scanner scanner, OrderManager orderManager) {
        System.out.println("Generating Sales Report...");
        SalesReport salesReport = SalesReport.generateReportFromOrders(orderManager.getAllOrders());
        System.out.println(salesReport.generateReport());
        System.out.print("Do you want to export this report to a file? (yes/no): ");
        String exportChoice = scanner.nextLine();
        if (exportChoice.equalsIgnoreCase("yes")) {
            System.out.print("Enter file name to export report: ");
            String reportFileName = scanner.nextLine();
            salesReport.exportReport(reportFileName);
        }
    }
}
