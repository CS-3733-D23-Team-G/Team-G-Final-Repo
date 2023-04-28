package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.EdgeDAO;
import edu.wpi.teamg.ORMClasses.Edge;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import javafx.fxml.FXML;

public class AddEdgePopUpController {

  @FXML MFXTextField sn;
  @FXML MFXTextField en;

  @FXML MFXButton sub;

  public void initialize() throws SQLException {

    sub.setOnMouseClicked(
        event -> {
          try {
            addEdge();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void addEdge() throws SQLException {
    EdgeDAO dao = new EdgeDAO();
    Edge e = new Edge();
    e.setStartNode(Integer.parseInt(sn.getText()));
    e.setEndNode(Integer.parseInt(en.getText()));
    e.setEdgeID(sn.getText() + "_" + en.getText());
    System.out.println(e.getEdgeID());

    dao.insert(e);
  }
}
