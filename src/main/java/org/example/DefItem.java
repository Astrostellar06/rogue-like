package org.example;

import java.util.concurrent.ThreadLocalRandom;

public class DefItem extends Item{
    private int dfs;
    boolean isEquipped;

    public DefItem() {
        super();
        int randomNum = ThreadLocalRandom.current().nextInt(0, 5);
        colorItem = new java.awt.Color(0, 130, 255);
        this.isEquipped = false;
        switch (randomNum) {
            case 0:
                this.name = "Shield";
                this.dfs = 10;
                break;
            case 1:
                this.name = "Armor";
                this.dfs = 15;
                break;
            case 2:
                this.name = "Helmet";
                this.dfs = 20;
                break;
            case 3:
                this.name = "Gloves";
                this.dfs = 5;
                break;
            case 4:
                this.name = "Boots";
                this.dfs = 25;
                break;
        }
    }

    public boolean getIsEquipped() {
        return isEquipped;
    }

    public void setIsEquipped(boolean isEquipped) {
        this.isEquipped = isEquipped;
    }

    public int getDfs() {
        return dfs;
    }
}
