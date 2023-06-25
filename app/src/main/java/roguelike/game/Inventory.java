package roguelike.game;

import roguelike.utils.Constants;

import javax.swing.*;

public class Inventory extends JFrame {

    public static void affInv() {
        if (!Constants.data.invOpen)
            Constants.game.aff();
        else {
            Constants.game.clearSideAff();
            Constants.terminal.write("Inventory: (0 /10)", 140, 35, Constants.data.font, Constants.data.background);
            if (Constants.data.player.getInv().size() != 10)
                Constants.terminal.write(String.valueOf(Constants.data.player.getInv().size()), 153, 35, Constants.data.font, Constants.data.background);
            else
                Constants.terminal.write("10", 152, 35, Constants.data.font, Constants.data.background);

            Constants.terminal.write("[A] to drop an item", 140, 77, Constants.data.font, Constants.data.background);
            Constants.terminal.write("[E] to equip an item", 140, 75, Constants.data.font, Constants.data.background);

            if (Constants.data.player.getInv().size() != 0) {
                for (int i = 0; i < Constants.data.player.getInv().size(); i++) {
                    if (i == Constants.data.itemInv)
                        Constants.terminal.write("> " + Constants.data.player.getInv().get(i).getName(), 140, 38 + 2 * i, Constants.data.font, Constants.data.background);
                    else
                        Constants.terminal.write(Constants.data.player.getInv().get(i).getName() + "  ", 140, 38 + 2 * i, Constants.data.font, Constants.data.background);
                    if (Constants.data.player.getInv().get(i).isEquipped())
                        Constants.terminal.write("Equipped", 160, 38 + 2 * i, Constants.data.font, Constants.data.background);
                }
            } else {
                Constants.terminal.write("Nothing seems to be here...", 140, 38, Constants.data.font, Constants.data.background);
            }
            Constants.terminal.repaint();
        }
    }
}
