package com.model;

import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class ShelterTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testNewShelterHasZeroOccupants() {
        Shelter shelter = new Shelter(
            "Test Shelter",
            new double[] {34.0007, -81.0348},
            3, true,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, null,
            new ArrayList<Person>(),
            new ArrayList<String>()
        );

        assertEquals(0, shelter.getCurrentOccupancy());
    }

    @Test
    public void testEmptyShelterAvailableSpaces() {
        Shelter shelter = new Shelter(
            "Test Shelter",
            new double[] {34.0007, -81.0348},
            3, true,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, null,
            new ArrayList<Person>(),
            new ArrayList<String>()
        );

        assertEquals(3, shelter.getAvailableSpaces());
    }

    @Test
    public void testSuccessfulCheckIn() {
        Shelter shelter = new Shelter(
            "Test Shelter",
            new double[] {34.0007, -81.0348},
            3, true,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, null,
            new ArrayList<Person>(),
            new ArrayList<String>()
        );

        Person person = new Person("Amy Smith", 35);
        ArrayList<Person> people = new ArrayList<Person>();
        people.add(person);

        boolean result = shelter.checkIn(
            people, new ArrayList<String>()
        );

        assertTrue(result);
        assertTrue(shelter.getOccupants().contains(person));
    }

    @Test
    public void testCheckInUpdatesOccupancyAndSpaces() {
        Shelter shelter = new Shelter(
            "Test Shelter",
            new double[] {34.0007, -81.0348},
            3, true,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, null,
            new ArrayList<Person>(),
            new ArrayList<String>()
        );

        ArrayList<Person> people = new ArrayList<Person>();
        people.add(new Person("Amy Smith", 35));
        people.add(new Person("John Smith", 36));

        assertTrue(shelter.checkIn(people, new ArrayList<String>()));

        assertEquals(2, shelter.getCurrentOccupancy());
        assertEquals(1, shelter.getAvailableSpaces());
    }

    @Test
    public void testCheckInExactlyFillsShelter() {
        Shelter shelter = new Shelter(
            "Test Shelter",
            new double[] {34.0007, -81.0348},
            3, true,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, null,
            new ArrayList<Person>(),
            new ArrayList<String>()
        );

        ArrayList<Person> people = new ArrayList<Person>();
        people.add(new Person("Amy Smith", 35));
        people.add(new Person("John Smith", 36));
        people.add(new Person("Sam Smith", 8));

        boolean result = shelter.checkIn(
            people, new ArrayList<String>()
        );

        assertTrue(result);
        assertEquals(3, shelter.getCurrentOccupancy());
        assertEquals(0, shelter.getAvailableSpaces());
        assertTrue(shelter.getOccupants().containsAll(people));
    }

    @Test
    public void testFullShelterRejectsCheckIn() {
        ArrayList<Person> occupants = new ArrayList<Person>();
        occupants.add(new Person("Amy Smith", 35));
        occupants.add(new Person("John Smith", 36));
        occupants.add(new Person("Sam Smith", 8));

        Shelter shelter = new Shelter(
            "Test Shelter",
            new double[] {34.0007, -81.0348},
            3, true,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, null,
            occupants,
            new ArrayList<String>()
        );

        Person person = new Person("Jane Doe", 25);
        ArrayList<Person> people = new ArrayList<Person>();
        people.add(person);

        boolean result = shelter.checkIn(
            people, new ArrayList<String>()
        );

        assertFalse(result);
        assertEquals(3, shelter.getCurrentOccupancy());
        assertEquals(0, shelter.getAvailableSpaces());
        assertFalse(shelter.getOccupants().contains(person));
    }

    @Test
    public void testGroupExceedsAvailableSpaces() {
        Person existingPerson = new Person("Amy Smith", 35);
        ArrayList<Person> occupants = new ArrayList<Person>();
        occupants.add(existingPerson);

        Shelter shelter = new Shelter(
            "Test Shelter",
            new double[] {34.0007, -81.0348},
            3, true,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, null,
            occupants,
            new ArrayList<String>()
        );

        ArrayList<Person> people = new ArrayList<Person>();
        people.add(new Person("Jane Doe", 25));
        people.add(new Person("John Doe", 26));
        people.add(new Person("Sam Doe", 8));

        ArrayList<String> pets = new ArrayList<String>();
        pets.add("Buddy");

        boolean result = shelter.checkIn(people, pets);

        assertFalse(result);
        assertEquals(1, shelter.getCurrentOccupancy());
        assertEquals(2, shelter.getAvailableSpaces());
        assertTrue(shelter.getOccupants().contains(existingPerson));

        for (Person person : people) {
            assertFalse(shelter.getOccupants().contains(person));
        }

        assertTrue(shelter.getPets().isEmpty());
    }

    @Test
    public void testPetFriendlyCheckIn() {
        Shelter shelter = new Shelter(
            "Test Shelter",
            new double[] {34.0007, -81.0348},
            3, true,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, null,
            new ArrayList<Person>(),
            new ArrayList<String>()
        );

        Person person = new Person("Amy Smith", 35);
        ArrayList<Person> people = new ArrayList<Person>();
        people.add(person);

        ArrayList<String> pets = new ArrayList<String>();
        pets.add("Buddy");

        boolean result = shelter.checkIn(people, pets);

        assertTrue(result);
        assertTrue(shelter.getOccupants().contains(person));
        assertEquals(1, shelter.getPets().size());
        assertTrue(shelter.getPets().contains("Buddy"));
        assertEquals(1, shelter.getCurrentOccupancy());
        assertEquals(2, shelter.getAvailableSpaces());
    }

    @Test
    public void testNoPetsShelterRejectsPets() {
        Shelter shelter = new Shelter(
            "Test Shelter",
            new double[] {34.0007, -81.0348},
            3, false,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, null,
            new ArrayList<Person>(),
            new ArrayList<String>()
        );

        ArrayList<Person> people = new ArrayList<Person>();
        people.add(new Person("Amy Smith", 35));

        ArrayList<String> pets = new ArrayList<String>();
        pets.add("Buddy");

        boolean result = shelter.checkIn(people, pets);

        assertFalse(result);
        assertEquals(0, shelter.getCurrentOccupancy());
        assertEquals(3, shelter.getAvailableSpaces());
        assertTrue(shelter.getPets().isEmpty());
    }

    @Test
    public void testNoPetsShelterAcceptsPeople() {
        Shelter shelter = new Shelter(
            "Test Shelter",
            new double[] {34.0007, -81.0348},
            3, false,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, null,
            new ArrayList<Person>(),
            new ArrayList<String>()
        );

        Person person = new Person("Amy Smith", 35);
        ArrayList<Person> people = new ArrayList<Person>();
        people.add(person);

        boolean result = shelter.checkIn(
            people, new ArrayList<String>()
        );

        assertTrue(result);
        assertTrue(shelter.getOccupants().contains(person));
        assertEquals(1, shelter.getCurrentOccupancy());
        assertTrue(shelter.getPets().isEmpty());
    }

    @Test
    public void testNullPeopleList() {
        Shelter shelter = new Shelter(
            "Test Shelter",
            new double[] {34.0007, -81.0348},
            3, true,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, null,
            new ArrayList<Person>(),
            new ArrayList<String>()
        );

        ArrayList<String> pets = new ArrayList<String>();
        pets.add("Buddy");

        boolean result = shelter.checkIn(null, pets);

        assertFalse(result);
        assertEquals(0, shelter.getCurrentOccupancy());
        assertEquals(3, shelter.getAvailableSpaces());
        assertTrue(shelter.getPets().isEmpty());
    }

    @Test
    public void testNullPetsList() {
        Shelter shelter = new Shelter(
            "Test Shelter",
            new double[] {34.0007, -81.0348},
            3, true,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, null,
            new ArrayList<Person>(),
            new ArrayList<String>()
        );

        ArrayList<Person> people = new ArrayList<Person>();
        people.add(new Person("Amy Smith", 35));

        boolean result = shelter.checkIn(people, null);

        assertFalse(result);
        assertEquals(0, shelter.getCurrentOccupancy());
        assertEquals(3, shelter.getAvailableSpaces());
        assertTrue(shelter.getPets().isEmpty());
    }
    @Test
