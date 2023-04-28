package edu.wpi.teamg.ORMClasses;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SpeechToText {
  Configuration config = new Configuration();
  String command = "";
  private boolean stopped = false;
  private long timeLim;
  private ScheduledExecutorService executorService;
  LiveSpeechRecognizer recog;

  public SpeechToText() throws IOException {
    config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
    config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
    config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
    executorService = Executors.newSingleThreadScheduledExecutor();
    recog = new LiveSpeechRecognizer(config);
  }

  public void detectCommand(int duration) throws IOException {
    try {
      this.timeLim = duration;
      recog.startRecognition(true);

      executorService.schedule(this::stop, timeLim, TimeUnit.SECONDS);
      SpeechResult result;
      while (!stopped && (result = recog.getResult()) != null) {
        command = result.getHypothesis();
        System.out.println("The command was: " + command);
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (recog != null) {
        recog.stopRecognition();
      }
      executorService.shutdownNow();
    }
  }

  private void stop() {
    recog.stopRecognition();
    stopped = true;
  }

  public static void main(String[] args) throws IOException {
    Configuration config = new Configuration();

    config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
    config.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
    config.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");

    LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(config);
    recognizer.startRecognition(true);

    System.out.println("Speech Recognition started");

    while (true) {
      SpeechResult result = recognizer.getResult();
      if (result != null) {
        resultListener(result, recognizer);
      }
    }
  }

  private static void resultListener(SpeechResult result, LiveSpeechRecognizer recognizer) {
    if (result != null) {
      String spokenText = result.getHypothesis();
      System.out.println("You said: " + spokenText);
      if (spokenText.equals("stop")) {
        recognizer.stopRecognition();
      }
    }
  }
}
