package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.Employee;
import edu.wpi.teamg.ORMClasses.MaintenanceRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.*;
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
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class MaintenanceRequestController {
  @FXML MFXButton maintainSubmitButton;

  @FXML MFXButton maintainClearButton;

  @FXML MFXDatePicker maintainDate;

  // TextFields
  @FXML MFXTextField maintainTime;

  @FXML MFXTextField maintainRecipient;

  @FXML MFXTextField maintainPhoneNumber;

  @FXML MFXCheckbox custodialCheck;
  @FXML MFXCheckbox technologicalCheck;
  @FXML MFXCheckbox mechanicalCheck;
  @FXML MFXCheckbox checkTree1;
  @FXML MFXCheckbox checkTree2;
  @FXML MFXCheckbox checkTree3;
  @FXML MFXCheckbox checkTree4;

  @FXML Line lineTree1;
  @FXML Text textTree1;
  @FXML Line lineTree2;
  @FXML Text textTree2;

  @FXML TextArea finalTreeLevel;

  @FXML MFXFilterComboBox locationSearchDropdown;
  @FXML MFXFilterComboBox employeeSearchDropdown;
  @FXML Label checkFields;
  @FXML Line assignToLine;
  @FXML Text assignToText;
  @FXML VBox vboxWithAssignTo;

  ObservableList<String> locationList;
  ObservableList<String> employeeList;

  static String typeOfMaintain;
  static String specifiedMaintain;

  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {
    maintainSubmitButton.setOnMouseClicked(
        event -> {
          allDataFilled();
        });

    if (!App.employee.getIs_admin()) {
      vboxWithAssignTo.getChildren().remove(assignToLine);
      vboxWithAssignTo.getChildren().remove(assignToText);
      vboxWithAssignTo.getChildren().remove(employeeSearchDropdown);
    }

    checkFields.setVisible(false);

    custodialCheck.setOnAction(event -> checkBoxTreeLv1("Custodial"));
    mechanicalCheck.setOnAction(event -> checkBoxTreeLv1("Mechanical"));
    technologicalCheck.setOnAction(event -> checkBoxTreeLv1("Technological"));
    checkTree1.setOnAction(event -> checkBoxTreeLv2("1"));
    checkTree2.setOnAction(event -> checkBoxTreeLv2("2"));
    checkTree3.setOnAction(event -> checkBoxTreeLv2("3"));
    checkTree4.setOnAction(event -> checkBoxTreeLv2("4"));
    ;

    maintainClearButton.setOnAction(event -> clearAllData());
    maintainRecipient.getText();
    // mealFoodChoice.setItems(foodList);
    maintainDate.getValue();
    maintainTime.getText();
    maintainPhoneNumber.getText();

    ArrayList<String> employeeNames = new ArrayList<>();
    HashMap<Integer, String> employeeLongName =
        this.getHashMapEmployeeLongName("Maintenance Request");

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
        });

    Collections.sort(locationNames, String.CASE_INSENSITIVE_ORDER);

    locationList = FXCollections.observableArrayList(locationNames);

    employeeSearchDropdown.setItems(employeeList);
    locationSearchDropdown.setItems(locationList);
    checkFields.getText();
  }

  public void exit() {
    Platform.exit();
  }

  public void checkBoxTreeLv1(String type) {

    if (custodialCheck.isSelected() == false
        && mechanicalCheck.isSelected() == false
        && technologicalCheck.isSelected() == false) {
      textTree1.setVisible(false);
      lineTree1.setVisible(false);

      textTree2.setVisible(false);
      lineTree2.setVisible(false);
      finalTreeLevel.setVisible(false);

      // Text
      checkTree1.setText("");
      checkTree2.setText("");
      checkTree3.setText("");
      checkTree4.setText("");

      // Checked
      checkTree1.setSelected(false);
      checkTree2.setSelected(false);
      checkTree3.setSelected(false);
      checkTree4.setSelected(false);

      // visibility
      checkTree1.setVisible(false);
      checkTree2.setVisible(false);
      checkTree3.setVisible(false);
      checkTree4.setVisible(false);
      return;
    }

    if (type == "Custodial") {
      typeOfMaintain = "Custodial";
      mechanicalCheck.setSelected(false);
      technologicalCheck.setSelected(false);
      textTree1.setVisible(true);
      lineTree1.setVisible(true);

      // Text
      checkTree1.setText("Cleaning");
      checkTree2.setText("Moving");
      checkTree3.setText("Repairing");
      checkTree4.setText("Other");

      // Checked
      checkTree1.setSelected(false);
      checkTree2.setSelected(false);
      checkTree3.setSelected(false);
      checkTree4.setSelected(false);
      // visibility
      checkTree1.setVisible(true);
      checkTree2.setVisible(true);
      checkTree3.setVisible(true);
      checkTree4.setVisible(true);

      textTree2.setVisible(false);
      lineTree2.setVisible(false);
      finalTreeLevel.setVisible(false);
    }
    if (type == "Mechanical") {
      typeOfMaintain = "Mechanical";
      custodialCheck.setSelected(false);
      technologicalCheck.setSelected(false);
      textTree1.setVisible(true);
      lineTree1.setVisible(true);

      // Text
      checkTree1.setText("Equipment Repair");
      checkTree2.setText("Machine Repair");
      checkTree3.setText("Other");
      checkTree4.setText("");
      // Checked
      checkTree1.setSelected(false);
      checkTree2.setSelected(false);
      checkTree3.setSelected(false);
      checkTree4.setSelected(false);
      // visibility
      checkTree1.setVisible(true);
      checkTree2.setVisible(true);
      checkTree3.setVisible(true);
      checkTree4.setVisible(false);

      textTree2.setVisible(false);
      lineTree2.setVisible(false);
      finalTreeLevel.setVisible(false);
    }
    if (type == "Technological") {
      typeOfMaintain = "Technological";
      mechanicalCheck.setSelected(false);
      custodialCheck.setSelected(false);
      textTree1.setVisible(true);
      lineTree1.setVisible(true);

      // Text
      checkTree1.setText("Hardware Issue");
      checkTree2.setText("Software Issue");
      checkTree3.setText("Other");
      checkTree4.setText("");
      // Checked
      checkTree1.setSelected(false);
      checkTree2.setSelected(false);
      checkTree3.setSelected(false);
      checkTree4.setSelected(false);
      // visibility
      checkTree1.setVisible(true);
      checkTree2.setVisible(true);
      checkTree3.setVisible(true);
      checkTree4.setVisible(false);

      textTree2.setVisible(false);
      lineTree2.setVisible(false);
      finalTreeLevel.setVisible(false);
    }
  }

  public void checkBoxTreeLv2(String type) {

    if (checkTree1.isSelected() == false
        && checkTree2.isSelected() == false
        && checkTree3.isSelected() == false
        && checkTree4.isSelected() == false) {
      textTree2.setVisible(false);
      lineTree2.setVisible(false);
      // Text
      finalTreeLevel.setText("");
      finalTreeLevel.setVisible(false);
      return;
    }

    if (type == "1") {
      typeOfMaintain = checkTree1.getText();

      checkTree2.setSelected(false);
      checkTree3.setSelected(false);
      checkTree4.setSelected(false);

      textTree2.setVisible(true);
      lineTree2.setVisible(true);
      finalTreeLevel.setVisible(true);
    }
    if (type == "2") {
      typeOfMaintain = checkTree2.getText();

      checkTree1.setSelected(false);
      checkTree3.setSelected(false);
      checkTree4.setSelected(false);

      textTree2.setVisible(true);
      lineTree2.setVisible(true);
      finalTreeLevel.setVisible(true);
    }
    if (type == "3") {
      typeOfMaintain = checkTree3.getText();
      checkTree2.setSelected(false);
      checkTree1.setSelected(false);
      checkTree4.setSelected(false);

      textTree2.setVisible(true);
      lineTree2.setVisible(true);
      finalTreeLevel.setVisible(true);
    }
    if (type == "4") {
      typeOfMaintain = checkTree4.getText();
      checkTree2.setSelected(false);
      checkTree1.setSelected(false);
      checkTree3.setSelected(false);

      textTree2.setVisible(true);
      lineTree2.setVisible(true);
      finalTreeLevel.setVisible(true);
    }
  }

  public void storeMaintenanceValues() throws SQLException {

    HashMap<Integer, Employee> employeeHash = dao.getAllEmployees();

    Employee signedIn = employeeHash.get(App.employee.getEmpID());

    MaintenanceRequest mr =
        new MaintenanceRequest(
            "MT",
            "ID "
                + App.employee.getEmpID()
                + ": "
                + signedIn.getFirstName()
                + " "
                + signedIn.getLastName(),
            // assume for now they are going to input a node number, so parseInt
            (String) locationSearchDropdown.getValue(),
            (String) employeeSearchDropdown.getValue(),
            StatusTypeEnum.blank,
            Date.valueOf(maintainDate.getValue()),
            StringToTime(maintainTime.getText()),
            maintainRecipient.getText(),
            maintainPhoneNumber.getText(),
            typeOfMaintain,
            specifiedMaintain,
            finalTreeLevel.getText());

    dao.insertMaintenance(mr);
    App.maintainRefresh();
  }

  public HashMap<Integer, String> getHashMapEmployeeLongName(String canServe) throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();

    try {
      longNameHashMap = dao.getEmployeeFullName(canServe);
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

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
    if (!(maintainRecipient.getText().equals("")
            || maintainPhoneNumber.getText().equals("")
            || maintainDate.getText().equals("")
            || maintainTime.getText().equals("")
            || locationSearchDropdown.getValue() == null)
        || (mechanicalCheck.isSelected() == false
            && custodialCheck.isSelected() == false
            && technologicalCheck.isSelected() == false)
        || (checkTree1.isSelected() == false
            && checkTree2.isSelected() == false
            && checkTree3.isSelected() == false
            && checkTree4.isSelected() == false)
        || finalTreeLevel.getText().equals("")) {

      try {
        storeMaintenanceValues();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      Navigation.navigate(Screen.MAINTENANCE_REQUEST_SUBMIT);
    } else {
      checkFields.setVisible(true);
    }
  }

  public void clearAllData() {
    maintainRecipient.setText("");
    maintainDate.setText("");
    maintainTime.setText("");
    maintainPhoneNumber.setText("");

    locationSearchDropdown.setValue(null);
    employeeSearchDropdown.setValue(null);

    mechanicalCheck.setSelected(false);
    technologicalCheck.setSelected(false);
    custodialCheck.setSelected(false);
    checkTree1.setSelected(false);
    checkTree2.setSelected(false);
    checkTree3.setSelected(false);
    checkTree4.setSelected(false);
    finalTreeLevel.setText("");

    finalTreeLevel.setVisible(false);
    checkTree1.setVisible(false);
    checkTree2.setVisible(false);
    checkTree3.setVisible(false);
    checkTree4.setVisible(false);
    mechanicalCheck.setVisible(false);
    technologicalCheck.setVisible(false);
    custodialCheck.setVisible(false);
    textTree1.setVisible(false);
    textTree2.setVisible(false);
    lineTree2.setVisible(false);
    lineTree1.setVisible(false);
    return;
  }
}
