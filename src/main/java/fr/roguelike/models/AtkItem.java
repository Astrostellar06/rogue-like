package fr.roguelike.models;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class AtkItem extends Item {
    private int atk;

    public AtkItem() {
        super();
        int randomNum = ThreadLocalRandom.current().nextInt(0, 5);
        colorItem = new Color(255, 115, 0);
        this.isEquipped = false;
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
}
