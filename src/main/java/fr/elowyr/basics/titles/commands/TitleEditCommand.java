package fr.elowyr.basics.titles.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.titles.TitleManager;
import fr.elowyr.basics.titles.data.Title;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class TitleEditCommand {

    private TitleManager titleManager = TitleManager.getInstance();

    @Command(name = "title.edit", aliases = "tag.edit", permission = "elowyrbasics.titre.edit")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!titleManager.isActive()) {
            ModuleManager.moduleDesactivate(args.getPlayer(), TitleManager.getInstance());
            return;
        }

        if (args.length() != 2) {
            player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.EDIT.USAGE")));
            return;
        }

        if (titleManager.getProvider().getTitles().stream().noneMatch(title -> title.getName().equalsIgnoreCase(args.getArgs(0)))) {
            player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.EDIT.NO-EXIST")));
            return;
        }

        final Title title = titleManager.getTitleByName(args.getArgs(0));

        title.setTitle(Utils.color(args.getArgs(1)));
        titleManager.getProvider().write();
        player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.EDIT.SUCCESS").replace("%title%", title.getTitle())));
    }
}
