package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class ChooseKioskPop {
  @FXML MFXButton setKioskOne;
  @FXML MFXButton setKioskTwo;

  @FXML
  public void initialize() {
    setKioskOne.setOnAction(event -> setKiosk(1));
    setKioskTwo.setOnAction(event -> setKiosk(2));
  }

  public void setKiosk(int i) {
    App.setKioskNumber(i);
    Navigation.navigate(Screen.SIGNAGE_SCREENSAVER_PAGE);
  }
}
