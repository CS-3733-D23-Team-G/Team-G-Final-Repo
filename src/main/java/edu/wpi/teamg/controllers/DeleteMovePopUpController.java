package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.MoveDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import edu.wpi.teamg.ORMClasses.Move;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javax.swing.*;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.SearchableComboBox;

public class DeleteMovePopUpController {

  @FXML SearchableComboBox moveDelNodeID;

  @FXML SearchableComboBox moveDel;

  @FXML MFXButton moveDelSub;

  PopOver win;

  ObservableList<String> locNames;
  ObservableList<String> date;

  public void initialize() {

    setMoveDelNodeID();
    moveDelSub.setOnMouseClicked(
        event -> {
          try {
            delMove();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    moveDelNodeID
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (moveDelNodeID.getValue() != null) {
                setMoveDel();
              }
            });
    // moveDelNodeID.setOnAction(event -> setMoveDel());

    /// moveDel.setOnMouseClicked(event -> setMoveDel());
  }

  public void setMoveDelNodeID() {
    ArrayList<LocationName> locations = new ArrayList<>(App.locMap.values());
    ArrayList<String> finalLocations = new ArrayList<>();

    for (int i = 0; i < locations.size(); i++) {
      finalLocations.add(locations.get(i).getLongName());
    }

    Collections.sort(finalLocations, String.CASE_INSENSITIVE_ORDER);
    locNames = FXCollections.observableArrayList(finalLocations);

    moveDelNodeID.setItems(locNames);
  }

  public void setMoveDel() {

    if (moveDelNodeID.getValue() != null) {
      ArrayList<String> finalLocations = new ArrayList<>();
      for (int i = 0; i < App.move.size(); i++) {
        if (Objects.equals(moveDelNodeID.getValue().toString(), App.move.get(i).getLongName())) {
          finalLocations.add(App.move.get(i).getDate().toString());
        }
      }

      Collections.sort(finalLocations, String.CASE_INSENSITIVE_ORDER);
      date = FXCollections.observableArrayList(finalLocations);

      moveDel.setItems(date);
    }
  }

  public void passOver(PopOver window) {
    this.win = window;
  }

  public void delMove() throws SQLException {

    MoveDAO moveDAO = new MoveDAO();
    Move moveToDelete = new Move();

    for (int i = 0; i < App.move.size(); i++) {

      if (Objects.equals(App.move.get(i).getLongName(), moveDelNodeID.getValue().toString())
          && App.move.get(i).getDate().compareTo(Date.valueOf(moveDel.getValue().toString()))
              == 0) {
        moveToDelete = App.move.get(i);
      }
    }

    moveDAO.delete(moveToDelete);

    win.hide();
    App.refresh();
  }
}
