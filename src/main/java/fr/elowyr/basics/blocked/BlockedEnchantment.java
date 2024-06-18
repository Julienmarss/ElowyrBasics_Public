package fr.elowyr.basics.blocked;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class BlockedEnchantment implements Listener {

    @EventHandler
    public void onEnchantDisable(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();

        if (damager == null) return;

        if (!(damager instanceof Player)) {
            return;
        }
        Player player = (Player) damager;
        ItemStack item = player.getItemInHand();
        if (item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) > 6 && player.hasPermission("elowyrbasics.enchant.bypass")
                || item.getEnchantmentLevel(Enchantment.KNOCKBACK) >= 1 && player.hasPermission("elowyrbasics.enchant.bypass")) {
            event.setCancelled(true);
            item.setType(Material.AIR);
        }
    }
}
