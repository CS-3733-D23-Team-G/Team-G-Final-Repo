package edu.wpi.teamg.controllers;

import static edu.wpi.teamg.App.*;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.Edge;
import edu.wpi.teamg.ORMClasses.LocationName;
import edu.wpi.teamg.ORMClasses.Move;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
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

  @FXML MFXToggleButton toggleEdge;

  @FXML MFXButton addEdge;

  @FXML MFXButton alignButton;

  @FXML MFXButton horizontalButton;

  @FXML MFXButton verticalButton;

  @FXML Label alignLabel;

  @FXML MFXButton deleteMove;

  @FXML MFXButton delEdge;

  @FXML MFXButton locNameMod;

  @FXML MFXToggleButton toggSn;

  @FXML MFXButton messageButton;

  @FXML MFXButton help;

  @FXML MFXDatePicker mapEditDate;

  @FXML AnchorPane forms;

  boolean moved = false;

  boolean lineGen;
  int floor = 0;

  int nodeClickCount = 0;

  Node nodeCon1 = new Node();
  Node nodeCon2 = new Node();
  String message;

  ArrayList<ImageView> img = new ArrayList<>();

  ArrayList<Node> alignedNodes = new ArrayList<Node>();
  ArrayList<Node> allCircles = new ArrayList<Node>();

  boolean isAlignClicked = false;

  boolean editEdge = false;

  boolean shortNameToggle = true;

  static boolean playAnimation = false;

  boolean moves = false;
  HashMap<Integer, Move> moving = new HashMap<>();

  public void initialize() throws SQLException, IOException {
    App.bool = false;
    verticalButton.setVisible(false);
    horizontalButton.setVisible(false);
    updateMove();

    forms.setDisable(true);
    toggSn.setSelected(true);
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

    toggleEdge.setOnAction(
        event -> {
          if (!toggleEdge.isSelected()) {
            nodePane.getChildren().removeIf(node -> node instanceof Line);
            lineGen = false;
          }
          if (toggleEdge.isSelected()) {
            lineGen = true;
            edgeDisplay(floor);
          }
        });

    toggSn.setOnAction(
        event -> {
          if (!toggSn.isSelected()) {
            nodePane.getChildren().removeIf(node -> node instanceof Text);
            shortNameToggle = false;
          }
          if (toggSn.isSelected()) {
            try {
              shortNameToggle = true;
              newNodes(floor);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
        });

    mapEditDate.setOnCommit(
        event -> {
          updateMove();
          nodePane.getChildren().removeIf(node -> node instanceof Text);
          try {
            floorButtons(img, floor);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    addEdge.setOnMouseClicked(
        event -> {
          editEdge = true;
        });

    deleteMove.setOnMouseClicked(
        event -> {
          try {
            deleteAMove();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    delEdge.setOnMouseClicked(
        event -> {
          try {
            deleteEdge();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    locNameMod.setOnMouseClicked(
        event -> {
          try {
            locPop();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
    alignButton.setOnMouseClicked(
        event -> {
          isAlignClicked = true;
          horizontalButton.setVisible(true);
          verticalButton.setVisible(true);
        });
    horizontalButton.setOnMouseClicked(
        event -> {
          try {
            alignCirclesHorizontal(allCircles);
            completeAnimation("Nodes aligned horizontally.");
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
          isAlignClicked = false;
          allCircles.clear();
        });

    verticalButton.setOnMouseClicked(
        event -> {
          try {
            alignCirclesVertical(allCircles);
            completeAnimation("Nodes aligned vertically.");
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
          isAlignClicked = false;
          allCircles.clear();
        });

    messageButton.setOnMouseClicked(
        event -> {
          try {
            displayMoveChange();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    help.setOnMouseClicked(
        event -> {
          try {
            getHelp();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

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
    pane.zoomTo(.5, new Point2D(1250, 850));
    pane.zoomTo(.5, new Point2D(1250, 850));

    pane.centreOnX(1000);
    pane.centreOnY(500);

    ArrayList<ImageView> imageViewsList = new ArrayList<>();
    imageViewsList.add(mapView);
    imageViewsList.add(mapViewL2);
    imageViewsList.add(mapViewFloor1);
    imageViewsList.add(mapViewFloor2);
    imageViewsList.add(mapViewFloor3);

    img = imageViewsList;

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
    //
    //    NodeDAO nodeDAO = new NodeDAO();
    //
    //    HashMap<Integer, edu.wpi.teamg.ORMClasses.Node> nodes = nodeDAO.getAll();
    //    ArrayList<edu.wpi.teamg.ORMClasses.Node> listOfNodes = new ArrayList<>(nodes.values());

    if (lineGen) {
      edgeDisplay(0);
    }
    ArrayList<Node> listOfNodes = allNodeList;
    //    HashMap<Integer, String> ln = nodeDAO.getLongNames("L1");
    //    HashMap<Integer, String> sn = nodeDAO.getShortName("L1");

    for (int i = 0; i < listOfNodes.size(); i++) {
      if (Objects.equals(listOfNodes.get(i).getFloor(), "L1")) {
        getNodesWFunctionality(listOfNodes, i, sn);
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

  public void alignCirclesVertical(ArrayList<Node> circles) throws SQLException {
    int firstX = circles.get(0).getXcoord();
    int firstY = circles.get(0).getYcoord();

    for (Node circle : circles) {
      circle.setXcoord(firstX);

      nodeDAO.update(circle, "xcoord", circle.getXcoord());

      if (firstY == circle.getYcoord()) {

        circle.setYcoord(circle.getYcoord() + 200);
        nodeDAO.update(circle, "ycoord", circle.getYcoord());
      }
    }

    nodePane.getChildren().clear();
    floorButtons(img, floor);

    refresh();
  }

  public void alignCirclesHorizontal(ArrayList<Node> circles) throws SQLException {
    int firstY = circles.get(0).getYcoord();
    int firstX = circles.get(0).getXcoord();

    for (Node circle : circles) {
      circle.setYcoord(firstY);

      nodeDAO.update(circle, "ycoord", circle.getYcoord());

      if (firstX == circle.getXcoord()) {

        circle.setXcoord(circle.getXcoord() + 200);
        nodeDAO.update(circle, "xcoord", circle.getXcoord());
      }
    }

    nodePane.getChildren().clear();
    floorButtons(img, floor);

    refresh();
  }

  public void goToL1(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(0).setVisible(true);

    nodePane.getChildren().clear();

    floor = 0;
    if (lineGen) {
      edgeDisplay(0);
    }
    newNodes(0);
  }

  public void goToL2(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }

    nodePane.getChildren().clear();

    floor = 1;
    if (lineGen) {
      edgeDisplay(1);
    }

    imgs.get(1).setVisible(true);
    newNodes(1);
  }

  public void goToFloor1(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(2).setVisible(true);

    nodePane.getChildren().clear();
    floor = 2;
    if (lineGen) {
      edgeDisplay(2);
    }
    newNodes(2);
  }

  public void goToFloor2(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(3).setVisible(true);

    if (nodePane != null) {
      nodePane.getChildren().clear();
    }

    floor = 3;
    if (lineGen) {
      edgeDisplay(3);
    }
    newNodes(3);
  }

  public void goToFloor3(ArrayList<ImageView> imgs) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(4).setVisible(true);

    nodePane.getChildren().clear();

    floor = 4;
    if (lineGen) {
      edgeDisplay(4);
    }
    newNodes(4);
  }

  public void floorButtons(ArrayList<ImageView> imgs, int index) throws SQLException {
    for (int i = 0; i < imgs.size(); i++) {
      imgs.get(i).setVisible(false);
    }
    imgs.get(index).setVisible(true);
    newNodes(index);
  }

  public void newNodes(int index) throws SQLException {
    //    NodeDAO nodeDAO = new NodeDAO();
    //
    //    HashMap<Integer, edu.wpi.teamg.ORMClasses.Node> nodes = nodeDAO.getAll();
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

    ArrayList<Node> listOfNodes = allNodeList;

    switch (index) {
      case 0:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "L1")) {
            getNodesWFunctionality(listOfNodes, i, sn);
          }
        }
        break;
      case 1:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "L2")) {
            getNodesWFunctionality(listOfNodes, i, snL2);
          }
        }
        break;

      case 2:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "1 ")) {
            getNodesWFunctionality(listOfNodes, i, sn1);
          }
        }

        break;
      case 3:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "2 ")) {
            getNodesWFunctionality(listOfNodes, i, sn2);
          }
        }

        break;
      case 4:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "3 ")) {
            getNodesWFunctionality(listOfNodes, i, sn3);
          }
        }

        break;
    }
  }

  public void edgeDisplay(int index) {

    switch (index) {
      case 0:
        for (int i = 0; i < listOfEdges.size(); i++) {
          if (Objects.equals(
                  allNodes.get(listOfEdges.get(i).getStartNode()).getFloor(),
                  allNodes.get(listOfEdges.get(i).getEndNode()).getFloor())
              && Objects.equals(allNodes.get(listOfEdges.get(i).getStartNode()).getFloor(), "L1")) {
            getEdgeDisplay(listOfEdges, i);
          }
        }

        break;
      case 1:
        for (int i = 0; i < listOfEdges.size(); i++) {
          if (Objects.equals(
                  allNodes.get(listOfEdges.get(i).getStartNode()).getFloor(),
                  allNodes.get(listOfEdges.get(i).getEndNode()).getFloor())
              && Objects.equals(allNodes.get(listOfEdges.get(i).getStartNode()).getFloor(), "L2")) {
            getEdgeDisplay(listOfEdges, i);
          }
        }

        break;

      case 2:
        for (int i = 0; i < listOfEdges.size(); i++) {
          if (Objects.equals(
                  allNodes.get(listOfEdges.get(i).getStartNode()).getFloor(),
                  allNodes.get(listOfEdges.get(i).getEndNode()).getFloor())
              && Objects.equals(allNodes.get(listOfEdges.get(i).getStartNode()).getFloor(), "1 ")) {
            getEdgeDisplay(listOfEdges, i);
          }
        }

        break;

      case 3:
        for (int i = 0; i < listOfEdges.size(); i++) {
          if (Objects.equals(
                  allNodes.get(listOfEdges.get(i).getStartNode()).getFloor(),
                  allNodes.get(listOfEdges.get(i).getEndNode()).getFloor())
              && Objects.equals(allNodes.get(listOfEdges.get(i).getStartNode()).getFloor(), "2 ")) {
            getEdgeDisplay(listOfEdges, i);
          }
        }

        break;

      case 4:
        for (int i = 0; i < listOfEdges.size(); i++) {
          if (Objects.equals(
                  allNodes.get(listOfEdges.get(i).getStartNode()).getFloor(),
                  allNodes.get(listOfEdges.get(i).getEndNode()).getFloor())
              && Objects.equals(allNodes.get(listOfEdges.get(i).getStartNode()).getFloor(), "3 ")) {
            getEdgeDisplay(listOfEdges, i);
          }
        }

        break;
    }
  }

  public void getEdgeDisplay(ArrayList<Edge> edgeArray, int i) {

    Node node1 = allNodes.get(edgeArray.get(i).getStartNode());
    Node node2 = allNodes.get(edgeArray.get(i).getEndNode());
    Line pathLine =
        new Line(node1.getXcoord(), node1.getYcoord(), node2.getXcoord(), node2.getYcoord());
    pathLine.setStrokeWidth(10);
    pathLine.setStroke(Color.rgb(1, 45, 90));
    pathLine.setOnMouseClicked(
        event -> {
          try {
            displayEdgeData(edgeArray.get(i), node1, node2);
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });

    nodePane.getChildren().add(pathLine);
    pathLine.toFront();
  }

  public void completeAnimation(String message) {

    // Form Completion PopUp
    AnchorPane rect = new AnchorPane();
    rect.setLayoutX(1000);
    rect.setStyle(
        "-fx-pref-width: 400; -fx-pref-height: 100; -fx-background-color: #97E198; -fx-background-radius: 10");
    rect.setLayoutY(800);
    rect.toFront();

    Text completionText = new Text("You Are All Set!");
    completionText.setLayoutX(1125);
    completionText.setLayoutY(845);
    completionText.setStyle(
        "-fx-stroke: #000000;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 25;"
            + "-fx-font-weight: 500;");
    completionText.toFront();

    Text completionTextSecondRow = new Text(message);
    completionTextSecondRow.setLayoutX(1125);
    completionTextSecondRow.setLayoutY(875);
    completionTextSecondRow.setStyle(
        "-fx-stroke: #404040;"
            + "-fx-fill: #012D5A;"
            + "-fx-font-size: 20;"
            + "-fx-font-weight: 500;");
    completionTextSecondRow.toFront();

    // Image checkmarkImage = new Image("edu/wpi/teamg/Images/checkMarkIcon.png");
    ImageView completionImage = new ImageView(App.checkmarkImage);

    completionImage.setFitHeight(50);
    completionImage.setFitWidth(50);
    completionImage.setLayoutX(1025);
    completionImage.setLayoutY(825);
    completionImage.toFront();

    rect.setOpacity(0.0);
    completionImage.setOpacity(0.0);
    completionText.setOpacity(0.0);
    completionTextSecondRow.setOpacity(0.0);

    forms.getChildren().add(rect);
    forms.getChildren().add(completionText);
    forms.getChildren().add(completionImage);
    forms.getChildren().add(completionTextSecondRow);

    FadeTransition fadeIn1 = new FadeTransition(Duration.seconds(0.5), rect);
    fadeIn1.setFromValue(0.0);
    fadeIn1.setToValue(1.0);

    FadeTransition fadeIn2 = new FadeTransition(Duration.seconds(0.5), completionImage);
    fadeIn2.setFromValue(0.0);
    fadeIn2.setToValue(1.0);

    FadeTransition fadeIn3 = new FadeTransition(Duration.seconds(0.5), completionText);
    fadeIn3.setFromValue(0.0);
    fadeIn3.setToValue(1.0);

    FadeTransition fadeIn4 = new FadeTransition(Duration.seconds(0.5), completionTextSecondRow);
    fadeIn4.setFromValue(0.0);
    fadeIn4.setToValue(1.0);

    ParallelTransition parallelTransition =
        new ParallelTransition(fadeIn1, fadeIn2, fadeIn3, fadeIn4);

    parallelTransition.play();

    parallelTransition.setOnFinished(
        (event) -> {
          FadeTransition fadeOut1 = new FadeTransition(Duration.seconds(0.5), rect);
          fadeOut1.setDelay(Duration.seconds(1.5));
          fadeOut1.setFromValue(1.0);
          fadeOut1.setToValue(0.0);

          FadeTransition fadeOut2 = new FadeTransition(Duration.seconds(0.5), completionImage);
          fadeOut2.setDelay(Duration.seconds(1.5));
          fadeOut2.setFromValue(1.0);
          fadeOut2.setToValue(0.0);

          FadeTransition fadeOut3 = new FadeTransition(Duration.seconds(0.5), completionText);
          fadeOut3.setDelay(Duration.seconds(1.5));
          fadeOut3.setFromValue(1.0);
          fadeOut3.setToValue(0.0);

          FadeTransition fadeOut4 =
              new FadeTransition(Duration.seconds(0.5), completionTextSecondRow);
          fadeOut4.setDelay(Duration.seconds(1.5));
          fadeOut4.setFromValue(1.0);
          fadeOut4.setToValue(0.0);

          fadeOut1.play();
          fadeOut2.play();
          fadeOut3.play();
          fadeOut4.play();
        });
  }

  void getNodesWFunctionality(ArrayList<Node> listOfNodes, int i, HashMap<Integer, String> sn)
      throws SQLException {

    Node currentNode = listOfNodes.get(i);

    Text nodeLabel = new Text();
    //

    //    LocationNameDAO locationNameDAO = new LocationNameDAO();
    //    HashMap<String, LocationName> labelMap = locationNameDAO.getAll();

    Circle point =
        new Circle(
            listOfNodes.get(i).getXcoord(),
            listOfNodes.get(i).getYcoord(),
            10,
            Color.rgb(1, 45, 90));

    point.setOnMouseClicked(
        event -> {
          if (isAlignClicked) {
            point.setFill(Color.valueOf("#118AB2"));
            allCircles.add(currentNode);
          }
        });

    if (shortNameToggle) {
      nodeLabel.setFill(Color.BLACK);
      nodeLabel.setFont(Font.font(30));

      nodeLabel.setText(
          App.locMap.get(moving.get(listOfNodes.get(i).getNodeID()).getLongName()).getShortName());

      nodeLabel.setLayoutX(listOfNodes.get(i).getXcoord());
      nodeLabel.setLayoutY(listOfNodes.get(i).getYcoord() + 10);
      nodeLabel.toFront();

      nodePane.getChildren().add(nodeLabel);
    }

    /*
       point.setOnMouseEntered(event ->

               Color.rgb(255, 255, 0);
               System.out.println("worked");
       point.setOnMouseExited(event -> Color.rgb(1, 45, 90));

    */

    //    point.setOnMouseClicked(
    //        new EventHandler<MouseEvent>() {
    //          @Override
    //          public void handle(MouseEvent event) {
    //            try {
    //
    //
    //
    //            } catch (SQLException | IOException e) {
    //              throw new RuntimeException(e);
    //            }
    //          }
    //        });

    point.setOnContextMenuRequested(
        event -> {
          try {
            displayData(currentNode);
          } catch (IOException e) {
            throw new RuntimeException(e);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    point.setOnMouseDragged(event -> recordDrag(event, point));

    point.setOnMouseReleased(
        event -> {
          pane.setGestureEnabled(true);

          try {
            if (moved) {
              confirmPop(
                  currentNode.getXcoord(),
                  currentNode.getYcoord(),
                  (int) point.getCenterX(),
                  (int) point.getCenterY(),
                  currentNode,
                  img,
                  currentNode.getFloor(),
                  point,
                  nodePane);
            }
            if (!moved) {

              if (editEdge) {
                if (nodeClickCount == 0) {
                  nodeCon1 = currentNode;
                  point.setFill(Color.valueOf("#ef476f"));
                  nodeClickCount = nodeClickCount + 1;
                }
                if (nodeClickCount == 1) {
                  nodeCon2 = currentNode;
                  point.setFill(Color.valueOf("#ef476f"));

                  if (nodeCon1 != nodeCon2) {
                    addEdgeOffClicks(nodeCon1, nodeCon2);
                    editEdge = false;
                  }
                }
              }
            }

            moved = false;

          } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
          }
        });

    nodePane.getChildren().add(point);
    nodeLabel.toFront();

    // point.setOnMouseReleased(event -> recordDrag());

  }

  public void addEdgeOffClicks(Node nodeCon1, Node nodeCon2) throws SQLException {
    Edge newEdge = new Edge(nodeCon1.getNodeID(), nodeCon2.getNodeID());
    newEdge.setEdgeID(nodeCon1.getNodeID() + "_" + nodeCon2.getNodeID());

    edgeDao.insert(newEdge);
    nodeClickCount = 0;
    System.out.println("edge added" + nodeCon1.getNodeID() + "      " + nodeCon2.getNodeID());

    floorButtons(img, floor);
    completeAnimation("Edge added.");
    refresh();
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
    controller.setFields(point, knownLoc, moving);

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

    window.setTitle("Add Node");

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
    controller.setWind(window);

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());

    window.setOnHiding(
        event -> {
          if (playAnimation) {
            completeAnimation("Location name added.");
            playAnimation = false;
          }
        });
  }

  public void addMoves() throws IOException, SQLException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/AddMove.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    AddMoveController controller = loader.getController();
    controller.moveSetter(moving);

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }

  public void deleteLocation() throws IOException, SQLException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/DeleteLocationNamePopOver.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    DeleteLocationNameControllerPopOver controller = loader.getController();
    controller.setWind(window);

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());

    window.setOnHiding(
        event -> {
          if (playAnimation) {
            completeAnimation("Location name deleted.");
            playAnimation = false;
          }
        });
  }

  public void displayEdgeData(Edge edge, Node A, Node B) throws IOException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/EdgeDataPopOver.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    EdgeDataPopOverController controller = loader.getController();
    controller.setEdgeFields(edge, A, B);

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }

  public void deleteEdge() throws IOException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/DeleteEdgePopOver.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    DeleteEdgeController controller = loader.getController();
    controller.setWind(window);

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());

    window.setOnHiding(
        event -> {
          if (playAnimation) {
            completeAnimation("Edge deleted.");
            playAnimation = false;
          }
        });
  }

  public void displayMoveChange() throws IOException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/MapEditorPopOver.fxml"));
    window.setContentNode(loader.load());

    MapEditorPopUpController controller = loader.getController();
    message = controller.message;

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }

  public void recordDrag(MouseEvent event, Circle point) {

    pane.setGestureEnabled(false);

    double xVal = event.getX();
    double yVal = event.getY();

    point.setCenterX(xVal);
    point.setCenterY(yVal);

    moved = true;
  }

  public void confirmPop(
      int x1,
      int y1,
      int x2,
      int y2,
      Node potentialUpdate,
      ArrayList<ImageView> imgs,
      String index,
      Circle point,
      Pane nodePane)
      throws IOException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/ConfirmPopUp.fxml"));
    window.setContentNode(loader.load());

    //    nodePane.getChildren().remove(point);

    window.setArrowSize(0);
    ConfirmPopUpController controller = loader.getController();

    controller.setFields(x1, y1, x2, y2, potentialUpdate, window, imgs, index, nodePane);

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());

    window.setOnHiding(
        event -> {
          try {
            nodePane.getChildren().clear();
            floorButtons(imgs, floor);
            if (playAnimation) {
              completeAnimation("Node moved.");
              playAnimation = false;
            }
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });
  }

  public void deleteAMove() throws IOException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/DeleteMovePopUp.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    DeleteMovePopUpController controller = loader.getController();
    controller.passOver(window);
    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }

  public void locPop() throws SQLException, IOException {

    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/locNamePopUp.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    LocNamePopUpController controller = loader.getController();
    controller.setW(window);

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());

    window.setOnHiding(
        event -> {
          if (playAnimation) {
            completeAnimation("Location name modified.");
            playAnimation = false;
          }
        });
  }

  public void getHelp() throws IOException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/EditorInstructionsPopOver.fxml"));
    window.setContentNode(loader.load());

    window.setArrowSize(0);
    EditorInstructionsPopOverController controller = loader.getController();
    controller.setW(window);

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
  }

  public int findIndex(String currentFloor) {
    int floorIndex = 0;
    switch (currentFloor) {
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
        break;
    }

    return floorIndex;
  }

  public void updateMove() {

    ArrayList<Move> updateMove = new ArrayList<>();

    for (int i = 0; i < move.size(); i++) {

      if (mapEditDate.getValue() == null) {
        if (move.get(i).getDate().toLocalDate().isEqual(LocalDate.of(2023, Month.JANUARY, 1))) {
          moving.put(move.get(i).getNodeID(), move.get(i));
        }
      } else {
        if (mapEditDate.getValue().isAfter(move.get(i).date.toLocalDate())) {
          moving.put(move.get(i).getNodeID(), move.get(i));
        } else if (mapEditDate.getValue().isEqual(move.get(i).getDate().toLocalDate())) {
          updateMove.add(move.get(i));
        }
      }
    }

    for (int i = 0; i < updateMove.size(); i++) {
      moving.put(updateMove.get(i).getNodeID(), updateMove.get(i));
    }
  }

  public void exit() {
    Platform.exit();
  }
}
