package com.model;

import java.util.ArrayList;

import org.junit.Test;

public class DataWriterTest {
    @Test 
    public void saveUsers() {
        UserList users = UserList.getInstance();

    }

    @Test 
    public void saveShelters() {
        ShelterList shelters = ShelterList.getInstance();
    }

    @Test 
    public void saveRequests() {
        RequestList requests = RequestList.getInstance();
    }

    @Test 
    public void saveHurricane() {
        Hurricane hurricane = new Hurricane("Katrina", "29201", null, false);
    }
}
