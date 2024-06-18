package fr.elowyr.basics.listeners;

import fr.elowyr.basics.utils.RegionUtils;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;

public class EnchantsListener implements Listener {

    private final Enchantment[] enchantments = {Enchantment.KNOCKBACK, Enchantment.THORNS};
    private Field a;

    public EnchantsListener() {
        try {
            this.a = ContainerAnvil.class.getDeclaredField("j");
            this.a.setAccessible(true);
        } catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        if (itemStack == null || itemStack.getType() == Material.AIR) {
            return;
        }

        for (Enchantment enchantment : enchantments) {
            itemStack.removeEnchantment(enchantment);
        }
    }

    @EventHandler
    public void anvil(InventoryClickEvent event) {
        Location location = event.getWhoClicked().getLocation();
        Inventory inventory = event.getClickedInventory();
        if (inventory == null || inventory.getType() != InventoryType.ANVIL || !RegionUtils.inArea(location, location.getWorld(), "spawn")) {
            return;
        }

        ContainerAnvil containerAnvil = (ContainerAnvil) ((CraftInventoryView) event.getView()).getHandle();
        try {
            BlockPosition blockPosition = (BlockPosition) this.a.get(containerAnvil);
            new Location(location.getWorld(), blockPosition.getX() + 0.5D, blockPosition.getY() + 0.5D, blockPosition.getZ() + 0.5D).getBlock().setData((byte) 1);
        } catch (IllegalAccessException exception) {
            exception.printStackTrace();
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (!(inventory instanceof AnvilInventory)) {
            return;
        }
        if (event.getRawSlot() != 2) {
            return;
        }
        ItemStack current = event.getCurrentItem();
        if (current != null && current.getType() != Material.AIR) {
            for (Enchantment enchantment : this.enchantments) {
                current.removeEnchantment(enchantment);
            }
        }
    }

    @EventHandler
    public void onSword(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        if (!(damager instanceof Player)) return;

        Player player = (Player) damager;
        ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR || player.isOp()) return;
        if (item.getEnchantmentLevel(Enchantment.DAMAGE_ALL) > 5) {
            event.setCancelled(true);
            item.setType(Material.AIR);
        }
    }

  /*  @EventHandler
    public void onEnchant(EnchantItemEvent event) {
        for (Enchantment enchantment : enchantments) {
            event.getEnchantsToAdd().remove(enchantment);
        }
    }*/

}
