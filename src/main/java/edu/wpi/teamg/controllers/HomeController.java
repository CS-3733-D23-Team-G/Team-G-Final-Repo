package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.RequestDAO;
import edu.wpi.teamg.ORMClasses.Request;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class HomeController {
  @FXML Text empName;
  // @FXML MFXButton EmployeeinfoHyperlink;
  @FXML VBox forms;
  @FXML VBox notifications;

  // TODO if there are no requests, add a message saying currently no requests.

  @FXML
  public void initialize() throws SQLException {

    empName.setText(" " + App.employee.getFirstName() + " " + App.employee.getLastName());
    empName.setFill(Color.valueOf("#012D5A"));

    RequestDAO requestDAO = new RequestDAO();
    HashMap<Integer, Request> hash = requestDAO.getOutstandingRequest(App.employee.getEmpID());

    hash.forEach(
        (i, m) -> {
          Text requestID = new Text("Ticket #" + i);
          requestID.setLayoutX(50);
          requestID.setLayoutY(55);
          requestID.setStyle("-fx-font-size: 16; -fx-font-weight: 800; -fx-font-family: Poppins");
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
              color = "#E19797;";
              break;
            case processing:
              color = "#FFDA83;";
              break;
          }

          Text request = new Text(thisType + "Request");
          request.setLayoutX(50);
          request.setLayoutY(85);
          request.setStyle("-fx-font-size: 24; -fx-font-weight: 800; -fx-font-family: Poppins");

          Text date = new Text("Do By: " + m.getRequestDate());
          date.setLayoutX(50);
          date.setLayoutY(110);
          date.setStyle(
              "-fx-font-size: 16; -fx-font-weight: 500;"
                  + "-fx-alignment: right; -fx-font-family: Poppins");

          Text bubbleText = new Text(String.valueOf(m.getStatus()));
          bubbleText.setStyle(
              "-fx-font-size: 16; -fx-font-weight: 500;"
                  + "-fx-alignment: center; -fx-font-family: Poppins");
          bubbleText.setLayoutX(640);
          bubbleText.setLayoutY(87);
          bubbleText.toFront();

          //          Pane pane = new Pane();
          //          pane.setStyle(
          //              "-fx-fill: #FFDA83;"
          //                  + "-fx-background-radius: 10;"
          //                  + "-fx-pref-width: 150;"
          //                  + "-fx-pref-height: 50;");
          //            pane.setLayoutX(600);
          //            pane.setLayoutY(55);

          //  bubbleText.setTextAlignment(TextAlignment.CENTER);

          Rectangle rect = new Rectangle(150, 50);
          rect.setStyle("-fx-fill:" + color);
          rect.setArcHeight(10);
          rect.setArcWidth(10);
          rect.setLayoutX(600);
          rect.setLayoutY(55);

          StackPane stack = new StackPane();
          stack.getChildren().addAll(rect, bubbleText);
          stack.setLayoutX(550);
          stack.setLayoutY(52);

          //     pane.setStyle("-fx-background-color: " + color);
          //          pane.setStyle("-fx-fill: #E19797");

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
          newAnchorPane.getChildren().add(stack);
          // newAnchorPane.getChildren().add(rect);
          //          newAnchorPane.getChildren().add(pane);
          // newAnchorPane.getChildren().add(bubbleText);
          newAnchorPane.getChildren().add(request);
          newAnchorPane.getChildren().add(date);
          forms.getChildren().add(newAnchorPane);
        });

    // Basis for the notifications, fake data for now.
    for (int i = 0; i < 5; i++) {
      Text notif = new Text("Notification");
      notif.setLayoutX(50);
      notif.setLayoutY(45);
      notif.setStyle("-fx-font-size: 30; -fx-font-weight: 600; -fx-font-family: Poppins");
      //      notif.setLayoutX(50);
      //      notif.setLayoutY(90);
      //      notif.setStyle("-fx-font-size: 30; -fx-font-weight: 600; -fx-font-family: Poppins");

      Text message = new Text("Please reset your password!");
      message.setLayoutX(50);
      message.setLayoutY(90);
      message.setStyle("-fx-font-size: 30; -fx-font-weight: 600; -fx-font-family: Poppins");
      //      message.setLayoutX(250);
      //      message.setLayoutY(90);
      //      message.setStyle(
      //          "-fx-font-size: 30; -fx-font-weight: 600;"
      //              + "-fx-alignment: right; -fx-font-family: Poppins");

      //      Text status = new Text("Processing");
      //      status.setStyle(
      //          "-fx-font-size: 18; -fx-font-weight: 100;"
      //              + "-fx-alignment: center; -fx-font-family: Poppins");
      //      status.setLayoutX(625);
      //      status.setLayoutY(83);
      //      //        status.setLayoutX(675);
      //      //        status.setLayoutY(75);
      //      status.toFront();
      //
      //      Pane pane = new Pane();
      //      pane.setStyle(
      //          "-fx-background-color: #97E198;"
      //              + "-fx-background-radius: 10;"
      //              + " -fx-pref-width: 150;"
      //              + "-fx-pref-height: 40;");
      //
      //      pane.setLayoutX(600);
      //      pane.setLayoutY(55);

      AnchorPane notifAnchorPane = new AnchorPane();

      notifAnchorPane.setStyle(
          "-fx-background-color: #C0C0C0;"
              + "-fx-background-radius: 10;"
              + " -fx-pref-width: 335;"
              + "-fx-pref-height: 150;"
              // top right bottom left
              + " -fx-padding: 10 25 10 25;"
              + " -fx-border-insets: 10 25 10 25;"
              + " -fx-background-insets: 10 25 10 25;");

      //        notifAnchorPane.getChildren().add(requestID);
      notifAnchorPane.getChildren().add(notif);
      notifAnchorPane.getChildren().add(message);
      notifications.getChildren().add(notifAnchorPane);
    }

    // EmployeeinfoHyperlink.setOnAction(event -> Navigation.navigate(Screen.EMPLOYEE_INFO));
  }
}
