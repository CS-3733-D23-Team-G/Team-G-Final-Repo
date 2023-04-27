package edu.wpi.teamg.ORMClasses;

import java.util.Locale;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class TextToSpeech {
  public static void main(String[] args)
      throws EngineException, AudioException, InterruptedException {
    System.setProperty(
        "freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
    Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");

    Synthesizer synth = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
    synth.allocate();

    synth.resume();
    synth.speakPlainText("This is a test in the speech recognition software", null);
    synth.waitEngineState(Synthesizer.QUEUE_EMPTY);

    synth.deallocate();
  }
}
