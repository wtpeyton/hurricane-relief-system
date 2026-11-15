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
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Password: " + password);
        App.setRoot("home");
    }

    @FXML
    void createAccount(ActionEvent event) throws IOException {
        App.setRoot("create_account");
    }

    @FXML
    void continueAsGuest(ActionEvent event) throws IOException {
        App.setRoot("home");
    }
}
