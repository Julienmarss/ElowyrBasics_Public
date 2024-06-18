package fr.elowyr.basics.missions.commands;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.util.TL;
import fr.elowyr.basics.missions.MissionManager;
import fr.elowyr.basics.missions.guis.MissionGUI;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.entity.Player;

public class MissionCommand extends FCommand {

    public MissionCommand() {
        this.aliases.add("mission");
        this.aliases.add("missions");
    }

    @Override
    public void perform(CommandContext commandContext) {
        final Player player = commandContext.player;
        
        if (!MissionManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, MissionManager.getInstance());
            return;
        }
        
        if(FPlayers.getInstance().getByPlayer(player).getFaction().isWilderness()) {
            player.sendMessage(Utils.color("&6&lFaction &7◆ &fVous &cdevez&f avoir une &efaction&f."));
            return;
        }
        final MissionGUI gui = new MissionGUI(1, player, MissionManager.getInstance());
        gui.open(player);
    }

    @Override
    public TL getUsageTranslation() {
        return TL.COMMAND_AUTOHELP_HELPFOR;
    }
}