package fr.elowyr.basics.bottlexp;

import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class BottleXPListener implements Listener {

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getPlayer().getItemInHand().getType() == Material.EXP_BOTTLE) {
            Player player = event.getPlayer();

            if (player.getItemInHand().hasItemMeta()) {
                final ItemMeta meta = player.getItemInHand().getItemMeta();
                if (meta.getLore().get(0) != null && meta.getLore().get(0).contains(BottleXPManager.getInstance().BOTTLE_LORE)) {
                    if (!BottleXPManager.getInstance().isActive()) {
                        ModuleManager.moduleDesactivate(player, BottleXPManager.getInstance());
                        event.setCancelled(true);
                        return;
                    }
                    event.setCancelled(true);
                    final int xp = Integer.parseInt(meta.getLore().get(0).split(": §6")[1]);
                    if (player.getItemInHand().getAmount() > 1) {
                        player.getItemInHand().setAmount(player.getItemInHand().getAmount() - 1);
                    } else {
                        player.getInventory().removeItem(player.getItemInHand());
                    }
                    player.giveExpLevels(xp);
                    player.sendMessage(Utils.color(BottleXPManager.getInstance().getConfig().getString("MESSAGES.ADD").replace("%level%", String.valueOf(xp))));
                }
            }
        }
    }
}
