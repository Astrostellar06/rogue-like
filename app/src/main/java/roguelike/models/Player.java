package roguelike.models;

import java.io.Serializable;
import java.util.ArrayList;

import roguelike.enums.Classe;
import roguelike.game.Game;

public class Player extends Entity implements Serializable {
    int mana, manaMax,manaRegen;
    ArrayList<Item> inv;
    ArrayList<Spell> spells;
    Classe type;

    public Player(String name, String type) {
        this.name = name;
        switch (type) {
            case "KNIGHT":
                this.type = Classe.KNIGHT;
                break;
            case "ARCHER":
                this.type = Classe.ARCHER;
                break;
            case "MAGE":
                this.type = Classe.MAGE;
                break;
        }
        this.level = 1;
        this.hp = 100;
        this.mana = 100;
        this.atk = 1;
        this.def = 0;
        this.coins = 0;
        this.xp = 0;
        this.hpMax = 100;
        this.manaMax = 100;
        this.manaRegen = 10;
        this.magicDef = 0;
        this.critChance = 10;
        this.inv = new ArrayList<>();
        this.spells = new ArrayList<>();
        boolean empty;
        this.x = -7;
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

    public int getCoins() {
        return coins;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getManaMax() {
        return manaMax;
    }

    public void setManaMax(int manaMax) {
        this.manaMax = manaMax;
    }

    public int getManaRegen() {
        return manaRegen;
    }

    public void setManaRegen(int manaRegen) {
        this.manaRegen = manaRegen;
    }

    public ArrayList<Item> getInv() {
        return inv;
    }

    public void setInv(ArrayList<Item> inv) {
        this.inv = inv;
    }

    public ArrayList<Spell> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<Spell> spells) {
        this.spells = spells;
    }

    public Classe getType() {
        return type;
    }

    public void setType(Classe type) {
        this.type = type;
    }
}
