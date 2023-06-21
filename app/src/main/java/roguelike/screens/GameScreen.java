package roguelike.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import roguelike.game.Game;

public class GameScreen implements Screen {

  public void displayOutput(AsciiPanel terminal) {
    Game game = new Game(terminal);
  }

  public Screen respondToUserInput(KeyEvent key) {
    return this;
  }
}