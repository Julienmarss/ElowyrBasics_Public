package fr.elowyr.basics.listeners;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.lang.Lang;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceBlockListener implements Listener {

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event) {
        for (String blocks : ElowyrBasics.getInstance().getConfig().getStringList("blocked.blocks")) {
            if (event.getBlock().getType().equals(Material.valueOf(blocks))) {
                event.setCancelled(true);
                Lang.send(event.getPlayer(), "blocked.placed");
            }
        }
    }
}
