package edu.wpi.teamg;

import edu.wpi.teamg.DAOs.DAORepo;
import edu.wpi.teamg.DAOs.LocationNameDAO;
import edu.wpi.teamg.DAOs.NodeDAO;
import edu.wpi.teamg.ORMClasses.Node;
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

  public static NodeDAO nodeDAO = new NodeDAO();

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

  public static DAORepo daoRepo = new DAORepo();

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

  public static LocationNameDAO locationNameDAO = new LocationNameDAO();

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
      l1Labels = nodeDAO.getSNgivenFloorExceptHall("L1");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
