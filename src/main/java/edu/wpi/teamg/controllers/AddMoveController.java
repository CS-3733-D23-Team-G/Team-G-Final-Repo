package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.MoveDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import edu.wpi.teamg.ORMClasses.Move;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.SearchableComboBox;

public class AddMoveController {

  @FXML SearchableComboBox moveNodeID;

  @FXML SearchableComboBox moveLongName;

  @FXML DatePicker moveDate;

  @FXML MFXButton moveSub;

  @FXML TextField foundLoc;

  ObservableList<String> longNameList;

  HashMap<Integer, Move> movin;

  public void initialize() throws SQLException {
    moveNodeID.setEditable(true);
    moveLongName.setEditable(true);
    foundLoc.setEditable(false);

    longNodes();
    nodeIDMovement();

    moveSub.setOnMouseClicked(
        event -> {
          try {
            insertMove();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    moveNodeID
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (moveNodeID.getValue() != null) {
                setID();
              }
            });
  }

  public void longNodes() throws SQLException {
    LocationNameDAO locationNameDAO = new LocationNameDAO();
    HashMap<String, LocationName> testingLongName = locationNameDAO.getAll();
    ArrayList<LocationName> allLoc = new ArrayList<>(testingLongName.values());
    ArrayList<String> allLocNames = new ArrayList<>();

    for (int i = 0; i < allLoc.size(); i++) {
      allLocNames.add(allLoc.get(i).getLongName());
    }

    int endFloorIndex = 0;
    //
    //    testingLongName.forEach(
    //            (i, m) -> {
    //              locationNames.add(m);
    //              // System.out.println("LocationName: " + m);
    //              //          System.out.println("Employee ID:" + m.getEmpid());
    //              //          System.out.println("Status:" + m.getStatus());
    //              //          System.out.println("Location:" + m.getLocation());
    //              //          System.out.println("Serve By:" + m.getServ_by());
    //              //          System.out.println();
    //            });

    Collections.sort(allLocNames, String.CASE_INSENSITIVE_ORDER);

    longNameList = FXCollections.observableArrayList(allLocNames);
    moveLongName.setItems(longNameList);
  }

  public void nodeIDMovement() throws SQLException {
    LocationNameDAO locationNameDAO = new LocationNameDAO();
    HashMap<String, LocationName> testingLongName = locationNameDAO.getAll();
    ArrayList<LocationName> allLoc = new ArrayList<>(testingLongName.values());
    ArrayList<String> allLocNames = new ArrayList<>();

    for (int i = 0; i < App.allNodeList.size(); i++) {
      allLocNames.add(String.valueOf(App.allNodeList.get(i).getNodeID()));
    }

    int endFloorIndex = 0;
    //
    //    testingLongName.forEach(
    //            (i, m) -> {
    //              locationNames.add(m);
    //              // System.out.println("LocationName: " + m);
    //              //          System.out.println("Employee ID:" + m.getEmpid());
    //              //          System.out.println("Status:" + m.getStatus());
    //              //          System.out.println("Location:" + m.getLocation());
    //              //          System.out.println("Serve By:" + m.getServ_by());
    //              //          System.out.println();
    //            });

    Collections.sort(allLocNames, String.CASE_INSENSITIVE_ORDER);

    longNameList = FXCollections.observableArrayList(allLocNames);
    moveNodeID.setItems(longNameList);
  }

  public void moveSetter(HashMap<Integer, Move> moves) {
    this.movin = moves;
  }

  public void setID() {
    if (!Objects.equals(moveNodeID.getValue().toString(), "")) {
      foundLoc.setText(movin.get(Integer.parseInt(moveNodeID.getValue().toString())).longName);
    }
  }

  public void insertMove() throws SQLException {
    int nodeID = Integer.parseInt(moveNodeID.getValue().toString());
    String ln = (String) moveLongName.getValue();
    Date date = Date.valueOf(moveDate.getValue());

    Move newMove = new Move(nodeID, ln, date);

    MoveDAO moveDAO = new MoveDAO();

    moveDAO.insert(newMove);
    App.refresh();
  }
}
