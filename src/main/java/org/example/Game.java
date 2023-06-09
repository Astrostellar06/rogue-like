package org.example;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JFrame implements KeyListener {
    private static final long serialVersionUID = 1060623638149583738L;
    private AsciiPanel terminal;
    private int x = 0;
    private int y = 0;
    String[] map;
    Player player;
    Color font;
    Color background;
    Color playerColor;
    Color roomColor;
    Color pathColor;

    public Game(String[] map, Player player) { //Création du jeu et affichage de la fenêtre
        super(); //Utilisation de JFrame et de AsciiPanel
        this.map = map;
        this.player = player;
        terminal = new AsciiPanel(170, 85, AsciiFont.CP437_12x12); //Taille de la fenêtre + police
        addKeyListener(this); //Ajout de l'écouteur de touches

        font = new Color(0, 255, 0);
        background = new Color(0, 0, 0);
        playerColor = new Color(255, 0, 0);
        roomColor = new Color(0, 0, 0);
        pathColor = new Color(0, 0, 0);

        for (int i = 0 ; i < 84 ; i++) {
            for (int j = 0 ; j < 170 ; j++) {
                terminal.write(Character.toString(32), j, i, font, background);
            }
        }

        //Génération de la map
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
        terminal.write(Character.toString(1), player.getX(), player.getY(), playerColor, roomColor);

        //Génération stats
        for (int i = 1 ; i < 84 ; i++) {
            terminal.write(Character.toString(186), 137, i, font, background);
            terminal.write(Character.toString(179), 0, i, font, background);
            terminal.write(Character.toString(179), 169, i, font, background);
        }
        for (int i = 1 ; i < 170 ; i++) {
            terminal.write(Character.toString(196), i, 84, font, background);
            terminal.write(Character.toString(196), i, 0, font, background);
        }
        terminal.write(Character.toString(210), 137, 0, font, background);
        terminal.write(Character.toString(208), 137, 84, font, background);
        terminal.write(Character.toString(218), 0, 0, font, background);
        terminal.write(Character.toString(191), 169, 0, font, background);
        terminal.write(Character.toString(192), 0, 84, font, background);
        terminal.write(Character.toString(217), 169, 84, font, background);

        terminal.write(player.getName(), 140, 2, font, background);
        for (int i = 0 ; i < player.getName().length() ; i++) {
            terminal.write(Character.toString(196), 140 + i, 3, font, background);
        }
        terminal.write("HP: " + player.getHp(), 140, 5, font, background);









        add(terminal);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void updateAff() {
        if (map[player.getY()].charAt(player.getX()) == '.')
            terminal.write(Character.toString(32), player.getX(), player.getY(), font, roomColor);
        else if (map[player.getY()].charAt(player.getX()) == '*')
            terminal.write(Character.toString(32), player.getX(), player.getY(), font, pathColor);

        if (map[player.getY() + y].charAt(player.getX() + x) == '.')
            terminal.write(Character.toString(1), player.getX() + x, player.getY() + y, playerColor, roomColor);
        else if (map[player.getY() + y].charAt(player.getX() + x) == '*')
            terminal.write(Character.toString(1), player.getX() + x, player.getY() + y, playerColor, pathColor);

        add(terminal);
        terminal.repaint();
    }

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