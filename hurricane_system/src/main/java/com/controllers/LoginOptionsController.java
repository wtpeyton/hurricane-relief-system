package com.controllers;

import java.io.IOException;

import com.relief.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginOptionsController {
    @FXML
    private Button createAccountButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button continueAsGuestButton;

    @FXML
    void createAccount(ActionEvent event) throws IOException {
        App.setRoot("create_account");
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    void continueAsGuest(ActionEvent event) throws IOException {
        App.setRoot("home");
    }
}
