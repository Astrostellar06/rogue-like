package org.example;

public class Room {

    int x, y, sx, sy, roomtype;
    String[] room;

    public Room(int x, int y, int sx, int sy, String[] room, int roomtype) {
        this.x = x;
        this.y = y;
        this.sx = sx;
        this.sy = sy;
        this.room = room;
        this.roomtype = roomtype;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSx() {
        return sx;
    }

    public int getSy() {
        return sy;
    }

    public String[] getRoom() {
        return room;
    }

    public void setRoom(String[] room) {
        this.room = room;
    }

    @Override
    public boolean equals(Object o) {
        Room room = (Room) o;
        return room.getX() == this.x && room.getY() == this.y;
    }
}