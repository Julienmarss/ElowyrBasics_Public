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
import org.bukkit.command.ConsoleCommandSender;

public class PointsTakeCommand {

    private final PointsManager pointsManager = PointsManager.getInstance();

    @Command(name = "points.take", aliases = "p.take", permission = "elowyrbasics.points.take")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() != 2) {
            sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.TAKE.USAGE")));
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

        if (profileData.getPoints() < quantity) {
            sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.NO-POINTS").replace("%player%", profileData.getPlayerName())));
            return;
        }

        profileData.setPoints(profileData.getPoints() - quantity);
        profileData.setSpents(profileData.getSpents() + quantity);
        sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.TAKE.SUCCESS").replace("%player%", target.getName()).replace("%quantity%", String.valueOf(quantity))));
        if (target.isOnline()) {
            target.getPlayer().sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.TAKE.SUCCESS-OTHER").replace("%quantity%", String.valueOf(quantity))));
        }
        if (sender instanceof ConsoleCommandSender) {
            PointsManager.getLogs().print("Console take " + quantity + " points to " + target.getName() + "\n");
        } else {
            PointsManager.getLogs().print(sender.getName() + " take " + quantity + " points to " + target.getName() + "\n");
        }
    }
}
