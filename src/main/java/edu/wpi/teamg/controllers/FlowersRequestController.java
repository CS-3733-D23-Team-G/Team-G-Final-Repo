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
import org.controlsfx.control.SearchableComboBox;
import org.controlsfx.control.CheckComboBox;


public class FlowersRequestController {

  @FXML MFXButton returnHomeButton;
  @FXML ChoiceBox<String> serviceRequestChoiceBox;
  @FXML MFXButton signagePageButton;
  @FXML MFXButton exitButton;

  @FXML ChoiceBox<String> bouquetSizeChoiceBox;
  @FXML CheckComboBox<String> flowerTypeCheckBox;
  @FXML MFXButton submit;
  @FXML MFXButton clearAll;
  @FXML TextField deliveryLocation;

  @FXML TextField orderingFor;
  @FXML
  TextArea notes;

  // Hung This is the name and list associated with test searchable list
  @FXML SearchableComboBox locationSearchDropdown;

  ObservableList<String> locationList =
      FXCollections.observableArrayList(
          "Room 1", "Blue Room", "Regal Room", "321 Room", "Help, I Need Somebody Room");

  @FXML TextField deliveryDate;

  @FXML TextField deliveryTime;
  @FXML TextField recipient;
  @FXML TextField bouquetNote;

  /*
   TODO: figure out how to get correct datatype to give to DB
  */


  ObservableList<String> listFlowers =
      FXCollections.observableArrayList(
          "Carnations", "Daisies", "Lilacs", "Orchids", "Roses", "Sunflowers");
  ObservableList<String> listSizes =
      FXCollections.observableArrayList(
          "10 Stems (small)", "20 Stems (medium)", "30 Stems (large)");
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
    bouquetSizeChoiceBox.setItems(listSizes);
    flowerTypeCheckBox.getItems().addAll(listFlowers);

    signagePageButton.setOnMouseClicked(
        event -> {
          Navigation.navigate(Screen.SIGNAGE_PAGE);
        });
    returnHomeButton.setOnMouseClicked(
        event -> {
          Navigation.navigate(Screen.HOME);
        });
    exitButton.setOnMouseClicked(event -> exit());
    clearAll.setOnAction(event -> clearFlowers());
    submit.setOnAction(
        event -> {
          storeFlowerValues();
          Navigation.navigate(Screen.FLOWERS_REQUEST_SUBMIT);
        });
    //    deliveryLocation.getText();
    //    orderingFor.getText();
    //    notes.setText("");


    // Hung this is where it sets the list - Andrew
    locationSearchDropdown.setItems(locationList);

    //    serviceRequestChoiceBox.setOnMouseClicked(
    //        event -> {
    //          loadServiceRequestForm();
    //        });
    //    submit.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOWERS_REQUEST_SUBMIT));

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

  public void storeFlowerValues() {
    System.out.println(
        "Delivery Location: "
            + deliveryLocation.getText()
            + "\nRecipient: "
            + recipient.getText()
            + "\nBouquet Note: "
            + bouquetNote.getText()
            + "\nDelivery Time: "
            + deliveryTime.getText()
            + "\nDelivery Date: "
            + deliveryDate.getText()
            + "\nBouquet Size: "
            + bouquetSizeChoiceBox.getValue());
  }

  public int bouquetSizeToInt(String s) {
    return -1;
  }

  // TODO: figure out clear for flowerTypeCheckBox
  public void clearFlowers() {
    bouquetSizeChoiceBox.setValue("");
    flowerTypeCheckBox.setCheckModel(null);
    deliveryLocation.setText("");
    recipient.setText("");
    bouquetNote.setText("");
  }

  public void exit() {
    Platform.exit();
  }
}
