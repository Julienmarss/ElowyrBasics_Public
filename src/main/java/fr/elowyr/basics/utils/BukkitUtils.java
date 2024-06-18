package fr.elowyr.basics.utils;

import fr.elowyr.basics.ElowyrBasics;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;

public class BukkitUtils
{
    
    public static int getPing(final Player player) {
        return ((CraftPlayer)player).getHandle().ping;
    }
    
    public static Material getType(final String data) {
        try {
            return Material.getMaterial(Integer.parseInt(data));
        }
        catch (Throwable ignored) {
            try {
                return Material.valueOf(data.toUpperCase());
            }
            catch (Throwable ignored2) {
                return Material.getMaterial(data);
            }
        }
    }
    
    public static ItemStack parseItemStack(final ConfigurationSection section) {
        if (section == null) {
            return null;
        }
        final Material type = getType(section.getString("type"));
        final int damage = section.getInt("damage", 0);
        if (type == null) {
            ElowyrBasics.severe("Material " + section.getString("type") + " not found (" + section + ")");
            return new ItemStack(Material.AIR);
        }
        final ItemMaker builder = ItemMaker.newBuilder(type, damage);
        if (section.isString("display-name")) {
            builder.displayName(Utils.color(section.getString("display-name")));
        }
        if (section.isList("lore")) {
            builder.lore(Utils.colorAll(section.getStringList("lore")));
        }
        if (section.isConfigurationSection("enchants")) {
            final ConfigurationSection enchSection = section.getConfigurationSection("enchants");
            for (final String name : enchSection.getKeys(false)) {
                final Enchantment ench = Enchantment.getByName(name);
                final int level = enchSection.getInt(name);
                builder.addEnchant(ench, level);
            }
        }
        if (section.isList("flags")) {
            builder.addFlags(parseFlags(section.getStringList("flags")));
        }
        if (section.isString("color")) {
            try {
                builder.setColor(DyeColor.valueOf(section.getString("color")).getColor());
            }
            catch (Throwable ignored) {}
        }
        else if (section.isConfigurationSection("color")) {
            final int red = section.getInt("color.red");
            final int green = section.getInt("color.green");
            final int blue = section.getInt("color.blue");
            builder.setColor(Color.fromRGB(red, green, blue));
        }
        if (section.isString("owner")) {
            builder.owner(section.getString("owner"));
        }
        return builder.build();
    }
    
    private static ItemFlag[] parseFlags(final List<String> flags) {
        final ItemFlag[] itemFlags = new ItemFlag[flags.size()];
        final Iterator<String> flagIterator = flags.iterator();
        int i = 0;
        while (flagIterator.hasNext()) {
            itemFlags[i] = ItemFlag.valueOf(flagIterator.next().toUpperCase());
            ++i;
        }
        return itemFlags;
    }
    
    public static void sendActionBar(final Player player, final String message) {
        sendPacket(player, new PacketPlayOutChat(fromText(message), (byte)2));
    }
    
    public static void broadcastActionBar(final String message) {
        final PacketPlayOutChat packet = new PacketPlayOutChat(fromText(message), (byte)2);
        for (final Player player : Bukkit.getOnlinePlayers()) {
            sendPacket(player, packet);
        }
    }
    
    public static void sendTitle(final Player player, final String title, final String subTitle, final int fadeIn, final int stay, final int fadeOut) {
        if (player == null || !player.isOnline() || !(player instanceof CraftPlayer)) {
            return;
        }
        final PlayerConnection con = ((CraftPlayer)player).getHandle().playerConnection;
        con.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, (IChatBaseComponent)null, fadeIn, stay, fadeOut));
        if (subTitle != null) {
            con.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, fromText(subTitle)));
        }
        if (title != null) {
            con.sendPacket(new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, fromText(title)));
        }
    }
    
    public static void broadcastTitle(final String title, final String subTitle, final int fadeIn, final int stay, final int fadeOut) {
        final PacketPlayOutTitle times = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, (IChatBaseComponent)null, fadeIn, stay, fadeOut);
        final PacketPlayOutTitle subTitlePacket = (subTitle != null) ? new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, fromText(subTitle)) : null;
        final PacketPlayOutTitle titlePacket = (title != null) ? new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, fromText(title)) : null;
        for (final Player player : Bukkit.getOnlinePlayers()) {
            final PlayerConnection con = ((CraftPlayer)player).getHandle().playerConnection;
            con.sendPacket(times);
            if (subTitlePacket != null) {
                con.sendPacket(subTitlePacket);
            }
            if (titlePacket != null) {
                con.sendPacket(titlePacket);
            }
        }
    }
    
    public static void sendPacket(final Player player, final Packet<?> packet) {
        if (player == null || !player.isOnline() || !(player instanceof CraftPlayer)) {
            return;
        }
        final PlayerConnection con = ((CraftPlayer)player).getHandle().playerConnection;
        if (con != null) {
            con.sendPacket(packet);
        }
    }
    
    private static IChatBaseComponent fromText(final String text) {
        return IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + text + "\"}");
    }
}
