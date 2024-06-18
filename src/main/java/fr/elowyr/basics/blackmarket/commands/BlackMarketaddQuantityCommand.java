package fr.elowyr.basics.blackmarket.commands;

import fr.elowyr.basics.blackmarket.BlackMarketManager;
import fr.elowyr.basics.blackmarket.data.BlackData;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class BlackMarketaddQuantityCommand {

    @Command(name = "blackmarket.add.quantity", aliases = "bm.add.quantity", permission = "elowyrbasics.blackmarket.quantity.add")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!BlackMarketManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, BlackMarketManager.getInstance());
            return;
        }

        if (args.length() != 2) {
            player.sendMessage(Utils.color("&c/blackmarket add quantity <id> <quantity>."));
            return;
        }

        BlackData blackData = BlackMarketManager.getInstance().getProvider().get(args.getArgs(0));

        if (blackData == null) {
            player.sendMessage(Utils.color("&cAucun n'objet n'existe sous cet id."));
            return;
        }

        try {
            int quantity = Integer.parseInt(args.getArgs(1));

            blackData.getItemSerializer().setQuantity(blackData.getItemSerializer().getQuantity() + quantity);
            BlackMarketManager.getInstance().getProvider().write();
            player.sendMessage(Utils.color("&fIl reste &adésormait &d" + blackData.getItemSerializer().getQuantity() + "&a disponible &fà l'achat."));
        } catch (NumberFormatException e) {
            player.sendMessage(Utils.color("&cLe chiffre est incorrect."));
        }
    }
}
