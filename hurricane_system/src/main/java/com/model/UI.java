package com.model;

import com.location.Address;
import com.location.MapTools;

public class UI {

    public void run() {
        // A Columbia address near the USC Horseshoe
        Address userAddress = new Address(
                "701 Sumter Street",
                "Columbia",
                "SC",
                "29208"
        );
        Address closestShelter;

        // Example hurricane-shelter locations around Columbia
        Address[] shelters = {
                new Address("3319 Millwood Avenue",
                        "Columbia", "SC", "29205"), // Dreher High School

                new Address("4800 Monticello Road",
                        "Columbia", "SC", "29203"), // Eau Claire High School

                new Address("5900 Powell Road",
                        "Columbia", "SC", "29203"), // W.J. Keenan High School

                new Address("1 Falcon Drive",
                        "Columbia", "SC", "29204"), // A.C. Flora High School

                new Address("7500 Brookfield Road",
                        "Columbia", "SC", "29223"), // Richland Northeast High School

                new Address("120 Sparkleberry Lane",
                        "Columbia", "SC", "29229"), // Spring Valley High School

                new Address("4801 Hardscrabble Road",
                        "Columbia", "SC", "29229"), // Ridge View High School

                new Address("800 Langford Road",
                        "Blythewood", "SC", "29016"), // Blythewood High School

                new Address("1 Redhawk Drive",
                        "Blythewood", "SC", "29016"), // Westwood High School

                new Address("2600 Lower Richland Boulevard",
                        "Hopkins", "SC", "29061") // Lower Richland High School
        };

        try {
            closestShelter = MapTools.getInstance()
                    .findClosestAddress(userAddress, shelters);

            System.out.println("User address: "
                    + userAddress);

            System.out.println("Closest shelter: "
                    + closestShelter);

            String[] directions = MapTools.getInstance().getDrivingDirections(userAddress, closestShelter);

            for (String direction : directions) {
                System.out.println(direction);
            }

        } catch (Exception e) {
            System.out.println("There was a problem finding a shelter.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new UI().run();
    }
}