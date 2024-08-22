package org.example.models;

import java.io.*;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class UserLogin {
    private HashMap<String, String> users = new HashMap<>();
    private HashMap<String, String> roles = new HashMap<>();
    private static final String USER_DATA_FILE = "users.txt";

    public UserLogin() {
        // Load existing users from file when the object is created
        try {
            loadUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addUser(String username, String password, String role) throws Exception {
        String hashedPassword = hashPassword(password);
        users.put(username, hashedPassword);
        roles.put(username, role);
        saveUsers(); // Save the user to file whenever a new user is added
    }

    public boolean login(String username, String password) throws Exception {
        String[] storedPasswordAndSalt = users.get(username).split(":");
        String storedHashedPassword = storedPasswordAndSalt[0];
        String storedSalt = storedPasswordAndSalt[1];

        String hashedPassword = hashPasswordWithSalt(password, storedSalt);

        return users.containsKey(username) && storedHashedPassword.equals(hashedPassword);
    }

    public String getRole(String username) {
        return roles.get(username);
    }

    private String hashPassword(String password) throws Exception {
        String salt = generateSalt();
        String saltedPassword = salt + password;

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(saltedPassword.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString() + ":" + salt; // Store the hash and the salt together
    }

    private String hashPasswordWithSalt(String password, String salt) throws Exception {
        String saltedPassword = salt + password;

        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(saltedPassword.getBytes("UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    private static String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);

        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    // Save user data to a file
    private void saveUsers() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_DATA_FILE))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                String username = entry.getKey();
                String password = entry.getValue();
                String role = roles.get(username);
                writer.write(username + "," + password + "," + role);
                writer.newLine();
            }
        }
    }

    // Load user data from a file
    private void loadUsers() throws IOException {
        File file = new File(USER_DATA_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String username = parts[0];
                        String password = parts[1];
                        String role = parts[2];
                        users.put(username, password);
                        roles.put(username, role);
                    }
                }
            }
        }
    }
}
