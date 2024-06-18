package fr.elowyr.basics.spawners.pickaxe;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import fr.elowyr.basics.spawners.SpawnersManager;
import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.nbt.RItemUnsafe;
import fr.elowyr.basics.utils.nbt.RNBTItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnersPickaxeOne extends AbstractPickaxe {

    public SpawnersPickaxeOne() {
        super("1", 1, true);
    }

    @Override
    public RItemUnsafe getRItemUnsafe() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.GOLD_PICKAXE).setName(SpawnersManager.getInstance().getConfig().getString("SPAWNERS.PICKAXE.LEVEL-1.NAME"))
                .setLore("§7§m---|-----------------------------|---",
                        "§b✯✯✯§7✯✯ §b§lRARE",
                        "",
                        "§7§l▶ §fCette §epioche§f vous permet de",
                        "§drécupérer§f vos §5spawneurs§f.",
                        "",
                        "§7◆ §fUtilisation: §a3§7/§a3",
                        "§7§m---|-----------------------------|---");
        RItemUnsafe itemUnsafe = new RItemUnsafe(itemBuilder);
        itemUnsafe.setInt("id", this.getId());
        itemUnsafe.setInt("durability", 3);
        return itemUnsafe;
    }

    public ItemBuilder getUpdateItem(ItemStack itemStack, RNBTItem rnbtItem) {
        ItemBuilder itemBuilder = new ItemBuilder(itemStack)
                .setName(SpawnersManager.getInstance().getConfig().getString("SPAWNERS.PICKAXE.LEVEL-1.NAME"))
                .setLore("§7§m---|-----------------------------|---",
                        "§b✯✯✯§7✯✯ §b§lRARE",
                        "",
                        "§7§l▶ §fCette §epioche§f vous permet de",
                        "§drécupérer§f vos §5spawneurs§f.",
                        "",
                        "§7◆ §fUtilisation: §a" + rnbtItem.getInt("durability") + "§7/§a3",
                        "§7§m---|-----------------------------|---");
        return itemBuilder;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();

        if (!isItems(this.getId(), player.getItemInHand()) || event.isCancelled()) return;

        if (event.getBlock().getType() != Material.MOB_SPAWNER) return;


        RNBTItem rnbtItem = new RNBTItem(player.getItemInHand());

        CreatureSpawner creatureSpawner = (CreatureSpawner) event.getBlock().getState();

        EntityType entityType = creatureSpawner.getSpawnedType();

        if (entityType == EntityType.WOLF) return;

        FLocation fLocation = new FLocation(event.getBlock().getLocation());
        if (!Board.getInstance().getFactionAt(fLocation).equals(FPlayers.getInstance().getByPlayer(player).getFaction()) && !Board.getInstance().getFactionAt(fLocation).equals(Factions.getInstance().getWilderness())) {
            player.sendMessage(Utils.color(SpawnersManager.getInstance().getConfig().getString("MESSAGES.ENNEMI-TERRITORY")));
            event.setCancelled(true);
            return;
        }

        player.getInventory().addItem(new ItemStack(new ItemBuilder(Material.MOB_SPAWNER)
                .setName(SpawnersManager.getInstance().getConfig().getString("SPAWNERS.GIVE.NAME").replace("%spawner%", entityType.getName())).toItemStack()));

        if (rnbtItem.getInt("durability") == 0) {
            Utils.decrementItem(player, player.getItemInHand(), 1);
            player.playSound(player.getLocation(), Sound.ITEM_BREAK, 5, 5);
            return;
        }
        player.setItemInHand(rnbtItem.setInt("durability", rnbtItem.getInt("durability") - 1).build());
        player.setItemInHand(this.getUpdateItem(player.getItemInHand(), rnbtItem).toItemStack());
        SpawnersManager.getLogs().print(player.getName() + " remove " + event.getBlock().getType() + "(Type: " + creatureSpawner.getCreatureTypeName() + ") at x:" + event.getBlock().getX() + ", y:" + event.getBlock().getY() + ", z: " + event.getBlock().getZ() + "\n");
    }
}
