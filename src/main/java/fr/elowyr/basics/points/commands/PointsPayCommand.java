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

public class PointsPayCommand {

    private final PointsManager pointsManager = PointsManager.getInstance();

    @Command(name = "points.pay", aliases = "p.pay", permission = "elowyrbasics.points.pay")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        ProfileData profileSender = ProfileManager.getInstance().getProvider().get(Bukkit.getPlayer(sender.getName()).getUniqueId().toString());

        if (args.length() != 2) {
            sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.PAY.USAGE")));
            return;
        }

        OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args.getArgs(0));
        ProfileData profileReceiver = ProfileManager.getInstance().getProvider().get(target.getUniqueId().toString());

        if (profileReceiver == null) {
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

        if (quantity > profileSender.getPoints()) {
            sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.PAY.NO-POINTS")));
            return;
        }

        profileSender.setPoints(profileSender.getPoints() - quantity);
        profileReceiver.setPoints(profileReceiver.getPoints() + quantity);
        sender.sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.PAY.SUCCESS").replace("%player%", target.getName())
                .replace("%quantity%", String.valueOf(quantity))));
        if (target.isOnline()) {
            target.getPlayer().sendMessage(Utils.color(pointsManager.getConfig().getString("MESSAGES.GIVE.SUCCESS-OTHER")
                    .replace("%quantity%", String.valueOf(quantity)).replace("%player%", sender.getName())));
        }
        PointsManager.getLogs().print(sender.getName() + " pay " + quantity + " points to " + target.getName() + "\n");
    }
}
