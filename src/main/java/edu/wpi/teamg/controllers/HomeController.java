package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.NotificationDAO;
import edu.wpi.teamg.DAOs.RequestDAO;
import edu.wpi.teamg.ORMClasses.Notification;
import edu.wpi.teamg.ORMClasses.Request;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

public class HomeController {
  @FXML Text empName;
  // @FXML MFXButton EmployeeinfoHyperlink;
  @FXML VBox forms;
  @FXML VBox notifications;

  static PopOver deleteConfirmation = new PopOver();

  static int notifToBeDeleted;

  // TODO if there are no requests, add a message saying currently no requests.

  @FXML
  public void initialize() throws SQLException {
    App.bool = false;
    empName.setText(" " + App.employee.getFirstName() + " " + App.employee.getLastName());
    empName.setFill(Color.valueOf("#012D5A"));

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

          long millis = System.currentTimeMillis();
          java.sql.Date currentDate = new java.sql.Date(millis);
          LocalDate currentLocalDate = currentDate.toLocalDate();

          Period periodOverdued =
              Period.between(i.getRequestDate().toLocalDate(), currentLocalDate);
          int daysOverdued = periodOverdued.getDays();

          int dateCompare = i.getRequestDate().compareTo(currentDate);

          System.out.println(dateCompare);

          if (dateCompare < 0) {

            date.setText("Do By: " + i.getRequestDate() + " (" + daysOverdued + " days overdue)");
            date.setFill(Paint.valueOf("#c20e15"));
            date.setStyle(
                "-fx-font-size: 20; -fx-font-weight: 800;"
                    + "-fx-alignment: right; -fx-font-family: Poppins");
          }

          if (i.getRequestDate().toLocalDate().isEqual(currentLocalDate)) {

            date.setText("Do By: " + i.getRequestDate() + " (Due Today)");
            date.setFill(Paint.valueOf("#000000"));
            date.setStyle(
                "-fx-font-size: 20; -fx-font-weight: 800;"
                    + "-fx-alignment: right; -fx-font-family: Poppins");
          }

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

    notifHash.forEach(
        (i) -> {
          Text notif = new Text("From: " + App.allEmployeeHash.get(i.getEmpid()));

          ImageView dismiss = new ImageView(App.notifDismissIcon);

          Button dismissBtn = new Button();
          dismissBtn.setStyle("-fx-pref-width: 28; -fx-pref-height: 28; -fx-opacity: 0");
          dismissBtn.setLayoutX(670);
          dismissBtn.setLayoutY(25);
          dismissBtn.toFront();

          dismiss.setFitHeight(28);
          dismiss.setLayoutX(670);
          dismiss.setLayoutY(25);
          // dismiss.toFront();
          dismiss.setPreserveRatio(true);

          dismissBtn.setOnMouseClicked(
              event -> {
                if (event.getButton() == MouseButton.PRIMARY) {
                  try {
                    notifToBeDeleted = i.getAlertID();
                    deleteConfirmation();
                  } catch (IOException e) {
                    throw new RuntimeException(e);
                  }
                  // System.out.println("Dismiss Notif " + i.getAlertID());
                }
              });

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

          notifAnchorPane.getChildren().add(dismiss);
          notifAnchorPane.getChildren().add(notif);
          notifAnchorPane.getChildren().add(notifDate);
          notifAnchorPane.getChildren().add(message);
          notifAnchorPane.getChildren().add(dismissBtn);
          notifications.getChildren().add(notifAnchorPane);
        });

    // EmployeeinfoHyperlink.setOnAction(event -> Navigation.navigate(Screen.EMPLOYEE_INFO));
  }

  public void deleteConfirmation() throws IOException {

    var loader =
        new FXMLLoader(App.class.getResource("views/NotificationDeletionConfirmation.fxml"));
    deleteConfirmation.setContentNode(loader.load());

    deleteConfirmation.setArrowSize(0);
    deleteConfirmation.setTitle("Confirm Deletion");

    deleteConfirmation.setHeaderAlwaysVisible(false);
    NotificationDeletionConfirmationController controller = loader.getController();

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    deleteConfirmation.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }
}
