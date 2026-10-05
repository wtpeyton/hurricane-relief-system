package com.model;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.UUID;

/**
 * [STUB] Loads data from JSON files for the hurricane relief system.
 * @author Jack Andrin
 */
public class DataLoader extends DataConstants {
    /**
     * Private constructor to prevent instantiation.
     */
    private DataLoader() {

    }

    /**
     * [STUB] Retrieves the list of users.
     * @return ArrayList of User objects.
     */
    public static ArrayList<User> getUsers() {
        return new ArrayList<User>();
    }

    /**
     * [STUB] Retrieves the list of requests.
     * @return ArrayList of Request objects.
     */
    public static ArrayList<Request> getRequests() {
        return new ArrayList<Request>();
    }

    /**
     * Retrieves the list of shelters.
     * @return ArrayList of Shelter objects.
     */
    public static ArrayList<Shelter> getShelters() {
        ArrayList<Shelter> shelters = new ArrayList<Shelter>();
        try {
            FileReader fileReader = new FileReader(SHELTERS_FILE_PATH);
            // Add code to parse the JSON file and populate the shelters list
            JSONArray sheltersArray = (JSONArray) new JSONParser().parse(fileReader);
            for (Object obj : sheltersArray) {
                JSONObject shelterJson = (JSONObject) obj;

                UUID shelterId = UUID.fromString((String) shelterJson.get("shelter_id"));
                String name = (String) shelterJson.get("name");
                JSONArray locationArray = (JSONArray) shelterJson.get("location");
                double[] location = new double[] { (double) locationArray.get(0), (double) locationArray.get(1) };
                int capacity = ((Long) shelterJson.get("capacity")).intValue();
                boolean petAcceptance = (Boolean) shelterJson.get("pet_acceptance");

                ArrayList<ReliefResource> supplies = new ArrayList<ReliefResource>();
                JSONArray suppliesArray = (JSONArray) shelterJson.get("supplies");
                for (Object supplyObj : suppliesArray) {
                    JSONObject supplyJson = (JSONObject) supplyObj;
                    UUID resourceId = UUID.fromString((String) supplyJson.get("resource_id"));
                    String type = (String) supplyJson.get("type");
                    int quantity = ((Long) supplyJson.get("quantity")).intValue();
                    JSONArray supplyLocationArray = (JSONArray) supplyJson.get("location");
                    double[] supplyLocation = new double[] { (double) supplyLocationArray.get(0), (double) supplyLocationArray.get(1) };
                    supplies.add(new ReliefResource(resourceId, type, quantity, supplyLocation));
                }
                
                MedicalService medicalService = MedicalService.valueOf((String) shelterJson.get("medical_service"));
                boolean vetService = (Boolean) shelterJson.get("vet_service");
                Accessibility accessibility = Accessibility.valueOf((String) shelterJson.get("accessibility"));
                User shelterAdmin = UserList.getInstance().getUser(UUID.fromString((String) shelterJson.get("shelter_admin")));

                Shelter shelter = new Shelter(shelterId, name, location, capacity, petAcceptance, 
                   supplies, medicalService, vetService, accessibility, shelterAdmin);
                shelters.add(shelter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shelters;
    }

    /**
     * [STUB] Retrieves the hurricane data.
     * @return Hurricane object.
     */
    public static Hurricane getHurricane() {
        return new Hurricane("", new ArrayList<String>(), HurricaneStatus.TROPICAL_STORM, true);
    }

    /**
     * Main method to test the DataLoader class.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        // Testing getShelters()
        ArrayList<Shelter> shelters = getShelters();
        for (Shelter shelter : shelters) {
            System.out.println(shelter);
        }
    }
}
