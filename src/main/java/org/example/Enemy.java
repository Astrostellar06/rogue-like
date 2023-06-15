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
        x = ThreadLocalRandom.current().nextInt(11, 30);
        y = ThreadLocalRandom.current().nextInt(11, 18);
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
