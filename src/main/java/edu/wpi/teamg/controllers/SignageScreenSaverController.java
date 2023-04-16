package edu.wpi.teamg.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class SignageScreenSaverController {

  @FXML ImageView westArrow;
  @FXML ImageView northArrow;
  @FXML ImageView southArrow;
  @FXML ImageView eastArrow;

  public void initialize() {
    getArrowDirectionFromEditor();
  }

  public void getArrowDirectionFromEditor() {
    int[] arrowDirection;
    SignageEditorController controller = new SignageEditorController();
    arrowDirection = controller.getArrowDirection();

    if (arrowDirection[0] == 1) {
      westArrow.setVisible(true);
    }

    if (arrowDirection[0] == 3) {
      eastArrow.setVisible(true);
    }

    if (arrowDirection[0] == 2) {
      northArrow.setVisible(true);
    }

    if (arrowDirection[0] == 4) {
      southArrow.setVisible(true);
    }

    if (arrowDirection[0] == 0) {
      westArrow.setVisible(false);
      southArrow.setVisible(false);
      northArrow.setVisible(false);
      eastArrow.setVisible(false);
    }
  }
}
