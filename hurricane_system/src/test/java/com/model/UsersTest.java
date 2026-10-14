package com.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests the login, user lookup, and account validation in UserList
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

    /**
     * Tests creating an account with valid user information.
     */
    @Test
    public void testValidAccountCreation() {
        UserList users = UserList.getInstance();

        User newUser = users.addUser(
            "Jane",
            "Doe",
            "password123",
            "8035559876",
            "29201",
            new double[] {34.0007, -81.0348}
        );

        if (newUser != null) {
            users.removeUser(newUser);
        }

        assertNotNull(newUser);
    }

    /**
     * Tests creating an account without a first name.
     */
    @Test
    public void testMissingFirstName() {
        UserList users = UserList.getInstance();

        User newUser = users.addUser(
            "",
            "Doe",
            "password123",
            "8035559876",
            "29201",
            new double[] {34.0007, -81.0348}
        );

        assertNull(newUser);
    }

    /**
     * Tests creating an account without a last name.
     */
    @Test
    public void testMissingLastName() {
        UserList users = UserList.getInstance();

        User newUser = users.addUser(
            "Jane",
            "",
            "password123",
            "8035559876",
            "29201",
            new double[] {34.0007, -81.0348}
        );

        assertNull(newUser);
    }

    /**
     * Tests creating an account without a password.
     */
    @Test
    public void testMissingPassword() {
        UserList users = UserList.getInstance();

        User newUser = users.addUser(
            "Jane",
            "Doe",
            "",
            "8035559876",
            "29201",
            new double[] {34.0007, -81.0348}
        );

        assertNull(newUser);
    }

    /**
     * Tests creating an account with an invalid phone number.
     */
    @Test
    public void testInvalidPhoneNumber() {
        UserList users = UserList.getInstance();

        User newUser = users.addUser(
            "Jane",
            "Doe",
            "password123",
            "803555",
            "29201",
            new double[] {34.0007, -81.0348}
        );

        assertNull(newUser);
    }

    /**
     * Tests creating an account with an invalid ZIP code.
     */
    @Test
    public void testInvalidZipCode() {
        UserList users = UserList.getInstance();

        User newUser = users.addUser(
            "Jane",
            "Doe",
            "password123",
            "8035559876",
            "292",
            new double[] {34.0007, -81.0348}
        );

        assertNull(newUser);
    }

    /**
     * Tests creating an account with a phone number that already exists.
     */
    @Test
    public void testDuplicatePhoneNumber() {
        UserList users = UserList.getInstance();

        User firstUser = new User(
            "Jane", "Doe", "password123",
            "8035559876", "29201",
            new double[] {34.0007, -81.0348}
        );

        users.addUser(firstUser);

        try {
            User duplicateUser = users.addUser(
                "John",
                "Smith",
                "password456",
                "8035559876",
                "29205",
                new double[] {34.0007, -81.0348}
            );

            assertNull(duplicateUser);
        } finally {
            users.removeUser(firstUser);
        }
    }
}