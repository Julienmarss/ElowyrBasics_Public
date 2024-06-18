package fr.elowyr.basics.titles;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.titles.commands.*;
import fr.elowyr.basics.titles.data.Title;
import fr.elowyr.basics.titles.data.provider.TitleProvider;
import fr.elowyr.basics.titles.data.utils.IOUtil;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class TitleManager extends Module {
    private static TitleManager instance;

    private File file;
    private YamlConfiguration config;

    private IOUtil ioUtil;
    private TitleProvider provider;

    public TitleManager() {
        super("Title");
        instance = this;
        this.file = new File(getPlugin().getDataFolder(), "titles.yml");
        load();
        registerProvider();
        registerPlaceHolder();

        registerCommand(new TitleCommand());
        registerCommand(new TitleCreateCommand());
        registerCommand(new TitleDeleteCommand());
        registerCommand(new TitleEditCommand());
        registerCommand(new TitleAddCommand());
        registerCommand(new TitleRemoveCommand());

        System.out.println("[Module] Title Loaded");
    }

    public void load() {
        if (!this.file.exists()) {
            this.getPlugin().saveResource("titles.yml", false);
        }
        try {
            this.config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8)));
            this.getPlugin().getLogger().info("Titles loaded");
        } catch (Throwable ex) {
            this.getPlugin().getLogger().severe("Failed to load Titles");
        }
    }

    private void registerProvider() {
        this.ioUtil = new IOUtil();
        this.provider = new TitleProvider(ElowyrBasics.getInstance());
        this.provider.read();
    }

    public String getTitle(Player player) {
        return ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString()).getTitle();
    }

    public Title getTitleByName(String name) {
        return provider.getTitles().parallelStream().filter(title -> title.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public String getFormatByName(String name) {
        Title title = getTitleByName(name);
        if (title != null) {
            return title.getTitle();
        }
        return "";
    }

    public void registerPlaceHolder() {
        new PlaceholderExpansion() {

            @Override
            public @NotNull
            String getIdentifier() {
                return "title";
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
            public @Nullable
            String onPlaceholderRequest(Player player, @NotNull String params) {
                ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
                if (profileData == null) return "";
                if (profileData.getTitle() == null) return "";
                return profileData.getTitle();
            }

        }.register();
    }

    public static TitleManager getInstance() {
        return instance;
    }

    public IOUtil getIoUtil() {
        return ioUtil;
    }

    public TitleProvider getProvider() {
        return provider;
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
