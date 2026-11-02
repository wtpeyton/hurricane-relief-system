package com.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.UUID;

import org.junit.Test;

import com.location.Address;

public class RequestListTest {
    @Test 
    public void testGetInstanceWithoutInstance(){
        RequestList created = RequestList.getInstance();
        assertNotEquals(null, created);
    }
    @Test
    public void testGetInstanceWithInstance(){
        RequestList created = RequestList.getInstance();
        RequestList newer = RequestList.getInstance();
        assertEquals(newer, created);
    }

   @Test 
    public void testAddRequestMultiple(){
        RequestList requests = RequestList.getInstance();
        User requester = DataLoader.getUsers().get(0);
        int before = requests.getRequests().size();

        Request added = requests.addRequest(EnumSet.of(RequestType.MEDICAL_EMERGENCY, RequestType.MISSING_PERSON), requester, true, new Address("1600 Hampton Street", "Columbia", "SC", "29208"), "Hurt my leg, and missing my brother");

        assertEquals(before + 1, requests.getRequests().size());
        assertTrue(requests.getRequests().contains(added));
        assertEquals(EnumSet.of(RequestType.MEDICAL_EMERGENCY, RequestType.MISSING_PERSON), added.getRequestType());
        assertEquals(requester.getUserId(), added.getRequester().getUserId());
        assertTrue(added.isForSomeoneElse());
        assertEquals("1600 Hampton Street, Columbia, SC 29208", added.getAddress().toString());
        assertEquals("Hurt my leg, and missing my brother", added.getDescription());
        assertEquals(RequestStatus.SUBMITTED, added.getStatus());
        assertEquals(19, added.getPriority());

        requests.removeRequest(added);
    }
    
    @Test
    public void testAddRequestSingle(){
        RequestList requests = RequestList.getInstance();
        User ada = UserList.getInstance().getUser(UUID.fromString("46ec8cfe-c556-4071-b7bd-c8c37a1f82a3"));
        Request request = new Request(EnumSet.of(RequestType.COMMUNITY_CLEANUP), ada, false, new Address("1600 Hampton Street", "Columbia", "SC", "29208"), "Debris in the road");
        int before = requests.getRequests().size();

        assertTrue(requests.addRequest(request));
        assertEquals(before + 1, requests.getRequests().size());
        assertTrue(requests.getRequests().contains(request));

        requests.removeRequest(request);
    }

    @Test
    public void testRemoveRequest(){
        RequestList requests = RequestList.getInstance();
        User ada = UserList.getInstance().getUser(UUID.fromString("46ec8cfe-c556-4071-b7bd-c8c37a1f82a3"));
        Request request = new Request(EnumSet.of(RequestType.COMMUNITY_CLEANUP), ada, false, new Address("1600 Hampton Street", "Columbia", "SC", "29208"), "Debris in the road");
        requests.addRequest(request);
        int before = requests.getRequests().size();

        assertTrue(requests.removeRequest(request));
        assertEquals(before - 1, requests.getRequests().size());
        assertFalse(requests.getRequests().contains(request));
        assertFalse(requests.removeRequest(request));
    }

    @Test
    public void testUpdateRequest(){
        RequestList requests = RequestList.getInstance();
        User ada = UserList.getInstance().getUser(UUID.fromString("46ec8cfe-c556-4071-b7bd-c8c37a1f82a3"));
        Request original = new Request(EnumSet.of(RequestType.COMMUNITY_CLEANUP), ada, false, new Address("1600 Hampton Street", "Columbia", "SC", "29208"), "Debris in the road");
        requests.addRequest(original);
        int before = requests.getRequests().size();

        Request replacement = new Request(original.getId(), ada, new ArrayList<User>(), original.getCreatedAt(), original.getPriority(), original.getRequestType(), original.getAddress(), "Road is now blocked", RequestStatus.CLOSED, "Updated", false);
        assertTrue(requests.updateRequest(replacement));
        assertEquals(before, requests.getRequests().size());
        Request found = requests.getRequestById(original.getId(), requests.getRequests());
        assertEquals("Road is now blocked", found.getDescription());
        assertEquals(RequestStatus.CLOSED, found.getStatus());
        assertEquals("Updated", found.getComment());

        Request unknown = new Request(EnumSet.of(RequestType.COMMUNITY_CLEANUP), ada, false, new Address("1600 Hampton Street", "Columbia", "SC", "29208"), "Not in the list");
        assertFalse(requests.updateRequest(unknown));

        requests.removeRequest(replacement);
    }

    @Test
    public void testSearchRequest(){
        RequestList requests = RequestList.getInstance();
        User ada = UserList.getInstance().getUser(UUID.fromString("46ec8cfe-c556-4071-b7bd-c8c37a1f82a3"));
        User grace = UserList.getInstance().getUser(UUID.fromString("20560f06-8a86-4aa5-b348-b926a87f71a6"));

        Request near = requests.addRequest(EnumSet.of(RequestType.MEDICAL_EMERGENCY), grace, false, new Address("1600 Hampton Street", "Columbia", "SC", "29208"), "Near request");
        Request far = requests.addRequest(EnumSet.of(RequestType.COMMUNITY_CLEANUP), ada, false, new Address("100 Meeting Street", "Charleston", "SC", "29401"), "Far request");
        far.setStatus(RequestStatus.CLOSED);

        assertEquals(requests.getRequests().size(), requests.searchRequest(null, null, null, null, null, null, true).size());

        ArrayList<Request> byType = requests.searchRequest(null, null, RequestType.MEDICAL_EMERGENCY, null, null, null, true);
        assertTrue(byType.contains(near));
        assertFalse(byType.contains(far));

        ArrayList<Request> byUser = requests.searchRequest(null, null, null, ada, null, null, true);
        assertTrue(byUser.contains(far));
        assertFalse(byUser.contains(near));

        ArrayList<Request> byStatus = requests.searchRequest(null, null, null, null, RequestStatus.CLOSED, null, true);
        assertTrue(byStatus.contains(far));
        assertFalse(byStatus.contains(near));

        ArrayList<Request> forVolunteer = requests.searchRequest(null, null, null, null, null, ada, true);
        assertTrue(forVolunteer.contains(near));
        assertFalse(forVolunteer.contains(far));

        Address here = new Address("1600 Hampton Street", "Columbia", "SC", "29208");
        ArrayList<Request> ascending = requests.searchRequest(null, here, null, null, null, null, true);
        assertTrue(ascending.indexOf(near) < ascending.indexOf(far));
        ArrayList<Request> descending = requests.searchRequest(null, here, null, null, null, null, false);
        assertTrue(descending.indexOf(far) < descending.indexOf(near));

        ArrayList<Request> combined = requests.searchRequest(null, null, RequestType.COMMUNITY_CLEANUP, ada, RequestStatus.CLOSED, null, true);
        assertEquals(1, combined.size());
        assertEquals(far, combined.get(0));

        requests.removeRequest(near);
        requests.removeRequest(far);
    }

}
