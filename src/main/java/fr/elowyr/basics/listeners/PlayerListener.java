package fr.elowyr.basics.listeners;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.utils.BukkitUtils;
import fr.elowyr.basics.utils.ItemBuilder;
import net.ess3.api.events.KitClaimEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.potion.CraftPotionEffectType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.event.block.BeaconEffectEvent;

public class PlayerListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        Player player = event.getPlayer();

        if (player.hasPermission("staff.use")) return;

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player current : Bukkit.getOnlinePlayers()) {
                    BukkitUtils.sendActionBar(current, "§7§l[§a✔§7§l] §a" + player.getName());
                }
            }
        }.runTaskAsynchronously(ElowyrBasics.getInstance());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    BukkitUtils.sendActionBar(player, "§7§l[§c✘§7§l] §c" + event.getPlayer().getName());
                }
            }
        }.runTaskAsynchronously(ElowyrBasics.getInstance());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        PlayerTeleportEvent.TeleportCause cause = event.getCause();
        if (cause == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            event.setCancelled(true);
        } else if (cause == PlayerTeleportEvent.TeleportCause.ENDER_PEARL && this.isBehindBorder(event)) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§6§lElowyr §7§l◆ §cVous ne pouvez pas vous téléporter en dehors de la bordure !");
        }
    }

    private boolean isBehindBorder(PlayerTeleportEvent event) {
        Location to = event.getTo();
        double size = to.getWorld().getWorldBorder().getSize() / 2;
        return to.getZ() > size || to.getZ() < -size || to.getX() > size || to.getX() < -size;
    }

    @EventHandler
    public void onKit(KitClaimEvent event) {
        if (event.getUser().getBase() == null) return;
        if (event.getKit().getName().equals("potions")) {
            giveItems(event.getUser().getBase(), 20, (short) 16421);
            giveItems(event.getUser().getBase(), 1, (short) 8259);
            giveItems(event.getUser().getBase(), 1, (short) 8265);
            giveItems(event.getUser().getBase(), 3, (short) 8226);
        } else if (event.getKit().getName().equals("perso")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "items give " + event.getUser().getName() + " orbe_magique 1");
        }
    }

    private void giveItems(Player player, int amount, short durability) {
        ItemStack potion = new ItemBuilder(Material.POTION, durability).toItemStack();
        for (int i = 0; i < amount; i++) {
            player.getInventory().addItem(potion);
        }
    }

    @EventHandler
    public void craftItem(PrepareItemCraftEvent event) {
        ItemStack itemStack = event.getRecipe().getResult();
        if (itemStack.getType() == Material.GOLDEN_APPLE && itemStack.getDurability() == 1) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
        }
    }

    @EventHandler
    public void onPortal(PortalCreateEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBeacon(BeaconEffectEvent event) {
        if (event.getEffect().getType() == CraftPotionEffectType.INCREASE_DAMAGE && event.getEffect().getAmplifier() == 1) {
            event.setEffect(new PotionEffect(CraftPotionEffectType.INCREASE_DAMAGE, event.getEffect().getDuration(), 0));
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onConsume(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();
        ItemStack itemStack = player.getItemInHand();

        if (itemStack == null || itemStack.getType() == null) return;
        if (itemStack.getType() != Material.POTION) return;

        Bukkit.getScheduler().runTaskLater(ElowyrBasics.getInstance(), () -> {
            player.getInventory().setItemInHand(new ItemStack(Material.AIR));
            player.updateInventory();
        }, 3L);
    }

    @EventHandler
    public void onTntDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            if (event.getEntity() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }
}
