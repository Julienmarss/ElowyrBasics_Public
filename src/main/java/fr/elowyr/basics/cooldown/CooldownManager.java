package fr.elowyr.basics.cooldown;

import fr.elowyr.basics.module.Module;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class CooldownManager extends Module {
    private static CooldownManager instance;

    private final File file;
    private YamlConfiguration config;

    public CooldownManager() {
        super("Cooldown");
        instance = this;
        this.file = new File(this.getPlugin().getDataFolder(), "cooldown.yml");
        load();

        registerListener(new AppleCooldown());
        registerListener(new EnderPearlCooldown());

        System.out.println("[Module] Cooldown Loaded");
    }

    public void load() {
        if (!this.file.exists()) {
            this.getPlugin().saveResource("cooldown.yml", false);
        }
        try {
            this.config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8)));
            this.getPlugin().getLogger().info("Cooldown loaded");
        } catch (Throwable ex) {
            this.getPlugin().getLogger().severe("Failed to load Cooldown");
        }
    }

    public static CooldownManager getInstance() {
        return instance;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public static void setInstance(CooldownManager instance) {
        CooldownManager.instance = instance;
    }

    public File getFile() {
        return file;
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
    }
}
