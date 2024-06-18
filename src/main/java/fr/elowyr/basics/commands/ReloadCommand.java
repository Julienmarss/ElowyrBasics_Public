package fr.elowyr.basics.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.lang.Lang;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;

public class ReloadCommand {

    @Command(name = "elowyrbasics.reload", permission = "elowyrbasics.reload")
    public void onCommand(CommandArgs commandArgs) {
        Lang.get().load();
        ElowyrBasics.getInstance().reloadConfig();
        Lang.send(commandArgs.getSender(), "reload-config");
    }

}
