package roguelike;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.prefs.*;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import roguelike.screens.HomeScreen;
import roguelike.screens.Screen;

public class App extends JFrame implements KeyListener {
  private static final long serialVersionUID = 1060623638149583738L;
  Preferences prefs = Preferences.userNodeForPackage(App.class);
  String font = prefs.get("font", "medium");

  public static MusicPlayer musicPlayer = new MusicPlayer();
  public static MusicPlayer sfx = new MusicPlayer();

  private AsciiPanel terminal;
  private Screen screen;

  public App(){
    super();
    terminal = new AsciiPanel(170, 85, getFont(font));
    add(terminal);
    pack();
    screen = new HomeScreen();
    addKeyListener(this);
    repaint();
    musicPlayer.playMusic("menu.wav", true);
  }
  
  public void repaint(){
    terminal.clear();
    screen.displayOutput(terminal);
    super.repaint();
  }

  public AsciiFont getFont(String font){
    if(font.equals("little")) return AsciiFont.CP437_8x8;
    if(font.equals("medium")) return AsciiFont.CP437_10x10;
    if(font.equals("big")) return AsciiFont.CP437_12x12;
    if(font.equals("giant")) return AsciiFont.CP437_16x16;
    return AsciiFont.CP437_10x10;
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
    app.setAlwaysOnTop(true);
    app.setVisible(true);
    app.requestFocus();
  }
}