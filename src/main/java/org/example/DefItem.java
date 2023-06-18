package org.example;

import java.util.concurrent.ThreadLocalRandom;

public class DefItem extends Item{
    private int def;
    boolean isEquipped;

    public DefItem() {
        super();
        int randomNum = ThreadLocalRandom.current().nextInt(0, 5);
        colorItem = new java.awt.Color(0, 130, 255);
        this.isEquipped = false;
        switch (randomNum) {
            case 0:
                this.name = "Shield";
                this.def = 10;
                break;
            case 1:
                this.name = "Armor";
                this.def = 15;
                break;
            case 2:
                this.name = "Helmet";
                this.def = 20;
                break;
            case 3:
                this.name = "Gloves";
                this.def = 5;
                break;
            case 4:
                this.name = "Boots";
                this.def = 25;
                break;
        }
    }

    public boolean getIsEquipped() {
        return isEquipped;
    }

    public void setIsEquipped(boolean isEquipped) {
        this.isEquipped = isEquipped;
    }

    public int getDef() {
        return def;
    }
}
