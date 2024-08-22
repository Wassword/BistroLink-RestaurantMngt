package org.example.main;

import org.example.login.UserLogin;
import org.example.managers.*;
import org.example.models.*;
import org.example.reports.SalesReport;
import org.example.utils.Colors;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserLogin userLogin = new UserLogin();
        ReservationManager reservationManager = new ReservationManager();
        SpecialOfferManager specialOfferManager = new SpecialOfferManager();
        OrderManager orderManager = OrderManager.getInstance();
        TableManager tableManager = new TableManager();
        InventoryManagement inventoryManagement = new InventoryManagement(5);  // Set a default low stock threshold
        MenuManagement menuManagement = new MenuManagement();
        StaffManagement staffManagement = new StaffManagement();

        System.out.println(Colors.GREEN + "Welcome to the Restaurant Management System!" + Colors.RESET);

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register a New Employee");
            System.out.println("3. Exit");
            System.out.print("Select an option: ");
            int initialOption = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            switch (initialOption) {
                case 1:
                    handleLogin(scanner, userLogin, reservationManager, specialOfferManager, orderManager, tableManager, inventoryManagement, menuManagement, staffManagement);
                    break;

                case 2:
                    registerNewEmployee(scanner, userLogin);
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

    private static void handleLogin(Scanner scanner, UserLogin userLogin, ReservationManager reservationManager, SpecialOfferManager specialOfferManager, OrderManager orderManager, TableManager tableManager, InventoryManagement inventoryManagement, MenuManagement menuManagement, StaffManagement staffManagement) {
        System.out.print(Colors.YELLOW + "Please enter your username: " + Colors.RESET);
        String username = scanner.nextLine();
        System.out.print(Colors.YELLOW + "Please enter your password: " + Colors.RESET);
        String password = scanner.nextLine();

        try {
            if (userLogin.login(username, password)) {
                System.out.println(Colors.GREEN + "Login successful!" + Colors.RESET);
                String role = userLogin.getRole(username);
                System.out.println("Your role: " + role);

                while (true) {
                    System.out.println("\nRestaurant Management System");

                    // Menu options based on role
                    if ("Manager".equalsIgnoreCase(role)) {
                        System.out.println("1. Create a Reservation");
                        System.out.println("2. Manage Special Offers");
                        System.out.println("3. Process Orders");
                        System.out.println("4. Manage Tables");
                        System.out.println("5. Manage Inventory");
                        System.out.println("6. Manage Menu");
                        System.out.println("7. Generate Sales Report");
                        System.out.println("8. Manage Staff");
                        System.out.println("9. Exit");
                    } else if ("Staff".equalsIgnoreCase(role)) {
                        System.out.println("1. Create a Reservation");
                        System.out.println("2. Process Orders");
                        System.out.println("3. Exit");
                    }

                    System.out.print("Select an option: ");
                    int option = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline

                    switch (option) {
                        case 1:
                            reservationManager.createAndAddReservation(scanner);  // Delegate to ReservationManager
                            break;

                        case 2:
                            if ("Manager".equalsIgnoreCase(role)) {
                                manageSpecialOffers(scanner, specialOfferManager, orderManager);
                            } else if ("Staff".equalsIgnoreCase(role)) {
                                processOrders(scanner, orderManager, menuManagement);
                            }
                            break;

                        case 3:
                            if ("Manager".equalsIgnoreCase(role)) {
                                processOrders(scanner, orderManager, menuManagement);
                            } else if ("Staff".equalsIgnoreCase(role)) {
                                System.out.println("Exiting the system.");
                                scanner.close();
                                return;
                            }
                            break;

                        case 4:
                            if ("Manager".equalsIgnoreCase(role)) {
                                manageTables(scanner, tableManager);
                            }
                            break;

                        case 5:
                            if ("Manager".equalsIgnoreCase(role)) {
                                manageInventory(scanner, inventoryManagement);
                            }
                            break;

                        case 6:
                            if ("Manager".equalsIgnoreCase(role)) {
                                manageMenu(scanner, menuManagement);
                            }
                            break;

                        case 7:
                            if ("Manager".equalsIgnoreCase(role)) {
                                generateSalesReport(orderManager);
                            }
                            break;

                        case 8:
                            if ("Manager".equalsIgnoreCase(role)) {
                                manageStaff(scanner, staffManagement);
                            }
                            break;

                        case 9:
                            if ("Manager".equalsIgnoreCase(role)) {
                                System.out.println("Exiting the system.");
                                scanner.close();
                                return;
                            }
                            break;

                        default:
                            System.out.println("Invalid option, please try again.");
                    }
                }
            } else {
                System.out.println(Colors.RED + "Invalid username or password." + Colors.RESET);
            }
        } catch (Exception e) {
            System.out.println(Colors.RED + "An error occurred during login: " + e.getMessage() + Colors.RESET);
        }
    }

    // Method to register a new employee
    private static void registerNewEmployee(Scanner scanner, UserLogin userLogin) {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();

        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

        System.out.print("Enter role (Manager/Staff): ");
        String role = scanner.nextLine();

        try {
            userLogin.addUser(newUsername, newPassword, role);
            System.out.println("New employee registered successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred while registering the employee: " + e.getMessage());
        }
    }

    // Method to manage special offers
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
                String offerIdToApply = scanner.nextLine();
                System.out.print("Enter Order ID: ");
                String orderIdForOffer = scanner.nextLine();
                Order orderForOffer = orderManager.getOrderById(orderIdForOffer);
                if (orderForOffer != null) {
                    specialOfferManager.applyOfferToOrder(offerIdToApply, orderForOffer);
                } else {
                    System.out.println("Order ID not found.");
                }
                break;

            case 4:
                System.out.print("Enter Offer ID to remove: ");
                String offerIdToRemove = scanner.nextLine();
                specialOfferManager.removeOffer(offerIdToRemove);
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    // Method to process orders
    private static void processOrders(Scanner scanner, OrderManager orderManager, MenuManagement menuManagement) {
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

                    // Assume ingredients are provided as a comma-separated list
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
                String orderIdToUpdate = scanner.nextLine();
                System.out.print("Enter new status (Waiting/Preparing/Ready): ");
                String status = scanner.nextLine();
                orderManager.updateOrderStatus(orderIdToUpdate, status);
                break;

            case 3:
                orderManager.listOrders();
                break;

            default:
                System.out.println("Invalid option.");
        }
    }

    // Method to manage tables
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

    // Method to manage inventory
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

    // Method to manage menu
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

                // Assume ingredients are provided as a comma-separated list
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

                // Assume ingredients are provided as a comma-separated list
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

    // Method to generate sales report
    private static void generateSalesReport(OrderManager orderManager) {
        System.out.println("Generating Sales Report...");
        SalesReport salesReport = SalesReport.generateReportFromOrders(orderManager.getAllOrders());
        System.out.println(salesReport.generateReport());
        System.out.print("Do you want to export this report to a file? (yes/no): ");
        Scanner scanner = new Scanner(System.in);
        String exportChoice = scanner.nextLine();
        if (exportChoice.equalsIgnoreCase("yes")) {
            System.out.print("Enter file name to export report: ");
            String reportFileName = scanner.nextLine();
            salesReport.exportReport(reportFileName);
        }
    }

    // Method to manage staff
    private static void manageStaff(Scanner scanner, StaffManagement staffManagement) {
        System.out.println("1. Add Staff");
        System.out.println("2. Remove Staff");
        System.out.println("3. Edit Staff");
        System.out.println("4. Display Staff");
        System.out.print("Select an option: ");
        int staffOption = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        switch (staffOption) {
            case 1:
                System.out.print("Enter Staff ID: ");
                int staffId = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                System.out.print("Enter Staff Name: ");
                String staffName = scanner.nextLine();
                System.out.print("Enter Staff Role: ");
                String staffRole = scanner.nextLine();
                System.out.print("Enter Hours Worked: ");
                int hoursWorked = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                Staff staff = new Staff(staffId, staffName, staffRole, hoursWorked);
                staffManagement.addStaff(staff);
                break;

            case 2:
                System.out.print("Enter Staff ID to remove: ");
                int staffIdToRemove = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                staffManagement.removeStaff(staffIdToRemove);
                break;

            case 3:
                System.out.print("Enter Staff ID to edit: ");
                int staffIdToEdit = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                System.out.print("Enter New Staff Name: ");
                String newStaffName = scanner.nextLine();
                System.out.print("Enter New Staff Role: ");
                String newStaffRole = scanner.nextLine();
                System.out.print("Enter New Hours Worked: ");
                int newHoursWorked = scanner.nextInt();
                scanner.nextLine(); // Consume the newline
                staffManagement.editStaff(staffIdToEdit, newStaffName, newStaffRole, newHoursWorked);
                break;

            case 4:
                staffManagement.displayStaff();
                break;

            default:
                System.out.println("Invalid option.");
        }
    }
}
