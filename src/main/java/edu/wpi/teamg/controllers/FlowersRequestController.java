package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

  @FXML TextField deliveryDate;

  @FXML TextField deliveryTime;
  @FXML TextField recipient;
  @FXML TextField bouquetNote;
  @FXML Label checkFields;

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
          allDataFilled();
        });
    checkFields.getText();

    //    deliveryLocation.getText();
    //    orderingFor.getText();
    //    notes.setText("");

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
    deliveryDate.setText("");
    deliveryTime.setText("");
  }

  //flowerTypeCheckBox and bouquetSizeChoiceBox Do Not Properly Check if Filled
  public void allDataFilled() {
    if (!(deliveryTime.getText().equals("")
        || deliveryDate.getText().equals("")
        || deliveryLocation.getText().equals("")
        || recipient.getText().equals("")
        || bouquetNote.getText().equals("")
        || flowerTypeCheckBox.getCheckModel().equals("")
        || bouquetSizeChoiceBox.getValue().equals(""))) {

      storeFlowerValues();
      Navigation.navigate(Screen.FLOWERS_REQUEST_SUBMIT);
    } else {
      checkFields.setText("Not All Fields Are Filled");
    }
  }

  public void exit() {
    Platform.exit();
  }
}
