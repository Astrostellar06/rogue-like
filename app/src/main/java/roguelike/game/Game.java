package roguelike.game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;
import roguelike.models.AtkItem;
import roguelike.models.Coin;
import roguelike.models.DefItem;
import roguelike.models.Enemy;
import roguelike.models.Entity;
import roguelike.models.Item;
import roguelike.models.Player;
import roguelike.models.Potion;
import roguelike.models.Room;
import roguelike.utils.MapGenerator;

public class Game extends JFrame implements KeyListener {

    public Game(AsciiPanel terminal) { //Création du jeu
        super(); //Utilisation de JFrame et de AsciiPanel
        Data.player = new Player("Astrostellar");
        Data.enemies = new ArrayList<>();
        Data.items = new ArrayList<>();
        Data.coins = new ArrayList<>();
        Data.terminal = terminal; //Taille de la fenêtre + police
        addKeyListener(this); //Ajout de l'écouteur de touches
        Data.font = new Color(17, 255, 0);
        Data.background = new Color(0, 0, 0);
        Data.playerColor = new Color(66, 248, 242);
        Data.roomColor = new Color(0, 0, 0);
        Data.pathColor = new Color(0, 0, 0);

        for (int i = 0 ; i < 10; i++) {
            addItem(new AtkItem());
            addItem(new DefItem());
            addItem(new Potion());
        }
        for (int i = 0 ; i < 5 ; i++) {
            addEnemy(new Enemy(1));
            addEnemy(new Enemy(2));
            addEnemy(new Enemy(3));
            addEnemy(new Enemy(4));
        }

        addCoins(60);
        aff();
    }

    public void affRooms(Room room) {
        for (int cy = 0; cy < room.getRoom().length; cy++) {
            char[] line = room.getRoom()[cy].toCharArray();
            for (int cx = 0; cx < line.length; cx++) {
                if (line[cx] == '|')
                    Data.terminal.write(Character.toString(179), room.getX() * 17 + cx, room.getY() * 17 + cy, Data.font, Data.background);
                else if (line[cx] == '-')
                    Data.terminal.write(Character.toString(196), room.getX() * 17 + cx, room.getY() * 17 + cy, Data.font, Data.background);
                else if (line[cx] == '1')
                    Data.terminal.write(Character.toString(218), room.getX() * 17 + cx, room.getY() * 17 + cy, Data.font, Data.background);
                else if (line[cx] == '2')
                    Data.terminal.write(Character.toString(191), room.getX() * 17 + cx, room.getY() * 17 + cy, Data.font, Data.background);
                else if (line[cx] == '3')
                    Data.terminal.write(Character.toString(192), room.getX() * 17 + cx, room.getY() * 17 + cy, Data.font, Data.background);
                else if (line[cx] == '4')
                    Data.terminal.write(Character.toString(217), room.getX() * 17 + cx, room.getY() * 17 + cy, Data.font, Data.background);
                else if (line[cx] == '.')
                    Data.terminal.write(Character.toString(32), room.getX() * 17 + cx, room.getY() * 17 + cy, Data.font, Data.roomColor);
                else if (line[cx] == '*')
                    Data.terminal.write(Character.toString(32), room.getX() * 17 + cx, room.getY() * 17 + cy, Data.font, Data.pathColor);
                else
                    Data.terminal.write(Character.toString(32), room.getX() * 17 + cx, room.getY() * 17 + cy, Data.font, Data.background);
            }
        }
    }

