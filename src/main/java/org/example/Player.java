package org.example;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    int level;
    int hp;
    int x;
    int y;
    int mana;
    int atk;
    int dfs;
    int coins;
    ArrayList<Item> inv;
    String name;

    public Player(String name) {
        this.name = name;
        this.level = 1;
        this.hp = 100;
        this.mana = 100;
        this.atk = 1;
        this.dfs = 0;
        this.coins = 0;
        this.inv = new ArrayList<>();
        boolean empty;
        this.x = 10;
        this.y = 10;
        do {
            this.x += 17;
            if (this.x >= 136) {
                this.x = 10;
                this.y += 17;
            }
            empty = true;
            if (empty && Game.charRoom(this.x, this.y) != '.')
                empty = false;
        } while (!empty);
    }

    public void move(int x, int y, Game app) {
        char c = app.charRoom(this.x+x, this.y+y);
        if (c != ' ' && c != '|' && c != '-' && c != '1' && c != '2' && c != '3' && c != '4') {
            app.updateAff();
            this.x += x;
            this.y += y;
        }
    }

    public int getLevel() {
        return level;
    }

    public int getHp() {
        return hp;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMana() {
        return mana;
    }

    public ArrayList<Item> getInv() {
        return inv;
    }

    public String getName() {
        return name;
    }

    public int getCoins() {
        return coins;
    }

    public int getAtk() {
        return atk;
    }

    public int getDfs() {
        return dfs;
    }
}
