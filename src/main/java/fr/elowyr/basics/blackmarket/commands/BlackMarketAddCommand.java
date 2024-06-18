package fr.elowyr.basics.blackmarket.commands;

import fr.elowyr.basics.blackmarket.BlackMarketManager;
import fr.elowyr.basics.blackmarket.data.BlackData;
import fr.elowyr.basics.blackmarket.data.serializer.ItemSerializer;
import fr.elowyr.basics.blackmarket.data.serializer.LoreSerializer;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class BlackMarketAddCommand {

    @Command(name = "blackmarket.add", aliases = "bm.add", permission = "elowyrbasics.blackmarket.add")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!BlackMarketManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, BlackMarketManager.getInstance());
            return;
        }

        if (args.length() != 2) {
            player.sendMessage(Utils.color("&c/blackmarket add <quantity> <price>."));
            return;
        }

        String id = UUID.randomUUID().toString().substring(0, 3);
        ItemStack hand = player.getItemInHand();

        if (hand == null || hand.getType() == Material.AIR || hand.getType() == null) {
            player.sendMessage(Utils.color("&cVous n'avez rien dans votre main."));
            return;
        }

        try {
            int quantity = Integer.parseInt(args.getArgs(0));
            int price = Integer.parseInt(args.getArgs(1));

            HashMap<String, Integer> enchants = new HashMap<>();

            hand.getEnchantments().forEach(((enchantment, integer) -> enchants.put(enchantment.toString(), integer)));
            if (hand.getItemMeta().getLore() != null) {
                LoreSerializer loreSerializer = new LoreSerializer(hand.getItemMeta().getLore(), BlackMarketManager.getInstance().getConfig().getStringList("inventory-lore"));
                ItemSerializer itemSerializer = new ItemSerializer(hand.getType().toString(), hand.getItemMeta().getDisplayName(), quantity, loreSerializer, hand.getEnchantments());
                BlackData blackData = new BlackData(id, itemSerializer, price);
                BlackMarketManager.getInstance().getProvider().provide(id, blackData);
                BlackMarketManager.getInstance().getProvider().write();
                player.sendMessage(Utils.color("&fVous venez &ad'ajouter&f l'objet au blackmarket &7(id:" + id + ")"));
            } else {
                player.sendMessage(Utils.color("&cSeulement les objets avec des lores fonctionnent."));
            }
        } catch (NumberFormatException e) {
            player.sendMessage(Utils.color("&cLe chiffre est incorrect."));
        }
    }
}
