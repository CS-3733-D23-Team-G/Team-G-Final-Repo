package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FlowersRequestController {

  @FXML MFXButton returnHomeButton;
  @FXML ChoiceBox<String> serviceRequestChoiceBox;
  @FXML MFXButton signagePageButton;
  @FXML MFXButton exitButton;
  @FXML ChoiceBox<String> flowerTypeChoiceBox;
  @FXML MFXButton submit;
  @FXML MFXButton clearAll;
  @FXML TextField deliveryLocation;
  @FXML TextField orderingFor;
  @FXML TextArea notes;
  ObservableList<String> listFlowers =
      FXCollections.observableArrayList(
          "Roses", "Tulips", "Daisies", "Sunflowers", "Carnations", "Orchids");
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
    serviceRequestChoiceBox.setOnAction(event -> loadServiceRequestForm());

    flowerTypeChoiceBox.setItems(listFlowers);

    signagePageButton.setOnMouseClicked(
        event -> {
          Navigation.navigate(Screen.SIGNAGE_PAGE);
        });
    returnHomeButton.setOnMouseClicked(
        event -> {
          Navigation.navigate(Screen.HOME);
        });
    exitButton.setOnMouseClicked(event -> exit());
    //    serviceRequestChoiceBox.setOnMouseClicked(
    //        event -> {
    //          loadServiceRequestForm();
    //        });
    //    submit.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOWERS_REQUEST_SUBMIT));
    clearAll.setOnAction(event -> clearFlowers());
    submit.setOnAction(
        event -> {
          Navigation.navigate(Screen.FLOWERS_REQUEST_SUBMIT);
        });
    //    deliveryLocation.getText();
    //    orderingFor.getText();
    //    notes.setText("");
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

  public void clearFlowers() {
    flowerTypeChoiceBox.setValue("");
    deliveryLocation.setText("");
    orderingFor.setText("");
    notes.setText("");
  }

  public void exit() {
    Platform.exit();
  }
}
