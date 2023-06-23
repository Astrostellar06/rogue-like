package roguelike.screens;

import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;

import asciiPanel.AsciiPanel;
import roguelike.App;
import roguelike.Assets;

public class SettingsScreen implements Screen {
  Preferences prefs = Preferences.userNodeForPackage(App.class);
  String font = prefs.get("font", "medium");

  private int selected = 0;
  private boolean inSetting = false;
  private int selectedSetting = 0;
  private int selectedX = 8;
  private int selectedY = 25;
  private boolean modified = false;

  public void displayOutput(AsciiPanel terminal) {
    Assets.display(terminal, Assets.settings, 15, 5, Assets.white);
    terminal.write("Change font size :", 10, 25, Assets.white);
    terminal.write("little", 15, 28, font.equals("little") ? Assets.green : Assets.white);
    terminal.write("medium", 28, 28, font.equals("medium") ? Assets.green : Assets.white);
    terminal.write("big", 41, 28, font.equals("big") ? Assets.green : Assets.white);
    terminal.write("giant", 54, 28, font.equals("giant") ? Assets.green : Assets.white);
    terminal.write(">", !inSetting ? selectedX : selectedX*(selectedSetting+1), !inSetting ? selectedY*(selected+1) : selectedY, Assets.green);
    if(modified){
      terminal.write("Restart the game for changes to be effective", 10, 20, Assets.white);
    }
    terminal.write("Press ESC to go back", 10, 80, Assets.white);
  }

  public void leaveSetting(){
    selectedX = 8;
    selectedY = 25;
    inSetting = false;
  };

  public void enterSetting(){
    selectedX = 13;
    if(selectedSetting == 0){
      selectedY = 28;
    }
    inSetting = true;
  }

  public Screen respondToUserInput(KeyEvent key) {

    if (key.getKeyCode() == KeyEvent.VK_DOWN){
      if(!inSetting){
        if(selected < 0){
          selected += 1;
        } else {
          selected = 0;
        }
      }
      if(selected == 0){
        if(selectedSetting < 3){
          selectedSetting += 1;
        } else {
          selectedSetting = 0;
        }
      }
    }
    if (key.getKeyCode() == KeyEvent.VK_UP){
      if(!inSetting){
        if(selected > 0){
          selected -= 1;
        } else {
          selected = 0;
        }
      }
      if(selected == 0){
        if(selectedSetting > 0){
          selectedSetting -= 1;
        } else {
          selectedSetting = 3;
        }
      }
    }
    if (key.getKeyCode() == KeyEvent.VK_ENTER){
      if(!inSetting){
        if(selected == 0){
          selectedSetting = 0;
          enterSetting();
        }
      } else {
        if(selected == 0){
          if(selectedSetting == 0){
            prefs.put("font", "little");
          }
          if(selectedSetting == 1){
            prefs.put("font", "medium");
          }
          if(selectedSetting == 2){
            prefs.put("font", "big");
          }
          if(selectedSetting == 3){
            prefs.put("font", "giant");
          }
          modified = true;
          leaveSetting();
        }
      }
    }
    if(key.getKeyCode() == KeyEvent.VK_ESCAPE){
      if(inSetting){
        leaveSetting();
      } else {
        return new HomeScreen();
      }
    }
    return this;
  }
}