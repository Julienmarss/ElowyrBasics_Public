package fr.elowyr.basics.randomtp.commands;

import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.randomtp.RTPManager;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class RTPCommand {

    @Command(name = "rtp", aliases = {"randomtp"}, permission = "elowyrbasics.randomtp")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!RTPManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, RTPManager.getInstance());
            return;
        }

        RTPManager.getInstance().openGUI(player);
    }
}
