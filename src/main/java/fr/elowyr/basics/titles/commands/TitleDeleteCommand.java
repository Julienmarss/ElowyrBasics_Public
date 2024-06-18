package fr.elowyr.basics.titles.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.titles.TitleManager;
import fr.elowyr.basics.titles.data.Title;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class TitleDeleteCommand {

    private TitleManager titleManager = TitleManager.getInstance();

    @Command(name = "title.delete", aliases = "tag.delete", permission = "elowyrbasics.title.delete")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!titleManager.isActive()) {
            ModuleManager.moduleDesactivate(args.getPlayer(), TitleManager.getInstance());
            return;
        }

        if (args.length() != 1) {
            player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.DELETE.USAGE")));
            return;
        }
        if (titleManager.getProvider().getTitles().stream().noneMatch(title -> title.getName().equalsIgnoreCase(args.getArgs(0)))) {
            player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.DELETE.NO-EXIST")));
            return;
        }

        final Title title = TitleManager.getInstance().getTitleByName(args.getArgs(0));

        titleManager.getProvider().remove(title.getName());
        player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.DELETE.SUCCESS").replace("%title%", title.getTitle())));
    }
}
