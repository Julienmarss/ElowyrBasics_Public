package fr.elowyr.basics.assets;

import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.BukkitUtils;
import org.bukkit.Material;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class AssetListener implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getCause().equals(EntityDamageEvent.DamageCause.FALL) && AssetManager.getInstance().hasNoFall(player)) {
                if (!AssetManager.getInstance().isActive()) {
                    ModuleManager.moduleDesactivate(event.getEntity(), AssetManager.getInstance());
                    return;
                }
                event.setCancelled(true);
            }
            if (event.getCause().equals(EntityDamageEvent.DamageCause.MAGIC) || event.getCause().equals(EntityDamageEvent.DamageCause.POISON) || event.getCause().equals(EntityDamageEvent.DamageCause.WITHER)) {
                if (!AssetManager.getInstance().isActive()) {
                    ModuleManager.moduleDesactivate(event.getEntity(), AssetManager.getInstance());
                    return;
                }
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (AssetManager.getInstance().hasNoFood(player))
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getEntity().getKiller() instanceof Player)) return;
        if (AssetManager.getInstance().hasAntiCleanUP((event.getEntity()).getKiller())) {
            event.getEntity().getKiller().setHealth(20.0D);
            event.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16421));
            event.getEntity().getKiller().getInventory().addItem(new ItemStack(Material.POTION, 1, (short) 16421));
        }
    }

    @EventHandler
    public void onRod(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        if (AssetManager.getInstance().hasNoRod(((Player) event.getEntity()).getPlayer()) && event.getDamager() instanceof FishHook && event.getEntity() instanceof Player) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRodUse(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.FISHING && AssetManager.getInstance().hasNoRod(event.getPlayer())) {
            event.setCancelled(true);
            BukkitUtils.sendActionBar(event.getPlayer(), "§4§l✘ §7§l• §cImpossible d'utiliser votre canne à pêche");
        }
    }
}
