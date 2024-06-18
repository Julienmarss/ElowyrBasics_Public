package fr.elowyr.basics.bottlexp.commands;

import fr.elowyr.basics.bottlexp.BottleXPManager;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BottleXpCommand {
    private BottleXPManager manager = BottleXPManager.getInstance();
    @Command(name = "bottlexp", aliases = {"bouteillexp"}, permission = "elowyrbasics.bottlexp")
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        if (!BottleXPManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(player, BottleXPManager.getInstance());
            return;
        }

        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(Utils.color(manager.getConfig().getString("MESSAGES.INVENTORY-FULL")));
            return;
        }

        if (commandArgs.length() == 0) {
            int count = player.getLevel();

            if (count != 0) {
                player.setExp(0.0F);
                player.setLevel(0);

                ItemStack itemStack = new ItemStack(Material.EXP_BOTTLE);
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName(String.valueOf(manager.BOTTLE_NAME));
                meta.setLore(Arrays.asList(manager.BOTTLE_LORE + count));
                itemStack.setItemMeta(meta);
                player.getInventory().addItem(itemStack);
                player.sendMessage(Utils.color(manager.getConfig().getString("MESSAGES.SUCCESS").replace("%level%", String.valueOf(count))));
            } else {
                player.sendMessage(Utils.color(manager.getConfig().getString("MESSAGES.DONT-LEVEL")));
            }
        } else if (commandArgs.length() == 1) {
            int number = Integer.parseInt(commandArgs.getArgs(0));
            if (number != 0 && number > 0) {
                if (player.getLevel() < number) {
                    player.sendMessage(Utils.color(manager.getConfig().getString("MESSAGES.DONT-LEVEL")));
                } else {
                    player.setLevel(player.getLevel() - number);
                    ItemStack itemStack = new ItemStack(Material.EXP_BOTTLE);
                    ItemMeta meta = itemStack.getItemMeta();
                    meta.setDisplayName(String.valueOf(manager.BOTTLE_NAME));
                    meta.setLore(Arrays.asList(manager.BOTTLE_LORE + number));
                    itemStack.setItemMeta(meta);
                    player.getInventory().addItem(itemStack);
                    player.sendMessage(Utils.color(manager.getConfig().getString("MESSAGES.SUCCESS").replace("%level%", String.valueOf(number))));
                }
            }
        }
    }
}