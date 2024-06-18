package fr.elowyr.basics.joyaux.commands;

import fr.elowyr.basics.joyaux.JoyauxManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.command.CommandSender;

public class JoyauxCommand {

    private final JoyauxManager joyauxManager = JoyauxManager.getInstance();

    @Command(name = "joyaux", permission = "elowyrbasics.joyaux.help")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        for (String usage : joyauxManager.getConfig().getStringList("MESSAGES.USAGE")) {
            sender.sendMessage(Utils.color(usage));
        }
    }
}
