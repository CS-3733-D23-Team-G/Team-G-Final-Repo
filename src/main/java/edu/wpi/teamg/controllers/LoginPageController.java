package edu.wpi.teamg.controllers;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginPageController {

  // public Label invalidInput;
  @FXML private MFXTextField emailBox;

  @FXML private Label forgotLabel;

  // @FXML private Label hospitalLabel;

  // @FXML private Label labelSubText;

  @FXML private MFXButton loginButton;

  @FXML private MFXPasswordField passwordBox;

  @FXML private MFXButton signUpButton;

  @FXML private TextField signUpField;
  // private Pane signUpPanel;
  // private Pane signInPanel;

  // private MFXButton backButton;
  // private TextField employeeIDField;

  // private Label wrongLabel;

  public void initialize() {
    // invalidInputLabel.getText();
    loginButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    // signUpButton.setOnMouseClicked(event -> signUpPanel.toFront());
    // backButton.setOnMouseClicked(event -> signInPanel.toFront());
    //  signUpButton.setOnMouseClicked(event -> allDataFilled());
    //    Navigation.navigate(Screen.HOME));
    // If email or password is incorrect, set label text to " Email or Password is Incorrect. Please
    // try again.

  }

  public void exit() {
    Platform.exit();
  }
}
