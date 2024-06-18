package fr.elowyr.basics.upgrade.commands;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.cmd.CommandContext;
import com.massivecraft.factions.cmd.FCommand;
import com.massivecraft.factions.util.TL;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionChest;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.upgrade.UpgradeManager;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.entity.Player;

public class FChestCommand extends FCommand {

    public FChestCommand() {
        this.aliases.add("chest");
    }

    @Override
    public void perform(CommandContext commandContext) {
        Player player = commandContext.player;
        FPlayer factionPlayer = FPlayers.getInstance().getByPlayer(player);

        if (!UpgradeManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, UpgradeManager.getInstance());
            return;
        }

        if (!factionPlayer.hasFaction()) {
            player.sendMessage(Utils.color("&6&lFaction &7◆ &fVous &cdevez&f avoir une &efaction&f."));
            return;
        }

        Faction faction = factionPlayer.getFaction();
        FactionData factionData = FactionManager.getInstance().getProvider().get(faction.getId());

        if (factionData == null) {
            player.sendMessage(Utils.color("&cContactez un Administrateur (error: file nul)"));
            return;
        }

        FactionChest factionChest = factionData.getChest();
        if (factionData.getUpgradeLevel() >= 2) {
            factionChest.openFactionChest(player, 1);
        } else if (factionData.getUpgradeLevel() >= 4) {
            factionChest.openFactionChest(player, 2);
        } else if (factionData.getUpgradeLevel() >= 6) {
            factionChest.openFactionChest(player, 3);
        } else if (factionData.getUpgradeLevel() == 8) {
            factionChest.openFactionChest(player, 4);
        } else {
            player.sendMessage(Utils.color("&fVous &cn'avez &fpas &eaccès&f au f chest&f. &7(/f prestige)"));
        }
    }

    @Override
    public TL getUsageTranslation() {
        return null;
    }
}