    public void aff() { //Affichage de la fenêtre
        for (int i = 0; i < 85; i++) {
            for (int j = 0; j < 170; j++) {
                Data.terminal.write(Character.toString(32), j, i, Data.font, Data.background);
            }
        }


        for (Room room : Data.listRooms) {
            affRooms(room);
        }

        Data.terminal.write(Character.toString(1), Data.player.getX(), Data.player.getY(), Data.playerColor, Data.roomColor);

        for (Enemy enemy : Data.enemies) {
            affEnemy(enemy);
        }

        for (Item item : Data.items) {
            affItem(item);
        }

        affCoins();

        //déco
        for (int i = 1; i < 84; i++) {
            Data.terminal.write(Character.toString(186), 137, i, Data.font, Data.background);
            Data.terminal.write(Character.toString(179), 0, i, Data.font, Data.background);
            Data.terminal.write(Character.toString(179), 169, i, Data.font, Data.background);
        }
        for (int i = 1; i < 169; i++) {
            Data.terminal.write(Character.toString(196), i, 84, Data.font, Data.background);
            Data.terminal.write(Character.toString(196), i, 0, Data.font, Data.background);
        }
        Data.terminal.write(Character.toString(210), 137, 0, Data.font, Data.background);
        Data.terminal.write(Character.toString(208), 137, 84, Data.font, Data.background);

        Data.terminal.write(Character.toString(218), 1, 0, Data.font, Data.background);
        Data.terminal.write(Character.toString(218), 0, 1, Data.font, Data.background);
        Data.terminal.write(Character.toString(217), 1, 1, Data.font, Data.background);

        Data.terminal.write(Character.toString(191), 168, 0, Data.font, Data.background);
        Data.terminal.write(Character.toString(191), 169, 1, Data.font, Data.background);
        Data.terminal.write(Character.toString(192), 168, 1, Data.font, Data.background);

        Data.terminal.write(Character.toString(192), 1, 84, Data.font, Data.background);
        Data.terminal.write(Character.toString(192), 0, 83, Data.font, Data.background);
        Data.terminal.write(Character.toString(191), 1, 83, Data.font, Data.background);

        Data.terminal.write(Character.toString(217), 168, 84, Data.font, Data.background);
        Data.terminal.write(Character.toString(217), 169, 83, Data.font, Data.background);
        Data.terminal.write(Character.toString(218), 168, 83, Data.font, Data.background);

        affStats(Data.player);

        for (int i = 0; i < 31; i++)
            Data.terminal.write(Character.toString(196), 138 + i, 31, Data.font, Data.background);
        Data.terminal.write(Character.toString(199), 137, 31, Data.font, Data.background);
        Data.terminal.write(Character.toString(180), 169, 31, Data.font, Data.background);

        add(Data.terminal);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
        requestFocus();
    }

    public void affStats(Entity target) {
        int b = 1;
        Entity entity;
        int dx = 140;
        int dy = 0;
        if (Data.inAttack) {
            b = 2;
            dy = 60;
            dx = 120;
        }
        for (int a = 0 ; a < b ; a++) {
            if (a == 0) {
                entity = Data.player;
            } else {
                entity = target;
                dx = 145;
                dy = 60;
            }
            if (entity instanceof Player) {
                Data.terminal.write(entity.getName(), dx, dy + 2, Data.font, Data.background);
                for (int i = 0; i < entity.getName().length(); i++)
                    Data.terminal.write(Character.toString(196), dx + i, dy + 3, Data.font, Data.background);
            } else {
                Data.terminal.write(entity.getName(), dx, dy + 2, ((Enemy) entity).getColor(), Data.background);
                for (int i = 0; i < entity.getName().length(); i++)
                    Data.terminal.write(Character.toString(196), dx + i, dy + 3, ((Enemy) entity).getColor(), Data.background);
            }
            Data.terminal.write("HP: " + entity.getHp() + "/" + entity.getHpMax() + "  ", dx, dy+7, Color.red, Data.background);
            Data.terminal.write("Attack: " + entity.getAtk() + "  ", dx, dy+9, new Color(255, 115, 0), Data.background);
            Data.terminal.write("Defense: " + entity.getDef() + "  ", dx, dy+11, new Color(0, 128, 255), Data.background);
            if (entity instanceof Player) {
                Data.terminal.write("Mana: " + Data.player.getMana() + "/" + Data.player.getManaMax() + "  ", dx, dy + 13, new Color(153, 0, 255), Data.background);
                Data.terminal.write("Mana regen: " + Data.player.getManaRegen() + "  ", dx, dy + 15, new Color(153, 0, 255), Data.background);
            } else
                dy -= 4;
            Data.terminal.write("Magic defense: " + entity.getMagicDef() + "  ", dx, dy+17, new Color(94, 0, 255), Data.background);
            Data.terminal.write("Crit chance: " + entity.getCritChance() + "  ", dx, dy+19, new Color(41, 168, 33), Data.background);
        }
        if (!Data.inAttack) {
            Data.terminal.write("Coins: " + Data.player.getCoins() + "  ", 140, 21, new Color(255, 204, 0), Data.background);
            int x = 0;
            for (int i = 1; i <= Data.player.getLevel(); i++) {
                x += getXpNeeded(i);
            }
            Data.terminal.write("Level: " + Data.player.getLevel() + "  (" + (x - getXpNeeded(Data.player.getLevel()) + Data.player.getXp()) + "/" + x + ")  ", 140, 25, Data.font, Data.background);

            Data.terminal.write(Character.toString(60), 140, 27, Data.font, Data.background);
            Data.terminal.write(Character.toString(62), 166, 27, Data.font, Data.background);
            for (int i = 141; i < 166; i++)
                Data.terminal.write(Character.toString(196), i, 27, Data.font, Data.background);
            for (int i = 141; i < 141 + 25 * Data.player.getXp() / getXpNeeded(Data.player.getLevel()); i++)
                Data.terminal.write(Character.toString(219), i, 27, Data.font, Data.background);
        }
        add(Data.terminal);
        Data.terminal.repaint();
    }

