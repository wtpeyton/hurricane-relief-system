package com.model;
import java.util.ArrayList;
import java.util.UUID;
public class Shelter {
    private UUID shelterId;
    private String name;
    private double[] location;
    private int capacity;
    private boolean petAcceptance;
    private ArrayList<ReliefResource> supplies;
    private MedicalService medicalService;
    private boolean vetService;
    private Accessibility accessibility;
    private User shelterAdmin;

    public Shelter(UUID shelterId, String name, double[] location, int capacity, boolean petAcceptance, 
                   ArrayList<ReliefResource> supplies, MedicalService medicalService,
                   boolean vetService, Accessibility accessibility, User shelterAdmin)
    {
        this.shelterId = shelterId;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.petAcceptance = petAcceptance;
        this.supplies = supplies;
        this.medicalService = medicalService;
        this.vetService = vetService;
        this.accessibility = accessibility;
        this.shelterAdmin = shelterAdmin;
    }
    public Shelter(String name, double[] location, int capacity, boolean petAcceptance,
                   ArrayList<ReliefResource> supplies, MedicalService medicalService,
                   boolean vetService, Accessibility accessibility, User shelterAdmin)
    {
        this.shelterId = UUID.randomUUID();
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.petAcceptance = petAcceptance;
        this.supplies = supplies;
        this.medicalService = medicalService;
        this.vetService = vetService;
        this.accessibility = accessibility;
        this.shelterAdmin = shelterAdmin;
    }
    public UUID getShelterId()
    {
        return shelterId;
    }
    public String getName()
    {
        return name;
    }
    public void setName( String name)
    {
        this.name = name;
    }
    public double[] getLocation()
    {
        return location;
    }

    public void setLocation(double[] location)
    {
        this.location = location;
    }
    public int getCapacity()
    {
        return capacity;
    }
    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }
    public boolean isPetAcceptance()
    {
        return petAcceptance;
    }
    public void setPetAcceptance(boolean petAcceptance)
    {
        this.petAcceptance = petAcceptance;
    }
    public ArrayList<ReliefResource> getSupplies()
    {
        return supplies;
    }
    public void setSupplies(ArrayList<ReliefResource> supplies)
    {
        this.supplies = supplies;
    }
    public MedicalService getMedicalService()
    {
        return medicalService;
    }
    public void setMedicalService(MedicalService medicalService)
    {
        this.medicalService = medicalService;
    }
    public boolean isVetService()
    {
        return vetService;
    }
    public void setVetService(boolean vetService)
    {
        this.vetService = vetService;
    }
    public Accessibility getAccessibility()
    {
        return accessibility;
    }
    public void setAccessibility(Accessibility accessibility)
    {
        this.accessibility = accessibility;
    }
    public User getShelterAdmin()
    {
        return shelterAdmin;
    }
    public void setShelterAdmin(User shelterAdmin)
    {
        this.shelterAdmin = shelterAdmin;
    }
}
