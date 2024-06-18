package fr.elowyr.basics.titles.gui;

import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.titles.TitleManager;
import fr.elowyr.basics.titles.data.Title;
import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.menu.BoardContainer;
import fr.elowyr.basics.utils.menu.ClickAction;
import fr.elowyr.basics.utils.menu.PaginationGUI;
import fr.elowyr.basics.utils.menu.items.VirtualItem;
import fr.elowyr.basics.utils.menu.menus.VirtualGUI;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TitleGUI extends PaginationGUI {

    public TitleGUI(int page, Player player, TitleManager titleManager) {
        super(page, titleManager.getProvider().getTitles().size(), 27, "&6&lElowyr &7▸ &eTitres &7(" + page + ")");
        this.setMaxItemsPerPage(27);

        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());

        getBorders(this, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1));

        this.setItem(4, new VirtualItem(new ItemBuilder(Material.SKULL_ITEM).setSkullOwner(player.getName()).setDurability((short) 3).setName("§e§lInformation §7▸ §6" + player.getName()).setLore(
                "&f",
                "&f⋄ &7Grade: " + PlaceholderAPI.setPlaceholders(player, "%vault_prefix%"),
                "&f⋄ &7Argent: &f" + PlaceholderAPI.setPlaceholders(player, "%vault_eco_balance_formatted%") + "$",
                "&f⋄ &7Crédits: &f" + PlaceholderAPI.setPlaceholders(player, "%points_points%") + "✪",
                ""
        ).toItemStack()));

        if (getPage() > 1) {
            this.setItem(47, new VirtualItem(new ItemBuilder(Material.ARROW).setName("§7§l⦙ §fPage §aPrécédente")).onItemClick(new ClickAction() {
                @Override
                public void onClick(InventoryClickEvent event) {
                    new TitleGUI(1, player, titleManager).open((Player) event.getWhoClicked());
                }
            }));
        }
        if (getPage() < getTotalPages()) {
            this.setItem(51, new VirtualItem(new ItemBuilder(Material.ARROW).setName("§7§l⦙ §fPage §aSuivante")).onItemClick(new ClickAction() {
                @Override
                public void onClick(InventoryClickEvent event) {
                    new TitleGUI(page + 1, player, titleManager).open((Player) event.getWhoClicked());
                }
            }));
        }
        List<String> descriptionEmpty = titleManager.getConfig().getStringList("GUI.EMPTY-DESCRIPTION");
        if (titleManager.getProvider().getTitles().isEmpty()) {
            this.setItem(22, new VirtualItem(new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 14)
                    .setName(titleManager.getConfig().getString("GUI.EMPTY-NAME"))
                    .setLore(descriptionEmpty)));
        } else {

            Collection<Title> titles = titleManager.getProvider().getTitles();
            Iterator<Title> iterator = titles.iterator();
            int[] datas = this.getDatas();
            List<String> descriptionHas = Utils.colorAll(titleManager.getConfig().getStringList("GUI.DESCRIPTION"));
            descriptionHas.replaceAll(line -> PlaceholderAPI.setPlaceholders(player,
                    line.replace("%player%", player.getName()).replace("%title%", titles.iterator().next().getTitle())));
            final BoardContainer boardContainer = this.getBoardContainer(20, 5, 2);
            for (int count = datas[0], index = 0; count < datas[1] && index < boardContainer.getSlots().size(); index++, count++) {
                Title title = iterator.next();
                if (profileData.getBuyedTitle().contains(title.getName())) {
                    this.setItem(boardContainer.getSlots().get(index), new VirtualItem(new ItemBuilder(Material.NAME_TAG)
                            .setName(titleManager.getConfig().getString("GUI.NAME").replace("%title%", title.getTitle()))
                            .setLore(descriptionHas)).onItemClick(new ClickAction() {
                        @Override
                        public void onClick(InventoryClickEvent event) {
                            if (event.isLeftClick()) {
                                if (!profileData.getTitle().equalsIgnoreCase(title.getTitle())) {
                                    profileData.setTitle(title.getTitle());
                                    player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.ACTIVED").replace("%title%", title.getTitle())));
                                } else {
                                    player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.ALREADY-ACTIVED").replace("%title%", title.getTitle())));
                                }
                                player.closeInventory();
                            } else if (event.isRightClick()) {
                                if (profileData.getTitle().equalsIgnoreCase(title.getTitle())) {
                                    profileData.setTitle("");
                                    player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.DESACTIVED").replace("%title%", title.getTitle())));
                                } else {
                                    player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.ALREADY-ALREADY-DESACTIVED").replace("%title%", title.getTitle())));
                                }
                                player.closeInventory();
                            }
                        }
                    }));
                } else {
                    List<String> description = Utils.colorAll(titleManager.getConfig().getStringList("GUI.DESCRIPTION-NO"));
                    description.replaceAll(line -> line.replace("%player%", player.getName()).replace("%title%", titles.iterator().next().getTitle()));
                    this.setItem(boardContainer.getSlots().get(index), new VirtualItem(new ItemBuilder(Material.BARRIER)
                            .setName("&e&l⦙ &6&lTitre &f- " + title.getTitle())
                            .setLore(description)).onItemClick(new ClickAction() {
                        @Override
                        public void onClick(InventoryClickEvent event) {
                            player.sendMessage(Utils.color(titleManager.getConfig().getString("MESSAGES.NO-HAVE").replace("%title%", title.getTitle())));
                            player.closeInventory();
                        }
                    }));
                }
            }
        }

        this.open(player);
    }

    @Override
    public void actionProcess(Player player, int i) {
    }

    public void getBorders(VirtualGUI inv, ItemStack itemStack) {
        for (int i : new int[]{0, 1, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 52})
            inv.setItem(i, new VirtualItem(itemStack));
    }
}
