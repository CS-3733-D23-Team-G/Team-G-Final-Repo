package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import org.controlsfx.control.PopOver;

public class PatientTopBannerController {
  @FXML ImageView login;

  @FXML ImageView exit;
  @FXML ImageView information;
  @FXML ImageView dictionary;
  @FXML MFXButton logoButton;

  static PopOver window = new PopOver();

  static PopOver dictionaryPopOver = new PopOver();

  @FXML
  public void initialize() {

    exit.setOnMouseClicked(event -> exit());
    information.setOnMouseClicked(event -> Navigation.navigate(Screen.CREDITS));
    dictionary.setOnMouseClicked(
        event -> {
          try {
            dictionary();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    login.setOnMouseClicked(
        event -> {
          try {
            login();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    logoButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_SCREENSAVER_PAGE));
  }

  public void exit() {
    Platform.exit();
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

  public void login() throws IOException {

    var loader = new FXMLLoader(App.class.getResource("views/LoginPage.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    window.setTitle("Login Panel");

    window.setHeaderAlwaysVisible(true);
    LoginController controller = loader.getController();

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }
}
