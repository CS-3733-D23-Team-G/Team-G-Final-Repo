package edu.wpi.teamg.controllers;

import edu.wpi.teamg.App;
import edu.wpi.teamg.DAOs.*;
import edu.wpi.teamg.ORMClasses.*;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.IntegerStringConverter;
import net.kurobako.gesturefx.GesturePane;
import org.controlsfx.control.PopOver;

public class SignageAdminController {

  @FXML Pane nodePane;
  public Group group;
  @FXML GesturePane pane;

  // @FXML MFXButton pathFindButton;
  @FXML Label fileLabel;
  // @FXML MFXTextField startLoc;
  // @FXML MFXTextField endLoc;
  @FXML MFXTextField results;
  //  @FXML GesturePane pane;

  @FXML ChoiceBox<String> importDrop;
  @FXML ChoiceBox<String> exportDrop;

  @FXML Button nodes;
  @FXML Button edges;

  @FXML Button nodeLoc;
  @FXML Button move;

  @FXML TableView<Node> nodeTable;
  @FXML TableView<Edge> edgeTable;
  @FXML TableView<Move> moveTable;
  @FXML TableView<LocationName> nodeLocTable;

  // Nodes
  @FXML TableColumn<Node, Integer> nodeNodeID;
  @FXML TableColumn<Node, Integer> nodeXcoord;
  @FXML TableColumn<Node, Integer> nodeYcoord;
  @FXML TableColumn<Node, String> nodeFloor;
  @FXML TableColumn<Node, String> nodeBuilding;

  // Edges
  @FXML TableColumn<Edge, String> edgeEdgeID;
  @FXML TableColumn<Edge, Integer> edgeStartNode;
  @FXML TableColumn<Edge, Integer> edgeEndNode;

  // Move

  @FXML TableColumn<Move, Integer> moveNodeID;
  @FXML TableColumn<Move, Date> moveDate;
  @FXML TableColumn<Move, String> moveLongName;

  // NodeLoc

  @FXML TableColumn<LocationName, String> locLongName;
  @FXML TableColumn<LocationName, String> locShortName;
  @FXML TableColumn<LocationName, String> locNodeType;

  @FXML MFXButton edit;
  @FXML MFXButton cancel;

  //  @FXML Button disMap;

  //  @FXML Button gf;
  //  @FXML Button l1;
  //  @FXML Button l2;
  //  @FXML Button floor1;
  //  @FXML Button floor2;
  //  @FXML Button floor3;

  //  @FXML MFXButton mapEdit;
  //  @FXML MFXButton mapCancel;

  //  @FXML MFXButton add;

  @FXML MFXButton mapEditorPageBtn;

  private boolean editableMap = false;

  ObservableList<String> importList =
      FXCollections.observableArrayList("Nodes", "Edges", "LocationName", "Moves");

  ObservableList<String> exportList =
      FXCollections.observableArrayList("Nodes", "Edges", "LocationName", "Moves");

