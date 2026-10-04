package com.model;

import java.util.ArrayList;
import java.util.UUID;
import java.util.Iterator;

/**
 * Represents a list of relief requests in the hurricane relief system.
 * @author Olivia Casper
 */
public class RequestList implements Iterable<Request> {
    private static RequestList instance;
    private ArrayList<Request> requests;

    /**
     * Creates the request list
     */
    private RequestList() {
    }

    /**
     * Returns the singleton instance of RequestList
     * @return the singleton RequestList instance
     */
    public static RequestList getInstance() {
        return null;
    }

    /**
     * Adds a new request to the system
     * @param requestType the type(s) of relief request
     * @param requester the user who submitted the request
     * @param forSomeoneElse whether the request is for someone else
     * @param location the location of the request
     * @param description a description of the request
     * @return the newly added request
     */
    public Request addRequest(ArrayList<RequestType> requestType, User requester, boolean forSomeoneElse, double[] location, String description) {
        return null;
    }

    /**
     * Adds an existing request to the system
     * @param request the request to add
     * @return true if the request was added successfully, false otherwise
     */
    public boolean addRequest(Request request) {
        return false;
    }

    /**
     * Removes a request from the system
     * @param request the request to remove
     * @return true if the request was removed successfully, false otherwise
     */
    public boolean removeRequest(Request request) {
        return false;
    }

    /**
     * Updates an existing request in the system
     * @param request the request to update
     * @return true if the request was updated successfully, false otherwise
     */
    public boolean updateRequest(Request request) {
        return false;
    }

    /**
     * Gets a request by its unique identifier
     * @param requestId the unique identifier of the request
     * @return the request with the matching ID
     */
    public Request getRequestById(UUID requestId) {
        return null;
    }

    /**
     * Finds requests near a location
     * @param location the location to search from
     * @return the requests near the location
     */
    public ArrayList<Request> findRequestsByDistance(double[] location) {
        return null;
    }

    /**
     * Finds requests containing a specific request type
     * @param type the request type to search for
     * @return the requests containing the specified request type
     */
    public ArrayList<Request> findRequestsByType(RequestType type) {
        return null;
    }

    /**
     * Finds requests submitted by a user
     * @param user the user who submitted the requests
     * @return the requests submitted by the user
     */
    public ArrayList<Request> findRequestsByUser(User user) {
        return null;
    }

    /**
     * Finds requests by their status
     * @param status the request status to search for
     * @return the requests with the specified status
     */
    public ArrayList<Request> findRequestsByStatus(RequestStatus status) {
        return null;
    }

    /**
     * Finds requests for a volunteer
     * @param volunteer the volunteer user
     * @return the requests for the volunteer to respond to
     */
    public ArrayList<Request> findVolunteerRequests(User volunteer) {
        return null;
    }

    /**
     * Saves the requests in the system
     * @return true if the requests were saved successfully, false otherwise
     */
    public boolean saveRequests() {
        return false;
    }

    /**
     * Returns an iterator over the requests in the system
     * @return an iterator over the requests
     */
    @Override
    public Iterator<Request> iterator() {
        return null;
    }
}
