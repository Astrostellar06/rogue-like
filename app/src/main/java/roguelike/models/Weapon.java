package roguelike.models;

import roguelike.enums.Classe;
import roguelike.enums.Rarity;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Weapon extends Item implements Serializable {
    int atk, mana;
    Classe classe;

    Rarity rarity;

    public Weapon() {
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
        int classeChoice = ran.nextInt(3);
        switch (classeChoice) {
            case 0:
                this.classe = Classe.CHEVALIER;
                break;
            case 1:
                this.classe = Classe.ARCHER;
                break;
            case 2:
                this.classe = Classe.MAGE;
                break;
            default:
                break;
        }

        switch (rarity) {
            case COMMON:
                this.colorItem = new Color(58, 238, 34);
                this.atk = 5;
                switch (this.classe) {
                    case CHEVALIER:
                        this.name = "Dagger";
                        break;
                    case ARCHER:
                        this.name = "Wooden bow";
                        break;
                    case MAGE:
                        this.name = "Wooden scepter";
                        break;
                }
                break;
            case UNCOMMON:
                this.colorItem = new Color(37, 100, 16);
                this.atk = 10;
                switch (this.classe) {
                    case CHEVALIER:
                        this.name = "Sword";
                        break;
                    case ARCHER:
                        this.name = "Reinforced bow";
                        break;
                    case MAGE:
                        this.name = "Reinforced scepter";
                        break;
                }
                break;
            case RARE:
                this.colorItem = new Color(9, 71, 157);
                this.atk = 15;
                switch (this.classe) {
                    case CHEVALIER:
                        this.name = "Axe";
                        break;
                    case ARCHER:
                        this.name = "Ornate bow";
                        break;
                    case MAGE:
                        this.name = "Ornate scepter";
                        break;
                }
                break;
            case EPIC:
                this.colorItem = new Color(85, 50, 134);
                this.atk = 20;
                switch (this.classe) {
                    case CHEVALIER:
                        this.name = "Mace";
                        break;
                    case ARCHER:
                        this.name = "Powerful bow";
                        break;
                    case MAGE:
                        this.name = "Magicful Scepter";
                        break;
                }
                break;
            case LEGENDARY:
                this.colorItem = new Color(255, 201, 0);
                this.atk = 25;
                switch (this.classe) {
                    case CHEVALIER:
                        this.name = "Master Sword";
                        break;
                    case ARCHER:
                        this.name = "Hero's Bow";
                        break;
                    case MAGE:
                        this.name = "Gandalf Scepter";
                        break;
                }
                break;
        }
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
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
