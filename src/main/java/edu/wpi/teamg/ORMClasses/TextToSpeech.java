package edu.wpi.teamg.ORMClasses;

import java.util.Locale;
import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class TextToSpeech {
  String spoken;
  Synthesizer synth = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));

  public TextToSpeech() throws EngineException {}

  public TextToSpeech(String spoken) throws EngineException, AudioException, InterruptedException {
    this.spoken = spoken;
    System.setProperty(
        "freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
    Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
    Synthesizer synth = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
    this.synth = synth;
    this.synth.allocate();
    this.synth.resume();
    this.synth.speakPlainText(spoken, null);
    this.synth.waitEngineState(Synthesizer.QUEUE_EMPTY);
    this.synth.deallocate();
  }

  public void pauseSpeech() throws EngineException, InterruptedException {
    synth.cancelAll();
  }
}
