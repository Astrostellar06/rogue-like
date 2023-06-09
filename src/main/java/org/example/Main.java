package org.example;

public class Main {
    public static void main(String[] args) {
        Player player = new Player("Player 1");
        String[] map = new Room(6).getMap();
        Game app = new Game(map, player);
    }
}
