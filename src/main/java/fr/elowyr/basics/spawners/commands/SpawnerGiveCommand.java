package fr.elowyr.basics.spawners.commands;

import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.spawners.SpawnersManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnerGiveCommand {

    @Command(name = "spawners.give", permission = "elowyrbasics.spawners.give")
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

        EntityType entityType = EntityType.fromName(args.getArgs(1));

        if (entityType == null) {
            player.sendMessage(Utils.color(SpawnersManager.getInstance().getConfig().getString("MESSAGES.NO-EXIST")));
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(args.getArgs(2));
        } catch (NumberFormatException e) {
            player.sendMessage(Utils.color(SpawnersManager.getInstance().getConfig().getString("MESSAGES.NO-NUMBER")));
            return;
        }

        SpawnersManager.getInstance().giveSpawnersToInventory(player, target, entityType, quantity);

        target.sendMessage(Utils.color(SpawnersManager.getInstance().getConfig().getString("MESSAGES.GIVE.SUCCESS-OTHER")
                .replace("%quantity%", String.valueOf(quantity))
                .replace("%spawner%", entityType.getName())));

        if (args.getSender() instanceof Player) {
            player.sendMessage(Utils.color(SpawnersManager.getInstance().getConfig().getString("MESSAGES.GIVE.SUCCESS")
                    .replace("%quantity%", String.valueOf(quantity))
                    .replace("%name%", target.getName())
                    .replace("%spawner%", entityType.getName())));
            SpawnersManager.getLogs().print(player.getName() + " give " + String.valueOf(quantity) + " spawners to " + target.getName() + " -> Type:" + entityType.getName() + "\n");
        }
    }
}
