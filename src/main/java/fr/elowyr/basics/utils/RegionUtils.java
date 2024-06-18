package fr.elowyr.basics.utils;

import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.stream.Collectors;

public class RegionUtils {

    public static boolean inArea(Location location, World world, String region) {
        if (!location.getWorld().getName().equals(world.getName())) {
            return false; // Avoid unnecessary checks if the worlds are different
        }
        RegionContainer regionContainer = WorldGuardPlugin.inst().getRegionContainer();
        if (regionContainer != null) {
            RegionManager regionManager = regionContainer.get(world);
            if (regionManager != null) {
                ProtectedRegion protectedRegion = regionManager.getRegion(region);
                return protectedRegion != null && protectedRegion.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());
            }
        }
        return false;
    }

    public static boolean inAreas(Location location, World world, String... regions) {
        if (!location.getWorld().getName().equals(world.getName())) {
            return false; // Avoid unnecessary checks if the worlds are different
        }
        RegionContainer regionContainer = WorldGuardPlugin.inst().getRegionContainer();
        if (regionContainer != null) {
            RegionManager regionManager = regionContainer.get(world);
            if (regionManager != null) {
                for (String region : regions) {
                    ProtectedRegion protectedRegion = regionManager.getRegion(region);
                    if (protectedRegion != null && protectedRegion.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static boolean isInAP(Location location, World world) {
        if (!location.getWorld().getName().equals(world.getName())) {
            return false; // Avoid unnecessary checks if the worlds are different
        }
        RegionContainer regionContainer = WorldGuardPlugin.inst().getRegionContainer();
        if (regionContainer != null) {
            RegionManager regionManager = regionContainer.get(world);
            if (regionManager != null) {
                for (Map.Entry<String, ProtectedRegion> region : regionManager.getRegions()
                        .entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().startsWith("ap"))
                        .collect(Collectors.toList())) {
                    if (region.getValue().contains(location.getBlockX(), location.getBlockY(), location.getBlockZ())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static int getPlayerCountInRegion(String regionName) {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("WorldGuard");

        if (plugin instanceof WorldGuardPlugin) {
            WorldGuardPlugin worldGuard = (WorldGuardPlugin) plugin;
            ProtectedRegion region = worldGuard.getRegionManager(Bukkit.getWorld("world")).getRegion(regionName);

            if (region != null) {
                int count = 0;

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (region.contains(p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ())) {
                        count++;
                    }
                }

                return count;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
}