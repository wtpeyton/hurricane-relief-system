package com.controllers;

import java.io.IOException;

import com.relief.App;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.util.Duration;

public class LoadingController {

    @FXML
    public void initialize() {
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(e -> {
            try {
                App.setRoot("login_options");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        delay.play();
    }
}
