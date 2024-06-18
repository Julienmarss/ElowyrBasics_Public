package fr.elowyr.basics.blackmarket.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.blackmarket.BlackMarketManager;
import fr.elowyr.basics.blackmarket.gui.BlackMarketGUI;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class BlackMarketCommand {

    @Command(name = "blackmarket", aliases = "bm", permission = "elowyrbasics.blackmarket.gui")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!BlackMarketManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, BlackMarketManager.getInstance());
            return;
        }

        if (args.length() == 1) {
            if (args.getArgs(0).equalsIgnoreCase("help") && player.hasPermission("elowyrbasics.blackmarket.help")) {
                player.sendMessage(Utils.color("&6/blackmarket add <quantity> <prix> <time>."));
                player.sendMessage(Utils.color("&6/blackmarket remove <id>."));
                player.sendMessage(Utils.color("&6/blackmarket timeleft <id> <time>."));
                player.sendMessage(Utils.color("&6/blackmarket add quantity <id> <quantity>."));
                player.sendMessage(Utils.color("&6/blackmarket remove quantity <id> <quantity>."));
            }
        }

        if (BlackMarketManager.getInstance().getProvider().getBlackDataMap() == null) {
            player.sendMessage(Utils.color("&cAucun article."));
            return;
        }

        BlackMarketGUI gui = new BlackMarketGUI(1, player, BlackMarketManager.getInstance());
        gui.open(player);
    }
}
