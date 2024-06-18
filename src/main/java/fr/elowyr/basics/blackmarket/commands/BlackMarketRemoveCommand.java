package fr.elowyr.basics.blackmarket.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.blackmarket.BlackMarketManager;
import fr.elowyr.basics.blackmarket.data.BlackData;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class BlackMarketRemoveCommand {

    @Command(name = "blackmarket.remove", aliases = "bm.remove", permission = "elowyrbasics.blackmarket.remove")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!BlackMarketManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, BlackMarketManager.getInstance());
            return;
        }

        if (args.length() != 1) {
            player.sendMessage(Utils.color("&c/blackmarket remove <id>."));
            return;
        }

        BlackData blackData = BlackMarketManager.getInstance().getProvider().get(args.getArgs(0));

        if (blackData == null) {
            player.sendMessage(Utils.color("&cAucun n'objet n'existe sous cet id."));
            return;
        }

        BlackMarketManager.getInstance().getProvider().remove(blackData.getId());
        player.sendMessage(Utils.color("&cVous venez de retirer l'objet " + blackData.getItemSerializer().getName() + " &7(" + blackData.getId() + ")"));
    }
}
