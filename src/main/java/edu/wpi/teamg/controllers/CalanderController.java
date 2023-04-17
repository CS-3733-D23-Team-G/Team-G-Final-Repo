package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class CalanderController {
  @FXML GridPane Calander;
  @FXML Text MonthText;
  @FXML MFXButton MonthForward;
  @FXML MFXButton MonthBack;
  int MonthState = 4;
  int StartDate = 6;
  String MonthVal = "April";

  @FXML
  public void initialize() {
    CalanderSet();
    MonthForward.setOnMouseClicked(event -> MonthSwitch(true));
    MonthBack.setOnMouseClicked(event -> MonthSwitch(false));
  }

  int MonthTotal = 30;

  public void MonthSwitch(Boolean MonthForward) {
    if (MonthForward) {
      MonthState++;
    } else {
      MonthState--;
    }
    if (Math.abs(MonthState - 4) > 2) {
      MonthState = 4;
    }
    switch (MonthState) {
      case 2:
        MonthVal = "February";
        MonthTotal = 28;
        StartDate = 4;
        CalanderSet();
        break;
      case 3:
        MonthVal = "March";
        MonthTotal = 31;
        StartDate = 3;
        CalanderSet();
        break;
      case 4:
        MonthVal = "April";
        MonthTotal = 30;
        StartDate = 6;
        CalanderSet();
        break;
      case 5:
        MonthVal = "May";
        MonthTotal = 31;
        StartDate = 1;
        CalanderSet();
        break;
      case 6:
        MonthVal = "June";
        MonthTotal = 30;
        StartDate = 4;
        CalanderSet();
        break;
    }
  }

  public void CalanderSet() {
    Calander.getChildren().clear();
    Boolean StartDateCheck = false;
    MonthText.setText(MonthVal);
    int DateAcc = 1;
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 7; j++) {
        StartDateCheck = (j == StartDate) || StartDateCheck;
        if (DateAcc <= MonthTotal && StartDateCheck) {
          Text dateInput = new Text(DateAcc + "");

          Calander.add(dateInput, j, i);
          DateAcc++;
        }
      }
      DateAcc++;
    }
  }
}
