package com.model;
public class HurricaneReliefUI {
    private HurricaneReliefSystem system;

    HurricaneReliefUI() {
        system = HurricaneReliefSystem.getInstance();
    }
    public void run() {
        scenario1();
        scenario2();
    }
    private void scenario1() {
        // [STUB] Implement scenario1 logic here
    }

    private void scenario2() {
        // [STUB] Implement scenario2 logic here
    }
    public static void main(String[] args) {
    HurricaneReliefUI hurricaneInterface = new HurricaneReliefUI();
    hurricaneInterface.run();
}
}