public void testShelterEligibilityForCarterFamily() {
    Shelter shelter = new Shelter(
        "Charleston Test Shelter",
        new double[] {32.7800, -79.9400},
        3, true,
        new ArrayList<ReliefResource>(),
        MedicalService.HIGH_SERVICE,
        true, Accessibility.VERY_ACCESSIBLE, null,
        new ArrayList<Person>(),
        new ArrayList<String>()
    );

    ArrayList<Person> people = new ArrayList<Person>();
    people.add(new Person("James Carter", 38));
    people.add(new Person("Emily Carter", 36));
    people.add(new Person("Sophie Carter", 9));

    ArrayList<String> pets = new ArrayList<String>();
    pets.add("Buddy");

    assertTrue(shelter.getAvailableSpaces() >= people.size());
    assertTrue(shelter.isPetAcceptance());
    assertEquals(
        MedicalService.HIGH_SERVICE,
        shelter.getMedicalService()
    );

    boolean result = shelter.checkIn(people, pets);

    assertTrue(result);
    assertTrue(shelter.getOccupants().containsAll(people));
    assertEquals(3, shelter.getCurrentOccupancy());
    assertEquals(0, shelter.getAvailableSpaces());
    assertEquals(pets, shelter.getPets());
}

@Test
public void testCarterFamilyAndBuddySaveAndReload() throws Exception {
    File testFile = temporaryFolder.newFile("shelters.json");

    UserList users = UserList.getInstance();

    User admin = new User(
        "Felix", "Smith", "password123",
        "8035551234", "29201",
        new double[] {32.7800, -79.9400}
    );

    users.addUser(admin);

    try {
        Shelter shelter = new Shelter(
            "Charleston Test Shelter",
            new double[] {32.7800, -79.9400},
            5, true,
            new ArrayList<ReliefResource>(),
            MedicalService.HIGH_SERVICE,
            true, Accessibility.VERY_ACCESSIBLE, admin,
            new ArrayList<Person>(),
            new ArrayList<String>()
        );

        ArrayList<Person> people = new ArrayList<Person>();
        people.add(new Person("James Carter", 38));
        people.add(new Person("Emily Carter", 36));
        people.add(new Person("Sophie Carter", 9));

        ArrayList<String> pets = new ArrayList<String>();
        pets.add("Buddy");

        assertTrue(shelter.checkIn(people, pets));

        ArrayList<Shelter> shelters = new ArrayList<Shelter>();
        shelters.add(shelter);

        assertTrue(DataWriter.saveShelters(
            shelters, testFile.getAbsolutePath()
        ));

        ArrayList<Shelter> loadedShelters = DataLoader.getShelters(
            testFile.getAbsolutePath()
        );

        assertEquals(1, loadedShelters.size());

        Shelter loadedShelter = loadedShelters.get(0);

        assertNotSame(shelter, loadedShelter);
        assertEquals(
            shelter.getShelterId(),
            loadedShelter.getShelterId()
        );

        assertEquals(3, loadedShelter.getOccupants().size());

        for (int i = 0; i < people.size(); i++) {
            Person expected = people.get(i);
            Person actual = loadedShelter.getOccupants().get(i);

            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getAge(), actual.getAge());
            assertNotSame(expected, actual);
        }

        assertEquals(pets, loadedShelter.getPets());
        assertEquals(5, loadedShelter.getCapacity());
        assertEquals(3, loadedShelter.getCurrentOccupancy());
        assertEquals(2, loadedShelter.getAvailableSpaces());

        assertNotNull(loadedShelter.getShelterAdmin());
        assertEquals(
            admin.getUserId(),
            loadedShelter.getShelterAdmin().getUserId()
        );
    } finally 
    {
        users.removeUser(admin);
    }
}
@Test
public void testNewShelterConstructorValues() {
    double[] location = new double[] {34.0007, -81.0348};

    User admin = new User(
        "Felix", "Smith", "password123",
        "8035551234", "29201", location
    );

    ArrayList<ReliefResource> supplies = new ArrayList<ReliefResource>();
    supplies.add(new ReliefResource("Blankets", 10, location));

    ArrayList<Person> occupants = new ArrayList<Person>();
    occupants.add(new Person("James Carter", 38));

    ArrayList<String> pets = new ArrayList<String>();
    pets.add("Buddy");

    Shelter shelter = new Shelter(
        "Test Shelter", location, 10, true,
        supplies, MedicalService.HIGH_SERVICE,
        true, Accessibility.VERY_ACCESSIBLE,
        admin, occupants, pets
    );

    assertNotNull(shelter.getShelterId());
    assertEquals("Test Shelter", shelter.getName());
    assertArrayEquals(location, shelter.getLocation(), 0.000001);
    assertEquals(10, shelter.getCapacity());
    assertTrue(shelter.isPetAcceptance());
    assertEquals(supplies, shelter.getSupplies());
    assertEquals(MedicalService.HIGH_SERVICE, shelter.getMedicalService());
    assertTrue(shelter.isVetService());
    assertEquals(Accessibility.VERY_ACCESSIBLE, shelter.getAccessibility());
    assertEquals(admin, shelter.getShelterAdmin());
    assertEquals(occupants, shelter.getOccupants());
    assertEquals(pets, shelter.getPets());
    assertEquals(1, shelter.getCurrentOccupancy());
    assertEquals(9, shelter.getAvailableSpaces());
}

