package com.model;
import java.util.ArrayList;
import java.util.UUID;
public class ShelterList 
{
    private static ShelterList instance;
    private ArrayList<Shelter> shelters;
    private ShelterList() 
    {
        shelters = new ArrayList<>();
    }
    public static ShelterList getInstance() 
    {
        if (instance == null)
            instance = new ShelterList();
        return instance;
    }
    public Shelter addShelter(String name, double[] location, User shelterAdmin)
    {
    
    }
    public boolean addShelter( Shelter shelter)
    {

    }
    public boolean removeShelter(Shelter shelter)
    {

    }
    public Shelter findShelter(UUID shelterId)
    {

    }
    public ArrayList<Shelter> findSheltersByDistance(double[] location)
    {

    }
    public boolean saveShelters()
    {

    }

}
