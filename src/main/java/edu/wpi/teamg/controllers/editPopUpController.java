package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.MoveDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import edu.wpi.teamg.ORMClasses.Move;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
//import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.List;
import java.util.ListIterator;
import javafx.fxml.FXML;

public class editPopUpController {

  @FXML MFXTextField nID;
  @FXML MFXTextField nXcoord;
  @FXML MFXTextField nYcoord;
  @FXML MFXTextField nFloor;
  @FXML MFXTextField nBuilding;
  @FXML MFXButton submit;


  @FXML MFXTextField shortName;
  @FXML MFXButton delete;


  @FXML MFXTextField longName; // step 1
  @FXML MFXTextField date; // step 1
  @FXML MFXTextField display; // step 1

  public void initialize() {

    nID.setEditable(false);
    nXcoord.setEditable(true);
    nYcoord.setEditable(true);
    nFloor.setEditable(true);
    nBuilding.setEditable(true);
    shortName.setEditable(true);
    longName.setEditable(true);
    date.setEditable(true);

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
    display.setOnMouseClicked(
         event -> {
              try {
                displayMove();
              } catch (SQLException e) {
                throw new RuntimeException(e);
              }
         }
    ); // step 2

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
  }

  public void displayMove() throws SQLException {
    int ID = Integer.parseInt(nID.getText());

    String name = String.valueOf(longName.getText());
    String text = date.getText();
    MoveDAO moveDAO = new MoveDAO();
    Date storedDate = moveDAO.stringToDate(text);
    Move move = new Move(ID, name, storedDate);

    List<edu.wpi.teamg.ORMClasses.Move> moves = moveDAO.getAll();
    int moveIndex = moves.indexOf(move);
    List<Integer> nIDs = new ArrayList<>();

    for(int i = 0; i < moves.size(); i++) {
      nIDs.add(ID);
    }

    ListIterator<Move> moveIterator = moves.listIterator();
    if(moveIterator.hasNext()) {
      while(moveIterator.hasNext()) {
        if(nIDs.indexOf(ID) == moveIndex) {
          moveDAO.insert(moves.get(moveIndex).getNodeID());
          moveDAO.insert(moves.get(moveIndex).getLongName());
          moveDAO.insert(moves.get(moveIndex).getDate());
          break;
        }
      }
    } else {
      if(nIDs.size() == 1 ||  moveIndex == nIDs.size() - 1) {
        moveDAO.insert(moves.get(moveIndex).getNodeID());
        moveDAO.insert(moves.get(moveIndex).getLongName());
        moveDAO.insert(moves.get(moveIndex).getDate());
      } else {
        throw new SQLException();
      }
    }



  }
}
