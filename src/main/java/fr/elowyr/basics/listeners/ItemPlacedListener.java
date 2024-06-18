package fr.elowyr.basics.listeners;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.lang.Lang;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemPlacedListener implements Listener {

    @EventHandler
    public void onItemInventory(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        for (String items : ElowyrBasics.getInstance().getConfig().getStringList("blocked.items")) {
            if (event.getItem().getType() == Material.valueOf(items)) {
                event.setCancelled(true);
                Lang.send(event.getPlayer(), "blocked.placed");
            }
        }
    }
}
