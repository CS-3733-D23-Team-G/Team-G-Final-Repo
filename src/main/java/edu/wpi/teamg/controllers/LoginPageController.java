package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class LoginPageController {

  @FXML private TextField emailBox;

  @FXML private Label forgotLabel;

  @FXML private Label hospitalLabel;

  @FXML private Label labelSubText;

  @FXML private MFXButton loginButton;

  @FXML private TextField passwordBox;

  @FXML private MFXButton signUpButton;

  @FXML private TextField signUpField;
  private Pane signUpPanel;
  private Pane signInPanel;

  private MFXButton backButton;
  private TextField employeeIDField;

  private Label wrongLabel;

  public void initialize() {
    loginButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    signUpButton.setOnMouseClicked(event -> signUpPanel.toFront());
    backButton.setOnMouseClicked(event -> signInPanel.toFront());
    signUpButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    // If email or password is incorrct, set label text to " Email or Password is Incorrect. Please
    // try again.

  }

  public void exit() {
    Platform.exit();
  }
}
