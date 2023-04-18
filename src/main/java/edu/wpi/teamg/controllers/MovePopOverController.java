package edu.wpi.teamg.controllers;

import edu.wpi.teamg.ORMClasses.Move;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class MovePopOverController {

  @FXML MFXTextField nodeID;
  @FXML TextArea moveInfo;

  public void initialize() {
    // nodeID.setText("123");
    nodeID.setEditable(false);
    // moveInfo.setText("123");
    moveInfo.setEditable(false);
  }

  public void setFields(int NodeID) {
    nodeID.setText(String.valueOf(NodeID));
  }

  public void displayMove(int NodeID, ArrayList<Move> moveArray) {

    nodeID.setText(String.valueOf(NodeID));

    for (int i = 0; i < moveArray.size(); i++) {
      moveInfo.setText(
          moveInfo.getText()
              + "Location Name: "
              + moveArray.get(i).getLongName()
              + "         Date: "
              + moveArray.get(i).getDate().toString());
    }
  }
}
