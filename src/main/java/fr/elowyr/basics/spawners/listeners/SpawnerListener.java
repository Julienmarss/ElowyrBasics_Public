package fr.elowyr.basics.spawners.listeners;

import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.spawners.SpawnersManager;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.concurrent.ThreadLocalRandom;

public class SpawnerListener implements Listener {

    private SpawnersManager manager = SpawnersManager.getInstance();

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (block.getType() == Material.MOB_SPAWNER && player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().hasDisplayName()) {

            if (block.getType() == Material.MOB_SPAWNER && player.getInventory().getItemInHand().getItemMeta().getDisplayName()
                    .equalsIgnoreCase(Utils.color("&fSpawneur à &6Ours"))) {
                return;
            }

            if (!SpawnersManager.getInstance().isActive()) {
                ModuleManager.moduleDesactivate(player, SpawnersManager.getInstance());
                event.setCancelled(true);
                return;
            }

            try {
                CreatureSpawner creatureSpawner = (CreatureSpawner) block.getState();
                creatureSpawner.setSpawnedType(EntityType.fromName(player.getInventory().getItemInHand().getItemMeta().getDisplayName()
                        .replace(manager.getConfig().getString("SPAWNERS.GIVE.NAME").replace("%spawner%", ""), "").toLowerCase()));
                player.sendMessage(Utils.color(manager.getConfig().getString("MESSAGES.PLACED-SUCCESS").replace("%spawners%", String.valueOf(creatureSpawner.getCreatureTypeName()))));
                SpawnersManager.getLogs().print(player.getName() + " placed Spawner " + creatureSpawner.getCreatureTypeName() + " at x:" + block.getX() + ", y: " + block.getY() + ", z:" + block.getZ() + "\n");
            } catch (Exception e) {
                e.printStackTrace();
                player.sendMessage(Utils.color("&cUne erreur est survenue !"));
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() != Material.MOB_SPAWNER) return;

        if (!SpawnersManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(event.getPlayer(), SpawnersManager.getInstance());
            event.setCancelled(true);
            return;
        }

        if (event.getPlayer().getItemInHand() == null || event.getPlayer().getItemInHand().getType() == Material.AIR ||
                event.getPlayer().getItemInHand().getType() != Material.GOLD_PICKAXE && !event.getPlayer().getItemInHand().hasItemMeta()
                        && !event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()) {
            event.getPlayer().sendMessage(Utils.color(SpawnersManager.getInstance().getConfig().getString("MESSAGES.BAD-PICKAXE")));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSpawnerBreak(EntityExplodeEvent event) {

        for (Block block : event.blockList()) {

            if (block.getType() == Material.MOB_SPAWNER) {

                event.setCancelled(true);

                int chance = ThreadLocalRandom.current().nextInt(100);
                if (chance > 50) {

                    BlockState blockState = block.getState();
                    CreatureSpawner creatureSpawner = (CreatureSpawner) blockState;
                    EntityType entityType = creatureSpawner.getSpawnedType();

                    SpawnersManager.getInstance().dropItemOnGround(block.getLocation(), entityType);
                }

                block.setType(Material.AIR);
            }

        }
    }


    @EventHandler
    public void spawnEvent(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getItem() != null) {
            if (event.getClickedBlock().getType() == Material.MOB_SPAWNER && event.getItem().getType() == Material.MONSTER_EGG) {
                event.setCancelled(true);
            }
        }
    }
}
