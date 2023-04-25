package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import edu.wpi.teamg.ORMClasses.Move;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import org.controlsfx.control.PopOver;

public class editPopUpController {

  @FXML MFXTextField nID;
  @FXML MFXTextField nXcoord;
  @FXML MFXTextField nYcoord;
  @FXML MFXTextField nFloor;
  @FXML MFXTextField nBuilding;
  @FXML MFXButton submit;

  @FXML MFXTextField shortName;

  @FXML MFXButton delete;

  @FXML MFXButton fmoves;

  public void initialize() {

    nID.setEditable(false);
    nXcoord.setEditable(true);
    nYcoord.setEditable(true);
    nFloor.setEditable(true);
    nBuilding.setEditable(true);
    shortName.setEditable(true);
    submit.setOnMouseClicked(
        event -> {
          try {
            editPopUp();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    delete.setOnMouseClicked(
        event -> {
          try {
            deleteNode();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    fmoves.setOnMouseClicked(
        event -> {
          try {
            disMove((Integer.parseInt(nID.getText())));
          } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void setFields(Node node, LocationName loc) {
    nID.setText(String.valueOf(node.getNodeID()));
    nXcoord.setText(String.valueOf(node.getXcoord()));
    nYcoord.setText(String.valueOf(node.getYcoord()));
    nFloor.setText(node.getFloor());
    nBuilding.setText(node.getBuilding());
    shortName.setText(loc.getShortName());
  }

  public void editPopUp() throws SQLException {
    NodeDAO nodeDAO = new NodeDAO();
    LocationNameDAO locationNameDAO = new LocationNameDAO();
    HashMap<String, LocationName> LocationNames = locationNameDAO.getAll();
    HashMap<Integer, String> sn = nodeDAO.getShortName(nFloor.getText());
    HashMap<Integer, String> ln = nodeDAO.getLongNames(nFloor.getText());
    ArrayList<LocationName> locs = new ArrayList<>(LocationNames.values());
    LocationName knownLoc = new LocationName();

    for (int i = 0; i < locs.size(); i++) {
      if (Objects.equals(sn.get(Integer.parseInt(nID.getText())), locs.get(i).getShortName())) {
        knownLoc = locs.get(i);
      }
    }

    System.out.println(knownLoc.getLongName());
    Node node =
        new Node(
            Integer.parseInt(nID.getText()),
            Integer.parseInt(nXcoord.getText()),
            Integer.parseInt(nYcoord.getText()),
            nFloor.getText(),
            nBuilding.getText());
    nodeDAO.update(node, "xcoord", Integer.parseInt(nXcoord.getText()));
    nodeDAO.update(node, "ycoord", Integer.parseInt(nYcoord.getText()));
    nodeDAO.update(node, "floor", nFloor.getText());
    nodeDAO.update(node, "building", nBuilding.getText());
    locationNameDAO.update(knownLoc, "shortname", shortName.getText());
    App.refresh();
  }

  public void deleteNode() throws SQLException {
    NodeDAO nodeDAO = new NodeDAO();
    Node node =
        new Node(
            Integer.parseInt(nID.getText()),
            Integer.parseInt(nXcoord.getText()),
            Integer.parseInt(nYcoord.getText()),
            nFloor.getText(),
            nBuilding.getText());
    nodeDAO.delete(node);

    App.refresh();
  }

  public void disMove(int nodeID) throws SQLException, IOException {
    ArrayList<Move> nodeMoves = new ArrayList<>();
    DAORepo daoRepo = new DAORepo();
    List move = daoRepo.getAllMoves();
    for (int i = 0; i < move.size(); i++) {
      Move moveNode = (Move) move.get(i);
      if (moveNode.getNodeID() == nodeID) {
        nodeMoves.add(moveNode);
      }
    }

    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/MovePopOver.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    MovePopOverController controller = loader.getController();
    controller.setFields(nodeID);
    controller.displayMove(nodeID, nodeMoves);

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }
}
