package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class HomeController {

  @FXML MFXButton QuickTestButton;
  @FXML MFXButton OtherTestButton;

  @FXML
  public void initialize() {
    QuickTestButton.setOnMouseClicked(event -> Navigation.navigate(Screen.EDIT_SIGNAGE_PAGE));
    OtherTestButton.setOnAction(event -> Navigation.navigate(Screen.SIGNAGE_SCREENSAVER_PAGE));
  }
}
