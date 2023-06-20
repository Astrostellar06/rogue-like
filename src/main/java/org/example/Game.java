package org.example;

import javax.swing.*;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Game extends JFrame implements KeyListener {

    private static final long serialVersionUID = 1060623638149583738L;
    private AsciiPanel terminal;
    private int x = 0, y = 0;
    private Player player;
    static ArrayList<Enemy> enemies;
    static ArrayList<Item> items;
    static ArrayList<Coin> coins;
    private Color font, background, playerColor, roomColor, pathColor;
    private boolean invOpen = false, justPickedUp = false, pickUp = false, inAttack = false, waitingForAttack = false, waitingForEnemy = false, waitingForReturn = false;; //état de jeu
    private int itemSelected = 0, itemInv = 0, attackSelected = 1;
    private long tempsInactif = 0;
    static ArrayList<Room> listRooms = MapGenerator.generate();
    private String[] arrow = {" __   ",
            " \\ \\  ",
            "  \\ \\ ",
            "   > >",
            "  / / ",
            " /_/  "};
    private Enemy enemyAttacked = null;


    public Game() { //Création du jeu
        super(); //Utilisation de JFrame et de AsciiPanel
        this.player = new Player("Astrostellar");
        enemies = new ArrayList<>();
        items = new ArrayList<>();
        coins = new ArrayList<>();
        terminal = new AsciiPanel(170, 85, AsciiFont.TALRYTH_15_15); //Taille de la fenêtre + police
        addKeyListener(this); //Ajout de l'écouteur de touches
        font = new Color(17, 255, 0);
        background = new Color(0, 0, 0);
        playerColor = new Color(255, 255, 0);
        roomColor = new Color(0, 0, 0);
        pathColor = new Color(0, 0, 0);
        this.listRooms = listRooms;
        aff();
    }

    public void affRooms(Room room) {
        for (int cy = 0; cy < room.getRoom().length; cy++) {
            char[] line = room.getRoom()[cy].toCharArray();
            for (int cx = 0; cx < line.length; cx++) {
                if (line[cx] == '|')
                    terminal.write(Character.toString(179), room.getX() * 17 + cx, room.getY() * 17 + cy, font, background);
                else if (line[cx] == '-')
                    terminal.write(Character.toString(196), room.getX() * 17 + cx, room.getY() * 17 + cy, font, background);
                else if (line[cx] == '1')
                    terminal.write(Character.toString(218), room.getX() * 17 + cx, room.getY() * 17 + cy, font, background);
                else if (line[cx] == '2')
                    terminal.write(Character.toString(191), room.getX() * 17 + cx, room.getY() * 17 + cy, font, background);
                else if (line[cx] == '3')
                    terminal.write(Character.toString(192), room.getX() * 17 + cx, room.getY() * 17 + cy, font, background);
                else if (line[cx] == '4')
                    terminal.write(Character.toString(217), room.getX() * 17 + cx, room.getY() * 17 + cy, font, background);
                else if (line[cx] == '.')
                    terminal.write(Character.toString(32), room.getX() * 17 + cx, room.getY() * 17 + cy, font, roomColor);
                else if (line[cx] == '*')
                    terminal.write(Character.toString(32), room.getX() * 17 + cx, room.getY() * 17 + cy, font, pathColor);
                else
                    terminal.write(Character.toString(32), room.getX() * 17 + cx, room.getY() * 17 + cy, font, background);
            }
        }
    }

    public void aff() { //Affichage de la fenêtre
        for (int i = 0; i < 85; i++) {
            for (int j = 0; j < 170; j++) {
                terminal.write(Character.toString(32), j, i, font, background);
            }
        }


        for (Room room : listRooms) {
            affRooms(room);
        }

        terminal.write(Character.toString(1), player.x, player.y, playerColor, roomColor);

        for (Enemy enemy : enemies) {
            affEnemy(enemy);
        }

        for (Item item : items) {
            affItem(item);
        }

        affCoins();

        //déco
        for (int i = 1; i < 84; i++) {
            terminal.write(Character.toString(186), 137, i, font, background);
            terminal.write(Character.toString(179), 0, i, font, background);
            terminal.write(Character.toString(179), 169, i, font, background);
        }
        for (int i = 1; i < 169; i++) {
            terminal.write(Character.toString(196), i, 84, font, background);
            terminal.write(Character.toString(196), i, 0, font, background);
        }
        terminal.write(Character.toString(210), 137, 0, font, background);
        terminal.write(Character.toString(208), 137, 84, font, background);

        terminal.write(Character.toString(218), 1, 0, font, background);
        terminal.write(Character.toString(218), 0, 1, font, background);
        terminal.write(Character.toString(217), 1, 1, font, background);

        terminal.write(Character.toString(191), 168, 0, font, background);
        terminal.write(Character.toString(191), 169, 1, font, background);
        terminal.write(Character.toString(192), 168, 1, font, background);

        terminal.write(Character.toString(192), 1, 84, font, background);
        terminal.write(Character.toString(192), 0, 83, font, background);
        terminal.write(Character.toString(191), 1, 83, font, background);

        terminal.write(Character.toString(217), 168, 84, font, background);
        terminal.write(Character.toString(217), 169, 83, font, background);
        terminal.write(Character.toString(218), 168, 83, font, background);

        affStats(player);

        for (int i = 0; i < 31; i++)
            terminal.write(Character.toString(196), 138 + i, 31, font, background);
        terminal.write(Character.toString(199), 137, 31, font, background);
        terminal.write(Character.toString(180), 169, 31, font, background);

        add(terminal);
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
        if (inAttack) {
            b = 2;
            dy = 60;
            dx = 120;
        }
        for (int a = 0 ; a < b ; a++) {
            if (a == 0) {
                entity = player;
            } else {
                entity = target;
                dx = 145;
                dy = 60;
            }
            if (entity instanceof Player) {
                terminal.write(entity.name, dx, dy + 2, font, background);
                for (int i = 0; i < entity.name.length(); i++)
                    terminal.write(Character.toString(196), dx + i, dy + 3, font, background);
            } else {
                terminal.write(entity.name, dx, dy + 2, ((Enemy) entity).color, background);
                for (int i = 0; i < entity.name.length(); i++)
                    terminal.write(Character.toString(196), dx + i, dy + 3, ((Enemy) entity).color, background);
            }
            terminal.write("HP: " + entity.hp + "/" + entity.hpMax + "  ", dx, dy+7, Color.red, background);
            terminal.write("Attack: " + entity.atk + "  ", dx, dy+9, new Color(255, 115, 0), background);
            terminal.write("Defense: " + entity.def + "  ", dx, dy+11, new Color(0, 128, 255), background);
            if (entity instanceof Player) {
                terminal.write("Mana: " + player.mana + "/" + player.manaMax + "  ", dx, dy + 13, new Color(153, 0, 255), background);
                terminal.write("Mana regen: " + player.manaRegen + "  ", dx, dy + 15, new Color(153, 0, 255), background);
            } else
                dy -= 4;
            terminal.write("Magic defense: " + entity.magicDef + "  ", dx, dy+17, new Color(94, 0, 255), background);
            terminal.write("Crit chance: " + entity.critChance + "  ", dx, dy+19, new Color(41, 168, 33), background);
        }
        if (!inAttack) {
            terminal.write("Coins: " + player.getCoins() + "  ", 140, 21, new Color(255, 204, 0), background);
            int x = 0;
            for (int i = 1; i <= player.level; i++) {
                x += getXpNeeded(i);
            }
            terminal.write("Level: " + player.getLevel() + "  (" + (x - getXpNeeded(player.level) + player.xp) + "/" + x + ")  ", 140, 25, font, background);

            terminal.write(Character.toString(60), 140, 27, font, background);
            terminal.write(Character.toString(62), 166, 27, font, background);
            for (int i = 141; i < 166; i++)
                terminal.write(Character.toString(196), i, 27, font, background);
            for (int i = 141; i < 141 + 25 * player.xp / getXpNeeded(player.level); i++)
                terminal.write(Character.toString(219), i, 27, font, background);
        }
        add(terminal);
        terminal.repaint();
    }

    public int getXpNeeded(int x) {
        return (int) (Math.pow(x, 2) * 10);
    }

    public void affInv() {
        if (!invOpen)
            aff();
        else {
            clearSideAff();
            terminal.write("Inventory: (0 /10)", 140, 35, font, background);
            if (player.inv.size() != 10)
                terminal.write(String.valueOf(player.inv.size()), 153, 35, font, background);
            else
                terminal.write("10", 152, 35, font, background);

            terminal.write("[A] to drop an item", 140, 77, font, background);
            terminal.write("[E] to equip an item", 140, 75, font, background);

            if (player.inv.size() != 0) {
                for (int i = 0; i < player.inv.size(); i++) {
                    if (i == itemInv)
                        terminal.write("> " + player.inv.get(i).getName(), 140, 38 + 2 * i, font, background);
                    else
                        terminal.write(player.inv.get(i).getName() + "  ", 140, 38 + 2 * i, font, background);
                    if (player.inv.get(i).getIsEquipped())
                        terminal.write("Equipped", 160, 38 + 2 * i, font, background);
                }
            } else {
                terminal.write("Nothing seems to be here...", 140, 38, font, background);
            }
            add(terminal);
            terminal.repaint();
        }
    }

    //Méthode très importante
    public void updateAff() { //Mise à jour de l'affichage après un déplacement + test de la case sur laquelle le joueur se trouve
        if (charRoom(player.x, player.y) == '.')
            terminal.write(Character.toString(32), player.x, player.y, font, roomColor);
        else if (charRoom(player.x, player.y) == '*')
            terminal.write(Character.toString(32), player.x, player.y, font, pathColor);

        if (charRoom(player.x+x, player.y+y) == '.')
            terminal.write(Character.toString(1), player.x + x, player.y + y, playerColor, roomColor);
        else if (charRoom(player.x+x, player.y+y) == '*')
            terminal.write(Character.toString(1), player.x + x, player.y + y, playerColor, pathColor);

        add(terminal);
        for (int i = 0; i < enemies.size(); i++) {
            if (enemies.get(i).x == player.x + x && enemies.get(i).y == player.y + y) {
                enemyAttacked = enemies.get(i);
                affAttack(enemyAttacked);
            }
        }

        if (!inAttack) {
            for (int i = 0; i < coins.size(); i++) {
                if (coins.get(i).x == player.x + x && coins.get(i).y == player.y + y) {
                    coins.remove(i);
                    player.coins++;
                    clearSideAff();
                    terminal.write("+1 coin", 140, 35, font, background);
                    add(terminal);
                    terminal.repaint();
                    affStats(player);
                }
            }

            moveEnemies();
            if (!inAttack) {
                affAllItems();
                affCoins();
            }
        }

        if (!inAttack) { //Je refais le test, car moveEnemies() peut changer la valeur de inAttack
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).x == player.x + x && items.get(i).y == player.y + y) {
                    for (int j = 0; j < 6; j++) {
                        terminal.write(Character.toString(2 - j % 2), player.x + x, player.y + y, playerColor, background);
                        add(terminal);
                        terminal.paintImmediately(terminal.getBounds());
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int j = 140; j < 169; j++)
                        terminal.write(Character.toString(32), j, 35, font, background);
                    pickUp = true;
                    itemSelected = i;
                    clearSideAff();
                    affMsg("You found a " + items.get(i).name, 140, 35);
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
        for (Enemy enemy : enemies) {
            int a = 0;
            int b = 0;
            if (!inAttack)
                terminal.write(Character.toString(32), enemy.x, enemy.y, enemy.color, roomColor);
            //test si le joueur est dans la même salle que l'ennemi + petit random pour que l'ennemi ne soit pas trop prévisible
            if (ThreadLocalRandom.current().nextInt(0, 8) != 0 && MapGenerator.getRoomByXY(listRooms, (player.x)/17, (player.y)/17).getX() == MapGenerator.getRoomByXY(listRooms, (enemy.x)/17, (enemy.y)/17).getX() && MapGenerator.getRoomByXY(listRooms, (player.x)/17, (player.y)/17).getY() == MapGenerator.getRoomByXY(listRooms, (enemy.x)/17, (enemy.y)/17).getY()) {
                //petit random pour que l'ennemi se rapproche soit en x soit en y du joueur
                if (ThreadLocalRandom.current().nextInt(0, 2) == 0) {
                    if (enemy.x < player.x && charRoom(enemy.x+1, enemy.y) == '.')
                        a = 1;
                    else if (enemy.x > player.x && charRoom(enemy.x-1, enemy.y) == '.')
                        a = -1;
                    else if (enemy.y < player.y && charRoom(enemy.x, enemy.y+1) == '.')
                        b = 1;
                    else if (enemy.y > player.y && charRoom(enemy.x, enemy.y-1) == '.')
                        b = -1;
                } else {
                    if (enemy.y < player.y && charRoom(enemy.x, enemy.y+1) == '.')
                        b = 1;
                    else if (enemy.y > player.y && charRoom(enemy.x, enemy.y-1) == '.')
                        b = -1;
                    else if (enemy.x < player.x && charRoom(enemy.x+1, enemy.y) == '.')
                        a = 1;
                    else if (enemy.x > player.x && charRoom(enemy.x-1, enemy.y) == '.')
                        a = -1;
                }
            } else {
                int j = ThreadLocalRandom.current().nextInt(1, 5);
                if (j == 1 && charRoom(enemy.x+1, enemy.y) == '.')
                    a = 1;
                else if (j == 2 && charRoom(enemy.x-1, enemy.y) == '.')
                    a = -1;
                else if (j == 3 && charRoom(enemy.x, enemy.y+1) == '.')
                    b = 1;
                else if (j == 4 && charRoom(enemy.x, enemy.y-1) == '.')
                    b = -1;
            }
            for (int j = 0; j < enemies.size(); j++) {
                if (enemy.x + a == enemies.get(j).x && enemy.y + b == enemies.get(j).y && enemy != enemies.get(j)) {
                    a = 0;
                    b = 0;
                }
            }
            enemy.x += a;
            enemy.y += b;
            enemyAttacked = enemy;
            if (enemy.x == player.x + x && enemy.y == player.y + y) {
                affAttack(enemyAttacked);
                break;
            }
            if (!inAttack)
                terminal.write(Character.toString(234), enemy.x, enemy.y, enemy.color, roomColor);
        }
        if (!inAttack) {
            add(terminal);
            terminal.repaint();
        }
    }

    public void affAttack(Enemy enemy) {
        inAttack = true;
        waitingForAttack = true;
        attackSelected = 1;
        clearSideAff();
        affMsg("You are attacked ", 140, 35);
        affMsg("by a " + enemy.name, 140, 36);
        affMsg("Press [Enter] to Attack", 140, 39);
        tempsInactif = System.currentTimeMillis();
    }
    public void attack(Enemy enemy) {
        waitingForAttack = false;
        for (int i = 1 ; i < 169 ; i++) {
            for (int j = 1 ; j < 84 ; j++) {
                terminal.write(" ", i, j, font, background);
            }
        }

        for (int i = 1 ; i < 169 ; i++) {
            terminal.write(Character.toString(196), i, 60, font, background);
        }
        terminal.write(Character.toString(195), 0, 60, font, background);
        terminal.write(Character.toString(180), 169, 60, font, background);
        terminal.write(Character.toString(196), 137, 0, font, background);
        terminal.write(Character.toString(196), 137, 84, font, background);
        terminal.write(Character.toString(217), 1, 1, font, background);
        terminal.write(Character.toString(192), 168, 1, font, background);
        terminal.write(Character.toString(179), 169, 31, font, background);
        terminal.write(Character.toString(191), 1, 83, font, background);
        terminal.write(Character.toString(218), 168, 83, font, background);
        for (int i = 61 ; i < 84 ; i++) {
            terminal.write(Character.toString(179), 142, i, font, background);
            terminal.write(Character.toString(186), 117, i, font, background);
        }
        terminal.write(Character.toString(194), 142, 60, font, background);
        terminal.write(Character.toString(210), 117, 60, font, background);
        terminal.write(Character.toString(193), 142, 84, font, background);
        terminal.write(Character.toString(208), 117, 84, font, background);
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
            terminal.write(attack[i], 13, 63 + i, font, background);
            terminal.write(item[i], 68, 63 + i, font, background);
            terminal.write(arrow[i], 7, 63 + i, font, background);
        }
        for (int i = 0 ; i < 59 ; i++)
            terminal.write(knight[i], 2, 1+i, font, background);
        for (int i = 0 ; i < 8 ; i++)
            terminal.write(spell[i], 13, 73 + i, font, background);
        affStats(enemy);
        tempsInactif = System.currentTimeMillis();
    }

    public void affSelection() {
        int dx = 7;
        int dy = 63;
        if (attackSelected == 2)
            dy = 73;
        else if (attackSelected == 3)
            dx = 62;
        for (int i = 0 ; i < 6 ; i++) {
            terminal.write("      ", 7, 63 + i, font, background);
            terminal.write("      ", 62, 63 + i, font, background);
            terminal.write("      ", 7, 73 + i, font, background);
        }
        for (int i = 0 ; i < 6 ; i++)
            terminal.write(arrow[i], dx, dy + i, font, background);
        add(terminal);
        terminal.repaint();
    }

    public void attack2(Enemy enemy) {
        clearBottomAff();
        if (attackSelected == 1) {
            String weapon = "fists";
            for (Item item : player.inv) {
                if (item instanceof AtkItem && ((AtkItem) item).isEquipped) {
                    weapon = item.name;
                    break;
                }
            }
            enemy.hp -= player.atk - enemy.def;
            if (enemy.hp <= 0)
                enemy.hp = 0;
            affStats(enemy);
            affMsg("You attack the " + enemy.name + " with your " + weapon + ".", 7, 64);
            waitingForEnemy = true;
        }
    }

    public void attackEnemy(Enemy enemy) {
        if (enemy.hp == 0)
            winCombat(enemy);
        else {
            clearBottomAff();
            player.hp -= enemy.atk - player.def;
            affStats(enemy);
            affMsg("The " + enemy.name + " attacks you.", 7, 64);
            waitingForEnemy = false;
            waitingForAttack = true;
        }
    }

    public void winCombat(Enemy enemy) {
        waitingForEnemy = false;
        waitingForAttack = false;
        inAttack = false;
        clearBottomAff();
        player.xp += enemy.xp;
        player.coins += enemy.coins;
        enemies.remove(enemy);
        affMsg("You killed the " + enemy.name + "!", 7, 64);
        affMsg("Reward: " + enemy.xp + " xp, " + enemy.coins + " coins.", 7, 66);
        waitingForReturn = true;
    }

    public void clearSideAff() {
        for (int i = 35; i < 84; i++)
            for (int j = 140; j < 169; j++)
                terminal.write(Character.toString(32), j, i);
        terminal.write(Character.toString(218), 168, 83, font, background);
    }

    public void  clearBottomAff() {
        for (int i = 61 ; i < 83 ; i ++)
            for (int j = 1 ; j < 117 ; j++)
                terminal.write(Character.toString(32), j, i);
        add(terminal);
        terminal.repaint();
    }

    public void pickedUp(char keyPressed, int i) { //Méthode pour ramasser un item, called seulement lors d'un input E ou R
        clearSideAff();
        justPickedUp = true;
        if (keyPressed == 'E') {
            if (player.inv.size() < 10) {
                player.inv.add(items.get(i));
                affMsg("You picked up the " + items.get(i).name, 140, 35);
                items.remove(i);
            } else {
                affMsg("You can't carry more", 140, 35);
                affMsg("than 10 items", 140, 36);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                affMsg("You decided to let", 140, 38);
                affMsg("the " + items.get(i).name, 140, 39);
            }
        } else {
            affMsg("You decided to let", 140, 35);
            affMsg("the " + items.get(i).name, 140, 36);
        }
    }

    public void affMsg(String msg, int x, int y) { //Méthode pour afficher un message avec un effet de défilement
        for (int i = 0; i < msg.length(); i++) {
            terminal.write(msg.charAt(i), x + i, y, font, background);
            add(terminal);
            terminal.paintImmediately(terminal.getBounds());
            long temps = System.currentTimeMillis();
            while (System.currentTimeMillis() - temps < 20) {
            }
        }
        add(terminal);
        terminal.repaint();
        tempsInactif = System.currentTimeMillis();
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
        affEnemy(enemy);
    }

    public void affEnemy(Enemy enemy) {
        terminal.write(Character.toString(234), enemy.x, enemy.y, enemy.color, roomColor);
        add(terminal);
        terminal.repaint();
    }

    public void addItem(Item item) {
        items.add(item);
        affItem(item);
    }

    public void affItem(Item item) {
        terminal.write(Character.toString(235), item.x, item.y, item.colorItem, roomColor);
        add(terminal);
        terminal.repaint();
    }

    public void addCoins(int x) {
        for (int i = 0; i < x; i++)
            coins.add(new Coin());
        affCoins();
    }

    public void affCoins() {
        for (Coin coin : coins) {
            boolean aff = true;
            for (Enemy enemy : enemies) {
                if (coin.x == enemy.x && coin.y == enemy.y)
                    aff = false;
            }
            if (aff)
                terminal.write(Character.toString(7), coin.x, coin.y, new Color(255, 204, 0), roomColor);
        }
        add(terminal);
        terminal.repaint();
    }

    public void affAllItems() {
        for (Item item : items) {
            boolean aff = true;
            for (Enemy enemy : enemies) {
                if (item.x == enemy.x && item.y == enemy.y)
                    aff = false;
            }
            if (aff)
                affItem(item);
        }
    }

    public static char charRoom(int x, int y) {
        int X = x/17;
        int Y = y/17;

        Room room = MapGenerator.getRoomByXY(listRooms, X, Y);


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
        if (System.currentTimeMillis() - tempsInactif > 20) {
            if (inAttack && !waitingForAttack && !waitingForEnemy) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN && attackSelected == 1)
                    attackSelected = 2;
                else if (e.getKeyCode() == KeyEvent.VK_UP && attackSelected == 2)
                    attackSelected = 1;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT && attackSelected == 1)
                    attackSelected = 3;
                else if (e.getKeyCode() == KeyEvent.VK_LEFT && attackSelected == 3)
                    attackSelected = 1;
                else if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    attack2(enemyAttacked);
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    affSelection();
                }
            } else if (inAttack && !waitingForAttack && waitingForEnemy) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    attackEnemy(enemyAttacked);
            } else if (inAttack && waitingForAttack) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    attack(enemyAttacked);
                }
            } else if (waitingForReturn) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    aff();
                    waitingForReturn = false;
                }
            } else if (pickUp) {//si on est sur un item
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    pickUp = false;
                    pickedUp('E', itemSelected);
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    pickUp = false;
                    pickedUp('R', itemSelected);
                }
            } else if (invOpen) {
                if (e.getKeyCode() == KeyEvent.VK_I) {
                    itemInv = 0;
                    invOpen = !invOpen;
                    affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_E || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    terminal.write("                 ", 140, 71, Color.WHITE, Color.BLACK);
                    add(terminal);
                    terminal.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    itemInv--;
                    if (itemInv == -1)
                        itemInv = player.inv.size() - 1;
                    affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    itemInv++;
                    if (itemInv == player.inv.size())
                        itemInv = 0;
                    affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_A && player.inv.size() != 0) {
                    player.inv.remove(itemInv);
                    if (itemInv == player.inv.size())
                        itemInv--;
                    affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_E && player.inv.size() != 0) {
                    if (!(player.inv.get(itemInv) instanceof ManaItem) && player.inv.get(itemInv).getIsEquipped()) {
                        player.inv.get(itemInv).setIsEquipped(false);
                        if (player.inv.get(itemInv) instanceof AtkItem)
                            player.atk = 1;
                        else if (player.inv.get(itemInv) instanceof DefItem)
                            player.def = 0;
                        affInv();
                        affStats(player);
                    } else if (!(player.inv.get(itemInv) instanceof ManaItem) && !player.inv.get(itemInv).getIsEquipped()) {
                        for (int i = 0; i < player.inv.size(); i++) {
                            if (player.inv.get(i).getIsEquipped() && player.inv.get(i).getClass() == player.inv.get(itemInv).getClass())
                                player.inv.get(i).setIsEquipped(false);
                        }
                        player.inv.get(itemInv).setIsEquipped(true);
                        if (player.inv.get(itemInv) instanceof AtkItem)
                            player.atk = ((AtkItem) player.inv.get(itemInv)).getAtk();
                        else if (player.inv.get(itemInv) instanceof DefItem)
                            player.def = ((DefItem) player.inv.get(itemInv)).getDef();
                        affInv();
                        affStats(player);
                    } else if (player.inv.get(itemInv) instanceof ManaItem) {
                        terminal.write("Cannot be equiped", 140, 71, font, background);
                        add(terminal);
                    }
                }
            } else {
                if (e.getKeyCode() == KeyEvent.VK_I) {
                    itemInv = 0;
                    invOpen = !invOpen;
                    affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                    x = 1;
                else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_Q)
                    x = -1;
                else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_Z)
                    y = -1;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                    y = 1;
                if (x == 1 || x == -1 || y == 1 || y == -1) {
                    player.move(x, y, this);
                    x = 0;
                    y = 0;
                    if (justPickedUp) {
                        justPickedUp = false;
                        if (!inAttack)
                            affAllItems();
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public AsciiPanel getTerminal() {
        return terminal;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}