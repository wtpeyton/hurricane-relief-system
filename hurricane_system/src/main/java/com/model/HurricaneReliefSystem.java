package com.model;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 * [PARTIAL STUB] Singleton facade for the Hurricane Relief System.
 * @author Jack Andrin
 */
public class HurricaneReliefSystem {
    private UserList userList;
    private RequestList requestList;
    private ShelterList shelterList;
    private User currentUser;
    private Hurricane currentHurricane;
    private static HurricaneReliefSystem instance;

    /**
     * Private constructor for the singleton HurricaneReliefSystem.
     */
    private HurricaneReliefSystem() {
        this.userList = UserList.getInstance();
        this.requestList = RequestList.getInstance();
        this.shelterList = ShelterList.getInstance();
    }

    /**
     * Returns the singleton instance of the HurricaneReliefSystem.
     * @return the singleton instance
     */
    public static HurricaneReliefSystem getInstance() {
        if (instance == null) {
            instance = new HurricaneReliefSystem();
        }
        return instance;
    }

    /**
     * Returns the list of users in the system.
     * @return the user list
     */
    public UserList getUserList() {
        return userList;
    }

    /**
     * Returns the list of requests in the system.
     * @return the request list
     */
    public RequestList getRequestList() {
        return requestList;
    }

    /**
     * Returns the list of shelters in the system.
     * @return the shelter list
     */
    public ShelterList getShelterList() {
        return shelterList;
    }

    /**
     * Returns the currently logged-in user.
     * @return the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Returns the current hurricane being tracked.
     * @return the current hurricane
     */
    public Hurricane getCurrentHurricane() {
        return currentHurricane;
    }

    /**
     * Logs in a user with the given phone number and password.
     * @param phoneNumber the user's phone number
     * @param password the user's password
     * @return true if login is successful, false otherwise
     */
    public boolean login(String phoneNumber, String password) {
        currentUser = userList.getUser(phoneNumber, password);
        if (currentUser == null) {
            return false;
        }
        currentHurricane = DataLoader.getHurricane();
        userList = UserList.getInstance();
        requestList = RequestList.getInstance();
        shelterList = ShelterList.getInstance();
        return true;
    }

    /**
     * Logs out the currently logged-in user.
     * @return true if logout is successful, false otherwise
     */
    public boolean logout() {
        if (currentUser != null) {
            currentUser = null;
            userList.saveUsers();
            requestList.saveRequests();
            shelterList.saveShelters();
            currentHurricane.saveHurricane();
            return true;
        }
        return false;
    }

    /**
     * Creates a new user account with the given information.
     * @param firstName the user's first name
     * @param lastName the user's last name
     * @param password the user's password
     * @param phoneNumber the user's phone number
     * @param locationZip the user's location ZIP code
     * @param location the user's location coordinates
     * @return the created User object, or null if creation fails
     */
    public User createAccount(String firstName, String lastName,
                              String password, String phoneNumber,
                              String locationZip, double[] location) {
        return userList.addUser(firstName, lastName, password, phoneNumber, locationZip, location);
        
    }

    /**
     * Submits a new relief request with the given information.
     * @param type the type of the request
     * @param location the location coordinates of the request
     * @param description the description of the request
     * @return the created Request object, or null if submission fails
     */
    public Request submitReliefRequest(EnumSet<RequestType> type, boolean forSomeoneElse,double[] location, String description) {
        return requestList.addRequest(type, currentUser, forSomeoneElse, location, description);
    }

    /**
     * Updates an existing relief request with the given description.
     * @param request the request to update
     * @param description the new description for the request
     * @return true if the update is successful, false otherwise
     */
    public boolean updateReliefRequest(Request request, String description) {
        request.setDescription(description);
        return requestList.updateRequest(request);
    }

    /**
     * Cancels an existing relief request.
     * @param request the request to cancel
     * @return true if the cancellation is successful, false otherwise
     */
    public boolean cancelReliefRequest(Request request) {
        if (request.getRequester().equals(currentUser)) {
            request.setStatus(RequestStatus.CLOSED);
            return requestList.updateRequest(request);
        }
        return false;
    }

    /**
     * Finds all relief requests submitted by the current user.
     * @return a list of the current user's requests
     */
    public ArrayList<Request> findMyRequests() {
        return requestList.findRequestsByUser(currentUser);
    }

    /**
     * Finds all relief requests near the current user's location.
     * @return a list of nearby requests
     */
    public ArrayList<Request> findNearbyRequests() {
        return requestList.findRequestsByDistance(currentUser.getLocation());
    }

    /**
     * Finds all relief requests of the specified type.
     * @param type the type of requests to find
     * @return a list of requests matching the specified type
     */
    public ArrayList<Request> findRequestsByType(RequestType type) {
        return requestList.findRequestsByType(type);
    }

