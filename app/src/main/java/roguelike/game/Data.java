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
    Preferences prefs = Preferences.userNodeForPackage(App.class);

    // Game data
    public final long serialVersionUID = 1060623638149583738L;
    public AsciiPanel terminal;
    public int x = 0, y = 0, itemSelected = 0, itemInv = 0, attackSelected = 1, spellSelected = 0, numberPotions = 0;
    public Player player;
    public ArrayList<Enemy> enemies;
    public ArrayList<Item> items;
    public ArrayList<Coin> coins;
    public int[] stats;
    public Color font, background, playerColor, roomColor, pathColor;
    public boolean invOpen = false, justPickedUp = false, pickUp = false, inAttack = false, waitingForAttack = false, waitingForEnemy = false, waitingForReturn = false, over = false, waitingForChoice = false; //état de jeu
    public long tempsInactif = 0;
    public ArrayList<Room> listRooms = MapGenerator.generate();
    public Enemy enemyAttacked = null;

    public void getTheme(){
        String theme = prefs.get("theme", "dark");
        if(theme.equals("dark")){
            font = Assets.green;
            background = Assets.black;
            playerColor = Assets.lightBlue;
            roomColor = Assets.black;
            pathColor = Assets.black;
        }
    }

    public void getPlayer(){
        String username = prefs.get("username", "Undefined");
        String type = prefs.get("class", "Warrior");
        player = new Player(username, type);
    }
}
