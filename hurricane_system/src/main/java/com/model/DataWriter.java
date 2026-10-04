package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

    public static boolean saveUsers(ArrayList<User> users){

        JSONArray userArray = new JSONArray();
        for (User user: users) {
            UUID id = user.getUserId();
            double[] location = user.getLocation();
            JSONArray locationArray = new JSONArray();
            locationArray.add(String.valueOf(location[0]));
            locationArray.add(String.valueOf(location[1]));
            String locationZip = user.getLocationZip();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String password = user.getPassword();
            String phoneNumber = user.getPhoneNumber();
            ArrayList<Permission> permissions = user.getPermissions();
            JSONArray permissionArray = new JSONArray();
            for (Permission permission : permissions) {
                permissionArray.add(permission.name());
            }
            ArrayList<Credential> credentials = user.getCredentials();
            JSONArray credentialArray = new JSONArray();
            for (Credential credential : credentials){
                credentialArray.add(credential.name());
            }
            ArrayList<ReliefResource> equipment = user.getEquipment();
            JSONArray equipmentArray = new JSONArray();
            for (ReliefResource resource : equipment){
                JSONArray resourceLocation = new JSONArray();
                double[] resourceCoordinate = resource.getLocation();
                resourceLocation.add(String.valueOf(resourceCoordinate[0]));
                resourceLocation.add(String.valueOf(resourceCoordinate[1]));
                JSONObject currentResource = new JSONObject();
                currentResource.put(RESOURCE_TYPE, resource.getType());
                currentResource.put(RESOURCE_LOCATION, resourceLocation);
                currentResource.put(RESOURCE_QUANTITY, resource.getQuantity());
                currentResource.put(RESOURCE_ID, resource.getResourceId().toString());

                equipmentArray.add(currentResource);
            }
            JSONObject currentUser = new JSONObject();
            //Adding all of the items into the user object
            currentUser.put(USER_ID, id.toString());
            currentUser.put(USER_LOCATION, locationArray);
            currentUser.put(USER_LOCATION_ZIP, locationZip);
            currentUser.put(USER_FIRST_NAME, firstName);
            currentUser.put(USER_LAST_NAME, lastName);
            currentUser.put(USER_PASSWORD, password);
            currentUser.put(USER_PHONE_NUMBER, phoneNumber);
            currentUser.put(USER_PERMISSIONS, permissionArray);
            currentUser.put(USER_CREDENTIALS, credentialArray);
            currentUser.put(USER_EQUIPMENT, equipmentArray);
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
    public static boolean saveRequests(ArrayList<Request> requests){
        return true;
    }
    public static boolean saveShelters(ArrayList<Shelter> shelters){
        return true;
    }
    public static boolean saveHurricane(Hurricane hurricane){
        return true;
    }
}

