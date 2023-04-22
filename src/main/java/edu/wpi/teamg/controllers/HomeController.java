package edu.wpi.teamg.controllers;

import static edu.wpi.teamg.DAOs.RequestDAO.getOutstandingRequest;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.RequestDAO;
import edu.wpi.teamg.ORMClasses.Request;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class HomeController {
  @FXML Text empName;
  @FXML MFXButton EmployeeinfoHyperlink;
  @FXML VBox forms;

  // TODO if there are no requests, add a message saying currently no requests.

  @FXML
  public void initialize() throws SQLException {

    empName.setText(" " + App.employee.getFirstName() + " " + App.employee.getLastName());
    empName.setFill(Color.valueOf("#012D5A"));

    RequestDAO requestDAO = new RequestDAO();
    HashMap<Integer, Request> hash = getOutstandingRequest(App.employee.getEmpID());

    // i will eventually be number of blank and processing requests
    for (int i = 0; i < hash.size(); i++) {

      Text newTest = new Text("This is VBOX test");

      HBox newAnchorPane = new HBox();

      newAnchorPane.setStyle(
          "-fx-background-color: #C0C0C0;-fx-background-radius: 10; -fx-pref-width: 360; -fx-pref-height: 150; fx-padding: 10; -fx-border-insets: 10; -fx-background-insets: 10;");

      newAnchorPane.getChildren().add(newTest);
      forms.getChildren().add(newAnchorPane);
    }
    EmployeeinfoHyperlink.setOnAction(event -> Navigation.navigate(Screen.EMPLOYEE_INFO));
  }
}
