package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.sql.SQLException;
import java.util.HashMap;
import javafx.fxml.FXML;

public class LocNamePopUpController {
  @FXML TextArea nodID;
  @FXML TextArea shortName;
  @FXML TextArea longName;
  @FXML TextArea nType;

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

  public void setF(Node node) {
    nodID.setText(String.valueOf(node.getNodeID()));
    nodID.setText(node.getLongName());
    nodID.setText(node.getShortName());
    nodID.setText(node.getNodeType());
  }

  public void editPopUp() throws SQLException {

    NodeDAO nodeDAO = new NodeDAO();

    HashMap<Integer, Node> nodes = nodeDAO.getAll();
    Node ourNode = nodes.get(Integer.parseInt(nodID.getText()));

    String oldLong = ourNode.getLongName();
    String oldShort = ourNode.getShortName();
    String oldNodeType = ourNode.getNodeType();

    //        String newLong = longName.getText();
    //        String newShort = shortName.getText();
    //        String newNodeType = nodID.getText();

    LocationNameDAO locationNameDAO = new LocationNameDAO();

    LocationName oldLoc = new LocationName(oldLong, oldShort, oldNodeType);
    //        LocationName updatedLoc = new LocationName(newLong,newShort,newNodeType);

    locationNameDAO.update(oldLoc, "shortname", shortName.getText());
    locationNameDAO.update(oldLoc, "node", nodID.getText());
    locationNameDAO.update(oldLoc, "longname", longName.getText());
  }
}
