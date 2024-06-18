package fr.elowyr.basics.commands;

import fr.elowyr.basics.utils.BukkitUtils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;

public class TitleCommand {

    @Command(name = "etitle", permission = "elowyrbasics.title")
    public void onCommand(CommandArgs args) {

        String line = String.join(" ", args.getArgs()).replace('&', '§');
        String[] split = line.split("###");
        Bukkit.getOnlinePlayers().forEach(player -> BukkitUtils.sendTitle(player, split[0], split[1], 20, 50, 20));
    }
}
