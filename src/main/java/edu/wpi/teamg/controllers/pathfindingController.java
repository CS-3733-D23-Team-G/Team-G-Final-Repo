package edu.wpi.teamg.controllers;

import static edu.wpi.teamg.App.*;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.MoveDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.awt.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.SearchableComboBox;

// Touch Ups
// Make NodeInfo Disappear More clean
// If we have an error all nodes should remain displayed

public class pathfindingController {
  public Group group;
  @FXML MFXButton goToAdminSign;
  @FXML MFXButton pathFindButton;

  @FXML TextArea results;
  @FXML GesturePane pane;
  @FXML Pane nodePane;

  // Change Floor Maps
  @FXML MFXButton l1;
  @FXML MFXButton l2;
  @FXML MFXButton floor1;
  @FXML MFXButton floor2;
  @FXML MFXButton floor3;

  private ArrayList<ImageView> imageViewsList = new ArrayList<>();

  @FXML SearchableComboBox startLocDrop;
  @FXML SearchableComboBox endLocDrop;

  @FXML MFXComboBox floorStart;
  @FXML MFXComboBox floorEnd;

  @FXML MFXCheckbox aStarCheckBox;
  @FXML MFXCheckbox bfsCheckBox;
  @FXML MFXCheckbox dfsCheckBox;

  @FXML DatePicker date;

  @FXML MFXToggleButton dSN;

  ObservableList<String> locationListStart;
  ObservableList<String> locationListEnd;
  ObservableList<String> FloorList;

  DAORepo dao = new DAORepo();
  Algorithm algo;

  boolean snLab = true;

  int floor = 0;

