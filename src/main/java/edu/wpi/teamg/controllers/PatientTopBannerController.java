package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class PatientTopBannerController {
  @FXML MFXButton signagePageButton;
  @FXML MFXButton exitButton;

  @FXML MFXButton statusButton;
  @FXML ChoiceBox<String> serviceRequestChoiceBox;

  ObservableList<String> list =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");

  @FXML
  public void initialize() {
    //    signagePageButton.setOnAction(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));

    exitButton.setOnMouseClicked(event -> exit());

  }

  public void exit() {
    Platform.exit();
  }


}
