package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PathfindingAlertPopUpController {

  @FXML Text messageText;

  String message;

  public void initialize() {

    if (App.message == null) {
      messageText.setText("There is currently no alerts.");

    } else {
      messageText.setText(App.message);
    }
  }
}
