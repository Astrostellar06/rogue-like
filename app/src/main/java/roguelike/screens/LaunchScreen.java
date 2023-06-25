package roguelike.screens;

import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;
import asciiPanel.AsciiPanel;
import roguelike.App;
import roguelike.Assets;
import roguelike.Player;

public class LaunchScreen implements Screen {
  public Preferences prefs = Preferences.userNodeForPackage(App.class);

  String[][] alphabet = Assets.alphabet;
  String username = "";
  String[] name = {"","","","","","","","",};
  int nameLength = 0;
  boolean maxLengthWarning = false;
  boolean noNameWarning = false;

  int selected = 0;

  public void displayOutput(AsciiPanel terminal) {
    terminal.writeCenter("Type your name", 2, Assets.primary());
    Assets.displayCenter(terminal, name, 3, Assets.primary());

    if(maxLengthWarning){
      terminal.writeCenter("name can only contains 12 characters", 13, Assets.primary());
    }
    if(noNameWarning){
      terminal.writeCenter("you need a name", 13, Assets.primary());
    }

    Assets.display(terminal, Assets.warriorBg, 35, 25, selected == 0 ? Assets.blue : Assets.primary());
    Assets.display(terminal, Assets.warriorIcon, 30, 35, selected == 0 ? Assets.primarySelected() : Assets.secondary());
    Assets.display(terminal, Assets.warrior, 21, 60, selected == 0 ? Assets.primarySelected() : Assets.secondary());
    Assets.display(terminal, Assets.archerBg, 80, 25, selected == 1 ? Assets.blue : Assets.primary());
    Assets.display(terminal, Assets.archerIcon, 75, 35, selected == 1 ? Assets.primarySelected() : Assets.secondary());
    Assets.display(terminal, Assets.archer, 69, 60, selected == 1 ? Assets.primarySelected() : Assets.secondary());
    Assets.display(terminal, Assets.mageBg, 124, 25, selected == 2 ? Assets.blue : Assets.primary());
    Assets.display(terminal, Assets.mageIcon, 120, 35, selected == 2 ? Assets.primarySelected() : Assets.secondary());
    Assets.display(terminal, Assets.mage, 117,60, selected == 2 ? Assets.primarySelected() : Assets.secondary());

    terminal.write("Press [Enter] to launch the game", 10, 80, Assets.primary());
  }

  public void addLetter(int i){
    String[] letter = alphabet[i];
    for(int j=0; j<name.length; j++){
      if(name[j].length()>0){
        name[j] = name[j].substring(0, name[j].length())+letter[j];
      } else {
        name[j] = name[j]+letter[j];
      }
    };
    nameLength += 1;
    noNameWarning = false;
  }

  public Screen respondToUserInput(KeyEvent key) {
    if (key.getKeyCode() >= 65 && key.getKeyCode() <= 90){
      if(nameLength > 12-1){ 
        maxLengthWarning = true;
      } else {
        username += key.getKeyChar();
        addLetter(key.getKeyCode()-65);
      }
    }
    if(key.getKeyCode() == KeyEvent.VK_BACK_SPACE){
      name = new String[]{"","","","","","","","",};
      nameLength = 0;
      username = "";
      maxLengthWarning = false;
    }
    if (key.getKeyCode() == KeyEvent.VK_LEFT){
      if(selected > 0){
        selected -=1;
      } else {
        selected = 2;
      }
    }
    if (key.getKeyCode() == KeyEvent.VK_RIGHT){
      if(selected < 2){
        selected +=1;
      } else {
        selected = 0;
      }
    }
    if(key.getKeyCode() == KeyEvent.VK_ENTER){
      if(nameLength == 0){
        noNameWarning = true;
      } else {
        prefs.put("username", username);
        prefs.put("class", selected == 0 ? "KNIGHT" : selected == 1 ? "ARCHER" : "MAGE");
        App.player.stop();
        App.player.playMusic("start.wav", false);
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        return new GameScreen(true);
      }
    }
    return this;
  }
}