    public int getXpNeeded(int x) {
        return (int) (Math.pow(x, 2) * 10);
    }

    public void affInv() {
        if (!Data.invOpen)
            aff();
        else {
            clearSideAff();
            Data.terminal.write("Inventory: (0 /10)", 140, 35, Data.font, Data.background);
            if (Data.player.getInv().size() != 10)
                Data.terminal.write(String.valueOf(Data.player.getInv().size()), 153, 35, Data.font, Data.background);
            else
                Data.terminal.write("10", 152, 35, Data.font, Data.background);

            Data.terminal.write("[A] to drop an item", 140, 77, Data.font, Data.background);
            Data.terminal.write("[E] to equip an item", 140, 75, Data.font, Data.background);

            if (Data.player.getInv().size() != 0) {
                for (int i = 0; i < Data.player.getInv().size(); i++) {
                    if (i == Data.itemInv)
                        Data.terminal.write("> " + Data.player.getInv().get(i).getName(), 140, 38 + 2 * i, Data.font, Data.background);
                    else
                        Data.terminal.write(Data.player.getInv().get(i).getName() + "  ", 140, 38 + 2 * i, Data.font, Data.background);
                    if (Data.player.getInv().get(i).isEquipped())
                        Data.terminal.write("Equipped", 160, 38 + 2 * i, Data.font, Data.background);
                }
            } else {
                Data.terminal.write("Nothing seems to be here...", 140, 38, Data.font, Data.background);
            }
            add(Data.terminal);
            Data.terminal.repaint();
        }
    }

