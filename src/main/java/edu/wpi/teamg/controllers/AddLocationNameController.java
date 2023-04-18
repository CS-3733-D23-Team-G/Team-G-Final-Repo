package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import javafx.fxml.FXML;

public class AddLocationNameController {

  @FXML MFXTextField locNodeID;

  @FXML MFXTextField locShortName;

  @FXML MFXTextField locLongName;

  @FXML MFXTextField locNodeType;

  @FXML MFXButton locSub;

  public void initialize() {
    locNodeID.setEditable(true);
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

  public void addLocName() throws SQLException {

    LocationNameDAO locationNameDAO = new LocationNameDAO();
    int NodeID = Integer.parseInt(locNodeID.getText());
    String shortName = locShortName.getText();
    String longName = locLongName.getText();
    String nodeType = locNodeType.getText();

    LocationName locName = new LocationName(shortName, longName, nodeType);
    locationNameDAO.insert(locName);
  }
}
