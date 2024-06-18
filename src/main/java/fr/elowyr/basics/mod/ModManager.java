package fr.elowyr.basics.mod;

import com.google.common.collect.Lists;
import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.mod.commands.FreezeCommand;
import fr.elowyr.basics.mod.commands.ModCommand;
import fr.elowyr.basics.mod.commands.VanishCommand;
import fr.elowyr.basics.mod.listeners.CancelledListener;
import fr.elowyr.basics.mod.listeners.ModListener;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.tasks.VanishTask;
import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class ModManager extends Module {
    private static ModManager instance;

    private List<UUID> staff = new ArrayList<>();
    private List<UUID> vanishedPlayers = Lists.newArrayList();
    private Set<UUID> frozenPlayers = new HashSet<>();

    private File file;
    private YamlConfiguration staffConfig;

    public ModManager() {
        super("StaffMod");
        instance = this;

        this.file = new File(ElowyrBasics.getInstance().getDataFolder(), "staffmod.yml");
        load();

        registerCommand(new ModCommand());
        registerCommand(new VanishCommand());
        registerCommand(new FreezeCommand());

        registerListener(new ModListener());
        registerListener(new CancelledListener());
    }

    public void load() {
        if (!this.file.exists()) {
            this.getPlugin().saveResource("staffmod.yml", false);
        }
        try {
            this.staffConfig = YamlConfiguration.loadConfiguration(this.file);
            this.getPlugin().getLogger().info("StaffMod loaded");
        } catch (Throwable ex) {
            this.getPlugin().getLogger().severe("Failed to load StaffMod");
        }
    }

    public void setMod(Player player) {
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
        profileData.changeVanish(player, true);
        Bukkit.getScheduler().runTaskTimerAsynchronously(ElowyrBasics.getInstance(), new VanishTask(), 0L, 40L);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.CREATIVE);
        player.getInventory().setItem(0, (new ItemBuilder(Material.SKULL_ITEM).setName("&cStaff en ligne").toItemStack()));
        player.getInventory().setItem(2, (new ItemBuilder(Material.STICK).setName("&cKnockback").toItemStack()));
        player.getInventory().setItem(3, (new ItemBuilder(Material.BOOK).setName("&6Inspection").toItemStack()));
        player.getInventory().setItem(4, (new ItemBuilder(Material.EYE_OF_ENDER).setName("&6Téléportation").toItemStack()));
        player.getInventory().setItem(5, (new ItemBuilder(Material.BLAZE_ROD).setName("&bFreeze").toItemStack()));
        player.getInventory().setItem(6, (new ItemBuilder(Material.INK_SACK).setDurability((short) 10).setName("&aVous êtes invisible").toItemStack()));
        player.getInventory().setItem(8, (new ItemBuilder(Material.REDSTONE).setName("&cQuitter").toItemStack()));
        staff.add(player.getUniqueId());
        player.sendMessage(Utils.color(this.staffConfig.getString("MESSAGES.MOD")));
    }

    public void removeMod(Player player) {
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());
        profileData.changeVanish(player, false);
        player.getInventory().clear();
        player.updateInventory();
        if (player.getGameMode() == GameMode.SURVIVAL) {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
        staff.remove(player.getUniqueId());
        player.sendMessage(Utils.color(this.staffConfig.getString("MESSAGES.UNMOD")));
    }

    public void setFrozen(Player player, Player target) {
        if (isFrozen(target)) {
            this.frozenPlayers.remove(target.getUniqueId());
            target.removePotionEffect(PotionEffectType.BLINDNESS);
            target.removePotionEffect(PotionEffectType.SLOW);
            target.removePotionEffect(PotionEffectType.WEAKNESS);
            target.sendMessage(Utils.color(this.staffConfig.getString("MESSAGES.UNFREEZE").replace("%player%", target.getName())));
            return;
        }
        this.frozenPlayers.add(target.getUniqueId());
        target.sendMessage("");
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l████&4&l█&f&l████"));
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l███&4&l█&6&l█&4&l█&f&l███&r            &cTu viens d'être gelé,"));
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l██&4&l█&6&l█&0&l█&6&l█&4&l█&f&l██      &cRejoins le discord du serveur."));
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l██&4&l█&6&l█&0&l█&6&l█&4&l█&f&l██"));
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l█&4&l█&6&l██&0&l█&6&l██&4&l█&f&l█    &cTu as 2 minutes pour venir,"));
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&l█&4&l█&6&l█████&4&l█&f&l█        &cne te déconnecte pas."));
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l█&6&l███&0&l█&6&l███&4&l█        &7discord.gg/elowyr"));
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&l█████████"));
        target.sendMessage("");
        //player.sendMessage(Utils.color(this.staffConfig.getString("MESSAGES.FREEZE").replace("%player%", target.getName())));
        target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 15814, 0));
        target.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 15814, 0));
        target.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 15814, 0));
    }

    public void randomTeleport(Player player) {
        List<Player> finalPlayers = Bukkit.getOnlinePlayers().stream()
                .filter(players -> !players.equals(player))
                .filter(players -> !players.getWorld().getName().equals("utils"))
                .collect(Collectors.toList());
        if (finalPlayers.isEmpty()) {
            player.sendMessage(Utils.color(this.staffConfig.getString("MESSAGES.EMPTY-PLAYERS")));
            return;
        }

        Player teleportTo = finalPlayers.get(new Random().nextInt(finalPlayers.size()));
        player.teleport(teleportTo);
        player.sendMessage(Utils.color(this.staffConfig.getString("MESSAGES.TELEPORT").replace("%player%", teleportTo.getName())));
    }

    public void msgStaff(String msg) {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.hasPermission("elowyrbasics.mod")) {
                players.sendMessage(Utils.color(msg));
            }
        }
    }

    public boolean isFrozen(Player player) {
        return this.frozenPlayers.contains(player.getUniqueId());
    }

    public boolean isStaff(Player player) {
        return this.staff.contains(player.getUniqueId());
    }

    public static ModManager getInstance() {
        return instance;
    }

    public Set<UUID> getFrozenPlayers() {
        return frozenPlayers;
    }

    public File getFile() {
        return file;
    }

    public YamlConfiguration getStaffConfig() {
        return staffConfig;
    }

    public List<UUID> getVanished() {
        return vanishedPlayers;
    }
}
