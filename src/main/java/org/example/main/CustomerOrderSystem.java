package org.example.main;

import org.example.managers.MenuManagement;
import org.example.managers.OrderManager;
import org.example.models.MenuItem;
import org.example.models.Order;
import org.example.utils.Colors;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.InputStream;
import java.util.Scanner;

public class CustomerOrderSystem {
    private final MenuManagement menuManagement;
    private final OrderManager orderManager;

    public CustomerOrderSystem(MenuManagement menuManagement, OrderManager orderManager) {
        this.menuManagement = menuManagement;
        this.orderManager = orderManager;
    }

    public void run() {
        // Start playing background music
        playBackgroundMusic("/OneRepublic_-_Nobody_from_Kaiju_No_8.wav");

        Scanner scanner = new Scanner(System.in);
        System.out.println(Colors.GREEN + "Welcome to the Restaurant Ordering System" + Colors.RESET);

        // Display the menu
        menuManagement.listMenuItems();

        // Process order
        System.out.print(Colors.YELLOW + "Enter Order ID: " + Colors.RESET);
        String orderId = scanner.nextLine();
        Order order = new Order(orderId);

        while (true) {
            System.out.print(Colors.YELLOW + "Enter Item Name (or 'done' to finish): " + Colors.RESET);
            String itemName = scanner.nextLine();
            if (itemName.equalsIgnoreCase("done")) {
                break;
            }

            MenuItem menuItem = menuManagement.getMenuItem(itemName);
            if (menuItem == null) {
                System.out.println(Colors.RED + "Item not found. Please try again." + Colors.RESET);
                continue;
            }

            System.out.print(Colors.YELLOW + "Enter Quantity: " + Colors.RESET);
            int quantity = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            order.addItem(menuItem, quantity);
        }

        orderManager.createOrder(order);
        System.out.println(Colors.GREEN + "Order created successfully!" + Colors.RESET);
    }

    private void playBackgroundMusic(String filepath) {
        try (InputStream audioSrc = getClass().getResourceAsStream(filepath)) {
            if (audioSrc != null) {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(audioSrc));
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);  // Loop the music continuously
            } else {
                System.out.println(Colors.RED + "Cannot find the music file." + Colors.RESET);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MenuManagement menuManagement = new MenuManagement();
        OrderManager orderManager = OrderManager.getInstance();
        CustomerOrderSystem system = new CustomerOrderSystem(menuManagement, orderManager);
        system.run();
    }
}