    //Méthode très importante
    public void updateAff() { //Mise à jour de l'affichage après un déplacement + test de la case sur laquelle le joueur se trouve
        if (charRoom(Data.player.getX(), Data.player.getY()) == '.')
            Data.terminal.write(Character.toString(32), Data.player.getX(), Data.player.getY(), Data.font, Data.roomColor);
        else if (charRoom(Data.player.getX(), Data.player.getY()) == '*')
            Data.terminal.write(Character.toString(32), Data.player.getX(), Data.player.getY(), Data.font, Data.pathColor);

        if (charRoom(Data.player.getX()+Data.x, Data.player.getY()+Data.y) == '.')
            Data.terminal.write(Character.toString(1), Data.player.getX() + Data.x, Data.player.getY() + Data.y, Data.playerColor, Data.roomColor);
        else if (charRoom(Data.player.getX()+Data.x, Data.player.getY()+Data.y) == '*')
            Data.terminal.write(Character.toString(1), Data.player.getX() + Data.x, Data.player.getY() + Data.y, Data.playerColor, Data.pathColor);

        add(Data.terminal);
        for (int i = 0; i < Data.enemies.size(); i++) {
            if (Data.enemies.get(i).getX() == Data.player.getX() + Data.x && Data.enemies.get(i).getY() == Data.player.getY() + Data.y) {
                Data.enemyAttacked = Data.enemies.get(i);
                affAttack(Data.enemyAttacked);
            }
        }

        if (!Data.inAttack) {
            for (int i = 0; i < Data.coins.size(); i++) {
                if (Data.coins.get(i).getX() == Data.player.getX() + Data.x && Data.coins.get(i).getY() == Data.player.getY() + Data.y) {
                    Data.coins.remove(i);
                    Data.player.setCoins(Data.player.getCoins()+1);
                    clearSideAff();
                    Data.terminal.write("+1 coin", 140, 35, Data.font, Data.background);
                    add(Data.terminal);
                    Data.terminal.repaint();
                    affStats(Data.player);
                }
            }

            moveEnemies();
            if (!Data.inAttack) {
                affAllItems();
                affCoins();
            }
        }

        if (!Data.inAttack) { //Je refais le test, car moveEnemies() peut changer la valeur de Data.inAttack
            for (int i = 0; i < Data.items.size(); i++) {
                if (Data.items.get(i).getX() == Data.player.getX() + Data.x && Data.items.get(i).getY() == Data.player.getY() + Data.y) {
                    for (int j = 0; j < 6; j++) {
                        Data.terminal.write(Character.toString(2 - j % 2), Data.player.getX() + Data.x, Data.player.getY() + Data.y, Data.playerColor, Data.background);
                        add(Data.terminal);
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
                    clearSideAff();
                    affMsg("You found a " + Data.items.get(i).getName(), 140, 35);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    affMsg("Do you want to pick it up ?", 140, 39);
                    affMsg("Press [E] to Pick-Up", 140, 41);
                    affMsg("Press [R] to Ignore", 140, 42);
                    //Laisse le jeu dans un état ou les seuls inputs possibles sont E ou R
                }
            }
        }
    }

    public void moveEnemies() {
        for (Enemy enemy : Data.enemies) {
            int a = 0;
            int b = 0;
            if (!Data.inAttack)
                Data.terminal.write(Character.toString(32), enemy.getX(), enemy.getY(), enemy.getColor(), Data.roomColor);
            //test si le joueur est dans la même salle que l'ennemi + petit random pour que l'ennemi ne soit pas trop prévisible
            if (ThreadLocalRandom.current().nextInt(0, 8) != 0 && MapGenerator.getRoomByXY(Data.listRooms, (Data.player.getX())/17, (Data.player.getY())/17).getX() == MapGenerator.getRoomByXY(Data.listRooms, (enemy.getX())/17, (enemy.getY())/17).getX() && MapGenerator.getRoomByXY(Data.listRooms, (Data.player.getX())/17, (Data.player.getY())/17).getY() == MapGenerator.getRoomByXY(Data.listRooms, (enemy.getX())/17, (enemy.getY())/17).getY()) {
                //petit random pour que l'ennemi se rapproche soit en x soit en y du joueur
                if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
                    if (enemy.getX() < Data.player.getX() && charRoom(enemy.getX()+1, enemy.getY()) == '.')
                        a = 1;
                    else if (enemy.getX() > Data.player.getX() && charRoom(enemy.getX()-1, enemy.getY()) == '.')
                        a = -1;
                    else if (enemy.getY() < Data.player.getY() && charRoom(enemy.getX(), enemy.getY()+1) == '.')
                        b = 1;
                    else if (enemy.getY() > Data.player.getY() && charRoom(enemy.getX(), enemy.getY()-1) == '.')
                        b = -1;
                } else {
                    if (enemy.getY() < Data.player.getY() && charRoom(enemy.getX(), enemy.getY()+1) == '.')
                        b = 1;
                    else if (enemy.getY() > Data.player.getY() && charRoom(enemy.getX(), enemy.getY()-1) == '.')
                        b = -1;
                    else if (enemy.getX() < Data.player.getX() && charRoom(enemy.getX()+1, enemy.getY()) == '.')
                        a = 1;
                    else if (enemy.getX() > Data.player.getX() && charRoom(enemy.getX()-1, enemy.getY()) == '.')
                        a = -1;
                }
            } else {
                int j = ThreadLocalRandom.current().nextInt(1, 5);
                if (j == 1 && charRoom(enemy.getX()+1, enemy.getY()) == '.')
                    a = 1;
                else if (j == 2 && charRoom(enemy.getX()-1, enemy.getY()) == '.')
                    a = -1;
                else if (j == 3 && charRoom(enemy.getX(), enemy.getY()+1) == '.')
                    b = 1;
                else if (j == 4 && charRoom(enemy.getX(), enemy.getY()-1) == '.')
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
                affAttack(Data.enemyAttacked);
                break;
            }
            if (!Data.inAttack)
                Data.terminal.write(Character.toString(234), enemy.getX(), enemy.getY(), enemy.getColor(), Data.roomColor);
        }
        if (!Data.inAttack) {
            add(Data.terminal);
            Data.terminal.repaint();
        }
    }

