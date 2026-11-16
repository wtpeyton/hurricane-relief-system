package com.controllers;


import java.io.IOException;
import com.relief.App;
import com.model.HurricaneReliefSystem;
import com.model.Permission;
import com.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class HomeController {
    @FXML
    private Label greetingLabel;

    @FXML
    private void initialize() {
        HurricaneReliefSystem system = HurricaneReliefSystem.getInstance();
        User currentUser = system.getCurrentUser();
        if (currentUser == null && system.loginAsGuest()) {
            currentUser = system.getCurrentUser();
        }

        if (currentUser == null || !currentUser.hasPermission(Permission.REGISTERED_USER)) {
            greetingLabel.setText("Hello, Guest");
            return;
        }

        String firstName = currentUser.getFirstName();
        greetingLabel.setText("Hello, " + (firstName.isBlank() ? "User" : firstName));
    }

    @FXML
    private void switchToSecondary() throws IOException {
        HurricaneReliefSystem.getInstance().logout(false);
        App.setRoot("login_options");
    }
}
