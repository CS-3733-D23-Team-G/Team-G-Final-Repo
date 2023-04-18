package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.controlsfx.control.SearchableComboBox;

public class DeleteLocationNameControllerPopOver {

  @FXML SearchableComboBox deleteLongName;
  @FXML MFXButton locDelete;

  ObservableList<String> longNameList;

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
    LocationNameDAO locationNameDAO = new LocationNameDAO();
    HashMap<String, LocationName> locs = new HashMap<>(locationNameDAO.getAll());
    LocationName del = locs.get((String) deleteLongName.getValue());

    locationNameDAO.delete(del);
  }
}
