package com.controllers;

import java.io.IOException;

import com.model.HurricaneReliefSystem;
import com.model.User;
import com.relief.App;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ForgotPasswordController {
    @FXML 
    private TextField phoneNumberField;

    @FXML
    private Label passwordLabel;

    @FXML 
    private Label errorLabel;

    @FXML 
    void login(ActionEvent event) throws IOException {
        App.setRoot("login");
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
    void findPassword(ActionEvent event) throws IOException {
        String phoneNumber = phoneNumberField.getText();
        User user = HurricaneReliefSystem.getInstance().getUserList().findUserByPhone(phoneNumber);
        if(user == null){
            passwordLabel.setVisible(true);
            passwordLabel.setManaged(true);
            showMessage("No account found with that phone number.");
            return;
        }

        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        passwordLabel.setText("Here is your password: " + user.getPassword());
        passwordLabel.setVisible(true);
        passwordLabel.setManaged(true);
         
    }

    private void showMessage(String message){
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }
}
