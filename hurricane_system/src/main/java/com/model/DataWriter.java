package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
/**
 * @author William Peyton
 * DataWriter to write from lists to json file.
 */
public class DataWriter extends DataConstants {
    /**
     * Private Constructor to prevent instantiation
     */
    private DataWriter(){

    }
    /**
     * Saves the list of users to JSON
     * @param users ArrayList of Users
     * @return boolean whether it works or not
     */
    public static boolean saveUsers(ArrayList<User> users){

        JSONArray userArray = new JSONArray();

        for (User user: users) {
            JSONArray userLocation = new JSONArray();
            userLocation.add(String.valueOf(user.getLocation()[0]));
            userLocation.add(String.valueOf(user.getLocation()[1]));

            JSONObject currentUser = new JSONObject();
            //Adding all of the items into the user object
            currentUser.put(USER_ID, user.getUserId().toString());
            currentUser.put(USER_LOCATION, userLocation);
            currentUser.put(USER_LOCATION_ZIP, user.getLocationZip());
            currentUser.put(USER_FIRST_NAME, user.getFirstName());
            currentUser.put(USER_LAST_NAME, user.getLastName());
            currentUser.put(USER_PASSWORD, user.getPassword());
            currentUser.put(USER_PHONE_NUMBER, user.getPhoneNumber());
            currentUser.put(USER_PERMISSIONS, createPermissionArray(user));
            currentUser.put(USER_CREDENTIALS, createCredentialArray(user));
            currentUser.put(USER_EQUIPMENT, createEquipmentArray(user));
            //Adding the user object to the user array.
            userArray.add(currentUser);
        }
        try (FileWriter file = new FileWriter(USERS_FILE_PATH)) {
            //Writing the userArray to the file
            file.write(userArray.toJSONString());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Converts the ShelterList to a JSON File 
     * @param shelters the ArrayList of Shelters in ShelterList
     * @return boolean if it succeeds or fails
     */
    public static boolean saveShelters(ArrayList<Shelter> shelters){
        JSONArray shelterArray = new JSONArray();
        for (Shelter shelter : shelters){
            JSONObject currentShelter = new JSONObject();
            currentShelter.put(SHELTER_ACCESSIBILITY, shelter.getAccessibility().name());
            currentShelter.put(SHELTER_ADMIN, shelter.getShelterAdmin().getUserId().toString());
            currentShelter.put(SHELTER_CAPACITY, shelter.getCapacity());
            currentShelter.put(SHELTER_ID, shelter.getShelterId().toString());
            
            JSONArray shelterLocation = new JSONArray();
            shelterLocation.add(shelter.getLocation()[0]);
            shelterLocation.add(shelter.getLocation()[1]);
            currentShelter.put(SHELTER_LOCATION, shelterLocation);

            currentShelter.put(SHELTER_MEDICAL_SERVICE, shelter.getMedicalService().name());
            currentShelter.put(SHELTER_NAME, shelter.getName());
            currentShelter.put(SHELTER_PET_ACCEPTANCE, shelter.isPetAcceptance());
            currentShelter.put(SHELTER_VET_SERVICE, shelter.isVetService());
            currentShelter.put(SHELTER_SUPPLIES, createEquipmentArray(shelter));
            shelterArray.add(currentShelter);
        }
        try (FileWriter file = new FileWriter(SHELTERS_FILE_PATH)) {
            file.write(shelterArray.toJSONString());
            return true;   
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }
    /**
     * Converts the RequestsList to a JSON File
     * @param requests the ArrayList<Request> from Requests
     * @return boolean if it succeeds or fails
     */
    public static boolean saveRequests(ArrayList<Request> requests){
        JSONArray requestArray = new JSONArray();
        for(Request request : requests){
            JSONObject currentRequest = new JSONObject();
            currentRequest.put(REQUEST_COMMENT, request.getComment());
            currentRequest.put(REQUEST_CREATED_AT, request.getCreatedAt().toString());
            currentRequest.put(REQUEST_DESCRIPTION, request.getDescription());
            currentRequest.put(REQUEST_FOR_SOMEONE_ELSE, request.isForSomeoneElse());
            currentRequest.put(REQUEST_ID, request.getId().toString());

            JSONArray requestLocation = new JSONArray();
            requestLocation.add(String.valueOf(request.getLocation()[0]));
            requestLocation.add(String.valueOf(request.getLocation()[1]));
            currentRequest.put(REQUEST_LOCATION, requestLocation);
            
            currentRequest.put(REQUEST_PRIORITY, request.getPriority());
            currentRequest.put(REQUEST_REQUESTER, request.getRequester().getUserId().toString());
            currentRequest.put(REQUEST_RESPONDERS, createRespondersArray(request));
            currentRequest.put(REQUEST_STATUS, request.getStatus().name());
            currentRequest.put(REQUEST_TYPE, createTypeArray(request));
            
            requestArray.add(currentRequest);
        }
        try (FileWriter file = new FileWriter(REQUESTS_FILE_PATH)) {
            file.write(requestArray.toJSONString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Converts HurricaneList to a JSON file
     * @param hurricane from Hurricane List
     * @return boolean if it succeeds or fails
     */
    public static boolean saveHurricane(Hurricane hurricane){
        JSONObject hurricaneObject = new JSONObject();
        hurricaneObject.put(HURRICANE_ID, hurricane.getHurricaneId().toString());
        hurricaneObject.put(HURRICANE_NAME, hurricane.getName());
        hurricaneObject.put(HURRICANE_STATUS, hurricane.getStatus().name());
        hurricaneObject.put(HURRICANE_ACTIVE, hurricane.isActive());
        hurricaneObject.put(HURRICANE_AFFECTED_ZIP_CODES, createZipArray(hurricane));
        try (FileWriter file = new FileWriter(HURRICANE_FILE_PATH)) {
            file.write(hurricaneObject.toJSONString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Converts all the affected zip codes into a JSONArray
     * @param hurricane that is affecting the zip codes
     * @return JSONArray of zip codes
     */
    private static JSONArray createZipArray(Hurricane hurricane){
        JSONArray zipArray = new JSONArray();
        for(String zip : hurricane.getAffectedZipCodes()){
            zipArray.add(zip);
        }
        return zipArray;
    }
    /**
     * Converts all the types of a request to a JSONArray
     * @param request the request class that holds the types
     * @return JSONArray of the request types
     */
    private static JSONArray createTypeArray(Request request){
        JSONArray requestTypes = new JSONArray();
        for(RequestType type : request.getRequestType()){
            requestTypes.add(type.name());
        }
        return requestTypes;
    }
    /**
     * Converts all the responders of a request to a JSONArray
     * @param request the request class that holds the responders
     * @return JSONArray of the request responders
     */
    private static JSONArray createRespondersArray(Request request){
        JSONArray requestResponders = new JSONArray();
        for(User responder : request.getResponders()){
            requestResponders.add(responder.getUserId().toString());
        }
        return requestResponders;
    }
    /**
     * Iterates through the credentials of the User and converts them to a JSONArray
     * @param user The user whose credentials are being iterated
     * @return A JSONArray of the credentials
     */
    private static JSONArray createCredentialArray(User user){
        JSONArray credentials = new JSONArray();
        ArrayList<Credential> iterable = user.getCredentials();

        for(Credential item : iterable){
            credentials.add(item.name());
        }

        return credentials;
    }
    /**
     * Iterates through the permissions of the User and converts them to a JSONArray
     * @param user The user whose permissions are being iterated
     * @return A JSONArray of the permissions
     */
    private static JSONArray createPermissionArray(User user){
        JSONArray permissions = new JSONArray();
        ArrayList<Permission> iterable = user.getPermissions();

        for(Permission item : iterable){
            permissions.add(item.name());
        }

        return permissions;
    }
    /**
     * Iterates through a Users equipment and coverts them to a JSONArray
     * @param user the current user that we need to convert
     * @return A JSONArray of the equipment
     */
    private static JSONArray createEquipmentArray(User user){
        JSONArray equipment = new JSONArray();
        ArrayList<ReliefResource> iterable = user.getEquipment();

        for(ReliefResource item : iterable){
            JSONObject currentResource = new JSONObject();
            JSONArray locationArray = new JSONArray();
            locationArray.add(item.getLocation()[0]);
            locationArray.add(item.getLocation()[1]);
            currentResource.put(RESOURCE_ID, item.getResourceId().toString());
            currentResource.put(RESOURCE_LOCATION, locationArray);
            currentResource.put(RESOURCE_QUANTITY, item.getQuantity());
            currentResource.put(RESOURCE_TYPE, item.getType());

            equipment.add(currentResource);
        }
        
        return equipment;
    }
    /**
     * Iterates through the Shelter Supplies and converts to a JSONArray
     * @param shelter a shelter item
     * @return a JSONArray of the supplies
     */
    private static JSONArray createEquipmentArray(Shelter shelter){
        JSONArray equipment = new JSONArray();
        ArrayList<ReliefResource> iterable = shelter.getSupplies();

        for(ReliefResource item : iterable){
            JSONObject currentResource = new JSONObject();
            JSONArray locationArray = new JSONArray();
            locationArray.add(item.getLocation()[0]);
            locationArray.add(item.getLocation()[1]);
            currentResource.put(RESOURCE_ID, item.getResourceId().toString());
            currentResource.put(RESOURCE_LOCATION, locationArray);
            currentResource.put(RESOURCE_QUANTITY, item.getQuantity());
            currentResource.put(RESOURCE_TYPE, item.getType());

            equipment.add(currentResource);
        }
        
        return equipment;
    }

    /**
     * 
     *      TESTING TESTING TESTING
     *      MAIN    MAIN    MAIN
     * 
     */
    public static void main(String[] args) {
    // Test users
    ArrayList<User> users = new ArrayList<User>();

    User admin = new User(
        "William",
        "Peyton",
        "testPassword123",
        "8035551234",
        "29201",
        new double[] {34.0007, -81.0348}
    );
    admin.addPermission(Permission.SHELTER_ADMIN);
    admin.addPermission(Permission.VOLUNTEER);
    admin.addCredential(Credential.EMERGENCY_MEDICAL_TECHNICIAN_BASIC);
    admin.getEquipment().add(new ReliefResource(
        "Water bottles",
        50,
        new double[] {34.0007, -81.0348}
    ));
    users.add(admin);

    User responder = new User(
        "Jane",
        "Doe",
        "anotherTestPassword",
        "8035555678",
        "29205",
        new double[] {34.0090, -81.0281}
    );
    responder.addPermission(Permission.MEDICAL_VOLUNTEER);
    responder.addCredential(Credential.REGISTERED_NURSE);
    users.add(responder);

    // Test shelters
    ArrayList<Shelter> shelters = new ArrayList<Shelter>();
    ArrayList<ReliefResource> shelterSupplies = new ArrayList<ReliefResource>();
    shelterSupplies.add(new ReliefResource(
        "Blankets",
        100,
        new double[] {34.0100, -81.0300}
    ));
    shelterSupplies.add(new ReliefResource(
        "First aid kits",
        25,
        new double[] {34.0100, -81.0300}
    ));

    Shelter shelter = new Shelter(
        "Columbia Community Shelter",
        new double[] {34.0100, -81.0300},
        200,
        true,
        shelterSupplies,
        MedicalService.AVERAGE_SERVICE,
        true,
        Accessibility.VERY_ACCESSIBLE,
        admin
    );
    shelters.add(shelter);

    // Test requests
    ArrayList<Request> requests = new ArrayList<Request>();
    ArrayList<RequestType> requestTypes = new ArrayList<RequestType>();
    requestTypes.add(RequestType.MEDICAL_EMERGENCY);
    requestTypes.add(RequestType.ESSENTIAL_SUPPLIES);

    Request request = new Request(
        requestTypes,
        admin,
        false,
        new double[] {34.0050, -81.0350},
        "Family needs medical attention and drinking water."
    );
    request.addResponder(responder);
    request.setComment("Responder assigned and en route.");
    request.setStatus(RequestStatus.EN_ROUTE);
    requests.add(request);

    // Test hurricane
    ArrayList<String> affectedZipCodes = new ArrayList<String>();
    affectedZipCodes.add("29201");
    affectedZipCodes.add("29205");
    affectedZipCodes.add("29208");

    Hurricane hurricane = new Hurricane(
        "Test Hurricane",
        affectedZipCodes,
        HurricaneStatus.CATEGORY_TWO,
        true
    );

    // Write each JSON file
    System.out.println("Users saved: " + saveUsers(users));
    System.out.println("Shelters saved: " + saveShelters(shelters));
    System.out.println("Requests saved: " + saveRequests(requests));
    System.out.println("Hurricane saved: " + saveHurricane(hurricane));
    }
}
