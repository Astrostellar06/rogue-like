package roguelike.game;

import roguelike.models.Enemy;
import roguelike.utils.Constants;
import roguelike.utils.MapGenerator;

import java.util.concurrent.ThreadLocalRandom;

public class Moves {

    public static void updateAff() { //Mise à jour de l'affichage après un déplacement + test de la case sur laquelle le joueur se trouve
        if (Constants.game.charRoom(Constants.data.player.getX(), Constants.data.player.getY()) == '.')
            Constants.data.terminal.write(Character.toString(32), Constants.data.player.getX(), Constants.data.player.getY(), Constants.data.font, Constants.data.roomColor);
        else if (Constants.game.charRoom(Constants.data.player.getX(), Constants.data.player.getY()) == '*')
            Constants.data.terminal.write(Character.toString(32), Constants.data.player.getX(), Constants.data.player.getY(), Constants.data.font, Constants.data.pathColor);

        if (Constants.game.charRoom(Constants.data.player.getX()+Constants.data.x, Constants.data.player.getY()+Constants.data.y) == '.')
            Constants.data.terminal.write(Character.toString(1), Constants.data.player.getX() + Constants.data.x, Constants.data.player.getY() + Constants.data.y, Constants.data.playerColor, Constants.data.roomColor);
        else if (Constants.game.charRoom(Constants.data.player.getX()+Constants.data.x, Constants.data.player.getY()+Constants.data.y) == '*')
            Constants.data.terminal.write(Character.toString(1), Constants.data.player.getX() + Constants.data.x, Constants.data.player.getY() + Constants.data.y, Constants.data.playerColor, Constants.data.pathColor);

        for (int i = 0; i < Constants.data.enemies.size(); i++) {
            if (Constants.data.enemies.get(i).getX() == Constants.data.player.getX() + Constants.data.x && Constants.data.enemies.get(i).getY() == Constants.data.player.getY() + Constants.data.y) {
                Constants.data.enemyAttacked = Constants.data.enemies.get(i);
                Constants.game.affAttack(Constants.data.enemyAttacked);
            }
        }

        if (!Constants.data.inAttack) {
            for (int i = 0; i < Constants.data.coins.size(); i++) {
                if (Constants.data.coins.get(i).getX() == Constants.data.player.getX() + Constants.data.x && Constants.data.coins.get(i).getY() == Constants.data.player.getY() + Constants.data.y) {
                    Constants.data.coins.remove(i);
                    Constants.data.player.setCoins(Constants.data.player.getCoins()+1);
                    Constants.game.clearSideAff();
                    Constants.data.terminal.write("+1 coin", 140, 35, Constants.data.font, Constants.data.background);
                    Constants.game.affStats(Constants.data.player);
                }
            }

            moveEnemies();
            if (!Constants.data.inAttack) {
                Constants.game.affAllItems();
                Constants.game.affCoins();
            }
        }

        if (!Constants.data.inAttack) { //Je refais le test, car moveEnemies() peut changer la valeur de Constants.data.inAttack
            for (int i = 0; i < Constants.data.items.size(); i++) {
                if (Constants.data.items.get(i).getX() == Constants.data.player.getX() + Constants.data.x && Constants.data.items.get(i).getY() == Constants.data.player.getY() + Constants.data.y) {
                    for (int j = 0; j < 6; j++) {
                        Constants.data.terminal.write(Character.toString(2 - j % 2), Constants.data.player.getX() + Constants.data.x, Constants.data.player.getY() + Constants.data.y, Constants.data.playerColor, Constants.data.background);
                        Constants.data.terminal.paintImmediately(Constants.data.terminal.getBounds());
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int j = 140; j < 169; j++)
                        Constants.data.terminal.write(Character.toString(32), j, 35, Constants.data.font, Constants.data.background);
                    Constants.data.pickUp = true;
                    Constants.data.itemSelected = i;
                    Constants.game.clearSideAff();
                    Constants.game.affMsg("You found a " + Constants.data.items.get(i).getName(), 140, 35);
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
        for (Enemy enemy : Constants.data.enemies) {
            int a = 0;
            int b = 0;
            if (!Constants.data.inAttack)
                Constants.data.terminal.write(Character.toString(32), enemy.getX(), enemy.getY(), enemy.getColor(), Constants.data.roomColor);
            //test si le joueur est dans la même salle que l'ennemi + petit random pour que l'ennemi ne soit pas trop prévisible
            if (ThreadLocalRandom.current().nextInt(0, 8) != 0 && MapGenerator.getRoomByXY(Constants.data.listRooms, (Constants.data.player.getX())/17, (Constants.data.player.getY())/17).getX() == MapGenerator.getRoomByXY(Constants.data.listRooms, (enemy.getX())/17, (enemy.getY())/17).getX() && MapGenerator.getRoomByXY(Constants.data.listRooms, (Constants.data.player.getX())/17, (Constants.data.player.getY())/17).getY() == MapGenerator.getRoomByXY(Constants.data.listRooms, (enemy.getX())/17, (enemy.getY())/17).getY()) {
                //petit random pour que l'ennemi se rapproche soit en x soit en y du joueur
                if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
                    if (enemy.getX() < Constants.data.player.getX() && Constants.game.charRoom(enemy.getX()+1, enemy.getY()) == '.')
                        a = 1;
                    else if (enemy.getX() > Constants.data.player.getX() && Constants.game.charRoom(enemy.getX()-1, enemy.getY()) == '.')
                        a = -1;
                    else if (enemy.getY() < Constants.data.player.getY() && Constants.game.charRoom(enemy.getX(), enemy.getY()+1) == '.')
                        b = 1;
                    else if (enemy.getY() > Constants.data.player.getY() && Constants.game.charRoom(enemy.getX(), enemy.getY()-1) == '.')
                        b = -1;
                } else {
                    if (enemy.getY() < Constants.data.player.getY() && Constants.game.charRoom(enemy.getX(), enemy.getY()+1) == '.')
                        b = 1;
                    else if (enemy.getY() > Constants.data.player.getY() && Constants.game.charRoom(enemy.getX(), enemy.getY()-1) == '.')
                        b = -1;
                    else if (enemy.getX() < Constants.data.player.getX() && Constants.game.charRoom(enemy.getX()+1, enemy.getY()) == '.')
                        a = 1;
                    else if (enemy.getX() > Constants.data.player.getX() && Constants.game.charRoom(enemy.getX()-1, enemy.getY()) == '.')
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
            for (int j = 0; j < Constants.data.enemies.size(); j++) {
                if (enemy.getX() + a == Constants.data.enemies.get(j).getX() && enemy.getY() + b == Constants.data.enemies.get(j).getY() && enemy != Constants.data.enemies.get(j)) {
                    a = 0;
                    b = 0;
                }
            }
            enemy.setX(enemy.getX() + a);
            enemy.setY(enemy.getY() + b);
            Constants.data.enemyAttacked = enemy;
            if (enemy.getX() == Constants.data.player.getX() + Constants.data.x && enemy.getY() == Constants.data.player.getY() + Constants.data.y) {
                Constants.game.affAttack(Constants.data.enemyAttacked);
                break;
            }
            if (!Constants.data.inAttack)
                Constants.data.terminal.write(Character.toString(234), enemy.getX(), enemy.getY(), enemy.getColor(), Constants.data.roomColor);
        }
        if (!Constants.data.inAttack) {
            Constants.game.add(Constants.data.terminal);
            Constants.data.terminal.repaint();
        }
    }

}
