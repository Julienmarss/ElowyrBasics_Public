package fr.elowyr.basics.factions.commands;

import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;

public class PrestigesSaveCommand {

    @Command(name = "prestiges.save", permission = "elowyrbasics.prestige.save")
    public void onCommand(CommandArgs args) {
        FactionManager.getInstance().getProvider().write();
        args.getSender().sendMessage("Save Data Prestiges.");
    }
}
