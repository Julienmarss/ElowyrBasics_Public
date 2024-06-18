package fr.elowyr.basics.spawners.commands;

import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.spawners.SpawnersManager;
import fr.elowyr.basics.utils.ItemBuilder;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SpawnerCustomCommand {

    @Command(name = "spawners.custom", permission = "elowyrbasics.custom")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (!SpawnersManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(args.getPlayer(), SpawnersManager.getInstance());
            return;
        }

        player.getInventory().addItem(new ItemBuilder(Material.MOB_SPAWNER).setName("§cCustom Spawner").toItemStack());
    }
}
