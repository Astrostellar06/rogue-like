package roguelike;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

public class MusicPlayer {
  AudioInputStream audioInput;
  Clip clip;

  public void playMusic(String musicLoc, boolean loop){
    try {
      File musicPath = new File(musicLoc);
      if(musicPath.exists()){ 
        audioInput = AudioSystem.getAudioInputStream(musicPath);
        clip = AudioSystem.getClip();
        clip.open(audioInput);
        if (loop)
          clip.loop(Clip.LOOP_CONTINUOUSLY);
        else
          clip.start();
      } else{
        System.out.println("Couldn't find Music file");
      }
    } catch (Exception ex){
      ex.printStackTrace();
    }
  }
  public void stop(){
    clip.stop();
  }
}