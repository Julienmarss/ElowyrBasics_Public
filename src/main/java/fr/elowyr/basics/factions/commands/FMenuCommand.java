package fr.elowyr.basics.factions.commands;

import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.util.TL; 
import fr.elowyr.basics.factions.gui.FMenuGUI;
import fr.elowyr.basics.utils.Utils;

public class FMenuCommand extends FCommand {

    public FMenuCommand() {
        this.aliases.add("menu");
    }

    @Override
    public void perform(CommandContext commandContext) {
        if (!commandContext.fPlayer.hasFaction()) {
            commandContext.player.sendMessage(Utils.color("&6&lFaction &7◆ &fVous &cdevez&f avoir une &efaction&f."));
            return;
        }

        FMenuGUI gui = new FMenuGUI(commandContext.player);
        gui.open(commandContext.player);
    }

    @Override
    public TL getUsageTranslation() {
        return null;
    }
}
