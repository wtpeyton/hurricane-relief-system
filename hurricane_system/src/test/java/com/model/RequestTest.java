package com.model;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

public class RequestTest {
    private User requester;
    private User responder;
    private ArrayList<RequestType> requestTypes;
    private double[] location;
    private Request request;

    @Before
    public void setUp() {
        requester = new User("Clark","Coats", "password123",
                             "603-433-211", "03290", 
                             new double[]{43.1939, -71.5724}
                            );
        responder = new User( "Alex", "Krivejko", "password456",
                             "603-433-211", "03290", 
                             new double[]{43.1939, -71.5724}
                            );
        responder.addPermission(Permission.VOLUNTEER);
    
        requestTypes = new ArrayList<RequestType>();
        requestTypes.add(RequestType.MEDICAL_EMERGENCY);
        requestTypes.add(RequestType.ESSENTIAL_SUPPLIES);

        location = new double[] {32.7800, -79.9400};

        request = new Request( requestTypes, requester, 
               false, location,
                  "Need medical help and supplies.");
    }
    @Test 
    public void testRequestCreation() {
        Assert.assertNotNull(request);
        Assert.assertEquals(requester, request.getRequester());
        Assert.assertEquals(2, request.getRequestType().size());
        Assert.assertArrayEquals(location, request.getLocation(), 0.0001);
        Assert.assertEquals(RequestStatus.SUBMITTED, request.getStatus());
        Assert.assertEquals("Need medical help and supplies.", request.getDescription());
    }
    @Test 
    public void testSetStatus() {
        request.setStatus(RequestStatus.IN_PROGRESS);
        Assert.assertEquals(RequestStatus.IN_PROGRESS, request.getStatus());
    }
    @Test
    public void testPriorityCalculation() {
        Assert.assertEquals(16, request.getPriority());
    }
    @Test
    public void testSetComment() {
        request.setComment("Help is on the way.");
        Assert.assertEquals("Help is on the way.", request.getComment());
    }
    @Test
    public void testSetLocation() {
        double[] newLocation = {32.7900, -79.9600};
        request.setLocation(newLocation);
        Assert.assertArrayEquals(newLocation, request.getLocation(), 0.0001);
    }
    @Test
    public void testAddResponder() {
        boolean wasAdded = request.addResponder(responder);
        Assert.assertTrue(wasAdded);
        Assert.assertEquals(1, request.getResponders().size());
        Assert.assertEquals(responder, request.getResponders().get(0));
    }
    @Test
    public void testRemoveResponder() {
        request.addResponder(responder);
        boolean wasRemoved = request.removeResponder(responder);
        Assert.assertTrue(wasRemoved);
        Assert.assertTrue(request.getResponders().isEmpty());
    }
    @Test
    public void testRemoveResponderNotFound() {
        boolean wasRemoved = request.removeResponder(responder);
        Assert.assertFalse(wasRemoved);
        Assert.assertTrue(request.getResponders().isEmpty());
    }
}
