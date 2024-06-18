package fr.elowyr.basics.upgrade.listeners.advantage;

import com.massivecraft.factions.*;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ReduceDamageListener implements Listener {

    @EventHandler
    public void onReduceDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager().getType() != EntityType.PLAYER) return;
        Player damager = (Player) event.getDamager();
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(damager);
        Faction faction = Factions.getInstance().getFactionById(fPlayer.getFactionId());
        FLocation fLocation = new FLocation(damager.getLocation());
        FactionData factionData = FactionManager.getInstance().getProvider().get(faction.getId());
        Faction board = Board.getInstance().getFactionAt(fLocation);
        if (factionData == null) return;
        if (board == null) return;
        if (Board.getInstance().getFactionAt(fLocation).equals(faction)) {
            double damage = event.getDamage();
            double reducedDamage = damage;

            if (factionData.getUpgradeLevel() >= 5) {
                reducedDamage = damage * 0.98; //-2%
            } else if (factionData.getUpgradeLevel() >= 7) {
                reducedDamage = damage * 0.96; //-4%
            } else if (factionData.getUpgradeLevel() >= 8) {
                reducedDamage = damage * 0.94; //-6%
            }
            event.setDamage(reducedDamage);
        }
    }
}
