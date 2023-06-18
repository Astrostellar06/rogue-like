package org.example;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Player {
    int x, y, level, xp, hp, hpMax, mana, manaMax, atk, def, manaRegen, magicDef, critChance, coins;
    ArrayList<Item> inv;
    String name;

    public Player(String name) {
        this.name = name;
        this.level = 2;
        this.hp = 100;
        this.mana = 100;
        this.atk = 1;
        this.def = 0;
        this.coins = 0;
        this.xp = 15;
        this.hpMax = 100;
        this.manaMax = 100;
        this.manaRegen = 10;
        this.magicDef = 0;
        this.critChance = 10;
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

    public int getDef() {
        return def;
    }
}
