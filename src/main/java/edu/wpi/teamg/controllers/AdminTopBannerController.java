package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class AdminTopBannerController {
  @FXML MFXButton signagePageButton;
  @FXML MFXButton exitButton;

  @FXML MFXButton statusButton;
  @FXML ChoiceBox<String> serviceRequestChoiceBox;
  @FXML MFXButton HomeButton;
  @FXML MFXButton Logout;

  @FXML ChoiceBox<String> AdminChoiceBox;
  ObservableList<String> list =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");
  ObservableList<String> Adminlist =
      FXCollections.observableArrayList("Signage Page Editor", "Signage Page Admin", "Admin Forms");

  @FXML
  public void initialize() {
    //    signagePageButton.setOnAction(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
    signagePageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.PATHFINDING_PAGE));
    exitButton.setOnMouseClicked(event -> exit());
    statusButton.setOnMouseClicked(event -> Navigation.navigate(Screen.ADMIN_STATUS_PAGE));
    Logout.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_SCREENSAVER_PAGE));
    serviceRequestChoiceBox.setItems(list);
    serviceRequestChoiceBox.setOnAction(event -> loadServiceRequestForm());
    HomeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    AdminChoiceBox.setItems(Adminlist);
    AdminChoiceBox.setOnAction(event -> AdminServiceFroms());
  }

  public void exit() {
    Platform.exit();
  }

  public void AdminServiceFroms() {
    if (serviceRequestChoiceBox.getValue().equals("Signage Page Editor")) {
      Navigation.navigate(Screen.ADMIN_SIGNAGE_EDITOR);
    } else if (serviceRequestChoiceBox.getValue().equals("Signage Page Admin")) {
      Navigation.navigate(Screen.ADMIN_SIGNAGE_EDITOR);
    } else if (serviceRequestChoiceBox.getValue().equals("Admin Forms")) {
      Navigation.navigate(Screen.ADMIN_STATUS_PAGE);
    } else {
      return;
    }
  }

  public void loadServiceRequestForm() {
    if (serviceRequestChoiceBox.getValue().equals("Meal Request Form")) {
      Navigation.navigate(Screen.MEAL_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Furniture Request Form")) {
      Navigation.navigate(Screen.FURNITURE_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Conference Room Request Form")) {
      Navigation.navigate(Screen.ROOM_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Flowers Request Form")) {
      Navigation.navigate(Screen.FLOWERS_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Office Supplies Request Form")) {
      Navigation.navigate(Screen.SUPPLIES_REQUEST);
    } else {
      return;
    }
  }
}
