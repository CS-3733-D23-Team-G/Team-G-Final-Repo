package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class When2MeetController {

  @FXML MFXButton clearButton;
  @FXML GridPane timeGrid;
  @FXML MFXComboBox roomComboBox;
  @FXML Label dayLabel;

  public void intiialize() {

    String[] daysOfWeek = {
      "", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    };
    for (int i = 0; i < daysOfWeek.length; i++) {
      Label dayLabel = new Label(daysOfWeek[i]);
      dayLabel.setStyle("-fx-font-weight: bold");
      timeGrid.add(dayLabel, i + 1, 0);
    }

    for (int i = 0; i < 24; i++) {
      String timeSlot = String.format("%02d:00", i);
      Label timeLabel = new Label(timeSlot);
      timeLabel.setStyle("-fx-font-weight: bold");
      timeGrid.add(timeLabel, 0, i + 1);
    }
  }
}
