package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

public class SettingsPopOverController {

  @FXML MFXFilterComboBox locationDrop;
  @FXML MFXCheckbox aStarCheckBox;
  @FXML MFXCheckbox bfsCheckBox;
  @FXML MFXCheckbox dfsCheckBox;
  @FXML MFXCheckbox Dijkstracheckbox;
  @FXML MFXCheckbox awsCheckBox;
  @FXML MFXCheckbox clientSideCheckBox;

  @FXML MFXDatePicker date; // this is the same as in pathfinding, cus we're just moving it here

  public void initialize() {

    HashMap<Integer, String> locationHash = App.ln;

    switch (App.pathfindingAlgo) {
      case "Astar":
        aStarCheckBox.setSelected(true);
        break;
      case "BFS":
        bfsCheckBox.setSelected(true);
        break;
      case "DFS":
        dfsCheckBox.setSelected(true);
        break;
      case "Dijkstra":
        Dijkstracheckbox.setSelected(true);
        break;
    }

    ArrayList<String> locationArrayList = new ArrayList<String>(locationHash.values());

    ObservableList<String> locationList = FXCollections.observableArrayList(locationArrayList);

    locationDrop.setItems(locationList);

    aStarCheckBox.setOnAction(
        event -> {
          aStarCheckBox.setSelected(true);
          App.pathfindingAlgo = "Astar";
          System.out.println(App.pathfindingAlgo);
          if (aStarCheckBox.isSelected()) {
            bfsCheckBox.setSelected(false);
            dfsCheckBox.setSelected(false);
            Dijkstracheckbox.setSelected(false);
          }
        });
    Dijkstracheckbox.setOnAction(
        event -> {
          Dijkstracheckbox.setSelected(true);
          App.pathfindingAlgo = "Dijkstra";
          System.out.println(App.pathfindingAlgo);
          if (Dijkstracheckbox.isSelected()) {
            bfsCheckBox.setSelected(false);
            dfsCheckBox.setSelected(false);
            aStarCheckBox.setSelected(false);
          }
        });

    bfsCheckBox.setOnAction(
        event -> {
          bfsCheckBox.setSelected(true);
          App.pathfindingAlgo = "BFS";
          System.out.println(App.pathfindingAlgo);
          if (bfsCheckBox.isSelected()) {
            aStarCheckBox.setSelected(false);
            dfsCheckBox.setSelected(false);
            Dijkstracheckbox.setSelected(false);
          }
        });

    dfsCheckBox.setOnAction(
        event -> {
          dfsCheckBox.setSelected(true);
          App.pathfindingAlgo = "DFS";
          System.out.println(App.pathfindingAlgo);
          if (dfsCheckBox.isSelected()) {
            aStarCheckBox.setSelected(false);
            bfsCheckBox.setSelected(false);
            Dijkstracheckbox.setSelected(false);
          }
        });

    awsCheckBox.setOnAction(
        event -> {
          awsCheckBox.setSelected(true);
          if (awsCheckBox.isSelected()) {
            clientSideCheckBox.setSelected(false);
          }
        });

    clientSideCheckBox.setOnAction(
        event -> {
          clientSideCheckBox.setSelected(true);
          if (clientSideCheckBox.isSelected()) {
            awsCheckBox.setSelected(false);
          }
        });
    date.setOnCommit(
        event -> {
          App.pathfindingDate = date.getValue();

          System.out.println(App.bool);
          if (App.bool) {
            Navigation.navigate(Screen.PATHFINDING_PAGE);
            App.bool = false;
          }
          // if(Window.getWindows() == 2)// LLOlOL SORRY, NVM NOT SORRY fuck it just fix the date to

        });
  }
}