@Test
public void testExistingShelterConstructorValues() {
    UUID shelterId = UUID.randomUUID();
    double[] location = new double[] {32.7800, -79.9400};

    User admin = new User(
        "Felix", "Smith", "password123",
        "8035551234", "29201", location
    );

    ArrayList<ReliefResource> supplies = new ArrayList<ReliefResource>();
    supplies.add(new ReliefResource("Water", 20, location));

    ArrayList<Person> occupants = new ArrayList<Person>();
    occupants.add(new Person("Emily Carter", 36));

    ArrayList<String> pets = new ArrayList<String>();
    pets.add("Buddy");

    Shelter shelter = new Shelter(
        shelterId, "Saved Shelter", location, 20, true,
        supplies, MedicalService.AVERAGE_SERVICE,
        false, Accessibility.MODERATELY_ACCESSIBLE,
        admin, occupants, pets
    );

    assertEquals(shelterId, shelter.getShelterId());
    assertEquals("Saved Shelter", shelter.getName());
    assertArrayEquals(location, shelter.getLocation(), 0.000001);
    assertEquals(20, shelter.getCapacity());
    assertTrue(shelter.isPetAcceptance());
    assertEquals(supplies, shelter.getSupplies());
    assertEquals(MedicalService.AVERAGE_SERVICE, shelter.getMedicalService());
    assertFalse(shelter.isVetService());
    assertEquals(
        Accessibility.MODERATELY_ACCESSIBLE,
        shelter.getAccessibility()
    );
    assertEquals(admin, shelter.getShelterAdmin());
    assertEquals(occupants, shelter.getOccupants());
    assertEquals(pets, shelter.getPets());
    assertEquals(1, shelter.getCurrentOccupancy());
    assertEquals(19, shelter.getAvailableSpaces());
}