  @FXML
  public void initialize() throws SQLException {
    importDrop.setItems(importList);
    exportDrop.setItems(exportList);
    cancel.setOnMouseClicked(event -> cancelTable());
    edit.setOnMouseClicked(event -> editTable());
    //    disMap.setOnMouseClicked(event -> showAdminMap());
    //    mapEdit.setOnMouseClicked(event -> editMap());
    //    mapCancel.setOnMouseClicked(event -> cancelMap());
   // mapEditorPageBtn.setOnMouseClicked(event -> Navigation.navigate(Screen.ADMIN_MAP_EDITOR));
    //    add.setOnMouseClicked(
    //        event -> {
    //          try {
    //            addNode();
    //          } catch (IOException | SQLException e) {
    //            throw new RuntimeException(e);
    //          }
    //        });

    // importButton.setOnAction(event -> fileChooser());

    importDrop.setOnAction(
        event -> {
          try {
            fileChooser();
          } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
          }
        });
    exportDrop.setOnAction(
        event -> {
          try {
            fileExporter();
          } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
          }
        });

    // fileLabel.getText();

    /*pathFindButton.setOnMouseClicked(
       event -> {
         try {
           processAStarAlg();
         } catch (SQLException e) {
           throw new RuntimeException(e);
         }
       });
    */

    nodes.setOnMouseClicked(event -> loadNodeTable());
    edges.setOnMouseClicked(event -> loadEdgeTable());
    move.setOnMouseClicked(event -> loadMoveTable());
    nodeLoc.setOnMouseClicked(event -> loadLocTable());

    ObservableList<Node> nodeList;
    ObservableList<Edge> edgeList;
    ObservableList<Move> moveList;
    ObservableList<LocationName> locList;

    ArrayList<Node> nodes1 = getNodes();
    ArrayList<Edge> edges1 = getEdge();
    ArrayList<Move> move1 = getMove();
    ArrayList<LocationName> loc1 = getLoc();

    nodeList = FXCollections.observableArrayList(nodes1);
    edgeList = FXCollections.observableArrayList(edges1);
    moveList = FXCollections.observableArrayList(move1);
    locList = FXCollections.observableArrayList(loc1);

    nodeTable.setItems(nodeList);
    edgeTable.setItems(edgeList);
    moveTable.setItems(moveList);
    nodeLocTable.setItems(locList);

    nodeNodeID.setCellValueFactory(new PropertyValueFactory<>("NodeID"));
    nodeXcoord.setCellValueFactory(new PropertyValueFactory<>("xcoord"));
    nodeYcoord.setCellValueFactory(new PropertyValueFactory<>("ycoord"));
    nodeFloor.setCellValueFactory(new PropertyValueFactory<>("floor"));
    nodeBuilding.setCellValueFactory(new PropertyValueFactory<>("building"));

    edgeEdgeID.setCellValueFactory(new PropertyValueFactory<>("EdgeID"));
    edgeStartNode.setCellValueFactory(new PropertyValueFactory<>("startNode"));
    edgeEndNode.setCellValueFactory(new PropertyValueFactory<>("endNode"));

    moveNodeID.setCellValueFactory(new PropertyValueFactory<>("NodeID"));
    moveDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
    moveLongName.setCellValueFactory(new PropertyValueFactory<>("LongName"));

    locLongName.setCellValueFactory(new PropertyValueFactory<>("LongName"));
    locShortName.setCellValueFactory(new PropertyValueFactory<>("ShortName"));
    locNodeType.setCellValueFactory(new PropertyValueFactory<>("NodeType"));

    Image mapL1 = new Image("edu/wpi/teamg/Images/00_thelowerlevel1.png");
    Image mapL2 = new Image("edu/wpi/teamg/Images/00_thelowerlevel2.png");
    Image mapFloor1 = new Image("edu/wpi/teamg/Images/01_thefirstfloor.png");
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
    //
    //    l1.setOnMouseClicked(
    //        event -> {
    //          try {
    //            goToL1(imageViewsList);
    //          } catch (SQLException e) {
    //            throw new RuntimeException(e);
    //          }
    //        });
    //    l2.setOnMouseClicked(
    //        event -> {
    //          try {
    //            goToL2(imageViewsList);
    //          } catch (SQLException e) {
    //            throw new RuntimeException(e);
    //          }
    //        });
    //    floor1.setOnMouseClicked(
    //        event -> {
    //          try {
    //            goToFloor1(imageViewsList);
    //          } catch (SQLException e) {
    //            throw new RuntimeException(e);
    //          }
    //        });
    //    floor2.setOnMouseClicked(
    //        event -> {
    //          try {
    //            goToFloor2(imageViewsList);
    //          } catch (SQLException e) {
    //            throw new RuntimeException(e);
    //          }
    //        });
    //    floor3.setOnMouseClicked(
    //        event -> {
    //          try {
    //            goToFloor3(imageViewsList);
    //          } catch (SQLException e) {
    //            throw new RuntimeException(e);
    //          }
    //        });

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

  DAORepo dao = new DAORepo();

  @FXML
  void fileChooser() throws SQLException, IOException {
    switch (importDrop.getValue()) {
      case "Nodes":
        NodeDAO nodeDAO = new NodeDAO();
        chooserHelper(nodeDAO);
        break;
      case "Edges":
        EdgeDAO edgeDAO = new EdgeDAO();
        chooserHelper(edgeDAO);
        break;
      case "LocationName":
        LocationNameDAO locationNameDAO = new LocationNameDAO();
        chooserHelper(locationNameDAO);
        break;
      case "Moves":
        MoveDAO moveDAO = new MoveDAO();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters()
            .add(new FileChooser.ExtensionFilter("Comma Seperated Values", "*.csv"));
        File f = fc.showOpenDialog(null);
        if (f != null) {
          try {
            moveDAO.importCSV(f.getAbsolutePath());
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        }
        break;
      default:
        break;
    }
  }

  @FXML
  void fileExporter() throws SQLException, IOException {
    switch (exportDrop.getValue()) {
      case "Nodes":
        NodeDAO nodeDAO = new NodeDAO();
        nodeDAO.exportCSV();
        break;
      case "Edges":
        EdgeDAO edgeDAO = new EdgeDAO();
        edgeDAO.exportCSV();
        break;
      case "LocationName":
        LocationNameDAO lNameDAO = new LocationNameDAO();
        lNameDAO.exportCSV();
        break;
      case "Moves":
        MoveDAO mDao = new MoveDAO();
        mDao.exportCSV();
      default:
        break;
    }
  }

  public void loadNodeTable() {
    nodeTable.setVisible(true);
    edgeTable.setVisible(false);
    moveTable.setVisible(false);
    nodeLocTable.setVisible(false);
    pane.setVisible(false);
    nodePane.setVisible(false);
    group.setVisible(false);
  }

  public void loadEdgeTable() {
    nodeTable.setVisible(false);
    edgeTable.setVisible(true);
    moveTable.setVisible(false);
    nodeLocTable.setVisible(false);
    pane.setVisible(false);
    nodePane.setVisible(false);
    group.setVisible(false);
  }

  public void loadMoveTable() {
    nodeTable.setVisible(false);
    edgeTable.setVisible(false);
    moveTable.setVisible(true);
    nodeLocTable.setVisible(false);
    pane.setVisible(false);
    nodePane.setVisible(false);
    group.setVisible(false);
  }

  public void loadLocTable() {
    nodeTable.setVisible(false);
    edgeTable.setVisible(false);
    moveTable.setVisible(false);
    nodeLocTable.setVisible(true);
    pane.setVisible(false);
    nodePane.setVisible(false);
    group.setVisible(false);
  }

  public ArrayList<Node> getNodes() throws SQLException {
    HashMap<Integer, Node> nodes = dao.getAllNodes();

    ArrayList<Node> nodesList = new ArrayList<>(nodes.values());

    return nodesList;
  }

  public ArrayList<edu.wpi.teamg.ORMClasses.Edge> getEdge() throws SQLException {

    HashMap<String, Edge> edge = dao.getAllEdges();

    ArrayList<Edge> edgeList = new ArrayList<>(edge.values());

    return edgeList;
  }

  public ArrayList<Move> getMove() throws SQLException {
    List<Move> moveL = dao.getAllMoves();

    ArrayList<Move> moveAl = (ArrayList<Move>) moveL;

    return moveAl;
  }

  public ArrayList<LocationName> getLoc() throws SQLException {

    HashMap<String, LocationName> locNames = dao.getAllLocationNames();

    ArrayList<LocationName> locList = new ArrayList<>(locNames.values());

    return locList;
  }

  /*
  public void setPath(ArrayList<String> path) {
    results.setText(String.valueOf(path));
  }
   */

  public void cancelTable() {
    nodeTable.setEditable(false);
    moveTable.setEditable(false);
    locNodeType.setEditable(false);
    edgeTable.setEditable(false);
  }

  public void editTable() {

    NodeDAO nodeDAO = new NodeDAO();

    // Edit Nodes
    nodeTable.setEditable(true);
    nodeXcoord.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    nodeXcoord.setOnEditCommit(
        event -> {
          Node obj = event.getRowValue();
          obj.setXcoord(event.getNewValue());
          nodeDAO.update(obj, "xcoord", event.getNewValue());
        });

    nodeYcoord.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    nodeYcoord.setOnEditCommit(
        event -> {
          Node obj = event.getRowValue();
          obj.setYcoord(event.getNewValue());
          nodeDAO.update(obj, "ycoord", event.getNewValue());
        });
    nodeFloor.setCellFactory(TextFieldTableCell.forTableColumn());
    nodeFloor.setOnEditCommit(
        event -> {
          Node obj = event.getRowValue();
          obj.setFloor(event.getNewValue());
          nodeDAO.update(obj, "floor", event.getNewValue());
        });
    nodeBuilding.setCellFactory(TextFieldTableCell.forTableColumn());
    nodeBuilding.setOnEditCommit(
        event -> {
          Node obj = event.getRowValue();
          obj.setBuilding(event.getNewValue());
          nodeDAO.update(obj, "building", event.getNewValue());
        });

    // Edge Update
    EdgeDAO edgeDAO = new EdgeDAO();
    edgeTable.setEditable(true);
    edgeStartNode.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    edgeStartNode.setOnEditCommit(
        event -> {
          Edge obj = event.getRowValue();
          String col = event.getTableColumn().getText();
          edgeDAO.update(obj, col, event.getNewValue());
          obj.setStartNode(event.getNewValue());
        });
    edgeEndNode.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    edgeEndNode.setOnEditCommit(
        event -> {
          Edge obj = event.getRowValue();
          String col = event.getTableColumn().getText();
          edgeDAO.update(obj, col, event.getNewValue());
          obj.setEndNode(event.getNewValue());
        });

    MoveDAO moveDAO = new MoveDAO();
    moveTable.setEditable(true);
    moveNodeID.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    moveNodeID.setOnEditCommit(
        event -> {
          Move obj = event.getRowValue();
          String col = event.getTableColumn().getText();
          moveDAO.update(obj, col, event.getNewValue());
          obj.setNodeID(event.getNewValue());
        });

    moveDate.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
    moveDate.setOnEditCommit(
        event -> {
          Move obj = event.getRowValue();
          obj.setDate((java.sql.Date) event.getNewValue());
          // moveDAO.update(obj, "shortname", event.getNewValue());
        });
    moveLongName.setCellFactory(TextFieldTableCell.forTableColumn());
    moveLongName.setOnEditCommit(
        event -> {
          Move obj = event.getRowValue();
          obj.setLongName(event.getNewValue());
          // moveDAO.update(obj, "nodetype", event.getNewValue());
        });

    LocationNameDAO locationNameDAO = new LocationNameDAO();

    nodeLocTable.setEditable(true);
    locLongName.setCellFactory(TextFieldTableCell.forTableColumn());
    locLongName.setOnEditCommit(
        event -> {
          LocationName obj = event.getRowValue();

          locationNameDAO.update(obj, "longname", event.getNewValue());

          obj.setLongName(event.getNewValue());
        });

    locShortName.setCellFactory(TextFieldTableCell.forTableColumn());
    locShortName.setOnEditCommit(
        event -> {
          LocationName obj = event.getRowValue();
          obj.setShortName(event.getNewValue());
          locationNameDAO.update(obj, "shortname", event.getNewValue());
        });
    locNodeType.setCellFactory(TextFieldTableCell.forTableColumn());
    locNodeType.setOnEditCommit(
        event -> {
          LocationName obj = event.getRowValue();
          obj.setNodeType(event.getNewValue());
          locationNameDAO.update(obj, "nodetype", event.getNewValue());
        });
  }

  public void showAdminMap() {
    nodeTable.setVisible(false);
    edgeTable.setVisible(false);
    moveTable.setVisible(false);
    nodeLocTable.setVisible(false);
    pane.setVisible(true);
    nodePane.setVisible(true);
    group.setVisible(true);
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
    point.setOnMouseClicked(
        new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent event) {
            try {
              displayData(currentNode);
            } catch (IOException | SQLException e) {
              throw new RuntimeException(e);
            }
          }
        });
    nodePane.getChildren().add(point);
  }

  public void displayData(Node point) throws IOException, SQLException {

    //    nodePane.getChildren().removeIf(node -> node instanceof TextArea);
    //    nodePane.getChildren().removeIf(node -> node instanceof Button);
    //    TextArea displayNode = new TextArea();
    //    Button exit = new Button();
    //    Label nodeID = new Label();
    //    Label xcoord = new Label();
    //    Label ycoord = new Label();
    //    Label floor = new Label();
    //    Label building = new Label();
    // PopOver displayNodeTest = new PopOver();
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

    //    displayNode.setText(
    //        "NodeID: "
    //            + point.getNodeID()
    //            + "\nXcoord: "
    //            + point.getXcoord()
    //            + "\nYcoord: "
    //            + point.getYcoord()
    //            + "\nFloor: "
    //            + point.getFloor()
    //            + "\nBuilding: "
    //            + point.getBuilding());
    //
    //    displayNode.setFont(Font.font(35));
    //    displayNode.setLayoutX(point.getXcoord());
    //    displayNode.setLayoutY(point.getYcoord());
    //    displayNode.setPrefWidth(300);
    //    displayNode.setPrefHeight(300);
    //    displayNode.setVisible(true);
    //    displayNode.toFront();
    //    exit.setLayoutX(point.getXcoord() + 300);
    //    exit.setPrefWidth(100);
    //    exit.setPrefHeight(100);
    //    exit.setFont(Font.font(25));
    //    exit.setLayoutY(point.getYcoord());
    //    exit.setText("Close");
    //    exit.setVisible(true);
    //    exit.toFront();

    //    if (editableMap) {
    //      displayNode.setEditable(true);
    //    } else {
    //      displayNode.setEditable(false);
    //    }
    //
    //
    //    nodePane.getChildren().add(displayNode);
    //
    //
    //    exit.setOnMouseClicked(event -> remove(displayNode, exit));
  }

  public void remove(TextArea displayNode, Button exit) {
    nodePane.getChildren().remove(displayNode);
    nodePane.getChildren().remove(exit);
  }

  public void editMap() {
    editableMap = true;
  }

  public void cancelMap() {
    editableMap = false;
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

  public void exit() {
    Platform.exit();
  }

  void chooserHelper(
      LocationDAO dao) { // note because of move dao's uniqueness, this won't use that
    FileChooser fc = new FileChooser();
    fc.getExtensionFilters()
        .add(new FileChooser.ExtensionFilter("Comma Seperated Values", "*.csv"));
    File f = fc.showOpenDialog(null);
    if (f != null) {
      // fileLabel.setText("Selected File::" + f.getAbsolutePath());
      try {
        dao.importCSV(f.getAbsolutePath());
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
