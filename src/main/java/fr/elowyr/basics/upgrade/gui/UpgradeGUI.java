package fr.elowyr.basics.upgrade.gui;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.factions.gui.FMenuGUI;
import fr.elowyr.basics.upgrade.UpgradeManager;
import fr.elowyr.basics.upgrade.data.UpgradeData;
import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.menu.ClickAction;
import fr.elowyr.basics.utils.menu.PaginationGUI;
import fr.elowyr.basics.utils.menu.items.VirtualItem;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class UpgradeGUI extends PaginationGUI {

    public UpgradeGUI(int page, Player player, FactionData factionData) {
        super(page, UpgradeManager.getInstance().getUpgradesDatas().size(), 27, UpgradeManager.getInstance().getUpgradeConfig().getString("F_UPGRADE.INVENTORY_NAME"));
        this.setMaxItemsPerPage(27);
        for (int i : new int[]{0, 1, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 52, 53}) {
            this.setItem(i, new VirtualItem(new ItemBuilder(Material.STAINED_GLASS_PANE, 1).setDurability((short) 1)));
        }

        this.setItem(4, new VirtualItem(new ItemBuilder(Material.SKULL_ITEM).setSkullOwner(player.getName()).setDurability((short) 3).setName("§e§lInformation §7▸ §6Faction").setLore(
                "&f",
                "&7▪ &fAccomplissez un maximum de &6mission",
                " &fpour &agagner&f un maximum de &ePoints Farm&f !",
                "",
                "&7▪ §fClassement Farm: §6§l#" + PlaceholderAPI.setPlaceholders(player, "%classification_faction_index_farm_mine%"),
                "&7▪ §fClassement PvP: §6§l#" + PlaceholderAPI.setPlaceholders(player, "%classification_faction_index_pvp_mine%"),
                "&f"
        ).toItemStack()));

        for (UpgradeData upgrade : UpgradeManager.getInstance().getUpgradesDatas()) {
            if (factionData.getUpgradeLevel() > upgrade.getLevel()) {
                ItemStack itemLocked;
                if (upgrade.getItemLocked().startsWith("hdb-")) {
                    itemLocked = new ItemStack(ElowyrBasics.getInstance().getHeadDatabaseAPI().getItemHead(upgrade.getItemLocked().replace("hdb-", "")));
                } else {
                    itemLocked = new ItemStack(Material.getMaterial(upgrade.getItemLocked()));
                }

                this.setItem(upgrade.getSlot(), new VirtualItem(new ItemBuilder(itemLocked)
                        .setName(upgrade.getName())
                        .setLore(upgrade.getLore())
                        .toItemStack()
                ));
            } else {
                ItemStack itemUnLocked = null;

                if (upgrade.getItemUnLocked().startsWith("hdb-")) {
                    itemUnLocked = new ItemStack(ElowyrBasics.getInstance().getHeadDatabaseAPI().getItemHead(upgrade.getItemLocked().replace("hdb-", "")));
                } else {
                    itemUnLocked = new ItemStack(Material.getMaterial(upgrade.getItemUnLocked()));
                }

                this.setItem(upgrade.getSlot(), new VirtualItem(new ItemBuilder(itemUnLocked)
                        .setName(upgrade.getName())
                        .setLore(upgrade.getLore())
                        .toItemStack()
                ).onItemClick(new ClickAction() {
                    @Override
                    public void onClick(InventoryClickEvent event) {
                        UpgradeData upgradeData = UpgradeManager.getInstance().getUpgradeDataBySlot(event.getSlot());
                        if (factionData.getUpgradeLevel() < upgradeData.getLevel()) {
                            player.sendMessage(Utils.color(UpgradeManager.getInstance().getUpgradeConfig().getString("F_UPGRADE.FACTION_NOT_THIS_LEVEL")));
                            player.closeInventory();
                            return;
                        }

                        if (factionData.getUpgradeLevel() == upgradeData.getLevel() && factionData.getUpgrades().isEmpty()) {
                            player.closeInventory();
                            player.sendMessage(Utils.color(UpgradeManager.getInstance().getUpgradeConfig().getString("F_UPGRADE.ALREADY_UNLOCK")));
                            return;
                        }

                        if (factionData.getUpgradeLevel() > upgradeData.getLevel()) {
                            player.closeInventory();
                            player.sendMessage(Utils.color(UpgradeManager.getInstance().getUpgradeConfig().getString("F_UPGRADE.ALREADY_UNLOCK")));
                            return;
                        }
                        UpgradeManager.getInstance().upgradeRequirementGUI(player, factionData, upgradeData.getLevel());
                    }
                }));
            }
        }

        this.setItem(49, new VirtualItem(new ItemBuilder(
                ElowyrBasics.getInstance().getHeadDatabaseAPI().getItemHead("37793"))
                .setName("§c§l⦙ §4§lRetour")).onItemClick(new ClickAction() {
            @Override
            public void onClick(InventoryClickEvent event) {
                FMenuGUI gui = new FMenuGUI(player);
                gui.open(player);
            }
        }));

        this.open(player);
    }

    @Override
    public void actionProcess(Player player, int i) {

    }
}
