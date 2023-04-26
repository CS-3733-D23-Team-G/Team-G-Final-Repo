package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import javafx.fxml.FXML;
import org.controlsfx.control.PopOver;

public class ChooseKioskPop {
  @FXML MFXButton setKioskOne;
  @FXML MFXButton setKioskTwo;

  PopOver wind = new PopOver();

  @FXML
  public void initialize() {
    setKioskOne.setOnAction(event -> setKiosk(1));
    setKioskTwo.setOnAction(event -> setKiosk(2));
  }

  public void setKiosk(int i) {
    App.setKioskNumber(i);
    Navigation.navigate(Screen.SIGNAGE_SCREENSAVER_PAGE);
    wind.hide();
  }

  public void setWind(PopOver window) {
    this.wind = window;
  }
}