    public void affAttack(Enemy enemy) {
        Data.inAttack = true;
        Data.waitingForAttack = true;
        Data.attackSelected = 1;
        clearSideAff();
        affMsg("You are attacked ", 140, 35);
        affMsg("by a " + enemy.getName(), 140, 36);
        affMsg("Press [Enter] to Attack", 140, 39);
        Data.tempsInactif = System.currentTimeMillis();
    }
    public void attack(Enemy enemy) {
        Data.waitingForAttack = false;
        for (int i = 1 ; i < 169 ; i++) {
            for (int j = 1 ; j < 84 ; j++) {
                Data.terminal.write(" ", i, j, Data.font, Data.background);
            }
        }

        for (int i = 1 ; i < 169 ; i++) {
            Data.terminal.write(Character.toString(196), i, 60, Data.font, Data.background);
        }
        Data.terminal.write(Character.toString(195), 0, 60, Data.font, Data.background);
        Data.terminal.write(Character.toString(180), 169, 60, Data.font, Data.background);
        Data.terminal.write(Character.toString(196), 137, 0, Data.font, Data.background);
        Data.terminal.write(Character.toString(196), 137, 84, Data.font, Data.background);
        Data.terminal.write(Character.toString(217), 1, 1, Data.font, Data.background);
        Data.terminal.write(Character.toString(192), 168, 1, Data.font, Data.background);
        Data.terminal.write(Character.toString(179), 169, 31, Data.font, Data.background);
        Data.terminal.write(Character.toString(191), 1, 83, Data.font, Data.background);
        Data.terminal.write(Character.toString(218), 168, 83, Data.font, Data.background);
        for (int i = 61 ; i < 84 ; i++) {
            Data.terminal.write(Character.toString(179), 142, i, Data.font, Data.background);
            Data.terminal.write(Character.toString(186), 117, i, Data.font, Data.background);
        }
        Data.terminal.write(Character.toString(194), 142, 60, Data.font, Data.background);
        Data.terminal.write(Character.toString(210), 117, 60, Data.font, Data.background);
        Data.terminal.write(Character.toString(193), 142, 84, Data.font, Data.background);
        Data.terminal.write(Character.toString(208), 117, 84, Data.font, Data.background);
        String[] attack = {"          _   _             _    ",
        "     /\\  | | | |           | |   ",
        "    /  \\ | |_| |_ __ _  ___| | __",
        "   / /\\ \\| __| __/ _` |/ __| |/ /",
        "  / ____ \\ |_| || (_| | (__|   < ",
        " /_/    \\_\\__|\\__\\__,_|\\___|_|\\_\\"};
        String[] spell = {"   _____            _ _ ",
                "  / ____|          | | |",
                " | (___  _ __   ___| | |",
                "  \\___ \\| '_ \\ / _ \\ | |",
                "  ____) | |_) |  __/ | |",
                " |_____/| .__/ \\___|_|_|",
                "        | |             ",
                "        |_|             "};
        String[] item = {"  _____ _                     ",
                " |_   _| |                    ",
                "   | | | |_ ___ _ __ ___  ___ ",
                "   | | | __/ _ \\ '_ ` _ \\/ __|",
                "  _| |_| ||  __/ | | | | \\__ \\",
                " |_____|\\__\\___|_| |_| |_|___/"};
        String[] knight = {"                                .-'`-.",
                "                               /  | | \\",
                "                              /   | |  \\",
                "                             |  __|_|___|",
                "                             |' |||",
                "                             |(   _L   ||",
                "                             \\|`-'__`-'|'",
                "                              |  `--'  |",
                "                             _|        |-.",
                "                         .-'| |  \\     /  `-.",
                "                        /   | |\\     .'      `-.",
                "                       /    | | `''''           \\",
                "                      J     | |             _____|",
                "                      |  |  J J         .-'   ___ `-.",
                "                      |  \\   L L      .'  .-'  |  `-.`.",
                "                      | \\|   | |     /  .'|    |    |\\ \\",
                "                      |  |   J J   .' .'  |    |    | \\ \\",
                "                      |  |    L L J  /    |    |    |  \\ L",
                "                     /   |     \\ \\F J|    |    |    |   LJ",
                "                     |   |      \\J F | () | () | () | ()| L",
                "                    J  \\ |       FJ  |    |  / _`-. |   J |",
                "                    |    |      J |  |    | //    \\ |   J |",
                "                   J     |\\     | |  |    ||:(     ||   J |",
                "                   |     | `----| |  |    ||::`._.:||   | F",
                "                   |     /\\_    | |  |    ||:::::::F|   | F",
                "                   |    |  /`---| |  |    | \\:::::/ |   FJ",
                "                   F    |  / |  J |  |    |  `-:-'  |  J F",
                "                  J_.--/  /  |  J J  | () | () | () |()FJ",
                "                   |  |    /     L L |    |    |    | / F",
                "                   |  |     |    \\ \\ |    |    |    |/ /",
                "                 |`-. |    |     |\\ \\|    |    |    / /",
                "                 J'\\ \\|    |     | `.`.   |    |  .'.'",
                "                / .'> |    |     |  `-.`-.|____.-'.'",
                "              .' /::'_|    |/    |    `-.______.-'",
                "             / .::/   \\    |     |           |  |",
                "           .' /::'     |--._     |           |--|",
                "          / .::/       |    `-.__|     ____.-|//|",
                "        .' /::'        |        F `--'      ||< |",
                "       / .::/          |       J   |        FL\\\\|",
                "     .' /::'           )       |   |        F| >|",
                "    / .::/            (        \\   |        F|//|",
                "  .' /::'              \\       /   |        F|--|",
                " / .::/                 |` `' '(   (      ' J|  |",
                "| /::'                  |  | ` \\   \\  `    / J  |",
                "|_:/                    |  | | |    |`-  ''F J  J",
                "                        |    ' F    |   \"\" |  `-'",
                "                        |     J     |      |",
                "                        |     /     |      |",
                "                        |   .'      |      F",
                "                       /---'(       J     J",
                "                     .'     \\        L    |",
                "                  .-'        )       L    F",
                "                .'       .---'       \\__.-'",
                "               (       .'             L   |",
                "                `-----'               |   \\",
                "                                      J    \\",
                "                                       L    L",
                "                                       |    F",
                "                                       `-.-'"};
        for (int i = 0 ; i < 6 ; i++) {
            Data.terminal.write(attack[i], 13, 63 + i, Data.font, Data.background);
            Data.terminal.write(item[i], 68, 63 + i, Data.font, Data.background);
            Data.terminal.write(Data.arrow[i], 7, 63 + i, Data.font, Data.background);
        }
        for (int i = 0 ; i < 59 ; i++)
            Data.terminal.write(knight[i], 2, 1+i, Data.font, Data.background);
        for (int i = 0 ; i < 8 ; i++)
            Data.terminal.write(spell[i], 13, 73 + i, Data.font, Data.background);
        affStats(enemy);
        Data.tempsInactif = System.currentTimeMillis();
    }

