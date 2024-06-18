package fr.elowyr.basics.joyaux;

import com.avaje.ebean.validation.NotNull;
import fr.elowyr.basics.joyaux.commands.JoyauxCommand;
import fr.elowyr.basics.joyaux.commands.JoyauxGiveCommand;
import fr.elowyr.basics.joyaux.commands.JoyauxLookCommand;
import fr.elowyr.basics.joyaux.commands.JoyauxTakeCommand;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class JoyauxManager extends Module {
    private static JoyauxManager instance;

    private File file;
    private YamlConfiguration config;

    public JoyauxManager() {
        super("Joyaux");
        instance = this;
        this.file = new File(this.getPlugin().getDataFolder(), "joyaux.yml");
        load();
        registerPlaceHolder();
        registerCommand(new JoyauxCommand());
        registerCommand(new JoyauxGiveCommand());
        registerCommand(new JoyauxLookCommand());
        registerCommand(new JoyauxTakeCommand());

        registerListener(new JoyauxListener());
    }

    public void load() {
        if (!this.file.exists()) {
            this.getPlugin().saveResource("joyaux.yml", false);
        }
        try {
            this.config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8)));
            this.getPlugin().getLogger().info("Joyaux loaded");
        } catch (Throwable ex) {
            this.getPlugin().getLogger().severe("Failed to load Joyaux");
        }
    }

    public void registerPlaceHolder() {
        new PlaceholderExpansion() {

            @Override
            public @NotNull
            String getIdentifier() {
                return "joyaux";
            }

            @Override
            public @NotNull
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
                if (profileData == null) {
                    return "0";
                }
                return "" + profileData.getJoyaux();
            }
        }.register();
    }

    public static JoyauxManager getInstance() {
        return instance;
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
