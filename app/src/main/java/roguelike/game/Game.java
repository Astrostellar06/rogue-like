package roguelike.game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;
import roguelike.Assets;
import roguelike.models.*;
import roguelike.utils.Constants;
import roguelike.utils.MapGenerator;

public class Game extends JFrame implements KeyListener {

    public Game(AsciiPanel terminal) { //Création du jeu
        super(); //Utilisation de JFrame et de AsciiPanel
        Data.player = new Player("Astrostellar");
        Data.enemies = new ArrayList<>();
        Data.items = new ArrayList<>();
        Data.coins = new ArrayList<>();
        Data.stats = new int[7];
        Data.terminal = terminal; //Taille de la fenêtre + police
        Data.getTheme();
        addKeyListener(this); //Ajout de l'écouteur de touches

        for (int i = 0 ; i < 10; i++) {
            addItem(new Weapon());
            addItem(new Shield());
            addItem(new Potion());
            Data.player.getInv().add(new Potion());
        }

        for (int i = 0 ; i < 5 ; i++) {
            addEnemy(new Enemy(1));
            addEnemy(new Enemy(2));
            addEnemy(new Enemy(3));
            addEnemy(new Enemy(4));
        }

        Data.player.getSpells().add(new Spell(1));
        Data.player.getSpells().add(new Spell(2));
        Data.player.getSpells().add(new Spell(3));
        Data.player.getSpells().add(new Spell(4));
        Data.player.getSpells().add(new Spell(5));
        Data.player.getSpells().add(new Spell(6));
        Data.player.getSpells().add(new Spell(7));

        addCoins(60);
        aff();
    }



    public void aff() { //Affichage de la fenêtre
        for (int i = 0; i < 85; i++) {
            for (int j = 0; j < 170; j++) {
                Data.terminal.write(Character.toString(32), j, i, Data.font, Data.background);
            }
        }

        for (Room room : Data.listRooms)
            affRooms(room);

        Data.terminal.write(Character.toString(1), Data.player.getX(), Data.player.getY(), Data.playerColor, Data.roomColor);

        for (Enemy enemy : Data.enemies)
            Data.terminal.write(Character.toString(234), enemy.getX(), enemy.getY(), enemy.getColor(), Data.roomColor);

        for (Item item : Data.items)
            Data.terminal.write(Character.toString(235), item.getX(), item.getY(), item.getColorItem(), Data.roomColor);

        affCoins();
        deco();
        affStats(Data.player);

        for (int i = 0; i < 31; i++)
            Data.terminal.write(Character.toString(196), 138 + i, 31, Data.font, Data.background);
        Data.terminal.write(Character.toString(199), 137, 31, Data.font, Data.background);
        Data.terminal.write(Character.toString(180), 169, 31, Data.font, Data.background);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
        requestFocus();
    }

    public void deco() {
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

            Data.terminal.write(entity.getName(), dx, dy + 2, entity instanceof Player ? Data.font : ((Enemy) entity).getColor(), Data.background);
            for (int i = 0; i < entity.getName().length(); i++)
                Data.terminal.write(Character.toString(196), dx + i, dy + 3, entity instanceof Player ? Data.font : ((Enemy) entity).getColor(), Data.background);

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
                x += Game.getXpNeeded(i);
            }
            Data.terminal.write("Level: " + Data.player.getLevel() + "  (" + (x - Game.getXpNeeded(Data.player.getLevel()) + Data.player.getXp()) + "/" + x + ")  ", 140, 25, Data.font, Data.background);

