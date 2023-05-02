package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class TranslatePopOverController {
  @FXML TextArea userInput;

  @FXML TextArea translatedInput;

  @FXML MFXButton translateButton;

  //  private static final String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
  //  private static final String CLIENT_SECRET = "PUBLIC_SECRET";
  //  private static final String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";

  public void initialize() {
    userInput.setEditable(true);
    translatedInput.setVisible(true);
    translateButton.setOnMouseClicked(
        event -> {
          try {
            translate();
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void translate() throws Exception {
    // language, locationName, employeeID

  }
}
