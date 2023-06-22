package roguelike.game;

import roguelike.models.Enemy;
import roguelike.utils.Constants;
import roguelike.utils.MapGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class Moves {

    public static void updateAff() { //Mise à jour de l'affichage après un déplacement + test de la case sur laquelle le joueur se trouve
        if (Constants.game.charRoom(Data.player.getX(), Data.player.getY()) == '.')
            Data.terminal.write(Character.toString(32), Data.player.getX(), Data.player.getY(), Data.font, Data.roomColor);
        else if (Constants.game.charRoom(Data.player.getX(), Data.player.getY()) == '*')
            Data.terminal.write(Character.toString(32), Data.player.getX(), Data.player.getY(), Data.font, Data.pathColor);

        if (Constants.game.charRoom(Data.player.getX()+Data.x, Data.player.getY()+Data.y) == '.')
            Data.terminal.write(Character.toString(1), Data.player.getX() + Data.x, Data.player.getY() + Data.y, Data.playerColor, Data.roomColor);
        else if (Constants.game.charRoom(Data.player.getX()+Data.x, Data.player.getY()+Data.y) == '*')
            Data.terminal.write(Character.toString(1), Data.player.getX() + Data.x, Data.player.getY() + Data.y, Data.playerColor, Data.pathColor);

        for (int i = 0; i < Data.enemies.size(); i++) {
            if (Data.enemies.get(i).getX() == Data.player.getX() + Data.x && Data.enemies.get(i).getY() == Data.player.getY() + Data.y) {
                Data.enemyAttacked = Data.enemies.get(i);
                Constants.game.affAttack(Data.enemyAttacked);
            }
        }

        if (!Data.inAttack) {
            for (int i = 0; i < Data.coins.size(); i++) {
                if (Data.coins.get(i).getX() == Data.player.getX() + Data.x && Data.coins.get(i).getY() == Data.player.getY() + Data.y) {
                    Data.coins.remove(i);
                    Data.player.setCoins(Data.player.getCoins()+1);
                    Constants.game.clearSideAff();
                    Data.terminal.write("+1 coin", 140, 35, Data.font, Data.background);
                    Constants.game.affStats(Data.player);
                }
            }

            moveEnemies();
            if (!Data.inAttack) {
                Constants.game.affAllItems();
                Constants.game.affCoins();
            }
        }

        if (!Data.inAttack) { //Je refais le test, car moveEnemies() peut changer la valeur de Data.inAttack
            for (int i = 0; i < Data.items.size(); i++) {
                if (Data.items.get(i).getX() == Data.player.getX() + Data.x && Data.items.get(i).getY() == Data.player.getY() + Data.y) {
                    for (int j = 0; j < 6; j++) {
                        Data.terminal.write(Character.toString(2 - j % 2), Data.player.getX() + Data.x, Data.player.getY() + Data.y, Data.playerColor, Data.background);
                        Data.terminal.paintImmediately(Data.terminal.getBounds());
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int j = 140; j < 169; j++)
                        Data.terminal.write(Character.toString(32), j, 35, Data.font, Data.background);
                    Data.pickUp = true;
                    Data.itemSelected = i;
                    Constants.game.clearSideAff();
                    Constants.game.affMsg("You found a " + Data.items.get(i).getName(), 140, 35);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Constants.game.affMsg("Do you want to pick it up ?", 140, 39);
                    Constants.game.affMsg("Press [E] to Pick-Up", 140, 41);
                    Constants.game.affMsg("Press [R] to Ignore", 140, 42);
                    //Laisse le jeu dans un état ou les seuls inputs possibles sont E ou R
                }
            }
        }
    }

    public static void moveEnemies() {
        for (Enemy enemy : Data.enemies) {
            int a = 0;
            int b = 0;
            if (!Data.inAttack)
                Data.terminal.write(Character.toString(32), enemy.getX(), enemy.getY(), enemy.getColor(), Data.roomColor);
            //test si le joueur est dans la même salle que l'ennemi + petit random pour que l'ennemi ne soit pas trop prévisible
            if (ThreadLocalRandom.current().nextInt(0, 8) != 0 && MapGenerator.getRoomByXY(Data.listRooms, (Data.player.getX())/17, (Data.player.getY())/17).getX() == MapGenerator.getRoomByXY(Data.listRooms, (enemy.getX())/17, (enemy.getY())/17).getX() && MapGenerator.getRoomByXY(Data.listRooms, (Data.player.getX())/17, (Data.player.getY())/17).getY() == MapGenerator.getRoomByXY(Data.listRooms, (enemy.getX())/17, (enemy.getY())/17).getY()) {
                //petit random pour que l'ennemi se rapproche soit en x soit en y du joueur
                if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
                    if (enemy.getX() < Data.player.getX() && Constants.game.charRoom(enemy.getX()+1, enemy.getY()) == '.')
                        a = 1;
                    else if (enemy.getX() > Data.player.getX() && Constants.game.charRoom(enemy.getX()-1, enemy.getY()) == '.')
                        a = -1;
                    else if (enemy.getY() < Data.player.getY() && Constants.game.charRoom(enemy.getX(), enemy.getY()+1) == '.')
                        b = 1;
                    else if (enemy.getY() > Data.player.getY() && Constants.game.charRoom(enemy.getX(), enemy.getY()-1) == '.')
                        b = -1;
                } else {
                    if (enemy.getY() < Data.player.getY() && Constants.game.charRoom(enemy.getX(), enemy.getY()+1) == '.')
                        b = 1;
                    else if (enemy.getY() > Data.player.getY() && Constants.game.charRoom(enemy.getX(), enemy.getY()-1) == '.')
                        b = -1;
                    else if (enemy.getX() < Data.player.getX() && Constants.game.charRoom(enemy.getX()+1, enemy.getY()) == '.')
                        a = 1;
                    else if (enemy.getX() > Data.player.getX() && Constants.game.charRoom(enemy.getX()-1, enemy.getY()) == '.')
                        a = -1;
                }
            } else {
                int j = ThreadLocalRandom.current().nextInt(1, 5);
                if (j == 1 && Constants.game.charRoom(enemy.getX()+1, enemy.getY()) == '.')
                    a = 1;
                else if (j == 2 && Constants.game.charRoom(enemy.getX()-1, enemy.getY()) == '.')
                    a = -1;
                else if (j == 3 && Constants.game.charRoom(enemy.getX(), enemy.getY()+1) == '.')
                    b = 1;
                else if (j == 4 && Constants.game.charRoom(enemy.getX(), enemy.getY()-1) == '.')
                    b = -1;
            }
            for (int j = 0; j < Data.enemies.size(); j++) {
                if (enemy.getX() + a == Data.enemies.get(j).getX() && enemy.getY() + b == Data.enemies.get(j).getY() && enemy != Data.enemies.get(j)) {
                    a = 0;
                    b = 0;
                }
            }
            enemy.setX(enemy.getX() + a);
            enemy.setY(enemy.getY() + b);
            Data.enemyAttacked = enemy;
            if (enemy.getX() == Data.player.getX() + Data.x && enemy.getY() == Data.player.getY() + Data.y) {
                Constants.game.affAttack(Data.enemyAttacked);
                break;
            }
            if (!Data.inAttack)
                Data.terminal.write(Character.toString(234), enemy.getX(), enemy.getY(), enemy.getColor(), Data.roomColor);
        }
        if (!Data.inAttack) {
            Constants.game.add(Data.terminal);
            Data.terminal.repaint();
        }
    }

}
