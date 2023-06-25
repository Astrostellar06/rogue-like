package roguelike.models;

import roguelike.enums.Classe;
import roguelike.enums.Rarity;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;

public class Shield extends Item implements Serializable {
    int def;

    Rarity rarity;

    public Shield() {
        super();
        this.equipped = false;

        Random ran = new Random();
        int choice = ran.nextInt(1,101);
        if (choice < 4) {
            this.rarity = Rarity.LEGENDARY;
        } else if (choice < 11) {
            this.rarity = Rarity.EPIC;
        } else if (choice < 26) {
            this.rarity = Rarity.RARE;
        } else if (choice < 51) {
            this.rarity = Rarity.UNCOMMON;
        } else {
            this.rarity = Rarity.COMMON;
        }

        switch (rarity) {
            case COMMON:
                this.colorItem = new Color(58, 238, 34);
                this.def = 1;
                this.name = "Wooden Shield";
                break;
            case UNCOMMON:
                this.colorItem = new Color(37, 100, 16);
                this.def = 2;
                this.name = "Iron shield";
                break;
            case RARE:
                this.colorItem = new Color(9, 71, 157);
                this.def = 3;
                this.name = "Rhodium shield";
                break;
            case EPIC:
                this.colorItem = new Color(85, 50, 134);
                this.def = 4;
                this.name = "Platinum shield";
                break;
            case LEGENDARY:
                this.colorItem = new Color(255, 201, 0);
                this.def = 5;
                this.name = "Golden shield";
                break;
        }
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

}