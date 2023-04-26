package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.EmployeeDAO;
import edu.wpi.teamg.DAOs.NotificationDAO;
import edu.wpi.teamg.DAOs.RequestDAO;
import edu.wpi.teamg.ORMClasses.Notification;
import edu.wpi.teamg.ORMClasses.Request;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import java.sql.SQLException;
import java.util.ArrayList;
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
          requestID.setLayoutY(45);
          requestID.setStyle("-fx-font-size: 20; -fx-font-weight: 800; -fx-font-family: Poppins");
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
            case "MA":
              thisType = "Maintenance ";
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
            case done:
              color = "#97E198;";
              break;
          }

          Text request = new Text(thisType + "Request");
          request.setLayoutX(50);
          request.setLayoutY(85);
          request.setStyle("-fx-font-size: 26; -fx-font-weight: 800; -fx-font-family: Poppins");

          Text date = new Text("Do By: " + m.getRequestDate());
          date.setLayoutX(50);
          date.setLayoutY(120);
          date.setStyle(
              "-fx-font-size: 20; -fx-font-weight: 500;"
                  + "-fx-alignment: right; -fx-font-family: Poppins");

          Text bubbleText = new Text(String.valueOf(m.getStatus()));
          bubbleText.setStyle(
              "-fx-font-size: 20; -fx-font-weight: 500;"
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
          stack.setLayoutX(535);
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

    NotificationDAO notifDao = new NotificationDAO();
    ArrayList<Notification> notifHash = notifDao.getAllNotificationOf(App.employee.getEmpID());

    EmployeeDAO employeeDAO = new EmployeeDAO();
    HashMap<Integer, String> allEmployeeHash = employeeDAO.getAllEmployeeFullName();

    notifHash.forEach(
        (i) -> {
          Text notif = new Text("From: " + allEmployeeHash.get(i.getEmpid()));

          notif.setLayoutX(50);
          notif.setLayoutY(45);
          notif.setStyle("-fx-font-size: 20; -fx-font-weight: 800; -fx-font-family: Poppins");
          //      notif.setLayoutX(50);
          //      notif.setLayoutY(90);
          //      notif.setStyle("-fx-font-size: 30; -fx-font-weight: 600; -fx-font-family:
          // Poppins");

          String notifType = i.getNotiftype().toLowerCase();
          String color = "";
          Text message = null;

          switch (notifType) {
            case "alert":
              message = new Text("ALERT: " + i.getMessage());
              color = "#E19797;";
              break;
            default:
              message = new Text(i.getMessage());
              color = "#C0C0C0;";
              break;
          }

          message.setLayoutX(50);
          message.setLayoutY(85);
          message.setStyle("-fx-font-size: 26; -fx-font-weight: 800; -fx-font-family: Poppins");

          Text notifDate = new Text("Date: " + i.getNotifDate());
          notifDate.setLayoutX(50);
          notifDate.setLayoutY(120);
          notifDate.setStyle(
              "-fx-font-size: 20; -fx-font-weight: 500;"
                  + "-fx-alignment: right; -fx-font-family: Poppins");

          AnchorPane notifAnchorPane = new AnchorPane();

          notifAnchorPane.setStyle(
              "-fx-background-color: "
                  + color
                  + ";"
                  + "-fx-background-radius: 10;"
                  + "-fx-pref-width: 335;"
                  + "-fx-pref-height: 150;"
                  // top right bottom left
                  + " -fx-padding: 10 25 10 25;"
                  + " -fx-border-insets: 10 25 10 25;"
                  + " -fx-background-insets: 10 25 10 25;");

          //        notifAnchorPane.getChildren().add(requestID);
          notifAnchorPane.getChildren().add(notif);
          notifAnchorPane.getChildren().add(notifDate);
          notifAnchorPane.getChildren().add(message);
          notifications.getChildren().add(notifAnchorPane);
        });

    // EmployeeinfoHyperlink.setOnAction(event -> Navigation.navigate(Screen.EMPLOYEE_INFO));
  }
}
