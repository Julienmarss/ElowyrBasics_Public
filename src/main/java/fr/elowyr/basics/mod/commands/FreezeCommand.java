package fr.elowyr.basics.mod.commands;

import fr.elowyr.basics.mod.ModManager;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FreezeCommand {

    private ModManager modManager = ModManager.getInstance();

    @Command(name = "freeze", permission = "elowyrbasics.mod.freeze")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!modManager.isActive()) {
            ModuleManager.moduleDesactivate(player, modManager);
            return;
        }

        if (args.length() != 1) {
            player.sendMessage(Utils.color(modManager.getStaffConfig().getString("MESSAGES.USAGE-FREEZE")));
            return;
        }

        Player target = Bukkit.getPlayer(args.getArgs(0));
        if (target == null) {
            player.sendMessage(Utils.color(modManager.getStaffConfig().getString("MESSAGES.EMPTY-PLAYER")).replace("%player%", target.getName()));
            return;
        }
        modManager.setFrozen(player, target);
    }
}
