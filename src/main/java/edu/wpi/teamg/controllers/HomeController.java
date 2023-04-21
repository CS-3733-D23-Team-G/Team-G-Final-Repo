package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class HomeController {
  @FXML Text empName;

  @FXML
  public void initialize() throws SQLException {
    empName.setText(" " + App.employee.getFirstName() + " " + App.employee.getLastName());
    empName.setFill(Color.valueOf("#012D5A"));
  }
}
