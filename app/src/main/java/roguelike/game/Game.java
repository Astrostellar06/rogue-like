package roguelike.game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

import asciiPanel.AsciiPanel;
import roguelike.Assets;
import roguelike.models.*;
import roguelike.utils.Constants;
import roguelike.utils.FileConfiguration;
import roguelike.utils.MapGenerator;

public class Game extends JFrame implements KeyListener {

    public Game(AsciiPanel terminal, boolean newGame) { //Création du jeu
        super(); //Utilisation de JFrame et de AsciiPanel
        Constants.terminal = terminal;
        if (newGame) {
            Constants.data = new Data();
            Constants.data.enemies = new ArrayList<>();
            Constants.data.items = new ArrayList<>();
            Constants.data.coins = new ArrayList<>();
            Constants.data.stats = new int[7];
            Constants.getTheme();
            Constants.getPlayer();


            for (int i = 0 ; i < 10; i++) {
                addItem(new Weapon());
                addItem(new Shield());
                addItem(new Potion());
            }

            for (int i = 0 ; i < 5 ; i++) {
                addEnemy(new Enemy(1));
                addEnemy(new Enemy(2));
                addEnemy(new Enemy(3));
                addEnemy(new Enemy(4));
            }

            Constants.data.player.getSpells().add(new Spell(1));
            Constants.data.player.getSpells().add(new Spell(2));
            Constants.data.player.getSpells().add(new Spell(3));
            Constants.data.player.getSpells().add(new Spell(4));
            Constants.data.player.getSpells().add(new Spell(5));
            Constants.data.player.getSpells().add(new Spell(6));
            Constants.data.player.getSpells().add(new Spell(7));

            addCoins(60);
        } else {
            FileConfiguration.load();
        }

        addKeyListener(this); //Ajout de l'écouteur de touches
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                FileConfiguration.save();
                System.exit(0);
            }
        });


        aff();
    }



    public void aff() { //Affichage de la fenêtre
        for (int i = 0; i < 85; i++) {
            for (int j = 0; j < 170; j++) {
                Constants.terminal.write(Character.toString(32), j, i, Constants.data.font, Constants.data.background);
            }
        }

        for (Room room : Constants.data.listRooms)
            affRooms(room);

        Constants.terminal.write(Character.toString(1), Constants.data.player.getX(), Constants.data.player.getY(), Constants.data.playerColor, Constants.data.roomColor);

        for (Enemy enemy : Constants.data.enemies)
            Constants.terminal.write(Character.toString(234), enemy.getX(), enemy.getY(), enemy.getColor(), Constants.data.roomColor);

        for (Item item : Constants.data.items)
            Constants.terminal.write(Character.toString(235), item.getX(), item.getY(), item.getColorItem(), Constants.data.roomColor);

        affCoins();
        deco();
        affStats(Constants.data.player);

        for (int i = 0; i < 31; i++)
            Constants.terminal.write(Character.toString(196), 138 + i, 31, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(199), 137, 31, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(180), 169, 31, Constants.data.font, Constants.data.background);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
        requestFocus();
    }

    public void deco() {
        for (int i = 1; i < 84; i++) {
            Constants.terminal.write(Character.toString(186), 137, i, Constants.data.font, Constants.data.background);
            Constants.terminal.write(Character.toString(179), 0, i, Constants.data.font, Constants.data.background);
            Constants.terminal.write(Character.toString(179), 169, i, Constants.data.font, Constants.data.background);
        }
        for (int i = 1; i < 169; i++) {
            Constants.terminal.write(Character.toString(196), i, 84, Constants.data.font, Constants.data.background);
            Constants.terminal.write(Character.toString(196), i, 0, Constants.data.font, Constants.data.background);
        }
        Constants.terminal.write(Character.toString(210), 137, 0, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(208), 137, 84, Constants.data.font, Constants.data.background);

        Constants.terminal.write(Character.toString(218), 1, 0, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(218), 0, 1, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(217), 1, 1, Constants.data.font, Constants.data.background);

        Constants.terminal.write(Character.toString(191), 168, 0, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(191), 169, 1, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(192), 168, 1, Constants.data.font, Constants.data.background);

        Constants.terminal.write(Character.toString(192), 1, 84, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(192), 0, 83, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(191), 1, 83, Constants.data.font, Constants.data.background);

        Constants.terminal.write(Character.toString(217), 168, 84, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(217), 169, 83, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(218), 168, 83, Constants.data.font, Constants.data.background);
    }

    public void affRooms(Room room) {
        for (int cy = 0; cy < room.getRoom().length; cy++) {
            char[] line = room.getRoom()[cy].toCharArray();
            for (int cx = 0; cx < line.length; cx++) {
                if (line[cx] == '|')
                    Constants.terminal.write(Character.toString(179), room.getX() * 17 + cx, room.getY() * 17 + cy, Constants.data.font, Constants.data.background);
                else if (line[cx] == '-')
                    Constants.terminal.write(Character.toString(196), room.getX() * 17 + cx, room.getY() * 17 + cy, Constants.data.font, Constants.data.background);
                else if (line[cx] == '1')
                    Constants.terminal.write(Character.toString(218), room.getX() * 17 + cx, room.getY() * 17 + cy, Constants.data.font, Constants.data.background);
                else if (line[cx] == '2')
                    Constants.terminal.write(Character.toString(191), room.getX() * 17 + cx, room.getY() * 17 + cy, Constants.data.font, Constants.data.background);
                else if (line[cx] == '3')
                    Constants.terminal.write(Character.toString(192), room.getX() * 17 + cx, room.getY() * 17 + cy, Constants.data.font, Constants.data.background);
                else if (line[cx] == '4')
                    Constants.terminal.write(Character.toString(217), room.getX() * 17 + cx, room.getY() * 17 + cy, Constants.data.font, Constants.data.background);
                else if (line[cx] == '.')
                    Constants.terminal.write(Character.toString(32), room.getX() * 17 + cx, room.getY() * 17 + cy, Constants.data.font, Constants.data.roomColor);
                else if (line[cx] == '*')
                    Constants.terminal.write(Character.toString(32), room.getX() * 17 + cx, room.getY() * 17 + cy, Constants.data.font, Constants.data.pathColor);
                else
                    Constants.terminal.write(Character.toString(32), room.getX() * 17 + cx, room.getY() * 17 + cy, Constants.data.font, Constants.data.background);
            }
        }
    }

    public void affStats(Entity target) {
        int b = 1;
        Entity entity;
        int dx = 140;
        int dy = 0;
        if (Constants.data.inAttack) {
            b = 2;
            dy = 60;
            dx = 120;
        }
        for (int a = 0 ; a < b ; a++) {
            if (a == 0) {
                entity = Constants.data.player;
            } else {
                entity = target;
                dx = 145;
                dy = 60;
            }

            Constants.terminal.write(entity.getName(), dx, dy + 2, entity instanceof Player ? Constants.data.font : ((Enemy) entity).getColor(), Constants.data.background);
            for (int i = 0; i < entity.getName().length(); i++)
                Constants.terminal.write(Character.toString(196), dx + i, dy + 3, entity instanceof Player ? Constants.data.font : ((Enemy) entity).getColor(), Constants.data.background);

            Constants.terminal.write("HP: " + entity.getHp() + "/" + entity.getHpMax() + "  ", dx, dy+7, Color.red, Constants.data.background);
            Constants.terminal.write("Attack: " + entity.getAtk() + "  ", dx, dy+9, new Color(255, 115, 0), Constants.data.background);
            Constants.terminal.write("Defense: " + entity.getDef() + "  ", dx, dy+11, new Color(0, 128, 255), Constants.data.background);
            if (entity instanceof Player) {
                Constants.terminal.write("Mana: " + Constants.data.player.getMana() + "/" + Constants.data.player.getManaMax() + "  ", dx, dy + 13, new Color(153, 0, 255), Constants.data.background);
                Constants.terminal.write("Mana regen: " + Constants.data.player.getManaRegen() + "  ", dx, dy + 15, new Color(153, 0, 255), Constants.data.background);
            } else
                dy -= 4;
            Constants.terminal.write("Magic defense: " + entity.getMagicDef() + "  ", dx, dy+17, new Color(94, 0, 255), Constants.data.background);
            Constants.terminal.write("Crit chance: " + entity.getCritChance() + "  ", dx, dy+19, new Color(41, 168, 33), Constants.data.background);
        }
        if (!Constants.data.inAttack) {
            Constants.terminal.write("Coins: " + Constants.data.player.getCoins() + "  ", 140, 21, new Color(255, 204, 0), Constants.data.background);
            int x = 0;
            for (int i = 1; i <= Constants.data.player.getLevel(); i++) {
                x += Game.getXpNeeded(i);
            }
            Constants.terminal.write("Level: " + Constants.data.player.getLevel() + "  (" + (x - Game.getXpNeeded(Constants.data.player.getLevel()) + Constants.data.player.getXp()) + "/" + x + ")  ", 140, 25, Constants.data.font, Constants.data.background);

            Constants.terminal.write(Character.toString(60), 140, 27, Constants.data.font, Constants.data.background);
            Constants.terminal.write(Character.toString(62), 166, 27, Constants.data.font, Constants.data.background);
            for (int i = 141; i < 166; i++)
                Constants.terminal.write(Character.toString(196), i, 27, Constants.data.font, Constants.data.background);
            for (int i = 141; i < 141 + 25 * Constants.data.player.getXp() / Game.getXpNeeded(Constants.data.player.getLevel()); i++)
                Constants.terminal.write(Character.toString(219), i, 27, Constants.data.font, Constants.data.background);
        }
        add(Constants.terminal);
        Constants.terminal.repaint();
    }

    public static int getXpNeeded(int x) {
        return (int) (Math.pow(x, 2) * 10);
    }
    //Méthode très importante
    public void updateAff() { //Mise à jour de l'affichage après un déplacement + test de la case sur laquelle le joueur se trouve
        if (charRoom(Constants.data.player.getX(), Constants.data.player.getY()) == '.')
            Constants.terminal.write(Character.toString(32), Constants.data.player.getX(), Constants.data.player.getY(), Constants.data.font, Constants.data.roomColor);
        else if (charRoom(Constants.data.player.getX(), Constants.data.player.getY()) == '*')
            Constants.terminal.write(Character.toString(32), Constants.data.player.getX(), Constants.data.player.getY(), Constants.data.font, Constants.data.pathColor);

        if (charRoom(Constants.data.player.getX()+Constants.data.x, Constants.data.player.getY()+Constants.data.y) == '.')
            Constants.terminal.write(Character.toString(1), Constants.data.player.getX() + Constants.data.x, Constants.data.player.getY() + Constants.data.y, Constants.data.playerColor, Constants.data.roomColor);
        else if (charRoom(Constants.data.player.getX()+Constants.data.x, Constants.data.player.getY()+Constants.data.y) == '*')
            Constants.terminal.write(Character.toString(1), Constants.data.player.getX() + Constants.data.x, Constants.data.player.getY() + Constants.data.y, Constants.data.playerColor, Constants.data.pathColor);

        add(Constants.terminal);
        for (int i = 0; i < Constants.data.enemies.size(); i++) {
            if (Constants.data.enemies.get(i).getX() == Constants.data.player.getX() + Constants.data.x && Constants.data.enemies.get(i).getY() == Constants.data.player.getY() + Constants.data.y) {
                Constants.data.enemyAttacked = Constants.data.enemies.get(i);
                affAttack(Constants.data.enemyAttacked);
            }
        }

        if (!Constants.data.inAttack) {
            for (int i = 0; i < Constants.data.coins.size(); i++) {
                if (Constants.data.coins.get(i).getX() == Constants.data.player.getX() + Constants.data.x && Constants.data.coins.get(i).getY() == Constants.data.player.getY() + Constants.data.y) {
                    Constants.data.coins.remove(i);
                    Constants.data.player.setCoins(Constants.data.player.getCoins()+1);
                    clearSideAff();
                    Constants.terminal.write("+1 coin", 140, 35, Constants.data.font, Constants.data.background);
                    add(Constants.terminal);
                    Constants.terminal.repaint();
                    affStats(Constants.data.player);
                }
            }

            Moves.moveEnemies();
            if (!Constants.data.inAttack) {
                affAllItems();
                affCoins();
            }
        }

        if (!Constants.data.inAttack) { //Je refais le test, car moveEnemies() peut changer la valeur de Constants.data.inAttack
            for (int i = 0; i < Constants.data.items.size(); i++) {
                if (Constants.data.items.get(i).getX() == Constants.data.player.getX() + Constants.data.x && Constants.data.items.get(i).getY() == Constants.data.player.getY() + Constants.data.y) {
                    for (int j = 0; j < 6; j++) {
                        Constants.terminal.write(Character.toString(2 - j % 2), Constants.data.player.getX() + Constants.data.x, Constants.data.player.getY() + Constants.data.y, Constants.data.playerColor, Constants.data.background);
                        add(Constants.terminal);
                        Constants.terminal.paintImmediately(Constants.terminal.getBounds());
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int j = 140; j < 169; j++)
                        Constants.terminal.write(Character.toString(32), j, 35, Constants.data.font, Constants.data.background);
                    Constants.data.pickUp = true;
                    Constants.data.itemSelected = i;
                    clearSideAff();
                    affMsg("You found a " + Constants.data.items.get(i).getName(), 140, 35);
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

    public void affAttack(Enemy enemy) {
        Constants.data.inAttack = true;
        Constants.data.waitingForAttack = true;
        Constants.data.stats[0] = Constants.data.player.getHpMax();
        Constants.data.stats[1] = Constants.data.player.getAtk();
        Constants.data.stats[2] = Constants.data.player.getDef();
        Constants.data.stats[3] = Constants.data.player.getMagicDef();
        Constants.data.stats[4] = Constants.data.player.getCritChance();
        Constants.data.stats[5] = Constants.data.player.getManaMax();
        Constants.data.stats[6] = Constants.data.player.getManaRegen();
        clearSideAff();
        affMsg("You are attacked ", 140, 35);
        affMsg("by a " + enemy.getName(), 140, 36);
        affMsg("Press [Enter] to Attack", 140, 39);
        Constants.data.tempsInactif = System.currentTimeMillis();
    }
    public void attack(Enemy enemy) {
        Constants.data.attackSelected = 1;
        Constants.data.waitingForAttack = false;
        for (int i = 1 ; i < 169 ; i++) {
            for (int j = 1 ; j < 84 ; j++) {
                Constants.terminal.write(" ", i, j, Constants.data.font, Constants.data.background);
            }
        }

        for (int i = 1 ; i < 169 ; i++) {
            Constants.terminal.write(Character.toString(196), i, 60, Constants.data.font, Constants.data.background);
        }
        Constants.terminal.write(Character.toString(195), 0, 60, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(180), 169, 60, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(196), 137, 0, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(196), 137, 84, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(217), 1, 1, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(192), 168, 1, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(179), 169, 31, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(191), 1, 83, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(218), 168, 83, Constants.data.font, Constants.data.background);
        for (int i = 61 ; i < 84 ; i++) {
            Constants.terminal.write(Character.toString(179), 142, i, Constants.data.font, Constants.data.background);
            Constants.terminal.write(Character.toString(186), 117, i, Constants.data.font, Constants.data.background);
        }
        Constants.terminal.write(Character.toString(194), 142, 60, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(210), 117, 60, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(193), 142, 84, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(208), 117, 84, Constants.data.font, Constants.data.background);

        for (int i = 0 ; i < 6 ; i++) {
            Constants.terminal.write(Assets.attack[i], 13, 63 + i, Constants.data.font, Constants.data.background);
            Constants.terminal.write(Assets.item[i], 68, 63 + i, Constants.data.font, Constants.data.background);
            Constants.terminal.write(Assets.selected[i], 7, 63 + i, Constants.data.font, Constants.data.background);
        }

        for (int i = 0 ; i < 59 ; i++)
            Constants.terminal.write(Assets.knight[i], 2, 1+i, Constants.data.font, Constants.data.background);
        switch (enemy.getName()) {
            case "Goblin":
                for (int i = 0 ; i < 21 ; i++)
                    Constants.terminal.write(Assets.goblin[i], 100, 20+i, Constants.data.font, Constants.data.background);
                break;
            case "Skeleton":
                for (int i = 0 ; i < 38 ; i++)
                    Constants.terminal.write(Assets.skeleton[i], 100, 10+i, Constants.data.font, Constants.data.background);
                break;
            case "Reaper":
                for (int i = 0 ; i < 39 ; i++)
                    Constants.terminal.write(Assets.reaper[i], 100, 10+i, Constants.data.font, Constants.data.background);
                break;
            case "Dragon":
                for (int i = 0 ; i < 52 ; i++)
                    Constants.terminal.write(Assets.dragon[i], 85, 5+i, Constants.data.font, Constants.data.background);
                break;

        }
        for (int i = 0 ; i < 8 ; i++)
            Constants.terminal.write(Assets.spell[i], 13, 73 + i, Constants.data.font, Constants.data.background);
        affStats(enemy);
        Constants.data.tempsInactif = System.currentTimeMillis();
    }

    public void affSelection() {
        int dx = 7;
        int dy = 63;
        if (Constants.data.attackSelected == 2)
            dy = 73;
        else if (Constants.data.attackSelected == 3)
            dx = 62;
        for (int i = 0 ; i < 6 ; i++) {
            Constants.terminal.write("      ", 7, 63 + i, Constants.data.font, Constants.data.background);
            Constants.terminal.write("      ", 62, 63 + i, Constants.data.font, Constants.data.background);
            Constants.terminal.write("      ", 7, 73 + i, Constants.data.font, Constants.data.background);
        }
        for (int i = 0 ; i < 6 ; i++)
            Constants.terminal.write(Assets.selected[i], dx, dy + i, Constants.data.font, Constants.data.background);
        add(Constants.terminal);
        Constants.terminal.repaint();
    }

    public void attack2(Enemy enemy) {
        if (Constants.data.attackSelected == 1) {
            attackPlayer(enemy);
        } else {
            Constants.data.spellSelected = 0;
            Constants.data.waitingForChoice = true;
            affSpellSelected();
        }
    }

    public void attackPlayer(Enemy enemy) {
        clearBottomAff();
        if (Constants.data.attackSelected == 1) {
            String weapon = "fists";
            for (Item item : Constants.data.player.getInv()) {
                if (item instanceof Weapon && item.isEquipped()) {
                    weapon = item.getName();
                    break;
                }
            }
            Random rand = new Random();
            int crit = rand.nextInt(1, 101);
            if (crit <= Constants.data.player.getCritChance())
                enemy.setHp(enemy.getHp() - (Math.max(Constants.data.player.getAtk()*2 - enemy.getDef(), 0)));
            else
                enemy.setHp(enemy.getHp() - (Math.max(Constants.data.player.getAtk() - enemy.getDef(), 0)));            if (enemy.getHp() <= 0)
                enemy.setHp(0);
            affStats(enemy);
            affMsg("You attack the " + enemy.getName() + " with your " + weapon + " and deal " + (crit <= Constants.data.player.getCritChance() ? Constants.data.player.getAtk()*2 : Constants.data.player.getAtk()) + " damage.", 7, 64);
            if (crit <= Constants.data.player.getCritChance()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                affMsg("Critical Hit!", 7, 66);
            }
            Constants.data.waitingForEnemy = true;
        } else if (Constants.data.attackSelected == 2) {
            if (Constants.data.player.getSpells().get(Constants.data.spellSelected).getManaCost() > Constants.data.player.getMana()) {
                clearBottomAff();
                affMsg("You don't have enough mana to cast this spell.", 7, 64);
                Constants.data.waitingForChoice = false;
                Constants.data.waitingForAttack = true;
            } else {
                Constants.data.player.setMana(Constants.data.player.getMana() - Constants.data.player.getSpells().get(Constants.data.spellSelected).getManaCost());
                Constants.data.player.setHp(Constants.data.player.getHp() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getHpPlayer());
                Constants.data.player.setHpMax(Constants.data.player.getHpMax() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getHpMaxPlayer());
                if (Constants.data.player.getHp() > Constants.data.player.getHpMax())
                    Constants.data.player.setHp(Constants.data.player.getHpMax());
                if (Constants.data.player.getHp() < 0)
                    Constants.data.player.setHp(0);
                Constants.data.player.setAtk(Constants.data.player.getAtk() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getAtkPlayer());
                if (Constants.data.player.getAtk() < 1)
                    Constants.data.player.setAtk(1);
                Constants.data.player.setDef(Constants.data.player.getDef() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getDefPlayer());
                if (Constants.data.player.getDef() < 0)
                    Constants.data.player.setDef(0);
                Constants.data.player.setMagicDef(Constants.data.player.getMagicDef() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getMagicDefPlayer());
                if (Constants.data.player.getMagicDef() < 0)
                    Constants.data.player.setMagicDef(0);
                Constants.data.player.setCritChance(Constants.data.player.getCritChance() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getCritChancePlayer());
                if (Constants.data.player.getCritChance() < 0)
                    Constants.data.player.setCritChance(0);
                if (Constants.data.player.getCritChance() > 100)
                    Constants.data.player.setCritChance(100);
                Constants.data.player.setManaMax(Constants.data.player.getManaMax() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getManaMaxPlayer());
                if (Constants.data.player.getManaMax() < 0)
                    Constants.data.player.setManaMax(0);
                Constants.data.player.setMana(Constants.data.player.getMana() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getManaPlayer());
                if (Constants.data.player.getMana() > Constants.data.player.getManaMax())
                    Constants.data.player.setMana(Constants.data.player.getManaMax());
                if (Constants.data.player.getMana() < 0)
                    Constants.data.player.setMana(0);
                Constants.data.player.setManaRegen(Constants.data.player.getManaRegen() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getManaRegenPlayer());
                if (Constants.data.player.getManaRegen() < 0)
                    Constants.data.player.setManaRegen(0);

                enemy.setHp(enemy.getHp() - Constants.data.player.getSpells().get(Constants.data.spellSelected).getHpEnemy());
                if (enemy.getHp() <= 0)
                    enemy.setHp(0);
                if (enemy.getHp() > enemy.getHpMax())
                    enemy.setHp(enemy.getHpMax());
                enemy.setAtk(enemy.getAtk() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getAtkEnemy());
                if (enemy.getAtk() < 1)
                    enemy.setAtk(1);
                enemy.setDef(enemy.getDef() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getDefEnemy());
                if (enemy.getDef() < 0)
                    enemy.setDef(0);
                enemy.setMagicDef(enemy.getMagicDef() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getMagicDefEnemy());
                if (enemy.getMagicDef() < 0)
                    enemy.setMagicDef(0);
                enemy.setCritChance(enemy.getCritChance() + Constants.data.player.getSpells().get(Constants.data.spellSelected).getCritChanceEnemy());
                if (enemy.getCritChance() < 0)
                    enemy.setCritChance(0);
                if (enemy.getCritChance() > 100)
                    enemy.setCritChance(100);

                affStats(enemy);
                affMsg("You decided to cast " + Constants.data.player.getSpells().get(Constants.data.spellSelected).getName() + ".", 7, 64);

                Constants.data.waitingForChoice = false;
                Constants.data.waitingForEnemy = true;
            }

        } else if (Constants.data.attackSelected == 3) {
            Potion potionUsed = null;
            int j = -1;
            for (int i = 0 ; i < Constants.data.player.getInv().size() ; i++) {
                if (Constants.data.player.getInv().get(i) instanceof Potion) {
                    j++;
                    if (Constants.data.spellSelected == j) {
                        potionUsed = (Potion) Constants.data.player.getInv().get(i);
                        Constants.data.player.getInv().remove(i);
                        break;
                    }
                }
            }
            Constants.data.player.setHpMax(Constants.data.player.getHpMax() + potionUsed.getHpMaxPlayer());
            Constants.data.player.setHp(Constants.data.player.getHp() + potionUsed.getHpPlayer());
            if (Constants.data.player.getHp() > Constants.data.player.getHpMax())
                Constants.data.player.setHp(Constants.data.player.getHpMax());
            if (Constants.data.player.getHp() < 0)
                Constants.data.player.setHp(0);
            Constants.data.player.setAtk(Constants.data.player.getAtk() + potionUsed.getAtkPlayer());
            if (Constants.data.player.getAtk() < 1)
                Constants.data.player.setAtk(1);
            Constants.data.player.setDef(Constants.data.player.getDef() + potionUsed.getDefPlayer());
            if (Constants.data.player.getDef() < 0)
                Constants.data.player.setDef(0);
            Constants.data.player.setMagicDef(Constants.data.player.getMagicDef() + potionUsed.getMagicDefPlayer());
            if (Constants.data.player.getMagicDef() < 0)
                Constants.data.player.setMagicDef(0);
            Constants.data.player.setCritChance(Constants.data.player.getCritChance() + potionUsed.getCritChancePlayer());
            if (Constants.data.player.getCritChance() < 0)
                Constants.data.player.setCritChance(0);
            if (Constants.data.player.getCritChance() > 100)
                Constants.data.player.setCritChance(100);
            Constants.data.player.setManaMax(Constants.data.player.getManaMax() + potionUsed.getManaMaxPlayer());
            if (Constants.data.player.getManaMax() < 0)
                Constants.data.player.setManaMax(0);
            Constants.data.player.setMana(Constants.data.player.getMana() + potionUsed.getManaPlayer());
            if (Constants.data.player.getMana() > Constants.data.player.getManaMax())
                Constants.data.player.setMana(Constants.data.player.getManaMax());
            if (Constants.data.player.getMana() < 0)
                Constants.data.player.setMana(0);
            Constants.data.player.setManaRegen(Constants.data.player.getManaRegen() + potionUsed.getManaRegenPlayer());
            if (Constants.data.player.getManaRegen() < 0)
                Constants.data.player.setManaRegen(0);

            enemy.setHp(enemy.getHp() - potionUsed.getHpEnemy());
            if (enemy.getHp() <= 0)
                enemy.setHp(0);
            if (enemy.getHp() > enemy.getHpMax())
                enemy.setHp(enemy.getHpMax());
            enemy.setAtk(enemy.getAtk() + potionUsed.getAtkEnemy());
            if (enemy.getAtk() < 1)
                enemy.setAtk(1);
            enemy.setDef(enemy.getDef() + potionUsed.getDefEnemy());
            if (enemy.getDef() < 0)
                enemy.setDef(0);
            enemy.setMagicDef(enemy.getMagicDef() + potionUsed.getMagicDefEnemy());
            if (enemy.getMagicDef() < 0)
                enemy.setMagicDef(0);
            enemy.setCritChance(enemy.getCritChance() + potionUsed.getCritChanceEnemy());
            if (enemy.getCritChance() < 0)
                enemy.setCritChance(0);
            if (enemy.getCritChance() > 100)
                enemy.setCritChance(100);

            affStats(enemy);
            affMsg("You decided to use your " + potionUsed.getName() + ".", 7, 64);

            Constants.data.waitingForChoice = false;
            Constants.data.waitingForEnemy = true;
        }
    }

    public void affSpellSelected() {
        clearBottomAff();
        int dx = 4;
        int dy = 63;
        if (Constants.data.attackSelected == 3) {
            Constants.data.numberPotions = 0;
            for (int i = 0; i < Constants.data.player.getInv().size(); i++) {
                if (Constants.data.player.getInv().get(i) instanceof Potion) {
                    Constants.data.numberPotions++;
                    if (Constants.data.spellSelected == i) {
                        Constants.terminal.write("> " + Constants.data.player.getInv().get(i).getName() + "  ", dx, dy, Constants.data.font, Constants.data.background);
                        String[] potionDesc = new String[6];
                        potionDesc[0] = ("(" + ((Potion) Constants.data.player.getInv().get(i)).getHpPlayer() + "," + ((Potion) Constants.data.player.getInv().get(i)).getHpMaxPlayer() + ",");
                        potionDesc[1] = (((Potion) Constants.data.player.getInv().get(i)).getAtkPlayer() + ",");
                        potionDesc[2] = (((Potion) Constants.data.player.getInv().get(i)).getDefPlayer() + ",");
                        potionDesc[3] = (((Potion) Constants.data.player.getInv().get(i)).getManaPlayer() + "," + ((Potion) Constants.data.player.getInv().get(i)).getManaMaxPlayer() + "," + ((Potion) Constants.data.player.getInv().get(i)).getManaRegenPlayer() + ",");
                        potionDesc[4] = (((Potion) Constants.data.player.getInv().get(i)).getMagicDefPlayer() + ",");
                        potionDesc[5] = (((Potion) Constants.data.player.getInv().get(i)).getCritChancePlayer() + ")");

                        String[] potionDesc2 = new String[5];
                        potionDesc2[0] = ("(" + ((Potion) Constants.data.player.getInv().get(i)).getHpEnemy() + ",");
                        potionDesc2[1] = (((Potion) Constants.data.player.getInv().get(i)).getAtkEnemy() + ",");
                        potionDesc2[2] = (((Potion) Constants.data.player.getInv().get(i)).getDefEnemy() + ",");
                        potionDesc2[3] = (((Potion) Constants.data.player.getInv().get(i)).getMagicDefEnemy() + ",");
                        potionDesc2[4] = (((Potion) Constants.data.player.getInv().get(i)).getCritChanceEnemy() + ")");

                        affDesc(dx, dy, potionDesc, potionDesc2);
                    } else
                        Constants.terminal.write(Constants.data.player.getInv().get(i).getName() + "  ", dx, dy, Constants.data.font, Constants.data.background);

                    dx += 38;
                    if (dx > 80) {
                        dx = 4;
                        dy += 3;
                    }
                }
            }
        } else {
            for (int i = 0; i < Constants.data.player.getSpells().size(); i++) {
                if (Constants.data.spellSelected == i) {
                    Constants.terminal.write("> " + Constants.data.player.getSpells().get(i).getName() + " (" + Constants.data.player.getSpells().get(i).getManaCost() + " mana)  ", dx, dy, Constants.data.font, Constants.data.background);
                    String[] potionDesc = new String[6];
                    potionDesc[0] = ("(" + (Constants.data.player.getSpells().get(i)).getHpPlayer() + "," + ( Constants.data.player.getSpells().get(i)).getHpMaxPlayer() + ",");
                    potionDesc[1] = (( Constants.data.player.getSpells().get(i)).getAtkPlayer() + ",");
                    potionDesc[2] = (( Constants.data.player.getSpells().get(i)).getDefPlayer() + ",");
                    potionDesc[3] = (( Constants.data.player.getSpells().get(i)).getManaPlayer() + "," + ( Constants.data.player.getSpells().get(i)).getManaMaxPlayer() + "," + ( Constants.data.player.getSpells().get(i)).getManaRegenPlayer() + ",");
                    potionDesc[4] = (( Constants.data.player.getSpells().get(i)).getMagicDefPlayer() + ",");
                    potionDesc[5] = (( Constants.data.player.getSpells().get(i)).getCritChancePlayer() + ")");

                    String[] potionDesc2 = new String[5];
                    potionDesc2[0] = ("(" + ( Constants.data.player.getSpells().get(i)).getHpEnemy() + ",");
                    potionDesc2[1] = (( Constants.data.player.getSpells().get(i)).getAtkEnemy() + ",");
                    potionDesc2[2] = (( Constants.data.player.getSpells().get(i)).getDefEnemy() + ",");
                    potionDesc2[3] = (( Constants.data.player.getSpells().get(i)).getMagicDefEnemy() + ",");
                    potionDesc2[4] = (( Constants.data.player.getSpells().get(i)).getCritChanceEnemy() + ")");

                    affDesc(dx, dy, potionDesc, potionDesc2);
                } else
                    Constants.terminal.write(Constants.data.player.getSpells().get(i).getName() + " (" + Constants.data.player.getSpells().get(i).getManaCost() + " mana)  ", dx, dy, Constants.data.font, Constants.data.background);
                dx += 38;
                if (dx > 80) {
                    dx = 4;
                    dy += 3;
                }
            }
        }
        Constants.terminal.write("[Esc] to go back", 4, 81, Constants.data.font, Constants.data.background);
        add(Constants.terminal);
        Constants.terminal.repaint();
    }

    public void affDesc(int dx, int dy, String[] potionDesc, String[] potionDesc2) {
        Constants.terminal.write(potionDesc[0], dx, dy + 1, Color.red, Constants.data.background);
        Constants.terminal.write(potionDesc[1], dx + potionDesc[0].length(), dy + 1, new Color(255, 115, 0), Constants.data.background);
        Constants.terminal.write(potionDesc[2], dx + potionDesc[0].length() + potionDesc[1].length(), dy + 1, new Color(0, 128, 255), Constants.data.background);
        Constants.terminal.write(potionDesc[3], dx + potionDesc[0].length() + potionDesc[1].length() + potionDesc[2].length(), dy + 1, new Color(153, 0, 255), Constants.data.background);
        Constants.terminal.write(potionDesc[4], dx + potionDesc[0].length() + potionDesc[1].length() + potionDesc[2].length() + potionDesc[3].length(), dy + 1, new Color(94, 0, 255), Constants.data.background);
        Constants.terminal.write(potionDesc[5], dx + potionDesc[0].length() + potionDesc[1].length() + potionDesc[2].length() + potionDesc[3].length() + potionDesc[4].length(), dy + 1, new Color(41, 168, 33), Constants.data.background);
        Constants.terminal.write(potionDesc2[0], dx, dy + 2, Color.red, Constants.data.background);
        Constants.terminal.write(potionDesc2[1], dx + potionDesc2[0].length(), dy + 2, new Color(255, 115, 0), Constants.data.background);
        Constants.terminal.write(potionDesc2[2], dx + potionDesc2[0].length() + potionDesc2[1].length(), dy + 2, new Color(0, 128, 255), Constants.data.background);
        Constants.terminal.write(potionDesc2[3], dx + potionDesc2[0].length() + potionDesc2[1].length() + potionDesc2[2].length(), dy + 2, new Color(94, 0, 255), Constants.data.background);
        Constants.terminal.write(potionDesc2[4], dx + potionDesc2[0].length() + potionDesc2[1].length() + potionDesc2[2].length() + potionDesc2[3].length(), dy + 2, new Color(41, 168, 33), Constants.data.background);
    }

    public void attackEnemy(Enemy enemy) {
        if (enemy.getHp() == 0)
            winCombat(enemy);
        else if (Constants.data.player.getHp() <= 0)
            gameOver();
        else {
            clearBottomAff();
            Random rand = new Random();
            int crit = rand.nextInt(1, 101);
            if (crit <= enemy.getCritChance())
                Constants.data.player.setHp(Constants.data.player.getHp() - (Math.max(enemy.getAtk()*2 - Constants.data.player.getDef(), 0)));
            else
                Constants.data.player.setHp(Constants.data.player.getHp() - (Math.max(enemy.getAtk() - Constants.data.player.getDef(), 0)));
            if (Constants.data.player.getHp() <= 0)
                Constants.data.player.setHp(0);
            affStats(enemy);
            affMsg("The " + enemy.getName() + " attacks you and deals " + (crit <= enemy.getCritChance() ? enemy.getAtk() * 2 : enemy.getAtk()) + " damage.", 7, 64);
            if (crit <= enemy.getCritChance()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                affMsg("Critical Hit!", 7, 66);
            }
            Constants.data.waitingForEnemy = false;
            Constants.data.waitingForAttack = true;
            Constants.data.player.setMana(Constants.data.player.getMana() + Constants.data.player.getManaRegen());
            if (Constants.data.player.getMana() > Constants.data.player.getManaMax())
                Constants.data.player.setMana(Constants.data.player.getManaMax());
        }
    }

    public void gameOver() {
        for (int i = 1; i < 84; i++) {
            for (int j = 1; j < 169; j++) {
                Constants.terminal.write(Character.toString(32), j, i, Constants.data.font, Constants.data.background);
            }
        }
        Constants.terminal.write(Character.toString(217), 1, 1, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(192), 168, 1, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(191), 1, 83, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(218), 168, 83, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(179), 0, 60, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(179), 169, 60, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(196), 142, 84, Constants.data.font, Constants.data.background);
        Constants.terminal.write(Character.toString(196), 117, 84, Constants.data.font, Constants.data.background);

        for (int i = 30 ; i < 44 ; i++) {
            Constants.terminal.write(Assets.gameOver[i-30], 19, i, Constants.data.font, Constants.data.background);
        }
        Constants.data.over = true;
        add(Constants.terminal);
        Constants.terminal.repaint();
    }

    public void winCombat(Enemy enemy) {
        Constants.data.waitingForEnemy = false;
        Constants.data.waitingForAttack = false;
        Constants.data.inAttack = false;
        clearBottomAff();
        Constants.data.player.setHpMax(Constants.data.stats[0]);
        Constants.data.player.setAtk(Constants.data.stats[1]);
        Constants.data.player.setDef(Constants.data.stats[2]);
        Constants.data.player.setMagicDef(Constants.data.stats[3]);
        Constants.data.player.setCritChance(Constants.data.stats[4]);
        Constants.data.player.setManaMax(Constants.data.stats[5]);
        Constants.data.player.setManaRegen(Constants.data.stats[6]);
        Constants.data.player.setMana(Constants.data.player.getManaMax());
        Constants.data.player.setXp(Constants.data.player.getXp() + enemy.getXp());
        if (Constants.data.player.getXp() > getXpNeeded(Constants.data.player.getLevel())) {
            Constants.data.player.setXp(Constants.data.player.getXp() - getXpNeeded(Constants.data.player.getLevel()));
            Constants.data.player.setLevel(Constants.data.player.getLevel() + 1);
        }
        Constants.data.player.setCoins(Constants.data.player.getCoins() + enemy.getCoins());
        Constants.data.enemies.remove(enemy);
        affMsg("You killed the " + enemy.getName() + "!", 7, 64);
        affMsg("Reward: " + enemy.getXp() + " xp, " + enemy.getCoins() + " coins.", 7, 66);
        Constants.data.waitingForReturn = true;
    }

    public void clearSideAff() {
        for (int i = 35; i < 84; i++)
            for (int j = 140; j < 169; j++)
                Constants.terminal.write(Character.toString(32), j, i);
        Constants.terminal.write(Character.toString(218), 168, 83, Constants.data.font, Constants.data.background);
    }

    public void  clearBottomAff() {
        for (int i = 61 ; i < 83 ; i ++)
            for (int j = 1 ; j < 117 ; j++)
                Constants.terminal.write(Character.toString(32), j, i);
        add(Constants.terminal);
        Constants.terminal.repaint();
    }

    public void pickedUp(char keyPressed, int i) { //Méthode pour ramasser un item, called seulement lors d'un input E ou R
        clearSideAff();
        Constants.data.justPickedUp = true;
        if (keyPressed == 'E') {
            if (Constants.data.player.getInv().size() < 10) {
                Constants.data.player.getInv().add(Constants.data.items.get(i));
                affMsg("You picked up the " + Constants.data.items.get(i).getName(), 140, 35);
                Constants.data.items.remove(i);
            } else {
                affMsg("You can't carry more", 140, 35);
                affMsg("than 10 items", 140, 36);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                affMsg("You decided to let", 140, 38);
                affMsg("the " + Constants.data.items.get(i).getName(), 140, 39);
            }
        } else {
            affMsg("You decided to let", 140, 35);
            affMsg("the " + Constants.data.items.get(i).getName(), 140, 36);
        }
    }

    public void affMsg(String msg, int x, int y) { //Méthode pour afficher un message avec un effet de défilement
        if (msg.length() > 28 && x == 140) {
            String[] msgSplit = msg.split(" ");
            String msg1 = "";
            String msg2 = "";
            int m = 1;
            for (int i = 0; i < msgSplit.length; i++) {
                if (msg1.length() + msgSplit[i].length() < 28 && m == 1) {
                    msg1 += msgSplit[i] + " ";
                } else {
                    msg2 += msgSplit[i] + " ";
                    m = 2;
                }
            }
            affMsg(msg1, x, y);
            affMsg(msg2, x, y + 1);
            return;
        }
        for (int i = 0; i < msg.length(); i++) {
            Constants.terminal.write(msg.charAt(i), x + i, y, Constants.data.font, Constants.data.background);
            add(Constants.terminal);
            Constants.terminal.paintImmediately(Constants.terminal.getBounds());
            long temps = System.currentTimeMillis();
            while (System.currentTimeMillis() - temps < 20) {
            }
        }
        add(Constants.terminal);
        Constants.terminal.repaint();
        Constants.data.tempsInactif = System.currentTimeMillis();
    }

    public void addEnemy(Enemy enemy) {
        Constants.data.enemies.add(enemy);
        Constants.terminal.write(Character.toString(234), enemy.getX(), enemy.getY(), enemy.getColor(), Constants.data.roomColor);
        add(Constants.terminal);
        Constants.terminal.repaint();
    }

    public void addItem(Item item) {
        Constants.data.items.add(item);
        Constants.terminal.write(Character.toString(235), item.getX(), item.getY(), item.getColorItem(), Constants.data.roomColor);
        add(Constants.terminal);
        Constants.terminal.repaint();
    }

    public void addCoins(int x) {
        for (int i = 0; i < x; i++)
            Constants.data.coins.add(new Coin());
        affCoins();
    }

    public void affCoins() {
        coins : for (Coin coin : Constants.data.coins) {
            for (Enemy enemy : Constants.data.enemies) {
                if (coin.getX() == enemy.getX() && coin.getY() == enemy.getY())
                    continue coins;
            }
            Constants.terminal.write(Character.toString(7), coin.getX(), coin.getY(), new Color(255, 204, 0), Constants.data.roomColor);
        }
        add(Constants.terminal);
        Constants.terminal.repaint();
    }

    public void affAllItems() {
        for (Item item : Constants.data.items) {
            boolean aff = true;
            for (Enemy enemy : Constants.data.enemies) {
                if (item.getX() == enemy.getX() && item.getY() == enemy.getY()) {
                    aff = false;
                    break;
                }
            }
            if (aff)
                Constants.terminal.write(Character.toString(235), item.getX(), item.getY(), item.getColorItem(), Constants.data.roomColor);
        }
    }

    public static char charRoom(int x, int y) {
        int X = x/17;
        int Y = y/17;

        Room room = MapGenerator.getRoomByXY(Constants.data.listRooms, X, Y);

        if (room == null)
            return (' ');
        int dx = x - (room.getX())*17;
        int dy = y - (room.getY())*17;
        return room.getRoom()[dy].charAt(dx);
    }

    //On touche pas ce qui est en dessous

    @Override
    public void keyTyped(KeyEvent e) {}

    //Méthode très importante
    @Override
    public void keyPressed(KeyEvent e) { //Méthode pour gérer les inputs
        if (System.currentTimeMillis() - Constants.data.tempsInactif > 20) {
            if (Constants.data.inAttack && !Constants.data.waitingForAttack && !Constants.data.waitingForEnemy && !Constants.data.waitingForChoice) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN && Constants.data.attackSelected == 1)
                    Constants.data.attackSelected = 2;
                else if (e.getKeyCode() == KeyEvent.VK_UP && Constants.data.attackSelected == 2)
                    Constants.data.attackSelected = 1;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT && Constants.data.attackSelected == 1)
                    Constants.data.attackSelected = 3;
                else if (e.getKeyCode() == KeyEvent.VK_LEFT && Constants.data.attackSelected == 3)
                    Constants.data.attackSelected = 1;
                else if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    attack2(Constants.data.enemyAttacked);
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    affSelection();
                }
            } else if (Constants.data.inAttack && !Constants.data.waitingForAttack && !Constants.data.waitingForEnemy) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN && ((Constants.data.attackSelected == 2 && Constants.data.player.getSpells().size() > Constants.data.spellSelected + 3) || (Constants.data.attackSelected == 3 && Constants.data.numberPotions > Constants.data.spellSelected + 3)))
                    Constants.data.spellSelected += 3;
                else if (e.getKeyCode() == KeyEvent.VK_UP && Constants.data.spellSelected > 2)
                    Constants.data.spellSelected -= 3;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT && ((Constants.data.attackSelected == 2 && Constants.data.player.getSpells().size() > Constants.data.spellSelected + 1) || (Constants.data.attackSelected == 3 && Constants.data.numberPotions > Constants.data.spellSelected + 1)))
                    Constants.data.spellSelected += 1;
                else if (e.getKeyCode() == KeyEvent.VK_LEFT && Constants.data.spellSelected > 0)
                    Constants.data.spellSelected -= 1;
                else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Constants.data.waitingForChoice = false;
                    attack(Constants.data.enemyAttacked);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && ((Constants.data.numberPotions > 0 && Constants.data.attackSelected == 3) || (Constants.data.player.getSpells().size() != 0 && Constants.data.attackSelected == 2)))
                    attackPlayer(Constants.data.enemyAttacked);
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    affSpellSelected();
                }
            } else if (Constants.data.inAttack && !Constants.data.waitingForAttack) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    attackEnemy(Constants.data.enemyAttacked);
            } else if (Constants.data.inAttack) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (Constants.data.over) {
                        setVisible(false);
                        dispose();
                    } else if (Constants.data.player.getHp() == 0)
                        gameOver();
                    else
                        attack(Constants.data.enemyAttacked);
                }
            } else if (Constants.data.waitingForReturn) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    aff();
                    Constants.data.waitingForReturn = false;
                }
            } else if (Constants.data.pickUp) {//si on est sur un item
                if (e.getKeyCode() == KeyEvent.VK_E) {
                    Constants.data.pickUp = false;
                    pickedUp('E', Constants.data.itemSelected);
                }
                if (e.getKeyCode() == KeyEvent.VK_R) {
                    Constants.data.pickUp = false;
                    pickedUp('R', Constants.data.itemSelected);
                }
            } else if (Constants.data.invOpen) {
                if (e.getKeyCode() == KeyEvent.VK_I) {
                    Constants.data.itemInv = 0;
                    Constants.data.invOpen = !Constants.data.invOpen;
                    Inventory.affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_E || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                    Constants.terminal.write("                 ", 140, 71, Color.WHITE, Color.BLACK);
                    add(Constants.terminal);
                    Constants.terminal.repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    Constants.data.itemInv--;
                    if (Constants.data.itemInv == -1)
                        Constants.data.itemInv = Constants.data.player.getInv().size() - 1;
                    Inventory.affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    Constants.data.itemInv++;
                    if (Constants.data.itemInv == Constants.data.player.getInv().size())
                        Constants.data.itemInv = 0;
                    Inventory.affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_A && Constants.data.player.getInv().size() != 0) {
                    if (Constants.data.player.getInv().get(Constants.data.itemInv) instanceof Weapon && Constants.data.player.getInv().get(Constants.data.itemInv).isEquipped())
                        Constants.data.player.setAtk(1);
                    if (Constants.data.player.getInv().get(Constants.data.itemInv) instanceof Shield && Constants.data.player.getInv().get(Constants.data.itemInv).isEquipped())
                        Constants.data.player.setDef(0);
                    Constants.data.player.getInv().remove(Constants.data.itemInv);
                    if (Constants.data.itemInv == Constants.data.player.getInv().size())
                        Constants.data.itemInv--;
                    affStats(Constants.data.player);
                    Inventory.affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_E && Constants.data.player.getInv().size() != 0) {
                    if (!(Constants.data.player.getInv().get(Constants.data.itemInv) instanceof Potion) && Constants.data.player.getInv().get(Constants.data.itemInv).isEquipped()) {
                        Constants.data.player.getInv().get(Constants.data.itemInv).setEquipped(false);
                        if (Constants.data.player.getInv().get(Constants.data.itemInv) instanceof Weapon)
                            Constants.data.player.setAtk(1);
                        else if (Constants.data.player.getInv().get(Constants.data.itemInv) instanceof Shield)
                            Constants.data.player.setDef(0);
                        Inventory.affInv();
                        affStats(Constants.data.player);
                    } else if (!(Constants.data.player.getInv().get(Constants.data.itemInv) instanceof Potion) && !Constants.data.player.getInv().get(Constants.data.itemInv).isEquipped()) {
                        for (Item i : Constants.data.player.getInv()) {
                            if (i.isEquipped() && i.getClass() == Constants.data.player.getInv().get(Constants.data.itemInv).getClass())
                                i.setEquipped(false);
                        }
                        Constants.data.player.getInv().get(Constants.data.itemInv).setEquipped(true);
                        System.out.println(Constants.data.player.getInv().get(Constants.data.itemInv).isEquipped());

                        if (Constants.data.player.getInv().get(Constants.data.itemInv) instanceof Weapon)
                            Constants.data.player.setAtk(((Weapon) Constants.data.player.getInv().get(Constants.data.itemInv)).getAtk());
                        else if (Constants.data.player.getInv().get(Constants.data.itemInv) instanceof Shield)
                            Constants.data.player.setDef(((Shield) Constants.data.player.getInv().get(Constants.data.itemInv)).getDef());
                        Inventory.affInv();
                        affStats(Constants.data.player);
                    } else if (Constants.data.player.getInv().get(Constants.data.itemInv) instanceof Potion) {
                        Constants.terminal.write("Cannot be equiped", 140, 71, Constants.data.font, Constants.data.background);
                        add(Constants.terminal);
                    }
                }
            } else {
                if (e.getKeyCode() == KeyEvent.VK_I) {
                    Constants.data.itemInv = 0;
                    Constants.data.invOpen = !Constants.data.invOpen;
                    Inventory.affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
                    Constants.data.x = 1;
                else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_Q)
                    Constants.data.x = -1;
                else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_Z)
                    Constants.data.y = -1;
                else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
                    Constants.data.y = 1;
                if (Constants.data.x == 1 || Constants.data.x == -1 || Constants.data.y == 1 || Constants.data.y == -1) {
                    Constants.data.player.move(Constants.data.x, Constants.data.y, Constants.game);
                    Constants.data.x = 0;
                    Constants.data.y = 0;
                    if (Constants.data.justPickedUp) {
                        Constants.data.justPickedUp = false;
                        if (!Constants.data.inAttack)
                            affAllItems();
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

}