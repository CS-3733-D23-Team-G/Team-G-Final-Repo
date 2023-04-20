package edu.wpi.teamg.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PathfindingOverController {

  @FXML Text lnTxt;
  @FXML Text typeTxt;

  public void initialize() {}

  public void setFields(String ln, String nodeType) {
    lnTxt.setText(ln);
    typeTxt.setText(nodeType);
  }
}
