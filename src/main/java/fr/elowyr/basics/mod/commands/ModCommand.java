package fr.elowyr.basics.mod.commands;

import fr.elowyr.basics.mod.ModManager;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ModCommand {

    private ModManager modManager = ModManager.getInstance();

    @Command(name = "mod", aliases = {"staffmod"}, permission = "elowyrbasics.mod")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();
        if (!modManager.isActive()) {
            ModuleManager.moduleDesactivate(player, modManager);
            return;
        }

        if (modManager.isStaff(player)) {
           modManager.removeMod(player);
        } else {
            modManager.setMod(player);
        }
    }
}
