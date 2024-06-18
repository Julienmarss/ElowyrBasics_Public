package fr.elowyr.basics.missions.commands;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.missions.MissionManager;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class MissionResetCommand {

    @Command(name = "mission.reset", permission = "elowyrbasics.mission.reset")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!MissionManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, MissionManager.getInstance());
            return;
        }
        
        if (args.length() != 1) {
            player.sendMessage(Utils.color("§4§l✘ &7◆ &c/mission reset <faction>."));
            return;
        }

        Faction faction = Factions.getInstance().getByTag(args.getArgs(0));
        if (faction == null) {
            player.sendMessage(Utils.color("§4§l✘ &7◆ &cLa faction &4" + args.getArgs(0) + " &cn'existe pas."));
            return;
        }
        FactionData factionData = FactionManager.getInstance().getProvider().get(faction.getId());
        if (factionData == null) {
            player.sendMessage(Utils.color("§4§l✘ &7◆ &cVeuillez contacter un Administrateur."));
            return;
        }

        if (factionData.getMissions().isEmpty()) {
            player.sendMessage(Utils.color("§4§l✘ &7◆ &cLa faction ne possède pas de Mission."));
            return;
        }
        factionData.getMissions().clear();
        factionData.getMissionIdFinish().clear();
        factionData.getMissionIdProgress().clear();
        player.sendMessage(Utils.color("&2&l✔ &7◆ &fVous avez &6réinitialiser &fles &6Missions &fde &e" + factionData.getFactionName() + " &f!"));
    }
}