@Test
public void testShelterSettersUpdateValues() {
    Shelter shelter = new Shelter(
        "Original Shelter",
        new double[] {34.0007, -81.0348},
        3, false,
        new ArrayList<ReliefResource>(),
        MedicalService.MINIMUM_SERVICE,
        false, Accessibility.NOT_ACCESSIBLE, null,
        new ArrayList<Person>(),
        new ArrayList<String>()
    );

    UUID originalId = shelter.getShelterId();
    double[] newLocation = new double[] {32.7800, -79.9400};

    User admin = new User(
        "Felix", "Smith", "password123",
        "8035551234", "29201", newLocation
    );

    ArrayList<ReliefResource> supplies = new ArrayList<ReliefResource>();
    supplies.add(new ReliefResource("Blankets", 25, newLocation));

    ArrayList<Person> occupants = new ArrayList<Person>();
    occupants.add(new Person("James Carter", 38));
    occupants.add(new Person("Emily Carter", 36));

    ArrayList<String> pets = new ArrayList<String>();
    pets.add("Buddy");

    shelter.setName("Updated Shelter");
    assertEquals("Updated Shelter", shelter.getName());

    shelter.setLocation(newLocation);
    assertArrayEquals(newLocation, shelter.getLocation(), 0.000001);

    shelter.setCapacity(10);
    assertEquals(10, shelter.getCapacity());
    assertEquals(10, shelter.getAvailableSpaces());

    shelter.setPetAcceptance(true);
    assertTrue(shelter.isPetAcceptance());

    shelter.setSupplies(supplies);
    assertEquals(supplies, shelter.getSupplies());

    shelter.setMedicalService(MedicalService.HIGH_SERVICE);
    assertEquals(MedicalService.HIGH_SERVICE, shelter.getMedicalService());

    shelter.setVetService(true);
    assertTrue(shelter.isVetService());

    shelter.setAccessibility(Accessibility.VERY_ACCESSIBLE);
    assertEquals(Accessibility.VERY_ACCESSIBLE, shelter.getAccessibility());

    shelter.setShelterAdmin(admin);
    assertEquals(admin, shelter.getShelterAdmin());

    shelter.setOccupants(occupants);
    assertEquals(occupants, shelter.getOccupants());
    assertEquals(2, shelter.getCurrentOccupancy());
    assertEquals(8, shelter.getAvailableSpaces());

    shelter.setPets(pets);
    assertEquals(pets, shelter.getPets());
    assertEquals(2, shelter.getCurrentOccupancy());

    assertEquals(originalId, shelter.getShelterId());
}

@Test
public void testShelterToString() {
    Shelter shelter = new Shelter(
        "Test Shelter",
        new double[] {34.0007, -81.0348},
        3, true,
        new ArrayList<ReliefResource>(),
        MedicalService.HIGH_SERVICE,
        false, Accessibility.VERY_ACCESSIBLE, null,
        new ArrayList<Person>(),
        new ArrayList<String>()
    );

    String result = shelter.toString();

    assertTrue(result.startsWith("Shelter{"));
    assertTrue(result.contains("petAcceptance=true"));
    assertTrue(result.contains("supplies=[]"));
    assertTrue(result.contains("medicalService=HIGH_SERVICE"));
    assertTrue(result.contains("vetService=false"));
    assertTrue(result.contains("accessibility=VERY_ACCESSIBLE"));
    assertTrue(result.contains("shelterAdmin=null"));
    assertTrue(result.endsWith("}"));
}
}