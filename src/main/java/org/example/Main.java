package org.example;
//L'idée, c'est qu'il n'y a aucune boucle dans la classe Game, tout est géré par des évènements

public class Main {
    public static void main(String[] args) {
        Game app = new Game();
        Item item3 = new ManaItem();


        for (int i = 0 ; i < 10; i++) {
            app.addItem(new AtkItem());
            app.addItem(new DefItem());
        }
        for (int i = 0 ; i < 5 ; i++) {
            app.addEnemy(new Enemy(1));
            app.addEnemy(new Enemy(2));
            app.addEnemy(new Enemy(3));
            app.addEnemy(new Enemy(4));
        }
        app.addItem(item3);
        app.addCoins(60);
    }
}
