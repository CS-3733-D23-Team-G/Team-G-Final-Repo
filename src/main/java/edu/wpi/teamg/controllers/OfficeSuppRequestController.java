package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.OfficeSupplyRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
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
import javax.swing.*;
import org.controlsfx.control.SearchableComboBox;

public class OfficeSuppRequestController {
  @FXML MFXButton supplyConfirm;
  @FXML MFXButton supplyClear;
  @FXML MFXDatePicker supplyDate;
  @FXML MFXTextField supplyDeliverTime;
  @FXML MFXTextField supplyRecipient;
  @FXML MFXTextField recipientNotes;
  @FXML ImageView pensOption;
  @FXML ImageView selectedPens;
  @FXML ImageView tapeOption;
  @FXML ImageView selectedTape;
  @FXML ImageView staplerOption;
  @FXML ImageView selectedStapler;
  @FXML Label supplyChoice;
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
          "Office Supplices Request Form");
  DAORepo repo = new DAORepo();

  @FXML
  public void initialize() throws SQLException {
    supplyConfirm.setOnMouseClicked(
        event -> {
          supplyOrder();
          allDataFilled();
        });
    checkFields.setVisible(false);
    supplyClear.setOnAction(event -> clearAllData());

    supplyRecipient.getText();
    recipientNotes.getText();
    supplyChoice.getText();
    supplyDate.getValue();
    supplyDeliverTime.getText();

    selectedPens.setVisible(false);
    selectedPens.setDisable(true);
    selectedStapler.setVisible(false);
    selectedStapler.setDisable(true);
    selectedTape.setVisible(false);
    selectedTape.setDisable(true);

    pensOption.setOnMouseClicked(event -> selectedPenOption());
    staplerOption.setOnMouseClicked(event -> selectedStaplerOption());
    tapeOption.setOnMouseClicked(event -> selectTapeOption());

    ArrayList<String> empNames = new ArrayList<>();
    HashMap<Integer, String> empLong = this.getHashMapEmployeeLongName("Office Supplies Request");

    empLong.forEach(
        (i, m) -> {
          empNames.add("ID " + i + ": " + m);
        });
    Collections.sort(empNames, String.CASE_INSENSITIVE_ORDER);
    employeeList = FXCollections.observableArrayList(empNames);

    ArrayList<String> locNames = new ArrayList<>();
    HashMap<Integer, String> testLName = this.getHashMapMLongName();

    testLName.forEach(
        (i, m) -> {
          locNames.add(m);
        });

    Collections.sort(locNames, String.CASE_INSENSITIVE_ORDER);
    locationList = FXCollections.observableArrayList(locNames);

    employeeSearchDropdown.setItems(employeeList);
    locationSearchDropdown.setItems(locationList);
    checkFields.getText();
  }

  private void selectTapeOption() {
    if (selectedTape.isVisible()) {
      selectedTape.setVisible(false);
      return;
    }
    selectedTape.setVisible(true);
  }

  private void selectedStaplerOption() {
    if (selectedStapler.isVisible()) {
      selectedStapler.setVisible(false);
      return;
    }
    selectedStapler.setVisible(true);
  }

  private void selectedPenOption() {
    if (selectedPens.isVisible()) {
      selectedPens.setVisible(false);
      return;
    }
    selectedPens.setVisible(true);
  }

  private void clearAllData() {
    supplyRecipient.setText("");
    recipientNotes.setText("");
    supplyDate.setText("");
    supplyDeliverTime.setText("");
    supplyChoice.setText("");

    locationSearchDropdown.setValue(null);
    employeeSearchDropdown.setValue(null);

    selectedPens.setVisible(false);
    selectedPens.setDisable(true);
    selectedStapler.setVisible(false);
    selectedStapler.setDisable(true);
    selectedTape.setVisible(false);
    selectedTape.setDisable(true);
  }

  private void allDataFilled() {
    if (!(supplyRecipient.getText().equals("")
        || recipientNotes.getText().equals("")
        || supplyDate.getText().equals("")
        || supplyDeliverTime.getText().equals("")
        || Order.equals("")
        || locationSearchDropdown.getValue() == null
        || employeeSearchDropdown.getValue() == null)) {
      try {
        storeSupplyVal();
      } catch (SQLException e) {
        e.printStackTrace();
      }
      Navigation.navigate(Screen.SUPPLIES_REQUEST_SUBMIT);
    } else {
      checkFields.setVisible(true);
    }
  }

  private void storeSupplyVal() throws SQLException {
    OfficeSupplyRequest os =
        new OfficeSupplyRequest(
            "OS",
            "ID 1: John Doe",
            (String) locationSearchDropdown.getValue(),
            (String) employeeSearchDropdown.getValue(),
            StatusTypeEnum.blank,
            Date.valueOf(supplyDate.getValue()),
            StringToTime(supplyDeliverTime.getText()),
            supplyRecipient.getText(),
            Order,
            recipientNotes.getText());
    // System.out.println(Order);
    DAORepo dao = new DAORepo();
    dao.insertSupply(os);
    App.requestRefresh();
  }

  private Time StringToTime(String text) {
    String[] hourMin = text.split(":", 2);
    Time t = new Time(Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]), 00);
    return t;
  }

  private void supplyOrder() {
    if (selectedPens.isVisible()) Order += "Pens, ";
    if (selectedStapler.isVisible()) Order += "Stapler, ";
    if (selectedTape.isVisible()) Order += "Tape, ";

    supplyChoice.setText(Order);
  }

  public HashMap<Integer, String> getHashMapEmployeeLongName(String canServe) throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();
    longNameHashMap = repo.getEmployeeFullName(canServe);

    return longNameHashMap;
  }

  public HashMap<Integer, String> getHashMapMLongName() throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();

    try {
      longNameHashMap = repo.getMandFLLongName();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return longNameHashMap;
  }

  public void exit() {
    Platform.exit();
  }
}
