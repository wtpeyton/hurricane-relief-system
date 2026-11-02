package com.model;

import java.util.EnumSet;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

import com.location.Address;

/**
 * Tests the creation, priority, responders, people, and pets of relief requests.
 * @author Olivia, Ryan
 */
public class RequestTest {
    private User requester;
    private User responder;
    private EnumSet<RequestType> requestTypes;
    private Address address;
    private Request request;

    /**
     * Creates users and a request before each test.
     */
    @Before
    public void setUp() {
        requester = new User("Clark","Coats", "password123",
                             "6034332110", 
                             new Address("12 Main Street", "Nottingham", "NH", "03290")
                            );
        responder = new User( "Alex", "Krivejko", "password456",
                             "6034332111", 
                             new Address("12 Main Street", "Nottingham", "NH", "03290")
                            );
        responder.addPermission(Permission.VOLUNTEER);
    
        requestTypes = EnumSet.noneOf(RequestType.class);
        requestTypes.add(RequestType.MEDICAL_EMERGENCY);
        requestTypes.add(RequestType.ESSENTIAL_SUPPLIES);

        address = new Address("100 Meeting Street", "Charleston", "SC", "29401");

        request = new Request( requestTypes, requester, 
               false, address,
                  "Need medical help and supplies.");
    }

    /**
     * Tests that a new request is created.
     */
    @Test 
    public void testRequestCreation() {
        Assert.assertNotNull(request);
    }

    /**
     * Tests that a new request starts with SUBMITTED status.
     */
    @Test
    public void testInitialRequestStatus() {
        Assert.assertEquals(RequestStatus.SUBMITTED, request.getStatus());
    }

    /**
     * Tests updating the status of a request.
     */
    @Test 
    public void testSetStatus() {
        request.setStatus(RequestStatus.IN_PROGRESS);
        Assert.assertEquals(RequestStatus.IN_PROGRESS, request.getStatus());
    }

    /**
     * Tests the priority calculation for multiple request types.
     */
    @Test
    public void testPriorityCalculation() {
        Assert.assertEquals(16, request.getPriority());
    }

    /**
     * Tests updating the comment on a request.
     */
    @Test
    public void testSetComment() {
        request.setComment("Help is on the way.");
        Assert.assertEquals("Help is on the way.", request.getComment());
    }

    /**
     * Tests updating the address of a request.
     */
    @Test
    public void testSetAddress() {
        Address newAddress = new Address("200 Meeting Street", "Charleston", "SC", "29401");
        request.setAddress(newAddress);
        Assert.assertEquals(newAddress, request.getAddress());
    }
    /**
     * Tests adding a volunteer to a request.
     */
    @Test
    public void testAddResponder() {
        boolean wasAdded = request.addResponder(responder);
        Assert.assertTrue(wasAdded);
    }

    /**
     * Tests that an added responder appears in the responders list.
     */
    @Test
    public void testResponderAddedToList() {
        request.addResponder(responder);
        Assert.assertTrue(request.getResponders().contains(responder));
    }

    /**
     * Tests removing a volunteer from a request.
     */
    @Test
    public void testRemoveResponder() {
        request.addResponder(responder);
        boolean wasRemoved = request.removeResponder(responder);
        Assert.assertTrue(wasRemoved);
    }

    /**
     * Tests removing a volunteer who is not assigned to the request.
     */
    @Test
    public void testRemoveResponderNotFound() {
        boolean wasRemoved = request.removeResponder(responder);
        Assert.assertFalse(wasRemoved);
    }

    /**
     * Tests that a user without volunteer permission cannot respond to a request.
     */
    @Test
    public void testAddResponderWithoutPermission() {
        User regularUser = new User(
            "Jane", "Doe", "password123",
            "8035559876",
            new Address("1600 Hampton Street", "Columbia", "SC", "29201")
        );

        boolean wasAdded = request.addResponder(regularUser);

        Assert.assertFalse(wasAdded);
    }

    /**
     * Tests that the same volunteer cannot respond twice.
     */
    @Test
    public void testAddDuplicateResponder() {
        request.addResponder(responder);

        boolean wasAdded = request.addResponder(responder);

        Assert.assertFalse(wasAdded);
    }

