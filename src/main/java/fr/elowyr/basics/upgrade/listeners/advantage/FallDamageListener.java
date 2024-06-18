package fr.elowyr.basics.upgrade.listeners.advantage;

import com.massivecraft.factions.*;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class FallDamageListener implements Listener {

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
            Faction faction = Factions.getInstance().getFactionById(fPlayer.getFactionId());
            FLocation fLocation = new FLocation(player.getLocation());
            FactionData factionData = FactionManager.getInstance().getProvider().get(faction.getId());
            Faction board = Board.getInstance().getFactionAt(fLocation);
            if (factionData == null) return;
            if (board == null) return;

            if (board.equals(faction)) {
                if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if (factionData.getUpgradeLevel() >= 3) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
}
