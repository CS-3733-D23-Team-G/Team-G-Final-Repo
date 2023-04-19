package edu.wpi.teamg.controllers;

import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class LinkedNodesPopUpController {

  @FXML MFXTextField nodeID;

  @FXML TextArea linkedNodes;

  public void initialize() {
    nodeID.setEditable(false);
    linkedNodes.setEditable(false);
  }

  public void setField(Node A) {
    nodeID.setText(String.valueOf(A.getNodeID()));
  }

  //    public void displayLink(ArrayList<Edge> e, Node A) {
  //
  //        nodeID.setText(String.valueOf(A.getNodeID()));
  //
  //        for (int i = 0; i < e.size(); i++) {
  //            linkedNodes.setText(
  //                    moveInfo.getText()
  //                            + "Location Name: "
  //                            + moveArray.get(i).getLongName()
  //                            + "         Date: "
  //                            + moveArray.get(i).getDate().toString()
  //                            + "\n");
  //        }
  //    }
}
