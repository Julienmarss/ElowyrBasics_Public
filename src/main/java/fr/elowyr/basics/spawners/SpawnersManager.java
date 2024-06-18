package fr.elowyr.basics.spawners;

import com.google.common.collect.Lists;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.spawners.commands.SpawnerCommand;
import fr.elowyr.basics.spawners.commands.SpawnerCustomCommand;
import fr.elowyr.basics.spawners.commands.SpawnerGiveCommand;
import fr.elowyr.basics.spawners.commands.SpawnerPickaxeCommand;
import fr.elowyr.basics.spawners.listeners.SpawnerListener;
import fr.elowyr.basics.spawners.pickaxe.AbstractPickaxe;
import fr.elowyr.basics.spawners.pickaxe.SpawnersPickaxeOne;
import fr.elowyr.basics.spawners.pickaxe.SpawnersPickaxeThree;
import fr.elowyr.basics.spawners.pickaxe.SpawnersPickaxeTwo;
import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class SpawnersManager extends Module {
    private static SpawnersManager instance;

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static PrintStream logs;

    private final List<AbstractPickaxe> basePickaxe;
    private final List<AbstractPickaxe> pickaxes;

    private final File file;
    private YamlConfiguration config;

    public SpawnersManager() {
        super("Spawner");
        instance = this;
        this.file = new File(this.getPlugin().getDataFolder(), "spawners.yml");
        load();

        try {
            final File logFile = new File(this.getPlugin().getDataFolder(), "spawners-logs.txt");
            final OutputStream out = Files.newOutputStream(logFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
            SpawnersManager.logs = new PrintStream(new BufferedOutputStream(out, 16384), true);
        } catch (Throwable ex) {
            SpawnersManager.logs = System.out;
            throw new RuntimeException(ex);
        }

        this.basePickaxe = Lists.newLinkedList();
        this.pickaxes = Lists.newLinkedList();
        this.registerPickaxes();

        registerCommand(new SpawnerGiveCommand());
        registerCommand(new SpawnerPickaxeCommand());
        registerCommand(new SpawnerCommand());
        registerCommand(new SpawnerCustomCommand());

        registerListener(new SpawnerListener());

        System.out.println("[Module] Spawner Loaded");
    }

    public void load() {
        if (!this.file.exists()) {
            this.getPlugin().saveResource("spawners.yml", false);
        }
        try {
            this.config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8)));
            this.getPlugin().getLogger().info("Spawners loaded");
        } catch (Throwable ex) {
            this.getPlugin().getLogger().severe("Failed to load Spawners");
        }
    }

    public void giveSpawnersToInventory(CommandSender sender, Player player, EntityType entityType, int amount) {
        if (entityType == null) {
            sender.sendMessage(Utils.color(SpawnersManager.getInstance().getConfig().getString("MESSAGES.NO-EXIST")));
        }
        String name = this.config.getString("SPAWNERS.GIVE.NAME").replace("%spawner%", entityType.getName());
        List<String> lore = this.config.getStringList("SPAWNERS.GIVE.LORE");

        ItemStack itemStack = new ItemBuilder(Material.MOB_SPAWNER).setName(name).setLore(lore).toItemStack();
        itemStack.setAmount(amount);
        player.getInventory().addItem(itemStack);
    }

    public void dropItemOnGround(Location location, EntityType entityType) {
        String name = this.config.getString("SPAWNERS.GIVE.NAME").replace("%spawner%", entityType.getName());
        List<String> lore = this.config.getStringList("SPAWNERS.GIVE.LORE");

        ItemStack itemStack = new ItemBuilder(Material.MOB_SPAWNER).setName(name).setLore(lore).toItemStack();
        location.getWorld().dropItemNaturally(location, itemStack);
    }

    public EntityType getByName(String name) {

        for (EntityType entities : EntityType.values()) {

            if (entities.getName().equalsIgnoreCase(name))
                return entities;
        }

        return null;
    }

    private void registerPickaxes() {
        this.registerNormalItems(
                new SpawnersPickaxeOne(),
                new SpawnersPickaxeTwo(),
                new SpawnersPickaxeThree()
        );
        this.pickaxes.addAll(this.basePickaxe);
    }

    private void registerNormalItems(AbstractPickaxe... abstractPickaxes) {
        this.basePickaxe.addAll(Arrays.asList(abstractPickaxes));
    }

    public AbstractPickaxe getPickaxeByID(int id) {
        return this.getPickaxes().stream().filter(abstractPickaxe -> abstractPickaxe.getId() == id).findFirst().orElse(null);
    }

    public static PrintStream getLogs() {
        return logs;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public static SpawnersManager getInstance() {
        return instance;
    }

    public List<AbstractPickaxe> getBasePickaxe() {
        return basePickaxe;
    }

    public List<AbstractPickaxe> getPickaxes() {
        return pickaxes;
    }
}
