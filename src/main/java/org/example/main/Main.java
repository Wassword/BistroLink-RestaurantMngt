package org.example.main;

import org.example.login.UserLogin;
import org.example.managers.*;
import org.example.models.*;
import org.example.reports.SalesReport;
import org.example.utils.Colors;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MenuManagement menuManagement = new MenuManagement();

        System.out.println("1. Initialize and Save Menu");
        System.out.println("2. Continue to Restaurant Management System");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (choice == 1) {
            initializeAndSaveMenu(menuManagement);
        } else if (choice == 2) {
            startRestaurantManagementSystem(scanner, menuManagement);
        }
    }

    private static void initializeAndSaveMenu(MenuManagement menuManagement) {
        // Initialize some menu items
        menuManagement.addMenuItem(new MenuItem("Margherita Pizza", "Classic Margherita pizza with tomato, mozzarella, and basil", 15, 8.99, List.of("Tomato", "Mozzarella", "Basil")));
        menuManagement.addMenuItem(new MenuItem("Spaghetti Carbonara", "Spaghetti with eggs, cheese, pancetta, and pepper", 20, 12.50, List.of("Spaghetti", "Eggs", "Cheese", "Pancetta", "Pepper")));
        menuManagement.addMenuItem(new MenuItem("Tiramisu", "Traditional Italian dessert with mascarpone cheese and coffee", 30, 6.00, List.of("Mascarpone", "Coffee", "Ladyfingers", "Cocoa")));

        // Save the initialized menu
        try {
            menuManagement.saveMenu();
            System.out.println("Menu initialized and saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving menu: " + e.getMessage());
        }
    }

    private static void startRestaurantManagementSystem(Scanner scanner, MenuManagement menuManagement) {
        // Initialize other managers
        UserLogin userLogin = new UserLogin();
        ReservationManager reservationManager = new ReservationManager();
        SpecialOfferManager specialOfferManager = new SpecialOfferManager();
        OrderManager orderManager = new OrderManager();
        TableManager tableManager = new TableManager();
        InventoryManagement inventoryManagement = new InventoryManagement(5);  // Set a default low stock threshold
        MenuManagement menuManagement = new MenuManagement();
        StaffManagement staffManagement = new StaffManagement();  // Initialize StaffManagement

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
                    if (performLogin(scanner, userLogin, reservationManager, orderManager, tableManager, inventoryManagement, menuManagement)) {
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

    private static boolean performLogin(Scanner scanner, UserLogin userLogin, ReservationManager reservationManager, OrderManager orderManager, TableManager tableManager, InventoryManagement inventoryManagement, MenuManagement menuManagement) {
        try {
            System.out.print("Please enter your username: ");
            String username = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();
        // User Login Process
        System.out.println(Colors.GREEN + "Welcome to the Restaurant Management System" + Colors.RESET);
        System.out.print(Colors.YELLOW + "Please enter your username: " + Colors.RESET);
        String username = scanner.nextLine();
        System.out.print(Colors.YELLOW + "Please enter your password: " + Colors.RESET);
        String password = scanner.nextLine();

        try {
            if (userLogin.login(username, password)) {
                System.out.println(Colors.GREEN + "Login successful!" + Colors.RESET);
                String role = userLogin.getRole(username);
                System.out.println(Colors.BLUE + "Your role: " + role + Colors.RESET);

                while (true) {
                    System.out.println("\n" + Colors.BLUE + "Restaurant Management System" + Colors.RESET);
                    System.out.println("1. Create a Reservation");
                    System.out.println("2. Manage Special Offers");
                    System.out.println("3. Process Orders");
                    System.out.println("4. Manage Tables");
                    System.out.println("5. Manage Inventory");
                    System.out.println("6. Manage Menu");
                    System.out.println("7. Generate Sales Report");
                    System.out.println("8. Exit");
                    System.out.println("9. Manage Staff");  // New option for managing staff
                    System.out.print(Colors.YELLOW + "Select an option: " + Colors.RESET);
                if ("Manager".equalsIgnoreCase(role)) {
                    showManagerMenu(scanner, reservationManager, orderManager, tableManager, inventoryManagement, menuManagement);
                } else if ("Staff".equalsIgnoreCase(role)) {
                    showStaffMenu(scanner, orderManager, tableManager);
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

    private static void showManagerMenu(Scanner scanner, ReservationManager reservationManager, OrderManager orderManager, TableManager tableManager, InventoryManagement inventoryManagement, MenuManagement menuManagement) {
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
                            System.out.print(Colors.YELLOW + "Select an option: " + Colors.RESET);
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
                                    System.out.println(Colors.RED + "Invalid option." + Colors.RESET);
                            }
                            break;

                        case 3:
                            System.out.println("1. Create a new Order");
                            System.out.println("2. Update Order Status");
                            System.out.println("3. List all Orders");
                            System.out.print(Colors.YELLOW + "Select an option: " + Colors.RESET);
                            int orderOption = scanner.nextInt();
                            scanner.nextLine();  // Consume the newline
            switch (option) {
                case 1:
                    createReservation(scanner, reservationManager);
                    break;
                case 2:
                    // Uncomment and use when SpecialOfferManager is available
                    // manageSpecialOffers(scanner, specialOfferManager, orderManager);
                    System.out.println("Special Offer Management is not available yet.");
                    break;
                case 3:
                    processOrders(scanner, orderManager, menuManagement);
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

    private static void showStaffMenu(Scanner scanner, OrderManager orderManager, TableManager tableManager) {
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
                    processOrders(scanner, orderManager, null);
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

    // Placeholder for Special Offer management
    // private static void manageSpecialOffers(Scanner scanner, SpecialOfferManager specialOfferManager, OrderManager orderManager) {
    //     // Implement this when SpecialOfferManager is available
    // }

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

                if (menuManagement != null) {
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
                                    System.out.println(Colors.RED + "Invalid option." + Colors.RESET);
                            }
                            break;

                        case 4:
                            System.out.println("1. Add a new Table");
                            System.out.println("2. List all Tables");
                            System.out.println("3. Assign Customer to a Table");
                            System.out.println("4. Update Table Status");
                            System.out.print(Colors.YELLOW + "Select an option: " + Colors.RESET);
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
                                    System.out.println(Colors.RED + "Invalid option." + Colors.RESET);
                            }
                            break;

                        case 5:
                            System.out.println("1. Add Ingredient");
                            System.out.println("2. Use Ingredient");
                            System.out.println("3. Check Ingredient Stock");
                            System.out.println("4. Load Inventory from File");
                            System.out.println("5. Save Inventory to File");
                            System.out.print(Colors.YELLOW + "Select an option: " + Colors.RESET);
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
                                    System.out.println(Colors.RED + "Invalid option." + Colors.RESET);
                            }
                            break;

                        case 6:
                            System.out.println("1. Add Menu Item");
                            System.out.println("2. Remove Menu Item");
                            System.out.println("3. Edit Menu Item");
                            System.out.println("4. Save Menu");
                            System.out.println("5. Load Menu");
                            System.out.print(Colors.YELLOW + "Select an option: " + Colors.RESET);
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
                                    System.out.println(Colors.RED + "Invalid option." + Colors.RESET);
                            }
                            break;

                        case 7:
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
                            break;

                        case 8:
                            System.out.println(Colors.BLUE + "Exiting the system." + Colors.RESET);
                            scanner.close();
                            return;

                        case 9:
                            System.out.println("1. Add Staff");
                            System.out.println("2. Remove Staff");
                            System.out.println("3. Edit Staff");
                            System.out.println("4. Display All Staff");
                            System.out.print(Colors.YELLOW + "Select an option: " + Colors.RESET);
                            int staffOption = scanner.nextInt();
                            scanner.nextLine();  // Consume the newline

                            switch (staffOption) {
                                case 1:
                                    System.out.print(Colors.YELLOW + "Enter Staff ID: " + Colors.RESET);
                                    int staffId = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline
                                    System.out.print(Colors.YELLOW + "Enter Staff Name: " + Colors.RESET);
                                    String staffName = scanner.nextLine();
                                    System.out.print(Colors.YELLOW + "Enter Staff Role: " + Colors.RESET);
                                    String staffRole = scanner.nextLine();
                                    System.out.print(Colors.YELLOW + "Enter Hours Worked: " + Colors.RESET);
                                    int hoursWorked = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline

                                    Staff newStaff = new Staff(staffId, staffName, staffRole, hoursWorked);
                                    staffManagement.addStaff(newStaff);
                                    break;

                                case 2:
                                    System.out.print(Colors.YELLOW + "Enter Staff ID to remove: " + Colors.RESET);
                                    int removeStaffId = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline
                                    staffManagement.removeStaff(removeStaffId);
                                    break;

                                case 3:
                                    System.out.print(Colors.YELLOW + "Enter Staff ID to edit: " + Colors.RESET);
                                    int editStaffId = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline
                                    System.out.print(Colors.YELLOW + "Enter New Staff Name: " + Colors.RESET);
                                    String newStaffName = scanner.nextLine();
                                    System.out.print(Colors.YELLOW + "Enter New Staff Role: " + Colors.RESET);
                                    String newStaffRole = scanner.nextLine();
                                    System.out.print(Colors.YELLOW + "Enter New Hours Worked: " + Colors.RESET);
                                    int newHoursWorked = scanner.nextInt();
                                    scanner.nextLine(); // Consume the newline

                                    staffManagement.editStaff(editStaffId, newStaffName, newStaffRole, newHoursWorked);
                                    break;

                                case 4:
                                    staffManagement.displayStaff();
                                    break;

                                default:
                                    System.out.println(Colors.RED + "Invalid option." + Colors.RESET);
                            }
                            break;

                        default:
                            System.out.println(Colors.RED + "Invalid option, please try again." + Colors.RESET);
                    }
                }
            } else {
                System.out.println(Colors.RED + "Invalid username or password." + Colors.RESET);
            }
        } catch (Exception e) {
            System.out.println(Colors.RED + "An error occurred during login: " + e.getMessage() + Colors.RESET);
        }
    }
}
