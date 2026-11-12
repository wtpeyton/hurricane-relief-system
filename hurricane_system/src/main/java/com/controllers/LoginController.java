package com.controllers;


import java.io.IOException;
import javafx.event.ActionEvent;
import com.relief.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import com.model.*;


public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    void back(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML
    void login(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
    }
}
