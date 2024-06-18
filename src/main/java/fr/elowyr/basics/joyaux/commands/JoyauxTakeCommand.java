package fr.elowyr.basics.joyaux.commands;

import fr.elowyr.basics.joyaux.JoyauxManager;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class JoyauxTakeCommand {

    private final JoyauxManager joyauxManager = JoyauxManager.getInstance();

    @Command(name = "joyaux.take", permission = "elowyrbasics.joyaux.take")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() != 2) {
            sender.sendMessage(Utils.color(joyauxManager.getConfig().getString("MESSAGES.TAKE.USAGE")));
            return;
        }

        OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args.getArgs(0));
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(target.getUniqueId().toString());

        if (profileData == null) {
            sender.sendMessage(Utils.color(joyauxManager.getConfig().getString("MESSAGES.INVALID-PROFIL").replace("%player%", target.getName())));
            return;
        }

        int quantity = Integer.parseInt(args.getArgs(1));
        if (quantity <= 0) {
            sender.sendMessage(Utils.color(joyauxManager.getConfig().getString("MESSAGES.INVALID-QUANTITY")));
            return;
        }

        profileData.setJoyaux(profileData.getJoyaux() - quantity);
        sender.sendMessage(Utils.color(joyauxManager.getConfig().getString("MESSAGES.TAKE.SUCCESS").replace("%player%", target.getName()).replace("%quantity%", String.valueOf(quantity))));
        if (target.isOnline()) {
            target.getPlayer().sendMessage(Utils.color(joyauxManager.getConfig().getString("MESSAGES.TAKE.SUCCESS-OTHER").replace("%quantity%", String.valueOf(quantity))));
        }
    }
}