  @FXML
  public void initialize() throws SQLException {

    //  goToAdminSign.setOnMouseClicked(event -> Navigation.navigate(Screen.ADMIN_SIGNAGE_PAGE));

    aStarCheckBox.setSelected(true);
    dSN.setSelected(true);

    aStarCheckBox.setOnAction(
        event -> {
          if (aStarCheckBox.isSelected()) {
            bfsCheckBox.setSelected(false);
            dfsCheckBox.setSelected(false);
          }
        });

    bfsCheckBox.setOnAction(
        event -> {
          if (bfsCheckBox.isSelected()) {
            aStarCheckBox.setSelected(false);
            dfsCheckBox.setSelected(false);
          }
        });

    dfsCheckBox.setOnAction(
        event -> {
          if (dfsCheckBox.isSelected()) {
            aStarCheckBox.setSelected(false);
            bfsCheckBox.setSelected(false);
          }
        });
    dSN.setOnAction(
        event -> {
          if (!dSN.isSelected()) {
            nodePane.getChildren().removeIf(node -> node instanceof Text);
            snLab = false;
          }
          if (dSN.isSelected()) {
            snLab = true;
            try {
              newNodes(floor);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
        });

    startingFloor();
    longNameEnd(0);

    floorEnd.setOnAction(
        event -> {
          if (Objects.equals(floorEnd.getValue(), "L1")) {
            try {
              longNameEnd(0);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
          if (Objects.equals(floorEnd.getValue(), "L2")) {
            try {
              longNameEnd(1);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
          if (Objects.equals(floorEnd.getValue(), "1")) {
            try {
              longNameEnd(2);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }

          if (Objects.equals(floorEnd.getValue(), "2")) {
            try {
              longNameEnd(3);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }

          if (Objects.equals(floorEnd.getValue(), "3")) {
            try {
              longNameEnd(4);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
        });

    pathFindButton.setOnMouseClicked(
        event -> {
          try {
            if (aStarCheckBox.isSelected()) {
              algo = new Astar();
              setPath(algo.process(startLocDrop, endLocDrop, Date.valueOf(date.getValue())));
            } else if (dfsCheckBox.isSelected()) {
              algo = new DFS();
              setPath(algo.process(startLocDrop, endLocDrop, Date.valueOf(date.getValue())));
            } else if (bfsCheckBox.isSelected()) {
              algo = new BFS();
              setPath(algo.process(startLocDrop, endLocDrop, Date.valueOf(date.getValue())));
            }
          } catch (SQLException e) {
            System.err.println("SQL Exception");
            e.printStackTrace();
          }
        });

    // goToL1();
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
    longNameNodes(0);

    // Scales Map
    pane.setMinScale(.001);
    pane.zoomTo(.3, new Point2D(2500, 1700));
    pane.zoomTo(.3, new Point2D(2500, 1700));

    pane.centreOnX(1000);
    pane.centreOnY(500);

    imageViewsList.add(mapView);
    imageViewsList.add(mapViewL2);
    imageViewsList.add(mapViewFloor1);
    imageViewsList.add(mapViewFloor2);
    imageViewsList.add(mapViewFloor3);

    l1.setDisable(true);

    l1.setOnMouseClicked(
        event -> {
          l1.setDisable(true);
          l2.setDisable(false);
          floor1.setDisable(false);
          floor2.setDisable(false);
          floor3.setDisable(false);
          floor = 0;
          try {
            floorButtons(imageViewsList, 0);

          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    l2.setOnMouseClicked(
        event -> {
          l1.setDisable(false);
          l2.setDisable(true);
          floor1.setDisable(false);
          floor2.setDisable(false);
          floor3.setDisable(false);
          floor = 1;
          try {
            floorButtons(imageViewsList, 1);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    floor1.setOnMouseClicked(
        event -> {
          l1.setDisable(false);
          l2.setDisable(false);
          floor1.setDisable(true);
          floor2.setDisable(false);
          floor3.setDisable(false);
          floor = 2;
          try {
            floorButtons(imageViewsList, 2);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    floor2.setOnMouseClicked(
        event -> {
          l1.setDisable(false);
          l2.setDisable(false);
          floor1.setDisable(false);
          floor2.setDisable(true);
          floor3.setDisable(false);
          floor = 3;
          try {
            floorButtons(imageViewsList, 3);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
    floor3.setOnMouseClicked(
        event -> {
          l1.setDisable(false);
          l2.setDisable(false);
          floor1.setDisable(false);
          floor2.setDisable(false);
          floor3.setDisable(true);
          floor = 4;
          try {
            floorButtons(imageViewsList, 4);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    floorStart.setOnAction(
        event -> {
          if (Objects.equals(floorStart.getValue(), "L1")) {
            try {
              goToL1(imageViewsList);
              l1.setDisable(true);
              l2.setDisable(false);
              floor1.setDisable(false);
              floor2.setDisable(false);
              floor3.setDisable(false);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
          if (Objects.equals(floorStart.getValue(), "L2")) {
            try {
              goToL2(imageViewsList);
              l1.setDisable(false);
              l2.setDisable(true);
              floor1.setDisable(false);
              floor2.setDisable(false);
              floor3.setDisable(false);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }

          if (Objects.equals(floorStart.getValue(), "1")) {
            try {
              goToFloor1(imageViewsList);
              l1.setDisable(false);
              l2.setDisable(false);
              floor1.setDisable(true);
              floor2.setDisable(false);
              floor3.setDisable(false);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }

          if (Objects.equals(floorStart.getValue(), "2")) {
            try {
              goToFloor2(imageViewsList);
              l1.setDisable(false);
              l2.setDisable(false);
              floor1.setDisable(false);
              floor2.setDisable(true);
              floor3.setDisable(false);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }

          if (Objects.equals(floorStart.getValue(), "3")) {
            try {
              goToFloor3(imageViewsList);
              l1.setDisable(false);
              l2.setDisable(false);
              floor1.setDisable(false);
              floor2.setDisable(false);
              floor3.setDisable(true);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
        });

    // Scaling is currently the issue with the node map

    // HashMap<Integer, Node> nodes = App.;
    ArrayList<String> labelsL1 = new ArrayList<>(l1Labels.values());
    HashMap<Integer, Node> goodNodesL1 = nodeDAO.getNodeIDsGivenShortnames(labelsL1);
    ArrayList<Node> goodNodesListL1 = new ArrayList<>(goodNodesL1.values());
    for (int i = 0; i < goodNodesListL1.size(); i++) {
      //      if (Objects.equals(goodNodesListL1.get(i).getFloor(), "L1")) {
      //        getNodesWFunctionality(goodNodesListL1, i, l1Labels);
      //      }
      getNodesWFunctionality(goodNodesListL1, i, l1Labels);
    }
  }

  public void setPath(ArrayList<String> path) throws SQLException {

    System.out.println(path);
    //    if (path.size() == 1) {
    //      results.setText("Error: No Possible Path Found");
    //    } else {
    //      results.setText(String.valueOf(path));
    //    }

    NodeDAO nodeDAO = new NodeDAO();
    HashMap<Integer, Node> nodes = nodeDAO.getAll();
    ArrayList<String> pathForFloor = new ArrayList<>();
    Circle fPoint = new Circle();
    Polygon triangle = new Polygon();
    Circle start = new Circle();
    Circle end = new Circle();

    String floor = nodes.get(Integer.parseInt(path.get(0))).getFloor();
    switch (floor) {
      case "L1":
        goToL1(imageViewsList);
        l1.setDisable(true);
        l2.setDisable(false);
        floor1.setDisable(false);
        floor2.setDisable(false);
        floor3.setDisable(false);

        break;
      case "L2":
        goToL2(imageViewsList);
        l1.setDisable(false);
        l2.setDisable(true);
        floor1.setDisable(false);
        floor2.setDisable(false);
        floor3.setDisable(false);
        break;
      case "1 ":
        goToFloor1(imageViewsList);
        l1.setDisable(false);
        l2.setDisable(false);
        floor1.setDisable(true);
        floor2.setDisable(false);
        floor3.setDisable(false);
        break;
      case "2 ":
        goToFloor2(imageViewsList);
        l1.setDisable(false);
        l2.setDisable(false);
        floor1.setDisable(false);
        floor2.setDisable(true);
        floor3.setDisable(false);
        break;
      case "3 ":
        goToFloor3(imageViewsList);
        l1.setDisable(false);
        l2.setDisable(false);
        floor1.setDisable(false);
        floor2.setDisable(false);
        floor3.setDisable(true);
        break;
    }

    nodePane.getChildren().clear();
    for (int i = 0; i < path.size(); i++) {
      int finalI = i;

      Circle point =
          new Circle(
              nodes.get(Integer.parseInt(path.get(i))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(i))).getYcoord(),
              10,
              Color.rgb(1, 45, 90));
      if (i + 1 != path.size()
          && !Objects.equals(
              nodes.get(Integer.parseInt(path.get(i))).getFloor(),
              nodes.get(Integer.parseInt(path.get(i + 1))).getFloor())) {
        triangle.setFill(Color.rgb(246, 189, 56));
        point.setRadius(30);

        if (getFloorIndex(nodes.get(Integer.parseInt(path.get(i))).getFloor())
            < getFloorIndex(nodes.get(Integer.parseInt(path.get(i + 1))).getFloor())) {
          triangle
              .getPoints()
              .setAll(
                  point.getCenterX() - point.getRadius(),
                  point.getCenterY() + 10,
                  point.getCenterX(),
                  point.getCenterY() - point.getRadius(),
                  point.getCenterX() + point.getRadius(),
                  point.getCenterY() + 10);
        } else {
          triangle
              .getPoints()
              .setAll(
                  point.getCenterX() - point.getRadius(),
                  point.getCenterY() - 10,
                  point.getCenterX(),
                  point.getCenterY() + point.getRadius(),
                  point.getCenterX() + point.getRadius(),
                  point.getCenterY() - 10);
        }
        nodePane.getChildren().add(triangle);
        pathForFloor.add(path.get(i));
        triangle.setOnMouseClicked(
            event -> {
              try {
                nextFloor(nodes.get(Integer.parseInt(path.get(finalI + 1))), path, finalI + 1);
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              }
            });
        break;
      } else {

        if (i == 0) {
          point.setFill(Color.rgb(0, 156, 166));
          point.setRadius(20);

          start = point;
          nodePane.getChildren().add(start);
          pathForFloor.add(path.get(i));
        } else if (i + 1 == path.size()) {
          point.setFill(Color.rgb(0, 156, 166));
          point.setRadius(20);

          end = point;
          nodePane.getChildren().add(end);
          pathForFloor.add(path.get(i));
        } else {
          nodePane.getChildren().add(point);
          pathForFloor.add(path.get(i));
        }
      }
    }

    System.out.println(pathForFloor + " Testing");

    for (int i = 1; i < pathForFloor.size(); i++) {
      Line pathLine =
          new Line(
              nodes.get(Integer.parseInt(path.get(i - 1))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(i - 1))).getYcoord(),
              nodes.get(Integer.parseInt(path.get(i))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(i))).getYcoord());
      pathLine.setStrokeWidth(10);
      pathLine.setStroke(Color.rgb(1, 45, 90));
      nodePane.getChildren().add(pathLine);
    }

    triangle.toFront();
    start.toFront();
    end.toFront();
  }

  public void nextFloor(Node node, ArrayList<String> path, int index) throws SQLException {
    NodeDAO nodeDAO = new NodeDAO();
    HashMap<Integer, Node> nodes = nodeDAO.getAll();
    ArrayList<String> pathForFloor = new ArrayList<>();
    Polygon triangle = new Polygon();
    Circle downPoint = new Circle();
    Circle end = new Circle();

    String floor = node.getFloor();
    switch (floor) {
      case "L1":
        // goToL1(imageViewsList);
        floorButtons(imageViewsList, 0);
        l1.setDisable(true);
        l2.setDisable(false);
        floor1.setDisable(false);
        floor2.setDisable(false);
        floor3.setDisable(false);
        break;
      case "L2":
        // goToL2(imageViewsList);
        floorButtons(imageViewsList, 1);
        l1.setDisable(false);
        l2.setDisable(true);
        floor1.setDisable(false);
        floor2.setDisable(false);
        floor3.setDisable(false);
        break;
      case "1 ":
        // goToFloor1(imageViewsList);
        floorButtons(imageViewsList, 2);
        l1.setDisable(false);
        l2.setDisable(false);
        floor1.setDisable(true);
        floor2.setDisable(false);
        floor3.setDisable(false);
        break;
      case "2 ":
        // goToFloor2(imageViewsList);
        floorButtons(imageViewsList, 3);
        l1.setDisable(false);
        l2.setDisable(false);
        floor1.setDisable(false);
        floor2.setDisable(true);
        floor3.setDisable(false);
        break;
      case "3 ":
        // goToFloor3(imageViewsList);
        floorButtons(imageViewsList, 3);
        l1.setDisable(false);
        l2.setDisable(false);
        floor1.setDisable(false);
        floor2.setDisable(false);
        floor3.setDisable(true);
        break;
    }

    nodePane.getChildren().clear();
    for (int i = index; i < path.size(); i++) {
      int finalI = i;

      if (i == index) {
        downPoint =
            new Circle(
                nodes.get(Integer.parseInt(path.get(i))).getXcoord(),
                nodes.get(Integer.parseInt(path.get(i))).getYcoord(),
                20,
                Color.rgb(246, 189, 56));

        nodePane.getChildren().add(downPoint);
        downPoint.setOnMouseClicked(
            event -> {
              try {
                setPath(path);
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              }
            });
      }
      Circle point =
          new Circle(
              nodes.get(Integer.parseInt(path.get(i))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(i))).getYcoord(),
              10,
              Color.rgb(1, 45, 90));
      if (i + 1 != path.size()
          && !Objects.equals(
              nodes.get(Integer.parseInt(path.get(i))).getFloor(),
              nodes.get(Integer.parseInt(path.get(i + 1))).getFloor())) {

        point.setRadius(30);
        if (getFloorIndex(nodes.get(Integer.parseInt(path.get(i))).getFloor())
            < getFloorIndex(nodes.get(Integer.parseInt(path.get(i + 1))).getFloor())) {
          triangle
              .getPoints()
              .setAll(
                  point.getCenterX() - point.getRadius(),
                  point.getCenterY() + 10,
                  point.getCenterX(),
                  point.getCenterY() - point.getRadius(),
                  point.getCenterX() + point.getRadius(),
                  point.getCenterY() + 10);
        } else {
          triangle
              .getPoints()
              .setAll(
                  point.getCenterX() - point.getRadius(),
                  point.getCenterY() - 10,
                  point.getCenterX(),
                  point.getCenterY() + point.getRadius(),
                  point.getCenterX() + point.getRadius(),
                  point.getCenterY() - 10);
        }
        triangle.setFill(Color.rgb(246, 189, 56));
        nodePane.getChildren().add(triangle);
        pathForFloor.add(path.get(i));
        triangle.setOnMouseClicked(
            event -> {
              try {
                nextFloor(nodes.get(Integer.parseInt(path.get(finalI + 1))), path, finalI + 1);
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              }
            });
        break;
      } else {
        if (i == (path.size() - 1)) {
          point.setFill(Color.rgb(0, 156, 166));
          point.setRadius(20);
          end = point;
          nodePane.getChildren().add(end);
          pathForFloor.add(path.get(i));
        } else {

          nodePane.getChildren().add(point);
          pathForFloor.add(path.get(i));
        }
      }
    }

    for (int i = 1; i < pathForFloor.size(); i++) {
      Line pathLine =
          new Line(
              nodes.get(Integer.parseInt(path.get(index + i - 1))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(index + i - 1))).getYcoord(),
              nodes.get(Integer.parseInt(path.get(index + i))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(index + i))).getYcoord());
      pathLine.setStrokeWidth(10);
      pathLine.setStroke(Color.rgb(1, 45, 90));
      nodePane.getChildren().add(pathLine);
    }

    downPoint.toFront();
    triangle.toFront();
    end.toFront();
  }

  public void goToL1(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(0).setVisible(true);

    longNameNodes(0);

    newNodes(0);
  }

  public void goToL2(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(1).setVisible(true);
    longNameNodes(1);
    newNodes(1);
  }

  public void goToFloor1(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(2).setVisible(true);
    longNameNodes(2);

    newNodes(2);
  }

  public void goToFloor2(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(3).setVisible(true);
    longNameNodes(3);

    newNodes(3);
  }

  public void goToFloor3(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(4).setVisible(true);
    longNameNodes(4);

    newNodes(4);
  }

  public void floorButtons(ArrayList<ImageView> imgs, int index) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(index).setVisible(true);
    newNodes(index);
  }

  public int getFloorIndex(String floor) {
    int floorIndex = 0;
    switch (floor) {
      case "L2":
        floorIndex = 1;
        break;
      case "1 ":
        floorIndex = 2;
        break;
      case "2 ":
        floorIndex = 3;
        break;
      case "3 ":
        floorIndex = 4;
    }

    return floorIndex;
  }

  public void newNodes(int index) throws SQLException {
    //    NodeDAO nodeDAO = new NodeDAO();
    //
    //    HashMap<Integer, Node> nodes = nodeDAO.getAll();
    //    ArrayList<Node> listOfNodes = new ArrayList<>(nodes.values());
    ArrayList<Node> listOfNodes = allNodeList;

    ArrayList<Node> listOfGoodNodes;

    for (int i = 0; i < listOfNodes.size(); i++) {}

    //    HashMap<Integer, String> sn = nodeDAO.getShortName("L1");
    //    HashMap<Integer, String> snL2 = nodeDAO.getShortName("L2");
    //    HashMap<Integer, String> sn1 = nodeDAO.getShortName("1 ");
    //    HashMap<Integer, String> sn2 = nodeDAO.getShortName("2 ");
    //    HashMap<Integer, String> sn3 = nodeDAO.getShortName("3 ");
    //    HashMap<Integer, String> ln = nodeDAO.getLongNames("L1");
    //    HashMap<Integer, String> lnL2 = nodeDAO.getLongNames("L2");
    //    HashMap<Integer, String> ln1 = nodeDAO.getLongNames("1 ");
    //    HashMap<Integer, String> ln2 = nodeDAO.getLongNames("2 ");
    //    HashMap<Integer, String> ln3 = nodeDAO.getLongNames("3 ");

    //    ArrayList<String> shortNoHallL1 =  new ArrayList<>(l1Labels.values());
    //    ArrayList<String> shortNoHallL2 =  new ArrayList<>(l1Labels.values());
    //    ArrayList<String> shortNoHallF1 =  new ArrayList<>(l1Labels.values());
    //    ArrayList<String> shortNoHallF2 =  new ArrayList<>(l1Labels.values());
    //    ArrayList<String> shortNoHallF3 =  new ArrayList<>(l1Labels.values());

    // Need to convert Labels array from shortname strings to node id

    nodePane.getChildren().clear();
    switch (index) {
      case 0:

        ArrayList<String> labelsL1 = new ArrayList<>(l1Labels.values());
        HashMap<Integer, Node> goodNodesL1 = nodeDAO.getNodeIDsGivenShortnames(labelsL1);
        ArrayList<Node> goodNodesListL1 = new ArrayList<>(goodNodesL1.values());

        for (int i = 0; i < goodNodesListL1.size(); i++) {
          //          if (Objects.equals(goodNodesL1.get(i).getFloor(), "L1")) {
          //            getNodesWFunctionality(goodNodesListL1, i, l1Labels);
          //          }

          getNodesWFunctionality(goodNodesListL1, i, l1Labels);

        }
        break;
      case 1:
        ArrayList<String> labelsL2 = new ArrayList<>(l2Labels.values());
        HashMap<Integer, Node> goodNodesL2 = nodeDAO.getNodeIDsGivenShortnames(labelsL2);
        ArrayList<Node> goodNodesListL2 = new ArrayList<>(goodNodesL2.values());

        for (int i = 0; i < goodNodesListL2.size(); i++) {
          //          if (Objects.equals(listOfNodes.get(i).getFloor(), "L2")) {
          //            getNodesWFunctionality(goodNodesListL2, i, l2Labels);
          //          }

          getNodesWFunctionality(goodNodesListL2, i, l2Labels);
        }
        break;

      case 2:
        ArrayList<String> labels1 = new ArrayList<>(F1Labels.values());
        HashMap<Integer, Node> goodNodes1 = nodeDAO.getNodeIDsGivenShortnames(labels1);
        ArrayList<Node> goodNodesList1 = new ArrayList<>(goodNodes1.values());

        for (int i = 0; i < goodNodesList1.size(); i++) {
          //          if (Objects.equals(listOfNodes.get(i).getFloor(), "1 ")) {
          //            getNodesWFunctionality(goodNodesList1, i, F1Labels);
          //          }

          getNodesWFunctionality(goodNodesList1, i, F1Labels);
        }

        break;
      case 3:
        ArrayList<String> labels2 = new ArrayList<>(F2Labels.values());
        HashMap<Integer, Node> goodNodes2 = nodeDAO.getNodeIDsGivenShortnames(labels2);
        ArrayList<Node> goodNodesList2 = new ArrayList<>(goodNodes2.values());

        for (int i = 0; i < goodNodesList2.size(); i++) {
          //          if (Objects.equals(goodNodesList2.get(i).getFloor(), "2 ")) {
          //              getNodesWFunctionality(goodNodesList2, i, F2Labels);
          //          }

          getNodesWFunctionality(goodNodesList2, i, F2Labels);
        }

        break;
      case 4:
        ArrayList<String> labels3 = new ArrayList<>(F3Labels.values());
        HashMap<Integer, Node> goodNodes3 = nodeDAO.getNodeIDsGivenShortnames(labels3);
        ArrayList<Node> goodNodesList3 = new ArrayList<>(goodNodes3.values());

        for (int i = 0; i < goodNodesList3.size(); i++) {
          //          if (Objects.equals(goodNodes3.get(i).getFloor(), "3 ")) {
          //            getNodesWFunctionality(goodNodesList3, i, F3Labels);
          //          }

          getNodesWFunctionality(goodNodesList3, i, F3Labels);
        }

        break;
    }
  }

  private void getNodesWFunctionality(
      ArrayList<Node> listOfNodes, int i, HashMap<Integer, String> sn) throws SQLException {

    Node currentNode = listOfNodes.get(i);
    Label nodeLabel = new Label();
    Text txt = new Text();

    Circle point =
        new Circle(
            listOfNodes.get(i).getXcoord(),
            listOfNodes.get(i).getYcoord(),
            10,
            Color.rgb(1, 45, 90));

    // nodeLabel.setTextFill(Color.BLACK);
    txt.setFill(Color.BLACK);
    txt.setTextAlignment(TextAlignment.LEFT);
    // nodeLabel.setPrefSize(10, 10);
    txt.setFont(new Font(20));
    txt.setText(sn.get(listOfNodes.get(i).getNodeID()));
    txt.setLayoutX(listOfNodes.get(i).getXcoord() - 30);
    txt.setLayoutY(listOfNodes.get(i).getYcoord() + 30);
    txt.toFront();
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
            } catch (SQLException e) {
              throw new RuntimeException(e);
            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }
        });

    System.out.println(sn.get(listOfNodes.get(i).getNodeID()));

    nodePane.getChildren().add(point);
    nodePane.getChildren().add(txt);
  }

  public void displayData(Node point) throws SQLException, IOException {

    MoveDAO moveDAO = new MoveDAO();

    ArrayList<Move> move = new ArrayList<>(moveDAO.getAll());
    Move aMove = new Move();

    for (int i = 0; i < move.size(); i++) {
      if (move.get(i).getNodeID() == point.getNodeID()) {
        aMove = move.get(i);
      }
    }

    LocationNameDAO locationNameDAO = new LocationNameDAO();
    HashMap<String, LocationName> locNam = locationNameDAO.getAll();

    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/PathfindingPopOver.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    PathfindingOverController controller = loader.getController();
    controller.setFields(aMove.getLongName(), locNam.get(aMove.getLongName()).getShortName());

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());

    //    nodePane.getChildren().removeIf(node -> node instanceof TextArea);
    //    TextArea displayNode = new TextArea();
    //    NodeDAO nodeDAO = new NodeDAO();
    //    String floor = point.getFloor();
    //
    //    HashMap<Integer, String> sn = nodeDAO.getLongNames(floor);
    //
    //    displayNode.setFont(Font.font(35));
    //
    //    displayNode.setText(sn.get(point.getNodeID()));
    //
    //    displayNode.setLayoutX(point.getXcoord());
    //    displayNode.setLayoutY(point.getYcoord());
    //    displayNode.setPrefWidth(550);
    //    displayNode.setPrefHeight(100);
    //    displayNode.setVisible(true);
    //    displayNode.toFront();
    //    displayNode.setEditable(false);
    //
    //    nodePane.getChildren().add(displayNode);
  }

  public HashMap<Integer, String> getHashMapL1LongName(int index) throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();

    if (index == 0) {
      longNameHashMap = App.L1Floor;
    }
    if (index == 1) {
      longNameHashMap = App.L2Floor;
    }
    if (index == 2) {
      longNameHashMap = App.Floor1;
    }
    if (index == 3) {
      longNameHashMap = App.Floor2;
    }
    if (index == 4) {
      longNameHashMap = Floor3;
    }

    return longNameHashMap;
  }

  public void longNameNodes(int index) throws SQLException {
    ArrayList<String> locationNamesStart = new ArrayList<>();
    ArrayList<String> locationNamesEnd = new ArrayList<>();
    HashMap<Integer, String> testingLongName = this.getHashMapL1LongName(index);
    int endFloorIndex = 0;

    testingLongName.forEach(
        (i, m) -> {
          locationNamesStart.add(m);
          // System.out.println("LocationName: " + m);
          //          System.out.println("Employee ID:" + m.getEmpid());
          //          System.out.println("Status:" + m.getStatus());
          //          System.out.println("Location:" + m.getLocation());
          //          System.out.println("Serve By:" + m.getServ_by());
          //          System.out.println();
        });

    Collections.sort(locationNamesStart, String.CASE_INSENSITIVE_ORDER);

    locationListStart = FXCollections.observableArrayList(locationNamesStart);
    startLocDrop.setItems(locationListStart);
  }

  public void longNameEnd(int endFloorIndex) throws SQLException {
    ArrayList<String> locationNamesEnd = new ArrayList<>();
    HashMap<Integer, String> LongNameFinal = this.getHashMapL1LongName(endFloorIndex);

    LongNameFinal.forEach(
        (i, m) -> {
          locationNamesEnd.add(m);
          // System.out.println("LocationName: " + m);
          //          System.out.println("Employee ID:" + m.getEmpid());
          //          System.out.println("Status:" + m.getStatus());
          //          System.out.println("Location:" + m.getLocation());
          //          System.out.println("Serve By:" + m.getServ_by());
          //          System.out.println();
        });

    Collections.sort(locationNamesEnd, String.CASE_INSENSITIVE_ORDER);
    locationListEnd = FXCollections.observableArrayList(locationNamesEnd);
    endLocDrop.setItems(locationListEnd);
  }

  public void startingFloor() {
    ArrayList<String> floors = new ArrayList<>();
    floors.add("L1");
    floors.add("L2");
    floors.add("1");
    floors.add("2");
    floors.add("3");

    FloorList = FXCollections.observableArrayList(floors);

    floorStart.setItems(FloorList);
    floorEnd.setItems(FloorList);
    floorStart.setValue("L1");
    floorStart.setText("L1");
    floorEnd.setValue("L1");
    floorEnd.setText("L1");
  }

  public void listenToMouse() {}

  public void exit() {
    Platform.exit();
  }
}
