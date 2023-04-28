package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.EmployeeDAO;
import edu.wpi.teamg.DAOs.NotificationDAO;
import edu.wpi.teamg.DAOs.RequestDAO;
import edu.wpi.teamg.ORMClasses.Notification;
import edu.wpi.teamg.ORMClasses.Request;
import edu.wpi.teamg.ORMClasses.StatusTypeEnum;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class HomeController {
  @FXML Text empName;
  // @FXML MFXButton EmployeeinfoHyperlink;
  @FXML VBox forms;
  @FXML VBox notifications;
  @FXML Text quoteText;
  @FXML Text authorText;

  // TODO if there are no requests, add a message saying currently no requests.

  @FXML
  public void initialize() throws SQLException {
    fillQuote();
    ;
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

  public void fillQuote() {
    String output = "Nah Fam";

    try {

      URL url = new URL("https://zenquotes.io/api/random");

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.connect();

      // Check if connect is made
      int responseCode = conn.getResponseCode();
      // 200 OK
      if (responseCode != 200) {
        throw new RuntimeException("HttpResponseCode: " + responseCode);
      } else {

        StringBuilder informationString = new StringBuilder();
        Scanner scanner = new Scanner(url.openStream());

        while (scanner.hasNext()) {
          informationString.append(scanner.nextLine());
        }
        // Close the scanner
        scanner.close();
        //        System.out.println("doctor");
        //        System.out.println(informationString);

        // JSON simple library Setup with Maven is used to convert strings to JSON
        JSONParser parse = new JSONParser();
        JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));

        // Get the first JSON object in the JSON array
        //        System.out.println(dataObject.get(0));

        JSONObject JSONOut = (JSONObject) dataObject.get(0);
        String person;
        String quote;
        //        person = JSONOut.get("q").toStirng();
        quote = JSONOut.get("q").toString();
        //        authorText.setText(person);
        quoteText.setText(quote);
        conn.disconnect();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
