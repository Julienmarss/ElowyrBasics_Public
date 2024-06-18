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

public class JoyauxLookCommand {

    private final JoyauxManager joyauxManager = JoyauxManager.getInstance();

    @Command(name = "joyaux.look", permission = "elowyrbasics.joyaux.look")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() != 1) {
            sender.sendMessage(Utils.color(joyauxManager.getConfig().getString("MESSAGES.LOOK.USAGE")));
            return;
        }

        OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args.getArgs(0));
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(target.getUniqueId().toString());

        if (profileData == null) {
            sender.sendMessage(Utils.color(joyauxManager.getConfig().getString("MESSAGES.INVALID-PROFIL").replace("%player%", target.getName())));
            return;
        }

        sender.sendMessage(Utils.color(joyauxManager.getConfig().getString("MESSAGES.LOOK.INFO").replace("%player%", target.getName()).replace("%joyaux%", String.valueOf(profileData.getJoyaux()))));
    }
}
