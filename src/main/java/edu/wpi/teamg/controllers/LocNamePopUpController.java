package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.SearchableComboBox;

public class LocNamePopUpController {
  @FXML MFXTextField shortName;
  @FXML SearchableComboBox longName;
  @FXML MFXTextField nType;

  @FXML MFXButton sub;

  PopOver wind = new PopOver();
  ObservableList<String> locNames;

  LocationName loc = new LocationName();

  public void initialize() {
    shortName.setEditable(true);
    nType.setEditable(true);

    setF();
    sub.setOnMouseClicked(
        event -> {
          try {
            editPopUp();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    longName
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (longName.getValue() != null) {
                fillLocNames();
              }
            });
  }

  public void setF() {

    ArrayList<LocationName> loc = new ArrayList<>(App.locMap.values());
    ArrayList<String> finalLocations = new ArrayList<>();

    for (int i = 0; i < loc.size(); i++) {
      finalLocations.add(loc.get(i).getLongName());
    }

    Collections.sort(finalLocations, String.CASE_INSENSITIVE_ORDER);
    locNames = FXCollections.observableArrayList(finalLocations);

    longName.setItems(locNames);
  }

  public void setW(PopOver window) {
    this.wind = window;
  }

  public void fillLocNames() {
    loc = App.locMap.get(longName.getValue().toString());

    shortName.setText(loc.getShortName());
    nType.setText(loc.getNodeType());
  }

  public void editPopUp() throws SQLException {
    LocationNameDAO locationNameDAO = new LocationNameDAO();

    locationNameDAO.update(loc, "shortname", shortName.getText());
    locationNameDAO.update(loc, "nodetype", nType.getText());

    wind.hide();
    App.refresh();
  }
}
