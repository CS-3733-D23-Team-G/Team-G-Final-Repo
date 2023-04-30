package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.EmployeeDAO;
import edu.wpi.teamg.DAOs.NotificationDAO;
import edu.wpi.teamg.ORMClasses.Notification;
import io.github.palexdev.materialfx.controls.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class AddNotificationController {

  @FXML MFXButton notifSubmit;

  @FXML MFXButton notifClear;

  @FXML MFXDatePicker notifDate;

  @FXML MFXTextField notifTime;

  @FXML MFXComboBox notifType;

  @FXML TextArea notifMessage;

  @FXML MFXCheckListView notifRecipients;

  @FXML Label checkFields;

  @FXML AnchorPane forms;

  ObservableList<String> notifTypeList =
      FXCollections.observableArrayList("Alert", "Request Assign", "Message");

  ObservableList<String> recipientsList;

  EmployeeDAO empDao = new EmployeeDAO();
  NotificationDAO notifDao = new NotificationDAO();
  ArrayList<String> employeesList = new ArrayList<>();

  public void initialize() throws SQLException {
    App.bool = false;
    HashMap EmpHashMap = empDao.getAllEmployeeFullName();

    checkFields.setVisible(false);

    EmpHashMap.forEach(
        (i, m) -> {
          employeesList.add("ID " + i + ": " + m);
        });

    notifSubmit.setOnAction(
        event -> {
          try {
            completeAnimation();
            storeNotificationValues();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    recipientsList = FXCollections.observableArrayList(employeesList);

    notifRecipients.setItems(recipientsList);
    notifType.setItems(notifTypeList);

    notifClear.setOnAction(event -> clearNotif());
    notifSubmit.setOnAction(
        event -> {
          allDataFilled();
        });
  }

  public void allDataFilled() {
    if (!(notifDate == null
        || notifTime.equals("")
        || notifType.getValue() == null
        || notifMessage.getText() == null
        || notifRecipients.getSelectionModel() == null)) {
      try {
        storeNotificationValues();
        completeAnimation();
        clearNotif();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    } else {
      checkFields.setVisible(true);
    }
  }

  public void storeNotificationValues() throws SQLException {

    ObservableMap selectedRecipients = notifRecipients.getSelectionModel().getSelection();

    AtomicReference<String> recipients = new AtomicReference<>("");

    selectedRecipients.forEach(
        (i, m) -> {
          String employee = (String) m;
          String[] split1 = new String[2];

          String serveBy = "";

          split1 = employee.split(":");

          serveBy = split1[0].substring(3);

          String finalServeBy = serveBy;

          recipients.updateAndGet(v -> v + finalServeBy + ",");
        });

    System.out.println(recipients);

    Notification notification =
        new Notification(
            App.employee.getEmpID(),
            notifMessage.getText(),
            notifType.getText(),
            recipients.toString(),
            Date.valueOf(notifDate.getValue()),
            StringToTime(notifTime.getText()));

    notifDao.insert(notification);
  }

  public Time StringToTime(String s) {

    String[] hourMin = s.split(":", 2);
    Time t = new Time(Integer.parseInt(hourMin[0]), Integer.parseInt(hourMin[1]), 00);
    return t;
  }

  public void clearNotif() {
    notifRecipients.getSelectionModel().getSelection().clear();
    notifType.setValue(null);
    notifMessage.setText("");
    notifDate.setValue(null);
    notifTime.setText("");
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

    Text completionTextSecondRow = new Text("Notification Successfully Sent.");
    completionTextSecondRow.setLayoutX(445);
    completionTextSecondRow.setLayoutY(870);
    completionTextSecondRow.setStyle(
        "-fx-stroke: #000000;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 15;"
            + "-fx-font-weight: 500;");
    completionTextSecondRow.toFront();

    Image checkmarkImage = new Image("edu/wpi/teamg/Images/checkMarkIcon.png");
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
}
