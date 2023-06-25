package roguelike.models;

import roguelike.game.Data;
import roguelike.game.Game;
import roguelike.utils.Constants;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class Coin implements Serializable {
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
            if (Constants.data.items != null) {
                for (int i = 0; i < Constants.data.items.size(); i++) {
                    if (Constants.data.items.get(i).getX() == this.x && Constants.data.items.get(i).getY() == this.y)
                        empty = false;
                }
            }
            if (Constants.data.coins != null) {
                for (int i = 0; i < Constants.data.coins.size(); i++) {
                    if (Constants.data.coins.get(i).getX() == this.x && Constants.data.coins.get(i).getY() == this.y)
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
