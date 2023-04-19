package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.DAORepo;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import java.sql.SQLException;
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
    
  }

  private void supplyOrder() {
    if(selectedPens.isVisible())
      Order+="Pens, ";
    if(selectedStapler.isVisible())
      Order+="Stapler, ";
    if(selectedTape.isVisible())
      Order+="Tape, ";

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
