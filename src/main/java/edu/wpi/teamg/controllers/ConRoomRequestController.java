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

  @FXML MFXButton roomConfirm;
  @FXML MFXButton roomClearAll;

  // Text Fields
  @FXML MFXTextField roomMeetingPurpose;
  @FXML MFXDatePicker datePicker;
  @FXML MFXTextField roomTimeData;
  @FXML MFXTextField roomEndTime;


  // Hung This is the name and list associated with test searchable list
  @FXML SearchableComboBox locationSearchDropdown;
  @FXML Label checkFields;

  ObservableList<String> locationList =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");;



  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {

    roomConfirm.setOnMouseClicked(
        event -> {
          allDataFilled();
        });

    datePicker.setText("");
    //    checkFields.getText();

    roomMeetingPurpose.getText();
    // roomNumber.getText();
    roomTimeData.getText();
    // roomNumberData.setValue("noon");
    // roomNumberData.setItems(roomNumberDataList);
    // roomTimeData.setValue("noon");
    // roomTimeData.setItems(roomTimeDataList);
    // roomTimeData.getValue();
    // roomNumberData.getValue();

    roomClearAll.setOnAction(event -> clearAllData());

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
    locationSearchDropdown.setItems(locationList);
  }

  

  public void storeRoomValues() throws SQLException {

    ConferenceRoomRequest conRoom =
        new ConferenceRoomRequest(
            "CR",
            1,
            // assume for now they are going to input a node number, so parseInt
            (String) locationSearchDropdown.getValue(),
            1,
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

    System.out.println("Room Name: " + locationSearchDropdown.getValue());
    System.out.println(
        "Room ID: " + dao.getNodeIDbyLongName((String) locationSearchDropdown.getValue()));
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
