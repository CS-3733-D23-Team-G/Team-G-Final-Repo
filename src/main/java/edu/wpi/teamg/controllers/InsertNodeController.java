package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.MoveDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import edu.wpi.teamg.ORMClasses.Move;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.Date;
import java.sql.SQLException;
import javafx.fxml.FXML;

public class InsertNodeController {

  @FXML MFXTextField nID;
  @FXML MFXTextField nXcoord;
  @FXML MFXTextField nYcoord;
  @FXML MFXTextField nFloor;
  @FXML MFXTextField nBuilding;
  @FXML MFXTextField ln;
  @FXML MFXButton addNode;

  public void initialize() {
    nID.setEditable(true);
    nXcoord.setEditable(true);
    nYcoord.setEditable(true);
    nFloor.setEditable(true);
    nBuilding.setEditable(true);
    ln.setEditable(true);
    addNode.setOnMouseClicked(
        event -> {
          try {
            insertNode();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void insertNode() throws SQLException {
    NodeDAO nodeDAO = new NodeDAO();
    LocationNameDAO locationNameDAO = new LocationNameDAO();
    MoveDAO moveDAO = new MoveDAO();
    Node node =
        new Node(
            Integer.parseInt(nID.getText()),
            Integer.parseInt(nXcoord.getText()),
            Integer.parseInt(nYcoord.getText()),
            nFloor.getText(),
            nBuilding.getText());
    LocationName loc = new LocationName(ln.getText(), ln.getText(), "HALL");
    Date date = new Date(2023 - 01 - 01);

    Move move = new Move(Integer.parseInt(nID.getText()), loc.getLongName(), date);

    nodeDAO.insert(node);
    locationNameDAO.insert(loc);
    moveDAO.insert(move);
    App.refresh();
  }
}
