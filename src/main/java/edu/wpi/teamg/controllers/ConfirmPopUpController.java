package edu.wpi.teamg.controllers;

import static edu.wpi.teamg.App.nodeDAO;
import static edu.wpi.teamg.App.refresh;

import edu.wpi.teamg.ORMClasses.Node;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import javafx.fxml.FXML;
import org.controlsfx.control.PopOver;

public class ConfirmPopUpController {

  @FXML MFXTextField x1;
  @FXML MFXTextField y1;
  @FXML MFXTextField x2;
  @FXML MFXTextField y2;

  @FXML MFXButton confirm;
  @FXML MFXButton cancel;

  Node potentialUpdate;

  PopOver window;

  public void initialize() {
    confirm.setOnMouseClicked(
        event -> {
          try {
            confirmUpdate();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    cancel.setOnMouseClicked(event -> cancelUpdate());

    x1.setEditable(false);
    y1.setEditable(false);
    x2.setEditable(false);
    y2.setEditable(false);
  }

  public void setFields(int X1, int Y1, int X2, int Y2, Node updateTheNode, PopOver window) {
    this.potentialUpdate = updateTheNode;
    this.window = window;

    x1.setText(String.valueOf(X1));
    y1.setText(String.valueOf(Y1));
    x2.setText(String.valueOf(X2));
    y2.setText(String.valueOf(Y2));
  }

  public void confirmUpdate() throws SQLException {
    nodeDAO.update(potentialUpdate, "xcoord", Integer.parseInt(x2.getText()));
    nodeDAO.update(potentialUpdate, "ycoord", Integer.parseInt(y2.getText()));
    refresh();
    Navigation.navigate(Screen.ADMIN_MAP_EDITOR);
    window.hide();
  }

  public void cancelUpdate() {
    Navigation.navigate(Screen.ADMIN_MAP_EDITOR);
    window.hide();
  }
}
