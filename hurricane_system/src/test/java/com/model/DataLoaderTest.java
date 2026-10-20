package com.model;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import static org.junit.Assert.*;

public class DataLoaderTest {

    @Test
    public void testGetUsers() {
        java.util.ArrayList<User> users = DataLoader.getUsers();

        assertEquals(3, users.size());

        User ada = users.get(0);
        assertEquals(UUID.fromString("46ec8cfe-c556-4071-b7bd-c8c37a1f82a3"), ada.getUserId());
        assertEquals("Ada", ada.getFirstName());
        assertEquals("Lovelace", ada.getLastName());
        assertEquals("8035550100", ada.getPhoneNumber());
        assertEquals("29208", ada.getLocationZip());
        assertArrayEquals(new double[] {34.0007, -81.0348}, ada.getLocation(), 0.000001);
        assertEquals(Arrays.asList(Permission.REGISTERED_USER, Permission.VOLUNTEER), ada.getPermissions());
        assertEquals(Arrays.asList(Credential.BASIC_LIFE_SUPPORT), ada.getCredentials());
        assertEquals(1, ada.getEquipment().size());
        assertEquals("Generator", ada.getEquipment().get(0).getType());
        assertEquals(2, ada.getEquipment().get(0).getQuantity());

        User grace = users.get(1);
        assertEquals("Grace", grace.getFirstName());
        assertTrue(grace.getPermissions().isEmpty());
        assertTrue(grace.getCredentials().isEmpty());
        assertTrue(grace.getEquipment().isEmpty());
    }

    @Test
    public void testGetRequests() {
        UserList users = UserList.getInstance();

        java.util.ArrayList<Request> requests = DataLoader.getRequests();

        assertEquals(1, requests.size());
        Request request = requests.get(0);
        assertEquals(UUID.fromString("05fd8560-49e5-4f6e-ba02-53af591e572e"), request.getId());
        assertEquals(users.getUser(UUID.fromString("20560f06-8a86-4aa5-b348-b926a87f71a6")), request.getRequester());
        assertEquals(Instant.parse("2026-10-06T21:13:06.125235400Z"), request.getCreatedAt());
        assertEquals(16, request.getPriority());
        assertEquals(Arrays.asList(RequestType.MEDICAL_EMERGENCY, RequestType.ESSENTIAL_SUPPLIES), request.getRequestType());
        assertArrayEquals(new double[] {34.005, -81.035}, request.getLocation(), 0.000001);
        assertEquals("Family needs medical attention and drinking water.", request.getDescription());
        assertEquals(RequestStatus.EN_ROUTE, request.getStatus());
        assertEquals("Responder assigned and en route.", request.getComment());
        assertFalse(request.isForSomeoneElse());
        assertEquals(1, request.getResponders().size());
        assertEquals(users.getUser(UUID.fromString("46ec8cfe-c556-4071-b7bd-c8c37a1f82a3")),
                request.getResponders().get(0));
    }

    @Test
    public void testGetShelters() {
        UserList users = UserList.getInstance();

        java.util.ArrayList<Shelter> shelters = DataLoader.getShelters();

        assertEquals(1, shelters.size());
        Shelter shelter = shelters.get(0);
        assertEquals(UUID.fromString("d40a8bd4-7ceb-4464-8db8-c20cd6321dd5"), shelter.getShelterId());
        assertEquals("Columbia Community Shelter", shelter.getName());
        assertArrayEquals(new double[] {34.01, -81.03}, shelter.getLocation(), 0.000001);
        assertEquals(200, shelter.getCapacity());
        assertTrue(shelter.isPetAcceptance());
        assertEquals(MedicalService.AVERAGE_SERVICE, shelter.getMedicalService());
        assertTrue(shelter.isVetService());
        assertEquals(Accessibility.VERY_ACCESSIBLE, shelter.getAccessibility());
        assertEquals(users.getUser(UUID.fromString("bf349e54-7bc4-4b52-b3cc-e30eed9c381d")),
                shelter.getShelterAdmin());

        assertEquals(2, shelter.getSupplies().size());
        assertEquals("Blankets", shelter.getSupplies().get(0).getType());
        assertEquals(100, shelter.getSupplies().get(0).getQuantity());
        assertEquals("First aid kits", shelter.getSupplies().get(1).getType());
        assertEquals(25, shelter.getSupplies().get(1).getQuantity());
    }

    @Test
    public void testGetHurricane() {
        Hurricane hurricane = DataLoader.getHurricane();

        assertNotNull(hurricane);
        assertEquals(UUID.fromString("39dfaddd-5e49-4601-a5bb-abca0bcf7d75"), hurricane.getHurricaneId());
        assertEquals("Test Hurricane", hurricane.getName());
        assertEquals(Arrays.asList("29201", "29205", "29208"), hurricane.getAffectedZipCodes());
        assertEquals(HurricaneStatus.CATEGORY_TWO, hurricane.getStatus());
        assertTrue(hurricane.isActive());
    }
}
