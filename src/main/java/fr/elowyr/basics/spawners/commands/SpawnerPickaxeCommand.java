package fr.elowyr.basics.spawners.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.spawners.SpawnersManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnerPickaxeCommand {

    @Command(name = "spawners.pickaxe", permission = "elowyrbasics.spawners.pickaxe")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!SpawnersManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(args.getPlayer(), SpawnersManager.getInstance());
            return;
        }

        if (args.length() != 3) {
            for (String usage : SpawnersManager.getInstance().getConfig().getStringList("MESSAGES.USAGE")) {
                player.sendMessage(Utils.color(usage));
            }
            return;
        }

        Player target = Bukkit.getPlayer(args.getArgs(0));

        if (target == null) {
            player.sendMessage(Utils.color(SpawnersManager.getInstance().getConfig().getString("MESSAGES.NO-PLAYER").replace("%name%", target.getName())));
            return;
        }
        if (Integer.parseInt(args.getArgs(2)) <= 0) {
            player.sendMessage(Utils.color(SpawnersManager.getInstance().getConfig().getString("MESSAGES.INVALID-AMOUNT")));
            return;
        }
        ItemStack itemStack = new ItemStack(SpawnersManager.getInstance().getPickaxeByID(Integer.parseInt(args.getArgs(1))).getRItemUnsafe().toItemMaker().toItemStack());
        itemStack.setAmount(Integer.parseInt(args.getArgs(2)));
        target.getInventory().addItem(new ItemStack(itemStack));
        player.sendMessage(Utils.color(SpawnersManager.getInstance().getConfig().getString("MESSAGES.PICKAXE.SUCCESS")
                .replace("%quantity%", args.getArgs(2))
                .replace("%player%", target.getName())
                .replace("%level%", args.getArgs(2))));
        target.sendMessage(Utils.color(SpawnersManager.getInstance().getConfig().getString("MESSAGES.PICKAXE.SUCCESS-OTHER")
                .replace("%quantity%", args.getArgs(2))
                .replace("%level%", args.getArgs(1))));
        SpawnersManager.getLogs().print(args.getSender().getName() + " give Spawners Pickaxe to " + target.getName() + " (level: " + args.getArgs(1) + ", quantity: " + args.getArgs(2) + ")\n");
    }
}
