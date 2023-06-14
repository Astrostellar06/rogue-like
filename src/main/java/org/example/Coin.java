package org.example;

import java.util.concurrent.ThreadLocalRandom;

public class Coin {
    int x;
    int y;

    public Coin() {
        boolean empty;
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
            if (Game.coins != null) {
                for (int i = 0; i < Game.coins.size(); i++) {
                    if (Game.coins.get(i).getX() == this.x && Game.coins.get(i).getY() == this.y)
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
}
