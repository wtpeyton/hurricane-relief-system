package com.model;

import org.junit.Test;

import com.location.Address;
import static org.junit.Assert.*;
import java.util.UUID;

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
            "8035554321", new Address("1600 Hampton Street", "Columbia", "SC", "29201")
        );

        users.addUser(testUser);

        try {
            User result = users.getUser("8035554321", "password123");

            assertEquals(testUser, result);
        } finally {
            users.removeUser(testUser);
        }
    }

    /**
     * Tests logging in with an incorrect password.
     */
    @Test
    public void testInvalidPassword() {
        UserList users = UserList.getInstance();

        User testUser = new User(
            "Amy", "Smith", "password123",
            "8035554321", new Address("1600 Hampton Street", "Columbia", "SC", "29201")
        );

        users.addUser(testUser);

        try {
            User result = users.getUser("8035554321", "wrongpassword");

            assertNull(result);
        } finally {
            users.removeUser(testUser);
        }
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
            "8035554321", new Address("1600 Hampton Street", "Columbia", "SC", "29201")
        );

        users.addUser(testUser);

        try {
            User result = users.getUser(testUser.getUserId());

            assertEquals(testUser, result);
        } finally {
            users.removeUser(testUser);
        }
    }

    /**
     * Tests looking up a user ID that does not exist.
     */
    @Test
    public void testUnknownUserId() {
        UserList users = UserList.getInstance();

        User result = users.getUser(UUID.randomUUID());

        assertNull(result);
    }

    /**
     * Tests creating an account with valid user information.
     */
    @Test
    public void testValidAccountCreation() {
        UserList users = UserList.getInstance();
        User newUser = null;

        try {
            newUser = users.addUser(
                "Jane",
                "Doe",
                "password123",
                "8035559876",
                new Address("1600 Hampton Street", "Columbia", "SC", "29201")
            );

            assertNotNull(newUser);
        } finally {
            if (newUser != null) {
                users.removeUser(newUser);
            }
        }
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
            new Address("1600 Hampton Street", "Columbia", "SC", "29201")
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
            new Address("1600 Hampton Street", "Columbia", "SC", "29201")
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
            new Address("1600 Hampton Street", "Columbia", "SC", "29201")
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
            new Address("1600 Hampton Street", "Columbia", "SC", "29201")
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
            new Address("1600 Hampton Street", "Columbia", "SC", "292")
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
            "8035559876", new Address("1600 Hampton Street", "Columbia", "SC", "29201")
        );

        users.addUser(firstUser);

        try {
            User duplicateUser = users.addUser(
                "John",
                "Smith",
                "password456",
                "8035559876",
                new Address("1600 Hampton Street", "Columbia", "SC", "29205")
            );

            assertNull(duplicateUser);
        } finally {
            users.removeUser(firstUser);
        }
    }

    /**
     * Tests checking whether a registered phone number exists.
     */
    @Test
    public void testDoesUserExist() {
        UserList users = UserList.getInstance();

        User testUser = new User(
            "Amy", "Smith", "password123",
            "8035554321", new Address("1600 Hampton Street", "Columbia", "SC", "29201")
        );

        users.addUser(testUser);

        try {
            assertTrue(users.doesUserExist("8035554321"));
        } finally {
            users.removeUser(testUser);
        }
    }

    /**
     * Tests creating an account with letters in the phone number.
     */
    @Test
    public void testNonNumericPhoneNumber() {
        UserList users = UserList.getInstance();

        User newUser = users.addUser(
            "Jane",
            "Doe",
            "password123",
            "803555ABCD",
            new Address("1600 Hampton Street", "Columbia", "SC", "29201")
        );

        assertNull(newUser);
    }

    /**
     * Tests creating an account with letters in the ZIP code.
     */
    @Test
    public void testNonNumericZipCode() {
        UserList users = UserList.getInstance();

        User newUser = users.addUser(
            "Jane",
            "Doe",
            "password123",
            "8035559876",
            new Address("1600 Hampton Street", "Columbia", "SC", "292AB")
        );

        assertNull(newUser);
    }

    /**
     * Tests iterating through the user list.
     */
    @Test
    public void testIterator() {
        UserList users = UserList.getInstance();

        User testUser = new User(
            "Amy", "Smith", "password123",
            "8035554321", new Address("1600 Hampton Street", "Columbia", "SC", "29201")
        );

        users.addUser(testUser);

        try {
            boolean found = false;

            for (User user : users) {
                if (user == testUser) {
                    found = true;
                }
            }

            assertTrue(found);
        } finally {
            users.removeUser(testUser);
        }
    }
}