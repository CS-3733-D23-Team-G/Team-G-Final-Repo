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

public class TopBannerController {

  @FXML MFXButton HomeButton;
  @FXML MFXButton signagePageButton;

  @FXML MFXButton statusButton;
  @FXML ChoiceBox<String> serviceRequestChoiceBox;

  @FXML ImageView logout;

  @FXML ImageView exit;
  @FXML ImageView information;
  @FXML ImageView dictionary;

  static PopOver dictionaryPopOver = new PopOver();

  ObservableList<String> list =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Maintenance Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");

  @FXML
  public void initialize() {
    App.bool = false;
    //    signagePageButton.setOnAction(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
    signagePageButton.setOnAction(event -> Navigation.navigate(Screen.PATHFINDING_PAGE));
    exit.setOnMouseClicked(event -> exit());
    statusButton.setOnMouseClicked(event -> Navigation.navigate(Screen.ADMIN_STATUS_PAGE));

    serviceRequestChoiceBox.setItems(list);
    serviceRequestChoiceBox.setOnAction(event -> loadServiceRequestForm());
    logout.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_SCREENSAVER_PAGE));
    HomeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    dictionary.setOnMouseClicked(
        event -> {
          try {
            dictionary();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    information.setOnMouseClicked(event -> Navigation.navigate(Screen.ABOUT_PAGE));
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
}
