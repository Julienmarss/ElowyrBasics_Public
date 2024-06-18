package fr.elowyr.basics.randomtp;

import fr.elowyr.basics.utils.CooldownUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class RTPListener implements Listener {

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL && CooldownUtils.isOnCooldown("FALL", player)) {
                if (!RTPManager.getInstance().isActive()) {
                    return;
                }
                CooldownUtils.removeCooldown("FALL", player);
                event.setCancelled(true);
            }
        }
    }
}
