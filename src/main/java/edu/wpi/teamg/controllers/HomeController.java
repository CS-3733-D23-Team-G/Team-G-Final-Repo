package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class HomeController {

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
    signagePageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
    exitButton.setOnMouseClicked(event -> exit());
    statusButton.setOnMouseClicked(event -> Navigation.navigate(Screen.STATUS_PAGE));

    serviceRequestChoiceBox.setItems(list);
    serviceRequestChoiceBox.setOnAction(event -> loadServiceRequestForm());
  }

  public void exit() {
    Platform.exit();
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
}
