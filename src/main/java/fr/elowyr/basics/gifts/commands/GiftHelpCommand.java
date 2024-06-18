package fr.elowyr.basics.gifts.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.gifts.GiftManager;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class GiftHelpCommand {

    @Command(name = "gift.help", aliases = {"gifts.help", "don.help", "dons.help"}, permission = "elowyrbasics.gift.help")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!GiftManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, GiftManager.getInstance());
            return;
        }

        player.sendMessage(Utils.color("&fEnvoyer un don à un &dPartenaire&f:"));
        player.sendMessage(Utils.color("&e/gift send <player> <message>."));
        if (player.hasPermission("elowyrbasics.gift.gui")) {
            player.sendMessage(Utils.color("&e/gift &7- &fOuvrir la liste des dons."));
            player.sendMessage(Utils.color("&e/gift toggle &7- &fDésactiver la réception de dons."));
        }
    }
}
