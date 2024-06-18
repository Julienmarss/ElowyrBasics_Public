package fr.elowyr.basics.cooldown;

import fr.elowyr.basics.utils.CooldownUtils;
import fr.elowyr.basics.utils.DurationFormatter;
import fr.elowyr.basics.utils.RegionUtils;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EnderPearlCooldown implements Listener {

    private CooldownManager cooldownManager = CooldownManager.getInstance();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!CooldownManager.getInstance().isActive()) {
            return;
        }

        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)
                && event.getItem() != null && event.getItem().getType() == Material.ENDER_PEARL) {
            for (String regions : CooldownManager.getInstance().getConfig().getStringList("REGION-DISABLED-ENDERPEARL")) {
                if (RegionUtils.inArea(player.getLocation(), player.getWorld(), regions)) {
                    player.updateInventory();
                    event.setCancelled(true);
                    return;
                }
            }

            if (!RegionUtils.inArea(player.getLocation(), player.getWorld(), "warzone")) {
                if (CooldownUtils.isOnCooldown("enderpearl_nature", player)) {
                    player.sendMessage(Utils.color(cooldownManager.getConfig().getString("MESSAGES.ENDERPEARL.NATURE")
                            .replace("%time%", String.valueOf(DurationFormatter.getRemaining(CooldownUtils.getCooldownForPlayerLong("enderpearl_nature", player), true)))));
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
                CooldownUtils.addCooldown("enderpearl_nature", player, cooldownManager.getConfig().getInt("TIME.ENDERPEARL.NATURE"));
            } else {
                if (CooldownUtils.isOnCooldown("enderpearl_warzone", player)) {
                    player.sendMessage(Utils.color(cooldownManager.getConfig().getString("MESSAGES.ENDERPEARL.NATURE")
                            .replace("%time%", String.valueOf(DurationFormatter.getRemaining(CooldownUtils.getCooldownForPlayerLong("enderpearl_warzone", player), true)))));
                    event.setCancelled(true);
                    player.updateInventory();
                    return;
                }
                CooldownUtils.addCooldown("enderpearl_warzone", player, cooldownManager.getConfig().getInt("TIME.ENDERPEARL.WARZONE"));
            }
        }
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getItem() != null && event.getItem().getType() == Material.SNOW_BALL) {
            event.setCancelled(true);
        }
    }
}