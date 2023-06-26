package roguelike.screens;

import java.awt.event.KeyEvent;
import java.util.prefs.Preferences;

import asciiPanel.AsciiPanel;
import roguelike.App;
import roguelike.Assets;

public class SettingsScreen implements Screen {
  Preferences prefs = Preferences.userNodeForPackage(App.class);
  String font = prefs.get("font", "medium");
  String theme = prefs.get("theme", "dark");

  private int selected = 0;
  private boolean inSetting = false;
  private int selectedSetting = 0;
  private int selectedX = 8;
  private int selectedY = 25;
  private boolean needRestart = false;

  public void displayOutput(AsciiPanel terminal) {
    Assets.display(terminal, Assets.settings, 15, 5, Assets.primary());

    terminal.write("Change font size :", 10, 25, Assets.primary());
    terminal.write("little", 15, 28, font.equals("little") ? Assets.primarySelected() : Assets.primary());
    terminal.write("medium", 28, 28, font.equals("medium") ? Assets.primarySelected() : Assets.primary());
    terminal.write("big", 41, 28, font.equals("big") ? Assets.primarySelected() : Assets.primary());
    terminal.write("giant", 54, 28, font.equals("giant") ? Assets.primarySelected() : Assets.primary());

    terminal.write("Change theme :", 10, 35, Assets.primary());
    terminal.write("dark", 15, 38, theme.equals("dark") ? Assets.primarySelected() : Assets.primary());
    terminal.write("lollipop", 28, 38, theme.equals("lollipop") ? Assets.primarySelected() : Assets.primary());
    terminal.write("cyber", 41, 38, theme.equals("cyber") ? Assets.primarySelected() : Assets.primary());

    terminal.write(">", !inSetting ? selectedX : selectedX*(selectedSetting+1), !inSetting ? selectedY+(10*selected) : selectedY, Assets.primarySelected());

    if(needRestart){
      terminal.write("Restart the game for changes to be effective", 10, 20, Assets.primary());
    }
    terminal.write("Press [ESC] to go back", 10, 80, Assets.primary());
  }

  public void leaveSetting(){
    selectedX = 8;
    selectedY = 25;
    inSetting = false;
  };

  public void enterSetting(){
    selectedX = 13;
    if(selected == 0) selectedY = 28;
    if(selected == 1) selectedY = 38;
    inSetting = true;
  }

  public Screen respondToUserInput(KeyEvent key) {

    if (key.getKeyCode() == KeyEvent.VK_RIGHT && inSetting) {
      App.sfx.playMusic("select.wav", false);
      int maxSetting = 0;
      if(selected == 0) maxSetting = 3; // font size
      if(selected == 1) maxSetting = 2; // theme
      if(selectedSetting < maxSetting){
        selectedSetting += 1;
      } else {
        selectedSetting = 0;
      }
    }
    if (key.getKeyCode() == KeyEvent.VK_LEFT && inSetting) {
      App.sfx.playMusic("select.wav", false);
      int maxSetting = 0;
      if(selected == 0) maxSetting = 3; // font size
      if(selected == 1) maxSetting = 2; // theme
      if(selectedSetting > 0){
        selectedSetting -= 1;
      } else {
        selectedSetting = maxSetting;
      }
    }
    if (key.getKeyCode() == KeyEvent.VK_UP && !inSetting) {
      App.sfx.playMusic("select.wav", false);
      if(selected < 1){
        selected += 1;
      } else {
        selected = 0;
      }
    }
    if (key.getKeyCode() == KeyEvent.VK_DOWN && !inSetting) {
      App.sfx.playMusic("select.wav", false);
      if(selected > 0){
        selected -= 1;
      } else {
        selected = 1;
      }
    }
    if (key.getKeyCode() == KeyEvent.VK_ENTER){
      App.sfx.playMusic("select.wav", false);
      if(!inSetting){
        selectedSetting = 0;
        enterSetting();
      } else {
        if(selected == 0){
          if(selectedSetting == 0){
            prefs.put("font", "little");
            font = "little";
          }
          if(selectedSetting == 1){
            prefs.put("font", "medium");
            font = "medium";
          }
          if(selectedSetting == 2){
            prefs.put("font", "big");
            font = "big";
          }
          if(selectedSetting == 3){
            prefs.put("font", "giant");
            font = "giant";
          }
          needRestart = true;
          leaveSetting();
        }
        if(selected == 1){
          if(selectedSetting == 0){
            prefs.put("theme", "dark");
            theme = "dark";
          }
          if(selectedSetting == 1){
            prefs.put("theme", "lollipop");
            theme = "lollipop";
          }
          if(selectedSetting == 2){
            prefs.put("theme", "cyber");
            theme = "cyber";
          }
          leaveSetting();
        }
      }
    }
    if(key.getKeyCode() == KeyEvent.VK_ESCAPE){
      App.sfx.playMusic("select.wav", false);
      if(inSetting){
        leaveSetting();
      } else {
        return new HomeScreen();
      }
    }
    return this;
  }
}