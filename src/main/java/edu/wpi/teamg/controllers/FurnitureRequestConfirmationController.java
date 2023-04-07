package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class FurnitureRequestConfirmationController {

  @FXML MFXButton backToHomeButton;
  @FXML ChoiceBox<String> serviceRequestChoiceBox;
  @FXML MFXButton signagePageButton;
  @FXML MFXButton exitButton;

  ObservableList<String> list =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");

  @FXML
  public void initialize() {
    serviceRequestChoiceBox.setItems(list);
    signagePageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
    backToHomeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    exitButton.setOnMouseClicked(event -> exit());
    serviceRequestChoiceBox.setOnAction(event -> loadServiceRequestForm());
  }

  public void loadServiceRequestForm() {
    switch (serviceRequestChoiceBox.getValue()) {
      case "Meal Request Form" -> Navigation.navigate(Screen.MEAL_REQUEST);
      case "Furniture Request Form" -> Navigation.navigate(Screen.FURNITURE_REQUEST);
      case "Conference Request Form" -> Navigation.navigate(Screen.ROOM_REQUEST);
      case "Flowers Request Form" -> Navigation.navigate(Screen.FLOWERS_REQUEST);
      case "Office Supplies Request Form" -> Navigation.navigate(Screen.SUPPLIES_REQUEST);
      default -> {
      }
    }
  }

  public void exit() {
    Platform.exit();
  }
}
