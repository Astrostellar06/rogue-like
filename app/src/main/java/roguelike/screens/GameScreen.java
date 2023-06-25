package roguelike.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;
import roguelike.game.Game;
import roguelike.utils.Constants;

public class GameScreen implements Screen {

  boolean newGame;

  public GameScreen(boolean newGame){
    this.newGame = newGame;
  }

  public void displayOutput(AsciiPanel terminal) {
    Constants.game = new Game(terminal, newGame);
  }

  public Screen respondToUserInput(KeyEvent key) {
    return this;
  }
}