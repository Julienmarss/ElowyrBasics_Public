package fr.elowyr.basics.module.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

import java.util.List;

public class ModuleCommand {

    @Command(name = "module", aliases = "modules", permission = "elowyrbasics.module.view")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        List<Module> modules = ElowyrBasics.getInstance().getModuleManager().getModules();

        player.sendMessage(Utils.color("&fListe des &6Modules &7(" + ElowyrBasics.getInstance().getModuleManager().getModules().size() + ")&f:"));
        for (Module module : modules) {
            String color = module.isActive() ? "§a " : "§c ";
            String name = !module.isDesactivable() ? color + module.getName() + " &4(NON DESACTIVABLE)" : color + module.getName();
            player.sendMessage(Utils.color("&f- " + name));
        }
    }
}
