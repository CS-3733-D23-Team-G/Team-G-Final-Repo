package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
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

  public void initialize() {

    HashMap<Integer, String> locationHash = App.ln;

    ArrayList<String> locationArrayList = new ArrayList<String>(locationHash.values());

    ObservableList<String> locationList = FXCollections.observableArrayList(locationArrayList);

    locationDrop.setItems(locationList);

    aStarCheckBox.setOnAction(
        event -> {
          aStarCheckBox.setSelected(true);
          if (aStarCheckBox.isSelected()) {
            bfsCheckBox.setSelected(false);
            dfsCheckBox.setSelected(false);
            Dijkstracheckbox.setSelected(false);
          }
        });
    Dijkstracheckbox.setOnAction(
        event -> {
          Dijkstracheckbox.setSelected(true);
          if (Dijkstracheckbox.isSelected()) {
            bfsCheckBox.setSelected(false);
            dfsCheckBox.setSelected(false);
            aStarCheckBox.setSelected(false);
          }
        });

    bfsCheckBox.setOnAction(
        event -> {
          bfsCheckBox.setSelected(true);
          if (bfsCheckBox.isSelected()) {
            aStarCheckBox.setSelected(false);
            dfsCheckBox.setSelected(false);
            Dijkstracheckbox.setSelected(false);
          }
        });

    dfsCheckBox.setOnAction(
        event -> {
          dfsCheckBox.setSelected(true);
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
  }
}
