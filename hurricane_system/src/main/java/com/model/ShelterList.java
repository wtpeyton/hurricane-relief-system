package com.model;
import java.util.ArrayList;
import java.util.UUID;
import java.util.Iterator;

import com.location.Address;
import com.location.MapTools;
/**
 * ShelterList manages the list of shelters in the system.
 * @author Ryan Kouchoukos, Jack Andrin
 */
public class ShelterList implements Iterable<Shelter>
{
    private static ShelterList instance;
    private ArrayList<Shelter> shelters;
    /**
     * Private constructor to enforce singleton pattern.
     */
    private ShelterList() 
    {
        shelters = DataLoader.getShelters();
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
     * @param address the address of the shelter
     * @param capacity the maximum capacity of the shelter
     * @param petAcceptance whether the shelter accepts pets
     * @param supplies the list of relief resources available at the shelter
     * @param medicalService the medical services provided at the shelter
     * @param vetService whether veterinary services are available at the shelter
     * @param accessibility the accessibility features of the shelter
     * @param shelterAdmin the administrator responsible for the shelter
     * @return the newly added Shelter object
     */
    public Shelter addShelter(String name, Address address, int capacity, boolean petAcceptance,
                              ArrayList<ReliefResource> supplies, MedicalService medicalService,
                              boolean vetService, Accessibility accessibility, User shelterAdmin,
                              ArrayList<Person> occupants, ArrayList<String> pets)
    {
        Shelter shelter = new Shelter(name, address, capacity, petAcceptance, supplies,
                                      medicalService, vetService, accessibility, shelterAdmin,
                                      occupants, pets);
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
     * Finds shelters sorted by their distance from a given address.
     * @param address the address to measure distance from
     * @return a list of shelters sorted from closest to farthest from the given address
     */
    public ArrayList<Shelter> findSheltersByDistance(Address address)
    {
        ArrayList<Shelter> remaining = new ArrayList<Shelter>(shelters);
        ArrayList<Shelter> nearbyShelters = new ArrayList<Shelter>();

        try
        {
            while (!remaining.isEmpty())
            {
                Address[] addresses = new Address[remaining.size()];
                for (int i = 0; i < remaining.size(); i++)
                {
                    addresses[i] = remaining.get(i).getAddress();
                }

                Address closest = MapTools.getInstance().findClosestAddress(address, addresses);

                Shelter closestShelter = remaining.get(0);
                for (Shelter shelter : remaining)
                {
                    if (shelter.getAddress() == closest)
                    {
                        closestShelter = shelter;
                    }
                }

                nearbyShelters.add(closestShelter);
                remaining.remove(closestShelter);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            nearbyShelters.addAll(remaining);
        }

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
    
    public ArrayList<Shelter> filterShelters(ArrayList<Shelter> shelters, Request request){
        ArrayList<Shelter> returnShelters = new ArrayList<Shelter>();
        for (Shelter shelter : shelters) {
            if(request.getRequestType().contains(RequestType.MEDICAL_EMERGENCY)&&(shelter.getMedicalService()==MedicalService.MINIMUM_SERVICE)){
                continue;
            }else{
                returnShelters.add(shelter);
            }
            if(shelter.getCapacity() - shelter.getCurrentOccupancy() < (request.getPeople().size()+request.getPets().size())){
                continue;
            } else {
                returnShelters.add(shelter);
            }
            if(request.getPets().size()>0 && !(shelter.isPetAcceptance())){
                continue;
            } else {
                returnShelters.add(shelter);
            }
        }
        return returnShelters;
    }

    /**
     * Returns an iterator over the shelters in the system.
     *
     * @return an Iterator of Shelter objects
     */
    @Override
    public Iterator<Shelter> iterator() {
        return shelters.iterator();
    }

}
