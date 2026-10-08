package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a hurricane with its details and status.
 * Provides methods to check if the hurricane is active and which areas are affected.
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
     * Returns the unique ID of the hurricane.
     *
     * @return the hurricane's UUID
     */
    public UUID getHurricaneId() {
        return hurricaneId;
    }

    /**
     * Returns the name of the hurricane.
     *
     * @return the hurricane's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the list of zip codes affected by the hurricane.
     *
     * @return the list of affected zip codes
     */
    public ArrayList<String> getAffectedZipCodes() {
        return affectedZipCodes;
    }

    /**
     * Returns the current status of the hurricane.
     *
     * @return the hurricane's status
     */
    public HurricaneStatus getStatus() {
        return status;
    }

    /**
     * Sets the current status of the hurricane.
     *
     * @param status the new status of the hurricane
     */
    public void setStatus(HurricaneStatus status) {
        this.status = status;
    }

    /**
     * Sets whether the hurricane is currently active.
     *
     * @param active true if the hurricane is active, false otherwise
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Sets the name of the hurricane.
     *
     * @param name the new name of the hurricane
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the list of zip codes affected by the hurricane.
     *
     * @param affectedZipCodes the new list of affected zip codes
     */
    public void setAffectedZipCodes(ArrayList<String> affectedZipCodes) {
        this.affectedZipCodes = affectedZipCodes;
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
     * Saves the current state of the hurricane to persistent storage.
     *
     * @return true if the save operation is successful, false otherwise
     */
    public boolean saveHurricane() {
        return DataWriter.saveHurricane(this);
    }
}
