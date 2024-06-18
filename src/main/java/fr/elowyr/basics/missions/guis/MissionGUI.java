package fr.elowyr.basics.missions.guis;

import com.massivecraft.factions.FPlayers;
import fr.elowyr.basics.factions.FactionManager;
import fr.elowyr.basics.factions.data.FactionData;
import fr.elowyr.basics.factions.gui.FMenuGUI;
import fr.elowyr.basics.missions.MissionManager;
import fr.elowyr.basics.missions.data.Mission;
import fr.elowyr.basics.utils.*;
import fr.elowyr.basics.utils.menu.BoardContainer;
import fr.elowyr.basics.utils.menu.ClickAction;
import fr.elowyr.basics.utils.menu.PaginationGUI;
import fr.elowyr.basics.utils.menu.items.VirtualItem;
import fr.elowyr.basics.utils.menu.menus.VirtualGUI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MissionGUI extends PaginationGUI {

    public MissionGUI(int page, Player player, MissionManager missionManager) {
        super(page, missionManager.getMissions().size(), 27, "&7Missions de Faction");
        this.setMaxItemsPerPage(27);

        FactionData factionData = FactionManager.getInstance().getProvider().get(FPlayers.getInstance().getByPlayer(player).getFactionId());

        getBorders(this, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1));

        this.setItem(4, new VirtualItem(new ItemBuilder(Material.SKULL_ITEM).setSkullOwner(player.getName()).setDurability((short) 3).setName("§e§lInformation §7▸ §6Faction").setLore(
                "&f",
                "&7▪ &fAccomplissez un maximum de &6mission",
                " &fpour &agagner&f un maximum de &ePoints Farm&f !",
                "",
                "&7▪ §fClassement Farm: §6§l#" + PlaceholderAPI.setPlaceholders(player, "%classification_faction_index_farm_mine%"),
                "&7▪ §fClassement PvP: §6§l#" + PlaceholderAPI.setPlaceholders(player, "%classification_faction_index_pvp_mine%"),
                "&f"
        ).toItemStack()));

        if (getPage() > 1) {
            this.setItem(47, new VirtualItem(new ItemBuilder(Material.ARROW).setName("§7§l⦙ §fPage §aPrécédente")).onItemClick(new ClickAction() {
                @Override
                public void onClick(InventoryClickEvent event) {
                    new MissionGUI(1, player, missionManager).open((Player) event.getWhoClicked());
                }
            }));
        }
        if (getPage() < getTotalPages()) {
            this.setItem(51, new VirtualItem(new ItemBuilder(Material.ARROW).setName("§7§l⦙ §fPage §aSuivante")).onItemClick(new ClickAction() {
                @Override
                public void onClick(InventoryClickEvent event) {
                    new MissionGUI(page + 1, player, missionManager).open((Player) event.getWhoClicked());
                }
            }));
        }

        List<Mission> missions = missionManager.getMissions();

        int[] datas = this.getDatas();
        final BoardContainer boardContainer = this.getBoardContainer(10, 7, 4);
        for (int count = datas[0], index = 0; count < datas[1] && index < boardContainer.getSlots().size(); index++, count++) {
            Mission mission = missions.get(count).clone();
            String progressValue = factionData.getMissionById(mission.getId()) != null ? String.valueOf(factionData.getMissionById(mission.getId()).getProgress()) : "0";
            if (factionData.getMissions().isEmpty() && mission.getId() != 1 || !factionData.getMissions().isEmpty() && factionData.getMissions().get(0).getId() + 1 != mission.getId() && !factionData.getMissionIdFinish().contains(mission.getId()) && !factionData.getMissions().contains(mission) && mission.getId() != 1) {
                ItemStack icon = CustomHeads.create(missionManager.getConfig().getString("heads.impossible"));
                this.setItem(boardContainer.getSlots().get(index), new VirtualItem(new ItemBuilder(icon).setName("&6&lMission de Faction &7◆ &e#" + mission.getId())
                        .setLore(
                                "",
                                "&7◆ &fStatut: &cNon Sélectionnée &4✗",
                                "&7◆ &fObjectif: &4Vérrouillé &4✗",
                                "&7◆ &fProgression: &b0&f/&b0",
                                "",
                                "&7◆ §fRécompense:",
                                " &7▪ &7Remporte &cIndisponible",
                                "",
                                "&7➼ &cVous n'avez pas accès a cette quête."
                        )));
            } else if (factionData.getMissionIdFinish().contains(mission.getId()) && factionData.getMissions().get(0).isFinish()) {
                ItemStack icon = CustomHeads.create(missionManager.getConfig().getString("heads.finish"));
                this.setItem(boardContainer.getSlots().get(index), new VirtualItem(new ItemBuilder(icon).setName("&6&lMission de Faction &7◆ &e#" + mission.getId())
                        .setLore(
                                "",
                                "&7◆ &fStatut: &aTerminée &2✓",
                                "&7◆ &fObjectif: &7" + mission.getDescription(),
                                "&7◆ &fProgression: &b" + progressValue + "&f/&b" + mission.getObjective(),
                                "",
                                "&7◆ §fRécompense:",
                                " &7▪ &7Remporte &f" + mission.getRewards() + " &7points de farm.",
                                "",
                                "&7➼ &aVous avez terminé cette mission."
                        )));
            } else if (factionData.getMissionIdProgress().contains(mission.getId())) {
                ItemStack icon = CustomHeads.create(missionManager.getConfig().getString("heads.progress"));
                this.setItem(boardContainer.getSlots().get(index), new VirtualItem(new ItemBuilder(icon)
                        .setName("&6&lMission de Faction &7◆ &e#" + mission.getId())
                        .setLore(
                                "",
                                "&7◆ &fStatut: &6En cours &e↻",
                                "&7◆ &fObjectif: &7" + mission.getDescription(),
                                "&7◆ &fProgression: &b" + progressValue + "&f/&b" + mission.getObjective(),
                                "",
                                "&7◆ §fRécompense:",
                                " &7▪ &7Remporte &f" + mission.getRewards() + " &7points de farm.",
                                "",
                                "&7➼ &fCette &6mission &fest déjà en &acours&f."
                        )));
            } else if (!factionData.getMissionIdProgress().contains(mission.getId())){
                ItemStack icon = CustomHeads.create(missionManager.getConfig().getString("heads.select"));
                this.setItem(boardContainer.getSlots().get(index), new VirtualItem(new ItemBuilder(icon)
                        .setName("&6&lMission de Faction &7◆ &e#" + mission.getId())
                        .setLore(
                                "",
                                "&7◆ &fStatut: &cNon Sélectionnée &4✗",
                                "&7◆ &fObjectif: &7" + mission.getDescription(),
                                "&7◆ &fProgression: &b" + progressValue + "&f/&b" + mission.getObjective(),
                                "",
                                "&7◆ §fRécompense:",
                                " &7▪ &7Remporte &f" + mission.getRewards() + " &7points de farm.",
                                "",
                                "&7➼ &fClique ici pour &asélectionner &fla &6mission&f."
                        )).onItemClick(new ClickAction() {
                    @Override
                    public void onClick(InventoryClickEvent event) {
                        final Player player = (Player) event.getWhoClicked();
                        if (factionData.getMissionIdFinish().contains(mission.getId())) {
                            player.sendMessage(Utils.color("§4§l✘ &7◆ &fCette &6Mission &fest déjà &cterminée &fpour vous."));
                            return;
                        }
                        if (!factionData.getMissions().isEmpty()) {
                            if (!factionData.getMissions().get(0).isFinish()) {
                                player.sendMessage(Utils.color("§4§l✘ &7◆ &fIl est &cnécessaire &fde terminer votre &6Mission &factuelle."));
                                return;
                            }
                        }

                        FPlayers.getInstance().getByPlayer(player).getFaction().getOnlinePlayers().forEach(fplayer -> {
                            fplayer.playSound(player.getLocation(), Sound.ANVIL_USE, 10, 20);
                            BukkitUtils.sendTitle(fplayer, "§6Mission de Faction", "§fVous avez §adébuté§f la §6Mission de Faction #" + mission.getId() + "§f.", 20, 50, 20);
                        });
                        factionData.getMissions().add(mission);
                        factionData.getMissionIdProgress().add(mission.getId());
                        FactionManager.getInstance().getProvider().write(factionData);
                        player.closeInventory();
                    }
                }));
            }
        }
        ItemStack heads = CustomHeads.create("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FjODc4MjBiNWI5YmEzM2FhZjQzNjA3ZDlkMzFkOGZkODNjMmY5ZTgzOTk1ZDBkYmVkOGMyMDY3OGMwNzM1NSJ9fX0=");
        this.setItem(53, new VirtualItem(new ItemBuilder(heads).setName("§2§l⦙ §aRéinitialisation des missions")
                .setLore(
                        "",
                        "&7▪ &fCliquez pour obtenir des",
                        " &fnouvelles missions de faction.",
                        "",
                        "&7➼ &fPrix: &e50.000.000\u26c1",
                        "",
                        "&f&nRappel&f:",
                        "     &7&oLes missions se réinitialisent",
                        "&7&oautomatiquement chaque dimanche à 23h59",
                        ""
                )).onItemClick(new ClickAction() {
            @Override
            public void onClick(InventoryClickEvent event) {
                if (factionData.getMissions().size() < 3) {
                    player.sendMessage(Utils.color("§4§l✘ &7◆ &fVous devez &aterminer &faux minimums &63 missions&f."));
                    return;
                }
                if (VaultUtils.getBalance(player) < 10000000) {
                    player.sendMessage(Utils.color("§4§l✘ &7◆ &cVous n'avez pas suffisamment d'argent."));
                    return;
                }

                VaultUtils.withdrawMoney(player, 10000000);
                factionData.getMissions().clear();
                factionData.getMissionIdFinish().clear();
                player.sendMessage(Utils.color("&2&l✔ &7◆ &fVous avez &aréinitialisé &fvos missions avec &asuccès &f!"));
                FPlayers.getInstance().getByPlayer(player).getFaction().getOnlinePlayers().forEach(fplayer -> {
                    fplayer.playSound(player.getLocation(), Sound.ANVIL_USE, 10, 20);
                    BukkitUtils.sendTitle(fplayer, "§6Mission de Faction", "§fVous avez §aréinitialisé §fvos missions avec §asuccès §f!", 20, 50, 20);
                });
            }
        }));

        this.setItem(49, new VirtualItem(new ItemBuilder(Material.BARRIER).setName("§c§l⦙ §4§lRetour")).onItemClick(new ClickAction() {
            @Override
            public void onClick(InventoryClickEvent event) {
                FMenuGUI gui = new FMenuGUI(player);
                gui.open(player);
            }
        }));

    }

    public void getBorders(VirtualGUI inv, ItemStack itemStack) {
        for (int i : new int[]{0, 1, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 52})
            inv.setItem(i, new VirtualItem(itemStack));
    }

    @Override
    public void actionProcess(Player player, int i) {

    }
}
