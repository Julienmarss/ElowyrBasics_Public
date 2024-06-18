package fr.elowyr.basics.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class CooldownUtils {
    private static HashMap<String, HashMap<UUID, Long>> cooldown = new HashMap<>();

    public static void clearCooldowns() {
        cooldown.clear();
    }

    public static void createCooldown(String k) {
        if (cooldown.containsKey(k))
            throw new IllegalArgumentException("Ce cooldown existe deja");
        cooldown.put(k, new HashMap<>());
    }

    public static HashMap<UUID, Long> getCooldownMap(String k) {
        if (cooldown.containsKey(k))
            return cooldown.get(k);
        return null;
    }

    public static void addCooldown(String k, Player p, int seconds) {
        if (!cooldown.containsKey(k))
            createCooldown(k);
        long next = System.currentTimeMillis() + seconds * 1000L;
        (cooldown.get(k)).put(p.getUniqueId(), next);
    }

    public static boolean isOnCooldown(String k, Player p) {
        return (cooldown.containsKey(k) && (cooldown.get(k)).containsKey(p.getUniqueId()) &&
                System.currentTimeMillis() <= (cooldown.get(k)).get(p.getUniqueId()));
    }

    public static int getCooldownForPlayerInt(String k, Player p) {
        return (int)(((cooldown.get(k)).get(p.getUniqueId()) - System.currentTimeMillis()) / 1000L);
    }

    public static long getCooldownForPlayerLong(String k, Player p) {
        return (cooldown.get(k)).get(p.getUniqueId()) - System.currentTimeMillis();
    }

    public static void removeCooldown(String k, Player p) {
        if (!cooldown.containsKey(k))
            throw new IllegalArgumentException(String.valueOf(k) + " n'existe pas");
        ((HashMap)cooldown.get(k)).remove(p.getUniqueId());
    }
}
