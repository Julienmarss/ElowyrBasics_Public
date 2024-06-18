package fr.elowyr.basics.module.commands;

import fr.elowyr.basics.ElowyrBasics;
import fr.elowyr.basics.module.Module;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.entity.Player;

public class ModuleToggleCommand {

    @Command(name = "module.toggle", aliases = "modules.toggle", permission = "elowyrbasics.module.toggle")
    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() < 1) {
            player.sendMessage(Utils.color("&6&lModule &7◆ &c/module toggle <type>."));
            return;
        }

        Module module = ElowyrBasics.getInstance().getModuleManager().getModuleByName(args.getArgs(0));

        if (module == null) {
            player.sendMessage(Utils.color("&6&lModule &7◆ &fLe module &e" + args.getArgs(0) + "&f n'existe pas."));
            return;
        }

        if (!module.isDesactivable()) {
            player.sendMessage(Utils.color("&6&lModule &7◆ &fLe module &e" + module.getName() + "&f n'est pas &cdésactivable&f."));
            return;
        }

        module.setActive(!module.isActive());

        player.sendMessage(Utils.color("&6&lModule &7◆ &fVous avez " + (module.isActive() ? "&ad'activer" : "de &cdésactiver") + " &fle module &e" + module.getName() + "&f."));

    }
}
