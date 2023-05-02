package edu.wpi.teamg.controllers;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.wpi.teamg.App;
import edu.wpi.teamg.Main;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.WindowEvent;
import org.controlsfx.control.PopOver;

public class SpeechPopController {

  @FXML MFXButton speakButton;

  String command;
  LiveSpeechRecognizer recog;
  static PopOver window = new PopOver();

  PopOver hidingWindow = new PopOver();
  PatientTopBannerController topBanner = new PatientTopBannerController();

  public void initialize() {
    speakButton.setOnMouseClicked(
        event -> {
          try {
            startListening();
          } catch (IOException e) {
            throw new RuntimeException(e);
          }
        });
  }

  private void startListening() throws IOException {
    Configuration config = new Configuration();
    Timer timer = new Timer();
    config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
    // "src/main/resources/edu/wpi/teamg/voiceCommandFiles/6500.dic"
    config.setDictionaryPath(
        Objects.requireNonNull(Main.class.getResource("voiceCommandFiles/6500.dic"))
            .toExternalForm());
    config.setLanguageModelPath(
        Objects.requireNonNull(Main.class.getResource("voiceCommandFiles/6500.lm"))
            .toExternalForm());

    recog = new LiveSpeechRecognizer(config);

    recog.startRecognition(true);

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
      System.out.println("The command was: " + command);
      checkCommand();
      recog.stopRecognition();
    }
    recog.stopRecognition();
  }

  private void stopRecognition() {
    recog.stopRecognition();
  }

  private void checkCommand() throws IOException {
    switch (command) {
      case "OPEN PATH FINDING":
        Navigation.navigate(Screen.PATHFINDING_PAGE);
        break;
      case "OPEN STATUS FORM":
        Navigation.navigate(Screen.ADMIN_STATUS_PAGE);
        break;
      case "LOGIN":
        hidingWindow.hide();
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
        Navigation.navigate(Screen.SIGNAGE_SCREENSAVER_PAGE);
        App.employee.setIs_admin(false);
        break;
      default:
        break;
    }
  }

  public void passWindow(PopOver listenPop) {
    hidingWindow = listenPop;
  }
}
