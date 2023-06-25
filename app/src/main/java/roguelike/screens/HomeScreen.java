package roguelike.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import roguelike.Assets;

public class HomeScreen implements Screen {

  private int selected = 0;
  int homeSprite =(int) Math.round(Math.random()*3);

  public void displayOutput(AsciiPanel terminal) {
    if(homeSprite < 2){
      Assets.display(terminal, homeSprite == 0 ? Assets.skeleton : Assets.reaper, 100, 30, Assets.secondary());
    } else {
      Assets.display(terminal, homeSprite == 2 ? Assets.knight : Assets.dragon, 90, 25, Assets.secondary());
    }
    Assets.displayCenter(terminal, Assets.title, 5, Assets.primarySelected());
    Assets.display(terminal, Assets.selected, 10, 35+10*selected, Assets.primary());
    Assets.display(terminal, Assets.newgame, 20, 35, Assets.primary());
    Assets.display(terminal, Assets.resumegame, 20, 45, Assets.primary());
    Assets.display(terminal, Assets.settings, 20, 55, Assets.primary());
    terminal.write("Matthieu Jacques / Clement Laubertie / Matteo Lo Re", 2, 83);
  }

  public Screen respondToUserInput(KeyEvent key) {
    if (key.getKeyCode() == KeyEvent.VK_DOWN){
      if(selected < 2){
        selected += 1;
      } else {
        selected = 0;
      }
    }
    if (key.getKeyCode() == KeyEvent.VK_UP){
      if(selected > 0){
        selected -= 1;
      } else {
        selected = 2;
      }
    }
    if (key.getKeyCode() == KeyEvent.VK_ENTER){
      if(selected == 0){
        return new LaunchScreen();
      }
      if(selected == 1){
        return new GameScreen(false);
      }
      if(selected == 2){
        return new SettingsScreen();
      }
    }
    return this;
  }
}