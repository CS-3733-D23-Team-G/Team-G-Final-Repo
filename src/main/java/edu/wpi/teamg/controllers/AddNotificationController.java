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

  @FXML MFXTextField notifHeader;

  @FXML MFXComboBox notifType;

  @FXML TextArea notifMessage;

  @FXML MFXCheckListView notifRecipients;

  @FXML Label checkFields;

  @FXML MFXCheckbox selectAll;

  @FXML MFXCheckbox dismissible;

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
                notifRecipients.getSelectionModel().clearSelection();
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
    notifSubmit.setOnAction(event -> allDataFilled());
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
    notifRecipients.setItems(null);
    notifType.setValue(null);
    notifMessage.setText("");
    notifDate.setValue(null);
    notifTime.setText("");
    notifHeader.setText("");
  }
}
