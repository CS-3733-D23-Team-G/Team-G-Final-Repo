package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.EmployeeDAO;
import io.github.palexdev.materialfx.controls.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AddNotificationController {

  @FXML MFXButton notifSubmit;

  @FXML MFXButton notifClear;

  @FXML MFXDatePicker notifDate;

  @FXML MFXTextField notifTime;

  @FXML MFXComboBox notifType;

  @FXML TextArea notifMessage;

  @FXML MFXCheckListView notifRecipients;

  ObservableList<String> notifTypeList =
      FXCollections.observableArrayList("Alert", "Request Assign", "Message");

  ObservableList<String> recipientsList =
      FXCollections.observableArrayList("Viet Hung Pham", "Kristine Guan", "Aaron Mar");

  EmployeeDAO empDao = new EmployeeDAO();

  public void initialize() throws SQLException {

    HashMap EmpHashMap = empDao.getAllEmployeeFullName();

    ArrayList<String> employeesList = new ArrayList<>();

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
  }

  public void storeNotificationValues() throws SQLException {

    ObservableMap selectedRecipients = notifRecipients.getSelectionModel().getSelection();

    selectedRecipients.forEach(
        (i, m) -> {
          System.out.println(m);
        });
  }

  public void clearFlowers() {
    notifRecipients.setItems(null);
    notifType.setValue(null);
    notifMessage.setText("");
    notifDate.setValue(null);


  }
}
