package com.win.junit;


import org.example.login.UserLogin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class UserLoginTest {
    private UserLogin userLogin;

    @BeforeEach
    void setUp() {
        // Initialize the UserLogin object and clear the users.txt file before each test
        userLogin = new UserLogin();
        File file = new File("users.txt");
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testAddUser() throws Exception {
        userLogin.addUser("user1", "password1", "admin");
        assertTrue(userLogin.login("user1", "password1"));
        assertEquals("admin", userLogin.getRole("user1"));
    }

    @Test
    void testLoginWithCorrectCredentials() throws Exception {
        userLogin.addUser("user2", "password2", "user");
        assertTrue(userLogin.login("user2", "password2"));
    }

    @Test
    void testLoginWithIncorrectPassword() throws Exception {
        userLogin.addUser("user3", "password3", "user");
        assertFalse(userLogin.login("user3", "wrongpassword"));
    }

    @Test
    void testLoginWithNonExistentUser() throws Exception {
        assertFalse(userLogin.login("nonexistentuser", "password"));
    }

    @Test
    void testGetRole() throws Exception {
        userLogin.addUser("user4", "password4", "editor");
        assertEquals("editor", userLogin.getRole("user4"));
    }

    @Test
    void testSaveAndLoadUsers() throws Exception {
        userLogin.addUser("user5", "password5", "viewer");
        userLogin = new UserLogin(); // Re-instantiate to trigger load from file

        assertTrue(userLogin.login("user5", "password5"));
        assertEquals("viewer", userLogin.getRole("user5"));
    }

    @Test
    void testHashPassword() throws Exception {
        String hashedPassword = userLogin.hashPassword("testPassword");
        assertNotNull(hashedPassword);
        assertTrue(hashedPassword.contains(":")); // Check that it contains the salt separator
    }

    @Test
    void testHashPasswordWithSalt() throws Exception {
        String salt = "1234567890abcdef";
        String hashedPassword = userLogin.hashPasswordWithSalt("testPassword", salt);
        assertNotNull(hashedPassword);
        assertEquals(64, hashedPassword.length()); // SHA-256 produces a 64-character hash
    }
}
