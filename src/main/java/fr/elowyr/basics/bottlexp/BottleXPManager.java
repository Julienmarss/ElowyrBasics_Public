package fr.elowyr.basics.bottlexp;

import fr.elowyr.basics.bottlexp.commands.BottleXpCommand;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class BottleXPManager extends Module {
    private static BottleXPManager instance;

    private final File file;
    private YamlConfiguration config;

    public String BOTTLE_LORE;
    public String BOTTLE_NAME;

    public BottleXPManager() {
        super("BottleXP");
        instance = this;
        this.file = new File(this.getPlugin().getDataFolder(), "bottlexp.yml");
        load();
        BOTTLE_LORE = Utils.color(this.getConfig().getString("BOTTLE_LORE"));
        BOTTLE_NAME = Utils.color(this.getConfig().getString("BOTTLE_NAME"));

        registerCommand(new BottleXpCommand());

        registerListener(new BottleXPListener());

        System.out.println("[Module] BottleXP Loaded");
    }

    public void load() {
        if (!this.file.exists()) {
            this.getPlugin().saveResource("bottlexp.yml", false);
        }
        try {
            this.config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8)));
            this.getPlugin().getLogger().info("BottleXP loaded");
        } catch (Throwable ex) {
            this.getPlugin().getLogger().severe("Failed to load BottleXP");
        }
    }

    public static BottleXPManager getInstance() {
        return instance;
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
