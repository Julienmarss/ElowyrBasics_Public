package fr.elowyr.basics.utils;

import com.massivecraft.factions.Faction;
import fr.elowyr.basics.ElowyrBasics;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.io.File;
import java.util.*;

public class Utils {

    static final int SECOND = 1000;
    static final int MINUTE = SECOND * 60;
    static final int HOUR = MINUTE * 60;
    static final int DAY = HOUR * 24;
    static final int WEEK = DAY * 7;

    public static String color(final String value) {
        if (value == null) {
            return null;
        }
        return ChatColor.translateAlternateColorCodes('&', value);
    }

    public static void decrementItem(Player player, ItemStack item, int quantity) {
        int toRemove = quantity;
        PlayerInventory playerInventory = player.getInventory();
        for (ItemStack is : playerInventory.getContents()) {
            if (toRemove <= 0)
                break;
            if (is != null && is.getType() == item.getType() && is.getData().getData() == item.getData().getData()) {
                int amount = is.getAmount() - toRemove;
                toRemove -= is.getAmount();
                if (amount <= 0) {
                    playerInventory.removeItem(is);
                } else {
                    is.setAmount(amount);
                }
            }
        }
    }

    public static File getFormatedFile(ElowyrBasics plugin, String fileName) {
        return new File(plugin.getDataFolder(), fileName);
    }

    public static long getPotions(Player player) {
        if (player == null || !player.isOnline()) {
            return 0;
        }
        return Arrays.stream(player.getInventory().getContents())
                .filter(Objects::nonNull)
                .filter(item -> item.getType() == Material.POTION)
                .filter(item -> item.getDurability() == 16421).count();
    }

    public static List<String> colorAll(final List<String> value) {
        final ListIterator<String> iterator = value.listIterator();
        while (iterator.hasNext()) {
            iterator.set(color(iterator.next()));
        }
        return value;
    }

    public static int randomInt(int min, int max) {
        Random random = new Random();
        int range = max - min + 1;
        int randomNum = random.nextInt(range) + min;
        return randomNum;
    }

    public static int getRequiredMeta(Block block) {
        Material type = block.getType();
        //Dans le cas d'un bloc de melon ou une citrouille il n'y à pas de meta
        return type == Material.STRING || type == Material.COCOA ? 8 :
                type == Material.MELON || type == Material.PUMPKIN ? 0 :
                        type == Material.NETHER_WARTS ? 3 : 7;
    }

    public static String[] replaceAll(final String[] lines, final Object[] replacements) {
        for (int i = 0; i < lines.length; ++i) {
            lines[i] = replaceAll(lines[i], replacements);
        }
        return lines;
    }

    public static String replaceAll(String line, final Object[] replacements) {
        for (int i = 0; i < replacements.length; i += 2) {
            line = line.replace("%" + replacements[i] + "%", String.valueOf(replacements[i + 1]));
        }
        return line;
    }

    public static boolean isDefaultFaction(Faction faction) {
        return faction.isNone() || faction.isPeaceful() || faction.isSafeZone() || faction.isWarZone() || !faction.isNormal();
    }

    public static String remaining(long remaining) {
        int days    = (int)((remaining % WEEK) / DAY);
        int hours   = (int)((remaining % DAY) / HOUR) + days * 24;
        int minutes = (int)((remaining % HOUR) / MINUTE);
        int seconds = (int)((remaining % MINUTE) / SECOND);

        return String.format("%d heure(s) %d minute(s) et %d seconde(s)", hours, minutes, seconds);
    }

}