    public void affSelection() {
        int dx = 7;
        int dy = 63;
        if (Data.attackSelected == 2)
            dy = 73;
        else if (Data.attackSelected == 3)
            dx = 62;
        for (int i = 0 ; i < 6 ; i++) {
            Data.terminal.write("      ", 7, 63 + i, Data.font, Data.background);
            Data.terminal.write("      ", 62, 63 + i, Data.font, Data.background);
            Data.terminal.write("      ", 7, 73 + i, Data.font, Data.background);
        }
        for (int i = 0 ; i < 6 ; i++)
            Data.terminal.write(Data.arrow[i], dx, dy + i, Data.font, Data.background);
        add(Data.terminal);
        Data.terminal.repaint();
    }

    public void attack2(Enemy enemy) {
        clearBottomAff();
        if (Data.attackSelected == 1) {
            String weapon = "fists";
            for (Item item : Data.player.getInv()) {
                if (item instanceof AtkItem && ((AtkItem) item).isEquipped()) {
                    weapon = item.getName();
                    break;
                }
            }
            enemy.setHp(enemy.getHp() - (Data.player.getAtk() - enemy.getDef() < 0 ? 0 : Data.player.getAtk() - enemy.getDef()));
            if (enemy.getHp() <= 0)
                enemy.setHp(0);
            affStats(enemy);
            affMsg("You attack the " + enemy.getName() + " with your " + weapon + ".", 7, 64);
            Data.waitingForEnemy = true;
        }
    }

    public void attackEnemy(Enemy enemy) {
        if (enemy.getHp() == 0)
            winCombat(enemy);
        else {
            clearBottomAff();
            Data.player.setHp(Data.player.getHp() - (enemy.getAtk() - Data.player.getDef() < 0 ? 0 : enemy.getAtk() - Data.player.getDef()));
            affStats(enemy);
            affMsg("The " + enemy.getName() + " attacks you.", 7, 64);
            Data.waitingForEnemy = false;
            Data.waitingForAttack = true;
        }
    }

    public void winCombat(Enemy enemy) {
        Data.waitingForEnemy = false;
        Data.waitingForAttack = false;
        Data.inAttack = false;
        clearBottomAff();
        Data.player.setXp(Data.player.getXp() + enemy.getXp());
        if (Data.player.getXp() > getXpNeeded(Data.player.getLevel())) {
            Data.player.setXp(Data.player.getXp() - getXpNeeded(Data.player.getLevel()));
            Data.player.setLevel(Data.player.getLevel() + 1);
        }
        Data.player.setCoins(Data.player.getCoins() + enemy.getCoins());
        Data.enemies.remove(enemy);
        affMsg("You killed the " + enemy.getName() + "!", 7, 64);
        affMsg("Reward: " + enemy.getXp() + " xp, " + enemy.getCoins() + " coins.", 7, 66);
        Data.waitingForReturn = true;
    }

