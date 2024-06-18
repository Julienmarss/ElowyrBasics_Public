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

public class PointsGiveCommand {

    private final PointsManager pointsManager = PointsManager.getInstance();

    @Command(name = "points.give", aliases = "p.give", permission = "elowyrbasics.points.give")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() != 2) {
            sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.GIVE.USAGE")));
            return;
        }

        OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args.getArgs(0));
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(target.getUniqueId().toString());

        if (profileData == null) {
            sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.INVALID-PROFIL").replace("%player%", target.getName())));
            return;
        }

        int quantity;

        try {
            quantity = Integer.parseInt(args.getArgs(1));
        } catch (NumberFormatException e) {
            sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.INVALID-QUANTITY")));
            return;
        }

        if (quantity <= 0) {
            sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.INVALID-QUANTITY")));
            return;
        }

        profileData.setPoints(profileData.getPoints() + quantity);
        sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.GIVE.SUCCESS").replace("%player%", target.getName()).replace("%quantity%", String.valueOf(quantity))));
        if (target.isOnline()) {
            target.getPlayer().sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.GIVE.SUCCESS-OTHER").replace("%quantity%", String.valueOf(quantity))));
        }
        PointsManager.getLogs().print(sender.getName() + " give " + quantity + " points to " + target.getName() + "\n");
    }
}
