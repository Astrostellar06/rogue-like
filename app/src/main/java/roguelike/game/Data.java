package roguelike.game;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import asciiPanel.AsciiPanel;
import roguelike.App;
import roguelike.Assets;
import roguelike.models.Coin;
import roguelike.models.Enemy;
import roguelike.models.Item;
import roguelike.models.Player;
import roguelike.models.Room;
import roguelike.utils.MapGenerator;

public class Data implements Serializable {

    public Data() {}


    // Game data
    public final long serialVersionUID = 1060623638149583738L;

    public int x = 0, y = 0, itemSelected = 0, itemInv = 0, attackSelected = 1, spellSelected = 0, numberPotions = 0;
    public Player player;
    public ArrayList<Enemy> enemies;
    public ArrayList<Item> items;
    public ArrayList<Coin> coins;
    public int[] stats;
    public Color font, background, playerColor, roomColor, pathColor;
   public long tempsInactif = 0;
    public ArrayList<Room> listRooms = MapGenerator.generate();


}
