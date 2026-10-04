package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a list of users in the hurricane relief system.
 * @author Olivia Casper
 */
public class UserList {
    private static UserList instance;
    private ArrayList<User> users;

    /**
     * Creates the user list.
     */
    private UserList() {
    }

    /**
     * Returns the singleton instance of UserList.
     * @return the singleton UserList instance
     */
    public static UserList getInstance() {
        return null;
    }

    /**
     * Gets a user by their unique identifier.
     * @param userId the user's unique identifier
     * @return the user with the matching ID
     */
    public User getUser(UUID userId) {
        return null;
    }

    /**
     * Gets a user using their phone number and password.
     * @param phoneNumber the user's phone number
     * @param password the user's password
     * @return the user with the matching login information
     */
    public User getUserForLogin(String phoneNumber, String password) {
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
     * @return the newly created user
     */
    public User addUser(String firstName, String lastName, String password,
                        String phoneNumber, String locationZip, double[] location) {
        return null;
    }

    /**
     * Adds an existing user to the system.
     * @param user the user to add
     * @return true if the user was added successfully, false otherwise
     */
    public boolean addUser(User user) {
        return false;
    }

    /**
     * Saves the users in the system
     * @return true if the users were saved successfully, false otherwise
     */
    public boolean saveUsers() {
        return false;
    }

    /**
     * Removes a user from the system.
     * @param user the user to remove
     * @return true if the user was removed successfully, false otherwise
     */
    public boolean removeUser(User user) {
        return false;
    }

    /**
     * Checks if a user exists with the given phone number.
     * @param phoneNumber the user's phone number to check
     * @return true if the user exists, false otherwise
     */
    public boolean doesUserExist(String phoneNumber) {
        return false;
    }
}
