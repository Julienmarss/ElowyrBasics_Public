package fr.elowyr.basics.commands;

import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class StarterPacketCommand {

    @Command(name = "starterpack", permission = "elowyrbasics.starterpack")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() != 2) {
            return;
        }

        Player target = Bukkit.getPlayer(args.getArgs(0));

        if (target == null) {
            return;
        }

        String level = args.getArgs(1);

        if (level.equalsIgnoreCase("5")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "items give " + target.getName() + " hammer3 3");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit pvp " + target.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit pvp " + target.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit pvp " + target.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cc give p Rare 2 " + target.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawners give " + target.getName() + " PigZombie 2");

        } else if (level.equalsIgnoreCase("10")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "items give " + target.getName() + " hammer3 4");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit pvp " + target.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit pvp " + target.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit pvp " + target.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit pvp " + target.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit pvp " + target.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kit pvp " + target.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cc give p Rare 2 " + target.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cc give p Epique 1 " + target.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawners give " + target.getName() + " PigZombie 3");

        } else {
            player.sendMessage(Utils.color("&6&lElowyr &7◆ &c/starterpack <player> <5|10>."));
        }
    }
}
