package roguelike.utils;

import asciiPanel.AsciiPanel;
import roguelike.App;
import roguelike.Assets;
import roguelike.game.Data;
import roguelike.game.Game;
import roguelike.models.Player;

import java.util.prefs.Preferences;

public class Constants {

    public static Game game;
    public static Data data;

    static Preferences prefs = Preferences.userNodeForPackage(App.class);
    static public AsciiPanel terminal;

    public static void getTheme(){
        String theme = prefs.get("theme", "dark");
        if(theme.equals("lollipop")){
            data.font = Assets.primary();
            data.background = Assets.black;
            data.playerColor = Assets.primarySelected();
            data.roomColor = Assets.black;
            data.pathColor = Assets.black;
        }
        if(theme.equals("cyber")){
            data.font = Assets.green;
            data.background = Assets.black;
            data.playerColor = Assets.lightBlue;
            data.roomColor = Assets.black;
            data.pathColor = Assets.black;
        }
    }

    public static void getPlayer(){
        String username = prefs.get("username", "Undefined");
        String type = prefs.get("class", "Warrior");
        data.player = new Player(username, type);
    }

}
