package fr.elowyr.basics.points;

import com.avaje.ebean.validation.NotNull;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.points.commands.*;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class PointsManager extends Module {
    private static PointsManager instance;

    private File file;
    private YamlConfiguration config;

    public static PrintStream logs;

    public PointsManager() {
        super("Points");
        instance = this;
        this.file = new File(getPlugin().getDataFolder(), "points.yml");
        load();
        try {
            final File logFile = new File(this.getPlugin().getDataFolder(), "points-logs.txt");
            final OutputStream out = Files.newOutputStream(logFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
            PointsManager.logs = new PrintStream(new BufferedOutputStream(out, 16384), true);
        }
        catch (Throwable ex) {
            PointsManager.logs = System.out;
            throw new RuntimeException(ex);
        }
        registerPlaceHolder();
        registerCommand(new PointsCommand());
        registerCommand(new PointsGiveCommand());
        registerCommand(new PointsPayCommand());
        registerCommand(new PointsLookCommand());
        registerCommand(new PointsTakeCommand());
        registerCommand(new PointsRemoveCommand());
    }

    public void load() {
        if (!this.file.exists()) {
            this.getPlugin().saveResource("points.yml", false);
        }
        try {
            this.config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8)));
            this.getPlugin().getLogger().info("Points loaded");
        } catch (Throwable ex) {
            this.getPlugin().getLogger().severe("Failed to load Points");
        }
    }

    public void registerPlaceHolder() {
        new PlaceholderExpansion() {

            @Override
            public @com.avaje.ebean.validation.NotNull
            String getIdentifier() {
                return "points";
            }

            @Override
            public @com.avaje.ebean.validation.NotNull
            String getAuthor() {
                return "AnZok";
            }

            @Override
            public @NotNull
            String getVersion() {
                return "1.0.0";
            }

            public String onPlaceholderRequest(Player player, String params) {
                if (player == null)
                    return null;
                ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
                if (params.equalsIgnoreCase("points")) {
                    if (profileData == null) {
                        return "0";
                    }
                    return "" + profileData.getPoints();
                }
                if (params.equalsIgnoreCase("spents")) {
                    if (profileData == null) {
                        return "0";
                    }
                    return ""  + profileData.getSpents();
                }
                return null;
            }
        }.register();
    }

    public static String formatAmount(double amount) {
        if (amount >= 1_000_000) {
            return String.format("%.1fM", amount / 1_000_000);
        } else if (amount >= 1_000) {
            return String.format("%.1fK", amount / 1_000);
        } else {
            return String.format("%.2f", amount);
        }
    }

    public static PointsManager getInstance() {
        return instance;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public static PrintStream getLogs() {
        return logs;
    }
}
