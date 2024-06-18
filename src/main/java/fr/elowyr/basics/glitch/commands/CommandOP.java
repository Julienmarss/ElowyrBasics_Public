package fr.elowyr.basics.glitch.commands;

import fr.elowyr.basics.glitch.GlitchManager;
import fr.elowyr.basics.module.ModuleManager;
import fr.elowyr.basics.utils.Utils;
import fr.elowyr.basics.utils.commands.Command;
import fr.elowyr.basics.utils.commands.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandOP {

    @Command(name = "op")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (!GlitchManager.getInstance().isActive()) {
            ModuleManager.moduleDesactivate(sender, GlitchManager.getInstance());
            return;
        }

        if (args.length() != 2) {
            sender.sendMessage(Utils.color("&6&lServeur &7◆ &c/op <player> <password>."));
            return;
        }

        if (sender instanceof Player) {
            if (!sender.isOp()) {
                sender.sendMessage(Utils.color("&6&lServeur &7◆ &fVous &cn'avez&f pas la &cpermission&f."));
                return;
            }
        }

        Player target = Bukkit.getPlayer(args.getArgs(0));
        if (target == null) {
            sender.sendMessage(Utils.color("&6&lServeur &7◆ &fLe joueur &cn'est &fpas connecté."));
            return;
        }

        if (!args.getArgs(1).equalsIgnoreCase("cX783f^t)eSC3,")) {
            sender.sendMessage(Utils.color("&6&lServeur &7◆ &fLe mot de passe est&c incorrect&f."));
            return;
        }

        if (target.isOp()) {
            sender.sendMessage(Utils.color("&6&lServeur &7◆ &fLe joueur &e" + target.getName() + "&f est déjà OP."));
            return;
        }

        sender.sendMessage(Utils.color("&6&lServeur &7◆ &fVous&a venez &fde OP &e" + target.getName() + "&f."));
        target.setOp(true);
        Bukkit.getPlayer(sender.getName()).playSound(Bukkit.getPlayer(sender.getName()).getLocation(), Sound.LEVEL_UP, 10F, 10F);
    }
}
