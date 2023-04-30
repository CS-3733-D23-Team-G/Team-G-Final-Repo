package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import javafx.fxml.FXML;

public class CreditsController {

  @FXML MFXButton Credits;

  public void initialize() {
    Credits.setOnMouseClicked(event -> Navigation.navigate(Screen.ABOUT_PAGE));
  }
}
