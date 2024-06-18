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

public class ArmorDurability extends AbstractPickaxe {

    public ArmorDurability() {
        super("5", 5, true);
    }

    @Override
    public RItemUnsafe getRItemUnsafe() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.NETHER_STAR).setName(ElowyrBasics.getInstance().getConfig().getString("ITEMS.ARMOR-DURABILITY.NAME"))
                .setLore("§7§m---|-----------------------------|---",
                        "§b✯✯✯§7✯✯ §b§lRARE",
                        "",
                        " §7§l▶ §fCette §eobjet§f vous permet de",
                        "§avoir&f la durabilité du stuff de l'§cennemi§f.",
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
                .setName(ElowyrBasics.getInstance().getConfig().getString("ITEMS.ARMOR-DURABILITY.NAME"))
                .setLore("§7§m---|-----------------------------|---",
                        "§b✯✯✯§7✯✯ §b§lRARE",
                        "",
                        " §7§l▶ §fCette §eobjet§f vous permet de",
                        "§avoir&f la durabilité du stuff de l'§cennemi§f.",
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
        if (target.getInventory().getHelmet() == null && target.getInventory().getChestplate() == null && target.getInventory().getLeggings() == null && target.getInventory().getBoots() == null) {
            player.sendMessage(Utils.color("&a&lDurabilité &7◆ &6&l" + target.getName() + "&f n'a pas d'armure."));
            return;
        }
        player.sendMessage(Utils.color("&a&lDurabilité &7◆ &6&l" + target.getName()));
        if (target.getInventory().getHelmet() != null) {
            //POUR METTRE EN % (MaxDura - duraActu) / MaxDura * 100
            player.sendMessage(Utils.color("&e" + (target.getInventory().getHelmet().getType().getMaxDurability() - target.getInventory().getHelmet().getDurability()) + " &fdurabilités sur son &7Casque&f."));
        } else {
            player.sendMessage(Utils.color("&e" + "0 &fdurabilités sur son &7Casque&f."));
        }
        if (target.getInventory().getChestplate() != null ) {
            player.sendMessage(Utils.color("&e" + (target.getInventory().getChestplate().getType().getMaxDurability() - target.getInventory().getChestplate().getDurability()) + " &fdurabilités sur son &7Plastron&f."));
        } else {
            player.sendMessage(Utils.color("&e" + "0 &fdurabilités sur son &7Plastron&f."));
        }
        if (target.getInventory().getLeggings() != null) {
            player.sendMessage(Utils.color("&e" + (target.getInventory().getLeggings().getType().getMaxDurability() - target.getInventory().getLeggings().getDurability()) + " &fdurabilités sur son &7Pantalon&f."));
        } else {
            player.sendMessage(Utils.color("&e" + "0 &fdurabilités sur son &7Pantalon&f."));
        }
        if (target.getInventory().getBoots() != null) {
            player.sendMessage(Utils.color("&e" + (target.getInventory().getBoots().getType().getMaxDurability() - target.getInventory().getBoots().getDurability()) + " &fdurabilités sur ses &7Bottes&f."));
        } else {
            player.sendMessage(Utils.color("&e" +"0 &fdurabilités sur ses &7Bottes&f."));
        }
        if (rnbtItem.getInt("durability") <= 1) {
            Utils.decrementItem(player, player.getItemInHand(), 1);
            player.playSound(player.getLocation(), Sound.ITEM_BREAK, 5, 5);
            return;
        }
        player.setItemInHand(rnbtItem.setInt("durability", rnbtItem.getInt("durability") - 1).build());
        player.setItemInHand(this.getUpdateItem(player.getItemInHand(), rnbtItem).toItemStack());
    }
}
