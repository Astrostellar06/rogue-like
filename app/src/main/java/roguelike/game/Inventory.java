package roguelike.game;

import roguelike.utils.Constants;

import javax.swing.*;

public class Inventory extends JFrame {

    public static void affInv() {
        if (!Data.invOpen)
            Constants.game.aff();
        else {
            Constants.game.clearSideAff();
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
            Data.terminal.repaint();
        }
    }
}
