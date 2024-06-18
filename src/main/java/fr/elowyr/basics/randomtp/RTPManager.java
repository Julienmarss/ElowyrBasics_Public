package fr.elowyr.basics.randomtp;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.randomtp.commands.RTPCommand;
import fr.elowyr.basics.utils.*;
import fr.elowyr.basics.utils.menu.ClickAction;
import fr.elowyr.basics.utils.menu.items.VirtualItem;
import fr.elowyr.basics.utils.menu.menus.Size;
import fr.elowyr.basics.utils.menu.menus.VirtualGUI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Random;

public class RTPManager extends Module {
    private static RTPManager instance;

    private final File file;
    private YamlConfiguration config;

    public RTPManager() {
        super("RandomTP");
        instance = this;
        this.file = new File(this.getPlugin().getDataFolder(), "randomtp.yml");
        load();
        registerCommand(new RTPCommand());

        registerListener(new RTPListener());

        System.out.println("[Module] RandomTP Loaded");
    }

    public void load() {
        if (!this.file.exists()) {
            this.getPlugin().saveResource("randomtp.yml", false);
        }
        try {
            this.config = YamlConfiguration.loadConfiguration(new BufferedReader(new InputStreamReader(Files.newInputStream(this.file.toPath()), StandardCharsets.UTF_8)));
            this.getPlugin().getLogger().info("RandomTP loaded");
        } catch (Throwable ex) {
            this.getPlugin().getLogger().severe("Failed to load RandomTP");
        }
    }

    public void openGUI(Player player) {
        VirtualGUI gui = new VirtualGUI(this.getConfig().getString("GUI-NAME"), Size.UNE_LIGNE);

        gui.setItem(0, new VirtualItem(new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 1)));

        if (!CooldownUtils.isOnCooldown("RTP", player)) {
            gui.setItem(4, new VirtualItem(new ItemBuilder(CustomHeads.create(CustomHeads.ONE_OAK)).setName("&e&l⦙ &6&lTéléportation")
                    .setLore(
                            "",
                            "&7▪ &fTéléportez-vous dans le",
                            "&f monde faction.",
                            "",
                            "&aCliquez pour être téléporté. &2✔"
                    ))
                    .onItemClick(new ClickAction() {
                @Override
                public void onClick(InventoryClickEvent event) {
                    event.getWhoClicked().teleport(getRandomLocation("world", 1000, 7000));
                    CooldownUtils.addCooldown("FALL", player, 5);
                    CooldownUtils.addCooldown("RTP", player, 1800);
                }
            }));
        } else {
            String timing = DurationFormatter.getRemaining(CooldownUtils.getCooldownForPlayerLong("RTP", player), false);
            gui.setItem(4, new VirtualItem(new ItemBuilder(CustomHeads.create(CustomHeads.ONE_OAK)).setName("&e&l⦙ &6&lTéléportation")
                    .setLore(
                            "",
                            "&7▪ &fTéléportez-vous dans le",
                            "&f monde faction.",
                            "",
                            "&7◆ &fCooldown: &d" + timing,
                            ""

                    )).onItemClick(new ClickAction() {
                @Override
                public void onClick(InventoryClickEvent event) {
                    event.getWhoClicked().sendMessage(Utils.color(getConfig().getString("MESSAGES.COOLDOWN").replace("%time%", String.valueOf(timing))));
                }
            }));
        }

        gui.setItem(8, new VirtualItem(new ItemBuilder(CustomHeads.create(CustomHeads.HOME)).setName("&c&l⦙ &4&lFermer")).onItemClick(new ClickAction() {
            @Override
            public void onClick(InventoryClickEvent event) {
                event.getWhoClicked().closeInventory();
            }
        }));
        gui.open(player);
    }

    public static Location getRandomLocation(String world, int min, int max) {

        Random rand = new Random();

        int x = rand.nextInt((max - min) + 1) + min;
        int z = rand.nextInt((max - min) + 1) + min;

        int y = Bukkit.getWorld(world).getHighestBlockYAt(x, z) + 5;

        Location location = new Location(Bukkit.getWorld(world), x, y, z);
        int radius = 10;

        FLocation fLocation = new FLocation(location);
        Faction faction = Board.getInstance().getFactionAt(fLocation);

        if (faction.isWarZone()) return getRandomLocation(world, min, max);

        for (int xLoc = -radius; xLoc <= radius; ++xLoc) {
            for (int yLoc = -radius; yLoc <= radius; ++yLoc) {
                for (int zLoc = -radius; zLoc <= radius; ++zLoc) {
                    Block radiusBlock = location.clone().add(xLoc, yLoc, zLoc).getBlock();

                    if (radiusBlock.getType() == Material.STATIONARY_WATER) {
                        return getRandomLocation(world, min, max);
                    }
                }
            }
        }

        if (!Utils.isDefaultFaction(faction)
                || Bukkit.getWorld(world).getBiome(x, z).equals(Biome.OCEAN)
                || Bukkit.getWorld(world).getBiome(z, z).equals(Biome.DEEP_OCEAN)) {
            return getRandomLocation(world, min, max);
        }

        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public static RTPManager getInstance() {
        return instance;
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
