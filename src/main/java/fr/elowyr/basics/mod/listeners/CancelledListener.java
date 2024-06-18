package fr.elowyr.basics.mod.listeners;

import fr.elowyr.basics.mod.ModManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class CancelledListener implements Listener {

    private ModManager modManager = ModManager.getInstance();

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (modManager.isStaff((Player) event.getWhoClicked())) {
            if (event.getCurrentItem() == null) return;

            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (modManager.isFrozen((Player) event.getEntity())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        if (modManager.isStaff(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (modManager.isStaff(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (modManager.isStaff(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (modManager.isStaff(event.getPlayer())) event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLost(FoodLevelChangeEvent event) {
        if (modManager.isStaff((Player) event.getEntity())) event.setCancelled(true);
    }
}
