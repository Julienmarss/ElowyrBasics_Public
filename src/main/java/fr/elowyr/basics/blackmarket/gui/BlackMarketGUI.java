package fr.elowyr.basics.blackmarket.gui;

import fr.elowyr.basics.blackmarket.BlackMarketManager;
import fr.elowyr.basics.utils.BukkitUtils;
import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.VaultUtils;
import fr.elowyr.basics.utils.menu.BoardContainer;
import fr.elowyr.basics.utils.menu.ClickAction;
import fr.elowyr.basics.utils.menu.PaginationGUI;
import fr.elowyr.basics.utils.menu.items.VirtualItem;
import fr.elowyr.basics.utils.menu.menus.VirtualGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;

public class BlackMarketGUI extends PaginationGUI {

    private final DecimalFormat decimalFormat = new DecimalFormat("##.0");

    public BlackMarketGUI(int page, Player player, BlackMarketManager blackMarketManager) {
        super(page, blackMarketManager.getProvider().getBlackDataMap().size(), 27, blackMarketManager.getConfig().getString("inventory-name"));


        getBorders(this, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1));

        this.setItem(4, new VirtualItem(new ItemBuilder(Material.SKULL_ITEM).setSkullOwner(player.getName())
                .setDurability((short) 3).setName("§e§lInformation §7▸ §6" + player.getName()).setLore(
                        "&f",
                        "&7▪ &fChaque jour, &6Léo&f propose une sélection",
                        "  &fd'objets &aprécieux &fà durée &climitée&f !",
                        "",
                        " &7◆ &fArgent: &e" + getMoneyFormat(VaultUtils.getEconomy().getBalance(player)) + "\u26c1",
                        " &7◆ &fChangement le: &e" + blackMarketManager.getConfig().getString("CHANGED-TIME")
                ).toItemStack()));

        if (blackMarketManager.getProvider().getBlackDataMap().isEmpty()) {
            this.setItem(22, new VirtualItem(new ItemBuilder(Material.STAINED_GLASS_PANE).setDurability((short) 14).setName(blackMarketManager.getConfig().getString("inventory-glass-empty"))
                    .setLore(
                            "",
                            "&7▪ &fAucun n'objet n'est &cdisponible &fpour le moment",
                            " &fdans le &6Marché Noir&f, reviens me voir plus tard !"
                    )));
        } else {

            blackMarketManager.getProvider().getBlackDataMap().forEach(blackData -> {
                int[] datas = this.getDatas();
                final BoardContainer boardContainer = this.getBoardContainer(21, 3, 2);
                for (int count = datas[0], index = 0; count < datas[1] && index < boardContainer.getSlots().size(); index++, count++) {
                    this.setItem(boardContainer.getSlots().get(index), new VirtualItem(new ItemBuilder(blackData.getItemSerializer().toItemMarket(blackData.getPrice(), blackData.getItemSerializer().getQuantity()))
                            .setName("&6&lObjet &7◆ &e" + blackData.getItemSerializer().getName())
                            .setLore(blackData.getItemSerializer().lore(blackData.getId(), blackData.getPrice(), blackData.getItemSerializer().getQuantity()))).onItemClick(new ClickAction() {
                        @Override
                        public void onClick(InventoryClickEvent event) {

                            if (blackData.getItemSerializer().getQuantity() > 1) {
                                if (blackData.getItemSerializer().getQuantity() == 10) {
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        BukkitUtils.sendTitle(player, Utils.color(BlackMarketManager.getInstance().getConfig().getString("MESSAGES.QUANTITY.TITLE"))
                                                , Utils.color(BlackMarketManager.getInstance().getConfig().getString("MESSAGES.QUANTITY.SUBTITLE").replace("%quantity%",
                                                        String.valueOf(blackData.getItemSerializer().getQuantity())).replace("%item%", blackData.getItemSerializer().getName())), 20, 50, 20);
                                    }
                                }
                                blackData.getItemSerializer().setQuantity(blackData.getItemSerializer().getQuantity() - 1);
                                event.getWhoClicked().getInventory().addItem(blackData.getItemSerializer().toItemStack());
                                BukkitUtils.sendTitle((Player) event.getWhoClicked(), Utils.color(BlackMarketManager.getInstance().getConfig().getString("MESSAGES.BUY.TITLE")),
                                        Utils.color(BlackMarketManager.getInstance().getConfig().getString("MESSAGES.BUY.SUBTITLE").replace("%price%",
                                                String.valueOf(blackData.getPrice())).replace("%item%", blackData.getItemSerializer().getName())), 20, 50, 20);
                                player.closeInventory();
                            } else {
                                blackMarketManager.getProvider().remove(blackData.getId());
                                blackMarketManager.getProvider().write();
                                event.getWhoClicked().getInventory().addItem(blackData.getItemSerializer().toItemStack());
                                BukkitUtils.sendTitle((Player) event.getWhoClicked(), Utils.color(BlackMarketManager.getInstance().getConfig().getString("MESSAGES.BUY.TITLE")),
                                        Utils.color(BlackMarketManager.getInstance().getConfig().getString("MESSAGES.BUY.SUBTITLE").replace("%price%",
                                                String.valueOf(blackData.getPrice())).replace("%item%", blackData.getItemSerializer().getName())), 20, 50, 20);
                                player.closeInventory();
                            }
                        }
                    }));
                }
            });
        }

        if (getPage() > 1) {
            this.setItem(47, new VirtualItem(new ItemBuilder(Material.ARROW).setName("§7§l⦙ §fPage §aPrécédente")).onItemClick(new ClickAction() {
                @Override
                public void onClick(InventoryClickEvent event) {
                    new BlackMarketGUI(1, player, blackMarketManager).open((Player) event.getWhoClicked());
                }
            }));
        }
        if (getPage() < getTotalPages()) {
            this.setItem(51, new VirtualItem(new ItemBuilder(Material.ARROW).setName("§7§l⦙ §fPage §aSuivante")).onItemClick(new ClickAction() {
                @Override
                public void onClick(InventoryClickEvent event) {
                    new BlackMarketGUI(page + 1, player, blackMarketManager).open((Player) event.getWhoClicked());
                }
            }));
        }

        this.setItem(49, new VirtualItem(new ItemBuilder(Material.BARRIER).setName("§c§l⦙ §4§lFermer")).onItemClick(new ClickAction() {
            @Override
            public void onClick(InventoryClickEvent event) {
                player.closeInventory();
            }
        }));

    }

    public void getBorders(VirtualGUI inv, ItemStack itemStack) {
        for (int i : new int[]{0, 1, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 52, 53})
            inv.setItem(i, new VirtualItem(itemStack));
    }

    @Override
    public void actionProcess(Player player, int i) {
    }

    private String getMoneyFormat(double money) {
        String format = decimalFormat.format(money / 1000) + "K";
        if (money < 1000) {
            format = decimalFormat.format(money);
            return format;
        }
        if (money >= 1000000 && money < 1000000000) {
            format = decimalFormat.format(money / 1000000) + "M";
            return format;
        }
        if (money >= 1000000000) {
            format = decimalFormat.format(money / 1000000000) + "Md";
            return format;
        }
        return format;
    }
}
