package fr.elowyr.basics.upgrade.listeners.type;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.upgrade.UpgradeManager;
import fr.elowyr.basics.upgrade.data.Upgrade;
import fr.elowyr.basics.upgrade.data.UpgradeType;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.projectiles.ProjectileSource;

import java.util.List;

public class EnderPearlUpgradeListener implements Listener {

    private UpgradeManager upgradeManager = UpgradeManager.getInstance();

    @EventHandler
    public void onThrowPearl(ProjectileLaunchEvent event) {

        ProjectileSource projectileSource = event.getEntity().getShooter();

        if (!(event.getEntity() instanceof EnderPearl)) return;
        if (event.isCancelled()) return;
        if (!(projectileSource instanceof Player)) return;

        Player player = (Player) projectileSource;

        Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
        FactionData factionData = upgradeManager.getFactionByName(faction.getTag());
        if (factionData == null) return;
        List<Upgrade> upgrades = upgradeManager.getUpgradeByTarget(factionData, "ENDER_PEARL");

        if (upgrades == null) return;

        upgrades.forEach(upgrade -> {
            if (upgrade == null) return;


            if (upgrade.getUpgradeType() != UpgradeType.THROW) return;
            if (upgrade.getActualGoal() >= upgrade.getGoal()) return;

            upgrade.setActualGoal(upgrade.getActualGoal() + 1);
        });
    }
}
