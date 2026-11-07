package com.model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.junit.After;
import org.junit.Before;

public class HurricaneReliefSystemTest {
    private static final double[] TEST_LOCATION = new double[]{34.0, -81.0};

    private HurricaneReliefSystem facade;
    private User testUser;
    private final ArrayList<Request> createdRequests = new ArrayList<>();
    private final ArrayList<User> createdUsers = new ArrayList<>();
    private final ArrayList<Shelter> createdShelters = new ArrayList<>();

    @Before
    public void setUp() {
        facade = HurricaneReliefSystem.getInstance();
        testUser = facade.createAccount(
            "Test", "User", "test-password", "9115550000", "29201", TEST_LOCATION
        );
        assertNotNull(testUser);
        createdUsers.add(testUser);
    }

    @After
    public void tearDown() {
        facade.logout(false);
        for (Request request : createdRequests) {
            facade.getRequestList().removeRequest(request);
        }
        for (User user : createdUsers) {
            facade.getUserList().removeUser(user);
        }
        for (Shelter shelter : createdShelters) {
            facade.getShelterList().removeShelter(shelter);
        }
    }

    private void loginTestUser() {
        assertTrue(facade.login("9115550000", "test-password"));
    }

    private User createAdditionalUser() {
        User additionalUser = facade.createAccount(
            "Additional", "User", "test-password", "5555552222", "29201", TEST_LOCATION
        );
        createdUsers.add(additionalUser);
        return additionalUser;
    }

    private Shelter findCreatedShelter(String name) {
        for (Shelter shelter : facade.getShelterList()) {
            if (name.equals(shelter.getName())) {
                return shelter;
            }
        }
        return null;
    }

    @Test
    public void testGetInstanceAndLists() {
        loginTestUser();
        assertSame(facade, HurricaneReliefSystem.getInstance());
        assertSame(UserList.getInstance(), facade.getUserList());
        assertSame(RequestList.getInstance(), facade.getRequestList());
        assertSame(ShelterList.getInstance(), facade.getShelterList());
        assertSame(testUser, facade.getCurrentUser());
        assertNotNull(facade.getCurrentHurricane());
    }

    @Test
    public void testInvalidLogin() {
        assertFalse(facade.login("5555550000", "wrong-password"));
        assertFalse(facade.login("wrong-phone", "test-password"));
        assertNull(facade.getCurrentUser());
        assertFalse(facade.logout(false));
        assertTrue(facade.viewEquipment().isEmpty());
        assertFalse(facade.addEquipment("Water Bottles", 1, TEST_LOCATION));
        assertFalse(facade.removeEquipment(new ReliefResource("Water Bottles", 1, TEST_LOCATION)));
    }

    @Test
    public void testInvalidAccountCreation() {
        assertNull(facade.createAccount(
            "Test", "User", "test-password", "not-a-phone", "29201", TEST_LOCATION
        ));

        assertFalse(facade.login("not-a-phone", "test-password"));
        assertNull(facade.getCurrentUser());
    }

    @Test
    public void testSubmitUpdateCancelRequest() {
        loginTestUser();
        Request request = facade.submitReliefRequest(EnumSet.of(RequestType.ESSENTIAL_SUPPLIES), false, TEST_LOCATION, "Need water");
        assertNotNull(request);
        createdRequests.add(request);

        assertSame(testUser, request.getRequester());
        assertTrue(facade.findMyRequests().contains(request));
        assertEquals(RequestStatus.SUBMITTED, request.getStatus());

        assertTrue(facade.updateReliefRequest(request, "Updated description"));
        assertEquals("Updated description", request.getDescription());

        assertTrue(facade.cancelReliefRequest(request));
        assertEquals(RequestStatus.CLOSED, request.getStatus());
    }

    @Test
    public void testFindRequestsByTypeStatusAndDistance() {
        loginTestUser();
        Request nearbyRequest = facade.submitReliefRequest(
            EnumSet.of(RequestType.ESSENTIAL_SUPPLIES),
            false,
            new double[]{34.01, -81.0},
            "Need water"
        );
        Request distantRequest = facade.submitReliefRequest(
            EnumSet.of(RequestType.RESCUE_TRAPPED),
            false,
            new double[]{35.0, -80.0},
            "Need rescue"
        );
        createdRequests.add(nearbyRequest);
        createdRequests.add(distantRequest);
        assertTrue(facade.cancelReliefRequest(distantRequest));

        assertTrue(facade.findRequestsByType(RequestType.ESSENTIAL_SUPPLIES).contains(nearbyRequest));
        assertFalse(facade.findRequestsByType(RequestType.ESSENTIAL_SUPPLIES).contains(distantRequest));
        assertTrue(facade.findRequestsByStatus(RequestStatus.SUBMITTED).contains(nearbyRequest));
        assertTrue(facade.findRequestsByStatus(RequestStatus.CLOSED).contains(distantRequest));

        ArrayList<Request> nearbyRequests = facade.findNearbyRequests();
        assertTrue(nearbyRequests.indexOf(nearbyRequest) < nearbyRequests.indexOf(distantRequest));
    }

