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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

  @FXML MFXCheckbox electrical;
  @FXML MFXCheckbox waste;
  @FXML MFXCheckbox roof;
  @FXML MFXCheckbox move;
  @FXML MFXCheckbox floor;
  @FXML MFXCheckbox glass;
  @FXML MFXCheckbox paint;
  @FXML MFXCheckbox custodial;
  @FXML MFXCheckbox keyandlock;
  @FXML MFXCheckbox heat;
  @FXML MFXCheckbox ventilation;
  @FXML MFXCheckbox airCond;
  @FXML MFXCheckbox recycle;
  @FXML MFXCheckbox officeSer;
  @FXML MFXCheckbox other;
  @FXML MFXCheckbox elevator;
  @FXML MFXCheckbox pest;
  @FXML MFXCheckbox appliance;

  @FXML TextArea finalTreeLevel;
  @FXML Text Problemdes;
  @FXML MFXFilterComboBox locationSearchDropdown;
  @FXML MFXFilterComboBox employeeSearchDropdown;
  @FXML Label checkFields;
  @FXML Line assignToLine;
  @FXML Text assignToText;
  @FXML VBox vboxWithAssignTo;
  @FXML ImageView img_main;

  ObservableList<String> locationList;
  ObservableList<String> employeeList;

  static String typeOfMaintain;
  static String specifiedMaintain;

  DAORepo dao = new DAORepo();
  private MFXCheckbox[] checkbox;
  private boolean somethingSelected = false;

  @FXML
  public void initialize() throws SQLException {
    checkbox =
        new MFXCheckbox[] {
          electrical,
          waste,
          roof,
          move,
          floor,
          glass,
          paint,
          custodial,
          keyandlock,
          heat,
          ventilation,
          airCond,
          recycle,
          officeSer,
          other,
          elevator,
          pest,
          appliance
        };
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
    finalTreeLevel.setVisible(false);
    Problemdes.setVisible(false);
   

    for (MFXCheckbox box : checkbox) {
      box.setOnAction(event -> setString(box.getText()));
    }
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

  public void setString(String box1) {
    String answer = "";
    for (MFXCheckbox box : checkbox) {
      if (!box.getText().equals(box1)) {
        box.setSelected(false);
      }
    }
    somethingSelected = true;
    typeOfMaintain = box1;
    finalTreeLevel.setVisible(true);
    Problemdes.setVisible(true);
  }

  public void storeMaintenanceValues() throws SQLException {

    HashMap<Integer, Employee> employeeHash = dao.getAllEmployees();

    System.out.println(typeOfMaintain + " " + finalTreeLevel.getText());

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
            Date.valueOf(maintainDate.getValue().toString()),
            StringToTime(maintainTime.getText()),
            maintainRecipient.getText(),
            maintainPhoneNumber.getText(),
            typeOfMaintain,
            typeOfMaintain,
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

  public boolean checkBoxSlected() {
    boolean answer = true;
    for (MFXCheckbox box : checkbox) {
      if (box.isSelected()) {
        answer = false;
      }
    }
    return answer;
  }

  public void allDataFilled() {
    if (!(maintainRecipient.getText().equals("")
        || maintainPhoneNumber.getText().equals("")
        || maintainDate.getText().equals("")
        || maintainTime.getText().equals("")
        || locationSearchDropdown.getValue() == null
        || finalTreeLevel.getText().equals(""))) {
      // || checkBoxSlected()
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

    for (MFXCheckbox box : checkbox) {
      box.setSelected(false);
    }
    finalTreeLevel.setText("");

    finalTreeLevel.setVisible(false);
    Problemdes.setVisible(false);
    checkFields.setVisible(false);

    return;
  }
}
