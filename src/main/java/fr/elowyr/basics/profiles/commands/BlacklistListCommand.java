package fr.elowyr.basics.profiles.commands;

import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.command.CommandSender;

public class BlacklistListCommand {

    @Command(name = "blacklist.list", permission = "elowyrbasics.blacklistlist")
    public void onCommand(CommandArgs args) {
        CommandSender player = args.getSender();

        if (args.length() != 0) {
            player.sendMessage(Utils.color("&6&lServeur &7◆ &c/blacklist list."));
            return;
        }

        for (ProfileData profile: ProfileManager.getInstance().getProvider().getProfiles()) {
            if (!profile.isBlacklist()) {
                player.sendMessage(Utils.color("&6&lServeur &7◆ &fIl n'y a &caucun&f blacklist sur le serveur."));
                return;
            }
            player.sendMessage(Utils.color("&fListe des &cBlackLists&f:"));
            player.sendMessage(Utils.color("&f- &e" + profile.getPlayerName()));
        }
    }
}
