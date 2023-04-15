package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.DAOs.EdgeDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.Edge;
import edu.wpi.teamg.ORMClasses.Graph;
import edu.wpi.teamg.ORMClasses.Node;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
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
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.SearchableComboBox;

// Touch Ups
// Make NodeInfo Disappear More clean
// If we have an error all nodes should remain displayed

public class SignagePageController {
  public Group group;
  @FXML MFXButton backToHomeButton;
  @FXML ChoiceBox<String> serviceRequestChoiceBox;
  @FXML MFXButton signagePageButton;
  @FXML MFXButton exitButton;
  @FXML MFXButton goToAdminSign;
  @FXML VBox tohome;
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

  @FXML SearchableComboBox floorStart;
  @FXML SearchableComboBox floorEnd;

  ObservableList<String> list =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");

  ObservableList<String> locationListStart;
  ObservableList<String> locationListEnd;
  ObservableList<String> FloorList;

  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {
    serviceRequestChoiceBox.setItems(list);
    signagePageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.PATHFINDING_PAGE));
    backToHomeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    goToAdminSign.setOnMouseClicked(event -> Navigation.navigate(Screen.ADMIN_SIGNAGE_PAGE));
    exitButton.setOnMouseClicked(event -> exit());
    serviceRequestChoiceBox.setOnAction(event -> loadServiceRequestForm());
    tohome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));

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
            processAStarAlg();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        });

    // goToL1();

    Image mapL1 = new Image("edu/wpi/teamg/Images/00_thelowerlevel1_pts'ed.png");
    Image mapL2 = new Image("edu/wpi/teamg/Images/00_thelowerlevel2_pts'ed.png");
    Image mapFloor1 = new Image("edu/wpi/teamg/Images/01_thefirstfloor_pts'ed.png");
    Image mapFloor2 = new Image("edu/wpi/teamg/Images/02_thesecondfloor.png");
    Image mapFloor3 = new Image("edu/wpi/teamg/Images/03_thethirdfloor.png");

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

    floorStart.setOnAction(
        event -> {
          if (Objects.equals(floorStart.getValue(), "L1")) {
            try {
              goToL1(imageViewsList);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
          if (Objects.equals(floorStart.getValue(), "L2")) {
            try {
              goToL2(imageViewsList);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }

          if (Objects.equals(floorStart.getValue(), "1")) {
            try {
              goToFloor1(imageViewsList);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }

          if (Objects.equals(floorStart.getValue(), "2")) {
            try {
              goToFloor2(imageViewsList);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }

          if (Objects.equals(floorStart.getValue(), "3")) {
            try {
              goToFloor3(imageViewsList);
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
          }
        });

    // Scaling is currently the issue with the node map

    NodeDAO nodeDAO = new NodeDAO();

    HashMap<Integer, edu.wpi.teamg.ORMClasses.Node> nodes = nodeDAO.getAll();
    ArrayList<edu.wpi.teamg.ORMClasses.Node> listOfNodes = new ArrayList<>(nodes.values());

    for (int i = 0; i < listOfNodes.size(); i++) {
      if (Objects.equals(listOfNodes.get(i).getFloor(), "L1")) {
        getNodesWFunctionality(listOfNodes, i);
      }
    }
  }

  public void loadServiceRequestForm() {
    if (serviceRequestChoiceBox.getValue().equals("Meal Request Form")) {
      Navigation.navigate(Screen.MEAL_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Furniture Request Form")) {
      Navigation.navigate(Screen.FURNITURE_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Conference Room Request Form")) {
      Navigation.navigate(Screen.ROOM_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Flowers Request Form")) {
      Navigation.navigate(Screen.FLOWERS_REQUEST);
    } else if (serviceRequestChoiceBox.getValue().equals("Office Supplies Request Form")) {
      Navigation.navigate(Screen.SUPPLIES_REQUEST);
    } else {
      return;
    }
  }

  public void processAStarAlg() throws SQLException {
    ArrayList<String> path = new ArrayList<>();

    NodeDAO nodeDAO = new NodeDAO();
    EdgeDAO edgeDAO = new EdgeDAO();
    ArrayList<Node> allNodes = new ArrayList<>(nodeDAO.getAll().values());
    ArrayList<Edge> allEdges = new ArrayList<>(edgeDAO.getAll().values());

    String L1StartNodeLongName = (String) startLocDrop.getValue();
    String L1EndNodeLongName = (String) endLocDrop.getValue();

    int L1StartNodeID = dao.getNodeIDbyLongName(L1StartNodeLongName);
    int L1EndNodeID = dao.getNodeIDbyLongName(L1EndNodeLongName);

    Node[] nodeArray = new Node[allNodes.size()];
    for (int i = 0; i < allNodes.size(); i++) {
      nodeArray[i] = allNodes.get(i);
    }
    Edge[] edgeArray = new Edge[allEdges.size()];
    for (int i = 0; i < allEdges.size(); i++) {
      edgeArray[i] = allEdges.get(i);
    }

    int startNode = 0;
    int endNode = 0;
    for (int i = 0; i < allNodes.size(); i++) {

      if (nodeArray[i].getNodeID() == L1StartNodeID) {
        startNode = i;
      }
      if (nodeArray[i].getNodeID() == L1EndNodeID) {
        endNode = i;
      }
    }

    Graph G1 = new Graph(nodeArray, edgeArray);
    int[][] Adj = G1.createWeightedAdj();

    System.out.println(nodeArray[0].getNodeID());
    path = G1.aStarAlg(Adj, startNode, endNode);

    setPath(path);
  }

  public void setPath(ArrayList<String> path) throws SQLException {

    if (path.size() == 1) {
      results.setText("Error: No Possible Path Found");
    } else {
      results.setText(String.valueOf(path));
    }

    NodeDAO nodeDAO = new NodeDAO();
    HashMap<Integer, Node> nodes = nodeDAO.getAll();

    ArrayList<String> pathForFloor = new ArrayList<>();

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
        point.setFill(Color.rgb(246, 189, 56));
        point.setRadius(20);
        nodePane.getChildren().add(point);
        pathForFloor.add(path.get(i));
        point.setOnMouseClicked(
            event -> {
              try {
                nextFloor(nodes.get(Integer.parseInt(path.get(finalI + 1))), path, finalI + 1);
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              }
            });
        break;
      } else {
        nodePane.getChildren().add(point);
        pathForFloor.add(path.get(i));
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
  }

  public void nextFloor(Node node, ArrayList<String> path, int index) throws SQLException {
    NodeDAO nodeDAO = new NodeDAO();
    HashMap<Integer, Node> nodes = nodeDAO.getAll();
    ArrayList<String> pathForFloor = new ArrayList<>();

    String floor = node.getFloor();
    switch (floor) {
      case "L1":
        goToL1(imageViewsList);
        break;
      case "L2":
        goToL2(imageViewsList);
        break;
      case "1 ":
        goToFloor1(imageViewsList);
        break;
      case "2 ":
        goToFloor2(imageViewsList);
        break;
      case "3 ":
        goToFloor3(imageViewsList);
        break;
    }

    nodePane.getChildren().clear();
    for (int i = index; i < path.size(); i++) {
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
        point.setFill(Color.rgb(246, 189, 56));
        point.setRadius(20);
        nodePane.getChildren().add(point);
        pathForFloor.add(path.get(i));
        point.setOnMouseClicked(
            event -> {
              try {
                nextFloor(nodes.get(Integer.parseInt(path.get(finalI + 1))), path, finalI + 1);
              } catch (SQLException ex) {
                throw new RuntimeException(ex);
              }
            });
        break;
      } else {
        nodePane.getChildren().add(point);
        pathForFloor.add(path.get(i));
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

  public void newNodes(int index) throws SQLException {
    NodeDAO nodeDAO = new NodeDAO();

    HashMap<Integer, edu.wpi.teamg.ORMClasses.Node> nodes = nodeDAO.getAll();
    ArrayList<edu.wpi.teamg.ORMClasses.Node> listOfNodes = new ArrayList<>(nodes.values());

    nodePane.getChildren().clear();
    switch (index) {
      case 0:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "L1")) {
            getNodesWFunctionality(listOfNodes, i);
          }
        }
        break;
      case 1:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "L2")) {
            getNodesWFunctionality(listOfNodes, i);
          }
        }
        break;

      case 2:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "1 ")) {
            getNodesWFunctionality(listOfNodes, i);
          }
        }

        break;
      case 3:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "2 ")) {
            getNodesWFunctionality(listOfNodes, i);
          }
        }

        break;
      case 4:
        for (int i = 0; i < listOfNodes.size(); i++) {
          if (Objects.equals(listOfNodes.get(i).getFloor(), "3 ")) {
            getNodesWFunctionality(listOfNodes, i);
          }
        }

        break;
    }
  }

  private void getNodesWFunctionality(ArrayList<Node> listOfNodes, int i) {
    Node currentNode = listOfNodes.get(i);
    Circle point =
        new Circle(
            listOfNodes.get(i).getXcoord(),
            listOfNodes.get(i).getYcoord(),
            10,
            Color.rgb(1, 45, 90));
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
            }
          }
        });
    nodePane.getChildren().add(point);
  }

  public void displayData(Node point) throws SQLException {

    nodePane.getChildren().removeIf(node -> node instanceof TextArea);
    TextArea displayNode = new TextArea();
    NodeDAO nodeDAO = new NodeDAO();
    String floor = point.getFloor();

    HashMap<Integer, String> sn = nodeDAO.getLongNames(floor);

    displayNode.setFont(Font.font(35));

    displayNode.setText("Location: " + sn.get(point.getNodeID()));

    displayNode.setLayoutX(point.getXcoord());
    displayNode.setLayoutY(point.getYcoord());
    displayNode.setPrefWidth(550);
    displayNode.setPrefHeight(100);
    displayNode.setVisible(true);
    displayNode.toFront();
    displayNode.setEditable(false);

    nodePane.getChildren().add(displayNode);
  }

  public HashMap<Integer, String> getHashMapL1LongName(int index) throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();

    if (index == 0) {
      try {
        longNameHashMap = dao.getL1LongNames();
      } catch (SQLException e) {
        System.err.print(e.getErrorCode());
      }
    }
    if (index == 1) {
      try {
        longNameHashMap = dao.getL2LongNames();
      } catch (SQLException e) {
        System.err.print(e.getErrorCode());
      }
    }
    if (index == 2) {
      try {
        longNameHashMap = dao.getF1LongNames();
      } catch (SQLException e) {
        System.err.print(e.getErrorCode());
      }
    }
    if (index == 3) {
      try {
        longNameHashMap = dao.getLongNames("2 ");
      } catch (SQLException e) {
        System.err.print(e.getErrorCode());
      }
    }
    if (index == 4) {
      try {
        longNameHashMap = dao.getLongNames("3 ");
      } catch (SQLException e) {
        System.err.print(e.getErrorCode());
      }
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
    floorEnd.setValue("L1");
  }

  public void listenToMouse() {}

  public void exit() {
    Platform.exit();
  }
}
