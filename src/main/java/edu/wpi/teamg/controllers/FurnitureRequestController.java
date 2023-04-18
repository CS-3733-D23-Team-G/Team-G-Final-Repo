package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.FurnitureRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import org.controlsfx.control.SearchableComboBox;

public class FurnitureRequestController {
  @FXML MFXButton furnSubmitButton;

  @FXML MFXButton furnClearAll;

  @FXML MFXDatePicker furnDate;

  // TextFields
  @FXML MFXTextField furnTimeOfDeliver;

  @FXML MFXTextField furnRecipient;

  @FXML MFXTextField furnNotesData;

  @FXML ImageView selectedChair;
  @FXML ImageView chairOption;
  @FXML ImageView selectedCouch;
  @FXML ImageView couchOption;
  @FXML ImageView selectedTable;
  @FXML ImageView tableOption;

  // @FXML ChoiceBox<String> mealFoodChoice;
  @FXML Label furnChoice;

  @FXML SearchableComboBox locationSearchDropdown;
  @FXML SearchableComboBox employeeSearchDropdown;
  @FXML Label checkFields;

  String Order = "";

  ObservableList<String> locationList;
  ObservableList<String> employeeList;

  ObservableList<String> list =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");
  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {
    furnSubmitButton.setOnMouseClicked(
        event -> {
          furnOrder();
          allDataFilled();
          Navigation.navigate(Screen.FURNITURE_REQUEST_SUBMIT);
        });

    furnClearAll.setOnAction(event -> clearAllData());

    furnRecipient.getText();
    furnNotesData.getText();
    // mealFoodChoice.setItems(foodList);
    furnChoice.getText();
    furnDate.getValue();
    furnTimeOfDeliver.getText();

    selectedChair.setVisible(false);
    selectedChair.setDisable(true);
    selectedCouch.setVisible(false);
    selectedCouch.setDisable(true);
    selectedTable.setVisible(false);
    selectedTable.setDisable(true);
    chairOption.setOnMouseClicked(event -> selectChairOption());
    tableOption.setOnMouseClicked(event -> selectTableOption());
    couchOption.setOnMouseClicked(event -> selectCouchOption());

    ArrayList<String> employeeNames = new ArrayList<>();
    HashMap<Integer, String> employeeLongName =
        this.getHashMapEmployeeLongName("Furniture Request");

    employeeLongName.forEach(
        (i, m) -> {
          employeeNames.add("ID " + i + ": " + m);
        });

    Collections.sort(employeeNames, String.CASE_INSENSITIVE_ORDER);

    employeeList = FXCollections.observableArrayList(employeeNames);

    ArrayList<String> locationNames = new ArrayList<>();
    HashMap<Integer, String> testingLongName = this.getHashMapMLongName();

    testingLongName.forEach(
        (i, m) -> {
          locationNames.add(m);
          //          System.out.println("Request ID:" + m.getReqid());
          //          System.out.println("Employee ID:" + m.getEmpid());
          //          System.out.println("Status:" + m.getStatus());
          //          System.out.println("Location:" + m.getLocation());
          //          System.out.println("Serve By:" + m.getServ_by());
          //          System.out.println();
        });

    Collections.sort(locationNames, String.CASE_INSENSITIVE_ORDER);

    locationList = FXCollections.observableArrayList(locationNames);

    // Hung this is where it sets the list - Andrew
    employeeSearchDropdown.setItems(employeeList);
    locationSearchDropdown.setItems(locationList);
    checkFields.getText();
  }

  public void exit() {
    Platform.exit();
  }

  public void selectChairOption() {
    if (selectedChair.isVisible()) {
      selectedChair.setVisible(false);
      //  Order.replaceAll("Chair,", "");
    } else {
      selectedChair.setVisible(true);
      // Order += "Chair, ";
    }
  }

  public void selectCouchOption() {
    if (selectedCouch.isVisible() == false) {
      selectedCouch.setVisible(true);
      // Order += "Couch, ";
    } else if (selectedCouch.isVisible() == true) {
      selectedCouch.setVisible(false);
      //   Order.replaceAll("Couch, ", "");
    }
  }

  public void selectTableOption() {
    if (selectedTable.isVisible() == false) {
      selectedTable.setVisible(true);
      // Order += "Table, ";
    } else if (selectedTable.isVisible() == true) {
      selectedTable.setVisible(false);
      //  Order.replaceAll("Table, ", "");
    }
  }

  public void furnOrder() {
    if (selectedChair.isVisible()) {
      Order += "Chair, ";
    }
    if (selectedCouch.isVisible()) {
      Order += "Couch, ";
    }
    if (selectedTable.isVisible()) {
      Order += "Table, ";
    }
    furnChoice.setText(Order);
    // System.out.println(mealFoodChoice.getText());
  }

  public void storeFurnValues() throws SQLException {

    FurnitureRequest mr =
        new FurnitureRequest(
            "FR",
            "ID 1: John Doe",
            // assume for now they are going to input a node number, so parseInt
            (String) locationSearchDropdown.getValue(),
            (String) employeeSearchDropdown.getValue(),
            StatusTypeEnum.blank,
            Date.valueOf(furnDate.getValue()),
            StringToTime(furnTimeOfDeliver.getText()),
            furnRecipient.getText(),
            Order,
            furnNotesData.getText());

    System.out.println(Order);

    DAORepo dao = new DAORepo();
    dao.insertFurniture(mr);
  }

  public HashMap<Integer, String> getHashMapEmployeeLongName(String canServe) throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();
    longNameHashMap = dao.getEmployeeFullName(canServe);

    return longNameHashMap;
  }

  public HashMap<Integer, String> getHashMapMLongName() throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();

    try {
      longNameHashMap = dao.getMandFLLongName();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return longNameHashMap;
  }

  public Time StringToTime(String s) {

    String[] hourMin = s.split(":", 2);
    Time t = new Time(Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]), 00);
    return t;
  }

  public void allDataFilled() {
    if (!(furnRecipient.getText().equals("")
        || furnNotesData.getText().equals("")
        || furnDate.getText().equals("")
        || furnTimeOfDeliver.getText().equals("")
        || Order.equals("")
        || locationSearchDropdown.getValue() == null
        || employeeSearchDropdown.getValue() == null)) {

      try {
        storeFurnValues();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      Navigation.navigate(Screen.FURNITURE_REQUEST_SUBMIT);
    } else {
      checkFields.setText("Not All Fields Are Filled");
    }
  }

  public void clearAllData() {
    // mealDeliveryLocationData.setText("");
    furnRecipient.setText("");
    furnNotesData.setText("");
    furnDate.setText("");
    furnTimeOfDeliver.setText("");
    furnChoice.setText("");

    locationSearchDropdown.setValue(null);

    selectedChair.setVisible(false);
    selectedChair.setDisable(true);
    selectedCouch.setVisible(false);
    selectedCouch.setDisable(true);
    selectedTable.setVisible(false);
    selectedTable.setDisable(true);

    employeeSearchDropdown.setValue(null);
    return;
  }
}
