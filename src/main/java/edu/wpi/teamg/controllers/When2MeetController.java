package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class When2MeetController {

  @FXML MFXButton clearButton;
  @FXML MFXButton submitButton;
  @FXML GridPane timeGrid;
  @FXML MFXComboBox roomDropDown;
  @FXML MFXDatePicker datePicker;

  ObservableList<String> rooms;

  public void initialize() {

    ArrayList<String> roomList = new ArrayList<>();

    rooms = FXCollections.observableArrayList(roomList);

    roomList.add("BTM Conference Center");
    roomList.add("Restroom BTM Conference Center");
    roomList.add("Duncan Reid Conference Room");
    roomList.add("Medical Records Conference Room");
    roomList.add("Carrie M. Hall Conference Center");

    roomDropDown.setItems(rooms);

    // loop through all the rows and columns in the grid pane
    for (int row = 0; row < timeGrid.getRowCount(); row++) {
      for (int col = 0; col < timeGrid.getColumnCount(); col++) {
        // create a stack pane for each grid cell
        StackPane cell = new StackPane();
        // create a rectangle shape to fill the stack pane with
        Rectangle rect = new Rectangle(50, 50, Color.TRANSPARENT);
        // add the rectangle shape to the stack pane
        cell.getChildren().add(rect);
        // add the stack pane to the grid pane
        timeGrid.add(cell, col, row);

        // add a click event handler to the stack pane
        cell.setOnMouseClicked(
            event -> {
              // toggle the color of the rectangle between blue and transparent
              if (rect.getFill() == Color.TRANSPARENT) {
                rect.setFill(Color.BLUE);
              } else {
                rect.setFill(Color.TRANSPARENT);
              }
            });
      }
    }

    clearButton.setOnMouseClicked(
        event -> {
          clear();
        });

    submitButton.setOnMouseClicked(
        (event -> {
          clear();
        }));
  }

  private void clear() {
    // loop through all the rows and columns in the grid pane
    for (int row = 0; row < timeGrid.getRowCount(); row++) {
      for (int col = 0; col < timeGrid.getColumnCount(); col++) {
        // get the stack pane at this row and column
        StackPane cell =
            (StackPane) timeGrid.getChildren().get(row * timeGrid.getColumnCount() + col);
        // get the rectangle shape from the stack pane
        Rectangle rect = (Rectangle) cell.getChildren().get(0);
        // check if the rectangle is blue
        if (rect.getFill() == Color.BLUE) {
          // remove the rectangle from the stack pane
          cell.getChildren().remove(rect);
        }
      }
    }
  }
}
