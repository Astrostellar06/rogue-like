package roguelike.models;

import roguelike.game.Data;
import roguelike.game.Game;

import java.util.concurrent.ThreadLocalRandom;

public class Coin {
    int x,y,value;

    public Coin(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public Coin() {
        boolean empty;
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
            if (Data.coins != null) {
                for (int i = 0; i < Data.coins.size(); i++) {
                    if (Data.coins.get(i).getX() == this.x && Data.coins.get(i).getY() == this.y)
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
