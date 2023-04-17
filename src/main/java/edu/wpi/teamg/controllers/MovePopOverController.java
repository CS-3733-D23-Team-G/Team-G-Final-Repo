package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class MovePopOverController {

  @FXML MFXTextField nodeID;
  @FXML MFXTextField moveInfo;

  public void initialize() {
    nodeID.setEditable(false);
    moveInfo.setEditable(false);
  }

  public void displayMove(int nodeID) {}
}
