package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SpeechPopController {
    @FXML
    Label speakLabel;

    @FXML
    MFXButton speakButton;

    public void initialize(){
        speakLabel.setText("");
        speakButton.setOnMouseClicked(event -> startListening());
    }

    private void startListening() {

    }
}
