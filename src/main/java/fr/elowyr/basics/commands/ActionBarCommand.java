package fr.elowyr.basics.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.utils.BukkitUtils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionBarCommand {

    @Command(name = "actionbar", permission = "elowyrbasics.actionbar")
    public void onCommand(CommandArgs args) {
        String[] arg = args.getArgs();
        if (arg.length < 1) {
            return;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    BukkitUtils.sendActionBar(player, String.join(" ", arg).replace('&', '§'));
                }
            }
        }.runTaskAsynchronously(ElowyrBasics.getInstance());
    }
}
