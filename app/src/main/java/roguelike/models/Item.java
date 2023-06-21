package roguelike.models;
import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

import roguelike.game.Data;
import roguelike.game.Game;

public abstract class Item {
    String name;
    int x;
    int y;
    Color colorItem;
    boolean isEquipped;

    public Item() {
        boolean empty = true;
        do {
            empty = true;
            this.x = ThreadLocalRandom.current().nextInt(0, 170);
            this.y = ThreadLocalRandom.current().nextInt(0, 85);
            if (Data.items != null) {
                for (int i = 0; i < Data.items.size(); i++) {
                    if (Data.items.get(i).getX() == this.x && Data.items.get(i).getY() == this.y)
                        empty = false;
                }
            }
            if (empty && Data.enemies != null) {
                for (int i = 0; i < Data.enemies.size(); i++) {
                    if (Data.enemies.get(i).getX() == this.x && Data.enemies.get(i).getY() == this.y)
                        empty = false;
                }
            }
            if (empty && Game.charRoom(this.x, this.y) != '.')
                empty = false;
        } while (!empty);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Color getColorItem() {
        return colorItem;
    }

    public void setColorItem(Color colorItem) {
        this.colorItem = colorItem;
    }

    public boolean isEquipped() {
        return isEquipped;
    }

    public void setEquipped(boolean equipped) {
        isEquipped = equipped;
    }
}
