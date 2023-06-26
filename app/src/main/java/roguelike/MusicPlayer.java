package roguelike;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import java.net.URL;

public class MusicPlayer {
  AudioInputStream audioInput;
  Clip clip;

  public void playMusic(String musicLoc, boolean loop){
    try {
      URL musicPath = getClass().getResource("audio/"+musicLoc);
      audioInput = AudioSystem.getAudioInputStream(musicPath);
      clip = AudioSystem.getClip();
      clip.open(audioInput);
      if (loop)
        clip.loop(Clip.LOOP_CONTINUOUSLY);
      else
        clip.start();
    } catch (Exception ex){
      ex.printStackTrace();
    }
  }
  public void stop(){
    clip.stop();
  }
}