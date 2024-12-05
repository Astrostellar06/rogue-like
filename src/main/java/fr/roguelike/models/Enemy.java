package fr.roguelike.models;

import fr.roguelike.game.Data;
import fr.roguelike.game.Game;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Entity {
    Color color;
    int type;

    public Enemy(int type) {
        super();
        this.type = type;
        switch (type) {
            case 1:
                this.name = "Goblin";
                this.hp = 10;
                this.hpMax = 10;
                this.atk = 2;
                this.def = 0;
                this.magicDef = 0;
                this.critChance = 10;
                this.xp = 5;
                this.coins = 5;
                this.color = new Color(33, 133, 33);
                break;
            case 2:
                this.name = "Skeleton";
                this.hp = 20;
                this.hpMax = 20;
                this.atk = 5;
                this.def = 0;
                this.magicDef = 0;
                this.critChance = 10;
                this.xp = 10;
                this.coins = 10;
                this.color = new Color(255, 255, 255);
                break;
            case 3:
                this.name = "Orc";
                this.hp = 30;
                this.hpMax = 30;
                this.atk = 10;
                this.def = 0;
                this.magicDef = 0;
                this.critChance = 10;
                this.xp = 15;
                this.coins = 15;
                this.color = new Color(168, 21, 21);
                break;
            case 4:
                this.name = "Dragon";
                this.hp = 50;
                this.hpMax = 50;
                this.atk = 15;
                this.def = 0;
                this.magicDef = 0;
                this.critChance = 10;
                this.xp = 20;
                this.coins = 20;
                this.color = new Color(140, 108, 227);
                break;
        }
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
            if (empty && Game.charRoom(this.x, this.y) != '.') {
                empty = false;
            }
        } while (!empty);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
