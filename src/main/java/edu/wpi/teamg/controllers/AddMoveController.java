package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.MoveDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import edu.wpi.teamg.ORMClasses.Move;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import org.controlsfx.control.SearchableComboBox;

public class AddMoveController {

  @FXML MFXTextField moveNodeID;

  @FXML SearchableComboBox moveLongName;

  @FXML DatePicker moveDate;

  @FXML MFXButton moveSub;

  ObservableList<String> longNameList;

  public void initialize() throws SQLException {
    moveNodeID.setEditable(true);
    moveLongName.setEditable(true);

    longNodes();

    moveSub.setOnMouseClicked(
        event -> {
          try {
            insertMove();
          } catch (SQLException e) {
            throw new RuntimeException(e);
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

  public void insertMove() throws SQLException {
    int nodeID = Integer.parseInt(moveNodeID.getText());
    String ln = (String) moveLongName.getValue();
    Date date = Date.valueOf(moveDate.getValue());

    Move newMove = new Move(nodeID, ln, date);

    MoveDAO moveDAO = new MoveDAO();

    moveDAO.insert(newMove);
  }
}
