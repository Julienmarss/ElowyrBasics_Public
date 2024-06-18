package fr.elowyr.basics.missions;

import com.google.common.collect.Lists;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.cmd.FCmdRoot;
import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.missions.commands.MissionCommand;
import fr.elowyr.basics.missions.commands.MissionResetCommand;
import fr.elowyr.basics.missions.data.Mission;
import fr.elowyr.basics.missions.data.MissionType;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.utils.BukkitUtils;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class MissionManager extends Module {
    private static MissionManager instance;

    private final List<Mission> missions;

    private final File file;
    private YamlConfiguration config;

    public MissionManager() {
        super("Missions");
        instance = this;
        this.file = new File(this.getPlugin().getDataFolder(), "missions.yml");
        load();
        this.missions = Lists.newArrayList();
        FCmdRoot root = FCmdRoot.getInstance();
        root.addSubCommand(new MissionCommand());
        registerMissions();

        registerCommand(new MissionResetCommand());

        registerListener(new MissionListener());

        System.out.println("[Module] Missions Loaded");
    }

    public void load() {
        if (!this.file.exists()) {
            this.getPlugin().saveResource("missions.yml", false);
        }
        try {
            this.config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8)));
            this.getPlugin().getLogger().info("Missions loaded");
        } catch (Throwable ex) {
            this.getPlugin().getLogger().severe("Failed to load Missions");
        }
    }

    private void registerMissions() {
        ConfigurationSection section = this.getConfig().getConfigurationSection("missions");

        for (final String key : section.getKeys(false)) {
            int id = section.getInt(key + ".id");
            String description = section.getString(key + ".description");
            MissionType type = MissionType.valueOf(section.getString(key + ".missionType"));
            Material material = null;
            EntityType entityType = null;
            switch (type) {
                case BREAK_BLOCK:
                    material = Material.valueOf(section.getString(key + ".material"));
                    break;
                case KILL:
                    entityType = EntityType.valueOf(section.getString(key + ".entityType"));
                    break;
            }
            int objective = section.getInt(key + ".objective");
            int rewards = section.getInt(key + ".rewards");
            this.missions.add(new Mission(id, description, type, material, entityType, 0, objective, rewards, false));
        }
    }

    public Mission getMissionById(int id) {
        return this.getMissions().stream().filter(mission -> mission.getId() == id).findFirst().orElse(null);
    }

    public void updateMission(MissionType missionType, Player player, Material material, int i, boolean message) {
        this.updateMission(missionType, player, material, null, i, message);
    }

    public void updateMission(MissionType missionType, Player player, Material material, boolean message) {
        this.updateMission(missionType, player, material, null, 0, message);
    }

    public void updateMission(MissionType missionType, Player player, EntityType entityType, int i, boolean message) {
        this.updateMission(missionType, player, null, entityType, i, message);
    }

    public void updateMission(MissionType missionType, Player player, Material material, EntityType entityType, int i, boolean message) {
        FactionData factionData = FactionManager.getInstance().getProvider().get(FPlayers.getInstance().getByPlayer(player).getFactionId());
        if (factionData == null) return;
        factionData.getMissions().forEach(mission -> {
            if (mission.getMissionType().equals(missionType)) {
                if (!mission.isFinish()) {
                    if (mission.getMaterial() != null && entityType == null && mission.getMaterial().equals(material) ||
                            mission.getMaterial() == null && entityType != null && entityType.equals(mission.getEntityType())) {
                        this.upgrade(mission, factionData, i);
                        String name = null;
                        if (mission.getMaterial() == null && entityType != null) {
                            name = entityType.name().toLowerCase();
                        } else if (mission.getEntityType() == null && material != null) {
                            name = material.name().toLowerCase();
                        }
                        if (message) {
                            BukkitUtils.sendActionBar(player, Utils.color("&2&l✔ &7◆ &fVous avez &arécupéré &e" + i + " " + name + " &fpour votre &6faction&f."));
                        }
                    }
                }
            }
        });
    }

    private void upgrade(Mission mission, FactionData factionData, int i) {
        mission.incrementProgress(i);
        if (mission.getProgress() >= mission.getObjective()) {
            if (!mission.isFinish()) {
                mission.setFinish(true);
                FactionManager.getInstance().getProvider().write(factionData);
                if (mission.getRewards() != 0) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "classification give-faction farm " + factionData.getFactionName() + " " + mission.getRewards());
                }
                Factions.getInstance().getFactionById(factionData.getFactionId()).getOnlinePlayers().forEach(fplayer -> {
                    fplayer.playSound(fplayer.getLocation(), Sound.LEVEL_UP, 10, 20);
                    BukkitUtils.sendTitle(fplayer, "§6Mission de Faction", "§fVous venez de §aterminer§f la §6Mission de Faction #" + mission.getId() + " §f!", 20, 50, 20);
                    fplayer.sendMessage("");
                    fplayer.sendMessage(Utils.color("&fVous avez &aobtenu &6" + mission.getRewards() + " &fpoints de farm !"));
                    fplayer.sendMessage("");
                });
                factionData.getMissionIdFinish().add(mission.getId());
            }
        }
    }

    public static MissionManager getInstance() {
        return instance;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}