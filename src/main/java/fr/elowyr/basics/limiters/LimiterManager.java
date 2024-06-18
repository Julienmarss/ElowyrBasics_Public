package fr.elowyr.basics.limiters;

import com.google.common.collect.Lists;
import fr.elowyr.basics.module.Module;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class LimiterManager extends Module {

	private static LimiterManager instance;
	private List<Limiter> limiters;

	private final File file;
	private YamlConfiguration config;

	public LimiterManager() {
		super("Limiter");
		instance = this;
		this.limiters = Lists.newArrayList();
		this.file = new File(this.getPlugin().getDataFolder(), "limiter.yml");
		load();

		registerListener(new LimiterListener());

		this.registerLimiters();

		System.out.println("[Module] Limiter Loaded");
	}

	public void load() {
		if (!this.file.exists()) {
			this.getPlugin().saveResource("limiter.yml", false);
		}
		try {
			this.config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8)));
			this.getPlugin().getLogger().info("Limiter loaded");
		} catch (Throwable ex) {
			this.getPlugin().getLogger().severe("Failed to load Limiter");
		}
	}

	private void registerLimiters() {
		for (String key : this.config.getKeys(false)) {
			this.addLimiter(Material.getMaterial(config.getString(key + ".material")), config.getInt(key + ".limit_per_chunk"));
		}
	}

	public Limiter getBlockLimiter(Block block) {
		for(Limiter limit : this.limiters) {
			if(limit.getMaterial() == block.getType()) {
				return limit;
			}
		}
		return null;
	}
	
	public void addLimiter(Material material, int limitPerChunk) {
		Limiter limit = new Limiter(material, limitPerChunk);
		this.limiters.add(limit);
	}

	public static LimiterManager getInstance() {
		return instance;
	}
}
