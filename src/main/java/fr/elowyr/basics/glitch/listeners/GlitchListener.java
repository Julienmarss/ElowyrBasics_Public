package fr.elowyr.basics.glitch.listeners;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.Random;

public class GlitchListener implements Listener {

    @EventHandler
    public void onBlockPLace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (event.isCancelled()) {
            Random random = new Random();
            if (event.getBlockPlaced().getX() == player.getLocation().getBlockX() && event.getBlockPlaced().getZ() == player.getLocation().getBlockZ()) {
                player.teleport(new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), random.nextInt(100), player.getLocation().getYaw()));
            }
        }
    }

    @EventHandler
    public void onMountSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.HORSE || event.getEntityType() == EntityType.WITHER || event.getEntityType() == EntityType.ENDER_DRAGON) {
            event.setCancelled(true);
        }
    }


}
