
package com.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;
import org.junit.Assert.*;

import com.location.Address;

public class DataWriterTest {

    /**
     * Tests a users name is saved correctly
     */
    @Test
    public void testSaveUsers() {
        ArrayList<User> temp = DataLoader.getUsers();
        ArrayList<User> users = new ArrayList<User>();
        ArrayList<ReliefResource> adaEquipment = new ArrayList<ReliefResource>();
        adaEquipment.add(new ReliefResource(UUID.fromString("a763baa7-2285-4c4e-b990-f880adcbecb7"), "Generator", 2,
                new Address("1600 Hampton Street", "Columbia", "SC", "29208")));
        User ada = new User(UUID.fromString("46ec8cfe-c556-4071-b7bd-c8c37a1f82a3"), new Address("1600 Hampton Street", "Columbia", "SC", "29208"),
                "Ada", "Lovelace", "password123", "8035550100",
                EnumSet.of(Permission.REGISTERED_USER, Permission.VOLUNTEER), EnumSet.of(Credential.BASIC_LIFE_SUPPORT),
                adaEquipment);
        users.add(ada);
        assertTrue(DataWriter.saveUsers(users));

        ArrayList<User> loaded = DataLoader.getUsers();

        User adaUser = loaded.get(0);
        assertEquals(UUID.fromString("46ec8cfe-c556-4071-b7bd-c8c37a1f82a3"), adaUser.getUserId());
        assertEquals("Ada", adaUser.getFirstName());
        assertEquals("Lovelace", adaUser.getLastName());
        assertEquals("password123", adaUser.getPassword());
        assertEquals("1600 Hampton Street, Columbia, SC 29208", adaUser.getAddress().toString());
        assertEquals("29208", adaUser.getAddress().getZip());
        assertEquals(Set.of(Permission.REGISTERED_USER, Permission.VOLUNTEER), adaUser.getPermissions());
        assertEquals(Set.of(Credential.BASIC_LIFE_SUPPORT), adaUser.getCredentials());
        assertEquals(1, adaUser.getEquipment().size());
        assertEquals("Generator", adaUser.getEquipment().get(0).getType());
        assertEquals(2, adaUser.getEquipment().get(0).getQuantity());
        DataWriter.saveUsers(temp);
    }
    /**
     * Tests if requests are saved correctly 
     */
    @Test
    public void testSaveRequest() {
        ArrayList<Request> temp = DataLoader.getRequests();
        ArrayList<Request> requests = new ArrayList<Request>();
        
        Request request = new Request(UUID.fromString("05fd8560-49e5-4f6e-ba02-53af591e572e"), UserList.getInstance().getUser(UUID.fromString("20560f06-8a86-4aa5-b348-b926a87f71a6")), new ArrayList<User>(List.of(UserList.getInstance().getUser(UUID.fromString("46ec8cfe-c556-4071-b7bd-c8c37a1f82a3")))), Instant.parse("2026-10-06T21:13:06.125235400Z"), 16, EnumSet.of(RequestType.MEDICAL_EMERGENCY, RequestType.ESSENTIAL_SUPPLIES), new Address("2700 Devine Street", "Columbia", "SC", "29205"), "Family needs medical attention and drinking water.", RequestStatus.EN_ROUTE, "Responder assigned and en route.", false);

        requests.add(request);
        assertTrue(DataWriter.saveRequests(requests));

        requests = DataLoader.getRequests();
        request = requests.get(0);
        assertEquals(UUID.fromString("05fd8560-49e5-4f6e-ba02-53af591e572e"), request.getId());
        assertEquals(UserList.getInstance().getUser(UUID.fromString("20560f06-8a86-4aa5-b348-b926a87f71a6")), request.getRequester());
        assertEquals(new ArrayList<User>(List.of(UserList.getInstance().getUser(UUID.fromString("46ec8cfe-c556-4071-b7bd-c8c37a1f82a3")))), request.getResponders());
        assertEquals(Instant.parse("2026-10-06T21:13:06.125235400Z"), request.getCreatedAt());
        assertEquals(16, request.getPriority());
        assertEquals(EnumSet.of(RequestType.MEDICAL_EMERGENCY, RequestType.ESSENTIAL_SUPPLIES), request.getRequestType());
        assertEquals("2700 Devine Street, Columbia, SC 29205", request.getAddress().toString());
        assertEquals("Family needs medical attention and drinking water.", request.getDescription());
        assertEquals(RequestStatus.EN_ROUTE, request.getStatus());
        assertEquals("Responder assigned and en route.",request.getComment());
        assertEquals(false, request.isForSomeoneElse());
        
        DataWriter.saveRequests(temp);
    }
    /**
     * tests if shelters are saved correctly
     */
    @Test
    public void testSaveShelters() {
        ArrayList<Shelter> temp = DataLoader.getShelters();
        try {
            ArrayList<Shelter> shelters = new ArrayList<Shelter>();

            ArrayList<ReliefResource> supplies = new ArrayList<ReliefResource>();
            supplies.add(new ReliefResource(UUID.fromString("d0d5d842-ec94-456b-a1ff-84660a93fabc"), "Blankets", 100, new Address("1100 Elmwood Avenue", "Columbia", "SC", "29201")));
            supplies.add(new ReliefResource(UUID.fromString("95fc4188-f87a-478a-bd14-66531e3e4d7c"), "First aid kits", 25, new Address("1100 Elmwood Avenue", "Columbia", "SC", "29201")));

            ArrayList<Person> occupants = new ArrayList<Person>();
            occupants.add(new Person("Alex Johnson", 34));
            occupants.add(new Person("Maria Garcia", 29));
            occupants.add(new Person("Sam Wilson", 8));

            ArrayList<String> pets = new ArrayList<String>(List.of("Buddy Johnson", "Luna Garcia"));

            Shelter shelter = new Shelter(UUID.fromString("d40a8bd4-7ceb-4464-8db8-c20cd6321dd5"), "Columbia Community Shelter", new Address("1100 Elmwood Avenue", "Columbia", "SC", "29201"), 200, true, supplies, MedicalService.AVERAGE_SERVICE, true, Accessibility.VERY_ACCESSIBLE, UserList.getInstance().getUser(UUID.fromString("bf349e54-7bc4-4b52-b3cc-e30eed9c381d")), occupants, pets);

            shelters.add(shelter);
            assertTrue(DataWriter.saveShelters(shelters));

            shelters = DataLoader.getShelters();
            assertEquals(1, shelters.size());
            shelter = shelters.get(0);

            assertEquals(UUID.fromString("d40a8bd4-7ceb-4464-8db8-c20cd6321dd5"), shelter.getShelterId());
            assertEquals("Columbia Community Shelter", shelter.getName());
            assertEquals("1100 Elmwood Avenue, Columbia, SC 29201", shelter.getAddress().toString());
            assertEquals(200, shelter.getCapacity());
            assertTrue(shelter.isPetAcceptance());
            assertEquals(MedicalService.AVERAGE_SERVICE, shelter.getMedicalService());
            assertTrue(shelter.isVetService());
            assertEquals(Accessibility.VERY_ACCESSIBLE, shelter.getAccessibility());
            assertEquals(UserList.getInstance().getUser(UUID.fromString("bf349e54-7bc4-4b52-b3cc-e30eed9c381d")), shelter.getShelterAdmin());

            assertEquals(2, shelter.getSupplies().size());
            assertEquals("Blankets", shelter.getSupplies().get(0).getType());
            assertEquals(100, shelter.getSupplies().get(0).getQuantity());
            assertEquals("First aid kits", shelter.getSupplies().get(1).getType());
            assertEquals(25, shelter.getSupplies().get(1).getQuantity());

            assertEquals(3, shelter.getOccupants().size());
            assertEquals("Alex Johnson", shelter.getOccupants().get(0).getName());
            assertEquals(34, shelter.getOccupants().get(0).getAge());
            assertEquals("Sam Wilson", shelter.getOccupants().get(2).getName());
            assertEquals(8, shelter.getOccupants().get(2).getAge());

            assertEquals(new ArrayList<String>(List.of("Buddy Johnson", "Luna Garcia")), shelter.getPets());
        } finally {
            DataWriter.saveShelters(temp);
        }
    }
    /**
     * Tests if hurricanes are saved correctly
     */
    @Test
    public void testSaveHurricane() {
        Hurricane temp = DataLoader.getHurricane();
        try {
            Hurricane hurricane = new Hurricane(UUID.fromString("39dfaddd-5e49-4601-a5bb-abca0bcf7d75"), "Test Hurricane", new ArrayList<String>(List.of("29201", "29205", "29208")), HurricaneStatus.CATEGORY_TWO, true);

            assertTrue(DataWriter.saveHurricane(hurricane));

            hurricane = DataLoader.getHurricane();
            assertEquals(UUID.fromString("39dfaddd-5e49-4601-a5bb-abca0bcf7d75"), hurricane.getHurricaneId());
            assertEquals("Test Hurricane", hurricane.getName());
            assertEquals(new ArrayList<String>(List.of("29201", "29205", "29208")), hurricane.getAffectedZipCodes());
            assertEquals(HurricaneStatus.CATEGORY_TWO, hurricane.getStatus());
            assertTrue(hurricane.isActive());
        } finally {
            DataWriter.saveHurricane(temp);
        }
    }
}
