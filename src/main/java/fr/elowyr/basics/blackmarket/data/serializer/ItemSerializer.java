package fr.elowyr.basics.blackmarket.data.serializer;

import fr.elowyr.basics.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemSerializer {

    private String type;
    private String name;
    private int quantity;
    private LoreSerializer loreSerializer;
    private Map<Enchantment, Integer> enchants;

    public ItemSerializer(String type, String name, int quantity, LoreSerializer loreSerializer, Map<Enchantment, Integer> enchants) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.loreSerializer = loreSerializer;
        this.enchants = enchants;
    }

    public ItemSerializer(String type, String name, int quantity, Map<Enchantment, Integer> enchants) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
        this.enchants = enchants;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LoreSerializer getLoreSerializer() {
        return loreSerializer;
    }

    public void setLoreSerializer(LoreSerializer loreSerializer) {
        this.loreSerializer = loreSerializer;
    }

    public Map<Enchantment, Integer> getEnchants() {
        return enchants;
    }

    public void setEnchants(Map<Enchantment, Integer> enchants) {
        this.enchants = enchants;
    }

    public ItemStack toItemMarket(int price, int quantity) {

        List<String> loreCombined = new ArrayList<>();
        if (loreSerializer != null) {
            this.loreSerializer.getCombinedLore().forEach(line -> loreCombined.add(
                    ChatColor.translateAlternateColorCodes('&',
                            line.replace("%quantity%", String.valueOf(quantity))
                                    .replace("%price%", String.valueOf(price)))));
        }
        return new ItemBuilder(Material.getMaterial(this.type))
                .setName(this.name)
                .setLore(loreCombined)
                .addEnchantments(enchants)
                .toItemStack();
    }

    public List<String> lore(String id, int price, int quantity) {
        List<String> loreCombined = new ArrayList<>();
        if (loreSerializer != null) {
            this.loreSerializer.getCombinedLore().forEach(line -> loreCombined.add(
                    ChatColor.translateAlternateColorCodes('&',
                            line.replace("%id%", id).replace("%quantity%", String.valueOf(quantity))
                                    .replace("%price%", String.valueOf(price)))));
        }
        return loreCombined;
    }

    public ItemStack toItemStack() {

        return new ItemBuilder(Material.getMaterial(this.type))
                .setName(this.name)
                .setLore(this.loreSerializer.getDefaultLore())
                .addEnchantments(enchants)
                .toItemStack();
    }

}
