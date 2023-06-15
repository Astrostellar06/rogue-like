package org.example;
//L'idée, c'est qu'il n'y a aucune boucle dans la classe Game, tout est géré par des évènements

public class Main {
    public static void main(String[] args) {
        Game app = new Game();
        Item item3 = new ManaItem();


        for (int i = 0 ; i < 30; i++) {
            app.addItem(new AtkItem());
            app.addItem(new DefItem());
        }
        for (int i = 0 ; i < 20 ; i++) {
            app.addEnemy(new Enemy(100, 10, 1));
        }
        app.addItem(item3);
        app.addCoins(100);
    }
}
