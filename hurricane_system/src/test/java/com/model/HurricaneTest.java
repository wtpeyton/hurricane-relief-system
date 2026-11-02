package com.model;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Assert;

public class HurricaneTest {
    @Test 
    public void testSetStatus(){
        ArrayList<String> zips = new ArrayList<String>();
        zips.add("29201");
        Hurricane temp = new Hurricane("Katrina", zips, HurricaneStatus.TROPICAL_STORM, false);
        temp.setStatus(HurricaneStatus.CATEGORY_ONE);
        assertEquals(temp.getStatus(), HurricaneStatus.CATEGORY_ONE);
        assertNotEquals(temp.getStatus(), HurricaneStatus.TROPICAL_STORM);
    }
    @Test 
    public void testSetActive(){
        ArrayList<String> zips = new ArrayList<String>();
        zips.add("29201");
        Hurricane temp = new Hurricane("Katrina", zips, HurricaneStatus.TROPICAL_STORM, false);
        temp.setActive(true);
        assertTrue(temp.isActive());
    }
    @Test 
    public void testSetName(){
        ArrayList<String> zips = new ArrayList<String>();
        zips.add("29201");
        Hurricane temp = new Hurricane("Katrina", zips, HurricaneStatus.TROPICAL_STORM, false);
        temp.setName("Milo");
        assertEquals(temp.getName(),"Milo");
    }
    @Test 
    public void testSetAffectedZipCodes(){
        ArrayList<String> zips = new ArrayList<String>();
        zips.add("29201");
        Hurricane temp = new Hurricane("Katrina", zips, HurricaneStatus.TROPICAL_STORM, false);
        ArrayList<String> newZips = new ArrayList<>();
        newZips.add("20001");
        temp.setAffectedZipCodes(newZips);
        assertEquals(temp.getAffectedZipCodes(), newZips);
    }
    
}
