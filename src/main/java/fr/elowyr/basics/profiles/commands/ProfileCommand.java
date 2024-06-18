package fr.elowyr.basics.profiles.commands;

import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.command.CommandSender;

public class ProfileCommand {

    @Command(name = "profile.list", permission = "elowyrbasics.profile.list")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        sender.sendMessage(Utils.color("&6&lProfiles &7◆ &fIl y a &aactuellement &c&l" + ProfileManager.getInstance().getProvider().getProfiles().size() + " &fprofiles &2enregistrés&f !"));
    }
}
