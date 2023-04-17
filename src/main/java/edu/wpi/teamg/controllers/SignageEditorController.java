package edu.wpi.teamg.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class SignageEditorController {

  @FXML ImageView westArrow;
  @FXML ImageView northArrow;
  @FXML ImageView southArrow;
  @FXML ImageView eastArrow;

  @FXML
  public void initialize() {
    westArrow.setVisible(true);
    westArrow.setDisable(true);

    northArrow.setVisible(false);
    northArrow.setDisable(false);

    southArrow.setVisible(false);
    southArrow.setDisable(true);

    eastArrow.setVisible(false);
    eastArrow.setDisable(true);

    eastArrow.onMouseClickedProperty();
    westArrow.onMouseClickedProperty();
    northArrow.onMouseClickedProperty();
    southArrow.onMouseClickedProperty();
  }

  public void changeWestArrow() {
    westArrow.setVisible(true);
    westArrow.setDisable(false);

    northArrow.setVisible(false);
    northArrow.setDisable(true);

    eastArrow.setVisible(false);

    southArrow.setVisible(false);
  }

  public void changeNorthArrow() {
    westArrow.setVisible(false);

    northArrow.setVisible(true);
    northArrow.setDisable(true);

    eastArrow.setVisible(false);
    eastArrow.setDisable(false);

    southArrow.setVisible(false);
  }

  public void changeEastArrow() {
    westArrow.setVisible(false);

    northArrow.setVisible(false);

    eastArrow.setVisible(true);
    eastArrow.setDisable(true);

    southArrow.setVisible(false);
    southArrow.setDisable(false);
  }

  public void changeSouthArrow() {
    westArrow.setVisible(false);
    westArrow.setDisable(false);

    northArrow.setVisible(false);

    eastArrow.setVisible(false);

    southArrow.setVisible(true);
    southArrow.setDisable(true);
  }
}

