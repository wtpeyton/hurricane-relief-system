package com.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Represents a relief request in the hurricane relief system.
 * @author Olivia Casper
 */
public class Request {
    private UUID id;
    private User requester;
    private ArrayList<User> responders;
    private Instant createdAt;
    private int priority;
    private ArrayList<RequestType> requestType;
    private double[] location;
    private String description;
    private RequestStatus status;
    private String comment;
    private boolean forSomeoneElse;

    /**
     * Creates a new relief request
     * @param requestType the type of relief request
     * @param requester the user who submitted the request
     * @param forSomeoneElse whether the request is for someone else
     * @param location the location of the request as a double array [latitude, longitude]
     * @param description a description of the request
     */
    public Request(ArrayList<RequestType> requestType, User requester, boolean forSomeoneElse, double[] location, String description) {
        this.id = UUID.randomUUID();
        this.requester = requester;
        this.responders = new ArrayList<User>();
        this.createdAt = Instant.now();
        this.priority = 0;

        for(RequestType type : requestType) {
            this.priority += type.priority;
        }
        
        this.requestType = requestType;
        this.location = location;
        this.description = description;
        this.status = RequestStatus.SUBMITTED; // Default status
        this.comment = "";
        this.forSomeoneElse = forSomeoneElse;
    }

    /**
     * Creates a relief request with existing information
     * @param id the unique identifier of the request
     * @param requester the user who submitted the request
     * @param responders the users responding to the request
     * @param createdAt the timestamp when the request was created
     * @param priority the priority level of the request
     * @param requestType the type of relief request
     * @param location the location of the request as a double array [latitude, longitude]
     * @param description a description of the request
     * @param status the current status of the request
     * @param comment any additional comments related to the request
     * @param forSomeoneElse whether the request is for someone else
     */
    public Request(UUID id, User requester, ArrayList<User> responders, Instant createdAt, int  priority, ArrayList<RequestType> requestType, 
        double[] location, String description, RequestStatus status, String comment, boolean forSomeoneElse) {  
        this.id = id;
        this.requester = requester;
        this.responders = responders;
        this.createdAt = createdAt;
        this.priority = priority;
        this.requestType = requestType;
        this.location = location;
        this.description = description;
        this.status = status;
        this.comment = comment;
        this.forSomeoneElse = forSomeoneElse;
    }

    /**
     * Checks if the request is for someone else
     * @return true if the request is for someone else, false otherwise
     */
    public boolean isForSomeoneElse() {
        return forSomeoneElse;
    }

    /**
     * Adds a responder to the request
     * @param responder the user responding to the request
     * @return the index of the added responder
     */
    public int addResponder(User responder) {
        responders.add(responder);
        return responders.indexOf(responder);
    }

    /**
     * Removes a responder from the request
     * @param responder the user to remove from the responders list
     * @return the index of the removed responder
     */
    public int removeResponder(User responder) {
        int index = responders.indexOf(responder);
        if (index != -1) {
            responders.remove(index);
        }
        return index;
    }

    /**
     * Sets the status of the request
     * @param status the new status of the request
     */
    public void setStatus(RequestStatus status) {
        this.status = status;
    }
    /**
     * Tests the Request class.
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        User requester = new User(
            "Amy", "Smith", "password123",
            "8035551234", "29201",
            new double[]{34.0007, -81.0348}
        );

        User responder = new User(
            "John", "Doe", "password456",
            "8035555678", "29205",
            new double[]{34.0190, -80.9950}
        );

        // Create request with multiple request types
        ArrayList<RequestType> requestTypes = new ArrayList<RequestType>();
        requestTypes.add(RequestType.EVACUATION_ASSISTANCE);
        requestTypes.add(RequestType.ESSENTIAL_SUPPLIES);

        Request request = new Request(
            requestTypes,
            requester,
            false,
            new double[]{34.0007, -81.0348},
            "Need help evacuating and need supplies"
        );

        // Test calculated priority
        System.out.println("Priority: " + request.priority);

        // Test whether request is for someone else
        System.out.println("For someone else: "
            + request.isForSomeoneElse());

        // Test adding responder
        System.out.println("Add responder index: "
            + request.addResponder(responder));

        // Test removing responder
        System.out.println("Remove responder index: "
            + request.removeResponder(responder));

        // Test removing responder that is no longer there
        System.out.println("Remove missing responder: "
            + request.removeResponder(responder));

        // Test changing status
        request.setStatus(RequestStatus.IN_PROGRESS);

        System.out.println("Request test complete.");
    }
}

