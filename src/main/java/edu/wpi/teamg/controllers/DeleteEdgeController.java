package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.ORMClasses.Edge;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import org.controlsfx.control.PopOver;

public class DeleteEdgeController {

  @FXML MFXFilterComboBox edgeIDs;
  @FXML MFXTextField startN;
  @FXML MFXTextField endN;
  @FXML MFXButton subDel;
  ObservableList<String> edgeNames;

  Edge viewDel = new Edge();

  PopOver wind = new PopOver();

  public void initialize() {

    startN.setEditable(false);
    endN.setEditable(false);
    setEdgeIDs();

    edgeIDs
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (edgeIDs.getValue() != null) {
                fillEdges();
              }
            });

    subDel.setOnMouseClicked(
        event -> {
          try {
            deleteEdge();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void setWind(PopOver window) {
    this.wind = window;
  }

  public void setEdgeIDs() {
    ArrayList<Edge> edges = new ArrayList<>(App.listOfEdges);
    ArrayList<String> finalLocations = new ArrayList<>();

    for (int i = 0; i < edges.size(); i++) {
      finalLocations.add(edges.get(i).getEdgeID());
    }

    Collections.sort(finalLocations, String.CASE_INSENSITIVE_ORDER);
    edgeNames = FXCollections.observableArrayList(finalLocations);

    edgeIDs.setItems(edgeNames);
  }

  public void fillEdges() {

    viewDel = App.edgeMap.get(edgeIDs.getValue().toString());

    startN.setText(String.valueOf(viewDel.getStartNode()));
    endN.setText(String.valueOf(viewDel.getEndNode()));
  }

  public void deleteEdge() throws SQLException {
    App.edgeDao.delete(viewDel);
    App.refresh();
    MapEditorController.playAnimation = true;
    wind.hide();
  }
}
