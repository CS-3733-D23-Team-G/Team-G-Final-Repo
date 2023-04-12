package edu.wpi.teamg.controllers;

import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class editPopUpController {

  @FXML MFXTextField nID;
  @FXML MFXTextField nXcoord;
  @FXML MFXTextField nYcoord;
  @FXML MFXTextField nFloor;
  @FXML MFXTextField nBuilding;
  @FXML VBox popUp;

  public void initialize() {

    nID.setEditable(false);
    nXcoord.setEditable(true);
    nYcoord.setEditable(true);
    nFloor.setEditable(true);
    nBuilding.setEditable(true);
  }

  public void setFields(Node node) {
    nID.setText(String.valueOf(node.getNodeID()));
    nXcoord.setText(String.valueOf(node.getXcoord()));
    nYcoord.setText(String.valueOf(node.getYcoord()));
    nFloor.setText(node.getFloor());
    nBuilding.setText(node.getBuilding());
  }
}
