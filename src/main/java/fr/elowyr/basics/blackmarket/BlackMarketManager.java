package fr.elowyr.basics.blackmarket;


import fr.elowyr.basics.blackmarket.commands.*;
import fr.elowyr.basics.blackmarket.data.provider.BlackMarketProvider;
import fr.elowyr.basics.blackmarket.data.utils.IOUtil;
import fr.elowyr.basics.module.Module;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class BlackMarketManager extends Module {
    private static BlackMarketManager instance;

    private IOUtil ioUtil;
    private BlackMarketProvider provider;
    private final File file;
    private YamlConfiguration config;

    public BlackMarketManager() {
        super("BlackMarket");
        instance = this;
        this.file = new File(this.getPlugin().getDataFolder(), "blackmarket.yml");
        load();
        registerProvider();

        registerCommand(new BlackMarketCommand());
        registerCommand(new BlackMarketAddCommand());
        registerCommand(new BlackMarketRemoveCommand());
        registerCommand(new BlackMarketaddQuantityCommand());
        registerCommand(new BlackMarketRemoveQuantityCommand());

        System.out.println("[Module] BlackMarket Loaded");
    }

    public void load() {
        if (!this.file.exists()) {
            this.getPlugin().saveResource("blackmarket.yml", false);
        }
        try {
            this.config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8)));
            this.getPlugin().getLogger().info("BlackMarket loaded");
        } catch (Throwable ex) {
            this.getPlugin().getLogger().severe("Failed to load BlackMarket");
        }
    }

    private void registerProvider() {
        this.ioUtil = new IOUtil();
        this.provider = new BlackMarketProvider(this);
        this.provider.read();
    }

    public static BlackMarketManager getInstance() {
        return instance;
    }

    public BlackMarketProvider getProvider() {
        return provider;
    }

    public IOUtil getIoUtil() {
        return ioUtil;
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
