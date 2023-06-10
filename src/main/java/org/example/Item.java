package org.example;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Item {
    String name;
    int x;
    int y;
    Color colorItem;

    public Item() {
        this.x = ThreadLocalRandom.current().nextInt(17, 30);
        this.y = ThreadLocalRandom.current().nextInt(17, 30);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
