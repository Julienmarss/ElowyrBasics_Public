package fr.elowyr.basics.upgrade.listeners.type;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.upgrade.UpgradeManager;
import fr.elowyr.basics.upgrade.data.Upgrade;
import fr.elowyr.basics.upgrade.data.UpgradeType;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class BreakUpgradeListener implements Listener {

    private UpgradeManager upgradeManager = UpgradeManager.getInstance();

    @EventHandler
    public void onBreak(BlockBreakEvent event) {

        if (event.isCancelled()) return;

        Player player = event.getPlayer();
        Block block = event.getBlock();

        Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
        FactionData factionData = upgradeManager.getFactionByName(faction.getTag());
        if (factionData == null) return;
        List<Upgrade> upgrades = upgradeManager.getUpgradeByTarget(factionData, block.getType().name());

        if (upgrades == null) return;

        if (player.getItemInHand() != null && player.getItemInHand().getItemMeta() != null && player.getItemInHand().getItemMeta().getDisplayName() != null
                && player.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.color("&e&l✦ &6&lHoue de l''Empereur &e&l✦"))) return;

        upgrades.forEach(upgrade -> {
            if (upgrade == null) return;

            if (block.getType().name().contains("LOG")) {
                if (block.getData() != upgrade.getItemData().getData()) return;
            }


            if (upgrade.getUpgradeType() != UpgradeType.BREAK) return;
            if (upgrade.getActualGoal() >= upgrade.getGoal()) return;

            upgrade.setActualGoal(upgrade.getActualGoal() + 1);
        });
    }
}