    /**
     * Finds all relief requests with the specified status.
     * @param status the status of requests to find
     * @return a list of requests matching the specified status
     */
    public ArrayList<Request> findRequestsByStatus(RequestStatus status) {
        return requestList.findRequestsByStatus(status);
    }

    /**
     * Responds to an existing relief request.
     * @param request the request to respond to
     * @return true if the response is successful, false otherwise
     */
    public boolean respondToRequest(Request request) {
        return request.addResponder(currentUser);
    }
    
    /**
     * Withdraws from an existing relief request.
     * @param request the request to withdraw from
     * @return true if the withdrawal is successful, false otherwise
     */
    public boolean withdrawFromRequest(Request request) {
        return request.removeResponder(currentUser);
    }
    
    /**
     * Updates the status of an existing relief request.
     * @param request the request to update
     * @param status the new status to set
     * @return true if the update is successful, false otherwise
     */
    public boolean updateRequestStatus(Request request, RequestStatus status) {
        if (request.getResponders().contains(currentUser) || currentUser.getPermissions().contains(Permission.APP_ADMINISTRATOR)) {
            request.setStatus(status);
            return requestList.updateRequest(request);
        }
        return false;
    }
    
    /**
     * [STUB] Finds all nearby shelters.
     * @return a list of nearby shelters
     */
    public ArrayList<Shelter> findNearbyShelters() {
        // [STUB] Implement findNearbyShelters logic here
        return new ArrayList<>();
    }
    
    /**
     * Becomes a volunteer for the relief system.
     * @return true if the operation is successful, false otherwise
     */
    public boolean becomeVolunteer() {
        return currentUser.addPermission(Permission.VOLUNTEER);
    }

    /**
     * [STUB] Finds all volunteer opportunities sorted by location and filtered by credentials.
     * @return a list of volunteer opportunities
     */
    public ArrayList<Request> findVolunteerOpportunities() {
        // [STUB] Implement findVolunteerOpportunities logic here
        return new ArrayList<>();
    }
    
    /**
     * Removes a user from the relief system.
     * @param user the user to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeUser(User user) {
        if (currentUser.getPermissions().contains(Permission.APP_ADMINISTRATOR)) {
            return userList.removeUser(user);
        }
        return false;
    }
    
    /**
     * [STUB] Approves an existing relief request.
     * @param request the request to approve
     * @return true if the approval is successful, false otherwise
     */
    public boolean approveRequest(Request request) {
        // [STUB] Implement approveRequest logic here
        return false;
    }
    
    /**
     * [STUB] Rejects an existing relief request.
     * @param request the request to reject
     * @return true if the rejection is successful, false otherwise
     */
    public boolean rejectRequest(Request request) {
        // [STUB] Implement rejectRequest logic here
        return false;
    }
    
    /**
     * [STUB] Creates a new hurricane entry in the system.
     * @param name the name of the hurricane
     * @param affectedZipCodes the list of affected zip codes
     * @param status the status of the hurricane
     * @param active whether the hurricane is currently active
     * @return true if the creation is successful, false otherwise
     */
    public boolean createHurricane(String name, ArrayList<String> affectedZipCodes, HurricaneStatus status, boolean active) {
        // [STUB] Implement createHurricane logic here
        return false;
    }
    
    /**
     * [STUB] Modifies an existing hurricane entry in the system.
     * @param hurricane the hurricane to modify
     * @param name the new name of the hurricane
     * @param affectedZipCodes the new list of affected zip codes
     * @param status the new status of the hurricane
     * @param active whether the hurricane is currently active
     * @return true if the modification is successful, false otherwise
     */
    public boolean modifyHurricane(Hurricane hurricane, String name, ArrayList<String> affectedZipCodes, HurricaneStatus status, boolean active) {
        // [STUB] Implement modifyHurricane logic here
        return false;
    }
    
    /**
     * [STUB] Removes an existing hurricane entry from the system.
     * @param hurricane the hurricane to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeHurricane(Hurricane hurricane) {
        // [STUB] Implement removeHurricane logic here
        return false;
    }
    
    /**
     * Adds a permission to a user.
     * @param user the user to add the permission to
     * @param permission the permission to add
     * @return true if the addition is successful, false otherwise
     */
    public boolean addPermission(User user, Permission permission) {
        if (currentUser.getPermissions().contains(Permission.APP_ADMINISTRATOR)) {
            return user.addPermission(permission);
        }
        return false;
    }
    
    /**
     * Removes a permission from a user.
     * @param user the user to remove the permission from
     * @param permission the permission to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removePermission(User user, Permission permission) {
        if (currentUser.getPermissions().contains(Permission.APP_ADMINISTRATOR)) {
            return user.removePermission(permission);
        }
        return false;
    }
    
    /**
     * Adds a credential to the current user.
     * @param credential the credential to add
     * @return true if the addition is successful, false otherwise
     */
    public boolean addCredential(Credential credential) {
        return currentUser.addCredential(credential);
    }

