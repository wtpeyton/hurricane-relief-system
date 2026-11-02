package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;

import com.location.Address;
import com.location.MapTools;

import java.util.Iterator;
import java.util.EnumSet;

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
    public ArrayList<Request> getRequests(){
        return requests;
    }

    /**
     * Adds a new request to the system
     * @param requestType the type(s) of relief request
     * @param requester the user who submitted the request
     * @param forSomeoneElse whether the request is for someone else
     * @param address the address of the request
     * @param description a description of the request
     * @return the newly added request
     */
    public Request addRequest(EnumSet<RequestType> requestType, User requester, 
                              boolean forSomeoneElse, Address address, String description) {
        Request request = new Request(requestType, requester, forSomeoneElse, address, description);
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
     * Gets the ArrayList of Requests based on the search criteria
     * @param requestId searches by the request id
     * @param address searches by the address
     * @param type search by the type of the request
     * @param user search by the submission of the request or the requests the volunteer can respond to
     * @param status search by the status of the request
     * @param volunter searches the request based on if the volunteer is qualified to assist
     * @return an ArrayList of filtered requests
     */
    public ArrayList<Request> searchRequest(UUID requestId, Address address, RequestType type, User user, RequestStatus status, User volunteer, boolean ascending){ //Refer to Tristan for volunteerOrUser explanation it is to avoid same type parameter
        ArrayList<Request> results = new ArrayList<Request>(requests);
        if(volunteer != null){ //Removes the requests that arent submitted status
            results = findVolunteerRequests(volunteer, results);
        }
        if(address != null){ //Removes the requests based on the address requirements
            results = findRequestsByDistance(address, results);
        }
        if(type != null){ //Removes the requests based on the type
            results = findRequestsByType(type, results);
        }
        if(status != null){ //Removes the requests based on status
            results = findRequestsByStatus(status, results);
        }
        if(user != null){ //Removes the requests based on user submission
            results = findRequestsByUser(user, results);
        }
        if(ascending){
            return results;
        }
        else if(!ascending){
            ArrayList<Request> reversedResults = new ArrayList<Request>();
            for(int i = results.size() - 1; i >= 0; i--){
                reversedResults.add(results.get(i));
            }
            return reversedResults;
        }
        return null;
    }

    /**
     * Gets a request by its unique identifier
     * @param requestId the unique identifier of the request
     * @return the request with the matching ID
     */
    public Request getRequestById(UUID requestId, ArrayList<Request> requests) {
        for (Request request : requests) {
            if (request.getId().equals(requestId)) {
                return request;
            }
        }
        return null;
    }

    /**
     * Finds requests ordered by distance from a address
     * @param address the address to search from
     * @return the requests ordered from closest to farthest
     */
    public ArrayList<Request> findRequestsByDistance(Address address, ArrayList<Request> requests) {
        ArrayList<Request> remaining = new ArrayList<Request>(requests);
        ArrayList<Request> results = new ArrayList<Request>();

    try {
        while (!remaining.isEmpty()) {
            Address[] addresses = new Address[remaining.size()];
            for (int i = 0; i < remaining.size(); i++) {
                addresses[i] = remaining.get(i).getAddress();
            }

            Address closest = MapTools.getInstance().findClosestAddress(address, addresses);

            Request closestRequest = remaining.get(0);
            for (Request request : remaining) {
                if (request.getAddress() == closest) {
                    closestRequest = request;
                }
            }

            // Add the closest request and remove it from the remaining requests
            results.add(closestRequest);
            remaining.remove(closestRequest);
        }
    } catch (Exception e) {
        e.printStackTrace();
        results.addAll(remaining);
    }

        return results;
    }

    /**
     * Finds requests containing a specific request type
     * @param type the request type to search for
     * @return the requests containing the specified request type
     */
    public ArrayList<Request> findRequestsByType(RequestType type, ArrayList<Request> requests) {
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
    public ArrayList<Request> findRequestsByUser(User user, ArrayList<Request> requests) {
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
    public ArrayList<Request> findRequestsByStatus(RequestStatus status, ArrayList<Request> requests) {
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
    public ArrayList<Request> findVolunteerRequests(User volunteer, ArrayList<Request> requests) {
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

    public void getRequestPrintOut(User volunteer, Request request, ShelterList shelterList){
        try {
            String fileName = volunteer.getFirstName() + volunteer.getLastName() + "output.txt";
            String[] userToRequest = MapTools.getDrivingDirections(volunteer.getAddress(), request.getAddress());
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            writer.println(request.getId());
            writer.println(request.getRequester().getFirstName() + " " + request.getRequester().getLastName());
            writer.println(request.getRequestType());
            writer.println(request.getAddress());
            writer.println(request.getStatus());
            writer.println(request.getPriority());
            writer.println(request.getDescription());
            writer.println(request.getComment());
            writer.println(request.getCreatedAt());
            writer.println(request.getPeople().size());
            writer.println("Directions to Request");
            for (String string : userToRequest) {
                writer.println(string);
            }
            ArrayList<Shelter> shelters = shelterList.filterShelters(shelterList.findSheltersByDistance(request.getAddress()), request);
            if(shelters.isEmpty()){
                writer.println("No suitable shelters");
            } else {
                Shelter shelter = shelters.get(0);
            String[] requestToShelter = MapTools.getInstance().getDrivingDirections(request.getAddress(), shelter.getAddress());
            writer.println("Shelter: " + shelter.getName());
            writer.println(shelter.getAddress());
            writer.println("Directions to Shelter");
            for (String step : requestToShelter) {
                writer.println(step);
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
