package edu.wpi.teamg.ORMClasses;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.wpi.teamg.navigation.Navigation;
import edu.wpi.teamg.navigation.Screen;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class SpeechToText {
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

  public void startCommand(){
    recog.startRecognition(true);
  }

  private void checkCommand() {
    switch (command) {
      case "OPEN PATH FINDING":
        Navigation.navigate(Screen.PATHFINDING_PAGE);
        break;
      case "OPEN STATUS FORMS":
        Navigation.navigate(Screen.ADMIN_STATUS_PAGE);
        break;
      case "LOGIN":
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
