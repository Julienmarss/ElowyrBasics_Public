package fr.elowyr.basics.points.commands;

import fr.elowyr.basics.points.PointsManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.command.CommandSender;

public class PointsCommand {

    private final PointsManager pointsManager = PointsManager.getInstance();

    @Command(name = "points", aliases = "p", permission = "elowyrbasics.points.help")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        for (String usage : pointsManager.getConfig().getStringList("MESSAGES.USAGE")) {
            sender.sendMessage(Utils.color(usage));
        }
    }
}
