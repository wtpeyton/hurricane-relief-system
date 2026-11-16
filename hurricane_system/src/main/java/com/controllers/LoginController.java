package com.controllers;


import java.io.IOException;

import com.model.HurricaneReliefSystem;
import com.relief.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController {
    @FXML
    private Label errorLabel;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField visiblePasswordField;

    @FXML
    private Button passwordVisibilityButton;

    @FXML
    private Button loginButton;

    @FXML
    void initialize() {
        visiblePasswordField.textProperty().bindBidirectional(passwordField.textProperty());
    }

    @FXML
    void togglePasswordVisibility(ActionEvent event) {
        boolean showPassword = !visiblePasswordField.isVisible();
        TextField currentField = showPassword ? passwordField : visiblePasswordField;
        TextField nextField = showPassword ? visiblePasswordField : passwordField;
        int anchor = currentField.getAnchor();
        int caretPosition = currentField.getCaretPosition();

        currentField.setVisible(false);
        nextField.setVisible(true);
        passwordVisibilityButton.setText(showPassword ? "Hide" : "Show");
        passwordVisibilityButton.setAccessibleText(showPassword ? "Hide password" : "Show password");
        nextField.requestFocus();
        nextField.selectRange(anchor, caretPosition);
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        String phoneNumber = phoneNumberField.getText();
        String password = passwordField.getText();
        if(HurricaneReliefSystem.getInstance().login(phoneNumber, password)){
            App.setRoot("home");    
        } else {
            showError("Could not find that user.");
        }
    }

    private void showError(String message){
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    @FXML
    void createAccount(ActionEvent event) throws IOException {
        App.setRoot("create_account");
    }

    @FXML
    void continueAsGuest(ActionEvent event) throws IOException {
        App.setRoot("home");
    }

    @FXML 
    void forgotPassword(ActionEvent event) throws IOException {
        App.setRoot("forgot_password");
    }
}
