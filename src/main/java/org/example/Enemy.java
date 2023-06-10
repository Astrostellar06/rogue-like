package org.example;

public class Enemy {
    int hp;
    int atk;
    int type;
    int x;
    int y;

    public Enemy(int hp, int atk, int type) {
        this.hp = hp;
        this.atk = atk;
        this.type = type;
        x = 15;
        y = 15;
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
