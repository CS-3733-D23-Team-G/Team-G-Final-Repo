package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class LoginTopBannerController {

  @FXML MFXButton exitButton;


  @FXML
  public void initialize() {
    exitButton.setOnMouseClicked(event -> exit());
  }

  public void exit() {
    Platform.exit();
  }
}
