package com.model;

import java.util.UUID;
/**
 * Represents a relief resource available at a shelter.
 * @author Ryan Kouchoukos
 */
public class ReliefResource 
{
    private UUID resourceId;
    private String type;
    private int quantity;
    private double[] location;
<<<<<<< HEAD
    
=======
    /**
     * Constructs a new ReliefResource with the specified type, quantity, and location.
     * @param type the type of the relief resource
     * @param quantity the quantity of the relief resource
     * @param location the location of the relief resource as a double array [latitude, longitude]
     */
>>>>>>> RyanKouchoukos
    public ReliefResource(String type, int quantity, double[] location)
    {
        this.resourceId = UUID.randomUUID();
        this.type = type;
        this.quantity = quantity;
        this.location = location;
    }
<<<<<<< HEAD
    public ReliefResource(UUID resourceId, String type, int quantity, double[] location)
    {

    }
    public UUID getResourceId()
    {

    }
    public String getType()
    {

    }
    public void setType(String type)
    {

    }
    public int getQuantity()
    {

    }
    public void setQuantity(int quantity)
    {

    }
    public double[] getLocation()
    {

    }
    public void setLocation(double[] location)
    {

    }
    public String toString()
    {
    
=======
    /**
     * Constructs a new ReliefResource with the specified resource ID, type, quantity, and location.
     * this is used when the resource ID is already known, such as when retrieving resources from a database.
     * @param resourceId the unique identifier of the relief resource
     * @param type the type of the relief resource
     * @param quantity the quantity of the relief resource
     * @param location the location of the relief resource as a double array [latitude, longitude]
     */
    public ReliefResource(UUID resourceId, String type, int quantity, double[] location)
    {
        this.resourceId = resourceId;
        this.type = type;
        this.quantity = quantity;
        this.location = location;
    }
    /**
     * @return the unique identifier of the relief resource
     */
    public UUID getResourceId()
    {
        return resourceId;
    }
    /**
     * @return the type of the relief resource
     */
    public String getType()
    {
        return type;
    }
    /**
     * Sets the type of the relief resource.
     * @param type the new type of the relief resource
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * @return the quantity of the relief resource
     */
    public int getQuantity()
    {
        return quantity;
    }
    /**
     * Sets the quantity of the relief resource.
     * @param quantity the new quantity of the relief resource
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
    /**
     * @return the location of the relief resource as a double array [latitude, longitude]
     */
    public double[] getLocation()
    {
        return location;
    }
    /**
     * Sets the location of the relief resource.
     * @param location the new location of the relief resource as a double array [latitude, longitude]
     */
    public void setLocation(double[] location)
    {
        this.location = location;
    }
    /**
     * @return a string representation of the relief resource
     */
    public String toString()
    {
        return "ReliefResource{" +
                "resourceId=" + resourceId +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", location=[" + location[0] + ", " + location[1] + "]" +
                '}';
>>>>>>> RyanKouchoukos
    }
}
