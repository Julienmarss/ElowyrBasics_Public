package fr.elowyr.basics.titles.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.titles.TitleManager;
import fr.elowyr.basics.titles.data.Title;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class TitleCreateCommand {

    private TitleManager titleManager = TitleManager.getInstance();

    @Command(name = "title.create", aliases = "tag.create", permission = "elowyrbasics.title.create")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!titleManager.isActive()) {
            ModuleManager.moduleDesactivate(args.getPlayer(), TitleManager.getInstance());
            return;
        }

        if (args.length() != 2) {
            player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.CREATE.USAGE")));
            return;
        }
        if (titleManager.getProvider().getTitles().stream().anyMatch(title -> title.getName().equalsIgnoreCase(args.getArgs(0)))) {
            player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.CREATE.ALREADY-EXIST")));
            return;
        }

        final Title title = new Title(args.getArgs(0), Utils.color(args.getArgs(1)));

        titleManager.getProvider().provide(title.getName(), title);
        titleManager.getProvider().write();
        player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.CREATE.SUCCESS").replace("%title%", title.getTitle())));
    }
}
