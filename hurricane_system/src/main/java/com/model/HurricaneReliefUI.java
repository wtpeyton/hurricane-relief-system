package com.model;
/**
 * HurricaneReliefUI is the user interface class for the Hurricane Relief System.
 * @author Ryan Kouchoukos, Olivia Casper
 */
public class HurricaneReliefUI {
    private HurricaneReliefSystem system;
    /**
     * Constructor for HurricaneReliefUI.
     */
    HurricaneReliefUI() {
        system = HurricaneReliefSystem.getInstance();
    }
    /**
     * Runs the Hurricane Relief System scenarios.
     */
    public void run() {
        scenario1();
        scenario2();
    }
    /**
     * Runs the login scenario using an existing user.
     */
    private void scenario1() {
        System.out.println();

        if (!system.login("8035550100", "password123")) {
            System.out.println("Sorry we couldn't login.");
            return;
        }

        System.out.println("Ada Lovelace is now logged in.");
    }
    /**
     * Runs the second scenario for the Hurricane Relief System.
     */
    private void scenario2() {
        User newUser = system.createAccount("Ryan","Kouchoukos","password123",
                                            "6033978909","29205",new double[] {34, -81});

    if (newUser == null) {
        System.out.println("Sorry, we could not create your account.");
        return;
    }

    System.out.println("Ryan K Account created successfully.");
}
    /**
     * Main method to run the Hurricane Relief System UI.
     */
    public static void main(String[] args) {
        HurricaneReliefUI hurricaneInterface = new HurricaneReliefUI();
        hurricaneInterface.run();
    }
}
