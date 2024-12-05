package fr.roguelike.models;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Potion extends Item {
    private int manaCost;

    public Potion() {
        super();
        colorItem = new Color(100, 0, 255);
        int randomNum = ThreadLocalRandom.current().nextInt(0, 5);
        switch (randomNum) {
            case 0:
                this.name = "Mana Potion";
                this.manaCost = 10;
                break;
            case 1:
                this.name = "Mana Elixir";
                this.manaCost = 15;
                break;
            case 2:
                this.name = "Mana Flask";
                this.manaCost = 20;
                break;
            case 3:
                this.name = "Mana Vial";
                this.manaCost = 5;
                break;
            case 4:
                this.name = "Mana Bottle";
                this.manaCost = 25;
                break;
        }
    }
}
