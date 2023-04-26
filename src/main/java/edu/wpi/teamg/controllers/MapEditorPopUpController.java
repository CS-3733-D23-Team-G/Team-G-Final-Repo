package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class MapEditorPopUpController {

  @FXML TextArea messageText;
  @FXML Label moveChangeLabel;
  @FXML MFXButton confirmButton;
  String message;

  public void initialize() {
    messageText.setEditable(true);

    confirmButton.setOnMouseClicked(
        event -> {
          message = messageText.getText();
          getMessage();
        });
  }

  public String getMessage() {
    return this.message;
  }
}