    @Test
    public void testModifyAnotherUsersRequest() {
        loginTestUser();
        Request anotherRequest = facade.submitReliefRequest(EnumSet.of(RequestType.ESSENTIAL_SUPPLIES), false, TEST_LOCATION, "Need food");
        createdRequests.add(anotherRequest);
        facade.logout(false);

        User anotherUser = facade.createAccount(
            "Another", "User", "test-password", "5555551111", "29201", TEST_LOCATION
        );
        createdUsers.add(anotherUser);
        facade.login("5555551111", "test-password");

        assertFalse(facade.updateReliefRequest(anotherRequest, "Updated description"));
        assertFalse(facade.cancelReliefRequest(anotherRequest));
    }

    @Test
    public void testBecomeVolunteerAndAddEquipment() {
        loginTestUser();
        assertTrue(facade.becomeVolunteer());
        assertTrue(testUser.getPermissions().contains(Permission.VOLUNTEER));

        assertTrue(facade.addEquipment("Water Bottles", 100, TEST_LOCATION));

        ReliefResource equipment = facade.viewEquipment().get(0);
        assertEquals("Water Bottles", equipment.getType());
        assertTrue(facade.removeEquipment(equipment));
        assertTrue(facade.viewEquipment().isEmpty());
        assertFalse(facade.removeEquipment(equipment));
    }

    @Test
    public void testRespondUpdateWithdrawFromRequest() {
        loginTestUser();
        Request request = facade.submitReliefRequest(EnumSet.of(RequestType.ESSENTIAL_SUPPLIES), false, TEST_LOCATION, "Need blankets");
        createdRequests.add(request);
        facade.logout(false);

        User anotherUser = facade.createAccount(
            "Another", "User", "test-password", "5555551111", "29201", TEST_LOCATION
        );
        createdUsers.add(anotherUser);
        facade.login("5555551111", "test-password");
        facade.becomeVolunteer();

        assertTrue(facade.respondToRequest(request));
        assertTrue(request.getResponders().contains(anotherUser));

        assertTrue(facade.updateRequestStatus(request, RequestStatus.IN_PROGRESS));
        assertEquals(RequestStatus.IN_PROGRESS, request.getStatus());

        assertTrue(facade.withdrawFromRequest(request));
        assertFalse(request.getResponders().contains(anotherUser));
    }

    @Test
    public void testFindVolunteerOpportunities() {
        loginTestUser();
        Request request = facade.submitReliefRequest(EnumSet.of(RequestType.ESSENTIAL_SUPPLIES), false, TEST_LOCATION, "Need water");
        createdRequests.add(request);
        facade.logout(false);

        User additionalUser = createAdditionalUser();
        facade.login("5555552222", "test-password");
        assertTrue(facade.becomeVolunteer());

        assertTrue(facade.findVolunteerOpportunities().contains(request));
    }

    @Test
    public void testCreateFindEditDeleteShelter() {
        loginTestUser();
        testUser.getPermissions().add(Permission.SHELTER_ADMIN);
        assertTrue(facade.createShelter(
            "Facade Test Shelter",
            TEST_LOCATION,
            25,
            true,
            new ArrayList<ReliefResource>(),
            MedicalService.MINIMUM_SERVICE,
            false,
            Accessibility.MODERATELY_ACCESSIBLE
        ));

        Shelter shelter = findCreatedShelter("Facade Test Shelter");
        assertNotNull(shelter);
        assertTrue(facade.findNearbyShelters().contains(shelter));

        assertTrue(facade.editShelter(
            shelter,
            "Updated Shelter",
            TEST_LOCATION,
            100,
            true,
            new ArrayList<ReliefResource>(),
            MedicalService.MINIMUM_SERVICE,
            false,
            Accessibility.MODERATELY_ACCESSIBLE
        ));
        assertEquals("Updated Shelter", shelter.getName());
        assertEquals(100, shelter.getCapacity());

        assertTrue(facade.deleteShelter(shelter));
        assertNull(findCreatedShelter("Updated Shelter"));
        assertFalse(facade.deleteShelter(shelter));
    }

    @Test
    public void testAppAdminPowers() {
        User targetUser = createAdditionalUser();
        facade.login("5555552222", "test-password");
        Request request = facade.submitReliefRequest(EnumSet.of(RequestType.RESCUE_TRAPPED), false, TEST_LOCATION, "Need rescue");
        createdRequests.add(request);
        facade.logout(false);

        loginTestUser();
        testUser.getPermissions().add(Permission.APP_ADMINISTRATOR);

        assertTrue(facade.addPermission(targetUser, Permission.VOLUNTEER));
        assertTrue(facade.removePermission(targetUser, Permission.VOLUNTEER));
        assertFalse(facade.removePermission(targetUser, Permission.VOLUNTEER));

        assertTrue(facade.addCredential(targetUser, Credential.PARAMEDIC));
        assertTrue(facade.removeCredential(targetUser, Credential.PARAMEDIC));
        assertFalse(facade.removeCredential(targetUser, Credential.PARAMEDIC));

        assertTrue(facade.approveRequest(request));
        assertEquals(RequestStatus.SUBMITTED, request.getStatus());
        assertTrue(facade.rejectRequest(request));
        assertEquals(RequestStatus.CLOSED, request.getStatus());

        assertTrue(facade.updateRequestStatus(request, RequestStatus.IN_PROGRESS));
        assertEquals(RequestStatus.IN_PROGRESS, request.getStatus());

        assertTrue(facade.removeUser(targetUser));
        assertFalse(facade.removeUser(targetUser));
    }

