package fr.elowyr.basics.profiles.commands;

import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlacklistCommand {

    @Command(name = "blacklist", permission = "elowyrbasics.blacklist")
    public void onCommand(CommandArgs args) {
        CommandSender player = args.getSender();

        if (args.length() != 1) {
            player.sendMessage(Utils.color("&6&lServeur &7◆ &c/blacklist <player>."));
            return;
        }

        Player target = Bukkit.getServer().getPlayer(args.getArgs(0));
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(target.getUniqueId().toString());

        if (profileData == null) {
            player.sendMessage(Utils.color("&6&lServeur &7◆ &fLe joueur &e" + target.getName() + "&f est &cinconnu&f."));
            return;
        }

        if (profileData.isBlacklist()) {
            player.sendMessage(Utils.color("&6&lServeur &7◆ &fLe joueur &e" + target.getName() + "&f est déjà&c blacklist&f du serveur."));
            return;
        }

        profileData.setBlacklist(true);
        Bukkit.broadcastMessage(Utils.color("&6&lServeur &7◆ &fLe joueur &e" + target.getName() + "&f à été &cblacklist&f du serveur."));
        target.kickPlayer(Utils.color("&6&lServeur &7◆ &fVous avez été &cblacklist &fdu serveur."));
    }
}
