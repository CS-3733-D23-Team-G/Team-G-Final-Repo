package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.Employee;
import edu.wpi.teamg.ORMClasses.MaintenanceRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import io.github.palexdev.materialfx.controls.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MaintenanceRequestController {
  @FXML MFXButton maintainSubmitButton;

  @FXML MFXButton maintainClearButton;

  @FXML MFXDatePicker maintainDate;

  // TextFields

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

  @FXML AnchorPane forms;

  @FXML MFXFilterComboBox locationSearchDropdown;
  @FXML MFXFilterComboBox employeeSearchDropdown;
  @FXML MFXFilterComboBox hour;
  @FXML MFXFilterComboBox minutes;
  @FXML Label checkFields;
  @FXML Line assignToLine;
  @FXML Text assignToText;
  @FXML VBox vboxWithAssignTo;

  ObservableList<String> locationList;
  ObservableList<String> employeeList;
  ObservableList<String> hourList;
  ObservableList<String> minuteList;

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
    maintainPhoneNumber.getText();

    ArrayList<String> time_hour = new ArrayList<>();
    int x = 0;
    while (x < 24) {
      x++;
      time_hour.add("" + x);
    }
    hourList = FXCollections.observableArrayList(time_hour);

    ArrayList<String> time_minute = new ArrayList<>();
    int j = 0;
    while (j < 60) {
      time_minute.add("" + j);
      j += 15;
    }
    minuteList = FXCollections.observableArrayList(time_minute);

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
    hour.setItems(hourList);
    minutes.setItems(minuteList);
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
            StringToTime((String) hour.getValue(), (String) minutes.getValue()),
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

  public Time StringToTime(String h, String m) {
    Time t = new Time(Integer.parseInt(h), Integer.parseInt(m), 00);
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
        || hour.getValue() == null
        || minutes.getValue() == null
        || locationSearchDropdown.getValue() == null
        || finalTreeLevel.getText().equals(""))) {
      // || checkBoxSlected()
      try {
        storeMaintenanceValues();
        completeAnimation("Maintenance request sent!");
        clearAllData();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    } else {
      checkFields.setVisible(true);
    }
  }

  public void completeAnimation(String message) {

    // Form Completion PopUp
    AnchorPane rect = new AnchorPane();
    rect.setLayoutX(500);
    rect.setStyle(
        "-fx-pref-width: 400; -fx-pref-height: 100; -fx-background-color: #97E198; -fx-background-radius: 10");
    rect.setLayoutY(800);
    rect.toFront();

    Text completionText = new Text("You Are All Set!");
    completionText.setLayoutX(625);
    completionText.setLayoutY(845);
    completionText.setStyle(
        "-fx-stroke: #000000;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 25;"
            + "-fx-font-weight: 500;");
    completionText.toFront();

    Text completionTextSecondRow = new Text(message);
    completionTextSecondRow.setLayoutX(625);
    completionTextSecondRow.setLayoutY(875);
    completionTextSecondRow.setStyle(
        "-fx-stroke: #404040;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 20;"
            + "-fx-font-weight: 500;");
    completionTextSecondRow.toFront();

    // Image checkmarkImage = new Image("edu/wpi/teamg/Images/checkMarkIcon.png");
    ImageView completionImage = new ImageView(App.checkmarkImage);

    completionImage.setFitHeight(50);
    completionImage.setFitWidth(50);
    completionImage.setLayoutX(525);
    completionImage.setLayoutY(825);
    completionImage.toFront();

    rect.setOpacity(0.0);
    completionImage.setOpacity(0.0);
    completionText.setOpacity(0.0);
    completionTextSecondRow.setOpacity(0.0);

    forms.getChildren().add(rect);
    forms.getChildren().add(completionText);
    forms.getChildren().add(completionImage);
    forms.getChildren().add(completionTextSecondRow);

    FadeTransition fadeIn1 = new FadeTransition(Duration.seconds(0.5), rect);
    fadeIn1.setFromValue(0.0);
    fadeIn1.setToValue(1.0);

    FadeTransition fadeIn2 = new FadeTransition(Duration.seconds(0.5), completionImage);
    fadeIn2.setFromValue(0.0);
    fadeIn2.setToValue(1.0);

    FadeTransition fadeIn3 = new FadeTransition(Duration.seconds(0.5), completionText);
    fadeIn3.setFromValue(0.0);
    fadeIn3.setToValue(1.0);

    FadeTransition fadeIn4 = new FadeTransition(Duration.seconds(0.5), completionTextSecondRow);
    fadeIn4.setFromValue(0.0);
    fadeIn4.setToValue(1.0);

    ParallelTransition parallelTransition =
        new ParallelTransition(fadeIn1, fadeIn2, fadeIn3, fadeIn4);

    parallelTransition.play();

    parallelTransition.setOnFinished(
        (event) -> {
          FadeTransition fadeOut1 = new FadeTransition(Duration.seconds(0.5), rect);
          fadeOut1.setDelay(Duration.seconds(1.5));
          fadeOut1.setFromValue(1.0);
          fadeOut1.setToValue(0.0);

          FadeTransition fadeOut2 = new FadeTransition(Duration.seconds(0.5), completionImage);
          fadeOut2.setDelay(Duration.seconds(1.5));
          fadeOut2.setFromValue(1.0);
          fadeOut2.setToValue(0.0);

          FadeTransition fadeOut3 = new FadeTransition(Duration.seconds(0.5), completionText);
          fadeOut3.setDelay(Duration.seconds(1.5));
          fadeOut3.setFromValue(1.0);
          fadeOut3.setToValue(0.0);

          FadeTransition fadeOut4 =
              new FadeTransition(Duration.seconds(0.5), completionTextSecondRow);
          fadeOut4.setDelay(Duration.seconds(1.5));
          fadeOut4.setFromValue(1.0);
          fadeOut4.setToValue(0.0);

          fadeOut1.play();
          fadeOut2.play();
          fadeOut3.play();
          fadeOut4.play();
        });
  }

  public void clearAllData() {
    maintainRecipient.setText("");
    maintainDate.setText("");
    maintainPhoneNumber.setText("");

    locationSearchDropdown.setValue(null);
    employeeSearchDropdown.setValue(null);
    hour.setValue(null);
    minutes.setValue(null);

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
