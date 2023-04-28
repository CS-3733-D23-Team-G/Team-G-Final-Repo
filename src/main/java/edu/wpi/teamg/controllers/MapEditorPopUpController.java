package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
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

    confirmButton.setOnAction(
        event -> {
          App.message = messageText.getText();
        });
  }
}
