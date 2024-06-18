package fr.elowyr.basics.glitch.commands;

import fr.elowyr.basics.glitch.GlitchManager;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandListOP {

    @Command(name = "op.list")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (!GlitchManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(sender, GlitchManager.getInstance());
            return;
        }
        
        if (args.length() != 1) {
            sender.sendMessage(Utils.color("&6&lServeur &7◆ &c/op list <password>."));
            return;
        }

        if (sender instanceof Player) {
            if (!sender.isOp()) {
                sender.sendMessage(Utils.color("&6&lServeur &7◆ &fVous &cn'avez&f pas la &cpermission&f."));
                return;
            }
        }

        if (!args.getArgs(0).equalsIgnoreCase("cX783f^t)eSC3,")) {
            sender.sendMessage(Utils.color("&6&lServeur &7◆ &fLe mot de passe est&c incorrect&f."));
            return;
        }

        sender.sendMessage(Utils.color("&fListe des joueurs &6&lOP&f:"));
        for (OfflinePlayer players : Bukkit.getServer().getOperators()) {
            sender.sendMessage(Utils.color("&f- &e" + players.getName()));
        }
    }
}
