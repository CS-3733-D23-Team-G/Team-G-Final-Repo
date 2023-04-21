package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class HomeController {
  @FXML Text empName;
  @FXML VBox forms;

  @FXML
  public void initialize() throws SQLException {

    empName.setText(" " + App.employee.getFirstName() + " " + App.employee.getLastName());
    empName.setFill(Color.valueOf("#012D5A"));

    for (int i = 0; i < 5; i++) {

      Text newTest = new Text("This is VBOX test");

      HBox newAnchorPane = new HBox();

      newAnchorPane.setStyle(
          "-fx-background-color: #C0C0C0; -fx-background-radius: 10; -fx-pref-width: 360; -fx-pref-height: 150; fx-padding: 10; -fx-border-insets: 10; -fx-background-insets: 10;");

      newAnchorPane.getChildren().add(newTest);
      forms.getChildren().add(newAnchorPane);
    }
  }
}
