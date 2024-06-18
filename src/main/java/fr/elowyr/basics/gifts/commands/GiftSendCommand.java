package fr.elowyr.basics.gifts.commands;

import com.google.common.collect.Lists;
import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.gifts.GiftManager;
import fr.elowyr.basics.gifts.data.Gift;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.profiles.ProfileManager;
import fr.elowyr.basics.profiles.data.ProfileData;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import fr.elowyr.basics.utils.menu.CloseAction;
import fr.elowyr.basics.utils.menu.menus.Size;
import fr.elowyr.basics.utils.menu.menus.VirtualGUI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class GiftSendCommand {

    @Command(name = "gift.send", aliases = {"don.send", "dons.send"}, permission = "elowyrbasics.gift.send")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!GiftManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, GiftManager.getInstance());
            return;
        }

        if (args.length() < 2) {
            player.sendMessage(Utils.color("&6&lDon &7◆ &c/gift send <player> <message>."));
            return;
        }

        Player target = Bukkit.getServer().getPlayer(args.getArgs(0));
        ProfileData profileData = ProfileManager.getInstance().getProvider().get(target.getUniqueId().toString());
        if (profileData == null) {
            player.sendMessage(Utils.color("&6&lDon &7◆ &fLe joueur &e" + target.getName() + "&f est &cintrouvable &f!"));
            return;
        }

        if (!target.getPlayer().hasPermission("elowyrbasics.gift.gui")) {
            player.sendMessage(Utils.color("&6&lDon &7◆ &fLe joueur &e" + target.getName() + "&f n'est pas &cautorisé &fa recevoir des dons."));
            return;
        }

        if (target.getPlayer() == player || player.getName().equalsIgnoreCase(target.getName())) {
            player.sendMessage(Utils.color("&6&lDon &7◆ &fVous ne &cpouvez &fpas vous envoyer un &edon&f a vous même."));
            return;
        }

        if (profileData.isToggleGifts()) {
            player.sendMessage(Utils.color("&6&lDon &7◆ &fLe joueur à &cdésactivé&f la reception des dons."));
            return;
        }

        final String message = StringUtils.join(args.getArgs(), ' ', 1, args.length());
        VirtualGUI gui = new VirtualGUI("&fDéposez vos Items", Size.SIX_LIGNE).allowClick().onCloseAction(new CloseAction() {
            @Override
            public void onClose(InventoryCloseEvent event) {
                Inventory inventory = event.getInventory();
                List<ItemStack> items = Lists.newArrayList();
                for (int count = 0; count < (inventory.getContents().length); count++) {
                    ItemStack item = inventory.getContents()[count];
                    if (item != null && item.getType() != Material.AIR) {
                        items.add(item);
                    }
                }

                if (items.isEmpty()) {
                    player.sendMessage(Utils.color("&6&lDon &7◆ &fVous &cn'avez&f déposé aucun item."));
                    return;
                }

                Gift gift = new Gift(player.getName(), message, items);
                profileData.getGifts().add(gift);
                player.sendMessage(Utils.color("&6&lDon &7◆ &fVous &avenez &fde faire un don à &e" + target.getName() + "&f."));
                if (target.isOnline()) {
                    target.sendMessage(Utils.color("&6&lDon &7◆ &fVous venez de reçevoir un don de &e" + player.getName() +"&f !"));
                }
            }
        });
        gui.open(player);
    }
}
