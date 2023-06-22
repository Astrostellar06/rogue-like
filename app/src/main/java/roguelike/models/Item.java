package roguelike.models;

import java.awt.*;

public abstract class Item {

    int x, y;
    boolean equipped;
    String name;
    Color colorItem;

    public Item(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public Item(int x, int y, String name, Color colorItem) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.colorItem = colorItem;
    }

    public Item() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColorItem() {
        return colorItem;
    }

    public void setColorItem(Color colorItem) {
        this.colorItem = colorItem;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
}
