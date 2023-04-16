package edu.wpi.teamg.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class SignageEditorController {

  @FXML ImageView arrow1;

  public static int[] arrowDirectionNumber = new int[10];

  @FXML
  public void initialize() {

    arrowSetUp(arrowDirectionNumber);

    westArrow.setOnMouseClicked(event -> arrowSwap());
    northArrow.setOnMouseClicked(event -> arrowSwap());
    eastArrow.setOnMouseClicked(event -> arrowSwap());
    southArrow.setOnMouseClicked(event -> arrowSwap());
    noArrow.setOnMouseClicked(event -> arrowSwap());
  }

  public void arrowSwap() {
    if (westArrow.isVisible()) {
      westArrow.setVisible(false);
      northArrow.setVisible(true);

      arrowDirectionNumber[0] = 2;

    } else if (northArrow.isVisible()) {
      northArrow.setVisible(false);
      eastArrow.setVisible(true);

      arrowDirectionNumber[0] = 3;

    } else if (eastArrow.isVisible()) {
      eastArrow.setVisible(false);
      southArrow.setVisible(true);

      arrowDirectionNumber[0] = 4;

    } else if (southArrow.isVisible()) {
      southArrow.setVisible(false);
      noArrow.setVisible(true);

      arrowDirectionNumber[0] = 0;

    } else if (noArrow.isVisible()) {
      noArrow.setVisible(false);
      westArrow.setVisible(true);

      arrowDirectionNumber[0] = 1;
    }
  }

  public void arrowSetUp(int[] readList) {

    noArrow.setVisible(false);
    northArrow.setVisible(false);
    southArrow.setVisible(false);
    westArrow.setVisible(false);
    eastArrow.setVisible(false);

    // for (int i = 0; i <= 9; i++) {

    int v = readList[0];
    switch (v) {
      case 0:
        noArrow.setVisible(true);
        break;
      case 1:
        westArrow.setVisible(true);
        break;
      case 2:
        northArrow.setVisible(true);
        break;
      case 3:
        eastArrow.setVisible(true);
        break;
      case 4:
        southArrow.setVisible(true);
        break;
    }
  }
  // }

  public int[] getArrowDirection() {
    return arrowDirectionNumber;
  }
}
