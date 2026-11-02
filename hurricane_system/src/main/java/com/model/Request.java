package com.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.UUID;

import com.location.Address;

/**
 * Represents a relief request in the hurricane relief system.
 * @author Olivia Casper, Jack Andrin
 */
public class Request {
    private UUID id;
    private User requester;
    private ArrayList<User> responders;
    private Instant createdAt;
    private int priority;
    private EnumSet<RequestType> requestType;
    private Address address;
    private String description;
    private RequestStatus status;
    private String comment;
    private boolean forSomeoneElse;
    private ArrayList<Person> people;
    private ArrayList<String> pets;

    /**
     * Creates a new relief request
     * @param requestType the type of relief request
     * @param requester the user who submitted the request
     * @param forSomeoneElse whether the request is for someone else
     * @param address the address of the request
     * @param description a description of the request
     */
    public Request(EnumSet<RequestType> requestType, User requester, boolean forSomeoneElse, Address address, String description) {
        this.id = UUID.randomUUID();
        this.requester = requester;
        this.responders = new ArrayList<User>();
        this.createdAt = Instant.now();
        this.priority = 0;

        // Add the priority values of all selected request types
        for(RequestType type : requestType) {
            this.priority += type.priority;
        }

        this.requestType = EnumSet.copyOf(requestType);
        this.address = address;
        this.description = description;
        this.status = RequestStatus.SUBMITTED;
        this.comment = "";
        this.forSomeoneElse = forSomeoneElse;
        this.people = new ArrayList<Person>();
        this.pets = new ArrayList<String>();
    }

    /**
     * Creates a relief request with existing information
     * @param id the unique identifier of the request
     * @param requester the user who submitted the request
     * @param responders the users responding to the request
     * @param createdAt the timestamp when the request was created
     * @param priority the priority level of the request
     * @param requestType the type of relief request
     * @param address the address of the request
     * @param description a description of the request
     * @param status the current status of the request
     * @param comment any additional comments related to the request
     * @param forSomeoneElse whether the request is for someone else
     */
    public Request(UUID id, User requester, ArrayList<User> responders, Instant createdAt, int  priority, EnumSet<RequestType> requestType, 
        Address address, String description, RequestStatus status, String comment, boolean forSomeoneElse) {  
        this.id = id;
        this.requester = requester;
        this.responders = responders;
        this.createdAt = createdAt;
        this.priority = priority;
        this.requestType = EnumSet.copyOf(requestType);
        this.address = address;
        this.description = description;
        this.status = status;
        this.comment = comment;
        this.forSomeoneElse = forSomeoneElse;
        this.people = new ArrayList<Person>();
        this.pets = new ArrayList<String>();
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
     * @return true if the responder was added successfully, false otherwise
     */
    public boolean addResponder(User responder) {
        if (!responders.contains(responder) && (responder.getPermissions().contains(Permission.VOLUNTEER)
                || responder.getPermissions().contains(Permission.MEDICAL_VOLUNTEER))) {
            responders.add(responder);
            if (status == RequestStatus.SUBMITTED) {
                status = RequestStatus.EN_ROUTE;
            }
            return true;
        }
        return false;
    }

    /**
     * Removes a responder from the request
     * @param responder the user to remove from the responders list
     * @return true if the responder was removed successfully, false otherwise
     */
    public boolean removeResponder(User responder) {
        int index = responders.indexOf(responder);
        if (index != -1) {
            responders.remove(index);
            if (responders.isEmpty() && status != RequestStatus.CLOSED) {
                status = RequestStatus.SUBMITTED;
            }
            return true;
        }
        return false;
    }

    /**
     * Sets the status of the request
     * @param status the new status of the request
     */
    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    /**
     * Sets the description for the request.
     * @param description the new description for the request
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the comment for the request.
     * @param comment the new comment for the request
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Sets the address for the request.
     * @param address the new address for the request
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Adds a person to the relief request.
     * @param person the person needing assistance
     */
    public void addPerson(Person person) {
        people.add(person);
    }

    /**
     * Returns the people included in the relief request.
     * @return the list of people
     */
    public ArrayList<Person> getPeople() {
        return people;
    }

    /**
     * Adds a pet to the relief request.
     * @param pet the name of the pet
     */
    public void addPet(String pet) {
        pets.add(pet);
    }

    /**
     * Returns the pets included in the relief request.
     * @return the list of pets
     */
    public ArrayList<String> getPets() {
        return pets;
    }

    /**
     * Returns the request's unique identifier.
     * @return the request's unique identifier
     */
    public UUID getId() {
        return id;
    }

    /**
     * Returns the user who submitted the request.
     * @return the user who submitted the request
     */
    public User getRequester() {
        return requester;
    }

    /**
     * Returns the request types.
     * @return the request types
     */
    public EnumSet<RequestType> getRequestType() {
        return requestType;
    }

    /**
     * Returns the address of the request.
     * @return the address of the request
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Returns the request status.
     * @return the request status
     */
    public RequestStatus getStatus() {
        return status;
    }

    /**
     * Returns the creation timestamp of the request.
     * @return the creation timestamp of the request
     */
    public Instant getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the list of responders for the request.
     * @return the list of responders for the request
     */
    public ArrayList<User> getResponders() {
        return responders;
    }

    /**
     * Returns the description of the request.
     * @return the description of the request
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the comment for the request.
     * @return the comment for the request
     */
    public String getComment() {
        return comment;
    }

    /**
     * Returns the priority of the request.
     * @return the priority of the request
     */
    public int getPriority() {
        return priority;
    }
}

