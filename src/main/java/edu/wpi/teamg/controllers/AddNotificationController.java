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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class AddNotificationController {

  @FXML MFXButton notifSubmit;

  @FXML MFXButton notifClear;

  @FXML MFXDatePicker notifDate;

  @FXML MFXTextField notifTime;

  @FXML MFXTextField notifHeader;

  @FXML MFXComboBox notifType;

  @FXML TextArea notifMessage;

  @FXML MFXCheckListView notifRecipients;

  @FXML Label checkFields;

  @FXML MFXCheckbox selectAll;

  @FXML MFXCheckbox dismissible;

  @FXML AnchorPane forms;

  // AddNotification
  @FXML TableView<Notification> notifTable;
  @FXML TableColumn<Notification, Integer> notifAlertIDColumn;
  @FXML TableColumn<Notification, Date> notifDateColumn;
  @FXML TableColumn<Notification, Time> notifTimeColumn;
  @FXML TableColumn<Notification, String> notifTypeColumn;
  @FXML TableColumn<Notification, Integer> notifEmpIDColumn;
  @FXML TableColumn<Notification, String> notifRecipientsColumn;
  @FXML TableColumn<Notification, String> notifMessageColumn;
  @FXML TableColumn<Notification, String> notifHeaderColumn;

  // TODO
  ObservableList<Notification> testNotificationList;

  ObservableList<String> notifTypeList =
      FXCollections.observableArrayList("Alert", "Request Assign", "Message");

  ObservableList<String> recipientsList;

  EmployeeDAO empDao = new EmployeeDAO();
  NotificationDAO notifDao = new NotificationDAO();
  ArrayList<String> employeesList = new ArrayList<>();

  public void initialize() throws SQLException {
    App.bool = false;
    HashMap EmpHashMap = empDao.getAllEmployeeFullName();

    dismissible.setSelected(true);

    checkFields.setVisible(false);

    EmpHashMap.forEach(
        (i, m) -> {
          employeesList.add("ID " + i + ": " + m);
        });

    notifSubmit.setOnAction(
        event -> {
          try {
            // completeAnimation("Notification sent!");
            storeNotificationValues();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    recipientsList = FXCollections.observableArrayList(employeesList);

    notifRecipients.setItems(recipientsList);
    notifType.setItems(notifTypeList);

    notifType
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (options, oldValue, newValue) -> {
              if (newValue.equals("Alert")) {
                notifRecipients.getSelectionModel().selectItems(recipientsList);
                dismissible.setSelected(false);
                selectAll.setSelected(true);
              } else {
                selectAll.setSelected(false);
                dismissible.setSelected(true);
              }
            });

    selectAll.setOnAction(
        event -> {
          if (!selectAll.isSelected()) {
            notifRecipients.getSelectionModel().clearSelection();
          } else if (selectAll.isSelected()) {
            notifRecipients.getSelectionModel().selectItems(recipientsList);
          }
        });

    notifClear.setOnAction(event -> clearNotif());
    notifSubmit.setOnAction(
        event -> {
          allDataFilled();
        });

    ArrayList<Notification> testingNotification = App.testingNotifs;

    testNotificationList = FXCollections.observableArrayList(testingNotification);

    notifTable.setItems(testNotificationList);

    notifAlertIDColumn.setCellValueFactory(new PropertyValueFactory<>("alertID"));
    notifDateColumn.setCellValueFactory(new PropertyValueFactory<>("notifDate"));
    notifTimeColumn.setCellValueFactory(new PropertyValueFactory<>("notifTime"));
    notifTypeColumn.setCellValueFactory(new PropertyValueFactory<>("notiftype"));
    notifEmpIDColumn.setCellValueFactory(new PropertyValueFactory<>("empid"));
    notifRecipientsColumn.setCellValueFactory(new PropertyValueFactory<>("recipients"));
    notifMessageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
    notifHeaderColumn.setCellValueFactory(new PropertyValueFactory<>("notifheader"));
  }

  public void allDataFilled() {
    if (!(notifDate == null
            || notifTime.equals("")
            || notifType.getValue() == null
            || notifMessage.getText() == null
            || notifRecipients.getSelectionModel() == null)
        || notifHeader.equals("")) {
      try {
        storeNotificationValues();
        completeAnimation("Notification sent!");
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
            notifHeader.getText(),
            notifMessage.getText(),
            notifType.getText(),
            recipients.toString(),
            Date.valueOf(notifDate.getValue()),
            StringToTime(notifTime.getText()),
            dismissible.isSelected());

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
    notifHeader.setText("");
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
}
