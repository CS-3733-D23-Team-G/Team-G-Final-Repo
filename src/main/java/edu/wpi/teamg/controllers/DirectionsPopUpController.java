package edu.wpi.teamg.controllers;

import edu.wpi.teamg.ORMClasses.Move;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

public class DirectionsPopUpController {

  @FXML Text lnStart;
  @FXML Text lnEnd;
  @FXML TextArea pathInstructions;
  @FXML MFXButton closePath;

  ArrayList<Move> updatedMoves;

  ArrayList<String> path;
  PopOver wind;

  boolean first = false;

  boolean last = false;

  public void initialize() {
    closePath.setOnMouseClicked(event -> close());
  }

  public void setF(PopOver window, ArrayList<String> getPath, ArrayList<Move> movin) {
    this.wind = window;
    this.path = getPath;
    this.updatedMoves = movin;
    printTxtPath();
  }

  public void printTxtPath() {
    for (int i = 0; i < path.size(); i++) {
      if (i == 0) {
        first = true;
      }
      if (i == path.size() - 1) {
        last = true;
      }
      for (int j = 0; j < updatedMoves.size(); j++) {

        if (first) {
          if (updatedMoves.get(j).getNodeID() == Integer.parseInt(path.get(i))) {
            lnStart.setText(updatedMoves.get(j).getLongName());
            pathInstructions.setText("Start At: " + updatedMoves.get(j).getLongName() + "\n");
            first = false;
          }

        } else if (last) {
          if (updatedMoves.get(j).getNodeID() == Integer.parseInt(path.get(i))) {
            lnEnd.setText(updatedMoves.get(j).getLongName());
            pathInstructions.setText(
                pathInstructions.getText() + " End At:" + updatedMoves.get(j).getLongName());
            last = false;
          }

        } else {
          if (updatedMoves.get(j).getNodeID() == Integer.parseInt(path.get(i))) {
            pathInstructions.setText(
                pathInstructions.getText() + "\t" + updatedMoves.get(j).getLongName() + "\n");
          }
        }
      }
    }
  }

  public void close() {
    wind.hide();
  }
}
