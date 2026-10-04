package com.model;
import java.util.ArrayList;
import java.util.UUID;
/**
 * @author Ryan Kouchoukos
 * ShelterList manages the list of shelters in the system.
 */
public class ShelterList 
{
    private static ShelterList instance;
    private ArrayList<Shelter> shelters;
    /**
     * Private constructor to enforce singleton pattern.
     */
    private ShelterList() 
    {
        shelters = new ArrayList<>();
    }
    /**
     * 
     * @return the singleton instance of ShelterList.
     */
    public static ShelterList getInstance() 
    {
        if (instance == null)
            instance = new ShelterList();
        return instance;
    }
    /**
     * Adds a new shelter to the list of shelters.
     * @param name the name of the shelter
     * @param location the geographical location of the shelter
     * @param capacity the maximum capacity of the shelter
     * @param petAcceptance whether the shelter accepts pets
     * @param supplies the list of relief resources available at the shelter
     * @param medicalService the medical services provided at the shelter
     * @param vetService whether veterinary services are available at the shelter
     * @param accessibility the accessibility features of the shelter
     * @param shelterAdmin the administrator responsible for the shelter
     * @return the newly added Shelter object
     */
    public Shelter addShelter(String name, double[] location, int capacity, boolean petAcceptance, 
                              ArrayList<ReliefResource> supplies, MedicalService medicalService, 
                              boolean vetService, Accessibility accessibility,User shelterAdmin)
    {
        Shelter shelter = new Shelter(name, location, capacity, petAcceptance, supplies,
                                      medicalService, vetService, accessibility, shelterAdmin);
        shelters.add(shelter);
        return shelter;
    }
    /**
     * Adds an existing shelter to the list of shelters.
     * @param shelter the Shelter object to be added
     * @return true if the shelter was successfully added, false otherwise
     */
    public boolean addShelter( Shelter shelter)
    {
        return shelters.add(shelter);
    }
    /**
     * Removes a shelter from the list of shelters.
     * @param shelter the Shelter object to be removed
     * @return true if the shelter was successfully removed, false otherwise
     */
    public boolean removeShelter(Shelter shelter)
    {
        return shelters.remove(shelter);
    }
    /**
     * Finds a shelter by its unique identifier.
     * @param shelterId the UUID of the shelter to be found
     * @return the Shelter object if found, null otherwise
     */
    public Shelter findShelter(UUID shelterId)
    {
        for (Shelter shelter : shelters) 
            {
            if (shelter.getShelterId().equals(shelterId)) 
                return shelter;
            }
        return null;
    }
    /**
     * Finds shelters sorted by their distance from a given location.
     * @param location the geographical location to measure distance from
     * @return a list of shelters sorted by proximity to the given location
     */
    public ArrayList<Shelter> findSheltersByDistance(double[] location)
    {
        ArrayList<Shelter> nearbyShelters = new ArrayList<>(shelters);
        nearbyShelters.sort((shelter1, shelter2) -> 
            Double.compare
            (
                Math.sqrt(Math.pow(shelter1.getLocation()[0] - location[0], 2) + Math.pow(shelter1.getLocation()[1] - location[1], 2)),
                Math.sqrt(Math.pow(shelter2.getLocation()[0] - location[0], 2) + Math.pow(shelter2.getLocation()[1] - location[1], 2))
            )
        );
        return nearbyShelters;
    }
    /**
     * Saves the current list of shelters to persistent storage.
     * @return true if the shelters were successfully saved, false otherwise
     */
    public boolean saveShelters()
    {
        return DataWriter.saveShelters(shelters);
    }

}
