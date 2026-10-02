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
    
    public ReliefResource(String type, int quantity, double[] location)
    {
        this.resourceId = UUID.randomUUID();
        this.type = type;
        this.quantity = quantity;
        this.location = location;
    }
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
    
    }
}
