package edu.wpi.teamg.controllers;

import static edu.wpi.teamg.App.*;
import static edu.wpi.teamg.Main.*;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.LocationName;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.PopOver;

public class MapEditorController {

  @FXML Pane nodePane;
  public Group group;
  @FXML GesturePane pane;

  @FXML Button l1;
  @FXML Button l2;
  @FXML Button floor1;
  @FXML Button floor2;
  @FXML Button floor3;

  @FXML MFXButton add;

  @FXML MFXButton addLoc;
  @FXML MFXButton addMove;

  @FXML MFXButton deleteLoc;

  public void initialize() throws SQLException, IOException {
    pane.setVisible(true);
    nodePane.setVisible(true);
    group.setVisible(true);
    add.setOnMouseClicked(
        event -> {
          try {
            addNode();
          } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
          }
        });
    addLoc.setOnMouseClicked(
        event -> {
          try {
            addLocationName();
          } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
          }
        });
    addMove.setOnMouseClicked(
        event -> {
          try {
            addMoves();
          } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
          }
        });
    deleteLoc.setOnMouseClicked(
        event -> {
          try {
            deleteLocation();
          } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
          }
        });

    //    Image mapL1 =

    ImageView mapView = new ImageView(mapL1);
    ImageView mapViewL2 = new ImageView(mapL2);
    ImageView mapViewFloor1 = new ImageView(mapFloor1);
    ImageView mapViewFloor2 = new ImageView(mapFloor2);
    ImageView mapViewFloor3 = new ImageView(mapFloor3);

    group.getChildren().add(mapView);
    group.getChildren().add(mapViewL2);
    group.getChildren().add(mapViewFloor1);
    group.getChildren().add(mapViewFloor2);
    group.getChildren().add(mapViewFloor3);

    mapViewL2.setVisible(false);
    mapViewFloor1.setVisible(false);
    mapViewFloor2.setVisible(false);
    mapViewFloor3.setVisible(false);

    mapView.toBack();
    mapView.relocate(0, 0);

    mapViewL2.toBack();
    mapViewL2.relocate(0, 0);

    mapViewFloor1.toBack();
    mapViewFloor1.relocate(0, 0);

    mapViewFloor2.toBack();
    mapViewFloor2.relocate(0, 0);

    mapViewFloor3.toBack();
    mapViewFloor3.relocate(0, 0);

    nodePane.setLayoutX(0);
    nodePane.setLayoutY(0);
    nodePane.setMinWidth(mapL1.getWidth());
    nodePane.setMinHeight(mapL1.getHeight());
    nodePane.setMaxWidth(mapL1.getWidth());
    nodePane.setMaxHeight(mapL1.getHeight());

    // Scales Map
    pane.setMinScale(.001);
    pane.zoomTo(.000001, new Point2D(2500, 1700));
    pane.zoomTo(.000001, new Point2D(2500, 1700));

    ArrayList<ImageView> imageViewsList = new ArrayList<>();
    imageViewsList.add(mapView);
    imageViewsList.add(mapViewL2);
    imageViewsList.add(mapViewFloor1);
    imageViewsList.add(mapViewFloor2);
    imageViewsList.add(mapViewFloor3);

    l1.setOnMouseClicked(
        event -> {
          try {
            goToL1(imageViewsList);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    l2.setOnMouseClicked(
        event -> {
          try {
            goToL2(imageViewsList);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    floor1.setOnMouseClicked(
        event -> {
          try {
            goToFloor1(imageViewsList);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    floor2.setOnMouseClicked(
        event -> {
          try {
            goToFloor2(imageViewsList);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    floor3.setOnMouseClicked(
        event -> {
          try {
            goToFloor3(imageViewsList);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    // Scaling is currently the issue with the node map

    NodeDAO nodeDAO = new NodeDAO();

    HashMap<Integer, edu.wpi.teamg.ORMClasses.Node> nodes = nodeDAO.getAll();
    ArrayList<edu.wpi.teamg.ORMClasses.Node> listOfNodes = new ArrayList<>(nodes.values());
    HashMap<Integer, String> ln = nodeDAO.getLongNames("L1");
    HashMap<Integer, String> sn = nodeDAO.getShortName("L1");

    for (int i = 0; i < listOfNodes.size(); i++) {
      if (Objects.equals(listOfNodes.get(i).getFloor(), "L1")) {
        getNodesWFunctionality(listOfNodes, i, sn, ln);
      }
    }
    //    test.setOnMouseClicked(
    //        event -> {
    //          try {
    //            disMove(listOfNodes.get(0).getNodeID());
    //          } catch (SQLException e) {
    //            throw new RuntimeException(e);
    //          } catch (IOException e) {
    //            throw new RuntimeException(e);
    //          }
    //        });
  }

  public void goToL1(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(0).setVisible(true);

    newNodes(0);
  }

  public void goToL2(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(1).setVisible(true);
    newNodes(1);
  }

  public void goToFloor1(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(2).setVisible(true);

    newNodes(2);
  }

  public void goToFloor2(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(3).setVisible(true);

    newNodes(3);
  }

  public void goToFloor3(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(4).setVisible(true);

    newNodes(4);
  }

  public void newNodes(int index) throws SQLException {
    NodeDAO nodeDAO = new NodeDAO();

    HashMap<Integer, edu.wpi.teamg.ORMClasses.Node> nodes = nodeDAO.getAll();
    ArrayList<edu.wpi.teamg.ORMClasses.Node> listOfNodes = new ArrayList<>(nodes.values());
    HashMap<Integer, String> sn = nodeDAO.getShortName("L1");
    HashMap<Integer, String> snL2 = nodeDAO.getShortName("L2");
    HashMap<Integer, String> sn1 = nodeDAO.getShortName("1 ");
    HashMap<Integer, String> sn2 = nodeDAO.getShortName("2 ");
    HashMap<Integer, String> sn3 = nodeDAO.getShortName("3 ");
    HashMap<Integer, String> ln = nodeDAO.getLongNames("L1");
    HashMap<Integer, String> lnL2 = nodeDAO.getLongNames("L2");
    HashMap<Integer, String> ln1 = nodeDAO.getLongNames("1 ");
    HashMap<Integer, String> ln2 = nodeDAO.getLongNames("2 ");
    HashMap<Integer, String> ln3 = nodeDAO.getLongNames("3 ");

    nodePane.getChildren().clear();
    switch (index) {
      case 0:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "L1")) {
            getNodesWFunctionality(listOfNodes, i, sn, ln);
          }
        }
        break;
      case 1:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "L2")) {
            getNodesWFunctionality(listOfNodes, i, snL2, lnL2);
          }
        }
        break;

      case 2:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "1 ")) {
            getNodesWFunctionality(listOfNodes, i, sn1, ln1);
          }
        }

        break;
      case 3:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "2 ")) {
            getNodesWFunctionality(listOfNodes, i, sn2, ln2);
          }
        }

        break;
      case 4:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "3 ")) {
            getNodesWFunctionality(listOfNodes, i, sn3, ln3);
          }
        }

        break;
    }
  }

  private void getNodesWFunctionality(
      ArrayList<Node> listOfNodes, int i, HashMap<Integer, String> sn, HashMap<Integer, String> ln)
      throws SQLException {

    Node currentNode = listOfNodes.get(i);
    Label nodeLabel = new Label();

    LocationNameDAO locationNameDAO = new LocationNameDAO();
    HashMap<String, LocationName> labelMap = locationNameDAO.getAll();

    Circle point =
        new Circle(
            listOfNodes.get(i).getXcoord(),
            listOfNodes.get(i).getYcoord(),
            10,
            Color.rgb(1, 45, 90));
    if (!Objects.equals(labelMap.get(ln.get(currentNode.getNodeID())).getNodeType(), "HALL")) {
      nodeLabel.setTextFill(Color.BLACK);
      nodeLabel.setText(sn.get(listOfNodes.get(i).getNodeID()));
      nodeLabel.setLayoutX(listOfNodes.get(i).getXcoord());
      nodeLabel.setLayoutY(listOfNodes.get(i).getYcoord() + 10);
      nodeLabel.toFront();
    }
    /*
       point.setOnMouseEntered(event ->

               Color.rgb(255, 255, 0);
               System.out.println("worked");
       point.setOnMouseExited(event -> Color.rgb(1, 45, 90));

    */

    point.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            try {
              displayData(currentNode);
            } catch (SQLException | IOException e) {
              throw new RuntimeException(e);
            }
          }
        });
    nodePane.getChildren().add(point);
    nodePane.getChildren().add(nodeLabel);
  }

  public void displayData(Node point) throws IOException, SQLException {

    NodeDAO nodeDAO = new NodeDAO();

    LocationNameDAO locationNameDAO = new LocationNameDAO();

    HashMap<String, LocationName> LocationNames = locationNameDAO.getAll();
    HashMap<Integer, String> sn = nodeDAO.getShortName(point.getFloor());
    ArrayList<LocationName> locs = new ArrayList<>(LocationNames.values());
    LocationName knownLoc = new LocationName();

    for (int i = 0; i < locs.size(); i++) {
      if (Objects.equals(sn.get(point.getNodeID()), locs.get(i).getShortName())) {
        knownLoc = locs.get(i);
      }
    }

    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/editNodePopUp.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    editPopUpController controller = loader.getController();
    controller.setFields(point, knownLoc);

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }

  public void remove(javafx.scene.control.TextArea displayNode, Button exit) {
    nodePane.getChildren().remove(displayNode);
    nodePane.getChildren().remove(exit);
  }

  public void addNode() throws IOException, SQLException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/InsertNode.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    InsertNodeController controller = loader.getController();

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }

  public void addLocationName() throws IOException, SQLException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/AddLocationName.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    AddLocationNameController controller = loader.getController();

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }

  public void addMoves() throws IOException, SQLException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/AddMove.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    AddMoveController controller = loader.getController();

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }

  public void deleteLocation() throws IOException, SQLException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/DeleteLocationNamePopOver.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    DeleteLocationNameControllerPopOver controller = loader.getController();

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }

  public void exit() {
    Platform.exit();
  }
}
