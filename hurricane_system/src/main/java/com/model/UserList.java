package com.model;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Iterator;

/**
 * Represents a list of users in the hurricane relief system.
 * @author Olivia Casper, Jack Andrin
 */
public class UserList implements Iterable<User> {
    private static UserList instance;
    private ArrayList<User> users;

    /**
     * Creates the user list and loads the users from the data loader.
     */
    private UserList() {
        users = DataLoader.getUsers();
    }

    /**
     * Returns the singleton instance of UserList.
     * @return the singleton UserList instance
     */
    public static UserList getInstance() {
        if (instance == null) {
            instance = new UserList();
        }
        return instance;
    }

    /**
     * Gets a user by their unique identifier.
     * @param userId the user's unique identifier
     * @return the user with the matching ID, or null if no user is found
     */
    public User getUser(UUID userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets a user using their phone number and password.
     * @param phoneNumber the user's phone number
     * @param password the user's password
     * @return the user with the matching login information, or null if no user is found
     */
    public User getUser(String phoneNumber, String password) {
        for (User user : users) {
            if (user.getPhoneNumber().equals(phoneNumber) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Adds a new user to the list/system.
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param password the user's password
     * @param phoneNumber the user's phone number
     * @param locationZip the user's location zip code
     * @param location the user's location as a double array [latitude, longitude]
     * @return the newly created user, or null if the account information is invalid
     */
    public User addUser(String firstName, String lastName, String password,
                        String phoneNumber, String locationZip, double[] location) {

        if (firstName.isEmpty() || lastName.isEmpty() || password.isEmpty() || phoneNumber.length() != 10 || doesUserExist(phoneNumber)) {
            return null;
        }
        
        User user = new User(firstName, lastName, password, phoneNumber, locationZip, location);
        users.add(user);
        return user;
    }

    /**
     * Adds an existing user to the system.
     * @param user the user to add
     * @return true if the user was added successfully, false otherwise
     */
    public boolean addUser(User user) {
        return users.add(user);
    }

    /**
     * Saves the users in the system
     * @return true if the users were saved successfully, false otherwise
     */
    public boolean saveUsers() {
        return DataWriter.saveUsers(users);
    }

    /**
     * Removes a user from the system.
     * @param user the user to remove
     * @return true if the user was removed successfully, false otherwise
     */
    public boolean removeUser(User user) {
        return users.remove(user);
    }

    /**
     * Checks if a user exists with the given phone number.
     * @param phoneNumber the user's phone number to check
     * @return true if the user exists, false otherwise
     */
    public boolean doesUserExist(String phoneNumber) {
        for (User user : users) {
            if (user.getPhoneNumber().equals(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns an iterator over the users in the system.
     *
     * @return an Iterator of User objects
     */
    @Override
    public Iterator<User> iterator() {
        return users.iterator();
    }

    public static void main(String[] args) {
        UserList userList = UserList.getInstance();

        // Create a user for testing
        User testUser = new User(
            "Amy",
            "Smith",
            "password123",
            "8035551234",
            "29201",
            new double[]{34.0007, -81.0348}
        );

        userList.addUser(testUser);

        // Test correct login
        System.out.println("Correct login:");
        System.out.println(userList.getUser("8035551234", "password123"));

        // Test incorrect password
        System.out.println("Incorrect password:");
        System.out.println(userList.getUser("8035551234", "wrongpassword"));

        // Test unknown phone number
        System.out.println("Unknown user:");
        System.out.println(userList.getUser("9999999999", "password123"));

        // Test the other overloaded getUser method
        System.out.println("Find by ID:");
        System.out.println(userList.getUser(testUser.getUserId()));

        // Test adding a new user
        System.out.println("Add new user:");
        User newUser = userList.addUser(
            "John",
            "Doe",
            "password456",
            "8035555678",
            "29205",
            new double[]{34.0090, -81.0281}
        );
        System.out.println(newUser);

        // Test adding a user with a duplicate phone number
        System.out.println("Add duplicate phone number:");
        User duplicateUser = userList.addUser(
            "Jane",
            "Doe",
            "password789",
            "8035555678",
            "29208",
            new double[]{34.0200, -81.0100}
        );
        System.out.println(duplicateUser);

        // Test adding a user with an empty first name
        System.out.println("Add user with empty first name:");
        User emptyName = userList.addUser(
                "",
                "Jones",
                "password123",
                "8035559999",
                "29201",
            new double[]{34.0007, -81.0348}
        );
        System.out.println(emptyName);

        // Test adding a user with an invalid phone number
        System.out.println("Add user with invalid phone number:");
        User invalidPhone = userList.addUser(
            "Sam",
            "Jones",
            "password123",
            "803555",
            "29201",
            new double[]{34.0007, -81.0348}
        );
        System.out.println(invalidPhone);
    }
}
