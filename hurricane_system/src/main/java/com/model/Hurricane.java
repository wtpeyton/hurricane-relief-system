package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * [PARTIAL STUB] Represents a hurricane with its details and status.
 * Provides methods to check if the hurricane is active and to notify affected users.
 *
 * @author Jack Andrin
 */
public class Hurricane {
    private UUID hurricaneId;
    private String name;
    private ArrayList<String> affectedZipCodes;
    private HurricaneStatus status;
    private boolean active;

    /**
     * Constructs a new Hurricane with the specified details.
     *
     * @param name the name of the hurricane
     * @param affectedZipCodes the list of affected zip codes
     * @param status the current status of the hurricane
     * @param active whether the hurricane is currently active
     */
    public Hurricane(String name, ArrayList<String> affectedZipCodes, HurricaneStatus status, boolean active) {
        this(UUID.randomUUID(), name, affectedZipCodes, status, active);
    }

    /**
     * Constructs a new Hurricane with the specified details, including a unique hurricane ID.
     *
     * @param hurricaneId the unique ID of the hurricane
     * @param name the name of the hurricane
     * @param affectedZipCodes the list of affected zip codes
     * @param currentStatus the current status of the hurricane
     * @param active whether the hurricane is currently active
     */
    public Hurricane(UUID hurricaneId, String name, ArrayList<String> affectedZipCodes, HurricaneStatus currentStatus, boolean active) {
        this.hurricaneId = hurricaneId;
        this.name = name;
        this.affectedZipCodes = affectedZipCodes;
        this.status = currentStatus;
        this.active = active;
    }

    /**
     * Returns whether the hurricane is currently active.
     *
     * @return true if the hurricane is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * [STUB] Notifies users in the affected zip codes about the hurricane.
     */
    public void notifyAffectedUsers() {
        
    }
}
