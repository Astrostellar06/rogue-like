package roguelike.game;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import asciiPanel.AsciiPanel;
import roguelike.models.Coin;
import roguelike.models.Enemy;
import roguelike.models.Item;
import roguelike.models.Player;
import roguelike.models.Room;
import roguelike.utils.MapGenerator;

public class Data implements Serializable {

    // Game data
    public static final long serialVersionUID = 1060623638149583738L;
    public static AsciiPanel terminal;
    public static int x = 0, y = 0, itemSelected = 0, itemInv = 0, attackSelected = 1;
    public static Player player;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<Item> items;
    public static ArrayList<Coin> coins;
    public static Color font, background, playerColor, roomColor, pathColor;
    public static boolean invOpen = false, justPickedUp = false, pickUp = false, inAttack = false, waitingForAttack = false, waitingForEnemy = false, waitingForReturn = false;; //état de jeu
    public static long tempsInactif = 0;
    public static ArrayList<Room> listRooms = MapGenerator.generate();
    public static String[] arrow = {" __   ",
            " \\ \\  ",
            "  \\ \\ ",
            "   > >",
            "  / / ",
            " /_/  "};
    public static Enemy enemyAttacked = null;
}
