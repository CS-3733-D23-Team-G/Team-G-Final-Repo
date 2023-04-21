package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.fxml.FXML;

public class LocNamePopUpController {
  @FXML MFXTextField nodID;
  @FXML MFXTextField shortName;
  @FXML MFXTextField longName;
  @FXML MFXTextField nType;

  @FXML MFXButton sub;

  public void initialize() {
    nodID.setEditable(false);
    shortName.setEditable(true);
    longName.setEditable(true);
    nType.setEditable(true);

    sub.setOnMouseClicked(
        event -> {
          try {
            editPopUp();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void setF(Node node) throws SQLException {
    NodeDAO nodeDAO = new NodeDAO();
    DAORepo daoRepo = new DAORepo();
    LocationNameDAO loc = new LocationNameDAO();

    HashMap<String, LocationName> locationName = loc.getAll();

    HashMap<Integer, String> ln = daoRepo.getAllLongName();
    HashMap<Integer, String> sn = nodeDAO.getShortName(node.getFloor());

    nodID.setText(String.valueOf(node.getNodeID()));
    longName.setText(ln.get(node.getNodeID()));
    shortName.setText(sn.get(node.getNodeID()));
    nType.setText(locationName.get(ln.get(node.getNodeID())).getNodeType());
  }

  public void editPopUp() throws SQLException {

    NodeDAO nodeDAO = App.nodeDAO;

    LocationNameDAO locationNameDAO = new LocationNameDAO();

    HashMap<Integer, Node> nodes = nodeDAO.getAll();

    Node ourNode = nodes.get(Integer.parseInt(nodID.getText()));
    HashMap<Integer, String> ln = nodeDAO.getAllLongName();
    HashMap<String, LocationName> locs = locationNameDAO.getAll();

    String oldLong = ln.get(ourNode.getNodeID());

    String oldShort = locs.get(oldLong).getShortName();
    String oldType = locs.get(oldLong).getNodeType();
    //        String newLong = longName.getText();
    //        String newShort = shortName.getText();
    //        String newNodeType = nodID.getText();

    LocationName oldLoc = new LocationName(oldLong, oldShort, oldType);
    //        LocationName updatedLoc = new LocationName(newLong,newShort,newNodeType);

    locationNameDAO.update(oldLoc, "shortname", shortName.getText());
    locationNameDAO.update(oldLoc, "nodetype", nType.getText());
    locationNameDAO.update(oldLoc, "longname", longName.getText());

    App.refresh();
  }
}
