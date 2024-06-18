package fr.elowyr.basics.factions.gui;

import com.massivecraft.factions.FPlayers;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.missions.MissionManager;
import fr.elowyr.basics.missions.guis.MissionGUI;
import fr.elowyr.basics.upgrade.UpgradeManager;
import fr.elowyr.basics.upgrade.data.Upgrade;
import fr.elowyr.basics.upgrade.gui.UpgradeGUI;
import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.menu.ClickAction;
import fr.elowyr.basics.utils.menu.items.VirtualItem;
import fr.elowyr.basics.utils.menu.menus.Size;
import fr.elowyr.basics.utils.menu.menus.VirtualGUI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FMenuGUI extends VirtualGUI {

    public FMenuGUI(Player player) {
        super("&7Menu de Faction", Size.CINQ_LIGNE);

        FactionData factionData = FactionManager.getInstance().getProvider().get(FPlayers.getInstance().getByPlayer(player).getFactionId()
        );
        getBorders(this, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1));

        this.setItem(4, new VirtualItem(new ItemBuilder(Material.SKULL_ITEM).setSkullOwner(player.getName()).setDurability((short) 3).setName("§6§lInformation §7➜ §eFaction").setLore(
                "§f",
                "§7▪ §fClassement Farm : §6§l#" + PlaceholderAPI.setPlaceholders(player, "%classification_faction_index_farm_mine%"),
                "§7▪ §fClassement PvP : §6§l#" + PlaceholderAPI.setPlaceholders(player, "%classification_faction_index_pvp_mine%"),
                "§7▪ §fPower : §a§l" + PlaceholderAPI.setPlaceholders(player, "%factionsuuid_faction_power%") + "§7/§a§l" + PlaceholderAPI.setPlaceholders(player, "%factionsuuid_faction_powermax%"),
                "§f"
        ).toItemStack()));

        this.setItem(21, new VirtualItem(new ItemBuilder(Material.DIAMOND_HOE).setName("§6§lMission de Faction")
                .setLore(
                        "",
                        "&7▪ &fAccédez aux &adifférentes",
                        " &fQuêtes de&6 Faction.",
                        ""
                )).onItemClick(new ClickAction() {
            @Override
            public void onClick(InventoryClickEvent event) {
                MissionGUI gui = new MissionGUI(1, player, MissionManager.getInstance());
                gui.open(player);
            }
        }));

        this.setItem(23, new VirtualItem(new ItemBuilder(Material.BOOK_AND_QUILL).setName("§6§lPrestige de Faction")
                .setLore(
                        "",
                        "&7▪ &fAccédez aux &adifférents",
                        " &fAmélioration de&6 Faction.",
                        ""
                )).onItemClick(new ClickAction() {
            @Override
            public void onClick(InventoryClickEvent event) {

                if (factionData.getUpgrades().isEmpty()) {
                    List<Upgrade> upgrades = new ArrayList<>();

                    UpgradeManager.getInstance().getUpgradesByLevel(1).forEach(upgrade -> {
                        upgrades.add(new Upgrade(upgrade));
                    });

                    factionData.setUpgradeLevel(1);
                    factionData.setUpgrades(upgrades);
                    FactionManager.getInstance().getProvider().write(factionData);
                }

                UpgradeGUI gui = new UpgradeGUI(1, player, factionData);
                gui.open(player);
            }
        }));

        this.setItem(40, new VirtualItem(new ItemBuilder(Material.BARRIER).setName("§c§l⦙ §4§lRetour")).onItemClick(new ClickAction() {
            @Override
            public void onClick(InventoryClickEvent event) {
                player.closeInventory();
            }
        }));
    }

    public void getBorders(VirtualGUI inv, ItemStack itemStack) {
        for (int i : new int[]{0, 1, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 43, 44})
            inv.setItem(i, new VirtualItem(itemStack));
    }
}
