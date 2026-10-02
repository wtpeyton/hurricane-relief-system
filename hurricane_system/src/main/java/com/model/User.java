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
}
