package edu.wpi.teamg.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class TranslateRequestController {
  @FXML TextArea language;
  @FXML TextArea longName;
  @FXML TextArea employeeID;
  @FXML Button submitButton;

  ObservableList<String[]> recordList;

  public void initialize() {
    language.setEditable(true);
    longName.setEditable(true);
    employeeID.setEditable(true);
    submitButton.setOnMouseClicked(
        event -> {
          try {
            submit();
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });
  }

  String recordLanguage;
  String recordLocation;
  String recordEmployee;

  public void submit() {
    String[] record = {recordLanguage, recordLocation, recordEmployee};
    recordLanguage = String.valueOf(language);
    recordLocation = String.valueOf(longName);
    recordEmployee = String.valueOf(employeeID);
    recordList.add(record);
  }
}
