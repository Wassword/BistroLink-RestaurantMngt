package org.example.managers;

import org.example.models.MenuItem;
import java.util.ArrayList;
import java.io.*;
import java.util.List;

public class MenuManagement {
    private List<MenuItem> menuItems = new ArrayList<>();


    public void addMenuItem(MenuItem menuItem) {
        menuItems.add(menuItem);
    }

    public void removeMenuItem(String itemName) {
        menuItems.removeIf(item -> item.getName().equalsIgnoreCase(itemName));
    }

    public void editMenuItem(String itemName, MenuItem newItem) {
        for (int i =0; i<menuItems.size(); i++) {
            if (menuItems.get(i).getName().equalsIgnoreCase(itemName)) {
                menuItems.set(i, newItem);
                break;
            }
        }
    }

    public void saveMenu() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("menu.dat"))) {
            oos.writeObject(menuItems);
        }

    }
    public void loadMenu() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("menu.dat"))) {
            menuItems = (List<MenuItem>) ois.readObject();
        }
    }


}
