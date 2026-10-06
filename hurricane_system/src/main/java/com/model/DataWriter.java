package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

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
     * [STUB] Saves the list of users to JSON
     * @param users ArrayList of Users
     * @return boolean whether it works or not
     */
    public static boolean saveUsers(ArrayList<User> users){

        JSONArray userArray = new JSONArray();

        for (User user: users) {
            JSONArray userLocation = new JSONArray();
            userLocation.add(user.getLocation()[0]);
            userLocation.add(user.getLocation()[1]);

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
        try {
            //Writing the userArray to the file
            FileWriter file = new FileWriter(USERS_FILE_PATH);
            file.write(userArray.toString());
            file.flush();
            file.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean saveShelters(ArrayList<Shelter> shelters){
        JSONArray shelterArray = new JSONArray();
        for (Shelter shelter : shelters){
            JSONObject currentShelter = new JSONObject();
            currentShelter.put(SHELTER_ACCESSIBILITY, shelter.getAccessibility())
            currentShelter.put(SHELTER_ADMIN, shelter.getShelterAdmin());
            currentShelter.put(SHELTER_CAPACITY, String.valueOf(shelter.getCapacity()));
            currentShelter.put(SHELTER_ID, shelter.getShelterId());
            
            JSONArray shelterLocation = new JSONArray();
            shelterLocation.add(String.valueOf(shelter.getLocation()[0]));
            shelterLocation.add(String.valueOf(shelter.getLocation()[1]));
            currentShelter.put(SHELTER_LOCATION, shelterLocation);

            currentShelter.put(SHELTER_MEDICAL_SERVICE, shelter.getMedicalService().name());
            currentShelter.put(SHELTER_NAME, shelter.getName());
            currentShelter.put(SHELTER_PET_ACCEPTANCE, shelter.isPetAcceptance());
            currentShelter.put(SHELTER_SUPPLIES, createEquipmentArray(shelter);
            shelterArray.add(currentShelter);
        }
        try {
            FileWriter file = new FileWriter(USERS_FILE_PATH);
            file.write(shelterArray.toString());
            file.flush();
            file.close();
         return true;   
        } catch (Exception e) {
            e.printStackTrace();
            return false; 
        }
    }
    public static boolean saveRequests(ArrayList<Request> requests){
        return true;
    }
    public static boolean saveHurricane(Hurricane hurricane){
        return true;
    }
    private static JSONArray createCredentialArray(User user){
        JSONArray credentials = new JSONArray();
        ArrayList<Credential> iterable = user.getCredentials();

        for(Credential item : iterable){
            credentials.add(item);
        }

        return credentials;
    }
    private static JSONArray createPermissionArray(User user){
        JSONArray permissions = new JSONArray();
        ArrayList<Permission> iterable = user.getPermissions();

        for(Permission item : iterable){
            permissions.add(item);
        }

        return permissions;
    }
    private static JSONArray createEquipmentArray(User user){
        JSONArray equipment = new JSONArray();
        ArrayList<ReliefResource> iterable = user.getEquipment();

        for(ReliefResource item : iterable){
            JSONObject currentResource = new JSONObject();
            JSONArray locationArray = new JSONArray();
            locationArray.add(item.getLocation()[0]);
            locationArray.add(item.getLocation()[1]);
            currentResource.put(RESOURCE_ID, item.getResourceId());
            currentResource.put(RESOURCE_LOCATION, locationArray);
            currentResource.put(RESOURCE_QUANTITY, item.getQuantity());
            currentResource.put(RESOURCE_TYPE, item.getType());

            equipment.add(item);
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
            currentResource.put(RESOURCE_ID, item.getResourceId());
            currentResource.put(RESOURCE_LOCATION, locationArray);
            currentResource.put(RESOURCE_QUANTITY, item.getQuantity());
            currentResource.put(RESOURCE_TYPE, item.getType());

            equipment.add(item);
        }
        
        return equipment;
    }
}

