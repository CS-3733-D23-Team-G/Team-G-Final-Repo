package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.ORMClasses.Edge;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class EdgeDataPopOverController {

  @FXML MFXTextField edgeID;

  @FXML MFXTextField startNode;

  @FXML MFXTextField startLn;

  @FXML MFXTextField endNode;

  @FXML MFXTextField endLn;

  Edge otherEdge;

  public void initialize() {
    edgeID.setEditable(false);
    startNode.setEditable(false);
    startLn.setEditable(false);
    endNode.setEditable(false);
    endLn.setEditable(false);
  }

  public void setEdgeFields(Edge edge, Node A, Node B) {

    edgeID.setText(edge.getEdgeID());
    startNode.setText(String.valueOf(A.getNodeID()));
    startLn.setText(App.ln.get(A.getNodeID()));
    endNode.setText(String.valueOf(B.getNodeID()));
    endLn.setText(App.ln.get(B.getNodeID()));
  }
}
