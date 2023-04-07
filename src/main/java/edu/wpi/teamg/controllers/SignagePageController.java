package edu.wpi.teamg.controllers;

import edu.wpi.teamg.DAOs.EdgeDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import edu.wpi.teamg.pathFinding.Edge;
import edu.wpi.teamg.pathFinding.Graph;
import edu.wpi.teamg.pathFinding.Node;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.kurobako.gesturefx.GesturePane;

public class SignagePageController {

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

  ObservableList<String> list =
      FXCollections.observableArrayList(
          "Conference Room Request Form",
          "Flowers Request Form",
          "Furniture Request Form",
          "Meal Request Form",
          "Office Supplies Request Form");

  @FXML
  public void initialize() {
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
    //    String imgPath = "/00_thelowerlevel1.png";
    //    Image map = new Image(getClass().getResourceAsStream(imgPath));
    //    ImageView image = new ImageView(map);
    Image map = new Image("/edu/wpi/teamg/Images/00_thelowerlevel1.png");
    ImageView mapView = new ImageView(map);
    pane.setContent(mapView);
    // pane.setContent(image);
    // pane.setMaxScale();
    //  pane.setMinScale(.001);
    // pane.zoomTo(.000001, new Point2D(2500, 1700));
    //  pane.zoomTo(.000001, new Point2D(2500, 1700));
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

    HashMap<Integer, edu.wpi.teamg.ORMClasses.Node> nodeMap = nodeDAO.getAll();
    HashMap<String, edu.wpi.teamg.ORMClasses.Edge> edgeMap = edgeDAO.getAll();

    ArrayList<edu.wpi.teamg.ORMClasses.Node> L1nodes = new ArrayList<>(nodeMap.values());
    ArrayList<edu.wpi.teamg.ORMClasses.Edge> L1edges = new ArrayList<>(edgeMap.values());

    ArrayList<Node> L1NodeFinal = new ArrayList<>();
    ArrayList<Edge> L1EdgeFinal = new ArrayList<>();

    // L1nodes = (ArrayList<edu.wpi.teamg.ORMClasses.Node>) nodeMap.values();

    for (int i = 0; i < L1nodes.size(); i++) {
      if (L1nodes.get(i).getFloor().equals("L1")) {
        L1NodeFinal.add(
            new Node(
                Integer.toString(L1nodes.get(i).getNodeID()),
                L1nodes.get(i).getNodeX(),
                L1nodes.get(i).getNodeY(),
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
        edu.wpi.teamg.ORMClasses.Node currentS = new edu.wpi.teamg.ORMClasses.Node();
        edu.wpi.teamg.ORMClasses.Node currentE = new edu.wpi.teamg.ORMClasses.Node();
        currentS = nodeMap.get(L1edges.get(i).getStartNode());
        currentE = nodeMap.get(L1edges.get(i).getEndNode());
        L1EdgeFinal.add(
            new Edge(
                L1edges.get(i).getEdgeID(),
                new Node(
                    (Integer.toString(currentS.getNodeID())),
                    currentS.getNodeX(),
                    currentS.getNodeY(),
                    currentS.getFloor(),
                    currentS.getBuilding()),
                new Node(
                    (Integer.toString(currentE.getNodeID())),
                    currentE.getNodeX(),
                    currentE.getNodeY(),
                    currentE.getFloor(),
                    currentE.getBuilding())));
      }
      /*
      CONDITIONALS FOR CONNECTING FLOORS
      if (!Objects.equals(nodeMap.get(L1edges.get(i).getStartNode()).getFloor(), "L1")
          && (nodeMap.get(L1edges.get(i).getEndNode()).getFloor()).equals("L1")) {
        System.out.println("ERROR1234");
      }
      if ((nodeMap.get(L1edges.get(i).getStartNode()).getFloor()).equals("L1")
          && !Objects.equals(nodeMap.get(L1edges.get(i).getEndNode()).getFloor(), "L1")) {
        System.out.println("ERROR1234");
      }

       */
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

      if (nodeArray[i].getNodeID().equals(start)) {
        startNode = i;
      }
      if (nodeArray[i].getNodeID().equals(end)) {
        endNode = i;
      }
    }

    Graph G1 = new Graph(nodeArray, edgeArray);
    int[][] Adj = G1.createWeightedAdj();

    path = G1.aStarAlg(Adj, startNode, endNode);

    setPath(path);
  }

  public void setPath(ArrayList<String> path) {
    results.setText(String.valueOf(path));
  }

  public void exit() {
    Platform.exit();
  }
}