    public void clearSideAff() {
        for (int i = 35; i < 84; i++)
            for (int j = 140; j < 169; j++)
                Data.terminal.write(Character.toString(32), j, i);
        Data.terminal.write(Character.toString(218), 168, 83, Data.font, Data.background);
    }

    public void  clearBottomAff() {
        for (int i = 61 ; i < 83 ; i ++)
            for (int j = 1 ; j < 117 ; j++)
                Data.terminal.write(Character.toString(32), j, i);
        add(Data.terminal);
        Data.terminal.repaint();
    }

    public void pickedUp(char keyPressed, int i) { //Méthode pour ramasser un item, called seulement lors d'un input E ou R
        clearSideAff();
        Data.justPickedUp = true;
        if (keyPressed == 'E') {
            if (Data.player.getInv().size() < 10) {
                Data.player.getInv().add(Data.items.get(i));
                affMsg("You picked up the " + Data.items.get(i).getName(), 140, 35);
                Data.items.remove(i);
            } else {
                affMsg("You can't carry more", 140, 35);
                affMsg("than 10 items", 140, 36);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                affMsg("You decided to let", 140, 38);
                affMsg("the " + Data.items.get(i).getName(), 140, 39);
            }
        } else {
            affMsg("You decided to let", 140, 35);
            affMsg("the " + Data.items.get(i).getName(), 140, 36);
        }
    }

    public void affMsg(String msg, int x, int y) { //Méthode pour afficher un message avec un effet de défilement
        for (int i = 0; i < msg.length(); i++) {
            Data.terminal.write(msg.charAt(i), x + i, y, Data.font, Data.background);
            add(Data.terminal);
            Data.terminal.paintImmediately(Data.terminal.getBounds());
            long temps = System.currentTimeMillis();
            while (System.currentTimeMillis() - temps < 20) {
            }
        }
        add(Data.terminal);
        Data.terminal.repaint();
        Data.tempsInactif = System.currentTimeMillis();
    }

    public void addEnemy(Enemy enemy) {
        Data.enemies.add(enemy);
        affEnemy(enemy);
    }

    public void affEnemy(Enemy enemy) {
        Data.terminal.write(Character.toString(234), enemy.getX(), enemy.getY(), enemy.getColor(), Data.roomColor);
        add(Data.terminal);
        Data.terminal.repaint();
    }

    public void addItem(Item item) {
        Data.items.add(item);
        affItem(item);
    }

    public void affItem(Item item) {
        Data.terminal.write(Character.toString(235), item.getX(), item.getY(), item.getColorItem(), Data.roomColor);
        add(Data.terminal);
        Data.terminal.repaint();
    }

    public void addCoins(int x) {
        for (int i = 0; i < x; i++)
            Data.coins.add(new Coin());
        affCoins();
    }

    public void affCoins() {
        for (Coin coin : Data.coins) {
            boolean aff = true;
            for (Enemy enemy : Data.enemies) {
                if (coin.getX() == enemy.getX() && coin.getY() == enemy.getY())
                    aff = false;
            }
            if (aff)
                Data.terminal.write(Character.toString(7), coin.getX(), coin.getY(), new Color(255, 204, 0), Data.roomColor);
        }
        add(Data.terminal);
        Data.terminal.repaint();
    }

    public void affAllItems() {
        for (Item item : Data.items) {
            boolean aff = true;
            for (Enemy enemy : Data.enemies) {
                if (item.getX() == enemy.getX() && item.getY() == enemy.getY())
                    aff = false;
            }
            if (aff)
                affItem(item);
        }
    }

    public static char charRoom(int x, int y) {
        int X = x/17;
        int Y = y/17;

        Room room = MapGenerator.getRoomByXY(Data.listRooms, X, Y);


        if (room == null)
            return (' ');
        int dx = x - (room.getX())*17;
        int dy = y - (room.getY())*17;
        return room.getRoom()[dy].charAt(dx);
    }

