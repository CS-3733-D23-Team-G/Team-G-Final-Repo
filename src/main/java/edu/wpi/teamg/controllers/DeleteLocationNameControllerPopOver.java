package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.MoveDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import edu.wpi.teamg.ORMClasses.Move;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.controlsfx.control.PopOver;

public class DeleteLocationNameControllerPopOver {

  @FXML MFXFilterComboBox deleteLongName;
  @FXML MFXButton locDelete;

  ObservableList<String> longNameList;

  PopOver wind = new PopOver();

  public void initialize() throws SQLException {

    longNodes();
    locDelete.setOnMouseClicked(
        event -> {
          try {
            deleteLn();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void setWind(PopOver window) {
    this.wind = window;
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
    deleteLongName.setItems(longNameList);
  }

  public void deleteLn() throws SQLException {
    NodeDAO nodeDAO = new NodeDAO();
    MoveDAO moveDAO = new MoveDAO();
    LocationNameDAO locationNameDAO = new LocationNameDAO();

    ArrayList<Move> moves = new ArrayList<>(moveDAO.getAll());
    HashMap<Integer, Node> nodes = nodeDAO.getAll();
    HashMap<String, LocationName> locs = new HashMap<>(locationNameDAO.getAll());

    LocationName del = locs.get((String) deleteLongName.getValue());

    for (int i = 0; i < moves.size(); i++) {
      if (Objects.equals(del.getLongName(), moves.get(i).getLongName())) {
        nodeDAO.delete(nodes.get(moves.get(i).getNodeID()));
      }
    }

    locationNameDAO.delete(del);
    MapEditorController.playAnimation = true;
    wind.hide();
    App.refresh();
  }
}
