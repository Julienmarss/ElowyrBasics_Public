package fr.elowyr.basics.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.HORSE) event.setCancelled(true);
        if (event.getEntityType() == EntityType.IRON_GOLEM) {
            event.getEntity().setHealth(5.0D);
        }
    }
}
