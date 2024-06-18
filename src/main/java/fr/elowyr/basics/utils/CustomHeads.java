package fr.elowyr.basics.utils;

import net.minecraft.server.v1_8_R3.MojangsonParser;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.UUID;

public class CustomHeads {

    private static final boolean newStorageSystem;
    public static String HOME = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJkN2E3NTFlYjA3MWUwOGRiYmM5NWJjNWQ5ZDY2ZTVmNTFkYzY3MTI2NDBhZDJkZmEwM2RlZmJiNjhhN2YzYSJ9fX0=";
    public static String ONE_OAK = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTU1ZGM3NzA0ZTZkYjA0ZGU2NjUxOTg1ZDY0YWY1MDQ4YWVmMzgzN2NhOGQ3OTg0ZjhhZjY4ZjA1OGNhZTUifX19";
    static {
        String versionString = Bukkit.getBukkitVersion();
        int[] version = Arrays.stream(versionString.substring(0, versionString.indexOf('-')).split("\\."))
                .mapToInt(Integer::parseInt)
                .toArray();
        newStorageSystem = version[0] > 1
                || (version[0] == 1 && version[1] > 16)
                || (version[0] == 1 && version[1] == 16 && version[2] >= 1);
    }

    public static ItemStack create(String texture) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        try {
            String nbtString = String.format(
                    "{SkullOwner:{Id:%s,Properties:{textures:[{Value:\"%s\"}]}}}",
                    serializeUuid(UUID.randomUUID()), texture
            );
            NBTTagCompound nbt = MojangsonParser.parse(nbtString);
            nmsItem.setTag(nbt);
        } catch (
                Exception e) {
            throw new AssertionError("NBT Tag parsing failed - This should never happen.", e);
        }
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    private static String serializeUuid(UUID uuid) {
        if (newStorageSystem) {
            StringBuilder result = new StringBuilder();
            long msb = uuid.getMostSignificantBits();
            long lsb = uuid.getLeastSignificantBits();
            return result.append("[I;")
                    .append(msb >> 32)
                    .append(',')
                    .append(msb & Integer.MAX_VALUE)
                    .append(',')
                    .append(lsb >> 32)
                    .append(',')
                    .append(lsb & Integer.MAX_VALUE)
                    .append(']')
                    .toString();
        } else {
            return '"' + uuid.toString() + '"';
        }
    }
}