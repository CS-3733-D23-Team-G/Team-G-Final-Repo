package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import javafx.fxml.FXML;

public class InsertNodeController {

  @FXML MFXTextField nID;
  @FXML MFXTextField nXcoord;
  @FXML MFXTextField nYcoord;
  @FXML MFXTextField nFloor;
  @FXML MFXTextField nBuilding;

  @FXML MFXButton addNode;

  public void initialize() {
    nID.setEditable(true);
    nXcoord.setEditable(true);
    nYcoord.setEditable(true);
    nFloor.setEditable(true);
    nBuilding.setEditable(true);
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
    Node node =
        new Node(
            Integer.parseInt(nID.getText()),
            Integer.parseInt(nXcoord.getText()),
            Integer.parseInt(nYcoord.getText()),
            nFloor.getText(),
            nBuilding.getText());

    nodeDAO.insert(node);
  }
}
