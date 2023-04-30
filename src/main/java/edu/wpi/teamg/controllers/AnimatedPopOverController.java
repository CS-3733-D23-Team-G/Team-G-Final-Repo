package edu.wpi.teamg.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AnimatedPopOverController {

  @FXML TextArea anitxt;

  @FXML MFXButton back;

  @FXML MFXButton next;

  ArrayList<String> path;

  String txtForPath;

  boolean start = true;

  boolean end = false;

  int index;

  public void initialize() {
    anitxt.setEditable(false);

    next.setOnMouseClicked(event -> goToNextEdge());


    back.setOnMouseClicked(event -> goBackEdge());


  }

  public void setTxtForPath(String txtPath) {
    anitxt.setText(txtPath);
  }

  public void getThePath(ArrayList<String> path) {
    this.path = path;
  }

  public void goToNextEdge() {

    index = index + 1;
  }

  public void goBackEdge() {

    index = index - 1;
  }
}
