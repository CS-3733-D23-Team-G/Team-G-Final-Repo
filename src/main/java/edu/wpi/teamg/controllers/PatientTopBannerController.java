package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class PatientTopBannerController {
  @FXML MFXButton exitButton;

  @FXML MFXButton loginButton;

  @FXML
  public void initialize() {
    //    signagePageButton.setOnAction(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
    exitButton.setOnMouseClicked(event -> exit());
    loginButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOGIN_PAGE));
  }

  public void exit() {
    Platform.exit();
  }
}
