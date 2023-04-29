package edu.wpi.teamg.controllers;

import edu.wpi.teamg.ORMClasses.SpeechToText;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SpeechPopController {
  @FXML Label speakLabel;

  @FXML MFXButton speakButton;

  public void initialize() {
    speakLabel.setText("");
    speakButton.setOnMouseClicked(
        event -> {
          try {
            startListening();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  private void startListening() throws IOException {
    SpeechToText stt = new SpeechToText();
    stt.startCommand();
    speakLabel.setText("Speak Now");
    stt.detectCommand();
  }
}
