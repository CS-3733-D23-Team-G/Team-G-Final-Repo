package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import org.controlsfx.control.PopOver;

public class AdminTopBannerController {
  @FXML MFXButton signagePageButton;
  @FXML MFXButton statusButton;
  @FXML ChoiceBox<String> serviceRequestChoiceBox;
  @FXML MFXButton HomeButton;

  @FXML ImageView logout;

  @FXML ImageView exit;

  @FXML ImageView settings;

  @FXML ImageView dictionary;

  @FXML ImageView information;

  @FXML ChoiceBox<String> AdminChoiceBox;

  static PopOver window = new PopOver();

  static PopOver dictionaryPopOver = new PopOver();
  ObservableList<String> list =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Maintenance Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");
  ObservableList<String> AdminList =
      FXCollections.observableArrayList(
          "Signage Page Editor",
          "Table View",
          "All Form Status",
          "Map Editor",
          "Add Employee",
          "Send Notification");

  @FXML
  public void initialize() {
    dictionary.setOnMouseClicked(
        event -> {
          try {
            dictionary();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    information.setOnMouseClicked(event -> Navigation.navigate(Screen.CREDITS));
    signagePageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.PATHFINDING_PAGE));
    settings.setOnMouseClicked(
        (event -> {
          try {
            settings();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        }));
    exit.setOnMouseClicked(event -> exit());
    statusButton.setOnMouseClicked(event -> Navigation.navigate(Screen.ADMIN_STATUS_PAGE));
    logout.setOnMouseClicked(
        event -> {
          Navigation.navigate(Screen.SIGNAGE_SCREENSAVER_PAGE);
          App.employee.setIs_admin(false);
        });
    serviceRequestChoiceBox.setItems(list);
    serviceRequestChoiceBox.setOnAction(event -> loadServiceRequestForm());
    HomeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    AdminChoiceBox.setItems(AdminList);
    AdminChoiceBox.setOnAction(event -> AdminServiceForms());
  }

  public void dictionary() throws IOException {

    var loader = new FXMLLoader(App.class.getResource("views/DictionaryPopUp.fxml"));
    dictionaryPopOver.setContentNode(loader.load());

    dictionaryPopOver.setArrowSize(0);
    dictionaryPopOver.setTitle("Medical Dictionary");

    dictionaryPopOver.setHeaderAlwaysVisible(true);
    DictionaryController controller = loader.getController();

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    dictionaryPopOver.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }

  public void exit() {
    Platform.exit();
  }

  public void AdminServiceForms() {
    if (AdminChoiceBox.getValue().equals("Signage Page Editor")) {
      Navigation.navigate(Screen.EDIT_SIGNAGE_PAGE);
    } else if (AdminChoiceBox.getValue().equals("Table View")) {
      Navigation.navigate(Screen.ADMIN_SIGNAGE_EDITOR);
    } else if (AdminChoiceBox.getValue().equals("All Form Status")) {
      Navigation.navigate(Screen.ADMIN_STATUS_PAGE);
    } else if (AdminChoiceBox.getValue().equals("Map Editor")) {
      Navigation.navigate(Screen.ADMIN_MAP_EDITOR);
    } else if (AdminChoiceBox.getValue().equals("Add Employee")) {
      Navigation.navigate(Screen.ADD_EMPLOYEE);
    } else if (AdminChoiceBox.getValue().equals("Send Notification")) {
      Navigation.navigate((Screen.NOTIFICATION_PAGE));
    } else {
      return;
    }
  }

  public void loadServiceRequestForm() {
    if (serviceRequestChoiceBox.getValue().equals("Meal Request Form")) {
      Navigation.navigate(Screen.MEAL_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Furniture Request Form")) {
      Navigation.navigate(Screen.FURNITURE_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Conference Room Request Form")) {
      Navigation.navigate(Screen.ROOM_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Flowers Request Form")) {
      Navigation.navigate(Screen.FLOWERS_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Office Supplies Request Form")) {
      Navigation.navigate(Screen.SUPPLIES_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Maintenance Request Form")) {
      Navigation.navigate(Screen.MAINTENANCE_REQUEST);
    } else {
      return;
    }
  }

  public void settings() throws IOException {

    var loader = new FXMLLoader(App.class.getResource("views/SettingsPopover.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    window.setTitle("Settings Panel");

    window.setHeaderAlwaysVisible(true);
    SettingsPopOverController controller = loader.getController();

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }
}
