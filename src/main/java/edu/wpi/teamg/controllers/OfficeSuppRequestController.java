package edu.wpi.teamg.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class OfficeSuppRequestController {

  @FXML
  public void initialize() {}

  public void exit() {
    Platform.exit();
  }
}