    /**
     * Tests adding a medical volunteer to a request.
     */
    @Test
    public void testAddMedicalVolunteer() {
        User medicalVolunteer = new User(
            "Jane", "Doe", "password123",
            "8035559876",
            new Address("1600 Hampton Street", "Columbia", "SC", "29201")
        );

        medicalVolunteer.addPermission(Permission.MEDICAL_VOLUNTEER);

        boolean wasAdded = request.addResponder(medicalVolunteer);

        Assert.assertTrue(wasAdded);
    }

    /**
     * Tests that adding a responder changes the status to EN_ROUTE.
     */
    @Test
    public void testStatusAfterAddingResponder() {
        request.addResponder(responder);

        Assert.assertEquals(RequestStatus.EN_ROUTE, request.getStatus());
    }

    /**
     * Tests that removing the last responder resets the status.
     */
    @Test
    public void testStatusAfterRemovingLastResponder() {
        request.addResponder(responder);
        request.removeResponder(responder);

        Assert.assertEquals(RequestStatus.SUBMITTED, request.getStatus());
    }

    /**
     * Tests that a request stays EN_ROUTE when another responder remains.
     */
    @Test
    public void testStatusAfterRemovingOneOfTwoResponders() {
        User secondResponder = new User(
            "Jane", "Doe", "password123",
            "8035559876",
            new Address("1600 Hampton Street", "Columbia", "SC", "29201")
        );

        secondResponder.addPermission(Permission.VOLUNTEER);

        request.addResponder(responder);
        request.addResponder(secondResponder);
        request.removeResponder(responder);

        Assert.assertEquals(RequestStatus.EN_ROUTE, request.getStatus());
    }

    /**
     * Tests that removing a responder does not reopen a closed request.
     */
    @Test
    public void testClosedStatusAfterRemovingResponder() {
        request.addResponder(responder);
        request.setStatus(RequestStatus.CLOSED);
        request.removeResponder(responder);

        Assert.assertEquals(RequestStatus.CLOSED, request.getStatus());
    }

    /**
     * Tests the priority of a request with only one request type.
     */
    @Test
    public void testPriorityWithOneRequestType() {
        EnumSet<RequestType> singleType =
            EnumSet.of(RequestType.ESSENTIAL_SUPPLIES);

        Request singleRequest = new Request(
            singleType, requester, false, address,
            "Need drinking water."
        );

        Assert.assertEquals(5, singleRequest.getPriority());
    }

    /**
     * Tests creating a request with existing information.
     */
    @Test
    public void testExistingRequestCreation() {
        Request existingRequest = new Request(
            request.getId(),
            requester,
            request.getResponders(),
            request.getCreatedAt(),
            16,
            requestTypes,
            address,
            "Need medical help and supplies.",
            RequestStatus.EN_ROUTE,
            "Responder assigned.",
            false
        );

        Assert.assertEquals(RequestStatus.EN_ROUTE, existingRequest.getStatus());
    }

    /**
     * Tests adding a family member to a relief request.
     */
    @Test
    public void testAddPerson() {
        Person person = new Person("Sophie Carter", 9);

        request.addPerson(person);

        Assert.assertTrue(request.getPeople().contains(person));
    }

    /**
     * Tests adding a pet with its animal type to a relief request.
     */
    @Test
    public void testAddPet() {
        request.addPet("Buddy (Dog)");

        Assert.assertTrue(request.getPets().contains("Buddy (Dog)"));
    }

    /**
     * Tests that a new request starts with no people.
     */
    @Test
    public void testInitialPeople() {
        Assert.assertTrue(request.getPeople().isEmpty());
    }

    /**
     * Tests that a new request starts with no pets.
     */
    @Test
    public void testInitialPets() {
        Assert.assertTrue(request.getPets().isEmpty());
    }

    /**
     * Tests adding multiple family members to one relief request.
     */
    @Test
    public void testAddMultiplePeople() {
        request.addPerson(new Person("James Carter", 38));
        request.addPerson(new Person("Emily Carter", 36));
        request.addPerson(new Person("Sophie Carter", 9));

        Assert.assertEquals(3, request.getPeople().size());
    }
}
