package edu.wpi.teamg;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.DAOs.EdgeDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.*;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static BorderPane rootPane;
  @Setter @Getter private static Stage frontStage;

  public static Image mapL1 = new Image("edu/wpi/teamg/Images/00_thelowerlevel1.png");

  public static Image mapL2 = new Image("edu/wpi/teamg/Images/00_thelowerlevel2.png");

  public static Image mapFloor1 = new Image("edu/wpi/teamg/Images/01_thefirstfloor.png");

  public static Image mapFloor2 = new Image("edu/wpi/teamg/Images/02_thesecondfloor.png");

  public static Image mapFloor3 = new Image("edu/wpi/teamg/Images/03_thethirdfloor.png");
  public static DAORepo daoRepo = new DAORepo();
  public static EdgeDAO edgeDao = new EdgeDAO();

  public static HashMap<String, Edge> edgeMap;

  public static Employee employee = new Employee();

  static {
    try {
      edgeMap = edgeDao.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static ArrayList<Edge> listOfEdges = new ArrayList<>(edgeMap.values());

  public static NodeDAO nodeDAO = new NodeDAO();

  public static HashMap<Integer, String> ln;

  static {
    try {
      ln = new HashMap<>(nodeDAO.getAllLongName());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, Node> allNodes;

  static {
    try {
      allNodes = nodeDAO.getAll();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static ArrayList<Node> allNodeList = new ArrayList<>(allNodes.values());

  public static HashMap<Integer, String> sn;

  static {
    try {
      sn = nodeDAO.getShortName("L1");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> snL2;

  static {
    try {
      snL2 = nodeDAO.getShortName("L2");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> sn1;

  static {
    try {
      sn1 = nodeDAO.getShortName("1 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> sn2;

  static {
    try {
      sn2 = nodeDAO.getShortName("2 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> sn3;

  static {
    try {
      sn3 = nodeDAO.getShortName("3 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> l1Labels;

  static {
    try {
      l1Labels = NodeDAO.getSNgivenFloorExceptHall("L1");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> l2Labels;

  static {
    try {
      l2Labels = NodeDAO.getSNgivenFloorExceptHall("L2");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> F1Labels;

  static {
    try {
      F1Labels = NodeDAO.getSNgivenFloorExceptHall("1 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> F2Labels;

  static {
    try {
      F2Labels = NodeDAO.getSNgivenFloorExceptHall("2 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> F3Labels;

  static {
    try {
      F3Labels = NodeDAO.getSNgivenFloorExceptHall("3 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> L1Floor;

  static {
    try {
      L1Floor = daoRepo.getL1LongNames();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> L2Floor;

  static {
    try {
      L2Floor = daoRepo.getL2LongNames();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> Floor1;

  static {
    try {
      Floor1 = daoRepo.getF1LongNames();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> Floor2;

  static {
    try {
      Floor2 = daoRepo.getLongNames("2 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, String> Floor3;

  static {
    try {
      Floor3 = daoRepo.getLongNames("3 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, Request> testingRequest;

  static {
    try {
      testingRequest = getHashMapRequest();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, MealRequest> testingMealHash;

  static {
    try {
      testingMealHash = getHashMapMeal();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, ConferenceRoomRequest> testingConfRoom;

  static {
    try {
      testingConfRoom = getHashConfRoom();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, FlowerRequest> testingFlower;

  static {
    try {
      testingFlower = getHashMapFlowerRequest();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, FurnitureRequest> testingFurns;

  static {
    try {
      testingFurns = getHashFurns();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap<Integer, MaintenanceRequest> testingMaintain;

  static {
    try {
      testingMaintain = getHashMaintain();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    /* primaryStage is generally only used if one of your components require the stage to display */
    App.primaryStage = primaryStage;

    final FXMLLoader loader = new FXMLLoader(App.class.getResource("views/Root.fxml"));
    final BorderPane root = loader.load();

    App.rootPane = root;

    final Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();

    Navigation.navigate(Screen.SIGNAGE_SCREENSAVER_PAGE);
  }

  public static void refresh() throws SQLException {

    nodeDAO = new NodeDAO();

    allNodes = new HashMap<>(nodeDAO.getAll());

    allNodeList = new ArrayList<>(allNodes.values());

    try {
      sn = nodeDAO.getShortName("L1");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      snL2 = nodeDAO.getShortName("L2");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      sn1 = nodeDAO.getShortName("1 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      sn2 = nodeDAO.getShortName("2 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      sn3 = nodeDAO.getShortName("3 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      l1Labels = nodeDAO.getSNgivenFloorExceptHall("L1");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      l2Labels = NodeDAO.getSNgivenFloorExceptHall("L2");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      F1Labels = NodeDAO.getSNgivenFloorExceptHall("1 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      F2Labels = NodeDAO.getSNgivenFloorExceptHall("2 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      F3Labels = NodeDAO.getSNgivenFloorExceptHall("3 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    daoRepo = new DAORepo();

    try {
      L1Floor = daoRepo.getL1LongNames();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      L2Floor = daoRepo.getL2LongNames();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      Floor1 = daoRepo.getF1LongNames();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      Floor2 = daoRepo.getLongNames("2 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      Floor3 = daoRepo.getLongNames("3 ");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    try {
      ln = new HashMap<>(nodeDAO.getAllLongName());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void mealRefresh() {
    try {
      testingRequest = getHashMapRequest();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      testingMealHash = getHashMapMeal();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void confRefresh() {
    try {
      testingRequest = getHashMapRequest();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      testingConfRoom = getHashConfRoom();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void flowerRefresh() {
    try {
      testingRequest = getHashMapRequest();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      testingFlower = getHashMapFlowerRequest();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void fernsRefresh() {
    try {
      testingRequest = getHashMapRequest();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      testingFurns = getHashFurns();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void maintainRefresh() {
    try {
      testingRequest = getHashMapRequest();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    try {
      testingMaintain = getHashMaintain();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static void requestRefresh() {
    try {
      testingRequest = getHashMapRequest();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public static HashMap getHashMapRequest() throws SQLException {

    HashMap<Integer, Request> requestHashMap = new HashMap<Integer, Request>();

    try {
      requestHashMap = daoRepo.getAllRequest();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return requestHashMap;
  }

  public static HashMap getHashMapMeal() throws SQLException {

    HashMap mealRequestHashMap = new HashMap<Integer, MealRequest>();

    try {
      mealRequestHashMap = daoRepo.getAllMealRequest();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return mealRequestHashMap;
  }

  public static HashMap getHashConfRoom() throws SQLException {

    HashMap<Integer, ConferenceRoomRequest> confRoomHash =
        new HashMap<Integer, ConferenceRoomRequest>();

    try {
      confRoomHash = daoRepo.getAllConferenceRequest();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return confRoomHash;
  }

  public static HashMap getHashMapFlowerRequest() throws SQLException {

    HashMap<Integer, FlowerRequest> flowerHashMap = new HashMap<Integer, FlowerRequest>();

    try {
      flowerHashMap = daoRepo.getALLFlowerRequest();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }
    return flowerHashMap;
  }

  public static HashMap getHashFurns() throws SQLException {

    HashMap<Integer, FurnitureRequest> furnsHash = new HashMap<Integer, FurnitureRequest>();

    try {
      furnsHash = daoRepo.getAllFurniture();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return furnsHash;
  }

  public static HashMap getHashMaintain() throws SQLException {

    HashMap<Integer, MaintenanceRequest> maintainHash = new HashMap<Integer, MaintenanceRequest>();

    try {
      maintainHash = daoRepo.getAllMaintenance();
    } catch (SQLException e) {
      System.err.print(e.getErrorCode());
    }

    return maintainHash;
  }

  static int code;
  static int empid;
  static String user;

  static boolean admin;

  public static void setAdmin(boolean admin) {
    App.admin = admin;
  }

  public static boolean getAdmin() {
    return App.admin;
  }

  public static void setCode(int code) {
    App.code = code;
  }

  public static void setEmp(int id) {
    App.empid = id;
  }

  public static int getCode() {
    return App.code;
  }

  public static int getEmp() {
    return App.empid;
  }

  public static void setUser(String username) {
    App.user = username;
  }

  public static String getUser() {
    return App.user;
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
