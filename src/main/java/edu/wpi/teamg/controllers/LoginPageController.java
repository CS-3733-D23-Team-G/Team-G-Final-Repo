package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.EdgeDAO;
import edu.wpi.teamg.DAOs.LoginDao;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.Edge;
import edu.wpi.teamg.ORMClasses.Graph;
import edu.wpi.teamg.ORMClasses.Node;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import net.kurobako.gesturefx.GesturePane;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;



public class LoginPageController {

    @FXML
    private TextField emailBox;

    @FXML
    private Label forgotLabel;

    @FXML
    private Label hospitalLabel;

    @FXML
    private Label labelSubText;

    @FXML
    private MFXButton loginButton;

    @FXML
    private TextField passwordBox;

    @FXML
    private MFXButton signUpButton;

    @FXML
    private TextField signUpField;
    private Pane signUpPanel;
    private Pane signInPanel;

    private MFXButton backButton;
    private TextField employeeIDField;

    private Label wrongLabel;
    private MFXButton backSignUpButton;


    LoginDao login = new LoginDao();


    public void initialize(){
        loginButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
        signUpButton.setOnMouseClicked(event -> signUpPanel.toFront());
        backButton.setOnMouseClicked(event -> signInPanel.toFront());
        signUpButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
        backSignUpButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
        // If email or password is incorrct, set label text to " Email or Password is Incorrect. Please try again.
        



    }


    public void exit() {
        Platform.exit();
    }
}
