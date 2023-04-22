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
import org.controlsfx.control.PopOver;

public class PatientTopBannerController {
  @FXML MFXButton exitButton;

  @FXML MFXButton loginButton;
  @FXML MFXButton logoButton;

  static PopOver window = new PopOver();

  @FXML
  public void initialize() {
    //    signagePageButton.setOnAction(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
    exitButton.setOnMouseClicked(event -> exit());
    // loginButton.setOnMouseClicked(event -> Navigation.navigate(Screen.LOGIN_PAGE));
    loginButton.setOnMouseClicked(
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
