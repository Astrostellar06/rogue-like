package roguelike.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import roguelike.Assets;

public class HomeScreen implements Screen {

  private int selected = 0;

  public void displayOutput(AsciiPanel terminal) {
    Assets.display(terminal, Assets.skeleton, 100, 30, Assets.gray);
    //display(terminal, candles, 1, SIZE[1]-candles.length-1, gray);
    Assets.displayCenter(terminal, Assets.title, 5, Assets.green);
    Assets.display(terminal, Assets.selected, 10, 35+10*selected, Assets.white);
    Assets.display(terminal, Assets.newgame, 20, 35, Assets.white);
    Assets.display(terminal, Assets.resumegame, 20, 45, Assets.white);
    Assets.display(terminal, Assets.settings, 20, 55, Assets.white);
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
        return new GameScreen();
      }
    }
    return this;
  }
}