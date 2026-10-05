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
     * Creates the request list and loads the requests from the data loader.
     */
    private RequestList() {
        requests = DataLoader.getRequests();
    }

    /**
     * Returns the singleton instance of RequestList
     * @return the singleton RequestList instance
     */
    public static RequestList getInstance() {
        if (instance == null) {
            instance = new RequestList();
        }
        return instance;
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
    public Request addRequest(ArrayList<RequestType> requestType, User requester, 
                              boolean forSomeoneElse, double[] location, String description) {
        Request request = new Request(requestType, requester, forSomeoneElse, location, description);
        requests.add(request);
        return request;
    }

    /**
     * Adds an existing request to the system
     * @param request the request to add
     * @return true if the request was added successfully, false otherwise
     */
    public boolean addRequest(Request request) {
        return requests.add(request);
    }

    /**
     * Removes a request from the system
     * @param request the request to remove
     * @return true if the request was removed successfully, false otherwise
     */
    public boolean removeRequest(Request request) {
        return requests.remove(request);
    }

    /**
     * Updates an existing request in the system
     * @param request the request to update
     * @return true if the request was updated successfully, false otherwise
     */
    public boolean updateRequest(Request request) {
        // Find the existing request with the same ID and replace it
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getId().equals(request.getId())) {
                requests.set(i, request);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a request by its unique identifier
     * @param requestId the unique identifier of the request
     * @return the request with the matching ID
     */
    public Request getRequestById(UUID requestId) {
        for (Request request : requests) {
            if (request.getId().equals(requestId)) {
                return request;
            }
        }
        return null;
    }

    /**
     * Finds requests ordered by distance from a location
     * @param location the location to search from
     * @return the requests ordered from closest to farthest
     */
    public ArrayList<Request> findRequestsByDistance(double[] location) {
        ArrayList<Request> remaining = new ArrayList<Request>(requests);
        ArrayList<Request> results = new ArrayList<Request>();

        // Find the closest remaining request until all requests are ordered
        while (!remaining.isEmpty()) {
            Request closestRequest = remaining.get(0);
            double[] closestLocation = closestRequest.getLocation();

            // Calculate the distance from the given location
            double closestDistance = Math.sqrt(
                Math.pow(closestLocation[0] - location[0], 2)
                + Math.pow(closestLocation[1] - location[1], 2)
            );

            // Check the remaining requests for one that is closer
            for (Request request : remaining) {
                double[] requestLocation = request.getLocation();

                double distance = Math.sqrt(
                    Math.pow(requestLocation[0] - location[0], 2)
                    + Math.pow(requestLocation[1] - location[1], 2)
                );

                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestRequest = request;
                }
            }

            // Add the closest request and remove it from the remaining requests
            results.add(closestRequest);
            remaining.remove(closestRequest);
        }

        return results;
    }

    /**
     * Finds requests containing a specific request type
     * @param type the request type to search for
     * @return the requests containing the specified request type
     */
    public ArrayList<Request> findRequestsByType(RequestType type) {
        ArrayList<Request> results = new ArrayList<Request>();
        
        for (Request request : requests) {
            if (request.getRequestType().contains(type)) {
                results.add(request);
            }
        }
        return results;
    }

    /**
     * Finds requests submitted by a user
     * @param user the user who submitted the requests
     * @return the requests submitted by the user
     */
    public ArrayList<Request> findRequestsByUser(User user) {
        ArrayList<Request> results = new ArrayList<Request>();
        
        for (Request request : requests) {
            if (request.getRequester().equals(user)) {
                results.add(request);
            }
        }
        return results;
    }

    /**
     * Finds requests by their status
     * @param status the request status to search for
     * @return the requests with the specified status
     */
    public ArrayList<Request> findRequestsByStatus(RequestStatus status) {
        ArrayList<Request> results = new ArrayList<Request>();
        
        for (Request request : requests) {
            if (request.getStatus() == status) {
                results.add(request);
            }
        }
        return results;
    }

    /**
     * Finds requests available for a volunteer to accept/respond to
     * @param volunteer the volunteer user
     * @return the requests available for the volunteer to respond to
     */
    public ArrayList<Request> findVolunteerRequests(User volunteer) {
        ArrayList<Request> results = new ArrayList<Request>();
        
        for (Request request : requests) {
            if (request.getStatus() == RequestStatus.SUBMITTED) {
                results.add(request);
            }
        }
        return results;
    }

    /**
     * Saves the requests in the system
     * @return true if the requests were saved successfully, false otherwise
     */
    public boolean saveRequests() {
        return DataWriter.saveRequests(requests);
    }

    /**
     * Returns an iterator over the requests in the system
     * @return an iterator over the requests
     */
    @Override
    public Iterator<Request> iterator() {
        return requests.iterator();
    }
}
