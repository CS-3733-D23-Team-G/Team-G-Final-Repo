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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.stage.Stage;

public class LoginPageController extends Node {

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

  public void start(Stage primaryStage) throws Exception {
    // text field
    // set title for the stage
    primaryStage.setTitle("creating TextField");

    // create a textfield
    LoginPageController node = new LoginPageController();
    //node.setLayoutX(100);
    //node.setLayoutY(200);


    // create a stack pane
    StackPane r = new StackPane();

    // add textfield
    r.getChildren().add(node);

    // create a scene
    Scene sc = new Scene(r, 200, 200);

    // set the scene
    primaryStage.setScene(sc);

    primaryStage.show();

    // password field
    primaryStage.setTitle("PasswordField Experiment 1");

    PasswordField passwordField = new PasswordField();

    HBox hbox = new HBox(passwordField);

    Scene scene = new Scene(hbox, 200, 100);
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  public void exit() {
    Platform.exit();
  }
}
