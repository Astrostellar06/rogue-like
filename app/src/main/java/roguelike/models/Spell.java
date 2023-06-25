package roguelike.models;

import roguelike.enums.Rarity;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Spell implements Serializable {
    String name;
    int manaCost, hpPlayer, hpMaxPlayer, atkPlayer, defPlayer, magicDefPlayer, critChancePlayer, manaPlayer, manaMaxPlayer, manaRegenPlayer, hpEnemy, atkEnemy, defEnemy, magicDefEnemy, critChanceEnemy;

    public Spell(int spellChoice) {
        super();
        switch (spellChoice) {
            case 1:
                this.hpPlayer = 20;
                this.name = "Spell of healing";
                this.manaCost = 10;
                break;
            case 3:
                this.hpEnemy = -15;
                this.name = "Spell of damage";
                this.manaCost = 10;
                break;
            case 4:
                this.defPlayer = 15;
                this.name = "Spell of defense";
                this.manaCost = 10;
                break;
            case 5:
                this.hpMaxPlayer = 25;
                this.hpPlayer = 20;
                this.name = "Spell of life";
                this.manaCost = 60;
                break;
            case 6:
                this.magicDefPlayer = 10;
                this.defEnemy = 10;
                this.critChancePlayer = 10;
                this.atkEnemy = 10;
                this.manaPlayer = 15;
                this.name = "Strange Spell";
                this.manaCost = 20;
                break;
            case 7:
                this.manaRegenPlayer = 10;
                this.manaMaxPlayer = 25;
                this.magicDefEnemy = -10;
                this.name = "Magic Spell";
                this.manaCost = 25;
                break;
            case 2:
                this.atkPlayer = 10;
                this.critChanceEnemy = -10;
                this.critChancePlayer = 10;
                this.name = "Mysterious Spell";
                this.manaCost = 25;
                break;
        }
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getHpPlayer() {
        return hpPlayer;
    }

    public void setHpPlayer(int hpPlayer) {
        this.hpPlayer = hpPlayer;
    }

    public int getHpMaxPlayer() {
        return hpMaxPlayer;
    }

    public void setHpMaxPlayer(int hpMaxPlayer) {
        this.hpMaxPlayer = hpMaxPlayer;
    }

    public int getAtkPlayer() {
        return atkPlayer;
    }

    public void setAtkPlayer(int atkPlayer) {
        this.atkPlayer = atkPlayer;
    }

    public int getDefPlayer() {
        return defPlayer;
    }

    public void setDefPlayer(int defPlayer) {
        this.defPlayer = defPlayer;
    }

    public int getMagicDefPlayer() {
        return magicDefPlayer;
    }

    public void setMagicDefPlayer(int magicDefPlayer) {
        this.magicDefPlayer = magicDefPlayer;
    }

    public int getCritChancePlayer() {
        return critChancePlayer;
    }

    public void setCritChancePlayer(int critChancePlayer) {
        this.critChancePlayer = critChancePlayer;
    }

    public int getManaPlayer() {
        return manaPlayer;
    }

    public void setManaPlayer(int manaPlayer) {
        this.manaPlayer = manaPlayer;
    }

    public int getManaMaxPlayer() {
        return manaMaxPlayer;
    }

    public void setManaMaxPlayer(int manaMaxPlayer) {
        this.manaMaxPlayer = manaMaxPlayer;
    }

    public int getManaRegenPlayer() {
        return manaRegenPlayer;
    }

    public void setManaRegenPlayer(int manaRegenPlayer) {
        this.manaRegenPlayer = manaRegenPlayer;
    }

    public int getHpEnemy() {
        return hpEnemy;
    }

    public void setHpEnemy(int hpEnemy) {
        this.hpEnemy = hpEnemy;
    }

    public int getAtkEnemy() {
        return atkEnemy;
    }

    public void setAtkEnemy(int atkEnemy) {
        this.atkEnemy = atkEnemy;
    }

    public int getDefEnemy() {
        return defEnemy;
    }

    public void setDefEnemy(int defEnemy) {
        this.defEnemy = defEnemy;
    }

    public int getMagicDefEnemy() {
        return magicDefEnemy;
    }

    public void setMagicDefEnemy(int magicDefEnemy) {
        this.magicDefEnemy = magicDefEnemy;
    }

    public int getCritChanceEnemy() {
        return critChanceEnemy;
    }

    public void setCritChanceEnemy(int critChanceEnemy) {
        this.critChanceEnemy = critChanceEnemy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