    /**
     * Adds a credential to a specified user.
     * @param user the user to add the credential to
     * @param credential the credential to add
     * @return true if the addition is successful, false otherwise
     */
    public boolean addCredential(User user, Credential credential) {
        if (currentUser.getPermissions().contains(Permission.APP_ADMINISTRATOR)) {
            return user.addCredential(credential);
        }
        return false;
    }
    
    /**
     * Removes a credential from the current user.
     * @param credential the credential to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeCredential(Credential credential) {
        return currentUser.removeCredential(credential);
    }
    
    /**
     * Removes a credential from a specified user.
     * @param user the user to remove the credential from
     * @param credential the credential to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeCredential(User user, Credential credential) {
        if (currentUser.getPermissions().contains(Permission.APP_ADMINISTRATOR)) {
            return user.removeCredential(credential);
        }
        return false;
    }

    /**
     * Creates a new shelter.
     * @param name the name of the shelter
     * @param location the location of the shelter as a double array [latitude, longitude]
     * @param capacity the capacity of the shelter
     * @param petAcceptance whether the shelter accepts pets
     * @param supplies the list of relief resources available at the shelter
     * @param medicalService the medical services available at the shelter
     * @param vetService whether veterinary services are available at the shelter
     * @param accessibility the accessibility features of the shelter
     * @return true if the creation is successful, false otherwise
     */
    public boolean createShelter(String name, double[] location, int capacity, boolean petAcceptance, ArrayList<ReliefResource> supplies, MedicalService medicalService, boolean vetService, Accessibility accessibility) {
        if (currentUser.getPermissions().contains(Permission.APP_ADMINISTRATOR) || currentUser.getPermissions().contains(Permission.SHELTER_ADMIN)) {
            return shelterList.addShelter(name, location, capacity, petAcceptance, supplies, medicalService, vetService, accessibility, currentUser, new ArrayList<Person>(), new ArrayList<String>()) != null;
        }
        return false;
    }

    /**
     * Edits an existing shelter.
     * @param shelter the shelter to edit
     * @param name the new name of the shelter
     * @param location the new location of the shelter as a double array [latitude, longitude]
     * @param capacity the new capacity of the shelter
     * @param petAcceptance whether the shelter accepts pets
     * @param supplies the new list of relief resources available at the shelter
     * @param medicalService the new medical services available at the shelter
     * @param vetService whether veterinary services are available at the shelter
     * @param accessibility the new accessibility features of the shelter
     * @return true if the edit is successful, false otherwise
     */
    public boolean editShelter(Shelter shelter, String name, double[] location, int capacity, boolean petAcceptance, ArrayList<ReliefResource> supplies, MedicalService medicalService, boolean vetService, Accessibility accessibility) {
        if (currentUser.getPermissions().contains(Permission.APP_ADMINISTRATOR) || shelter.getShelterAdmin().equals(currentUser)) {
            shelter.setName(name);
            shelter.setLocation(location);
            shelter.setCapacity(capacity);
            shelter.setPetAcceptance(petAcceptance);
            shelter.setSupplies(supplies);
            shelter.setMedicalService(medicalService);
            shelter.setVetService(vetService);
            shelter.setAccessibility(accessibility);
            return true;
        }
        return false;
    }

    /**
     * Deletes an existing shelter.
     * @param shelter the shelter to delete
     * @return true if the deletion is successful, false otherwise
     */
    public boolean deleteShelter(Shelter shelter) {
        if (currentUser.getPermissions().contains(Permission.APP_ADMINISTRATOR) || shelter.getShelterAdmin().equals(currentUser)) {
            return shelterList.removeShelter(shelter);
        }
        return false;
    }

    /**
     * Adds a new piece of equipment to the current user.
     * @param type the type of equipment
     * @param quantity the quantity of equipment
     * @param location the location of the equipment as a double array [latitude, longitude]
     * @return true if the addition is successful, false otherwise
     */
    public boolean addEquipment(String type, int quantity, double[] location) {
        if (currentUser != null) {
            ReliefResource equipment = new ReliefResource(type, quantity, location);
            currentUser.addEquipment(equipment);
            return true;
        }
        return false;
    }

    /**
     * Views all equipment of the current user.
     * @return an ArrayList of ReliefResource representing the equipment
     */
    public ArrayList<ReliefResource> viewEquipment() {
        if (currentUser != null) {
            return currentUser.getEquipment();
        }
        return new ArrayList<>();
    }

    /**
     * Removes a piece of equipment from the current user.
     * @param equipment the equipment to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeEquipment(ReliefResource equipment) {
        if (currentUser != null) {
            return currentUser.getEquipment().remove(equipment);
        }
        return false;
    }
}
