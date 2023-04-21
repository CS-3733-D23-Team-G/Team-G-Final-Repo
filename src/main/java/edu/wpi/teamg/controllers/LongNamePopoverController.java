package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.DAOs.RequestDAO;
import edu.wpi.teamg.ORMClasses.Request;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.controlsfx.control.SearchableComboBox;

public class LongNamePopoverController {

  @FXML SearchableComboBox tableLong;
  @FXML MFXButton tableSelect;

  ObservableList<String> longNameList;
  String newLn;

  Request request;

  public void initialize() throws SQLException {
    tableSelect.setOnMouseClicked(
        event -> {
          try {
            updateln();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void passObj(Request req) throws SQLException {
    request = req;
    longNodes(request.getReqtype());
  }

  public void longNodes(String reqType) throws SQLException {
    LocationNameDAO locationNameDAO = new LocationNameDAO();

    ArrayList<String> testingLongName = locationNameDAO.getLNgivenRequestType(reqType);

    //    ArrayList<LocationName> allLoc = new ArrayList<>(testingLongName.values());
    //    ArrayList<String> allLocNames = new ArrayList<>();
    //
    //    for (int i = 0; i < allLoc.size(); i++) {
    //      allLocNames.add(allLoc.get(i).getLongName());
    //    }

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

    Collections.sort(testingLongName, String.CASE_INSENSITIVE_ORDER);

    longNameList = FXCollections.observableArrayList(testingLongName);
    tableLong.setItems(longNameList);
  }

  public String updateln() throws SQLException {
    newLn = (String) tableLong.getValue();

    RequestDAO requestDAO = new RequestDAO();
    NodeDAO nodeDAO = new NodeDAO();
    requestDAO.update(
        request, "location", nodeDAO.getNodeIDbyLongName(newLn, new java.sql.Date(2023, 01, 01)));

    return newLn;
  }
}
