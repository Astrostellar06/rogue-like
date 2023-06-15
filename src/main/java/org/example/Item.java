package org.example;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Item {
    String name;
    int x;
    int y;
    Color colorItem;

    public Item() {
        boolean empty = true;
        do {
            empty = true;
            this.x = ThreadLocalRandom.current().nextInt(17, 30);
            this.y = ThreadLocalRandom.current().nextInt(17, 30);
            if (Game.items != null) {
                for (int i = 0; i < Game.items.size(); i++) {
                    if (Game.items.get(i).getX() == this.x && Game.items.get(i).getY() == this.y)
                        empty = false;
                }
            }
            if (Game.enemies != null) {
                for (int i = 0; i < Game.enemies.size(); i++) {
                    if (Game.enemies.get(i).getX() == this.x && Game.enemies.get(i).getY() == this.y)
                        empty = false;
                }
            }
        } while (!empty);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public boolean getIsEquipped() {
        return false;
    }

    public void setIsEquipped(boolean isEquipped) {}

    /*
    x = ThreadLocalRandom.current().nextInt(0, 170);
    y = ThreadLocalRandom.current().nextInt(0, 85);

    X = x//17;
    Y = y//17;

    dx = x%17;
    dy = y%17;

    Room room = mapGenerator.getRoomByXY(listRooms, X, Y);
    if (room != null) {
        if (room.getRoom()[dy].charAt(dx) == '.') {
            addItem();
        }
    }
    */
}
