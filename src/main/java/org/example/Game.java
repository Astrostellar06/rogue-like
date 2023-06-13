package org.example;

import javax.swing.*;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Game extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1060623638149583738L;
    private AsciiPanel terminal;
    private int x = 0;
    private int y = 0;
    String[] map;
    Player player;
    ArrayList<Enemy> enemies;
    ArrayList<Item> items;
    Color font;
    Color background;
    Color playerColor;
    Color roomColor;
    Color pathColor;

    public Game(String[] map, Player player) { //Création du jeu et affichage de la fenêtre
        super(); //Utilisation de JFrame et de AsciiPanel
        this.map = map;
        this.player = player;
        enemies = new ArrayList<>();
        items = new ArrayList<>();
        terminal = new AsciiPanel(170, 85, AsciiFont.CP437_8x8); //Taille de la fenêtre + police
        addKeyListener(this); //Ajout de l'écouteur de touches
        font = new Color(0, 255, 0);
        background = new Color(0, 0, 0);
        playerColor = new Color(255, 255, 0);
        roomColor = new Color(0, 0, 0);
        pathColor = new Color(0, 0, 0);
        aff();
    }

    public void aff() {
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

        //Génération stats
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
        for (int i = 0 ; i < player.getName().length() ; i++) {
            terminal.write(Character.toString(196), 140 + i, 3, font, background);
        }
        terminal.write("HP: " + player.getHp(), 140, 7, font, background);
        terminal.write("Mana: " + player.getMana(), 140, 9, font, background);

        terminal.write("Attack: " + player.atk, 140, 11, font, background);
        terminal.write("Defense: " + player.dfs, 140, 13, font, background);
        terminal.write("Level: " + player.getLevel(), 140, 17, font, background);










        add(terminal);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateAff() {
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
                player.inv.add(items.get(i));
                for (int j = 0 ; j < 6 ; j++) {
                    terminal.write(Character.toString(2 - j%2), player.x + x, player.y + y, playerColor, background);
                    add(terminal);
                    terminal.paintImmediately(terminal.getBounds()); //On force le repaint
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (int j = 140 ; j < 168 ; j++) {
                    terminal.write(Character.toString(32), j, 21, font, background);
                }
                affMsg("You picked up a " + items.get(i).name, 140, 21);
                items.remove(i);
            }
        }
    }

    public void affMsg(String msg, int x, int y) {
        for (int i = 0 ; i < msg.length() ; i++) {
            terminal.write(msg.charAt(i), x + i, y, font, background);
            add(terminal);
            terminal.paintImmediately(terminal.getBounds()); //On force le repaint
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

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_RIGHT)
            x = 1;
        else if (e.getKeyCode() == KeyEvent.VK_LEFT)
            x = -1;
        else if (e.getKeyCode() == KeyEvent.VK_UP)
            y = -1;
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
            y = 1;
        player.move(x, y, this);
        x = 0;
        y = 0;
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