package roguelike.models;

import roguelike.enums.Classe;
import roguelike.enums.Rarity;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Weapon extends Item {
    int atk, def, mana;
    Classe classe;

    Rarity rarity;

    public Weapon(String name, int x, int y, int atk, int def, int mana, Classe classe, Rarity rarity) {
        super(x,y, name);
        this.atk = atk;
        this.def = def;
        this.mana = mana;
        this.classe = classe;
        this.rarity = rarity;
        this.equipped = false;

        switch (rarity) {
            case COMMON:
                this.colorItem = new Color(58, 238, 34);
                break;
            case UNCOMMON:
                this.colorItem = new Color(37, 100, 16);
                break;
            case RARE:
                this.colorItem = new Color(9, 71, 157);
                break;
            case EPIC:
                this.colorItem = new Color(85, 50, 134);
                break;
            case LEGENDARY:
                this.colorItem = new Color(255, 201, 0);
                break;
        }
    }

    public Weapon() {
        super();
        int randomNum = ThreadLocalRandom.current().nextInt(0, 5);
        colorItem = new Color(255, 115, 0);
        this.equipped = false;
        switch (randomNum) {
            case 0:
                this.name = "Sword";
                this.atk = 10;
                break;
            case 1:
                this.name = "Axe";
                this.atk = 15;
                break;
            case 2:
                this.name = "Mace";
                this.atk = 20;
                break;
            case 3:
                this.name = "Dagger";
                this.atk = 5;
                break;
            case 4:
                this.name = "Spear";
                this.atk = 25;
                break;
        }
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }

}
