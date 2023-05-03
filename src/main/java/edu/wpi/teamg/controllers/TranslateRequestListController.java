package edu.wpi.teamg.controllers;

import edu.wpi.teamg.ORMClasses.Translate;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class TranslateRequestListController {
  @FXML TableColumn<Translate, String> languageColumn;
  @FXML TableColumn<Translate, String> locationColumn;
  @FXML TableColumn<Translate, String> employeeColumn;
  @FXML TableView<Translate> translateTable;

  public void initialize() {
    languageColumn.setCellValueFactory(
        row -> new SimpleStringProperty(row.getValue().getLanguage()));
    locationColumn.setCellValueFactory(
        row -> new SimpleStringProperty(row.getValue().getLocation()));
    employeeColumn.setCellValueFactory(
        row -> new SimpleStringProperty(row.getValue().getEmployee()));
    translateTable.setItems(FXCollections.observableList(Translate.getRequest()));
  }
}
