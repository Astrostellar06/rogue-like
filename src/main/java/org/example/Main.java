package org.example;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Partie pour mes tests
        Player player = new Player("Astrostellar");

        /*String[] map1 = new Room(1).getMap();
        String[] map2 = new Room(2).getMap();
        String[] map3 = new Room(3).getMap();
        String[] map4 = new Room(5).getMap();
        String[] map5 = new Room(6).getMap();
        String[] map6 = new Room(7).getMap();
        String[] map = new String[3*map1.length];
        for (int i = 0; i < map1.length; i++)
            map[i] = map5[i] + map3[i] + map1[i] + map1[i] + map2[i];
        for (int i = 0; i < map1.length; i++)
            map[i + map1.length] = map5[i + map1.length] + map3[i + map1.length] + map6[i] + map4[i];
        for (int i = 0; i < map1.length; i++)
            map[i + 2*map1.length] = map1[i] + map2[i] + map6[i + map1.length] + map4[i + map1.length];*/


        Game app = new Game();
        Item item3 = new ManaItem();

        //Création de la fenêtre
        //L'idée, c'est qu'il n'y a aucune boucle dans la classe Game, tout est géré par des évènements

        //Partie pour afficher mes objets tests
        for (int i = 0 ; i <  3; i++) {
            app.addItem(new AtkItem());
            app.addItem(new DefItem());
        }
        for (int i = 0 ; i < 3 ; i++) {
            app.addEnemy(new Enemy(100, 10, 1));
        }
        app.addItem(item3);
        app.addCoins(10);
    }
}
