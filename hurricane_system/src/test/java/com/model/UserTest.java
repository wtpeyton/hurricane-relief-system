package com.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.UUID;

/**
 * Tests user creation, permissions, and credentials.
 * @author Olivia Casper
 */
public class UserTest {

    private User user;

    /**
     * Creates a user before each test.
     */
    @Before
    public void setUp() {
        user = new User(
            "Amy", "Smith", "password123",
            "8035554321", "29201",
            new double[] {34.0007, -81.0348}
        );
    }

    /**
     * Tests creating a new user.
     */
    @Test
    public void testUserCreation() {
        assertNotNull(user.getUserId());
    }

    /**
     * Tests that a new user has no permissions.
     */
    @Test
    public void testInitialPermissions() {
        assertTrue(user.getPermissions().isEmpty());
    }

    /**
     * Tests adding a permission to a user.
     */
    @Test
    public void testAddPermission() {
        assertTrue(user.addPermission(Permission.VOLUNTEER));
    }

    /**
     * Tests checking a permission that a user has.
     */
    @Test
    public void testHasPermission() {
        user.addPermission(Permission.VOLUNTEER);

        assertTrue(user.hasPermission(Permission.VOLUNTEER));
    }

    /**
     * Tests checking a permission that a user does not have.
     */
    @Test
    public void testMissingPermission() {
        assertFalse(user.hasPermission(Permission.VOLUNTEER));
    }

    /**
     * Tests adding the same permission twice.
     */
    @Test
    public void testDuplicatePermission() {
        user.addPermission(Permission.VOLUNTEER);

        assertFalse(user.addPermission(Permission.VOLUNTEER));
    }

    /**
     * Tests removing an existing permission.
     */
    @Test
    public void testRemovePermission() {
        user.addPermission(Permission.VOLUNTEER);

        assertTrue(user.removePermission(Permission.VOLUNTEER));
    }

    /**
     * Tests removing a permission that the user does not have.
     */
    @Test
    public void testRemoveMissingPermission() {
        assertFalse(user.removePermission(Permission.VOLUNTEER));
    }

    /**
     * Tests that a new user has no credentials.
     */
    @Test
    public void testInitialCredentials() {
        assertTrue(user.getCredentials().isEmpty());
    }

    /**
     * Tests adding a credential to a user.
     */
    @Test
    public void testAddCredential() {
        assertTrue(user.addCredential(Credential.REGISTERED_NURSE));
    }

    /**
     * Tests adding the same credential twice.
     */
    @Test
    public void testDuplicateCredential() {
        user.addCredential(Credential.REGISTERED_NURSE);

        assertFalse(user.addCredential(Credential.REGISTERED_NURSE));
    }

    /**
     * Tests removing an existing credential.
     */
    @Test
    public void testRemoveCredential() {
        user.addCredential(Credential.REGISTERED_NURSE);

        assertTrue(user.removeCredential(Credential.REGISTERED_NURSE));
    }

    /**
     * Tests removing a credential that the user does not have.
     */
    @Test
    public void testRemoveMissingCredential() {
        assertFalse(user.removeCredential(Credential.REGISTERED_NURSE));
    }

    /**
     * Tests creating a user with existing information.
     */
    @Test
    public void testExistingUserCreation() {
        UUID id = UUID.randomUUID();

        User existingUser = new User(
            id,
            new double[] {34.0007, -81.0348},
            "29201",
            "Amy",
            "Smith",
            "password123",
            "8035554321",
            EnumSet.noneOf(Permission.class),
            EnumSet.noneOf(Credential.class),
            new ArrayList<ReliefResource>()
        );

        assertEquals(id, existingUser.getUserId());
    }

    /**
     * Tests the user's string representation.
     */
    @Test
    public void testToString() {
        String expected = user.getUserId()
            + ", Amy Smith, 8035554321, 29201";

        assertEquals(expected, user.toString());
    }
}
