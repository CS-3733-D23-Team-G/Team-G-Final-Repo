package edu.wpi.teamg.controllers;

import static edu.wpi.teamg.DAOs.RequestDAO.getOutstandingRequest;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.RequestDAO;
import edu.wpi.teamg.ORMClasses.Request;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    HashMap<Integer, Request> hash = requestDAO.getOutstandingRequest(App.employee.getEmpID());

    hash.forEach(
        (i, m) -> {
          Text requestID = new Text("Request #" + i + ": " + m.getStatus());
          requestID.setLayoutX(20);
          requestID.setLayoutY(35);
          requestID.setStyle("-fx-font-size: 24;");
          String type = m.getReqtype();
          String thisType = "";

          switch (type) {
            case "M":
              thisType = "Meal ";
              break;
            case "CR":
              thisType = "Conference Room ";
              break;

            case "FL":
              thisType = "Flower ";
              break;
            case "FR":
              thisType = "Furniture ";
              break;
            case "OS":
              thisType = "Office Supply ";
              break;
          }

          StatusTypeEnum status = m.getStatus();
          String color = "";
          switch (status) {
            case blank:
              color = "#B12B00;";
              break;
            case processing:
              color = "#0067B1;";
              break;
          }

          Text request = new Text(thisType + "Request");
          request.setLayoutX(20);
          request.setLayoutY(85);
          request.setStyle("-fx-font-size: 24;");

          Text date = new Text("Do By: " + m.getRequestDate());
          date.setLayoutX(470);
          date.setLayoutY(85);
          date.setStyle("-fx-font-size: 24;" + "-fx-alignment: right");

          Circle circle = new Circle(20);
          circle.setLayoutX(675);
          circle.setLayoutY(70);
          circle.setStyle("-fx-fill: " + color);

          AnchorPane newAnchorPane = new AnchorPane();

          newAnchorPane.setStyle(
              "-fx-background-color: #C0C0C0;"
                  + "-fx-background-radius: 10;"
                  + " -fx-pref-width: 335;"
                  + "-fx-pref-height: 150;"
                  // top right bottom left
                  + " -fx-padding: 10 25 10 25;"
                  + " -fx-border-insets: 10 25 10 25;"
                  + " -fx-background-insets: 10 25 10 25;");

          newAnchorPane.getChildren().add(requestID);
          newAnchorPane.getChildren().add(request);
          newAnchorPane.getChildren().add(circle);
          newAnchorPane.getChildren().add(date);
          forms.getChildren().add(newAnchorPane);
        });

    EmployeeinfoHyperlink.setOnAction(event -> Navigation.navigate(Screen.EMPLOYEE_INFO));
  }
}
