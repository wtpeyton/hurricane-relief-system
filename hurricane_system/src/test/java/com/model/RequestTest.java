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
    
        requestTypes = new ArrayList<RequestType>();
        requestTypes.add(RequestType.MEDICAL_EMERGENCY);
        requestTypes.add(RequestType.ESSENTIAL_SUPPLIES);

        location = new double[] {32.7800, -79.9400};

        request = new Request( requestTypes, requester, 
               false, location,
                  "Need medical help and supplies.");
    }
}
