package fr.elowyr.basics.upgrade;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.upgrade.data.Upgrade;
import fr.elowyr.basics.upgrade.data.UpgradeData;
import fr.elowyr.basics.upgrade.data.UpgradeType;
import fr.elowyr.basics.upgrade.listeners.FactionCommandListener;
import fr.elowyr.basics.upgrade.listeners.advantage.ExpDropListener;
import fr.elowyr.basics.upgrade.listeners.advantage.FallDamageListener;
import fr.elowyr.basics.upgrade.listeners.advantage.HomeListener;
import fr.elowyr.basics.upgrade.listeners.advantage.ReduceDamageListener;
import fr.elowyr.basics.upgrade.listeners.type.*;
import fr.elowyr.basics.utils.*;
import fr.elowyr.basics.utils.menu.ClickAction;
import fr.elowyr.basics.utils.menu.items.VirtualItem;
import fr.elowyr.basics.utils.menu.menus.Size;
import fr.elowyr.basics.utils.menu.menus.VirtualGUI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpgradeManager extends Module {
    private static UpgradeManager instance;

    private final List<UpgradeData> upgradeData;
    private final File file;
    private YamlConfiguration upgradeConfig;

    public UpgradeManager() {
        super("Upgrade");
        instance = this;
        this.file = new File(ElowyrBasics.getInstance().getDataFolder(), "upgrades.yml");
        load();
        this.upgradeData = new ArrayList<>();

        registerListener(new FactionCommandListener());
        registerListener(new BreakUpgradeListener());
        registerListener(new CraftUpgradeListener());
        registerListener(new EnchantUpgradeListener());
        registerListener(new EnderPearlUpgradeListener());
        registerListener(new FishUpgradeListener());
        registerListener(new KillEntityUpgradeListener());
        registerListener(new KillPlayerUpgradeListener());
        registerListener(new PlaceUpgradeListener());
        registerListener(new FallDamageListener());
        registerListener(new ExpDropListener());
        registerListener(new ReduceDamageListener());
        registerListener(new HomeListener());

        setup();
    }

    public void load() {
        if (!this.file.exists()) {
            this.getPlugin().saveResource("upgrades.yml", false);
        }
        try {
            this.upgradeConfig = YamlConfiguration.loadConfiguration(this.file);
            this.getPlugin().getLogger().info("Upgrades loaded");
        } catch (Throwable ex) {
            this.getPlugin().getLogger().severe("Failed to load Upgrades");
        }
    }

    public void setup() {
        for (String key : this.upgradeConfig.getConfigurationSection("F_UPGRADE.UPGRADES").getKeys(false)) {
            String path = "F_UPGRADE.UPGRADES." + key + ".";
            int level = this.upgradeConfig.getInt(path + "LEVEL");
            int slot = this.upgradeConfig.getInt(path + "SLOT");
            int price = this.upgradeConfig.getInt(path + "PRICE");
            List<String> rewards = this.upgradeConfig.getStringList(path + "REWARDS");
            String itemName = this.upgradeConfig.getString(path + "NAME");
            List<String> itemLore = this.upgradeConfig.getStringList(path + "LORE");
            itemLore.replaceAll(line -> line.replace("%price%", String.valueOf(price)));
            List<Upgrade> upgrades = new ArrayList<>();
            String itemLockedType = this.upgradeConfig.getString(path + "ITEM_LOCKED");
            String itemUnlockedType = this.upgradeConfig.getString(path + "ITEM_UNLOCKED");

            for (String upgradeKey : this.upgradeConfig.getConfigurationSection(path + "LIST").getKeys(false)) {
                String upgradePath = "F_UPGRADE.UPGRADES." + key + ".LIST." + upgradeKey + ".";

                String name = this.upgradeConfig.getString(upgradePath + "NAME");
                String displayName = this.upgradeConfig.getString(upgradePath + "DISPLAY_NAME");
                List<String> displayLore = this.upgradeConfig.getStringList(upgradePath + "DISPLAY_LORE");
                int upgradeSlot = this.upgradeConfig.getInt(upgradePath + "SLOT");
                String[] args = this.upgradeConfig.getString(upgradePath + "ICON").split(":");
                ItemData itemData = new ItemData(Material.getMaterial(args[0]), Byte.parseByte(args[1]));
                UpgradeType upgradeType = UpgradeType.valueOf(this.upgradeConfig.getString(upgradePath + "ACTION"));
                String target = this.upgradeConfig.getString(upgradePath + "TARGET");
                int goal = this.upgradeConfig.getInt(upgradePath + "GOAL");


                Upgrade upgrade = new Upgrade(name, displayName, displayLore, upgradeSlot, itemData, target, goal, upgradeType);
                upgrades.add(upgrade);
                UpgradeData upgradeData = new UpgradeData(level, slot, price, rewards, upgrades, itemName, itemLore, itemLockedType, itemUnlockedType);
                this.upgradeData.add(upgradeData);
            }
        }
    }

    public void handleEndUpgrade(FactionData factionData, Player player) {

        UpgradeData upgradeData = this.getUpgradeDataByLevel(factionData.getUpgradeLevel());
        if (upgradeData == null) return;

        if (!VaultUtils.has(player, upgradeData.getPrice())) {
            player.sendMessage(Utils.color(this.upgradeConfig.getString("F_UPGRADE.DONT_MONEY").replace("%price%", formatAmount(upgradeData.getPrice()))));
            return;
        }

        String title = this.upgradeConfig.getString("F_UPGRADE.TITLE_NEXT_LEVEL");
        String titleEnd = this.upgradeConfig.getString("F_UPGRADE.TITLE_NO_NEXT_LEVEL");
        String subTitle = this.upgradeConfig.getString("F_UPGRADE.SUBTITLE_NEXT_LEVEL");
        String subTitleEnd = this.upgradeConfig.getString("F_UPGRADE.SUBTITLE_NO_NEXT_LEVEL");
        UpgradeData nextUpgrade = this.getUpgradeDataByLevel(factionData.getUpgradeLevel() + 1);

        player.closeInventory();

        for (Player players : factionData.toFaction().getOnlinePlayers()) {
            if (nextUpgrade == null) {
                BukkitUtils.sendTitle(players, titleEnd, subTitleEnd, 20, 40, 20);
            } else {
                BukkitUtils.sendTitle(players, title, subTitle, 20, 40, 20);
            }
        }

        if (nextUpgrade != null) {
            factionData.setUpgradeLevel(nextUpgrade.getLevel());

            List<Upgrade> nexts = new ArrayList<>();
            nextUpgrade.getUpgrades().forEach(upgrade -> nexts.add(new Upgrade(upgrade)));

            factionData.setUpgrades(nexts);

        } else {
            factionData.getUpgrades().clear();
        }

        VaultUtils.withdrawMoney(player, upgradeData.getPrice());
        this.handleRewards(factionData, upgradeData);

    }

    private void handleRewards(FactionData factionData, UpgradeData upgradeData) {
        upgradeData.getRewards().forEach(line ->
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), line.replace("%faction%", factionData.getFactionName())));
    }

    public List<Upgrade> getUpgradesByLevel(int level) {
        UpgradeData upgradeData = this.getUpgradesDatas().stream().filter(data -> data.getLevel() == level).findFirst().orElse(null);
        if (upgradeData == null) return null;

        return upgradeData.getUpgrades();
    }

    public void upgradeRequirementGUI(Player player, FactionData factionData, int level) {
        UpgradeData upgradeData = getUpgradeDataByLevel(level);

        VirtualGUI gui = new VirtualGUI(upgradeData.getName(), Size.TROIS_LIGNE);

        factionData.getUpgrades().forEach(upgrade -> {
            List<String> lore = new ArrayList<>(upgrade.getLore());
            lore.replaceAll(line -> PlaceholderAPI.setPlaceholders(player,
                    line.replace("%actual-goal%", String.valueOf(upgrade.getActualGoal()))
                            .replace("%goal%", String.valueOf(upgrade.getGoal()))));

            ItemStack upgradeItem = new ItemBuilder(upgrade.getItemData().getMaterial(), 1, upgrade.getItemData().getData())
                    .setName(upgrade.getName())
                    .setLore(lore)
                    .toItemStack();
            gui.setItem(upgrade.getSlot(), new VirtualItem(upgradeItem));
        });

        if (factionData.getUpgradeLevel() == upgradeData.getLevel()) {
            int ended = 0;

            for (Upgrade upgrade : factionData.getUpgrades()) {
                if (upgrade.getActualGoal() >= upgrade.getGoal()) ended++;
            }

            if (ended >= factionData.getUpgrades().size()) {
                if (factionData.getUpgradeLevel() > 0 && factionData.getUpgrades().isEmpty()) {
                    gui.setItem(22, new VirtualItem(new ItemBuilder(Material.STAINED_GLASS_PANE, 1).setDurability((short) 0).setName(" ")));
                } else {
                    List<String> lore = new ArrayList<>(UpgradeManager.getInstance().getUpgradeConfig().getStringList("F_UPGRADE.UNLOCKED_REWARDS_LORE"));

                    lore.replaceAll(line -> PlaceholderAPI.setPlaceholders(player, line.replace("%price%", formatAmount(upgradeData.getPrice()))));

                    gui.setItem(22, new VirtualItem(new ItemBuilder(Material.WATCH)
                            .setName(UpgradeManager.getInstance().getUpgradeConfig().getString("F_UPGRADE.UNLOCKED_REWARDS"))
                            .setLore(lore)
                            .toItemStack()).onItemClick(new ClickAction() {
                        @Override
                        public void onClick(InventoryClickEvent event) {
                            if (factionData.getUpgradeLevel() == upgradeData.getLevel()) {
                                if (event.getCurrentItem().getType() != Material.WATCH) {
                                    return;
                                }
                                handleEndUpgrade(factionData, player);
                            }
                        }
                    }));
                }
            } else {
                List<String> lore = new ArrayList<>(UpgradeManager.getInstance().getUpgradeConfig().getStringList("F_UPGRADE.LOCKED_REWARDS_LORE"));

                lore.replaceAll(line -> PlaceholderAPI.setPlaceholders(player, line.replace("%price%", formatAmount(upgradeData.getPrice()))));

                gui.setItem(22, new VirtualItem(new ItemBuilder(Material.BARRIER)
                        .setName(UpgradeManager.getInstance().getUpgradeConfig().getString("F_UPGRADE.LOCKED_REWARD"))
                        .setLore(lore)
                        .toItemStack()));
            }
        } else {
            gui.setItem(22, new VirtualItem(new ItemBuilder(Material.STAINED_GLASS_PANE, 1).setDurability((short) 0).setName(" ")));
        }
        gui.open(player);
    }

    public List<Upgrade> getUpgradeByTarget(FactionData factionData, String target) {

        if (factionData.getUpgrades() == null) return null;

        return factionData.getUpgrades().stream().filter(upgrade -> upgrade.getTarget().equalsIgnoreCase(target)).collect(Collectors.toList());
    }

    public UpgradeData getUpgradeDataByLevel(int level) {
        return this.getUpgradesDatas().stream().filter(data -> data.getLevel() == level).findFirst().orElse(null);
    }

    public UpgradeData getUpgradeDataBySlot(int slot) {
        return this.getUpgradesDatas().stream().filter(data -> data.getSlot() == slot).findFirst().orElse(null);
    }

    public UpgradeData getUpgradeDataByName(String name) {
        return this.getUpgradesDatas().stream().filter(data -> data.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<UpgradeData> getUpgradesDatas() {
        return upgradeData;
    }

    public FactionData getFactionByName(String name) {
        return FactionManager.getInstance().getProvider().getProfiles().stream().filter(
                factionData -> factionData.getFactionName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static UpgradeManager getInstance() {
        return instance;
    }

    public YamlConfiguration getUpgradeConfig() {
        return upgradeConfig;
    }

    public static String formatAmount(double amount) {
        if (amount >= 1_000_000) {
            return String.format("%.1fM", amount / 1_000_000);
        } else if (amount >= 1_000) {
            return String.format("%.1fK", amount / 1_000);
        } else {
            return String.format("%.2f", amount);
        }
    }
}

