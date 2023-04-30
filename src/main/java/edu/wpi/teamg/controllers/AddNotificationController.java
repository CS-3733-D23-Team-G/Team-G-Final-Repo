package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.EmployeeDAO;
import edu.wpi.teamg.DAOs.NotificationDAO;
import edu.wpi.teamg.ORMClasses.Notification;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.*;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class AddNotificationController {

  @FXML MFXButton notifSubmit;

  @FXML MFXButton notifClear;

  @FXML MFXDatePicker notifDate;

  @FXML MFXTextField notifTime;

  @FXML MFXComboBox notifType;

  @FXML TextArea notifMessage;

  @FXML MFXCheckListView notifRecipients;

  @FXML Label checkFields;

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
            storeNotificationValues();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    recipientsList = FXCollections.observableArrayList(employeesList);

    notifRecipients.setItems(recipientsList);
    notifType.setItems(notifTypeList);

    notifClear.setOnAction(event -> clearNotif());
    notifSubmit.setOnAction(event -> allDataFilled());
  }

  public void allDataFilled() {
    if (!(notifDate == null
        || notifTime.equals("")
        || notifType.getValue() == null
        || notifMessage.getText() == null
        || notifRecipients.getSelectionModel() == null)) {
      try {
        storeNotificationValues();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
      Navigation.navigate(Screen.NOTIFICATION_CONFIRMATION);
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
}
