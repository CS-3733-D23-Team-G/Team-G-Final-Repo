package edu.wpi.teamg.ORMClasses;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import java.io.IOException;

public class SpeechToText {
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
