package edu.wpi.teamg.controllers;

import static edu.wpi.teamg.App.*;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.Edge;
import edu.wpi.teamg.ORMClasses.LocationName;
import edu.wpi.teamg.ORMClasses.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
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

  @FXML MFXButton deleteMove;

  @FXML MFXButton delEdge;

  @FXML MFXButton locNameMod;

  @FXML MFXToggleButton toggSn;
  boolean moved = false;

  boolean lineGen;
  int floor = 0;

  int nodeClickCount = 0;

  Node nodeCon1 = new Node();
  Node nodeCon2 = new Node();

  ArrayList<ImageView> img = new ArrayList<>();

  boolean editEdge = false;

  boolean shortNameToggle = true;

  public void initialize() throws SQLException, IOException {
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

    if (shortNameToggle) {
      nodeLabel.setFill(Color.BLACK);
      nodeLabel.setText(sn.get(listOfNodes.get(i).getNodeID()));
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
                  point);
            }
            if (!moved) {

              if (editEdge) {
                if (nodeClickCount == 0) {
                  nodeCon1 = currentNode;
                  nodeClickCount = nodeClickCount + 1;
                }
                if (nodeClickCount == 1) {
                  nodeCon2 = currentNode;

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

    edgeDao.insert(newEdge);
    nodeClickCount = 0;
    System.out.println("edge added" + nodeCon1.getNodeID() + "      " + nodeCon2.getNodeID());

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
      Circle point)
      throws IOException {
    final PopOver window = new PopOver();
    var loader = new FXMLLoader(App.class.getResource("views/ConfirmPopUp.fxml"));
    window.setContentNode(loader.load());

    window.setOnHiding(
        event -> {
          try {
            floorButtons(imgs, floor);
            nodePane.getChildren().remove(point);
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    window.setArrowSize(0);
    ConfirmPopUpController controller = loader.getController();
    controller.setFields(x1, y1, x2, y2, potentialUpdate, window, imgs, index);

    final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
    window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());
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

  public void exit() {
    Platform.exit();
  }
}
