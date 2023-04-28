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
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class HomeController {
  @FXML Text empName;
  // @FXML MFXButton EmployeeinfoHyperlink;
  @FXML VBox forms;
  @FXML VBox notifications;
  @FXML AnchorPane formsForAnimation;

  // TODO if there are no requests, add a message saying currently no requests.

  @FXML
  public void initialize() throws SQLException {

    empName.setText(" " + App.employee.getFirstName() + " " + App.employee.getLastName());
    empName.setFill(Color.valueOf("#012D5A"));

    empName.setOnMouseClicked(event -> completeAnimation());

    RequestDAO requestDAO = new RequestDAO();
    // HashMap<Integer, Request> hash = requestDAO.getOutstandingRequest(App.employee.getEmpID());
    ArrayList<Request> hash = requestDAO.getOutstandingRequest(App.employee.getEmpID());

    hash.forEach(
        (i) -> {
          Text requestID = new Text("Ticket #" + i.getReqid());
          requestID.setLayoutX(50);
          requestID.setLayoutY(45);
          requestID.setStyle("-fx-font-size: 20; -fx-font-weight: 800; -fx-font-family: Poppins");
          String type = i.getReqtype();
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

          StatusTypeEnum status = i.getStatus();
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

          Text date = new Text("Do By: " + i.getRequestDate());
          date.setLayoutX(50);
          date.setLayoutY(120);
          date.setStyle(
              "-fx-font-size: 20; -fx-font-weight: 500;"
                  + "-fx-alignment: right; -fx-font-family: Poppins");

          Text bubbleText = new Text(String.valueOf(i.getStatus()));
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

  public void completeAnimation() {

    // Form Completion PopUp
    AnchorPane rect = new AnchorPane();
    rect.setLayoutX(325);
    rect.setStyle(
        "-fx-pref-width: 440; -fx-pref-height: 100; -fx-background-color: #d9d9d9; -fx-border-radius: 5; -fx-background-insets: 5; -fx-border-insets: 5; -fx-padding: 5;"
            + "-fx-border-color: #000000;"
            + "-fx-border-width: 3;");
    rect.setLayoutY(800);
    rect.toFront();

    Text completionText = new Text("You Are All Set!");
    completionText.setLayoutX(445);
    completionText.setLayoutY(850);
    completionText.setStyle(
        "-fx-stroke: #000000;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 25;"
            + "-fx-font-weight: 500;");
    completionText.toFront();

    Text completionTextSecondRow = new Text("Conference Room Request Sent Successfully.");
    completionTextSecondRow.setLayoutX(445);
    completionTextSecondRow.setLayoutY(870);
    completionTextSecondRow.setStyle(
        "-fx-stroke: #000000;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 15;"
            + "-fx-font-weight: 500;");
    completionTextSecondRow.toFront();

    Image checkmarkImage = new Image("edu/wpi/teamg/Images/checkMarkIcon.png");
    ImageView completionImage = new ImageView(checkmarkImage);

    completionImage.setFitHeight(120);
    completionImage.setFitWidth(120);
    completionImage.setLayoutX(320);
    completionImage.setLayoutY(790);
    completionImage.toFront();

    rect.setOpacity(0.0);
    completionImage.setOpacity(0.0);
    completionText.setOpacity(0.0);
    completionTextSecondRow.setOpacity(0.0);

    formsForAnimation.getChildren().add(rect);
    formsForAnimation.getChildren().add(completionText);
    formsForAnimation.getChildren().add(completionImage);
    formsForAnimation.getChildren().add(completionTextSecondRow);

    FadeTransition fadeIn1 = new FadeTransition(Duration.seconds(1), rect);
    fadeIn1.setFromValue(0.0);
    fadeIn1.setToValue(1.0);

    FadeTransition fadeIn2 = new FadeTransition(Duration.seconds(1), completionImage);
    fadeIn2.setFromValue(0.0);
    fadeIn2.setToValue(1.0);

    FadeTransition fadeIn3 = new FadeTransition(Duration.seconds(1), completionText);
    fadeIn3.setFromValue(0.0);
    fadeIn3.setToValue(1.0);

    FadeTransition fadeIn4 = new FadeTransition(Duration.seconds(1), completionTextSecondRow);
    fadeIn4.setFromValue(0.0);
    fadeIn4.setToValue(1.0);

    ParallelTransition parallelTransition =
        new ParallelTransition(fadeIn1, fadeIn2, fadeIn3, fadeIn4);

    parallelTransition.play();

    parallelTransition.setOnFinished(
        (event) -> {
          FadeTransition fadeOut1 = new FadeTransition(Duration.seconds(1), rect);
          fadeOut1.setDelay(Duration.seconds(3));
          fadeOut1.setFromValue(1.0);
          fadeOut1.setToValue(0.0);

          FadeTransition fadeOut2 = new FadeTransition(Duration.seconds(1), completionImage);
          fadeOut2.setDelay(Duration.seconds(3));
          fadeOut2.setFromValue(1.0);
          fadeOut2.setToValue(0.0);

          FadeTransition fadeOut3 = new FadeTransition(Duration.seconds(1), completionText);
          fadeOut3.setDelay(Duration.seconds(3));
          fadeOut3.setFromValue(1.0);
          fadeOut3.setToValue(0.0);

          FadeTransition fadeOut4 =
              new FadeTransition(Duration.seconds(1), completionTextSecondRow);
          fadeOut4.setDelay(Duration.seconds(3));
          fadeOut4.setFromValue(1.0);
          fadeOut4.setToValue(0.0);

          fadeOut1.play();
          fadeOut2.play();
          fadeOut3.play();
          fadeOut4.play();
        });
  }
}
