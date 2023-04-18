package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;

public class LoginTopBannerController {

  @FXML MFXButton exitButton;
  @FXML MFXButton logoButton;

  @FXML
  public void initialize() {

    exitButton.setOnMouseClicked(event -> exit());
    logoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_SCREENSAVER_PAGE));
  }

  public void exit() {
    Platform.exit();
  }
}
