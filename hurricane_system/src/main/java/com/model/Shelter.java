package com.model;
import java.util.ArrayList;
import java.util.UUID;
/**
 * @Author Ryan Kouchoukos
 * represents a shelter that can provide housing and resources during a hurricane.
 */
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

    /**
     * Creates a shelter using an exisitng shelter ID.
     * This constructor is used when loading a previously saved shelter.
     * @param shelterId The unique identifier for the shelter.
     * @param name The name of the shelter.
     * @param location The geographical location of the shelter.
     * @param capacity The maximum number of occupants the shelter can accommodate.
     * @param petAcceptance Indicates if the shelter accepts pets.
     * @param supplies The list of relief resources available at the shelter.
     * @param medicalService The level of medical service provided at the shelter.
     * @param vetService Indicates if veterinary services are available at the shelter.
     * @param accessibility The accessibility level of the shelter.
     * @param shelterAdmin The user who administers the shelter.
     */
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
    /**
     * Creates a new shelter with a randomly generated ID.
     * 
     * @param name The name of the shelter.
     * @param location The geographical location of the shelter.
     * @param capacity The maximum number of occupants the shelter can accommodate.
     * @param petAcceptance Indicates if the shelter accepts pets.
     * @param supplies The list of relief resources available at the shelter.
     * @param medicalService The level of medical service provided at the shelter.
     * @param vetService Indicates if veterinary services are available at the shelter.
     * @param accessibility The accessibility level of the shelter.
     * @param shelterAdmin The user who administers the shelter.
     */
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
    /**
     * @return The shelter's unique identifier.
     */
    public UUID getShelterId()
    {
        return shelterId;
    }
    /**
     * @return The name of the shelter.
     */
    public String getName()
    {
        return name;
    }
    /**
     * Sets the name of the shelter.
     *
     * @param name The new name of the shelter.
     */
    public void setName( String name)
    {
        this.name = name;
    }
    /**
     * @return The geographical location of the shelter.
     */
    public double[] getLocation()
    {
        return location;
    }

    /**
     * Sets the geographical location of the shelter.
     *
     * @param location The new location of the shelter.
     */
    public void setLocation(double[] location)
    {
        this.location = location;
    }
    /**
     * @return The maximum number of occupants the shelter can accommodate.
     */
    public int getCapacity()
    {
        return capacity;
    }
    /**
     * Sets the maximum number of occupants the shelter can accommodate.
     *
     * @param capacity The new capacity of the shelter.
     */
    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }
    /**
     * @return Indicates if the shelter accepts pets.
     */
    public boolean isPetAcceptance()
    {
        return petAcceptance;
    }
    /**
     * Sets whether the shelter accepts pets.
     *
     * @param petAcceptance The new pet acceptance status of the shelter.
     */
    public void setPetAcceptance(boolean petAcceptance)
    {
        this.petAcceptance = petAcceptance;
    }
    /**
     * @return the relief resources available at the shelter
     */
    public ArrayList<ReliefResource> getSupplies()
    {
        return supplies;
    }
    /**
     * @param supplies The new relief resources available at the shelter.
     */
    public void setSupplies(ArrayList<ReliefResource> supplies)
    {
        this.supplies = supplies;
    }
    /**
     * @return The medical service available at the shelter.
     */
    public MedicalService getMedicalService()
    {
        return medicalService;
    }
    /**
     * Sets the medical service available at the shelter.
     *
     * @param medicalService The new medical service of the shelter.
     */
    public void setMedicalService(MedicalService medicalService)
    {
        this.medicalService = medicalService;
    }
    /**
     * @return Indicates if the shelter provides veterinary services.
     */
    public boolean isVetService()
    {
        return vetService;
    }
    /**
     * Sets whether the shelter provides veterinary services.
     *
     * @param vetService The new veterinary service status of the shelter.
     */
    public void setVetService(boolean vetService)
    {
        this.vetService = vetService;
    }
    /**
     * @return The accessibility features of the shelter.
     */
    public Accessibility getAccessibility()
    {
        return accessibility;
    }
    /**
     * Sets the accessibility features of the shelter.
     *
     * @param accessibility The new accessibility features of the shelter.
     */
    public void setAccessibility(Accessibility accessibility)
    {
        this.accessibility = accessibility;
    }
    /**
     * @return The admin user responsible for the shelter.
     */
    public User getShelterAdmin()
    {
        return shelterAdmin;
    }
    /**
     * Sets the admin user responsible for the shelter.
     *
     * @param shelterAdmin The new admin user of the shelter.
     */
    public void setShelterAdmin(User shelterAdmin)
    {
        this.shelterAdmin = shelterAdmin;
    }
    /**
     * @return A string representation of the shelter.
     */
    public String toString()
    {
        return "Shelter{" +
                "petAcceptance=" + petAcceptance +
                ", supplies=" + supplies +
                ", medicalService=" + medicalService +
                ", vetService=" + vetService +
                ", accessibility=" + accessibility +
                ", shelterAdmin=" + shelterAdmin +
                '}';
    }
}
