package roguelike.models;

import roguelike.game.Data;
import roguelike.game.Game;
import roguelike.utils.Constants;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Item implements Serializable {

    int x, y;
    boolean equipped;
    String name;
    Color colorItem;

    public Item(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Item(int x, int y, String name, Color colorItem) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.colorItem = colorItem;
    }

    public Item() {
        boolean empty;
        Random ran = new Random();
        do {
            empty = true;
            this.x = ran.nextInt(0, 170);
            this.y = ran.nextInt(0, 85);
            if (Constants.data.items != null) {
                for (int i = 0; i < Constants.data.items.size(); i++) {
                    if (Constants.data.items.get(i).getX() == this.x && Constants.data.items.get(i).getY() == this.y)
                        empty = false;
                }
            }
            if (empty && Constants.data.enemies != null) {
                for (int i = 0; i < Constants.data.enemies.size(); i++) {
                    if (Constants.data.enemies.get(i).getX() == this.x && Constants.data.enemies.get(i).getY() == this.y)
                        empty = false;
                }
            }
            if (empty && Game.charRoom(this.x, this.y) != '.')
                empty = false;
        } while (!empty);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColorItem() {
        return colorItem;
    }

    public void setColorItem(Color colorItem) {
        this.colorItem = colorItem;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
}
