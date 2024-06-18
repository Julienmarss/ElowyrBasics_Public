package fr.elowyr.basics.points.commands;

import fr.elowyr.basics.points.PointsManager;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class PointsLookCommand {

    private final PointsManager pointsManager = PointsManager.getInstance();

    @Command(name = "points.look", aliases = "p.look", permission = "elowyrbasics.points.look")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() != 1) {
            sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.LOOK.USAGE")));
            return;
        }

        OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args.getArgs(0));
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(target.getUniqueId().toString());

        if (profileData == null) {
            sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.INVALID-PROFIL").replace("%player%", target.getName())));
            return;
        }

        sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.LOOK.SUCCESS-POINTS").replace("%player%", target.getName()).replace("%points%", String.valueOf(profileData.getPoints()))));
        sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.LOOK.SUCCESS-SPENTS").replace("%player%", target.getName()).replace("%spents%", String.valueOf(profileData.getSpents()))));
    }
}
