package roguelike.models;

import roguelike.enums.Rarity;

import java.awt.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Potion extends Item {
    Rarity rarity;
    int hpPlayer, hpMaxPlayer, atkPlayer, defPlayer, magicDefPlayer, critChancePlayer, manaPlayer, manaMaxPlayer, manaRegenPlayer, hpEnemy, atkEnemy, defEnemy, magicDefEnemy, critChanceEnemy;

    public Potion() {
        super();
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

        int potionChoice = ran.nextInt(1, 9);
        switch (rarity) {
            case COMMON:
                this.colorItem = new Color(58, 238, 34);
                switch (potionChoice) {
                    case 1:
                        this.hpPlayer = 5;
                        this.name = "Tiny potion of health";
                        break;
                    case 2:
                        this.manaPlayer = 10;
                        this.name = "Tiny potion of mana";
                        break;
                    case 3:
                        this.hpEnemy = -5;
                        this.name = "Tiny potion of damage";
                        break;
                    case 4:
                        this.defPlayer = 5;
                        this.name = "Tiny potion of defense";
                        break;
                    case 5:
                        this.hpMaxPlayer = 15;
                        this.hpPlayer = 10;
                        this.name = "Tiny elixir of life";
                        break;
                    case 6:
                        this.magicDefPlayer = 5;
                        this.defEnemy = 5;
                        this.critChancePlayer = 5;
                        this.atkEnemy = 5;
                        this.manaPlayer = 5;
                        this.name = "Tiny strange brew";
                        break;
                    case 7:
                        this.manaRegenPlayer = 5;
                        this.manaMaxPlayer = 15;
                        this.magicDefEnemy = -5;
                        this.name = "Tiny magic philtre";
                        break;
                    case 8:
                        this.atkPlayer = 5;
                        this.critChanceEnemy = -5;
                        this.critChancePlayer = 5;
                        this.name = "Tiny mysterious concoction";
                        break;
                }
                break;
            case UNCOMMON:
                this.colorItem = new Color(58, 238, 34);
                switch (potionChoice) {
                    case 1:
                        this.hpPlayer = 10;
                        this.name = "Small potion of health";
                        break;
                    case 2:
                        this.manaPlayer = 10;
                        this.name = "Small potion of mana";
                        break;
                    case 3:
                        this.hpEnemy = -10;
                        this.name = "Small potion of damage";
                        break;
                    case 4:
                        this.defPlayer = 10;
                        this.name = "Small potion of defense";
                        break;
                    case 5:
                        this.hpMaxPlayer = 20;
                        this.hpPlayer = 15;
                        this.name = "Small elixir of life";
                        break;
                    case 6:
                        this.magicDefPlayer = 8;
                        this.defEnemy = 8;
                        this.critChancePlayer = 8;
                        this.atkEnemy = 5;
                        this.manaPlayer = 10;
                        this.name = "Small strange brew";
                        break;
                    case 7:
                        this.manaRegenPlayer = 7;
                        this.manaMaxPlayer = 20;
                        this.magicDefEnemy = -5;
                        this.name = "Small magic philtre";
                        break;
                    case 8:
                        this.atkPlayer = 8;
                        this.critChanceEnemy = -5;
                        this.critChancePlayer = 8;
                        this.name = "Small mysterious concoction";
                        break;
                }
                break;
            case RARE:
                this.colorItem = new Color(9, 71, 157);
                switch (potionChoice) {
                    case 1:
                        this.hpPlayer = 20;
                        this.name = "Medium potion of health";
                        break;
                    case 2:
                        this.manaPlayer = 25;
                        this.name = "Medium potion of mana";
                        break;
                    case 3:
                        this.hpEnemy = -15;
                        this.name = "Medium potion of damage";
                        break;
                    case 4:
                        this.defPlayer = 15;
                        this.name = "Medium potion of defense";
                        break;
                    case 5:
                        this.hpMaxPlayer = 25;
                        this.hpPlayer = 20;
                        this.name = "Medium elixir of life";
                        break;
                    case 6:
                        this.magicDefPlayer = 10;
                        this.defEnemy = 10;
                        this.critChancePlayer = 10;
                        this.atkEnemy = 10;
                        this.manaPlayer = 15;
                        this.name = "Medium strange brew";
                        break;
                    case 7:
                        this.manaRegenPlayer = 10;
                        this.manaMaxPlayer = 25;
                        this.magicDefEnemy = -10;
                        this.name = "Medium magic philtre";
                        break;
                    case 8:
                        this.atkPlayer = 10;
                        this.critChanceEnemy = -10;
                        this.critChancePlayer = 10;
                        this.name = "Medium mysterious concoction";
                        break;
                }
                break;
            case EPIC:
                this.colorItem = new Color(85, 50, 134);
                switch (potionChoice) {
                    case 1:
                        this.hpPlayer = 40;
                        this.name = "Large potion of health";
                        break;
                    case 2:
                        this.manaPlayer = 50;
                        this.name = "Large potion of mana";
                        break;
                    case 3:
                        this.hpEnemy = -25;
                        this.name = "Large potion of damage";
                        break;
                    case 4:
                        this.defPlayer = 20;
                        this.name = "Large potion of defense";
                        break;
                    case 5:
                        this.hpMaxPlayer = 30;
                        this.hpPlayer = 25;
                        this.name = "Large elixir of life";
                        break;
                    case 6:
                        this.magicDefPlayer = 15;
                        this.defEnemy = 15;
                        this.critChancePlayer = 15;
                        this.atkEnemy = 15;
                        this.manaPlayer = 20;
                        this.name = "Large strange brew";
                        break;
                    case 7:
                        this.manaRegenPlayer = 15;
                        this.manaMaxPlayer = 30;
                        this.magicDefEnemy = -15;
                        this.name = "Large magic philtre";
                        break;
                    case 8:
                        this.atkPlayer = 15;
                        this.critChanceEnemy = -15;
                        this.critChancePlayer = 15;
                        this.name = "Large mysterious concoction";
                        break;
                }
                break;
            case LEGENDARY:
                this.colorItem = new Color(255, 201, 0);
                switch (potionChoice) {
                    case 1:
                        this.hpPlayer = 75;
                        this.name = "Huge potion of health";
                        break;
                    case 2:
                        this.manaPlayer = 100;
                        this.name = "Huge potion of mana";
                        break;
                    case 3:
                        this.hpEnemy = -30;
                        this.name = "Huge potion of damage";
                        break;
                    case 4:
                        this.defPlayer = 25;
                        this.name = "Huge potion of defense";
                        break;
                    case 5:
                        this.hpMaxPlayer = 35;
                        this.hpPlayer = 30;
                        this.name = "Huge elixir of life";
                        break;
                    case 6:
                        this.magicDefPlayer = 20;
                        this.defEnemy = 20;
                        this.critChancePlayer = 20;
                        this.atkEnemy = 20;
                        this.manaPlayer = 25;
                        this.name = "Huge strange brew";
                        break;
                    case 7:
                        this.manaRegenPlayer = 20;
                        this.manaMaxPlayer = 35;
                        this.magicDefEnemy = -20;
                        this.name = "Huge magic philtre";
                        break;
                    case 8:
                        this.atkPlayer = 20;
                        this.critChanceEnemy = -20;
                        this.critChancePlayer = 20;
                        this.name = "Huge mysterious concoction";
                        break;
                }
                break;
        }
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
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
}