    //On touche pas ce qui est en dessous

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //Méthode très importante
    @Override
    public void keyPressed(KeyEvent e) { //Méthode pour gérer les inputs
        if (System.currentTimeMillis() - Data.tempsInactif > 20) {
            if (Data.inAttack && !Data.waitingForAttack && !Data.waitingForEnemy) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN && Data.attackSelected == 1)
                    Data.attackSelected = 2;
                else if (e.getKeyCode() == KeyEvent.VK_UP && Data.attackSelected == 2)
                    Data.attackSelected = 1;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT && Data.attackSelected == 1)
                    Data.attackSelected = 3;
                else if (e.getKeyCode() == KeyEvent.VK_LEFT && Data.attackSelected == 3)
                    Data.attackSelected = 1;
                else if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    attack2(Data.enemyAttacked);
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    affSelection();
                }
            } else if (Data.inAttack && !Data.waitingForAttack && Data.waitingForEnemy) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    attackEnemy(Data.enemyAttacked);
            } else if (Data.inAttack && Data.waitingForAttack) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    attack(Data.enemyAttacked);
                }
            } else if (Data.waitingForReturn) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    aff();
                    Data.waitingForReturn = false;
                }
            } else if (Data.pickUp) {//si on est sur un item
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    Data.pickUp = false;
                    pickedUp('E', Data.itemSelected);
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    Data.pickUp = false;
                    pickedUp('R', Data.itemSelected);
                }
            } else if (Data.invOpen) {
                if (e.getKeyCode() == KeyEvent.VK_I) {
                    Data.itemInv = 0;
                    Data.invOpen = !Data.invOpen;
                    affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_E || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    Data.terminal.write("                 ", 140, 71, Color.WHITE, Color.BLACK);
                    add(Data.terminal);
                    Data.terminal.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    Data.itemInv--;
                    if (Data.itemInv == -1)
                        Data.itemInv = Data.player.getInv().size() - 1;
                    affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    Data.itemInv++;
                    if (Data.itemInv == Data.player.getInv().size())
                        Data.itemInv = 0;
                    affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_A && Data.player.getInv().size() != 0) {
                    Data.player.getInv().remove(Data.itemInv);
                    if (Data.itemInv == Data.player.getInv().size())
                        Data.itemInv--;
                    affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_E && Data.player.getInv().size() != 0) {
                    if (!(Data.player.getInv().get(Data.itemInv) instanceof Potion) && Data.player.getInv().get(Data.itemInv).isEquipped()) {
                        Data.player.getInv().get(Data.itemInv).setEquipped(false);
                        if (Data.player.getInv().get(Data.itemInv) instanceof AtkItem)
                            Data.player.setAtk(1);
                        else if (Data.player.getInv().get(Data.itemInv) instanceof DefItem)
                            Data.player.setDef(0);
                        affInv();
                        affStats(Data.player);
                    } else if (!(Data.player.getInv().get(Data.itemInv) instanceof Potion) && !Data.player.getInv().get(Data.itemInv).isEquipped()) {
                        for (Item i : Data.player.getInv()) {
                            if (i.isEquipped() && i.getClass() == Data.player.getInv().get(Data.itemInv).getClass())
                                i.setEquipped(false);
                        }
                        Data.player.getInv().get(Data.itemInv).setEquipped(true);
                        System.out.println(Data.player.getInv().get(Data.itemInv).isEquipped());

                        if (Data.player.getInv().get(Data.itemInv) instanceof AtkItem)
                            Data.player.setAtk(((AtkItem) Data.player.getInv().get(Data.itemInv)).getAtk());
                        else if (Data.player.getInv().get(Data.itemInv) instanceof DefItem)
                            Data.player.setDef(((DefItem) Data.player.getInv().get(Data.itemInv)).getDef());
                        affInv();
                        affStats(Data.player);
                    } else if (Data.player.getInv().get(Data.itemInv) instanceof Potion) {
                        Data.terminal.write("Cannot be equiped", 140, 71, Data.font, Data.background);
                        add(Data.terminal);
                    }
                }
            } else {
                if (e.getKeyCode() == KeyEvent.VK_I) {
                    Data.itemInv = 0;
                    Data.invOpen = !Data.invOpen;
                    affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                    Data.x = 1;
                else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_Q)
                    Data.x = -1;
                else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_Z)
                    Data.y = -1;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                    Data.y = 1;
                if (Data.x == 1 || Data.x == -1 || Data.y == 1 || Data.y == -1) {
                    Data.player.move(Data.x, Data.y, this);
                    Data.x = 0;
                    Data.y = 0;
                    if (Data.justPickedUp) {
                        Data.justPickedUp = false;
                        if (!Data.inAttack)
                            affAllItems();
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}