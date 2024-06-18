package fr.elowyr.basics.spawners.pickaxe;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.nbt.RItemUnsafe;
import fr.elowyr.basics.utils.nbt.RNBTItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

public class PotionCounter extends AbstractPickaxe {

    public PotionCounter() {
        super("4", 4, true);
    }

    @Override
    public RItemUnsafe getRItemUnsafe() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.NETHER_STAR).setName(ElowyrBasics.getInstance().getConfig().getString("ITEMS.POTION-COUNTER.NAME"))
                .setLore("§7§m---|-----------------------------|---",
                        "§b✯✯✯§7✯✯ §b§lRARE",
                        "",
                        "§7§l▶ §fCette §eobjet§f vous permet de",
                        "§avoir§f le nombre de §dpotions §fqu'a un §6joueur§f.",
                        "",
                        "§7◆ §fUtilisation: §a5§7/§a5",
                        "§7§m---|-----------------------------|---");
        RItemUnsafe itemUnsafe = new RItemUnsafe(itemBuilder);
        itemUnsafe.setInt("id", this.getId());
        itemUnsafe.setInt("durability", 5);
        return itemUnsafe;
    }

    public ItemBuilder getUpdateItem(ItemStack itemStack, RNBTItem rnbtItem) {
        ItemBuilder itemBuilder = new ItemBuilder(itemStack)
                .setName(ElowyrBasics.getInstance().getConfig().getString("ITEMS.POTION-COUNTER.NAME"))
                .setLore("§7§m---|-----------------------------|---",
                        "§b✯✯✯§7✯✯ §b§lRARE",
                        "",
                        "§7§l▶ §fCette §eobjet§f vous permet de",
                        "§avoir§f le nombre de §dpotions §fqu'a un §6joueur§f.",
                        "",
                        "§7◆ §fUtilisation: §a" + rnbtItem.getInt("durability") + "§7/§a5",
                        "§7§m---|-----------------------------|---");
        return itemBuilder;
    }

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (!isItems(this.getId(), player.getItemInHand())) return;
        Player target = (Player) event.getRightClicked();
        RNBTItem rnbtItem = new RNBTItem(player.getItemInHand());
        player.sendMessage(Utils.color("§dPotions §7◆ §6§l" + target.getName() + " §fpossède §5" + Utils.getPotions(target) + " potion(s)§f."));
        if (rnbtItem.getInt("durability") <= 1) {
            Utils.decrementItem(player, player.getItemInHand(), 1);
            player.playSound(player.getLocation(), Sound.ITEM_BREAK, 5, 5);
            return;
        }
        player.setItemInHand(rnbtItem.setInt("durability", rnbtItem.getInt("durability") - 1).build());
        player.setItemInHand(this.getUpdateItem(player.getItemInHand(), rnbtItem).toItemStack());
    }
}
