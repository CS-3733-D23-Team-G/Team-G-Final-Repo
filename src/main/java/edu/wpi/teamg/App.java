package edu.wpi.teamg;

import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import java.io.IOException;
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

    Navigation.navigate(Screen.SIGNAGE_PAGE);
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
  }
}
