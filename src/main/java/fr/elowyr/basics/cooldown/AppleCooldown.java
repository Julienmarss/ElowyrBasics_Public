package fr.elowyr.basics.cooldown;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.utils.CooldownUtils;
import fr.elowyr.basics.utils.DurationFormatter;
import fr.elowyr.basics.utils.RegionUtils;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class AppleCooldown implements Listener {
    private CooldownManager cooldownManager = CooldownManager.getInstance();

    @EventHandler
    public void onItemConsume(final PlayerItemConsumeEvent event) {
        final Player player = event.getPlayer();
        if (event.getItem().getType() != Material.GOLDEN_APPLE) return;

        if (!CooldownManager.getInstance().isActive()) {
            return;
        }

        if (event.getItem().getDurability() == 1) {
            if (!cooldownManager.getConfig().getBoolean("CHEATAPPLE")) {
                player.sendMessage(Utils.color(cooldownManager.getConfig().getString("MESSAGES.CHEATAPPLE-DISABLED")));
                player.updateInventory();
                event.setCancelled(true);
                return;
            }
            for (String regions : CooldownManager.getInstance().getConfig().getStringList("REGION-DISABLED-APPLE")) {
                if (RegionUtils.inArea(player.getLocation(), player.getWorld(), regions)) {
                    player.updateInventory();
                    event.setCancelled(true);
                    return;
                }
            }
            if (CooldownUtils.isOnCooldown("gapple", player)) {
                player.sendMessage(Utils.color(cooldownManager.getConfig().getString("MESSAGES.CHEATAPPLE.CANNOT")
                        .replace("%time%", DurationFormatter.getRemaining(CooldownUtils.getCooldownForPlayerLong("gapple", player), false))));
                player.updateInventory();
                event.setCancelled(true);
                return;
            }
            CooldownUtils.addCooldown("gapple", player, cooldownManager.getConfig().getInt("TIME.CHEATAPPLE"));
            player.sendMessage(Utils.color(cooldownManager.getConfig().getString("MESSAGES.CHEATAPPLE.NOW")));
            ElowyrBasics.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(ElowyrBasics.getInstance(),
                    () -> cooldownManager.getConfig().getString("MESSAGES.CHEATAPPLE.END"), (cooldownManager.getConfig().getInt("TIME.CHEATAPPLE") * 20L));
        } else {
            if (!cooldownManager.getConfig().getBoolean("APPLE")) {
                player.sendMessage(Utils.color(cooldownManager.getConfig().getString("MESSAGES.APPLE-DISABLED")));
                event.setCancelled(true);
                return;
            }

            for (String regions : ElowyrBasics.getInstance().getConfig().getStringList("REGION-DISABLED")) {
                if (RegionUtils.inArea(player.getLocation(), player.getWorld(), regions)) return;
            }

            if (CooldownUtils.isOnCooldown("gapple2", player)) {
                player.sendMessage(Utils.color(cooldownManager.getConfig().getString("MESSAGES.APPLE.CANNOT")
                        .replace("%time%", DurationFormatter.getRemaining(CooldownUtils.getCooldownForPlayerLong("gapple2", player), false))));
                player.updateInventory();
                event.setCancelled(true);
                return;
            }
            CooldownUtils.addCooldown("gapple2", player, cooldownManager.getConfig().getInt("TIME.APPLE"));
            player.sendMessage(Utils.color(cooldownManager.getConfig().getString("MESSAGES.APPLE.NOW")));
            ElowyrBasics.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(ElowyrBasics.getInstance(),
                    () -> cooldownManager.getConfig().getString("MESSAGES.APPLE.END"), (cooldownManager.getConfig().getInt("TIME.APPLE") * 20L));
        }
    }
}

