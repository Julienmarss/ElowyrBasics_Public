package fr.elowyr.basics.expansions;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.utils.CooldownUtils;
import fr.elowyr.basics.utils.DurationFormatter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.minelink.ctplus.CombatTagPlus;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;

public class CombatExpansion extends PlaceholderExpansion {

    private final CombatTagPlus combatTagPlus = (CombatTagPlus) ElowyrBasics.getInstance().getServer().getPluginManager().getPlugin("CombatTagPlus");

    @Override
    public String getIdentifier() {
        return "elowyrcombat";
    }

    @Override
    public String getAuthor() {
        return "AnZok";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if (identifier.equalsIgnoreCase("enable")) {
            if (combatTagPlus.getTagManager().isTagged(player.getUniqueId())) {
                return "true";
            } else {
                return "false";
            }
        }
        if (identifier.equalsIgnoreCase("time")) {
            if (combatTagPlus.getTagManager().isTagged(player.getUniqueId())) {
                return "" + combatTagPlus.getTagManager().getTag(player.getUniqueId()).getTagDuration();
            }
        }
        if (identifier.equalsIgnoreCase("potions")) {
            return "" + getPotions(player);
        }
        if (identifier.equalsIgnoreCase("enderpearl")) {
            if (CooldownUtils.isOnCooldown("enderpearl_nature", player)) {
                return "" + DurationFormatter.getRemaining(CooldownUtils.getCooldownForPlayerLong("enderpearl_nature", player), false);
            } else if (CooldownUtils.isOnCooldown("enderpearl_warzone", player)) {
                return "" + DurationFormatter.getRemaining(CooldownUtils.getCooldownForPlayerLong("enderpearl_warzone", player), false);
            } else {
                return "§a✔";
            }
        }
        return null;
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
}