            Data.terminal.write(Character.toString(60), 140, 27, Data.font, Data.background);
            Data.terminal.write(Character.toString(62), 166, 27, Data.font, Data.background);
            for (int i = 141; i < 166; i++)
                Data.terminal.write(Character.toString(196), i, 27, Data.font, Data.background);
            for (int i = 141; i < 141 + 25 * Data.player.getXp() / Game.getXpNeeded(Data.player.getLevel()); i++)
                Data.terminal.write(Character.toString(219), i, 27, Data.font, Data.background);
        }
        add(Data.terminal);
        Data.terminal.repaint();
    }

    public static int getXpNeeded(int x) {
        return (int) (Math.pow(x, 2) * 10);
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

            Moves.moveEnemies();
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

    public void affAttack(Enemy enemy) {
        Data.inAttack = true;
        Data.waitingForAttack = true;
        Data.stats[0] = Data.player.getHpMax();
        Data.stats[1] = Data.player.getAtk();
        Data.stats[2] = Data.player.getDef();
        Data.stats[3] = Data.player.getMagicDef();
        Data.stats[4] = Data.player.getCritChance();
        Data.stats[5] = Data.player.getManaMax();
        Data.stats[6] = Data.player.getManaRegen();
        clearSideAff();
        affMsg("You are attacked ", 140, 35);
        affMsg("by a " + enemy.getName(), 140, 36);
        affMsg("Press [Enter] to Attack", 140, 39);
        Data.tempsInactif = System.currentTimeMillis();
    }
    public void attack(Enemy enemy) {
        Data.attackSelected = 1;
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

        for (int i = 0 ; i < 6 ; i++) {
            Data.terminal.write(Assets.attack[i], 13, 63 + i, Data.font, Data.background);
            Data.terminal.write(Assets.item[i], 68, 63 + i, Data.font, Data.background);
            Data.terminal.write(Assets.selected[i], 7, 63 + i, Data.font, Data.background);
        }

        for (int i = 0 ; i < 59 ; i++)
            Data.terminal.write(Assets.knight[i], 2, 1+i, Data.font, Data.background);
        for (int i = 0 ; i < 8 ; i++)
            Data.terminal.write(Assets.spell[i], 13, 73 + i, Data.font, Data.background);
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
            Data.terminal.write(Assets.selected[i], dx, dy + i, Data.font, Data.background);
        add(Data.terminal);
        Data.terminal.repaint();
    }

    public void attack2(Enemy enemy) {
        if (Data.attackSelected == 1) {
            attackPlayer(enemy);
        } else {
            Data.spellSelected = 0;
            Data.waitingForChoice = true;
            affSpellSelected();
        }
    }

    public void attackPlayer(Enemy enemy) {
        clearBottomAff();
        if (Data.attackSelected == 1) {
            String weapon = "fists";
            for (Item item : Data.player.getInv()) {
                if (item instanceof Weapon && item.isEquipped()) {
                    weapon = item.getName();
                    break;
                }
            }
            enemy.setHp(enemy.getHp() - (Math.max(Data.player.getAtk() - enemy.getDef(), 0)));
            if (enemy.getHp() <= 0)
                enemy.setHp(0);
            affStats(enemy);
            affMsg("You attack the " + enemy.getName() + " with your " + weapon + ".", 7, 64);
            Data.waitingForEnemy = true;
        } else if (Data.attackSelected == 2) {
            if (Data.player.getSpells().get(Data.spellSelected).getManaCost() > Data.player.getMana()) {
                clearBottomAff();
                affMsg("You don't have enough mana to cast this spell.", 7, 64);
                Data.waitingForChoice = false;
                Data.waitingForAttack = true;
            } else {
                Data.player.setMana(Data.player.getMana() - Data.player.getSpells().get(Data.spellSelected).getManaCost());
                Data.player.setHp(Data.player.getHp() + Data.player.getSpells().get(Data.spellSelected).getHpPlayer());
                Data.player.setHpMax(Data.player.getHpMax() + Data.player.getSpells().get(Data.spellSelected).getHpMaxPlayer());
                if (Data.player.getHp() > Data.player.getHpMax())
                    Data.player.setHp(Data.player.getHpMax());
                if (Data.player.getHp() < 0)
                    Data.player.setHp(0);
                Data.player.setAtk(Data.player.getAtk() + Data.player.getSpells().get(Data.spellSelected).getAtkPlayer());
                if (Data.player.getAtk() < 1)
                    Data.player.setAtk(1);
                Data.player.setDef(Data.player.getDef() + Data.player.getSpells().get(Data.spellSelected).getDefPlayer());
                if (Data.player.getDef() < 0)
                    Data.player.setDef(0);
                Data.player.setMagicDef(Data.player.getMagicDef() + Data.player.getSpells().get(Data.spellSelected).getMagicDefPlayer());
                if (Data.player.getMagicDef() < 0)
                    Data.player.setMagicDef(0);
                Data.player.setCritChance(Data.player.getCritChance() + Data.player.getSpells().get(Data.spellSelected).getCritChancePlayer());
                if (Data.player.getCritChance() < 0)
                    Data.player.setCritChance(0);
                Data.player.setManaMax(Data.player.getManaMax() + Data.player.getSpells().get(Data.spellSelected).getManaMaxPlayer());
                if (Data.player.getManaMax() < 0)
                    Data.player.setManaMax(0);
                Data.player.setMana(Data.player.getMana() + Data.player.getSpells().get(Data.spellSelected).getManaPlayer());
                if (Data.player.getMana() > Data.player.getManaMax())
                    Data.player.setMana(Data.player.getManaMax());
                if (Data.player.getMana() < 0)
                    Data.player.setMana(0);
                Data.player.setManaRegen(Data.player.getManaRegen() + Data.player.getSpells().get(Data.spellSelected).getManaRegenPlayer());
                if (Data.player.getManaRegen() < 0)
                    Data.player.setManaRegen(0);

                enemy.setHp(enemy.getHp() - Data.player.getSpells().get(Data.spellSelected).getHpEnemy());
                if (enemy.getHp() <= 0)
                    enemy.setHp(0);
                if (enemy.getHp() > enemy.getHpMax())
                    enemy.setHp(enemy.getHpMax());
                enemy.setAtk(enemy.getAtk() + Data.player.getSpells().get(Data.spellSelected).getAtkEnemy());
                if (enemy.getAtk() < 1)
                    enemy.setAtk(1);
                enemy.setDef(enemy.getDef() + Data.player.getSpells().get(Data.spellSelected).getDefEnemy());
                if (enemy.getDef() < 0)
                    enemy.setDef(0);
                enemy.setMagicDef(enemy.getMagicDef() + Data.player.getSpells().get(Data.spellSelected).getMagicDefEnemy());
                if (enemy.getMagicDef() < 0)
                    enemy.setMagicDef(0);
                enemy.setCritChance(enemy.getCritChance() + Data.player.getSpells().get(Data.spellSelected).getCritChanceEnemy());
                if (enemy.getCritChance() < 0)
                    enemy.setCritChance(0);

                affStats(enemy);
                affMsg("You decided to cast " + Data.player.getSpells().get(Data.spellSelected).getName() + ".", 7, 64);

                Data.waitingForChoice = false;
                Data.waitingForEnemy = true;
            }

        } else if (Data.attackSelected == 3) {
            Potion potionUsed = null;
            int j = -1;
            for (int i = 0 ; i < Data.player.getInv().size() ; i++) {
                if (Data.player.getInv().get(i) instanceof Potion) {
                    j++;
                    if (Data.spellSelected == j) {
                        potionUsed = (Potion) Data.player.getInv().get(i);
                        Data.player.getInv().remove(i);
                        break;
                    }
                }
            }
            Data.player.setHpMax(Data.player.getHpMax() + potionUsed.getHpMaxPlayer());
            Data.player.setHp(Data.player.getHp() + potionUsed.getHpPlayer());
            if (Data.player.getHp() > Data.player.getHpMax())
                Data.player.setHp(Data.player.getHpMax());
            if (Data.player.getHp() < 0)
                Data.player.setHp(0);
            Data.player.setAtk(Data.player.getAtk() + potionUsed.getAtkPlayer());
            if (Data.player.getAtk() < 1)
                Data.player.setAtk(1);
            Data.player.setDef(Data.player.getDef() + potionUsed.getDefPlayer());
            if (Data.player.getDef() < 0)
                Data.player.setDef(0);
            Data.player.setMagicDef(Data.player.getMagicDef() + potionUsed.getMagicDefPlayer());
            if (Data.player.getMagicDef() < 0)
                Data.player.setMagicDef(0);
            Data.player.setCritChance(Data.player.getCritChance() + potionUsed.getCritChancePlayer());
            if (Data.player.getCritChance() < 0)
                Data.player.setCritChance(0);
            Data.player.setManaMax(Data.player.getManaMax() + potionUsed.getManaMaxPlayer());
            if (Data.player.getManaMax() < 0)
                Data.player.setManaMax(0);
            Data.player.setMana(Data.player.getMana() + potionUsed.getManaPlayer());
            if (Data.player.getMana() > Data.player.getManaMax())
                Data.player.setMana(Data.player.getManaMax());
            if (Data.player.getMana() < 0)
                Data.player.setMana(0);
            Data.player.setManaRegen(Data.player.getManaRegen() + potionUsed.getManaRegenPlayer());
            if (Data.player.getManaRegen() < 0)
                Data.player.setManaRegen(0);

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

            affStats(enemy);
            affMsg("You decided to use your " + potionUsed.getName() + ".", 7, 64);

            Data.waitingForChoice = false;
            Data.waitingForEnemy = true;
        }
    }

    public void affSpellSelected() {
        clearBottomAff();
        int dx = 4;
        int dy = 63;
        if (Data.attackSelected == 3) {
            Data.numberPotions = 0;
            for (int i = 0; i < Data.player.getInv().size(); i++) {
                if (Data.player.getInv().get(i) instanceof Potion) {
                    Data.numberPotions++;
                    if (Data.spellSelected == i) {
                        Data.terminal.write("> " + Data.player.getInv().get(i).getName() + "  ", dx, dy, Data.font, Data.background);
                        String[] potionDesc = new String[6];
                        potionDesc[0] = ("(" + ((Potion) Data.player.getInv().get(i)).getHpPlayer() + "," + ((Potion) Data.player.getInv().get(i)).getHpMaxPlayer() + ",");
                        potionDesc[1] = (((Potion) Data.player.getInv().get(i)).getAtkPlayer() + ",");
                        potionDesc[2] = (((Potion) Data.player.getInv().get(i)).getDefPlayer() + ",");
                        potionDesc[3] = (((Potion) Data.player.getInv().get(i)).getManaPlayer() + "," + ((Potion) Data.player.getInv().get(i)).getManaMaxPlayer() + "," + ((Potion) Data.player.getInv().get(i)).getManaRegenPlayer() + ",");
                        potionDesc[4] = (((Potion) Data.player.getInv().get(i)).getMagicDefPlayer() + ",");
                        potionDesc[5] = (((Potion) Data.player.getInv().get(i)).getCritChancePlayer() + ")");

                        String[] potionDesc2 = new String[5];
                        potionDesc2[0] = ("(" + ((Potion) Data.player.getInv().get(i)).getHpEnemy() + ",");
                        potionDesc2[1] = (((Potion) Data.player.getInv().get(i)).getAtkEnemy() + ",");
                        potionDesc2[2] = (((Potion) Data.player.getInv().get(i)).getDefEnemy() + ",");
                        potionDesc2[3] = (((Potion) Data.player.getInv().get(i)).getMagicDefEnemy() + ",");
                        potionDesc2[4] = (((Potion) Data.player.getInv().get(i)).getCritChanceEnemy() + ")");

                        affDesc(dx, dy, potionDesc, potionDesc2);
                    } else
                        Data.terminal.write(Data.player.getInv().get(i).getName() + "  ", dx, dy, Data.font, Data.background);

                    dx += 38;
                    if (dx > 80) {
                        dx = 4;
                        dy += 3;
                    }
                }
            }
        } else {
            for (int i = 0; i < Data.player.getSpells().size(); i++) {
                if (Data.spellSelected == i) {
                    Data.terminal.write("> " + Data.player.getSpells().get(i).getName() + " (" + Data.player.getSpells().get(i).getManaCost() + " mana)  ", dx, dy, Data.font, Data.background);
                    String[] potionDesc = new String[6];
                    potionDesc[0] = ("(" + (Data.player.getSpells().get(i)).getHpPlayer() + "," + ( Data.player.getSpells().get(i)).getHpMaxPlayer() + ",");
                    potionDesc[1] = (( Data.player.getSpells().get(i)).getAtkPlayer() + ",");
                    potionDesc[2] = (( Data.player.getSpells().get(i)).getDefPlayer() + ",");
                    potionDesc[3] = (( Data.player.getSpells().get(i)).getManaPlayer() + "," + ( Data.player.getSpells().get(i)).getManaMaxPlayer() + "," + ( Data.player.getSpells().get(i)).getManaRegenPlayer() + ",");
                    potionDesc[4] = (( Data.player.getSpells().get(i)).getMagicDefPlayer() + ",");
                    potionDesc[5] = (( Data.player.getSpells().get(i)).getCritChancePlayer() + ")");

                    String[] potionDesc2 = new String[5];
                    potionDesc2[0] = ("(" + ( Data.player.getSpells().get(i)).getHpEnemy() + ",");
                    potionDesc2[1] = (( Data.player.getSpells().get(i)).getAtkEnemy() + ",");
                    potionDesc2[2] = (( Data.player.getSpells().get(i)).getDefEnemy() + ",");
                    potionDesc2[3] = (( Data.player.getSpells().get(i)).getMagicDefEnemy() + ",");
                    potionDesc2[4] = (( Data.player.getSpells().get(i)).getCritChanceEnemy() + ")");

                    affDesc(dx, dy, potionDesc, potionDesc2);
                } else
                    Data.terminal.write(Data.player.getSpells().get(i).getName() + " (" + Data.player.getSpells().get(i).getManaCost() + " mana)  ", dx, dy, Data.font, Data.background);
                dx += 38;
                if (dx > 80) {
                    dx = 4;
                    dy += 3;
                }
            }
        }
        Data.terminal.write("[Esc] to go back", 4, 81, Data.font, Data.background);
        add(Data.terminal);
        Data.terminal.repaint();
    }

    public void affDesc(int dx, int dy, String[] potionDesc, String[] potionDesc2) {
        Data.terminal.write(potionDesc[0], dx, dy + 1, Color.red, Data.background);
        Data.terminal.write(potionDesc[1], dx + potionDesc[0].length(), dy + 1, new Color(255, 115, 0), Data.background);
        Data.terminal.write(potionDesc[2], dx + potionDesc[0].length() + potionDesc[1].length(), dy + 1, new Color(0, 128, 255), Data.background);
        Data.terminal.write(potionDesc[3], dx + potionDesc[0].length() + potionDesc[1].length() + potionDesc[2].length(), dy + 1, new Color(153, 0, 255), Data.background);
        Data.terminal.write(potionDesc[4], dx + potionDesc[0].length() + potionDesc[1].length() + potionDesc[2].length() + potionDesc[3].length(), dy + 1, new Color(94, 0, 255), Data.background);
        Data.terminal.write(potionDesc[5], dx + potionDesc[0].length() + potionDesc[1].length() + potionDesc[2].length() + potionDesc[3].length() + potionDesc[4].length(), dy + 1, new Color(41, 168, 33), Data.background);
        Data.terminal.write(potionDesc2[0], dx, dy + 2, Color.red, Data.background);
        Data.terminal.write(potionDesc2[1], dx + potionDesc2[0].length(), dy + 2, new Color(255, 115, 0), Data.background);
        Data.terminal.write(potionDesc2[2], dx + potionDesc2[0].length() + potionDesc2[1].length(), dy + 2, new Color(0, 128, 255), Data.background);
        Data.terminal.write(potionDesc2[3], dx + potionDesc2[0].length() + potionDesc2[1].length() + potionDesc2[2].length(), dy + 2, new Color(94, 0, 255), Data.background);
        Data.terminal.write(potionDesc2[4], dx + potionDesc2[0].length() + potionDesc2[1].length() + potionDesc2[2].length() + potionDesc2[3].length(), dy + 2, new Color(41, 168, 33), Data.background);
    }

    public void attackEnemy(Enemy enemy) {
        if (enemy.getHp() == 0)
            winCombat(enemy);
        else if (Data.player.getHp() <= 0)
            gameOver();
        else {
            clearBottomAff();
            Data.player.setHp(Data.player.getHp() - (Math.max(enemy.getAtk() - Data.player.getDef(), 0)));
            if (Data.player.getHp() <= 0)
                Data.player.setHp(0);
            affStats(enemy);
            affMsg("The " + enemy.getName() + " attacks you.", 7, 64);
            Data.waitingForEnemy = false;
            Data.waitingForAttack = true;
            Data.player.setMana(Data.player.getMana() + Data.player.getManaRegen());
            if (Data.player.getMana() > Data.player.getManaMax())
                Data.player.setMana(Data.player.getManaMax());
        }
    }

    public void gameOver() {
        for (int i = 1; i < 84; i++) {
            for (int j = 1; j < 169; j++) {
                Data.terminal.write(Character.toString(32), j, i, Data.font, Data.background);
            }
        }
        Data.terminal.write(Character.toString(217), 1, 1, Data.font, Data.background);
        Data.terminal.write(Character.toString(192), 168, 1, Data.font, Data.background);
        Data.terminal.write(Character.toString(191), 1, 83, Data.font, Data.background);
        Data.terminal.write(Character.toString(218), 168, 83, Data.font, Data.background);
        Data.terminal.write(Character.toString(179), 0, 60, Data.font, Data.background);
        Data.terminal.write(Character.toString(179), 169, 60, Data.font, Data.background);
        Data.terminal.write(Character.toString(196), 142, 84, Data.font, Data.background);
        Data.terminal.write(Character.toString(196), 117, 84, Data.font, Data.background);

        for (int i = 30 ; i < 44 ; i++) {
            Data.terminal.write(Assets.gameOver[i-30], 19, i, Data.font, Data.background);
        }
        Data.over = true;
        add(Data.terminal);
        Data.terminal.repaint();
    }

    public void winCombat(Enemy enemy) {
        Data.waitingForEnemy = false;
        Data.waitingForAttack = false;
        Data.inAttack = false;
        clearBottomAff();
        Data.player.setHpMax(Data.stats[0]);
        Data.player.setAtk(Data.stats[1]);
        Data.player.setDef(Data.stats[2]);
        Data.player.setMagicDef(Data.stats[3]);
        Data.player.setCritChance(Data.stats[4]);
        Data.player.setManaMax(Data.stats[5]);
        Data.player.setManaRegen(Data.stats[6]);
        Data.player.setMana(Data.player.getManaMax());
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
        Data.terminal.write(Character.toString(234), enemy.getX(), enemy.getY(), enemy.getColor(), Data.roomColor);
        add(Data.terminal);
        Data.terminal.repaint();
    }

    public void addItem(Item item) {
        Data.items.add(item);
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
        coins : for (Coin coin : Data.coins) {
            for (Enemy enemy : Data.enemies) {
                if (coin.getX() == enemy.getX() && coin.getY() == enemy.getY())
                    continue coins;
            }
            Data.terminal.write(Character.toString(7), coin.getX(), coin.getY(), new Color(255, 204, 0), Data.roomColor);
        }
        add(Data.terminal);
        Data.terminal.repaint();
    }

    public void affAllItems() {
        for (Item item : Data.items) {
            boolean aff = true;
            for (Enemy enemy : Data.enemies) {
                if (item.getX() == enemy.getX() && item.getY() == enemy.getY()) {
                    aff = false;
                    break;
                }
            }
            if (aff)
                Data.terminal.write(Character.toString(235), item.getX(), item.getY(), item.getColorItem(), Data.roomColor);
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
    public void keyTyped(KeyEvent e) {}

    //Méthode très importante
    @Override
    public void keyPressed(KeyEvent e) { //Méthode pour gérer les inputs
        if (System.currentTimeMillis() - Data.tempsInactif > 20) {
            if (Data.inAttack && !Data.waitingForAttack && !Data.waitingForEnemy && !Data.waitingForChoice) {
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
            } else if (Data.inAttack && !Data.waitingForAttack && !Data.waitingForEnemy) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN && ((Data.attackSelected == 2 && Data.player.getSpells().size() > Data.spellSelected + 3) || (Data.attackSelected == 3 && Data.numberPotions > Data.spellSelected + 3)))
                    Data.spellSelected += 3;
                else if (e.getKeyCode() == KeyEvent.VK_UP && Data.spellSelected > 2)
                    Data.spellSelected -= 3;
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT && ((Data.attackSelected == 2 && Data.player.getSpells().size() > Data.spellSelected + 1) || (Data.attackSelected == 3 && Data.numberPotions > Data.spellSelected + 1)))
                    Data.spellSelected += 1;
                else if (e.getKeyCode() == KeyEvent.VK_LEFT && Data.spellSelected > 0)
                    Data.spellSelected -= 1;
                else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Data.waitingForChoice = false;
                    attack(Data.enemyAttacked);
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER && ((Data.numberPotions > 0 && Data.attackSelected == 3) || (Data.player.getSpells().size() != 0 && Data.attackSelected == 2)))
                    attackPlayer(Data.enemyAttacked);
                if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT) {
                    affSpellSelected();
                }
            } else if (Data.inAttack && !Data.waitingForAttack) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                    attackEnemy(Data.enemyAttacked);
            } else if (Data.inAttack) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (Data.over) {
                        setVisible(false);
                        dispose();
                    } else if (Data.player.getHp() == 0)
                        gameOver();
                    else
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
                    Inventory.affInv();
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
                    Inventory.affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    Data.itemInv++;
                    if (Data.itemInv == Data.player.getInv().size())
                        Data.itemInv = 0;
                    Inventory.affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_A && Data.player.getInv().size() != 0) {
                    if (Data.player.getInv().get(Data.itemInv) instanceof Weapon && Data.player.getInv().get(Data.itemInv).isEquipped())
                        Data.player.setAtk(1);
                    if (Data.player.getInv().get(Data.itemInv) instanceof Shield && Data.player.getInv().get(Data.itemInv).isEquipped())
                        Data.player.setDef(0);
                    Data.player.getInv().remove(Data.itemInv);
                    if (Data.itemInv == Data.player.getInv().size())
                        Data.itemInv--;
                    affStats(Data.player);
                    Inventory.affInv();
                }
                if (e.getKeyCode() == KeyEvent.VK_E && Data.player.getInv().size() != 0) {
                    if (!(Data.player.getInv().get(Data.itemInv) instanceof Potion) && Data.player.getInv().get(Data.itemInv).isEquipped()) {
                        Data.player.getInv().get(Data.itemInv).setEquipped(false);
                        if (Data.player.getInv().get(Data.itemInv) instanceof Weapon)
                            Data.player.setAtk(1);
                        else if (Data.player.getInv().get(Data.itemInv) instanceof Shield)
                            Data.player.setDef(0);
                        Inventory.affInv();
                        affStats(Data.player);
                    } else if (!(Data.player.getInv().get(Data.itemInv) instanceof Potion) && !Data.player.getInv().get(Data.itemInv).isEquipped()) {
                        for (Item i : Data.player.getInv()) {
                            if (i.isEquipped() && i.getClass() == Data.player.getInv().get(Data.itemInv).getClass())
                                i.setEquipped(false);
                        }
                        Data.player.getInv().get(Data.itemInv).setEquipped(true);
                        System.out.println(Data.player.getInv().get(Data.itemInv).isEquipped());

                        if (Data.player.getInv().get(Data.itemInv) instanceof Weapon)
                            Data.player.setAtk(((Weapon) Data.player.getInv().get(Data.itemInv)).getAtk());
                        else if (Data.player.getInv().get(Data.itemInv) instanceof Shield)
                            Data.player.setDef(((Shield) Data.player.getInv().get(Data.itemInv)).getDef());
                        Inventory.affInv();
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
                    Inventory.affInv();
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
                    Data.player.move(Data.x, Data.y, Constants.game);
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