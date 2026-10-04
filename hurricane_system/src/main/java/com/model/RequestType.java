package com.model;
/**
 * @author Ryan Kouchoukos and Olivia Casper
 * Enum representing different types of requests in the hurricane relief system.
 */
public enum RequestType {
    MEDICAL_EMERGENCY(11),
    EVACUATION_ASSISTANCE(7),
    MISSING_PERSON(8),
    RESCUE_TRAPPED(10),
    SHELTER_ASSISTANCE(6),
    IMMEDIATE_SAFETY_HAZARD(9),
    ESSENTIAL_SUPPLIES(5),
    COMMUNITY_CLEANUP(1),
    ANIMAL_PET_ASSISTANCE(4),
    RECOVERY_ASSISTANCE(2),
    EMOTIONAL_SUPPORT(3);

    public final int priority;
    /**
     * Creates a request type with a priority
     * @param priority the priority value of the request type
     */
    RequestType(int priority) {
        this.priority = priority;
    }
}
