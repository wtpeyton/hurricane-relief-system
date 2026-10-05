package com.model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class DataWriter extends DataConstants {

    public static boolean saveUsers(ArrayList<User> users){
        UserList userList = UserList.getInstance();

        JSONArray userArray = new JSONArray();
        for (User user: users) {
            UUID id = user.getUserId();
            double[] location = user.getLocation();
            String locationZip = user.getLocationZip();
            String firstName = user.getFirstName();
            String lastName = user.getLastName();
            String password = user.getPassword();
            String phoneNumber = user.getPhoneNumber();
            ArrayList<Permission> permissions = user.getPermissions();
            ArrayList<Credential> credentials = user.getCredentials();
            ArrayList<ReliefResource> equipment = user.getEquipment();

            JSONObject currentUser = new JSONObject();

            currentUser.put(USER_ID, id.toString());
            currentUser.put(USER_LOCATION, location.toString());
            currentUser.put(USER_LOCATION_ZIP, locationZip.toString());
            currentUser.put(USER_FIRST_NAME, firstName);
            currentUser.put(USER_LAST_NAME, lastName);
            currentUser.put(USER_PASSWORD, password);
            currentUser.put(USER_PHONE_NUMBER, phoneNumber);
            currentUser.put(USER_PERMISSIONS, permissions.toString());
            currentUser.put(USER_CREDENTIALS, credentials.toString());
            currentUser.put(USER_EQUIPMENT, equipment.toString());

            userArray.add(currentUser);
        }
        try {
            FileWriter file = new FileWriter(DATA_PATH);
            file.write(userArray.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
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
