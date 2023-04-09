package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.EdgeDAO;
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
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import net.kurobako.gesturefx.GesturePane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginPageController {
    @FXML
    MFXButton loginButton;

    @FXML
    StackPane stack;

    ObservableList<String> list =
            FXCollections.observableArrayList(
                    "Conference Room Request Form",
                    "Flowers Request Form",
                    "Furniture Request Form",
                    "Meal Request Form",
                    "Office Supplies Request Form");

    @FXML
    public void initialize()  {
      loginButton.setOnAction(event -> Navigation.navigate(Screen.HOME));
    }





    public void exit() {
        Platform.exit();
    }
}
