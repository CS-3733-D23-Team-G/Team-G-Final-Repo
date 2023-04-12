package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class PatientTopBannerController {
  @FXML MFXButton exitButton;

  @FXML
  public void initialize() {
    //    signagePageButton.setOnAction(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
    exitButton.setOnMouseClicked(event -> exit());
  }

  public void exit() {
    Platform.exit();
  }
}
