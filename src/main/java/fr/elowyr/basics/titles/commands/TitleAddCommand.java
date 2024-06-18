package fr.elowyr.basics.titles.commands;

import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.titles.TitleManager;
import fr.elowyr.basics.titles.data.Title;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TitleAddCommand {

    private TitleManager titleManager = TitleManager.getInstance();

    @Command(name = "title.add", aliases = "tag.add", permission = "elowyrbasics.title.add")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!titleManager.isActive()) {
            ModuleManager.moduleDesactivate(args.getPlayer(), TitleManager.getInstance());
            return;
        }

        if (args.length() != 2) {
            player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.ADD.USAGE")));
            return;
        }
        OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args.getArgs(0));
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(target.getUniqueId().toString());
        if (profileData == null) {
            player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.INVALID-PLAYER")).replace("%player%", target.getName()));
            return;
        }

        Title title = titleManager.getTitleByName(args.getArgs(1));
        if (title == null) {
            player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.INVALID-TITLE")));
            return;
        }

        if (profileData.getBuyedTitle().contains(title.getName())) {
            player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.ADD.ALREADY").replace("%title%", title.getTitle())));
            return;
        }

        profileData.getBuyedTitle().add(title.getName());
        ProfileManager.getInstance().getProvider().write(profileData);
        player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.ADD.SUCCESS").replace("%player%", target.getName()).replace("%title%", title.getTitle())));
        if (target.isOnline()) {
            target.getPlayer().sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.ADD.SUCCESS-OTHER").replace("%title%", title.getTitle())));
        }
    }
}