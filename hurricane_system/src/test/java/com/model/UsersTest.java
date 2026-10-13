package com.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the login and user lookup methods in UserList
 * @author Olivia Casper
 */
public class UsersTest {

    /**
     * Tests logging in with a valid phone number and password.
     */
    @Test
    public void testValidLogin() {
        UserList users = UserList.getInstance();

        User testUser = new User(
            "Amy", "Smith", "password123",
            "8035551234", "29201",
            new double[] {34.0007, -81.0348}
        );

        users.addUser(testUser);

        User result = users.getUser("8035551234", "password123");

        assertNotNull(result);

        users.removeUser(testUser);
    }

    /**
     * Tests logging in with an incorrect password.
     */
    @Test
    public void testInvalidPassword() {
        UserList users = UserList.getInstance();

        User testUser = new User(
            "Amy", "Smith", "password123",
            "8035551234", "29201",
            new double[] {34.0007, -81.0348}
        );

        users.addUser(testUser);

        User result = users.getUser("8035551234", "wrongpassword");

        users.removeUser(testUser);

        assertNull(result);
    }

    /**
     * Tests logging in with a phone number that does not exist.
     */
    @Test
    public void testUnknownUser() {
        UserList users = UserList.getInstance();

        User result = users.getUser("9999999999", "password123");

        assertNull(result);
    }

    /**
     * Tests finding a user by their unique identifier.
     */
    @Test
    public void testGetUserById() {
        UserList users = UserList.getInstance();

        User testUser = new User(
            "Amy", "Smith", "password123",
            "8035551234", "29201",
            new double[] {34.0007, -81.0348}
        );

        users.addUser(testUser);

        User result = users.getUser(testUser.getUserId());

        users.removeUser(testUser);

        assertEquals(testUser, result);
    }
}