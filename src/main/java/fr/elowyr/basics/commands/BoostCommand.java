package fr.elowyr.basics.commands;

import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BoostCommand {

    @Command(name = "elowyrboost", permission = "elowyrbasics.discord.boost")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (player.hasPermission("boost.discord")) {
            player.sendMessage(Utils.color("&9&lDiscord &7◆ &fVous devez attendre &d" + PlaceholderAPI.setPlaceholders(player, "%luckperms_expiry_time_boost.discord%") + "&f."));
            return;
        }

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cc give p Epique 1 " + player.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + player.getName() + " 50000");
    }
}
