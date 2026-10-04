package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a user in the hurricane relief system.
 * @author Olivia Casper
 */
public class User {
    private UUID userId;
    private double[] location;
    private String locationZip;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private ArrayList<Permission> permissions;
    private ArrayList<Credential> credentials;
    private ArrayList<ReliefResource> equipment;

    /**
     * Creates a new user
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param password the user's password
     * @param phoneNumber the user's phone number
     * @param locationZip the user's location zip code
     * @param location the user's location as a double array [latitude, longitude]
     */
    public User(String firstName, String lastName, String password,
                String phoneNumber, String locationZip, double[] location) {
        this.userId = UUID.randomUUID();
        this.location = location;
        this.locationZip = locationZip;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.permissions = new ArrayList<Permission>();
        this.credentials = new ArrayList<Credential>();
        this.equipment = new ArrayList<ReliefResource>();
    }
    
    /**
     * Creates a user with existing user information
     * @param userId the user's unique identifier
     * @param location the user's location as a double array [latitude, longitude]
     * @param locationZip the user's location zip code
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param password the user's password
     * @param phoneNumber the user's phone number
     * @param permissions the user's list of permissions
     * @param credentials the user's list of credentials
     * @param equipment the user's list of relief resources
     */
    public User(UUID userId, double[] location, String locationZip,
                String firstName, String lastName, String password,
                String phoneNumber, ArrayList<Permission> permissions,
                ArrayList<Credential> credentials,
                ArrayList<ReliefResource> equipment) {
        this.userId = userId;
        this.location = location;
        this.locationZip = locationZip;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.permissions = permissions;
        this.credentials = credentials;
        this.equipment = equipment;
    }

    /**
     * Checks if the user has a specific permission.
     * @param permission the permission to check
     * @return true if the user has the permission, false otherwise
     */
    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    /**
     * Adds a permission to the user's list of permissions.
     * @param permission the permission to add
     * @return true if the permission was added, false otherwise
     */
    public boolean addPermission(Permission permission) {
        return permissions.add(permission);
    }

    /**
     * Removes a permission from the user's list of permissions.
     * @param permission the permission to remove
     * @return true if the permission was removed, false otherwise
     */
    public boolean removePermission(Permission permission) {
        return permissions.remove(permission);
    }

    /**
     * Adds a credential to the user's list of credentials.
     * @param credential the credential to add
     * @return true if the credential was added, false otherwise
     */
    public boolean addCredential(Credential credential) {
        return credentials.add(credential);
    }

    /**
     * Removes a credential from the user's list of credentials.
     * @param credential the credential to remove
     * @return true if the credential was removed, false otherwise
     */
    public boolean removeCredential(Credential credential) {
        return credentials.remove(credential);
    }
    
    /**
     * Returns the user's unique identifier.
     * @return the user's unique identifier
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Returns the user's location.
     * @return the user's location
     */
    public double[] getLocation() {
        return location;
    }

    /**
     * Returns the user's location zip code.
     * @return the user's location zip code
     */
    public String getLocationZip() {
        return locationZip;
    }

    /**
     * Returns the user's first name.
     * @return the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the user's last name.
     * @return the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the user's password.
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's phone number.
     * @return the user's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Returns the user's permissions.
     * @return the user's permissions
     */
    public ArrayList<Permission> getPermissions() {
        return permissions;
    }

    /**
     * Returns the user's credentials.
     * @return the user's credentials
     */
    public ArrayList<Credential> getCredentials() {
        return credentials;
    }

    /**
     * Returns the user's equipment.
     * @return the user's equipment
     */
    public ArrayList<ReliefResource> getEquipment() {
        return equipment;
    }

    /**
     * Returns the user's information as a string.
     * @return the user's information
     */
    @Override
    public String toString() {
        return userId + ", " + firstName + " " + lastName + ", " + phoneNumber + ", " + locationZip;
    }
}
