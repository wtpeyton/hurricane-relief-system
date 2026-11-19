package com.controllers;


import java.io.IOException;
import javafx.event.ActionEvent;
import com.relief.App;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import com.model.*;
import com.location.Address;
import com.location.MapTools;


public class CreateAccountController {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField addressField;

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
    void createAccount(ActionEvent event) throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String phoneNumber = phoneNumberField.getText();
        String password = passwordField.getText();
        String addressText = addressField.getText().trim();
        HurricaneReliefSystem system = HurricaneReliefSystem.getInstance();

        try {
            Address address = Address.parse(addressText);
            // Make sure the map service can find this address before creating the account
            MapTools.getInstance().getCoordinates(address);
            User user = system.createAccount(firstName, lastName, password,
                    phoneNumber, address);

            if (user == null) {
                showError("Account could not be created. Check your information and try again.");
                return;
            }

            if (!system.getUserList().saveUsers()) {
                showWarning("Your account was created for this session but could not be saved. "
                        + "It may not be available after the application closes.");
            }

            if (system.login(phoneNumber, password)) {
                App.setRoot("home");
            } else {
                showError("Account was created, but automatic sign-in failed. Please log in.");
                App.setRoot("login");
            }
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
        } catch (IOException | IllegalStateException e) {
            showError("Unable to find that address: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            showError("Location lookup was interrupted. Please try again.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Could not create account");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Account created but not saved");
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    void continueAsGuest(ActionEvent event) throws IOException {
        HurricaneReliefSystem system = HurricaneReliefSystem.getInstance();

        if(system.loginAsGuest())
            App.setRoot("home");
    }
}
