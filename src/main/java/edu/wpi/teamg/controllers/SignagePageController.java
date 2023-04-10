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
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.SearchableComboBox;

public class SignagePageController {

  public Group group;
  @FXML MFXButton backToHomeButton;
  @FXML ChoiceBox<String> serviceRequestChoiceBox;
  @FXML MFXButton signagePageButton;
  @FXML MFXButton exitButton;
  @FXML MFXButton goToAdminSign;

  @FXML MFXButton pathFindButton;

  @FXML MFXTextField startLoc;

  @FXML MFXTextField endLoc;

  @FXML TextArea results;

  @FXML GesturePane pane;
  @FXML Pane nodePane;

  @FXML StackPane stack;

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

    startLoc.getText();
    endLoc.getText();

    Image map = new Image("/edu/wpi/teamg/Images/00_thelowerlevel1.png");
    ImageView mapView = new ImageView(map);

    group.getChildren().add(mapView);
    mapView.toBack();

    mapView.relocate(0, 0);
    nodePane.setLayoutX(0);
    nodePane.setLayoutY(0);
    nodePane.setMinWidth(map.getWidth());
    nodePane.setMinHeight(map.getHeight());
    nodePane.setMaxWidth(map.getWidth());
    nodePane.setMaxHeight(map.getHeight());

    // Scales Map
    pane.setMinScale(.001);
    pane.zoomTo(.000001, new Point2D(2500, 1700));
    pane.zoomTo(.000001, new Point2D(2500, 1700));

    // Scaling is currently the issue with the node map

    NodeDAO nodeDAO = new NodeDAO();

    HashMap<Integer, edu.wpi.teamg.ORMClasses.Node> nodes = nodeDAO.getAll();
    ArrayList<edu.wpi.teamg.ORMClasses.Node> listOfNodes = new ArrayList<>(nodes.values());

    // Adds Nodes To Node Pane and should display them (if you divide by 5 you get them on the page)
    // This indicates there is a scaling problem with the nodePane

    // Rectangle rec = new Rectangle(500, 500, Color.BLACK);
    //  Circle circ = new Circle(10, Color.BLACK);
    // circ.relocate(500, 500);

    ArrayList<String> locationNames = new ArrayList<>();
    HashMap<Integer, String> testingLongName = this.getHashMapL1LongName();

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

    // L1nodes = (ArrayList<edu.wpi.teamg.ORMClasses.Node>) nodeMap.values();

    for (int i = 0; i < L1nodes.size(); i++) {
      if (L1nodes.get(i).getFloor().equals("L1")) {
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

      if ((nodeMap.get(L1edges.get(i).getStartNode())).getFloor().equals("L1")
          && ((nodeMap.get(L1edges.get(i).getEndNode())).getFloor().equals("L1"))) {
        Node currentS = new Node();
        Node currentE = new Node();
        currentS = nodeMap.get(L1edges.get(i).getStartNode());
        currentE = nodeMap.get(L1edges.get(i).getEndNode());
        L1EdgeFinal.add(new Edge(currentS.getNodeID(), currentE.getNodeID()));
      }
    }

    String start = startLoc.getText();
    String end = endLoc.getText();

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

      if (nodeArray[i].getNodeID() == Integer.parseInt(start)) {
        startNode = i;
      }
      if (nodeArray[i].getNodeID() == Integer.parseInt(end)) {
        endNode = i;
      }
    }

    Graph G1 = new Graph(nodeArray, edgeArray);
    int[][] Adj = G1.createWeightedAdj();

    path = G1.aStarAlg(Adj, startNode, endNode);

    setPath(path);
  }

  public void setPath(ArrayList<String> path) throws SQLException {
    results.setText(String.valueOf(path));
    NodeDAO nodeDAO = new NodeDAO();
    HashMap<Integer, edu.wpi.teamg.ORMClasses.Node> nodes = nodeDAO.getAll();

    // path = 4
    // path = 0,1,2,3
    // Line = 0,0,1,1
    // Line = 1,1,2,2
    // Line = 2,2,3,3
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

  public HashMap<Integer, String> getHashMapL1LongName() throws SQLException {

    HashMap<Integer, String> longNameHashMap = new HashMap<Integer, String>();

    try {
      longNameHashMap = dao.getL1LongNames();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return longNameHashMap;
  }

  public void exit() {
    Platform.exit();
  }
}
