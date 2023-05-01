package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import javafx.fxml.FXML;
import org.controlsfx.control.PopOver;

public class AddLocationNameController {

  @FXML MFXTextField locShortName;

  @FXML MFXTextField locLongName;

  @FXML MFXTextField locNodeType;

  @FXML MFXButton locSub;

  PopOver wind = new PopOver();

  public void initialize() {
    locShortName.setEditable(true);
    locLongName.setEditable(true);
    locNodeType.setEditable(true);

    locSub.setOnMouseClicked(
        event -> {
          try {
            addLocName();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void setWind(PopOver window) {
    this.wind = window;
  }

  public void addLocName() throws SQLException {

    LocationNameDAO locationNameDAO = new LocationNameDAO();
    String shortName = locShortName.getText();
    String longName = locLongName.getText();
    String nodeType = locNodeType.getText();

    LocationName locName = new LocationName(shortName, longName, nodeType);
    locationNameDAO.insert(locName);
    MapEditorController.playAnimation = true;
    wind.hide();
    App.refresh();
  }
}
