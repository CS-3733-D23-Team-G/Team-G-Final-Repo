package edu.wpi.teamg.controllers;

import edu.wpi.teamg.ORMClasses.Translate;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TranslateController {

  @FXML TextField language;
  @FXML ComboBox<String> longName;
  @FXML ComboBox<String> employeeID;
  @FXML Button submitButton;

  @FXML Button viewHistory;

  public void initialize() {
    longName.getItems().add("45 Francis");
    longName.getItems().add("Tower");
    employeeID.getItems().add("Joe");
    employeeID.getItems().add("Doe");
    employeeID.getItems().add("Ian");

    language.getText();
    longName.getItems();
    employeeID.getItems();
    submitButton.setOnMouseClicked(
        event -> {
          try {
            translateRequest();
            listView();
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        });

    viewHistory.setOnMouseClicked(
        event -> {
          listView();
        });
  }

  //  ObservableList<String[]> recordList;
  //  String recordTime;
  //  String recordLanguage;
  //  String recordLocation;
  //  String recordEmployee;

  public void translateRequest() {
    Translate translate =
        new Translate(
            language.getText(),
            String.valueOf(longName.getValue()),
            String.valueOf(employeeID.getValue()));
    Translate.getRequest().add(translate);
  }

  public void listView() {
    Navigation.navigate(Screen.TRANSLATIONREQUESTLIST);
  }
}
