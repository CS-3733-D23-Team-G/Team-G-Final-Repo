package edu.wpi.teamg.ORMClasses;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.wpi.teamg.App;
import edu.wpi.teamg.controllers.LoginController;
import edu.wpi.teamg.controllers.PatientTopBannerController;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.Setter;
import org.controlsfx.control.PopOver;

public class SpeechToText {
  PatientTopBannerController topBanner = new PatientTopBannerController();
  static PopOver window = new PopOver();
  Configuration config = new Configuration();
  @Getter @Setter private String command;
  private boolean stopped = false;
  private Timer timer;
  LiveSpeechRecognizer recog;

  public SpeechToText() throws IOException {
    config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
    config.setDictionaryPath("src/main/resources/edu/wpi/teamg/voiceCommandFiles/6500.dic");
    config.setLanguageModelPath("src/main/resources/edu/wpi/teamg/voiceCommandFiles/6500.lm");
    recog = new LiveSpeechRecognizer(config);
    timer = new Timer();
  }

  public void detectCommand() throws IOException {
    System.out.println("recognition started");
    timer.schedule(
        new TimerTask() {
          @Override
          public void run() {
            recog.stopRecognition();
          }
        },
        5000);

    SpeechResult result;
    while ((result = recog.getResult()) != null) {
      command = result.getHypothesis();
      System.out.println("your command is: " + command);
      checkCommand();
      recog.stopRecognition();
    }
    recog.stopRecognition();
  }

  public void startCommand() {
    recog.startRecognition(true);
  }

  private void checkCommand() throws IOException {
    switch (command) {
      case "OPEN PATH FINDING":
        Navigation.navigate(Screen.PATHFINDING_PAGE);
        break;
      case "OPEN STATUS FORMS":
        Navigation.navigate(Screen.ADMIN_STATUS_PAGE);
        break;
      case "LOGIN":
        var loader = new FXMLLoader(App.class.getResource("views/LoginPage.fxml"));
        window.setContentNode(loader.load());
        window.setArrowSize(0);
        window.setTitle("Login Panel");
        window.setHeaderAlwaysVisible(true);
        LoginController controller = loader.getController();
        final Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
        window.show(App.getPrimaryStage(), mouseLocation.getX(), mouseLocation.getY());

        window.setOnHidden(
            new EventHandler<WindowEvent>() {
              @Override
              public void handle(WindowEvent event) {
                topBanner.window.getRoot().setDisable(false);
              }
            });
        break;
      case "LOGOUT":
        break;
      default:
        break;
    }
  }

  private void stop() {
    recog.stopRecognition();
    stopped = true;
  }

  //  public static void main(String[] args) throws IOException {
  //    SpeechToText stt = new SpeechToText();
  //    stt.detectCommand();
  //  }
}