    @Test
    public void testCreateModifyRemoveHurricane() {
        loginTestUser();
        testUser.getPermissions().add(Permission.APP_ADMINISTRATOR);
        ArrayList<String> affectedZipCodes = new ArrayList<>();
        affectedZipCodes.add("29201");

        assertTrue(facade.createHurricane(
            "Facade Hurricane", affectedZipCodes, HurricaneStatus.CATEGORY_ONE, true
        ));
        Hurricane hurricane = facade.getCurrentHurricane();
        assertNotNull(hurricane);
        assertEquals("Facade Hurricane", hurricane.getName());

        ArrayList<String> updatedZipCodes = new ArrayList<>();
        updatedZipCodes.add("29205");
        assertTrue(facade.modifyHurricane(
            hurricane, "Updated Facade Hurricane", updatedZipCodes,
            HurricaneStatus.CATEGORY_TWO, true
        ));
        assertEquals("Updated Facade Hurricane", hurricane.getName());
        assertEquals(updatedZipCodes, hurricane.getAffectedZipCodes());
        assertEquals(HurricaneStatus.CATEGORY_TWO, hurricane.getStatus());

        assertTrue(facade.removeHurricane(hurricane));
        assertFalse(hurricane.isActive());
        assertFalse(facade.modifyHurricane(
            null, "Invalid", updatedZipCodes, HurricaneStatus.CATEGORY_ONE, true
        ));
        assertFalse(facade.removeHurricane(null));
    }

    @Test
    public void testManageOwnCredentials() {
        loginTestUser();
        assertTrue(facade.addCredential(Credential.PARAMEDIC));
        assertTrue(testUser.getCredentials().contains(Credential.PARAMEDIC));
        assertFalse(facade.addCredential(Credential.PARAMEDIC));

        assertTrue(facade.removeCredential(Credential.PARAMEDIC));
        assertFalse(testUser.getCredentials().contains(Credential.PARAMEDIC));
        assertFalse(facade.removeCredential(Credential.PARAMEDIC));
    }

    @Test
    public void testCallingMethodsInvalidPermissions() {
        User targetUser = createAdditionalUser();
        facade.login("5555552222", "test-password");
        Request request = facade.submitReliefRequest(EnumSet.of(RequestType.RESCUE_TRAPPED), false, TEST_LOCATION, "Need rescue");
        Hurricane hurricane = new Hurricane(
            "Unauthorized Hurricane", new ArrayList<String>(),
            HurricaneStatus.CATEGORY_ONE, true
        );
        facade.logout(false);

        loginTestUser();
        assertFalse(facade.removeUser(targetUser));
        assertFalse(facade.approveRequest(request));
        assertFalse(facade.rejectRequest(request));
        assertFalse(facade.createHurricane(
            "Unauthorized", new ArrayList<String>(), HurricaneStatus.CATEGORY_ONE, true
        ));
        assertFalse(facade.createShelter(
            "Unauthorized Shelter",
            TEST_LOCATION,
            1,
            false,
            new ArrayList<ReliefResource>(),
            MedicalService.MINIMUM_SERVICE,
            false,
            Accessibility.MODERATELY_ACCESSIBLE
        ));
        assertFalse(facade.modifyHurricane(
            hurricane, "Unauthorized", new ArrayList<String>(),
            HurricaneStatus.CATEGORY_TWO, false
        ));
        assertFalse(facade.removeHurricane(hurricane));
        assertFalse(facade.addPermission(targetUser, Permission.VOLUNTEER));
        assertFalse(facade.removePermission(targetUser, Permission.VOLUNTEER));
        assertFalse(facade.addCredential(targetUser, Credential.PARAMEDIC));
        assertFalse(facade.removeCredential(targetUser, Credential.PARAMEDIC));
    }

    @Test
    public void testModifyOtherUserRequest() {
        User otherUser = createAdditionalUser();
        facade.login("5555552222", "test-password");
        Request request = facade.submitReliefRequest(EnumSet.of(RequestType.SHELTER_ASSISTANCE), false, TEST_LOCATION, "Need shelter");
        facade.logout(false);
        loginTestUser();

        assertFalse(facade.cancelReliefRequest(request));
        assertEquals(RequestStatus.SUBMITTED, request.getStatus());
        assertFalse(facade.updateRequestStatus(request, RequestStatus.CLOSED));
        assertFalse(facade.respondToRequest(request));
        assertFalse(facade.withdrawFromRequest(request));
    }
}
