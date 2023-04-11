package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.FlowerRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;
import org.controlsfx.control.IndexedCheckModel;

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
  @FXML Label checkFields;

  @FXML TextField orderingFor;
  @FXML TextArea notes;

  // Hung This is the name and list associated with test searchable list
  // @FXML SearchableComboBox locationSearchDropdown;

  ObservableList<String> locationList =
      FXCollections.observableArrayList(
          "Room 1", "Blue Room", "Regal Room", "321 Room", "Help, I Need Somebody Room");

  @FXML MFXDatePicker deliveryDate;

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

    checkFields.getText();

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
    //    deliveryLocation.getText();
    //    orderingFor.getText();
    //    notes.setText("");

    // Hung this is where it sets the list - Andrew
    // locationSearchDropdown.setItems(locationList);

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
  /*
  String reqtype,
        int empid,
        String location,
        int serv_by,
        StatusTypeEnum status,
        Date deliveryDate,
        Time deliveryTime,
        String flowerType,
        int numFlower,
        String note,
        String recipient
   */
  public void storeFlowerValues() throws SQLException {
    FlowerRequest flower =
        new FlowerRequest(
            "FL",
            1,
            deliveryLocation.getText(),
            1,
            StatusTypeEnum.blank,
            Date.valueOf(deliveryDate.getValue()),
            StringToTime(deliveryTime.getText()),
            mutipleFlowers(flowerTypeCheckBox.getCheckModel()),
            flowerConvert(bouquetSizeChoiceBox.getValue()),
            bouquetNote.getText(),
            recipient.getText());
    DAORepo f1 = new DAORepo();
    f1.insertFlowerRequest(flower);
    /*
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
            + bouquetSizeChoiceBox.getValue());*/
  }

  public int bouquetSizeToInt(String s) {
    return -1;
  }

  // TODO: figure out clear for flowerTypeCheckBox
  public void clearFlowers() {
    bouquetSizeChoiceBox.setValue("");
    flowerTypeCheckBox.setCheckModel(null);
    deliveryLocation.setText("");
    deliveryTime.setText("");
    recipient.setText("");
    bouquetNote.setText("");
    deliveryDate.setText("");
  }

  public void allDataFilled() {
    if (!(bouquetSizeChoiceBox == null
        || flowerTypeCheckBox == null
        || deliveryLocation.getText().equals("")
        || recipient.getText().equals("")
        || deliveryTime.getText().equals("")
        || bouquetNote.getText().equals(""))) {
      try {
        storeFlowerValues();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      Navigation.navigate(Screen.FLOWERS_REQUEST_SUBMIT);
    } else {
      checkFields.setText("Not All Fields Are Filled");
    }
  }

  public Time StringToTime(String s) {

    String[] hourMin = s.split(":", 2);
    Time t = new Time(Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]), 00);
    return t;
  }

  public int flowerConvert(String t) {
    int i = 0;
    switch (t) {
      case "10 Stems (small)":
        i = 10;
        break;
      case "20 Stems (medium)":
        i = 20;
        break;
      case "30 Stems (large)":
        i = 30;
        break;
      default:
        i = -1;
    }
    return i;
  }

  public String mutipleFlowers(IndexedCheckModel<String> f1) {
    String s1 = "";
    for (int i = 0; i < f1.getItemCount(); i++) {
      if (!(f1.getCheckedItems().get(i) == null)) {
        s1 += f1.getCheckedItems().get(i) + ", ";
      }
    }
    return s1;
  }

  public void exit() {
    Platform.exit();
  }
}
