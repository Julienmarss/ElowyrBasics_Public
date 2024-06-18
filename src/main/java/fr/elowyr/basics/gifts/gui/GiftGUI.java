package fr.elowyr.basics.gifts.gui;

import fr.elowyr.basics.gifts.data.Gift;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.DurationFormatter;
import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.menu.ClickAction;
import fr.elowyr.basics.utils.menu.items.VirtualItem;
import fr.elowyr.basics.utils.menu.menus.Size;
import fr.elowyr.basics.utils.menu.menus.VirtualGUI;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class GiftGUI extends VirtualGUI {

    public GiftGUI(Player player) {
        super("&6&lElowyr &7▸ &eVos Dons", Size.SIX_LIGNE);

        ProfileData profileData = ProfileManager.getInstance().getProvider().get(player.getUniqueId().toString());

        if (profileData.getGifts().isEmpty()) {
            this.setItem(22, new VirtualItem(new ItemBuilder(Material.STAINED_GLASS_PANE).setDyeColor(DyeColor.RED).setName("&cAucun don n'est disponible.")
                    .setLore(
                            "",
                            "&7▪ &fAucun &6don&f n'est &cdisponible &fpour",
                            " &fle moment reviens voir plus tard !"
                    )));
        } else {
            for (int count = 0; count < profileData.getGifts().size(); count++) {
                Gift gift = profileData.getGifts().get(count);
                int finalCount = count;
                this.addItem(new VirtualItem(new ItemBuilder(Material.CHEST).setName("&fDon de &6&l" + gift.getSender())
                        .setLore(
                                "&fDate du don: &e" + DurationFormatter.getDurationDate(gift.getCreatedMillis()),
                                "&fMessage: &b" + gift.getMessage()
                        )).onItemClick(new ClickAction() {
                    @Override
                    public void onClick(InventoryClickEvent event) {
                        if (player.getInventory().firstEmpty() == -1) {
                            player.sendMessage(Utils.color("&6&lDon &7◆ &fVous devez &cavoir&f de la place dans votre inventaire."));
                            return;
                        }
                        profileData.getGifts().remove(finalCount);
                        Inventory inventory = Bukkit.createInventory(null, 54, "§fDon de §6§l" + gift.getSender());
                        for (int items = 0; items < gift.getItems().size(); items++) {
                            inventory.addItem(gift.getItems().get(items));
                        }
                        player.closeInventory();
                        player.openInventory(inventory);
                    }
                }));
            }
        }
        this.open(player);
    }
}
