package org.example;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy {
    int hp;
    int atk;
    int type;
    int x;
    int y;
    Color color;

    public Enemy(int hp, int atk, int type) {
        this.hp = hp;
        this.atk = atk;
        this.type = type;
        this.color = new Color(ThreadLocalRandom.current().nextInt(0, 255), ThreadLocalRandom.current().nextInt(0, 255), ThreadLocalRandom.current().nextInt(0, 255));
        boolean empty;
        do {
            empty = true;
            this.x = ThreadLocalRandom.current().nextInt(0, 170);
            this.y = ThreadLocalRandom.current().nextInt(0, 85);
            if (Game.items != null) {
                for (int i = 0; i < Game.items.size(); i++) {
                    if (Game.items.get(i).getX() == this.x && Game.items.get(i).getY() == this.y)
                        empty = false;
                }
            }
            if (Game.coins != null) {
                for (int i = 0; i < Game.coins.size(); i++) {
                    if (Game.coins.get(i).getX() == this.x && Game.coins.get(i).getY() == this.y)
                        empty = false;
                }
            }
            if (empty && Game.charRoom(this.x, this.y) != '.') {
                empty = false;
            }
        } while (!empty);
        System.out.println(Game.charRoom(this.x, this.y));

    }

    public int getHp() {
        return hp;
    }

    public int getAtk() {
        return atk;
    }

    public int getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
