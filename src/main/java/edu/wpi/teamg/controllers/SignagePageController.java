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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.SearchableComboBox;

// Touch Ups
// Make NodeInfo Disappear More clean
// L1 Nodes need to be in dropdown from start
// If we have an error all nodes should remain displayed

public class SignagePageController {

  public Group group;
  @FXML MFXButton backToHomeButton;
  @FXML ChoiceBox<String> serviceRequestChoiceBox;
  @FXML MFXButton signagePageButton;
  @FXML MFXButton exitButton;
  @FXML MFXButton goToAdminSign;

  @FXML MFXButton pathFindButton;

  @FXML TextArea results;

  @FXML GesturePane pane;
  @FXML Pane nodePane;

  // Change Floor Maps
  @FXML MFXButton l1;
  @FXML MFXButton l2;
  @FXML MFXButton floor1;

  private ArrayList<ImageView> imageViewsList = new ArrayList<>();

  @FXML SearchableComboBox startLocDrop;
  @FXML SearchableComboBox endLocDrop;

  ObservableList<String> list =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");

  ObservableList<String> locationList;

  DAORepo dao = new DAORepo();

  @FXML
  public void initialize() throws SQLException {
    serviceRequestChoiceBox.setItems(list);
    signagePageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
    backToHomeButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    goToAdminSign.setOnMouseClicked(event -> Navigation.navigate(Screen.ADMIN_SIGNAGE_PAGE));
    exitButton.setOnMouseClicked(event -> exit());
    serviceRequestChoiceBox.setOnAction(event -> loadServiceRequestForm());

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
    ImageView mapView = new ImageView(mapL1);
    ImageView mapViewL2 = new ImageView(mapL2);
    ImageView mapViewFloor1 = new ImageView(mapFloor1);

    group.getChildren().add(mapView);
    group.getChildren().add(mapViewL2);
    group.getChildren().add(mapViewFloor1);

    mapViewL2.setVisible(false);
    mapViewFloor1.setVisible(false);

    mapView.toBack();
    mapView.relocate(0, 0);

    mapViewL2.toBack();
    mapViewL2.relocate(0, 0);

    mapViewFloor1.toBack();
    mapViewFloor1.relocate(0, 0);

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

    // Scaling is currently the issue with the node map

    NodeDAO nodeDAO = new NodeDAO();

    HashMap<Integer, edu.wpi.teamg.ORMClasses.Node> nodes = nodeDAO.getAll();
    ArrayList<edu.wpi.teamg.ORMClasses.Node> listOfNodes = new ArrayList<>(nodes.values());

    for (int i = 0; i < listOfNodes.size(); i++) {
      if (Objects.equals(listOfNodes.get(i).getFloor(), "L1")) {
        getNodesWFunctionality(listOfNodes, i);
      }
    }

    // Adds Nodes To Node Pane and should display them (if you divide by 5 you get them on the page)
    // This indicates there is a scaling problem with the nodePane

    // Rectangle rec = new Rectangle(500, 500, Color.BLACK);
    //  Circle circ = new Circle(10, Color.BLACK);
    // circ.relocate(500, 500);

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
    ArrayList<String> finaNodes = new ArrayList<>();

    String L1StartNodeLongName = (String) startLocDrop.getValue();
    String L1EndNodeLongName = (String) endLocDrop.getValue();

    System.out.println("Start Node LN = " + L1StartNodeLongName);
    System.out.println("End Node LN = " + L1EndNodeLongName);

    int L1StartNodeID = dao.getNodeIDbyLongName(L1StartNodeLongName);
    int L1EndNodeID = dao.getNodeIDbyLongName(L1EndNodeLongName);

    System.out.println("Start NodeID = " + L1StartNodeID);
    System.out.println("End NodeID = " + L1EndNodeID);

    // ArrayList<edu.wpi.teamg.ORMClasses.Node> L1nodeKeys = new ArrayList<>();
    // ArrayList<edu.wpi.teamg.ORMClasses.Node> L1edgeKeys = new ArrayList<>();

    NodeDAO nodeDAO = new NodeDAO();
    EdgeDAO edgeDAO = new EdgeDAO();

    HashMap<Integer, Node> nodeMap = nodeDAO.getAll();
    HashMap<String, Edge> edgeMap = edgeDAO.getAll();

    ArrayList<Node> L1nodes = new ArrayList<>(nodeMap.values());
    ArrayList<Edge> L1edges = new ArrayList<>(edgeMap.values());

    ArrayList<Node> L1NodeFinal = new ArrayList<>();
    ArrayList<Edge> L1EdgeFinal = new ArrayList<>();

    String floor = nodeMap.get(L1StartNodeID).getFloor();
    switch (floor) {
      case "L1":
        goToL1(imageViewsList);
        break;
      case "L2":
        goToL2(imageViewsList);
        break;
      case "1 ":
        goToFloor1(imageViewsList);
    }
    // L1nodes = (ArrayList<edu.wpi.teamg.ORMClasses.Node>) nodeMap.values();

    for (int i = 0; i < L1nodes.size(); i++) {
      if (L1nodes.get(i).getFloor().equals(floor)) {
        L1NodeFinal.add(
            new Node(
                L1nodes.get(i).getNodeID(),
                L1nodes.get(i).getXcoord(),
                L1nodes.get(i).getYcoord(),
                L1nodes.get(i).getFloor(),
                L1nodes.get(i).getBuilding()));
      }
    }

    for (int i = 0; i < L1edges.size(); i++) {
      // For each edge
      // If the start and end node are both on floor 1
      // Add edge to final edge array
      // If only start and end node are on floor 1
      // print out "error"

      if ((nodeMap.get(L1edges.get(i).getStartNode())).getFloor().equals(floor)
          && ((nodeMap.get(L1edges.get(i).getEndNode())).getFloor().equals(floor))) {
        Node currentS = new Node();
        Node currentE = new Node();
        currentS = nodeMap.get(L1edges.get(i).getStartNode());
        currentE = nodeMap.get(L1edges.get(i).getEndNode());
        L1EdgeFinal.add(new Edge(currentS.getNodeID(), currentE.getNodeID()));
      }
    }

    Node[] nodeArray = new Node[L1NodeFinal.size()];
    for (int i = 0; i < L1NodeFinal.size(); i++) {
      nodeArray[i] = L1NodeFinal.get(i);
    }
    Edge[] edgeArray = new Edge[L1EdgeFinal.size()];
    for (int i = 0; i < L1EdgeFinal.size(); i++) {
      edgeArray[i] = L1EdgeFinal.get(i);
    }

    int startNode = 0;
    int endNode = 0;
    for (int i = 0; i < L1NodeFinal.size(); i++) {

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
    HashMap<Integer, edu.wpi.teamg.ORMClasses.Node> nodes = nodeDAO.getAll();
    Edge e = new Edge(1025, 1275);

    // path = 4
    // path = 0,1,2,3
    // Line = 0,0,1,1
    // Line = 1,1,2,2
    // Line = 2,2,3,3

    nodePane.getChildren().clear();
    for (int i = 0; i < path.size(); i++) {
      Circle point =
          new Circle(
              nodes.get(Integer.parseInt(path.get(i))).getXcoord(),
              nodes.get(Integer.parseInt(path.get(i))).getYcoord(),
              10,
              Color.rgb(1, 45, 90));
      nodePane.getChildren().add(point);
    }

    for (int i = 1; i < path.size(); i++) {
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

    HashMap<Integer, String> sn = nodeDAO.getShortName(floor);
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

    return longNameHashMap;
  }

  public void longNameNodes(int index) throws SQLException {
    ArrayList<String> locationNames = new ArrayList<>();
    HashMap<Integer, String> testingLongName = this.getHashMapL1LongName(index);

    testingLongName.forEach(
        (i, m) -> {
          locationNames.add(m);
          // System.out.println("LocationName: " + m);
          //          System.out.println("Employee ID:" + m.getEmpid());
          //          System.out.println("Status:" + m.getStatus());
          //          System.out.println("Location:" + m.getLocation());
          //          System.out.println("Serve By:" + m.getServ_by());
          //          System.out.println();
        });

    Collections.sort(locationNames, String.CASE_INSENSITIVE_ORDER);

    locationList = FXCollections.observableArrayList(locationNames);

    startLocDrop.setItems(locationList);
    endLocDrop.setItems(locationList);
  }

  public void exit() {
    Platform.exit();
  }
}
