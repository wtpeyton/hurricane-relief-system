package com.model;
/**
 * HurricaneReliefUI is the user interface class for the Hurricane Relief System.
 * @author Ryan Kouchoukos
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
        //scenario1();
        scenario2();
    }
    private void scenario1() {
        // [STUB] Implement scenario1 logic here
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
