package fr.elowyr.basics.missions;

import fr.elowyr.basics.missions.data.MissionType;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class MissionListener implements Listener {
    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType() == null) return;

        if (block.getData() < Utils.getRequiredMeta(block)) return;
        int xp = 1;

        if (!MissionManager.getInstance().isActive()) {
            return;
        }
        if (player.getItemInHand() != null && player.getItemInHand().getItemMeta() != null && player.getItemInHand().getItemMeta().getDisplayName() != null && player.getInventory().getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase(Utils.color("&e&l✦ &6&lHoue de l'Empereur &e&l✦"))) {
            return;
        }

        MissionManager.getInstance().updateMission(MissionType.BREAK_BLOCK, player, block.getType(), xp, true);
    }

    @EventHandler
    public void onCraft(CraftItemEvent event) {
        if (event.isCancelled()) return;
        Player player = ((Player) event.getView().getPlayer());
        ItemStack itemStack = event.getRecipe().getResult();

        if (!MissionManager.getInstance().isActive()) {
            return;
        }

        MissionManager.getInstance().updateMission(MissionType.CRAFT, player, itemStack.getType(), 1, true);
    }

    @EventHandler
    public void onKill(EntityDeathEvent event) {
        EntityType entity = event.getEntityType();
        if (event.getEntity().getKiller() != null && event.getEntity().getKiller().getType().equals(EntityType.PLAYER)) {
            int points = 1;

            if (!MissionManager.getInstance().isActive()) {
                return;
            }

            MissionManager.getInstance().updateMission(MissionType.KILL, event.getEntity().getKiller(), entity, points, true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) return;
        if (event.getBlock() == null) return;

        if (!MissionManager.getInstance().isActive()) {
            return;
        }

        MissionManager.getInstance().updateMission(MissionType.BLOCK_PLACE, event.getPlayer(), event.getBlock().getType(), 1, true);
    }
}