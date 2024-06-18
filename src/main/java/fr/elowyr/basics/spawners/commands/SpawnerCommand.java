package fr.elowyr.basics.spawners.commands;

import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.spawners.SpawnersManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;

public class SpawnerCommand {

    @Command(name = "spawners", permission = "elowyrbasics.spawners.help")
    public void onCommand(CommandArgs args) {

        if (!SpawnersManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(args.getPlayer(), SpawnersManager.getInstance());
            return;
        }

        for (String usage : SpawnersManager.getInstance().getConfig().getStringList("MESSAGES.USAGE")) {
            args.getPlayer().sendMessage(Utils.color(usage));
        }
    }
}
