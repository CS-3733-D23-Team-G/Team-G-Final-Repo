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
  ObservableList<String> AdminList =
      FXCollections.observableArrayList(
          "Signage Page Editor",
          "Table View",
          "All Form Status",
          "Map Editor",
          "Add Employee",
          "Send Alert");

  @FXML
  public void initialize() {
    //    signagePageButton.setOnAction(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));

    //    serviceRequestChoiceBox.setValue("Service Request");
    //    statusButton.setText("Form Status");
    //    AdminChoiceBox.setValue("Admin Forms");
    //    signagePageButton.setText("PathFinding");

    signagePageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.PATHFINDING_PAGE));
    exitButton.setOnMouseClicked(event -> exit());
    statusButton.setOnMouseClicked(event -> Navigation.navigate(Screen.ADMIN_STATUS_PAGE));
    Logout.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_SCREENSAVER_PAGE));
    serviceRequestChoiceBox.setItems(list);
    serviceRequestChoiceBox.setOnAction(event -> loadServiceRequestForm());
    HomeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    AdminChoiceBox.setItems(AdminList);
    AdminChoiceBox.setOnAction(event -> AdminServiceForms());
  }

  public void exit() {
    Platform.exit();
  }

  public void AdminServiceForms() {
    if (AdminChoiceBox.getValue().equals("Signage Page Editor")) {
      Navigation.navigate(Screen.EDIT_SIGNAGE_PAGE);
    } else if (AdminChoiceBox.getValue().equals("Table View")) {
      Navigation.navigate(Screen.ADMIN_SIGNAGE_EDITOR);
    } else if (AdminChoiceBox.getValue().equals("All Form Status")) {
      Navigation.navigate(Screen.ADMIN_STATUS_PAGE);
    } else if (AdminChoiceBox.getValue().equals("Map Editor")) {
      Navigation.navigate(Screen.ADMIN_MAP_EDITOR);
    } else if (AdminChoiceBox.getValue().equals("Add Employee")) {
      Navigation.navigate(Screen.ADD_EMPLOYEE);
    } else if (AdminChoiceBox.getValue().equals("Send Alert")) {
      Navigation.navigate((Screen.ALERT_PAGE));
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
