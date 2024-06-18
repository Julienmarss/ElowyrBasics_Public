package fr.elowyr.basics.upgrade.listeners.type;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.upgrade.UpgradeManager;
import fr.elowyr.basics.upgrade.data.Upgrade;
import fr.elowyr.basics.upgrade.data.UpgradeType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class KillEntityUpgradeListener implements Listener {

    private UpgradeManager upgradeManager = UpgradeManager.getInstance();

    @EventHandler
    public void onBreak(EntityDeathEvent event) {
        if (event.getEntity() instanceof Player) return;

        if (event.getEntity().getKiller() == null) return;

        Player player = event.getEntity().getKiller();
        Entity entity = event.getEntity();


        Faction faction = FPlayers.getInstance().getByPlayer(player).getFaction();
        FactionData factionData = upgradeManager.getFactionByName(faction.getTag());
        if (factionData == null) return;
        List<Upgrade> upgrades = upgradeManager.getUpgradeByTarget(factionData, entity.getType().name());
        if (upgrades == null) return;
        upgrades.forEach(upgrade -> {
            System.out.println(upgrade);
            if (upgrade == null) return;

            if (upgrade.getUpgradeType() != UpgradeType.KILL_MOB) return;
            if (upgrade.getActualGoal() >= upgrade.getGoal()) return;

            upgrade.setActualGoal(upgrade.getActualGoal() + 1);
        });
    }
}
