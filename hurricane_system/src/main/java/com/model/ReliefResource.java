package com.model;

import java.util.UUID;

import com.location.Address;
/**
 * Represents a relief resource available at a shelter.
 * @author Ryan Kouchoukos
 */
public class ReliefResource
{
    private UUID resourceId;
    private String type;
    private int quantity;
    private Address address;
    /**
     * Constructs a new ReliefResource with the specified type, quantity, and address.
     * @param type the type of the relief resource
     * @param quantity the quantity of the relief resource
     * @param address the address of the relief resource
     */
    public ReliefResource(String type, int quantity, Address address)
    {
        this.resourceId = UUID.randomUUID();
        this.type = type;
        this.quantity = quantity;
        this.address = address;
    }
    /**
     * Constructs a new ReliefResource with the specified resource ID, type, quantity, and address.
     * this is used when the resource ID is already known, such as when retrieving resources from a database.
     * @param resourceId the unique identifier of the relief resource
     * @param type the type of the relief resource
     * @param quantity the quantity of the relief resource
     * @param address the address of the relief resource
     */
    public ReliefResource(UUID resourceId, String type, int quantity, Address address)
    {
        this.resourceId = resourceId;
        this.type = type;
        this.quantity = quantity;
        this.address = address;
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
     * @return the address of the relief resource
     */
    public Address getAddress()
    {
        return address;
    }
    /**
     * Sets the address of the relief resource.
     * @param address the new address of the relief resource
     */
    public void setAddress(Address address)
    {
        this.address = address;
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
                ", address=" + address +
                '}';
    }
}
