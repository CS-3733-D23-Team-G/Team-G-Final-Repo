package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.ORMClasses.Move;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.util.ArrayList;
import java.util.Objects;
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

  String orientation = "S";

  public void initialize() {
    closePath.setOnMouseClicked(event -> close());
    pathInstructions.setEditable(false);
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
        int up = 0;
        int down = 0;
        int left = 0;
        int right = 0;

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
                pathInstructions.getText() + "End At: " + updatedMoves.get(j).getLongName());
            last = false;
          }

        } else {
          if (i != 0 && updatedMoves.get(j).getNodeID() == Integer.parseInt(path.get(i))) {

//            if (!Objects.equals(
//                    App.allNodes.get(Integer.parseInt(path.get(i))).getFloor(),
//                    App.allNodes.get(Integer.parseInt(path.get(i - 1))).getFloor())) {
//              System.out.println(
//                      "\nFloor " + App.allNodes.get(Integer.parseInt(path.get(i))).getFloor() + "\n\n");
//            }

            if (App.allNodes.get(Integer.parseInt(path.get(i))).getXcoord()
                > App.allNodes.get(Integer.parseInt(path.get(i - 1))).getXcoord()) {
              right =
                  App.allNodes.get(Integer.parseInt(path.get(i))).getXcoord()
                      - App.allNodes.get(Integer.parseInt(path.get(i - 1))).getXcoord();
            }
            if (App.allNodes.get(Integer.parseInt(path.get(i))).getXcoord()
                < App.allNodes.get(Integer.parseInt(path.get(i - 1))).getXcoord()) {
              left =
                  App.allNodes.get(Integer.parseInt(path.get(i - 1))).getXcoord()
                      - App.allNodes.get(Integer.parseInt(path.get(i))).getXcoord();
            }
            if (App.allNodes.get(Integer.parseInt(path.get(i))).getYcoord()
                < App.allNodes.get(Integer.parseInt(path.get(i - 1))).getYcoord()) {
              up =
                  App.allNodes.get(Integer.parseInt(path.get(i - 1))).getYcoord()
                      - App.allNodes.get(Integer.parseInt(path.get(i))).getYcoord();
            }
            if (App.allNodes.get(Integer.parseInt(path.get(i))).getYcoord()
                > App.allNodes.get(Integer.parseInt(path.get(i - 1))).getYcoord()) {
              down =
                  App.allNodes.get(Integer.parseInt(path.get(i))).getYcoord()
                      - App.allNodes.get(Integer.parseInt(path.get(i - 1))).getYcoord();
            }

            if (up == down && up == right && up == left) {
              pathInstructions.setText(
                  pathInstructions.getText()
                      + "Go Up To: "
                      + updatedMoves.get(j).getLongName()
                      + "\n");

            } else {
              changeOrientation(up, down, left, right, j, i);
            }
          }
        }
      }
    }
  }

  public void changeOrientation(int up, int down, int left, int right, int j, int i) {

    System.out.println(up);
    System.out.println(down);
    System.out.println(left);
    System.out.println(right);

    switch (orientation) {
      case "R":
        if (right > left && right > down && right > up) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Go Straight To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "R";
        } else if (left > right && left > down && left > up) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Turn Around and Go To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "L";
        } else if (up > left && up > down && up > right) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Turn Left and Go To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "S";
        } else if (down > left && down > up && down > right) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Turn Right and Go To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");

          orientation = "D";
        }

        break;

      case "L":
        if (right > left && right > down && right > up) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Turn Around and Go To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");

          orientation = "R";
        } else if (left > right && left > down && left > up) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Go Straight To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");

          orientation = "L";
        } else if (up > left && up > down && up > right) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Turn Right and Go To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "S";
        } else if (down > left && down > up && down > right) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Turn Left and Go To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "D";
        }

        break;

      case "S":
        if (right > left && right > down && right > up) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Turn Right and Go To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "R";
        } else if (left > right && left > down && left > up) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Turn Left and Go To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "L";
        } else if (up > left && up > down && up > right) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Go Straight to: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "S";
        } else if (down > left && down > up && down > right) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Turn around and go to: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "D";
        }

        break;

      case "D":
        if (right > left && right > down && right > up) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Turn Left and Go To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "R";
        } else if (left > right && left > down && left > up) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Turn Right and Go To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "L";
        } else if (up > left && up > down && up > right) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Turn Around and Go Straight To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "S";
        } else if (down > left && down > up && down > right) {
          pathInstructions.setText(
              pathInstructions.getText()
                  + "Go Straight To: "
                  + updatedMoves.get(j).getLongName()
                  + "\n");
          orientation = "D";
        }

        break;
    }

    System.out.println(orientation);
  }

  public void close() {
    wind.hide();
  }
}
