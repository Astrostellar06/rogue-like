package org.example;

import javax.swing.*;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class Game extends JFrame implements KeyListener {

    private static final long serialVersionUID = 1060623638149583738L;
    private AsciiPanel terminal;
    private int x = 0;
    private int y = 0;
    private String[] map;
    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Item> items;
    private Color font;
    private Color background;
    private Color playerColor;
    private Color roomColor;
    private Color pathColor;
    private boolean invOpen = false;
    private boolean pickUp = false;
    private int itemSelected = 0;
    private boolean justPickedUp = false;
    private int itemInv = 0;

    public Game(String[] map, Player player) { //Création du jeu
        super(); //Utilisation de JFrame et de AsciiPanel
        this.map = map;
        this.player = player;
        enemies = new ArrayList<>();
        items = new ArrayList<>();
        terminal = new AsciiPanel(170, 85, AsciiFont.TALRYTH_15_15); //Taille de la fenêtre + police
        addKeyListener(this); //Ajout de l'écouteur de touches
        font = new Color(0, 255, 0);
        background = new Color(0, 0, 0);
        playerColor = new Color(255, 255, 0);
        roomColor = new Color(0, 0, 0);
        pathColor = new Color(0, 0, 0);
        aff();
    }

    public void aff() { //Affichage de la fenêtre
        for (int i = 0 ; i < 84 ; i++) {
            for (int j = 0 ; j < 170 ; j++) {
                terminal.write(Character.toString(32), j, i, font, background);
            }
        }

        for (int y = 0; y < map.length; y++)
            for (int x = 0; x < map[0].length(); x++)
                if (map[y].charAt(x) == '|')
                    terminal.write(Character.toString(179), x, y, font, background);
                else if (map[y].charAt(x) == '-')
                    terminal.write(Character.toString(196), x, y, font, background);
                else if (map[y].charAt(x) == '1')
                    terminal.write(Character.toString(218), x, y, font, background);
                else if (map[y].charAt(x) == '2')
                    terminal.write(Character.toString(191), x, y, font, background);
                else if (map[y].charAt(x) == '3')
                    terminal.write(Character.toString(192), x, y, font, background);
                else if (map[y].charAt(x) == '4')
                    terminal.write(Character.toString(217), x, y, font, background);
                else if (map[y].charAt(x) == '.')
                    terminal.write(Character.toString(32), x, y, font, roomColor);
                else if (map[y].charAt(x) == '*')
                    terminal.write(Character.toString(32), x, y, font, pathColor);
                else
                    terminal.write(Character.toString(32), x, y, font, background);
        terminal.write(Character.toString(1), player.x, player.y, playerColor, roomColor);

        for (Enemy enemy : enemies) {
            affEnemy(enemy);
        }

        for (Item item : items) {
            affItem(item);
        }

        //Génération stats + déco
        for (int i = 1 ; i < 84 ; i++) {
            terminal.write(Character.toString(186), 137, i, font, background);
            terminal.write(Character.toString(179), 0, i, font, background);
            terminal.write(Character.toString(179), 169, i, font, background);
        }
        for (int i = 1 ; i < 169 ; i++) {
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

        terminal.write(player.getName(), 140, 2, font, background);
        for (int i = 0 ; i < player.getName().length() ; i++)
            terminal.write(Character.toString(196), 140 + i, 3, font, background);
        terminal.write("HP: " + player.getHp(), 140, 7, Color.red, background);
        terminal.write("Mana: " + player.getMana(), 140, 9, new Color(100, 0, 255), background);
        terminal.write("Attack: " + player.atk, 140, 11, new Color(255, 115, 0), background);
        terminal.write("Defense: " + player.dfs, 140, 13, new Color(0, 128, 255), background);
        terminal.write("Coins: " + player.getCoins(), 140, 15, new Color(255, 255, 0), background);
        terminal.write("Level: " + player.getLevel(), 140, 19, font, background);

        for (int i = 0 ; i < 31 ; i++)
            terminal.write(Character.toString(196), 138 + i, 21, font, background);
        terminal.write(Character.toString(199), 137, 21, font, background);
        terminal.write(Character.toString(180), 169, 21, font, background);

        add(terminal);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
        requestFocus();
    }

    public void affInv() {
        if (!invOpen)
            aff();
        else {
            clearSideAff();
            terminal.write("Inventory: (0 /10)", 140, 25, font, background);
            if (player.inv.size() != 10)
                terminal.write(String.valueOf(player.inv.size()), 153, 25, font, background);
            else
                terminal.write("10", 152, 25, font, background);

            terminal.write("[A] to drop an item", 140, 65, font, background);

            if (player.inv.size() != 0) {
                for (int i = 0; i < player.inv.size(); i++) {
                    if (i == itemInv)
                        terminal.write("> " + player.inv.get(i).getName(), 140, 28 + 2*i, font, background);
                    else
                        terminal.write(player.inv.get(i).getName() + "  ", 140, 28 + 2*i, font, background);
                }
            } else {
                terminal.write("Nothing seems to be here...", 140, 28, font, background);
            }

            System.out.println("ping" + invOpen);
            add(terminal);
            terminal.repaint();
        }
    }

    //Méthode très importante
    public void updateAff() { //Mise à jour de l'affichage après un déplacement + test de la case sur laquelle le joueur se trouve
        if (map[player.y].charAt(player.x) == '.')
            terminal.write(Character.toString(32), player.x, player.y, font, roomColor);
        else if (map[player.y].charAt(player.x) == '*')
            terminal.write(Character.toString(32), player.x, player.y, font, pathColor);

        if (map[player.y + y].charAt(player.x + x) == '.')
            terminal.write(Character.toString(1), player.x + x, player.y + y, playerColor, roomColor);
        else if (map[player.y + y].charAt(player.x + x) == '*')
            terminal.write(Character.toString(1), player.x + x, player.y + y, playerColor, pathColor);

        add(terminal);
        terminal.repaint();

        for (int i = 0 ; i < items.size() ; i++) {
            if (items.get(i).x == player.x+x && items.get(i).y == player.y+y) {
                for (int j = 0 ; j < 6 ; j++) {
                    terminal.write(Character.toString(2 - j%2), player.x + x, player.y + y, playerColor, background);
                    add(terminal);
                    terminal.paintImmediately(terminal.getBounds());
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 140 ; j < 169 ; j++)
                    terminal.write(Character.toString(32), j, 25, font, background);
                pickUp = true;
                itemSelected = i;
                clearSideAff();
                affMsg("You found a " + items.get(i).name, 140, 25);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                affMsg("Do you want to pick it up ?", 140, 29);
                affMsg("Press [E] to Pick-Up", 140, 31);
                affMsg("Press [R] to Ignore", 140, 32);
                //Laisse le jeu dans un état ou les seuls inputs possibles sont E ou R
            }
        }
    }

    public void clearSideAff() {
        for (int i = 25 ; i < 84 ; i++)
            for (int j = 140 ; j < 169 ; j++)
                terminal.write(Character.toString(32), j, i);
    }

    public void pickedUp(char keyPressed, int i) { //Méthode pour ramasser un item, called seulement lors d'un input E ou R
        clearSideAff();
        justPickedUp = true;
        if (keyPressed == 'E') {
            if (player.inv.size() < 10) {
                player.inv.add(items.get(i));
                affMsg("You picked up the " + items.get(i).name, 140, 25);
                items.remove(i);
            } else {
                affMsg("You can't carry more than 10 items", 140, 25);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                affMsg("You decided to let", 140, 27);
                affMsg("the " + items.get(i).name, 140, 28);
            }
        } else {
            affMsg("You decided to let", 140, 25);
            affMsg("the " + items.get(i).name, 140, 26);
        }
    }

    public void affMsg(String msg, int x, int y) { //Méthode pour afficher un message avec un effet de défilement
        for (int i = 0 ; i < msg.length() ; i++) {
            terminal.write(msg.charAt(i), x + i, y, font, background);
            add(terminal);
            terminal.paintImmediately(terminal.getBounds());
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        add(terminal);
        terminal.repaint();
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
        affEnemy(enemy);
    }

    public void affEnemy(Enemy enemy) {
        terminal.write(Character.toString(232), enemy.x, enemy.y, new Color(255, 0, 0), roomColor);
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

    //On touche pas ce qui est en dessous

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //Méthode très importante
    @Override
    public void keyPressed(KeyEvent e) { //Méthode pour gérer les inputs
        if (!pickUp) {
            if (e.getKeyCode() == KeyEvent.VK_I) {
                itemInv = 0;
                invOpen = !invOpen;
                affInv();
            }
            if(!invOpen) {
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
                        for (int i = 0; i < items.size(); i++)
                            affItem(items.get(i));
                    }
                }
            } else {//Si inv est open
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    itemInv--;
                    if (itemInv == -1)
                        itemInv = player.inv.size()-1;
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
            }
        } else {//si on est sur un item
            if (e.getKeyCode() == KeyEvent.VK_E) {
                pickUp = false;
                pickedUp('E', itemSelected);
            }
            if (e.getKeyCode() == KeyEvent.VK_R) {
                pickUp = false;
                pickedUp('R', itemSelected);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
        }
    }

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

    public String[] getMap() {
        return map;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}