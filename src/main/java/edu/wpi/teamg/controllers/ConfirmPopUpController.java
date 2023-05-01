package edu.wpi.teamg.controllers;

import static edu.wpi.teamg.App.*;

import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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

  ArrayList<ImageView> map;
  String floor;

  Pane pane;

  public void initialize() {
    confirm.setOnMouseClicked(
        event -> {
          try {
            confirmUpdate();

          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    cancel.setOnMouseClicked(
        event -> {
          try {
            cancelUpdate();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    x1.setEditable(false);
    y1.setEditable(false);
    x2.setEditable(false);
    y2.setEditable(false);
  }

  public void setFields(
      int X1,
      int Y1,
      int X2,
      int Y2,
      Node updateTheNode,
      PopOver window,
      ArrayList<ImageView> img,
      String i,
      Pane nodePane) {
    this.potentialUpdate = updateTheNode;
    this.window = window;
    this.map = img;
    this.floor = i;

    this.pane = nodePane;
    x1.setText(String.valueOf(X1));
    y1.setText(String.valueOf(Y1));
    x2.setText(String.valueOf(X2));
    y2.setText(String.valueOf(Y2));
  }

  public void confirmUpdate() throws SQLException {
    nodeDAO.update(potentialUpdate, "xcoord", Integer.parseInt(x2.getText()));
    nodeDAO.update(potentialUpdate, "ycoord", Integer.parseInt(y2.getText()));

    refresh();
    MapEditorController.playAnimation = true;
    window.hide();
  }

  public void cancelUpdate() throws SQLException {
    // Navigation.navigate(Screen.ADMIN_MAP_EDITOR);
    //    MapEditorController controller = new MapEditorController();
    //
    //    int flNum = controller.findIndex(floor);

    //    controller.newNodes(flNum);
    //
    //    //    for (int i = 0; i < allNodeList.size(); i++) {
    //    //      if (Objects.equals(allNodeList.get(i).getFloor(), floor)) {
    //    //        controller.getNodesWFunctionality(allNodeList, i, sn);
    //    //      }
    //    //    }
    //
    MapEditorController.playAnimation = false;
    window.hide();
    //    controller.goToFloor2(map);
  }
}
