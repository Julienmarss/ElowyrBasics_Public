package fr.elowyr.basics.utils;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class ItemMaker
{
    private final ItemStack stack;
    private final ItemMeta meta;

    private ItemMaker(final ItemStack stack) {
        this.stack = stack;
        this.meta = stack.getItemMeta();
    }
    
    public ItemMaker lore(final List<String> lore) {
        this.meta.setLore(lore);
        return this;
    }
    
    public ItemMaker displayName(final String displayName) {
        this.meta.setDisplayName(displayName);
        return this;
    }
    
    public ItemStack build() {
        this.stack.setItemMeta(this.meta);
        return this.stack;
    }
    
    public ItemMaker addEnchant(final Enchantment ench, final int level) {
        if (this.meta instanceof EnchantmentStorageMeta) {
            ((EnchantmentStorageMeta)this.meta).addStoredEnchant(ench, level, true);
        }
        else {
            this.meta.addEnchant(ench, level, true);
        }
        return this;
    }
    
    public ItemMaker addFlags(final ItemFlag[] flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemMaker addFlag(final ItemFlag flags) {
        this.meta.addItemFlags(flags);
        return this;
    }

    public ItemMaker owner(final String player) {
        if (this.meta instanceof SkullMeta) {
            ((SkullMeta)this.meta).setOwner(player);
        }
        return this;
    }
    
    public ItemMaker setColor(final Color color) {
        if (this.meta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta)this.meta).setColor(color);
        }
        return this;
    }
    
    public static ItemMaker newBuilder(final Material type, final int data) {
        return new ItemMaker(new ItemStack(type, 1, (short)data));
    }

    public static ItemMaker newBuilder(ItemStack stack) {
        return new ItemMaker(stack);
    }
}
