package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class HomeController {
  DAORepo dao = new DAORepo();
  static HashMap totalNodes;
  static HashMap totalEdges;
  static HashMap totalLNames;
  static List totalMoves;
  @FXML MFXButton signagePageButton;
  @FXML MFXButton exitButton;

  @FXML MFXButton statusButton;
  @FXML ChoiceBox<String> serviceRequestChoiceBox;

  ObservableList<String> list =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");

  @FXML
  public void initialize() throws SQLException {
    signagePageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
    exitButton.setOnMouseClicked(event -> exit());
    statusButton.setOnMouseClicked(event -> Navigation.navigate(Screen.STATUS_PAGE));

    serviceRequestChoiceBox.setItems(list);
    serviceRequestChoiceBox.setOnAction(event -> loadServiceRequestForm());

    totalNodes = dao.getAllNodes();
    totalEdges = dao.getAllEdges();
    totalLNames = dao.getAllLocationNames();
    totalMoves = dao.getAllMoves();
  }

  public void exit() {
    Platform.exit();
  }

  //  public void loadServiceRequestForm() {
  //    if (serviceRequestChoiceBox.getValue().equals("Meal Request Form")) {
  //      Navigation.navigate(Screen.MEAL_REQUEST);
  //    } else if (serviceRequestChoiceBox.getValue().equals("Furniture Request Form")) {
  //      Navigation.navigate(Screen.FURNITURE_REQUEST);
  //    } else if (serviceRequestChoiceBox.getValue().equals("Conference Room Request Form")) {
  //      Navigation.navigate(Screen.ROOM_REQUEST);
  //    } else if (serviceRequestChoiceBox.getValue().equals("Flowers Request Form")) {
  //      Navigation.navigate(Screen.FLOWERS_REQUEST);
  //    } else if (serviceRequestChoiceBox.getValue().equals("Office Supplies Request Form")) {
  //      Navigation.navigate(Screen.SUPPLIES_REQUEST);
  //    } else {
  //      return;
  //    }
  //  }
  public void loadServiceRequestForm() {
    String selectedValue = serviceRequestChoiceBox.getValue();
    switch (selectedValue) {
      case "Meal Request Form":
        Navigation.navigate(Screen.MEAL_REQUEST);
        break;
      case "Furniture Request Form":
        Navigation.navigate(Screen.FURNITURE_REQUEST);
        break;
      case "Conference Room Request Form":
        Navigation.navigate(Screen.ROOM_REQUEST);
        break;
      case "Flowers Request Form":
        Navigation.navigate(Screen.FLOWERS_REQUEST);
        break;
      case "Office Supplies Request Form":
        Navigation.navigate(Screen.SUPPLIES_REQUEST);
        break;
      default:
        return;
    }
  }
}
