package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.ConferenceRoomRequest;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import org.controlsfx.control.SearchableComboBox;

public class ConRoomRequestController {

  // buttons
  @FXML MFXButton backToHomeButton;
  @FXML MFXButton exitButton;
  @FXML MFXButton signagePageButton;
  @FXML MFXButton roomConfirm;
  @FXML MFXButton roomClearAll;

  // Text Fields
  @FXML MFXTextField roomMeetingPurpose;
  @FXML MFXDatePicker datePicker;
  @FXML MFXTextField roomTimeData;
  @FXML MFXTextField roomEndTime;
  @FXML ChoiceBox<String> serviceRequestChoiceBox;

  // Hung This is the name and list associated with test searchable list
  @FXML SearchableComboBox locationSearchDropdown;
  @FXML SearchableComboBox employeeSearchDropdown;
  @FXML Label checkFields;

  ObservableList<String> locationList;
  ObservableList<String> employeeList;

  ObservableList<String> list =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");
  // ObservableList<String> roomTimeDataList =
  //    FXCollections.observableArrayList(
  //        "12:00", "12:30", "1:00", "1:30");
  // ObservableList<String> roomNumberDataList =
  //    FXCollections.observableArrayList(
  //        "1", "2", "3", "4", "5", "6");

  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {
    backToHomeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    signagePageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
    exitButton.setOnMouseClicked(event -> roomExit());
    roomConfirm.setOnMouseClicked(
        event -> {
          allDataFilled();
        });

    datePicker.setText("");
    checkFields.getText();

    roomMeetingPurpose.getText();
    // roomNumber.getText();
    roomTimeData.getText();
    // roomNumberData.setValue("noon");
    // roomNumberData.setItems(roomNumberDataList);
    // roomTimeData.setValue("noon");
    // roomTimeData.setItems(roomTimeDataList);
    // roomTimeData.getValue();
    // roomNumberData.getValue();
    serviceRequestChoiceBox.setItems(list);
    serviceRequestChoiceBox.setOnAction(event -> loadServiceRequestForm());
    roomClearAll.setOnAction(event -> clearAllData());

    ArrayList<String> employeeNames = new ArrayList<>();
    HashMap<Integer, String> employeeLongName =
        this.getHashMapEmployeeLongName("Conference Rooms Request");

    employeeLongName.forEach(
        (i, m) -> {
          employeeNames.add("ID " + i + ": " + m);
        });

    Collections.sort(employeeNames, String.CASE_INSENSITIVE_ORDER);

    employeeList = FXCollections.observableArrayList(employeeNames);

    ArrayList<String> locationNames = new ArrayList<>();
    HashMap<Integer, String> testingLongName = this.getHashMapCRLongName();

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

  public HashMap<Integer, String> getHashMapEmployeeLongName(String canServe) throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();

    try {
      longNameHashMap = dao.getEmployeeFullName(canServe);
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return longNameHashMap;
  }

  public void storeRoomValues() throws SQLException {

    ConferenceRoomRequest conRoom =
        new ConferenceRoomRequest(
            "CR",
            "ID 1: John Doe",
            // assume for now they are going to input a node number, so parseInt
            (String) locationSearchDropdown.getValue(),
            (String) employeeSearchDropdown.getValue(),
            StatusTypeEnum.blank,
            Date.valueOf(datePicker.getValue()),
            StringToTime(roomTimeData.getText()),
            StringToTime(roomEndTime.getText()),
            roomMeetingPurpose.getText());

    try {
      dao.insertConferenceRoomRequest(conRoom);
    } catch (SQLException e) {
      System.err.println("SQL Exception");
      e.printStackTrace();
    }

    // System.out.println("Employee Name: " + employeeSearchDropdown.getValue());
    //    System.out.println(
    //        "Room ID: " + dao.getNodeIDbyLongName((String) locationSearchDropdown.getValue()));

  }

  public HashMap<Integer, String> getHashMapCRLongName() throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();

    try {
      longNameHashMap = dao.getCRLongName();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return longNameHashMap;
  }

  public void clearAllData() {
    roomMeetingPurpose.setText("");
    datePicker.setText("");
    roomTimeData.setText("");
    roomEndTime.setText("");
    locationSearchDropdown.setValue(null);
    return;
  }

  public void allDataFilled() {
    if (!(roomMeetingPurpose.getText().equals("")
        || datePicker.getText().equals("")
        || roomTimeData.getText().equals("")
        || roomEndTime.getText().equals("")
        || locationSearchDropdown == null)) {
      try {
        storeRoomValues();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      Navigation.navigate(Screen.ROOM_REQUEST_SUBMIT);
    } else {
      checkFields.setText("Not All Fields Are Filled");
    }
  }

  public Time StringToTime(String s) {

    String[] hourMin = s.split(":", 2);
    Time t = new Time(Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]), 00);
    return t;
  }

  public void roomExit() {
    Platform.exit();
  }
}
