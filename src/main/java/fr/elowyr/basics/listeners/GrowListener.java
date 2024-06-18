package fr.elowyr.basics.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class GrowListener implements Listener {

    @EventHandler
    public void onBlockGrow(PlayerInteractEvent event) {

        if (event.getClickedBlock() == null || event.getClickedBlock().getType() != Material.SOIL) return;
        if (event.getClickedBlock().getType() == Material.SOIL && event.getAction() == Action.PHYSICAL) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPiston(BlockPistonExtendEvent event) {
        if (event.getBlock().getType() == Material.MELON_BLOCK) {
            event.setCancelled(true);
        }
    }
}
