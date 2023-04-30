package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.ORMClasses.Employee;
import edu.wpi.teamg.ORMClasses.OfficeSupplyRequest;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javax.swing.*;

public class OfficeSuppRequestController {
  @FXML MFXButton supplyConfirm;
  @FXML MFXButton supplyClear;
  @FXML MFXDatePicker supplyDate;
  @FXML MFXTextField supplyDeliverTime;
  @FXML MFXTextField supplyRecipient;
  @FXML TextArea recipientNotes;
  @FXML ImageView pensOption;
  @FXML ImageView selectedPens;
  @FXML ImageView tapeOption;
  @FXML ImageView selectedTape;
  @FXML ImageView staplerOption;
  @FXML ImageView selectedStapler;
  @FXML Label supplyChoice;
  @FXML MFXFilterComboBox locationSearchDropdown;
  @FXML MFXFilterComboBox employeeSearchDropdown;
  @FXML VBox vboxWithAssignTo;

  @FXML Label checkFields;

  @FXML Line assignToLine;
  @FXML Text assignToText;

  @FXML AnchorPane forms;

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

    if (!App.employee.getIs_admin()) {

      vboxWithAssignTo.getChildren().remove(assignToLine);
      vboxWithAssignTo.getChildren().remove(assignToText);
      vboxWithAssignTo.getChildren().remove(employeeSearchDropdown);
    }

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
        || supplyDate.getText().equals("")
        || supplyDeliverTime.getText().equals("")
        || Order.equals("")
        || locationSearchDropdown.getValue() == null)) {
      try {
        storeSupplyVal();
        completeAnimation();
        clearAllData();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    } else {
      checkFields.setVisible(true);
    }
  }

  private void storeSupplyVal() throws SQLException {

    HashMap<Integer, Employee> employeeHash = repo.getAllEmployees();

    Employee signedIn = employeeHash.get(App.employee.getEmpID());

    OfficeSupplyRequest os =
        new OfficeSupplyRequest(
            "OS",
            "ID "
                + App.employee.getEmpID()
                + ": "
                + signedIn.getFirstName()
                + " "
                + signedIn.getLastName(),
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

  public void completeAnimation() {

    // Form Completion PopUp
    AnchorPane rect = new AnchorPane();
    rect.setLayoutX(325);
    rect.setStyle(
        "-fx-pref-width: 440; -fx-pref-height: 100; -fx-background-color: #d9d9d9; -fx-border-radius: 5; -fx-background-insets: 5; -fx-border-insets: 5; -fx-padding: 5;"
            + "-fx-border-color: #000000;"
            + "-fx-border-width: 3;");
    rect.setLayoutY(800);
    rect.toFront();

    Text completionText = new Text("You Are All Set!");
    completionText.setLayoutX(445);
    completionText.setLayoutY(850);
    completionText.setStyle(
        "-fx-stroke: #000000;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 25;"
            + "-fx-font-weight: 500;");
    completionText.toFront();

    Text completionTextSecondRow = new Text("Conference Room Request Sent Successfully.");
    completionTextSecondRow.setLayoutX(445);
    completionTextSecondRow.setLayoutY(870);
    completionTextSecondRow.setStyle(
        "-fx-stroke: #000000;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 15;"
            + "-fx-font-weight: 500;");
    completionTextSecondRow.toFront();

    javafx.scene.image.Image checkmarkImage = new Image("edu/wpi/teamg/Images/checkMarkIcon.png");
    ImageView completionImage = new ImageView(checkmarkImage);

    completionImage.setFitHeight(120);
    completionImage.setFitWidth(120);
    completionImage.setLayoutX(320);
    completionImage.setLayoutY(790);
    completionImage.toFront();

    rect.setOpacity(0.0);
    completionImage.setOpacity(0.0);
    completionText.setOpacity(0.0);
    completionTextSecondRow.setOpacity(0.0);

    forms.getChildren().add(rect);
    forms.getChildren().add(completionText);
    forms.getChildren().add(completionImage);
    forms.getChildren().add(completionTextSecondRow);

    FadeTransition fadeIn1 = new FadeTransition(Duration.seconds(1), rect);
    fadeIn1.setFromValue(0.0);
    fadeIn1.setToValue(1.0);

    FadeTransition fadeIn2 = new FadeTransition(Duration.seconds(1), completionImage);
    fadeIn2.setFromValue(0.0);
    fadeIn2.setToValue(1.0);

    FadeTransition fadeIn3 = new FadeTransition(Duration.seconds(1), completionText);
    fadeIn3.setFromValue(0.0);
    fadeIn3.setToValue(1.0);

    FadeTransition fadeIn4 = new FadeTransition(Duration.seconds(1), completionTextSecondRow);
    fadeIn4.setFromValue(0.0);
    fadeIn4.setToValue(1.0);

    ParallelTransition parallelTransition =
        new ParallelTransition(fadeIn1, fadeIn2, fadeIn3, fadeIn4);

    parallelTransition.play();

    parallelTransition.setOnFinished(
        (event) -> {
          FadeTransition fadeOut1 = new FadeTransition(Duration.seconds(1), rect);
          fadeOut1.setDelay(Duration.seconds(3));
          fadeOut1.setFromValue(1.0);
          fadeOut1.setToValue(0.0);

          FadeTransition fadeOut2 = new FadeTransition(Duration.seconds(1), completionImage);
          fadeOut2.setDelay(Duration.seconds(3));
          fadeOut2.setFromValue(1.0);
          fadeOut2.setToValue(0.0);

          FadeTransition fadeOut3 = new FadeTransition(Duration.seconds(1), completionText);
          fadeOut3.setDelay(Duration.seconds(3));
          fadeOut3.setFromValue(1.0);
          fadeOut3.setToValue(0.0);

          FadeTransition fadeOut4 =
              new FadeTransition(Duration.seconds(1), completionTextSecondRow);
          fadeOut4.setDelay(Duration.seconds(3));
          fadeOut4.setFromValue(1.0);
          fadeOut4.setToValue(0.0);

          fadeOut1.play();
          fadeOut2.play();
          fadeOut3.play();
          fadeOut4.play();
        });
  }

  public void exit() {
    Platform.exit();
  }
}
