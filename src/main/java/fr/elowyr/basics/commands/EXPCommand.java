package fr.elowyr.basics.commands;

import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EXPCommand {

    @Command(name = "level.take", permission = "elowyrbasics.level.take")
    public void onCommand(CommandArgs commandArgs) {
        if (commandArgs.length() != 2) {
            commandArgs.getSender().sendMessage(Utils.color("&c/level take <player> <level>"));
            return;
        }
        Player target = Bukkit.getPlayer(commandArgs.getArgs(0));

        if (target == null) {
            commandArgs.getSender().sendMessage(Utils.color("&cLe joueur n'existe pas."));
            return;
        }

        int level = Integer.parseInt(commandArgs.getArgs(1));

        if (level <= 0) {
            commandArgs.getSender().sendMessage(Utils.color("&cNombre de niveau incorrect."));
            return;
        }

        target.setLevel(target.getLevel() - level);
        commandArgs.getSender().sendMessage(Utils.color("&fVous avez &cretiré &a" + level + "&f au joueur &6" + target.getName()));
    }
}
