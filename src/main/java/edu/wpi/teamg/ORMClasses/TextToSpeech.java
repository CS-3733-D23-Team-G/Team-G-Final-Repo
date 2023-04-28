package edu.wpi.teamg.ORMClasses;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import lombok.Getter;
import lombok.Setter;

public class TextToSpeech {
  @Getter @Setter String spoken;
  public final String voice_name = "kevin16";
  Voice voice;

  public TextToSpeech(String spoken) {
    System.setProperty(
        "freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
    this.spoken = spoken;
  }

  public void speak() {
    VoiceManager vm = VoiceManager.getInstance();
    voice = vm.getVoice(voice_name);
    voice.allocate();

    voice.speak(spoken);
  }

  public void pause() {}

  public void resume() {}
}
