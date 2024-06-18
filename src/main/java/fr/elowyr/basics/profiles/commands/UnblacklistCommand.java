package fr.elowyr.basics.profiles.commands;

import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class UnblacklistCommand {

    @Command(name = "unblacklist", permission = "elowyrbasics.unblacklist")
    public void onCommand(CommandArgs args) {
        CommandSender player = args.getSender();

        if (args.length() != 1) {
            player.sendMessage(Utils.color("&6&lServeur &7◆ &c/unblacklist <player>."));
            return;
        }

        OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args.getArgs(0));
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(target.getUniqueId().toString());

        if (profileData == null) {
            player.sendMessage(Utils.color("&6&lServeur &7◆ &fLe joueur &e" + target.getName() + "&f est &cinconnu&f."));
            return;
        }

        if (!profileData.isBlacklist()) {
            player.sendMessage(Utils.color("&6&lServeur &7◆ &fLe joueur &e" + target.getName() + "&f n'est pas&a blacklist&f du serveur."));
            return;
        }

        profileData.setBlacklist(false);
        Bukkit.broadcastMessage(Utils.color("&6&lServeur &7◆ &fLe joueur &e" + target.getName() + "&f à été &aunblacklist&f du serveur."));
    }
}
