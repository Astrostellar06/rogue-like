package roguelike;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import roguelike.screens.HomeScreen;
import roguelike.screens.Screen;

public class App extends JFrame implements KeyListener {
  private static final long serialVersionUID = 1060623638149583738L;

  private AsciiPanel terminal;
  private Screen screen;

  public App(){
    super();
    terminal = new AsciiPanel(170, 85, AsciiFont.DRAKE_10x10);
    add(terminal);
    pack();
    screen = new HomeScreen();
    addKeyListener(this);
    repaint();
  };
  
  public void repaint(){
    terminal.clear();
    screen.displayOutput(terminal);
    super.repaint();
  }

  public void keyPressed(KeyEvent e) {
    screen = screen.respondToUserInput(e);
    repaint();
  }

  public void keyReleased(KeyEvent e) { }

  public void keyTyped(KeyEvent e) { }

  public static void main(String[] args) {
    App app = new App();
    app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    app.setVisible(true);
  }
}