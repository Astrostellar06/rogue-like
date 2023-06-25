package roguelike.screens;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;

import asciiPanel.AsciiPanel;
import roguelike.Assets;

public class LaunchScreen implements Screen {

  String[][] alphabet = Assets.alphabet;
  String[] name = {"","","","","","","","",};
  int nameLength = 0;
  boolean maxLengthWarning = false;

  public void displayOutput(AsciiPanel terminal) {
    terminal.writeCenter("Type your name", 2, Assets.white);
    Assets.displayCenter(terminal, name, 3, Assets.white);
    if(maxLengthWarning){
      terminal.writeCenter("name can only contains 12 characters", 12, Assets.white);
    }
  }

  public void addLetter(int i){
    String[] letter = alphabet[i];
    for(int j=0; j<name.length; j++){
      if(name[j].length()>0){
        name[j] = name[j].substring(0, name[j].length()-1)+letter[j];
      } else {
        name[j] = name[j]+letter[j];
      }
    };
    nameLength += 1;
  }

  public Screen respondToUserInput(KeyEvent key) {
    if (key.getKeyCode() >= 65 && key.getKeyCode() <= 90){
      if(nameLength > 12-1){ 
        maxLengthWarning = true;
      } else {
        addLetter(key.getKeyCode()-65);
      }
    }
    if(key.getKeyCode() == KeyEvent.VK_BACK_SPACE){
      name = new String[]{"","","","","","","","",};
      nameLength = 0;
      maxLengthWarning = false;
    }
    return this;
  }
}