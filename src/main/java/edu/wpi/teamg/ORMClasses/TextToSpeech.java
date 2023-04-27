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
  public TextToSpeech(String spoken)throws EngineException {
    System.setProperty(
            "freetts.voices", "com.sun.speech.freetts.en.us" + ".cmu_us_kal.KevinVoiceDirectory");
    Central.registerEngineCentral("com.sun.speech.freetts" + ".jsapi.FreeTTSEngineCentral");
    Synthesizer synth = Central.createSynthesizer(new SynthesizerModeDesc(Locale.US));
    synth.allocate();
    this.spoken=spoken;
  }
  public void resumeSpeech() throws AudioException {
    synth.resume();
  }
  public void speakText() throws InterruptedException, EngineException {
    synth.speakPlainText(
            spoken,
            null);
    synth.waitEngineState(Synthesizer.QUEUE_EMPTY);
    synth.deallocate();
  }
  
  public void pauseSpeech(){
    synth.pause();
  }
}
