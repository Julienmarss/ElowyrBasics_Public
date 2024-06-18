package fr.elowyr.basics.expansions;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChunloExpansion extends PlaceholderExpansion {


    @Override
    public @NotNull String getIdentifier() {
        return "elowyrchunk";
    }

    @Override
    public @NotNull String getAuthor() {
        return "AnZok";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        for (World world : Bukkit.getWorlds()) {
            return String.valueOf(world.getLoadedChunks().length);
        }
        return null;
    }
}
