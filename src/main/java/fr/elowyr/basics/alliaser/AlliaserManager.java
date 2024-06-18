package fr.elowyr.basics.alliaser;

import fr.elowyr.basics.module.Module;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class AlliaserManager extends Module {
    private static AlliaserManager instance;

    private final File file;
    private YamlConfiguration config;

    public AlliaserManager() {
        super("Alliaser");
        instance = this;
        this.file = new File(this.getPlugin().getDataFolder(), "alliaser.yml");
        load();

        registerListener(new AlliaserListener(getAlliasers()));

        System.out.println("[Module] Alliaser Loaded");
    }

    public void load() {
        if (!this.file.exists()) {
            this.getPlugin().saveResource("alliaser.yml", false);
        }
        try {
            this.config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8)));
            this.getPlugin().getLogger().info("Alliaser loaded");
        } catch (Throwable ex) {
            this.getPlugin().getLogger().severe("Failed to load Alliaser");
        }
    }

    private List<Alliaser> getAlliasers() {
        return this.config.getStringList("aliases").stream().map(s -> {
            String[] strings = s.split("=>");
            return new Alliaser(strings[0], strings[1]);
        }).collect(Collectors.toList());
    }

    public static AlliaserManager getInstance() {
        return instance;
    }
}
